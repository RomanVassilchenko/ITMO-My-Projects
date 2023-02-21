package lab1;

import lab1.Utils.PrintUtil;
import lab1.models.Matrix;
import lab1.models.ResultSet;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class SimpleIterationMethod {
    private static final PrintUtil pr = new PrintUtil();
    private static double[][] val;
    public static void execute(Matrix matrix, double eps){
        pr.printMatrix(matrix);
        if (checkDiagonal(matrix.getMatrix(),matrix.getSize())){
            ResultSet rs = method_of_simple_iterations_SLAU(matrix, eps);
            pr.println(rs.getTable());
            pr.printVector("Решение системы: ", rs.getResult());
            pr.printVector("Вектор невязки: ", rs.getResiduals());
            return;
        }
        permuteMatrixHelper(matrix,0);
        if (val!= null){
            Matrix matrix1 = new Matrix(val);
            pr.println("Матрица после перестановки строк");
            pr.printMatrix(matrix1);
            ResultSet rs = method_of_simple_iterations_SLAU(matrix1, eps);
            pr.println(rs.getTable());
            pr.printVector("Решение системы: ", rs.getResult());
            pr.printVector("Вектор невязки: ", rs.getResiduals());
        } else {
            pr.notDiagonalAll();
        }
    }

    public static boolean checkDiagonal(double[][] matrix, int size) {
        int i, j, k = 1;
        double sum;
        for (i = 0; i < size;i++) {
            sum = 0;
            for (j = 0; j < size;j++) {
                sum+= abs(matrix[i][j]);
            }
            sum -= abs(matrix[i][i]);
            if (sum >= abs(matrix[i][i])) {
                k = 0;
            }
        }
        return (k == 1);
    }

    private static void permuteMatrixHelper(Matrix matrix, int index) {
        if(index >= matrix.getMatrix().length - 1){
            if (checkDiagonal(matrix.getMatrix(), matrix.getSize())){
                val = new double[matrix.getSize()][matrix.getSize()+1];
                for (int i = 0; i < matrix.getSize();i++){
                    for (int j = 0; j < matrix.getSize()+1;j++){
                        val[i][j] = matrix.getMatrix()[i][j];
                    }
                }
            }
        } else {
            for (int i = index; i < matrix.getMatrix().length; i++) {
                double[] t = matrix.getMatrix()[index];
                matrix.getMatrix()[index] = matrix.getMatrix()[i];
                matrix.getMatrix()[i] = t;

                permuteMatrixHelper(matrix, index + 1);

                t = matrix.getMatrix()[index];
                matrix.getMatrix()[index] = matrix.getMatrix()[i];
                matrix.getMatrix()[i] = t;
            }
            pr.printMatrix(matrix);
        }
    }

    private static ResultSet method_of_simple_iterations_SLAU(Matrix matrix, double eps) {
        ResultSet rs = new ResultSet();
        double[] x= new double[matrix.getSize()];
        double norma, sum, t;
        do
        {
            ArrayList<Double> esps = new ArrayList<>();
            norma = 0;

            for(int i = 0; i < matrix.getSize(); i++)
            {
                t = x[i];
                sum = 0;

                for(int j = 0; j < matrix.getSize(); j++)
                {
                    if(j != i)
                        sum += matrix.getMatrix()[i][j] * x[j];
                }
                x[i] = (matrix.getVector()[i] - sum) / matrix.getMatrix()[i][i];
                esps.add(abs(x[i] - t));
                if (abs(x[i] - t) > norma)
                    norma = abs(x[i] - t);
            }
            rs.addIter(x);
            rs.addE(esps);
        }
        while(norma > eps);


        rs.setResult(x);

        //Проверка
        ArrayList<Double> residuals = new ArrayList<>();
        for(int i = 0; i < matrix.getSize(); i++)
        {
            double S=0;
            for(int j = 0; j < matrix.getSize(); j++)
            {
                S += matrix.getMatrix()[i][j] * x[j] ;
            }
            residuals.add(S - matrix.getVector()[i]);
        }
        rs.setResiduals(residuals);
        return rs;
    }
}