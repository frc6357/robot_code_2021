import json
import numpy as np
from cubicspline_test_tbd import cubicSplineSegment
# Takes in a JSON file in the same directory as the python script and loads the JSON into the json_obj variable as a
# list of dictionaries of the format {'x' : (int), 'y' : (int), 'theta' : (float) }
with open("inputFile.json", "r") as file:
    json_obj = json.load(file)
points = [dictObj for dictObj in json_obj.items()][0][1]

# iterates through the list of points and groups into x values and y values of the current and next point in the list
# also assigns the variable for the angles of the current and next point of the list
for i in range(len(points)):
    if i < len(points) - 1:
        xs = np.array([int(points[i]['x']), int(points[i+1]['x'])])
        ys = np.array([int(points[i]['y']), int(points[i + 1]['y'])])
        start_angle = float(points[i]['theta'])
        end_angle = float(points[i+1]['theta'])

        string = "segment" + str(i + 1)
        splinePath = {}
        splineSegment = cubicSplineSegment(xs, ys, start_angle, end_angle)
        splinePath[string] = splineSegment

















