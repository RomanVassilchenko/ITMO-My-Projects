package lab1.models;
import models.ICommand;
import java.util.Scanner;
import lab1.MathModuleLab1;

public class RandomCommand implements ICommand {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getMessage() {
        return "Генерация рандомной матрицы";
    }

    @Override
    public void execute() {
        System.out.println("Генерация матрицы");
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
        MathModuleLab1.execute(createRandomMatrix(size), eps);
    }

    public Matrix createRandomMatrix(int size) {
        try{
            if (size > 20 || size <= 0) {
                throw new Exception();
            }
            double[][] matrix = new double[size][size + 1];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length + 1; j++) {
                    matrix[i][j] = Math.random() * 50 - 25;
                }
            }
            return new Matrix(matrix);
        } catch (Exception e) {
            System.out.println("Введена неверная размерность");
        }
        return null;
    }
}
