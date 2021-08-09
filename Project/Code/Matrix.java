package Project;

public class Matrix {
    double[] matrix;
    int n;

    /**
     * Constructs a class Matrix
     * @param num the size of the Matrix
     */
    public Matrix(int num){ // construct matrix[]
        this.matrix = new double[num * num];
        this.n=num;
    }

    /**
     * Constructs a class Matrix
     * @param m a two-dimensional matrix
     */
    public Matrix(double[][] m) { // convert [][] into matrix[]
        this.n = m.length;
        this.matrix = new double[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.matrix[i * n + j] = m[i][j];
            }
        }
    }

    /**
     * Gets the size of the Matrix
     * @return the size
     */
    public int getSize(){
        return (int)Math.sqrt(matrix.length);
    }

    /**
     * Gets the value in the Matrix
     * @param row row index
     * @param col column index
     */
    public double getElement(int row, int col) {
        return this.matrix[row * n + col];
    }

    /**
     * Sets the value in the Matrix
     * @param row row index
     * @param col column index
     * @param value the value to set
     */
    public void setElement(int row, int col, double value) {
        this.matrix[row * n + col] = value;
    }
}
