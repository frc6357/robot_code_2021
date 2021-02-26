import json
import numpy as np
from cubicSplineGeneration import cubicSplineSegment
import matplotlib.pyplot as plt
from orthogonal_point import orthoPointDistance
# TODO: Need to confirm the test case of loops/circles and paths that moves the robot 'backwards' or to the left

# Takes in a JSON file in the same directory as the python script and loads the JSON into the json_obj variable as a
# list of dictionaries of the format {'x' : (int), 'y' : (int), 'theta' : (float) }
with open("inputFile.json", "r") as file:
    json_obj = json.load(file)
points = [dictObj for dictObj in json_obj.items()][0][1]

# iterates through the list of points and groups into x values and y values of the current and next point in the list
# also assigns the variable for the angles of the current and next point of the list
# Initializes arrays for the xs and ys of the spline and gradient for each x and y pair
splinePathXs = []
splinePathYs = []
gradients = []
for i in range(len(points)):
    if i < len(points) - 1:
        # creates array of xs and ys of points for the current and next point
        # making each element a floating point number based off of the loaded in JSON file as well as initializes the
        # start and end angle, or the angle for the current point and the angle for the next point
        xs = np.array([float(points[i]['x']), float(points[i+1]['x'])])
        ys = np.array([float(points[i]['y']), float(points[i + 1]['y'])])
        start_angle = float(points[i]['theta'])
        end_angle = float(points[i+1]['theta'])

        # Generates and chronologically adds all required x values for the path and all required y values for the path
        # into one dictionary splinePath
        spline = cubicSplineSegment(xs, ys, start_angle, end_angle)
        splinePathXs.append(spline['xs'])
        splinePathYs.append(spline['ys'])
        gradients.append(spline['gradients'])

# makes the spline path one list of sequential xs and ys instead a list of lists
splinePathXs = [y for x in splinePathXs for y in x]
splinePathYs = [y for x in splinePathYs for y in x]
gradients = [y for x in gradients for y in x]



# makes the xs and ys for the general spline into xs and ys for each side of the wheelbase and adds to the lists
# initialized above
if (len(splinePathYs) == len(splinePathYs)) and (len(gradients) == len(splinePathXs)):
    orthoPoints = orthoPointDistance(gradients, splinePathXs, splinePathYs, 0.5)

# initialize and assigns values to lists for the xs and ys for the left and right side of the drivetrain
xsLeft = orthoPoints['x_left']
ysLeft = orthoPoints['y_left']
xsRight = orthoPoints['x_right']
ysRight = orthoPoints['y_right']
xsPair = orthoPoints['pair_x']
ysPair = orthoPoints['pair_y']






# Initializes matplotlib figure for plotting
fig, ax = plt.subplots(figsize=(6.5, 4))
ax.set_aspect(1)

# Plots all the x and y values creating a visual for the general spline path
ax.plot(splinePathXs, splinePathYs, color='k', linestyle='dotted', label='general path')

# Plots the left side of drivetrain path
ax.plot(xsLeft, ysLeft, color='b', linestyle='dotted', label='left side')

# Plots the right side of drivetrain path
ax.plot(xsRight, ysRight, color='r', linestyle='dotted', label='right side')

for i in range(0, len(xsPair)):
    xsPairLoop = xsPair[i]
    ysPairLoop = ysPair[i]
    ax.plot(xsPairLoop, ysPairLoop, color='g', linestyle='-')


#ax.legend(loc='best', ncol=2)
plt.show()















