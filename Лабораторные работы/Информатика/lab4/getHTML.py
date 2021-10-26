import requests

url = "https://itmo.ru/ru/schedule/0/P3109/schedule.htm"

page = requests.get(url)

html = page.content.decode("utf-8")

startIndex = html.index('<table id="1day"')
endIndex = html.index('</table>', startIndex)

html = html[startIndex:endIndex+8]
html = "<div>" + html + "</div>"
