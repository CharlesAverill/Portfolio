import pandas as pd
import requests
import urllib.request
import time
from bs4 import BeautifulSoup

columns = ["Name", "Category", "Quantity"]

categories = dict()

print(chr(27) + "[2J")

print('Please scan barcode, type \'q\' to save to CSV and quit.')

items = dict()

barcode_num = input()
while(not barcode_num is "q"):
    url = "https://www.barcodelookup.com/" + str(barcode_num)

    response = requests.get(url)

    soup = BeautifulSoup(response.text, "html.parser")

    title = ((soup.findAll('h4')[0]).text).strip()

    category = None

    lines = response.text.splitlines()

    for i in range(len(lines)):
        line = lines[i]
        if(("Category: " in line)):
            category = line[line.find("Category: ") + 37 : line.rfind("</span></div></div></div><div class=")]
            break

    """
    for i in range(len(category)):
        print(category[i])
        print(i)
        print()
    """
    data = (title, category)

    print(data)

    if(not title in items):
        items[title] = 1
        categories[title] = category
    else:
        items[title] = items[title] + 1
        categories[title] = category
    print(chr(27) + "[2J")
    print('Please scan barcode, type \'q\' to quit.')
    barcode_num = input()

names = list(items.keys())
#print(names)
quantities = list(items.values())
cats = list(categories.values())

Output = {'Name':names, 'Category':cats, 'Quantity':quantities}

df = pd.DataFrame(Output, columns = columns)

print(df)

print("Please enter output filename without extension")

filename = input() + '.xlsx'

export = df.to_excel(filename, index=None, header=True)

print("Thank you for using Charles Averill\'s Barcode Scraper")
