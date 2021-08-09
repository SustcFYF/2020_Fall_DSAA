package Project;

import java.io.*;

public class MatrixMultiply {

    public static int crossover_point;

    /**
     * Estimates a value for the crossover point for matrix size to decide whether
     * to use Strassen’s approach or the standard approach by running a series of
     * tests of multiplying randomly generated matrices of different sizes.
     *
     * <p></p>It should save this value to a configuration file.
     */
    public static void calibrate_crossover_point() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("./src/Project/crossover_point.txt"));
            String readline = br.readLine();
            crossover_point = Integer.parseInt(readline);
            System.out.println(crossover_point);

            // the parameters used to test
            int startPoint = 2000;
            int testSize = 10;

            long[] T1 = new long[testSize];
            long[] T2 = new long[testSize];
            long[] T3 = new long[testSize];
            Matrix a, b, c;
            long start, end;
            int n; // the size of the matrix
            for (int i = 0; i < testSize; i++) {
                n = startPoint + i; // the size of the matrix
                // generate two random matrix a and b
                a = GenerateRandomMatrix(n);
                b = GenerateRandomMatrix(n);
                System.out.printf("n = %-10d ", n); //print the size of matrix output

                start = System.currentTimeMillis();  // record the start time
                c = square_matrix_multiply(a, b);    // run square_matrix_multiply()
                end = System.currentTimeMillis();    // record the end time
                T1[i] = end - start;                 // T1 = standard method
                System.out.printf("Brutal: " + "T1 = %-10d ms    ", T1[i]);

                start = System.currentTimeMillis();  // record the start time
                c = strassen_multiply(a, b);         // run square_matrix_multiply()
                end = System.currentTimeMillis();    // record the end time
                T2[i] = end - start;                 // T1 = standard method
                System.out.printf("Strassen: " + "T2 = %-10d ms    ", T2[i]);

                start = System.currentTimeMillis();  // record the start time
                c = adaptive_multiply(a, b);         // run adaptive_multiply()
                end = System.currentTimeMillis();    // record the end time
                T3[i] = end - start;                 // T2 = adaptive strassen
                System.out.printf("adaptive: " + "T3 = %-10d ms\n", T3[i]);
            }
        } catch (IOException e) { // if there is no crossover point, then find it automatically
            int testSize = 200;
            long[] T1 = new long[testSize];
            long[] T2 = new long[testSize];
            Matrix a, b, c;
            long start, end;
            long minIndex = 0;
            long maxIndex = 0;
            int n; // the size of the matrix
            int startPoint = 100; // the start point to find the crossover_point
            for (int i = 0; i < testSize; i++) {
                n = startPoint + i; // the size of the matrix
                // generate two random matrix a and b
                a = GenerateRandomMatrix(n);
                b = GenerateRandomMatrix(n);
                start = System.currentTimeMillis();  // record the start time
                c = square_matrix_multiply(a, b);    // run square_matrix_multiply()
                end = System.currentTimeMillis();    // record the end time
                T1[i] = end - start;                 // T1 = standard method
                start = System.currentTimeMillis();  // record the start time
                c = strassen_multiply(a, b);         // run strassen_multiply()
                end = System.currentTimeMillis();    // record the end time
                T2[i] = end - start;                 // T2 = adaptive strassen
            }
            // find the minimum possible index of the crossover point
            for (int i = 0; i < testSize - 1; i++) {
                // multiply by the proportional coefficient to eliminate the effect of the special case
                if (T1[i] > (int) T2[i] * 1.1) {
                    minIndex = startPoint + i;
                    break;
                }
            }
            // find the maximum possible index of the crossover point
            for (int i = testSize - 1; i > -1; i--) {
                // multiply by the proportional coefficient to eliminate the effect of the special case
                if ((int) T1[i] * 1.1 < T2[i]) {
                    maxIndex = startPoint + i;
                    break;
                }
            }
            // calculate the average value of two indexes as the crossover_point
            crossover_point = (int) (minIndex + maxIndex) / 2;
            // store the result in a txt
            System.out.println("The crossover_point : " + crossover_point);
            try {
                PrintStream txt = new PrintStream("./src/Project/crossover_point.txt");
                PrintStream out = System.out;
                System.setOut(txt);
                System.out.println(crossover_point); // store the value into txt
                System.setOut(out);
            } catch (FileNotFoundException er) {
                er.printStackTrace();
            }
        }
    }

    /**
     * Uses the standard approach to multiply 2 matrices a and b, return their product.
     *
     * <p></p>Runs in theta(n^3)
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return the product of two matrices
     */
    public static Matrix square_matrix_multiply(Matrix a, Matrix b) {
        int n = a.getSize();
        Matrix C = new Matrix(n); // let C be a new n*n matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C.setElement(i,j,0); // initialize the value of C[i][j]
                for (int k = 0; k < n; k++) {
                    C.setElement(i,j,C.getElement(i,j)+a.getElement(i,k) * b.getElement(k,j)); // compute the final value of C[i][j]
                }
            }
        }
        return C;
    }

    /**
     * Uses Strassen’s algorithm to multiply 2 matrices a and b, return their product.
     *
     * <p></p>Runs in theta(n^lg7)
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return the product of two matrices
     */
    public static Matrix strassen_multiply(Matrix a, Matrix b) {
        int n = a.getSize();
        int cur = n;
        double k = 0;
        boolean isMakeEven = false;
        if (a.getSize() % 2 == 1) {
            a = makeEven(a);
            b = makeEven(b);
            isMakeEven = true;
        }
        Matrix C = strassenMultiplyMatrix(a, b);
        if (isMakeEven) {
            C = makeOdd(C);
        }
        return C;
    }

    /**
     * Uses standard matrix multiplication for matrices that are less than the
     * (previously stored or default) crossover point,
     * and uses nice Strassen’s method for matrices that are greater than the crossover point.
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return the product of two matrices
     */
    public static Matrix adaptive_multiply(Matrix a,Matrix b) {
        int n = a.getSize();
        if (n < crossover_point) {
            return square_matrix_multiply(a, b);
        } else return strassen_multiply(a, b);
    }

    /**
     * Generates a random n*n matrix with values in the range [-1, 1].
     *
     * @param n the size
     * @return the random matrix
     */
    public static Matrix GenerateRandomMatrix(int n) {
        Matrix matrix = new Matrix(n); // generate a n*n matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix.setElement(i,j,-1 + 2 * Math.random()); // randomly generate value in the range [-1,1]
            }
        }
        return matrix;
    }

    /**
     * Prints the matrix in format.
     *
     * @param matrix1 the matrix to print
     */
    public static void PrintMatrix(Matrix matrix1) {
        int m = (int)Math.sqrt(matrix1.getSize());
        int n = (int)Math.sqrt(matrix1.getSize());
        for (double num: matrix1.matrix) {
                System.out.printf("% 10f", num);
        }
        System.out.println();
    }

    /**
     * Adds two matrices according to matrix addition.
     *
     * <p></p>This is used in Strassen MM.
     *
     * @param A the first matrix
     * @param B the second matrix
     * @param n the size
     * @return the sum of two matrices
     */
    private static Matrix addMatrix( Matrix A,  Matrix B, int n) {
        Matrix C = new Matrix(n);
        for (int r = 0; r < n; r++)
            for (int c = 0; c < n; c++)
                C.setElement(r,c,A.getElement(r,c) + B.getElement(r,c));
        return C;
    }

    /**
     * Subtracts two matrices according to matrix addition.
     *
     * <p></p>This is used in Strassen MM.
     *
     * @param A the first matrix
     * @param B the second matrix
     * @param n the size
     * @return the difference of two matrices
     */
    private static Matrix minusMatrix( Matrix A,  Matrix B, int n) {
        Matrix C = new Matrix(n);
        for (int r = 0; r < n; r++)
            for (int c = 0; c < n; c++)
                C.setElement(r,c,A.getElement(r,c) - B.getElement(r,c));
        return C;
    }

    /**
     * Is used to test whether our nice strassen MM is right by comparing to standard MM.
     *
     */
    public static void isMatrixEqual() {
        int n = 46;
        Matrix a = GenerateRandomMatrix(n);
        Matrix b = GenerateRandomMatrix(n);
        Matrix c = square_matrix_multiply(a, b);
        Matrix d = strassen_multiply(a, b);
        // compare whether the outputs of two methods are the same
        PrintMatrix(c);
        PrintMatrix(d);
    }

    /**
     * Is the key step of nice strassen, can be recursively used in strassen_multiple method.
     *
     * <p></p>When size of matrix is no more than 32, use brutal MM to handle.
     *
     * @param A the first matrix
     * @param B the second matrix
     * @return the product of two matrices
     */
    public static Matrix strassenMultiplyMatrix(Matrix A, Matrix B) {
        boolean isMakeEven = false;
        int n = A.getSize();
        if (n % 2 == 1 && n != 1) {
            A = makeEven(A);
            B = makeEven(B);
            isMakeEven = true;
            n = A.getSize();
        }
        Matrix C = new Matrix(n);
        // Initialize the matrix:
        for (int rowIndex = 0; rowIndex < n; rowIndex++)
            for (int colIndex = 0; colIndex < n; colIndex++)
                C.setElement(rowIndex, colIndex, 0.0);
        if (n <= 32) // the recursion point
            C = square_matrix_multiply(A, B);
        else {
            Matrix A1 = new Matrix(n / 2);
            Matrix A2 = new Matrix(n / 2);
            Matrix A3 = new Matrix(n / 2);
            Matrix A4 = new Matrix(n / 2);
            Matrix B1 = new Matrix(n / 2);
            Matrix B2 = new Matrix(n / 2);
            Matrix B3 = new Matrix(n / 2);
            Matrix B4 = new Matrix(n / 2);
            // Slice matrices into 2 * 2 parts:
            for (int r = 0; r < n / 2; r++) {
                for (int c = 0; c < n / 2; c++) {
                    A1.setElement(r, c, A.getElement(r, c));
                    A2.setElement(r, c, A.getElement(r, n / 2 + c));
                    A3.setElement(r, c, A.getElement(n / 2 + r, c));
                    A4.setElement(r, c, A.getElement(n / 2 + r, n / 2 + c));
                    B1.setElement(r, c, B.getElement(r, c));
                    B2.setElement(r, c, B.getElement(r, n / 2 + c));
                    B3.setElement(r, c, B.getElement(n / 2 + r, c));
                    B4.setElement(r, c, B.getElement(n / 2 + r, n / 2 + c));
                }
            }
            // the size is halved
            n = n / 2;
            // calculate S matrices based on pseudocode
            Matrix S1 = minusMatrix(B2, B4, n);
            Matrix S2 = addMatrix(A1, A2, n);
            Matrix S3 = addMatrix(A3, A4, n);
            Matrix S4 = minusMatrix(B3, B1, n);
            Matrix S5 = addMatrix(A1, A4, n);
            Matrix S6 = addMatrix(B1, B4, n);
            Matrix S7 = minusMatrix(A2, A4, n);
            Matrix S8 = addMatrix(B3, B4, n);
            Matrix S9 = minusMatrix(A1, A3, n);
            Matrix S10 = addMatrix(B1, B2, n);
            // calculate P matrices based on pseudocode
            Matrix P1 = strassenMultiplyMatrix(A1, S1);
            Matrix P2 = strassenMultiplyMatrix(S2, B4);
            Matrix P3 = strassenMultiplyMatrix(S3, B1);
            Matrix P4 = strassenMultiplyMatrix(A4, S4);
            Matrix P5 = strassenMultiplyMatrix(S5, S6);
            Matrix P6 = strassenMultiplyMatrix(S7, S8);
            Matrix P7 = strassenMultiplyMatrix(S9, S10);
            // calculate C matrices based on pseudocode
            Matrix C1 = addMatrix(minusMatrix(addMatrix(P5, P4, n), P2, n), P6, n);
            Matrix C2 = addMatrix(P1, P2, n);
            Matrix C3 = addMatrix(P3, P4, n);
            Matrix C4 = minusMatrix(minusMatrix(addMatrix(P5, P1, n), P3, n), P7, n);
            // the size is doubled
            n *= 2;
            // combine 4 matrices into 1 matrix
            for (int r = 0; r < n / 2; r++) {
                for (int c = 0; c < n / 2; c++) {
                    C.setElement(r, c, C1.getElement(r, c));
                    C.setElement(r, n / 2 + c, C2.getElement(r, c));
                    C.setElement(n / 2 + r, c, C3.getElement(r, c));
                    C.setElement(n / 2 + r, n / 2 + c, C4.getElement(r, c));
                }
            }
            // if we make an odd matrix even, then change it back to be odd
            if (isMakeEven) {
                C = makeOdd(C);
            }
        }
        return C;
    }

    /**
     * Deletes one column and one row of 0s when the input size of square matrix is even
     * to make it the origin matrix.
     *
     * <p></p>Used in nice strassen MM.
     *
     * @param C matrix with even size
     * @return the origin matrix
     */
    public static Matrix makeOdd(Matrix C) {
        int size = C.getSize() - 1; // delete one column and one row
        Matrix newC = new Matrix(size);
        for (int r = 0; r < newC.getSize(); r++) {
            for (int c = 0; c < newC.getSize(); c++) {
                newC.setElement(r,c,C.getElement(r,c));
            }
        }
        C = newC;
        return C;
    }

    /**
     * Adds one column and one row of 0s when the input size of square matrix is odd
     * to make it can be divided
     *
     * <p></p>Used in nice strassen MM.
     *
     * @param A matrix with odd size
     * @return the adjusted matrix which can be applied to strassen MM
     */
    public static Matrix makeEven(Matrix A) {
        int size = A.getSize() + 1; // add one column and one row
        Matrix newA = new Matrix(size);
        for (int r = 0; r < A.getSize(); r++) {
            for (int c = 0; c < A.getSize(); c++) {
                newA.setElement(r,c,A.getElement(r,c));
            }
        }
        A = newA;
        return A;
    }

    public static void main(String[] args) {
        calibrate_crossover_point();
    }
}
