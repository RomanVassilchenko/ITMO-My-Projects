package lab1.models;

import java.util.Arrays;
import java.util.Objects;

public class Matrix {

    private final double [][] matrix;
    private final int size;

    public Matrix(double [][] matrix) {
        this.matrix = matrix;
        this.size = matrix.length;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return size;
    }

    public double[] getVector(){
        double [] vector = new double[size];
        for(int i = 0; i < this.size; i++){
            vector[i]=matrix[i][size];
        }
        return vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix1 = (Matrix) o;
        return size == matrix1.size &&
                Arrays.deepEquals(matrix, matrix1.matrix);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }

    @Override
    public String toString() {
        return "lab.Matrix{" +
                "matrix=" + Arrays.toString(matrix) +
                ", size=" + size +
                '}';
    }
}
