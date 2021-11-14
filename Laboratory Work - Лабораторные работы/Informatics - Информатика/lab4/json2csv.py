import json
from os import error, pathsep
from enum import Enum


def getContent(json, previousPath):
    global pathText, messageText
    if type(json) == str:
        messageText += json + ",\n"
        pathText += previousPath + ",\n"

    elif type(json) == dict:
        for keys in json.keys():
            getContent(json[keys], previousPath + f" / {keys}")

    elif type(json) == list:
        for index in range(len(json)):
            try:
                value = json[index]
                if type(value) == dict:
                    keyValue = list(value.keys())[0]
                    getContent(value[keyValue],
                               previousPath + f" / {index} / {keyValue}")
                else:
                    raise Exception
            except Exception as e:
                messageText += json[index] + ",\n"
                pathText += previousPath + f" / {index}" + ",\n"


def json2csvRun():
    global pathText, messageText
    pathText = ""
    messageText = ""
    with open('StartJson.json') as json_file:
        data = json.load(json_file)
        getContent(data["table"], "table")
        with open("PythonJson2CSV.csv", 'w') as the_file:
            the_file.writelines(pathText + messageText)


json2csvRun()
