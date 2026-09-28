// 20. Перестроить матрицу, переставляя в ней строки так, чтобы сумма
// элементов в строках полученной матрицы возрастала.
package com.mycomp;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Task2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
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
            System.out.print("Error, enter correct number!");
            scan.close();
            return;
        }

        System.out.println("\nМатрица: ");
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCol; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }

        int[][] result = sortRows(matrix);
        System.out.println("Результат: ");
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.printf("%4d", result[i][j]);
            }
            System.out.println();
        }
        scan.close();
    }

    static class RowWithSum {
        int[] row;
        int sum;

        RowWithSum(int[] row, int sum) {
            this.row = row;
            this.sum = sum;
        }
    }

    public static int[][] sortRows(int[][] matrix) {
        ArrayList<RowWithSum> list = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            int sum = getRowSum(matrix[i]);
            list.add(new RowWithSum(matrix[i], sum));
        }
        
        list.sort((a, b) -> Integer.compare(a.sum, b.sum));

        int[][] result = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).row;
        }
        return result;
    }

    public static int getRowSum(int[] mass) {
        int sum = 0;
        for (int j = 0; j < mass.length; j++) {
            sum += mass[j];
        }
        return sum;
    }
}
