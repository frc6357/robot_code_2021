# Calculate a sensible cubic spline path between two points identified
# by position and gradient (in the form of an angle in degrees counter-clockwise
# from the positive x axis).
#
# From https://docs.scipy.org/doc/scipy/reference/generated/scipy.interpolate.CubicSpline.html
#
import matplotlib.pyplot as plt
import numpy as np
import sys
import math
from scipy.interpolate import CubicSpline
import copy

# xs_input is a numpy array of the start and end x-coordinates
# ys_input is a numpy array of the start and end y-coordinates
# start_angle_deg is a float that represents the angle the robot is pointing in degrees at the start point
# end_angle_deg is a float that represents the angle the robot is pointing in degrees at the end point
# outputs a dictionary with xs and ys key
# xs contains a numpy array that represents the xs of the single cubic spline segment
# ys contains a numpy array that represents the ys of the single cubic spline segment
def cubicSplineSegment(xsParam, ysParam, start_angle_deg, end_angle_deg):


    # Start and end point locations
    x_init = xsParam
    y_init = ysParam

    # Gradients
    start_theta_deg = start_angle_deg
    end_theta_deg = end_angle_deg
    # TODO:Can do start angle 0 and end angle = 90; start angle 0 end angle = -90; can do start angle = x and
    #  end angle = -x  for x in [0, 45.99]
    # seems to be true that (start angle) - (end angle) <= 90 degrees


    # Gradient limit before we try using a rotation
    gradient_limit = 100.0


    # Rotate points by degrees around the origin. x and y are np.narray types
    def Rotate(x, y, degrees):
        radangle = math.radians(degrees)
        rotmatrix = np.array([[math.cos(radangle), -math.sin(radangle)], [math.sin(radangle), math.cos(radangle)]])

        # There's almost certainly a nice way to do this in a single line but, until I figure it out, lets
        # do this point-by-point
        xrot = np.zeros(len(x))
        yrot = np.zeros(len(y))

        for index in range(0, len(x)):
            rotpt = np.dot(rotmatrix, (x[index], y[index]))
            xrot[index] = rotpt.T[0]
            yrot[index] = rotpt.T[1]

        print("original values: ")
        print(x, y)
        print("Rotated by %d degrees:" % degrees)
        print(xrot, yrot)

        return (xrot, yrot)


    def toOrigin(x, y):
        # initializes variable to a copy so that changes to the copy will not affect the parameter variables
        x_copy = copy.deepcopy(x)
        y_copy = copy.deepcopy(y)

        # Stores translation and rotation values
        x_adjust = -x[0]
        y_adjust = -y[0]

        # Translates and rotates start point to (0,0) 0 degrees
        # Applies this translation to all x and y points
        # Applies rotation to all gradients
        x_copy += x_adjust
        y_copy += y_adjust

        return {
                "x":x_copy,
                "y":y_copy,
                "x_adjust":x_adjust,
                "y_adjust": y_adjust,
               }


    # assigns the translated and rotated points as well as storing how much the x, y, and angle was changed by
    startPoints = toOrigin(x_init, y_init)

    # reconstructs the numpy arrays for x and y
    x = startPoints["x"]
    y = startPoints["y"]

    # assigns the x, y and angle adjustments into unique variables
    x_adjust = startPoints["x_adjust"]
    y_adjust = startPoints["y_adjust"]

    finish = False
    angle_delta = -start_theta_deg  # Degrees
    angle_adjust = 10.0 if (end_theta_deg - start_theta_deg) < 0 else -10.0  # Degrees

    while not finish and (angle_delta < 180.0):
        # Make sure we don't have an infinite gradient at the start or end point
        if ((start_theta_deg + angle_delta) != 90.0) and ((end_theta_deg + angle_delta) != 90.0):
            # Deterimine start and end gradients in terms of radian angles
            start_theta = math.radians(start_theta_deg + angle_delta)
            end_theta = math.radians(end_theta_deg + angle_delta)

            # Gradient is just the tangent of the angle
            start_xm = math.tan(start_theta)
            end_xm = math.tan(end_theta)
            print("Start gradients: x %f, y %f" % (start_xm, end_xm))

            # Rotate our start and end points by the current angle offset (done to try to avoid large gradient segments)
            rotated_x, rotated_y = Rotate(x, y, angle_delta)
            print("Rotated by %d degrees:" % angle_delta)
            print(rotated_x, rotated_y)
            cs = CubicSpline(rotated_x, rotated_y, bc_type=((1, start_xm), (1, end_xm)))
            print(cs(rotated_x[len(rotated_x)-1]))

            step = rotated_x[1]/20

            xs = np.arange(0, rotated_x[1], step)
            #xs = np.arange(0,11)
            ys = cs(xs)
            gradients = cs(xs, 1)
            radian_angles = np.arctan(gradients)
            degree_angles = np.degrees(radian_angles)

            gradients = np.absolute(gradients)
            max_gradient = np.amax(gradients)
            print("maximum gradient = %f" % max_gradient)

            # If the calculated spline contains any near-vertical sections, go back and try the next rotation
            if max_gradient <= gradient_limit:
                # This spline is good so we need to de-rotate everything
                finish = True
                print("Matchd spline with no gradient greater than %f after %f degree rotation." % (
                gradient_limit, angle_delta))
                degree_angles -= angle_delta
                output_xs, output_ys = Rotate(xs, ys, -angle_delta)

                # Undo the initial rotation and translation of start and end point to each point on the spline

                output_xs -= x_adjust
                output_ys -= y_adjust



            else:
                angle_delta += angle_adjust
        else:
            angle_delta += angle_adjust
    if angle_delta >= 180.0:
        print("Failed to find a fitting spline with max gradient < %s after rotating up to 180 degrees!" % gradient_limit)
        sys.exit(1)



    return {"xs" : output_xs, "ys" : output_ys, "degree_angles" : degree_angles, "spline" : cs}





"""
    fig, ax = plt.subplots(figsize=(6.5, 4))
    ax.set_aspect(1)

    ax.plot(output_xs, output_ys, label="Fit")
    ax.plot(output_xs, output_ys, 'o')

    ax.plot(xs, ys, label="Pre-rotate")
    ax.plot(xs, ys, 'o')

    ax.plot(xsParam, ysParam, 'o', label="Endpoints")

    dx = np.cos(np.deg2rad(degree_angles))
    dy = np.sin(np.deg2rad(degree_angles))



    print("Gradients (degrees):")
    print(degree_angles)

    for index in range(0, len(output_xs)):
        ax.arrow(output_xs[index], output_ys[index], dx[index], dy[index])

    #ax.set_ylim([-4, 4])
    #ax.set_xlim([0, end_x+1])
    ax.legend(loc='best', ncol=2)
    plt.show()

    print("CubicSpline data type:", type(cs))
    print("ys data type:", type(ys))
"""