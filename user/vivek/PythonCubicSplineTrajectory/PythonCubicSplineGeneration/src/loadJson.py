import json
import numpy as np
import math


# Takes in a file in the same directory as the python script and loads the JSON into the json_obj variable
with open("inputFile.json", "r") as file:
    json_obj = json.load(file)

# Takes in JSON file and iterates through the array and outputs a chunk of each point and angle (x, y, theta)
arr = [dictObj for dictObj in json_obj.items()][0]
arr = [tuple(i.values()) for i in arr[1]]

# Takes in the list of points and angles and groups the current tuple and the next tuple together under one tuple
# ( (x1, y1, theta1), (x2, y2, theta2) )
# Does this for each coordinate and angle in the JSON file
points_angle =[]
for i in range(len(arr)):
    if i < len(arr) -1:
        e1 = arr[i]
        e2 = arr[i+1]
        x = (e1, e2)
        points_angle.append(x)
print(points_angle)


for i in points_angle:


    # splits the tuple into each individual chunk and assign each x and y-value and angle to a variable for each chunk
    p1 = i[0]
    p2 = i[1]
    x1, y1, a1 = p1
    x2, y2, a2 = p2
