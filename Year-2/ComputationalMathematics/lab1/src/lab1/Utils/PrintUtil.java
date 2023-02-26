package lab1.Utils;

import lab1.models.Matrix;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class PrintUtil {

    private final MathContext context = new MathContext(3, RoundingMode.HALF_UP);
    public PrintUtil() {
    }
    public void println(String message) {
        System.out.println(message);
    }
    public void print(String message) {
        System.out.print(message);
    }

    public void printVector(String message, ArrayList<Double> list){
        println(message);
        for(int i = 0; i < list.size(); i++){
            println("["+(i+1)+"] = "+String.format("%.15f",list.get(i)));
        }
    }

    public void printMatrix(Matrix matrix) {
        println("Матрица:");
        if (matrix != null) {
            for (int i = 0; i < matrix.getSize(); i++) {
                for (int j = 0; j < matrix.getSize() + 1; j++) {
                    print(String.valueOf(new BigDecimal(matrix.getMatrix()[i][j], context)));
                    print(" ");
                }
                println("");
            }
        }
    }

    public void notDiagonalAll() {
        println("Отсутствие диагонального преобладания");
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Printer";
    }
}
