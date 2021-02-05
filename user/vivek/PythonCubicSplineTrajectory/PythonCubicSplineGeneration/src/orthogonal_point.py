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
    # Iterates through the input x and y values to create two sets of coordinates equidistant from the original point
    for i in range(0, len(gradients)):
        # Makes a a copy of each x and y value as well as slope
        x, y = copy.deepcopy(xs[i]), copy.deepcopy(ys[i])
        m = copy.deepcopy(gradients[i])

        # Initialzes the value of the slope perpendicular to the derivative at each point
        m = -1/m

        # Calculates the amount to adjust the x value to make it left or right and also of the distance specified
        val = d / np.sqrt(1+math.pow(m, 2))

        # Shifts x value to the left
        xLeft = x - val
        # Shifts x value to the right
        xRight = x + val

        # calculates the corresponding y values to the above x values to make them a distance d apart
        yLeft = m*(xLeft - x) + y
        yRight = m*(xRight - x) + y

        # Add the new calculated values to the corresponding lists initialized at the beginning
        xsLeft.append(xLeft)
        xsRight.append(xRight)
        ysLeft.append(yLeft)
        ysRight.append(yRight)

    # After finished iterating through the list, return a dictionary of these lists containing the calculated points
    return {"x_left" : xsLeft, "y_left" : ysLeft, "x_right" : xsRight, "y_right" : ysRight}


