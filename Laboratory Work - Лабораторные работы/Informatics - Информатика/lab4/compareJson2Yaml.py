import os
import time
import json2yaml
import json2yamlWithLibrary
start_time = time.time()
for i in range(1, 10):
    json2yaml.json2yamlRun()
ownSolution = time.time() - start_time
print("--- %s seconds ---" % (ownSolution))

start_time = time.time()
for i in range(1, 10):
    json2yamlWithLibrary.json2yamlWithLibraryRun()
librarySolution = time.time() - start_time
print("--- %s seconds ---" % (librarySolution))

print(
    f"--- my own solution is {librarySolution / ownSolution} times faster that a library")
