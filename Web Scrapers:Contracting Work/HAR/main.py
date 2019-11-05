import requests
from bs4 import BeautifulSoup as bs
import re
import pandas as pd

class Entry:

    listing_price = None
    listing_status = None
    mlsnum = None
    daysonsite = None
    beds = None
    baths = None
    buildyear = None
    propertytype =  None
    address = None
    sqft = None
    foundation = None
    garage = None
    cost_sqft =  None

    url = None

    def __init__(self, url):
        self.url = url

    def listout(self):
        return [self.listing_price, self.listing_status,  self.mlsnum, self.daysonsite, self.beds, self.baths, self.buildyear, self.propertytype, self.address, self.sqft, self.foundation, self.garage, self.cost_sqft, self.url]


#Main

filename = input("Output CSV filename: ")

if(not filename.endswith(".csv")):
    filename = filename + ".csv"

rl_URLs = list()
nh_URLs = list()

entries = list()

rl_URL1 = "https://www.har.com/search/dosearch?page="
rl_URL2 = "&for_sale=1&property_class_id=3&listing_price_max=100000"
nh_URL1 = "https://www.har.com/search/dosearch?page="
nh_URL2 = "&for_sale=1&property_class_id=1,2&square_feet_max=2000&new_constr2=Yes"
for i in range(0, 10):
    print(str(i * 10) + "%")
    rl_URL = rl_URL1 + str(i) + rl_URL2
    nh_URL = nh_URL1 + str(i) + nh_URL2

    residential_lots_page = requests.get(rl_URL)
    new_homes_page = requests.get(nh_URL)

    if(residential_lots_page.status_code != 200):
        print("Residential Lots Page didn't respond, please try again.")
    if(new_homes_page.status_code != 200):
        print("New Homes Page didn't respond, please try again.")

    if(residential_lots_page.status_code == 200 and new_homes_page.status_code == 200):
        #Residential Lots first
        rl_soup = bs(residential_lots_page.content, 'html.parser')

        for a in rl_soup.find_all('a', class_="mpi_img_link"):
            #print('https://www.har.com' + a['href'])
            rl_URLs.append('https://www.har.com' + a['href'])

        #New Homes

        nh_soup = bs(new_homes_page.content, 'html.parser')

        for a in nh_soup.find_all('a', class_="mpi_img_link"):
            #print(a['href'])
            nh_URLs.append('https://www.har.com' + a['href'])


    for url in rl_URLs:
        e = Entry(url)

        page = requests.get(url)
        if(page.status_code != 200):
            continue

        soup = bs(page.content, 'html.parser')

        e.listing_price = soup.find_all('div', class_="dc_value")[0].text.strip()
        e.listing_price = e.listing_price[:e.listing_price.rindex("$")].strip()

        for a in soup.find_all('div', class_="dc_value"):
            if("status_active" in a):
                e.listing_status = a.text.strip()
                break

        e.mlsnum = e.url[e.url.rindex("_") + 1:]

        e.daysonsite = soup.find_all('b')[0].text.strip()

        e.propertytype = "Residential Lot"

        e.address = soup.find_all('h1', class_="property_title")[0].text.strip()
        e.address = re.sub(' +', ' ', e.address[:e.address.rindex("Directions")].replace('\n', ','))
        e.address = e.address[:e.address.rindex(',')]

        entries.append(e)

    print(str((i * 10) + 5) + "%")

    for url in nh_URLs:
        e = Entry(url)

        page = requests.get(url)
        if(page.status_code != 200):
            continue

        soup = bs(page.content, 'html.parser')

        e.listing_price = soup.find_all('div', class_="dc_value")[0].text.strip()
        e.cost_sqft = e.listing_price[e.listing_price.index("(") + 1:e.listing_price.index(")")]
        e.listing_price = e.listing_price[:e.listing_price.index("(")].strip()

        e.listing_status = soup.find_all('div', class_="dc_value")[1].text.strip()

        e.mlsnum = e.url[e.url.rindex("_") + 1:]

        e.daysonsite = soup.find_all('b')[0].text.strip()

        e.propertytype = "New Home"

        e.address = soup.find_all('h1', class_="property_title")[0].text.strip()
        e.address = re.sub(' +', ' ', e.address[:e.address.rindex("Directions")].replace('\n', ','))
        e.address = e.address[:e.address.rindex(',')]

        if("Bedroom" in str(page.content)):
            for a in soup.find_all('div', class_="dc_value"):
                if("Bedroom(s)" in a.text):
                    e.beds = a.text.strip()
                    e.beds = e.beds[:e.beds.rindex("Bedroom(s)") - 1]
                    break


        e.baths = soup.find_all('div', class_="dc_value")[11].text.strip()
        This is broken, fix it
        if("Garage" in str(page.content)):
            for a in soup.find_all('div', class_="dc_value"):
                if("/" in a.text):
                    e.garage = a.text.strip()
                    e.garage = e.garage[:e.garage.rindex("/") - 1]
                    break

        if("Year Built" in str(page.content)):
            for a in soup.find_all('div', class_="dc_value"):
                if("Appraisal District" in a.text and not "," in a.text and not "m²" in a.text):
                    e.buildyear = a.text.strip()
                    e.buildyear = e.buildyear[:e.buildyear.rindex("/") - 1]
                    break

        for a in soup.find_all('div', class_="dc_value"):
            if("m²" in a.text):
                e.sqft = a.find_all('span')[0].text.strip()
                break

        for a in soup.find_all('div', class_="dc_blocks_2c"):
            if("Foundation" in a.text):
                e.foundation = a.find_all('div', class_="dc_value")[0].text.strip()
                break

        entries.append(e)

print("100%\nExporting to CSV...")

data = []
columns = ["Listing Price", "Listing Status",  "MLS#", "Days on HAR", "Beds", "Baths", "Year Built", "Property Type", "Address", "Sq Ft", "Foundation", "Garages", "Cost / sq ft", "URL"]

for e in entries:
    data.append(e.listout())

df = pd.DataFrame(data, columns = columns)

df.to_csv(filename)
