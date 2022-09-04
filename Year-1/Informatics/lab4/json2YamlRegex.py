from itertools import chain
import re
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


def sequence(*funcs):
    if len(funcs) == 0:
        def result(src):
            yield (), src
        return result

    def result(src):
        for arg1, src in funcs[0](src):
            for others, src in sequence(*funcs[1:])(src):
                yield (arg1,) + others, src
    return result


number_regex = re.compile(
    r"(-?(?:0|[1-9]\d*)(?:\.\d+)?(?:[eE][+-]?\d+)?)\s*(.*)", re.DOTALL)


def parse_number(src):
    match = number_regex.match(src)
    if match is not None:
        number, src = match.groups()
        yield eval(number), src


string_regex = re.compile(
    r"('(?:[^\\']|\\['\\/bfnrt]|\\u[0-9a-fA-F]{4})*?')\s*(.*)", re.DOTALL)


def parse_string(src):
    match = string_regex.match(src)
    if match is not None:
        string, src = match.groups()
        yield eval(string), src


def parse_word(word, value=None):
    l = len(word)

    def result(src):
        if src.startswith(word):
            yield value, src[l:].lstrip()
    result.__name__ = "parse_%s" % word
    return result


parse_true = parse_word("true", True)
parse_false = parse_word("false", False)
parse_null = parse_word("null", None)


def parse_value(src):
    for match in chain(
        parse_string(src),
        parse_number(src),
        parse_array(src),
        parse_object(src),
        parse_true(src),
        parse_false(src),
        parse_null(src),
    ):
        yield match
        return


parse_left_square_bracket = parse_word("[")
parse_right_square_bracket = parse_word("]")
parse_empty_array = sequence(
    parse_left_square_bracket, parse_right_square_bracket)


def parse_array(src):
    for _, src in parse_empty_array(src):
        yield [], src
        return

    for (_, items, _), src in sequence(
        parse_left_square_bracket,
        parse_comma_separated_values,
        parse_right_square_bracket,
    )(src):
        yield items, src


parse_comma = parse_word(",")


def parse_comma_separated_values(src):
    for (value, _, values), src in sequence(
        parse_value,
        parse_comma,
        parse_comma_separated_values
    )(src):
        yield [value] + values, src
        return

    for value, src in parse_value(src):
        yield [value], src


parse_left_curly_bracket = parse_word("{")
parse_right_curly_bracket = parse_word("}")
parse_empty_object = sequence(
    parse_left_curly_bracket, parse_right_curly_bracket)


def parse_object(src):
    for _, src in parse_empty_object(src):
        yield {}, src
        return
    for (_, items, _), src in sequence(
        parse_left_curly_bracket,
        parse_comma_separated_keyvalues,
        parse_right_curly_bracket,
    )(src):
        yield items, src


parse_colon = parse_word(":")


def parse_keyvalue(src):
    for (key, _, value), src in sequence(
        parse_string,
        parse_colon,
        parse_value
    )(src):
        yield {key: value}, src


def parse_comma_separated_keyvalues(src):
    for (keyvalue, _, keyvalues), src in sequence(
        parse_keyvalue,
        parse_comma,
        parse_comma_separated_keyvalues,
    )(src):
        keyvalue.update(keyvalues)
        yield keyvalue, src
        return

    for keyvalue, src in parse_keyvalue(src):
        yield keyvalue, src


def parse(s):
    s = s.strip()
    match = list(parse_value(s))
    if len(match) != 1:
        raise ValueError("not a valid JSON string")
    result, src = match[0]
    if src.strip():
        raise ValueError("not a valid JSON string")
    return result


def json2yamlRun():
    global result
    result = "---\ntable:"
    with open('StartJson.json') as json_file:
        data = str(json.load(json_file))
        data = parse(data)
        getContent(data["table"], "")
        with open("PythonJson2YamlRegex.yaml", 'w') as the_file:
            the_file.writelines(result)


json2yamlRun()
