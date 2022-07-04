try:
    from BeautifulSoup import BeautifulSoup
except ImportError:
    from bs4 import BeautifulSoup
import requests

url = "https://itmo.ru/ru/schedule/0/P3109/schedule.htm"

page = requests.get(url)

html = page.content.decode("utf-8")
parsed_html = BeautifulSoup(html)
print(parsed_html.body.find('table', attrs={'id': '1day'}).text)
