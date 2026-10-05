// 20. Перестроить матрицу, переставляя в ней строки так, чтобы сумма
// элементов в строках полученной матрицы возрастала.
package com.mycomp;

import java.util.Scanner;
import java.util.ArrayList;

public class Task2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[][] matrix = MatrixUtils.inputOrGenerateMatrix(scan);

        System.out.println("\nМатрица: ");
        MatrixUtils.printMatrix(matrix);

        System.out.println("Результат: ");
        MatrixUtils.printMatrix(sortRows(matrix));
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
