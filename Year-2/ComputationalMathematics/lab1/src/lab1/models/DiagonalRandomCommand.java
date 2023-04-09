package lab1.models;
import models.ICommand;
import java.util.Scanner;
import lab1.SimpleIterationMethod;

public class DiagonalRandomCommand implements ICommand {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getMessage() { return "Генерация рандомной матрицы с диагональным преобладанием"; }

    @Override
    public void execute() {
        System.out.println("Генерация матрицы c диагональным преобладанием");
        int size;
        double eps;
        while (true) {
            try {
                System.out.println("Введите размер:");
                String buffer = scanner.nextLine();
                size = Integer.parseInt(buffer);
                System.out.println("Введите точность:");
                buffer = scanner.nextLine();
                eps = Double.parseDouble(buffer);
                break;
            }
            catch (Exception ignored){ }
        }
        Matrix matrix;
        do {
            matrix = createRandomMatrix(size);
        } while (!SimpleIterationMethod.checkDiagonal(matrix.getMatrix(), size));
        SimpleIterationMethod.execute(matrix, eps);
    }

    public Matrix createRandomMatrix(int size) {
        try{
            if (size > 20 || size <= 0) {
                throw new Exception();
            }
            double[][] matrix = new double[size][size + 1];
            int min = 1, max = 10;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length + 1; j++) {
                    if(i == j){
                        matrix[i][j] = Math.random() * ((max*size - min*size) + 1) + min*size;
                    }
                    else {
                        matrix[i][j] = Math.random() * ((max - min) + 1) + min;
                    }
                }
            }
            return new Matrix(matrix);
        } catch (Exception e) {
            System.out.println("Введена неверная размерность");
        }
        return null;
    }
}
