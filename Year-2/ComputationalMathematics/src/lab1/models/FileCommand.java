package lab1.models;

import lab1.SimpleIterationMethod;
import models.ICommand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileCommand implements ICommand {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getMessage() {
        return "Ввести матрицу с файла";
    }

    @Override
    public void execute() {
        System.out.println("Вводим матрицу с файла");
        System.out.println("Имя файла:");
        String path = scanner.nextLine();
        Matrix matrix = readMatrixFromFile(path);
        double eps;
        while (true) {
            try {
                System.out.println("Введите точность:");
                String buffer = scanner.nextLine();
                eps = Double.parseDouble(buffer);
                break;
            }
            catch (Exception ignored){ }
        }
        SimpleIterationMethod.execute(matrix, eps);
    }

    public Matrix readMatrixFromFile(String fileName) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            int size = Integer.parseInt(file.readLine().trim());
            double [][] matrix = new double[size][size + 1];
            for (int i = 0; i < size; i++) {
                String[] row = file.readLine().trim().split(" ");
                if (row.length > size + 1)
                    throw new ArrayIndexOutOfBoundsException();
                for (int j = 0; j < size + 1; j++) {
                    matrix[i][j] = Double.parseDouble(row[j].trim());
                }
            }
            return new Matrix(matrix);
        } catch (IOException e) {
            System.out.println("Ошибка ввода");
        }
        return null;
    }
}