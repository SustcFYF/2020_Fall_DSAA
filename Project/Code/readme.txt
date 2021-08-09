In our package Project, there are two classes.

The first class is Matrix, which is used to construct the class Matrix with an object matrix.
In this way, we use a one-dimensional array to store the two-dimensional matrix.
By using setter and getter method to write and read the element in the matrix, we can increase the calculation speed.

The second class is MatrixMultiply, which is used to calculate the crossover point and the matrix multiplication.
In the method calibrate_crossover_point, we can calculate the crossover_point automatically and store it in an txt file.
After that, run MatrixMultiply again and we can read the crossover_point from the txt file to do the matrix multiplication.
The output will be the running time of three methods (brutal, strassen and adaptive methods) mentioned in our report.

In verifying our experiments as reported in the report, we strongly recommend you to modify the parameters (startPoint and
testSize) in the method calibrate_crossover_point, because the results in our experiments are not obtained in one run.