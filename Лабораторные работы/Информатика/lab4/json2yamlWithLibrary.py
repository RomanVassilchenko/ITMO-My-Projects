import yaml
import json


def json2yamlWithLibraryRun():
    with open('StartJson.json') as json_file:
        data = json.load(json_file)
        yaml.dump(data, open("PythonJson2YamlWithLibrary.yaml", "w"), allow_unicode=True,
                  default_flow_style=False)
