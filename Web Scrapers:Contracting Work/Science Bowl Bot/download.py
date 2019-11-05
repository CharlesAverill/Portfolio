import requests
from bs4 import BeautifulSoup as bs

url = "https://science.osti.gov/wdts/nsb/Regional-Competitions/Resources/HS-Sample-Questions#set1"
page = requests.get(url)

pdf_urls = list()

if(page.status_code != 200):
    print("Page didn't respond, please try again.")
else:
    soup = bs(page.content, 'html.parser')

    for a in soup.find_all('a', class_="pdf"):
        pdf_urls.append("https://science.osti.gov" + a['href'])
        pdf = requests.get("https://science.osti.gov" + a['href'])
        name = a['href'][a['href'].rindex("/") + 1:]

        set = int(a['href'][a['href'].index("Sample-Set-")+11:a['href'].rindex("/")])

        if("?" in a['href']):
            name = a['href'][a['href'].rindex("/") + 1:a['href'].rindex("?")]

        open('PDFs/' + str(set) + "/" + name, 'wb').write(pdf.content)
