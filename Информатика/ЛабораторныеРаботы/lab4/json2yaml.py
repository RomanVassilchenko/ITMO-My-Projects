import json
from os import error
from enum import Enum


class BracketsType(Enum):
    Element = "  "
    ListElement = "- "


def getContent(json, previousTabs):
    global result
    if type(json) == str:
        result += json

    elif type(json) == dict:
        for keys in json.keys():
            result += "\n" + previousTabs + BracketsType.Element.value + keys + ": "
            getContent(json[keys], previousTabs + "  ")

    elif type(json) == list:
        for index in range(len(json)):
            try:
                value = json[index]
                if type(value) == dict:
                    keyValue = list(value.keys())[0]
                    result += "\n" + previousTabs + BracketsType.ListElement.value + keyValue + ": "
                    getContent(value[keyValue], previousTabs + "  ")
                else:
                    raise Exception
            except Exception as e:
                result += "\n" + previousTabs + BracketsType.ListElement.value + value


def json2yamlRun():
    global result
    result = "---\ntable:"
    with open('StartJson.json') as json_file:
        data = json.load(json_file)
        getContent(data["table"], "")
        with open("PythonJson2Yaml.yaml", 'w') as the_file:
            the_file.writelines(result)

json2yamlRun()
