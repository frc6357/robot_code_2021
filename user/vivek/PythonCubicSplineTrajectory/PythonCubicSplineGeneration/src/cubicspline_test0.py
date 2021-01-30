#!/usr/bin/env python3
#
# Calculate a sensible cubic spline path between two points identified
# by position and gradient (in the form of an angle in degrees counter-clockwise
# from the positive x axis).
#
# From https://docs.scipy.org/doc/scipy/reference/generated/scipy.interpolate.CubicSpline.html
#
from scipy.interpolate import CubicSpline
import matplotlib.pyplot as plt
import numpy as np
import sys
import math

# Start and end point locations
x = np.array([0, 10])
y = np.array([0, 0])

# Gradients
start_theta_deg = -90.0
end_theta_deg   = 0.0

# Gradient limit before we try using a rotation.
gradient_limit  = 100.0

# Rotate points by degrees around the origin. x and y are np.narray types
"""
def Rotate(x, y, degrees):
    radangle = math.radians(degrees)
    rotmatrix = np.array([[math.cos(radangle), -math.sin(radangle)], [math.sin(radangle), math.cos(radangle)]])

    # There's almost certainly a nice way to do this in a single line but, until I figure it out, let's
    # do this point-by-point.
    xrot = np.zeros(len(x))
    yrot = np.zeros(len(y))

    for index in range(0, len(x)):
        rotpt = np.dot(rotmatrix, (x[index], y[index]))
        xrot[index] = rotpt.T[0]
        yrot[index] = rotpt.T[1]

    print("Original values:")
    print(x, y)
    print("Rotated by %d degrees:"%degrees)
    print(xrot, yrot)

    return (xrot, yrot)
"""


def rotate(point, angle):
    x, y = point
    rotx = x * math.cos(math.radians(angle)) - y * math.sin(math.radians(angle))
    roty = x * math.sin(math.radians(angle)) + y * math.cos(math.radians(angle))
    return (rotx, roty)


finish          = False
angle_delta     = 0.0  # Degrees
angle_adjust    = 10.0 # Degrees

while not finish and (angle_delta < 180.0):

    # Make sure we don't have an infinite gradient at the start or end point.
    if ((start_theta_deg + angle_delta) != 90.0) and ((end_theta_deg + angle_delta) != 90.0):
        # Determine start and end gradients in terms of radian angles.
        start_theta     = math.radians(start_theta_deg + angle_delta)
        end_theta       = math.radians(end_theta_deg + angle_delta)

        # Gradient is just the tangent of the angle.
        start_xm = math.tan(start_theta)
        end_xm   = math.tan(end_theta)
        print("Start gradients: x %f, y %f"%(start_xm, end_xm))

        # Rotate our start and end points by the current angle offset (done to try to avoid large gradient segments).
        rotated_x = rotate(x, angle_delta)
        rotated_y = rotate(y, angle_delta)
        print("Rotated by %d degrees:"%angle_delta)
        print(rotated_x, rotated_y)
        cs = CubicSpline(rotated_x, rotated_y, bc_type=((1, start_xm), (1, end_xm)))
        xs = np.arange(0, 11)

        ys            = cs(xs)
        gradients     = cs(xs, 1)
        radian_angles = np.arctan(gradients)
        degree_angles = np.degrees(radian_angles)

        max_gradient  = np.amax(gradients)
        print("Maximum gradient = %f"%max_gradient)

        # If the calculate spline contains any near-vertical sections, go back and try the next rotation.
        if max_gradient <= gradient_limit:
            # This spline is good so we need to de-rotate everything.
            finish = True
            print("Matched spline with no gradient greater than %f after %f degree rotation."%(gradient_limit, angle_delta))
            degree_angles -= angle_delta
            output_xs = rotate(xs, -angle_delta)
            output_ys = rotate(ys, -angle_delta)
        else:
            angle_delta += angle_adjust
    else:
        angle_delta += angle_adjust

if angle_delta >= 180.0:
    print("Failed to find a fitting spline with max gradient < %s after rotating up to 180 degrees!"%gradient_limit)
    sys.exit(1)

fig, ax = plt.subplots(figsize=(6.5, 4))
ax.set_aspect(1)

ax.plot(output_xs, output_ys, label="Fit")
ax.plot(output_xs, output_ys, 'o')

ax.plot(xs, ys, label="Pre-rotate")
ax.plot(xs, ys, 'o')

dx = np.cos(np.deg2rad(degree_angles))
dy = np.sin(np.deg2rad(degree_angles))

ax.plot(x, y, 'o', label='Endpoints')

print("Gradients (degrees):")
print(degree_angles)

for index in range(0, len(output_xs)):
    ax.arrow(output_xs[index], output_ys[index], dx[index], dy[index])

ax.legend(loc='best', ncol=2)
plt.show()

sys.exit(0)