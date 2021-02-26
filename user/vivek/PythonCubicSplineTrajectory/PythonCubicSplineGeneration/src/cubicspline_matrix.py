import numpy as np

point = {
    "x": "0",
    "y": "0",
    "theta": "0.0"
    }

point1 = {
      "x": "8",
      "y": "8",
      "theta": "0.0"
    }
x = int(point["x"])
y = int(point["y"])
theta = point["theta"]

x1 = int(point1["x"])
y1 = int(point1["y"])
theta1 = point1["theta"]

spline_matrix = np.array([pow(x, 3), pow(x, 2), x, 1], [3*pow(x, 2), 2*x, 1, 0], [pow(x1, 3), pow(x1, 2), x1, 1], [3*pow(x1 ,2), 2*x1, 1, 0])

print(spline_matrix.shape)

