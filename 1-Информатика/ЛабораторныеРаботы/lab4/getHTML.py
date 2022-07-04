import requests
import html_to_json

url = "https://itmo.ru/ru/schedule/0/P3109/schedule.htm"

page = requests.get(url)

html = page.content.decode("utf-8")

startIndex = html.index('<table id="1day"')
endIndex = html.index('</table>', startIndex)

html = html[startIndex:endIndex+8]


output_json = html_to_json.convert(html)
print(output_json)

with open("parsedJson.json", 'w') as the_file:
    the_file.writelines(str(output_json).replace("'", '"'))
