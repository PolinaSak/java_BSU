package com.mycomp;

import java.util.Random;
import java.util.Scanner;

public class MatrixUtils {

    public static int[][] inputOrGenerateMatrix(Scanner scan) {
        System.out.print("Введите размеры матрицы через пробел (кол-во строк и столбцов): ");
        int numRows = scan.nextInt();
        int numCol = scan.nextInt();
        int[][] matrix = new int[numRows][numCol];

        System.out.println("Автоматически сгенерировать массив (1) или хотите ввести самостоятельно (2)?");
        int choise = scan.nextInt();

        if (choise == 1) {
            Random random = new Random();
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCol; j++) {
                    matrix[i][j] = random.nextInt(21) - 10;
                }
            }
        } else if (choise == 2) {
            System.out.println("Введите массив построчно: ");
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCol; j++) {
                    matrix[i][j] = scan.nextInt();
                }
            }
        } else {
            System.out.println("Error, enter correct number!");
            return null;
        }

        return matrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }
}
