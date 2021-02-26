import numpy as np
import copy
import math

# Takes in a numpy array of the x-values, y-values, and the derivative/gradient at each x,y pair
# Also needs a float parameter that represents the distance the points will be separated by
# Outputs a dicitonary of a list of x and y values that represent the values for the left side of the wheelbase and
# right side of the wheelbase that are a distance apart, with that distance being the length of the robot wheelbase

def orthoPointDistance(gradients, xs, ys, d):
    # Initialize the arrays that contain the x and y values for each side of the drivetrain
    xsLeft = []
    ysLeft = []
    xsRight = []
    ysRight = []

    pair_x = []
    pair_y = []
    # Iterates through the input x and y values to create two sets of coordinates equidistant from the original point
    for i in range(0, len(gradients)):

        # Makes a a copy of each x and y value as well as slope
        x, y = copy.deepcopy(xs[i]), copy.deepcopy(ys[i])
        m = copy.deepcopy(gradients[i])

        if m != 0:
            # Initialzes the value of the slope perpendicular to the derivative at each point
            m = -1/m

            # Calculates the amount to adjust the x value to make it left or right and also of the distance specified
            val = d / np.sqrt(1 + math.pow(m, 2))

            # Shifts x value to the left
            x1 = x - val
            # Shifts x value to the right
            x2 = x + val

            # calculates the corresponding y values to the above x values to make them a distance d apart
            y1 = m * (x1 - x) + y
            y2 = m * (x2 - x) + y

            if y1 > y:
                xsLeft.append(x1)
                ysLeft.append(y1)
                xsRight.append(x2)
                ysRight.append(y2)
            else:
                xsLeft.append(x2)
                ysLeft.append(y2)
                xsRight.append(x1)
                ysRight.append(y1)


        else:
            xsLeft.append(x)
            xsRight.append(x)
            ysLeft.append(y + d)
            ysRight.append(y - d)





    for i in range(0, len(xsLeft)):
        pair_x.append([xsLeft[i], xsRight[i]])
        pair_y.append([ysLeft[i], ysRight[i]])



    # After finished iterating through the list, return a dictionary of these lists containing the calculated points
    return {
            "x_left" : xsLeft, "y_left" : ysLeft, "x_right" : xsRight, "y_right" : ysRight, "pair_x" : pair_x,
            "pair_y" : pair_y
            }
