package com.mycomp;
// 6. Для каждой строки матрицы найти сумму элементов матрицы,

// расположенных между первым и вторым положительными элементами
// каждой строки. Отсортировать строки матрицы по этой сумме. Строки, в
// которых все элементы отрицательные — удалить. Строки у которых только
// один положительный элемент — удалить.

import java.util.Scanner;
import java.util.ArrayList;

public class Task1 {
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

    public static Integer sumBetweenTwoPositive(int[] mass) {
        int firstIndex = -1;
        int secondIndex = -1;
        int sum = 0;
        for (int j = 0; j < mass.length; j++) {
            if (mass[j] > 0) {
                if (firstIndex == -1) {
                    firstIndex = j;
                } else if (secondIndex == -1) {
                    secondIndex = j;
                    for (int i = firstIndex + 1; i < secondIndex; i++) {
                        sum += mass[i];
                    }
                    return sum;
                }
            }
        }
        return null;
    }

    public static int[][] sortRows(int[][] matrix) {
        ArrayList<RowWithSum> list = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            Integer sum = sumBetweenTwoPositive(matrix[i]);
            if (sum != null) {
                list.add(new RowWithSum(matrix[i], sum));
            }

        }
        list.sort((a, b) -> Integer.compare(a.sum, b.sum));

        int[][] result = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).row;
        }
        return result;
    }
}
