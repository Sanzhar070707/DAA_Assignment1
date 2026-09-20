package com.daa;

import java.util.Random;

public class QuickSort {

    private static final Random random = new Random();

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }
        metrics.startTimer();
        sort(a, 0, a.length - 1, metrics);
        metrics.stopTimer();
    }

    private static void sort(int[] a, int low, int high, Metrics metrics) {
        while (low < high) {
            metrics.enterRecursion();

            // Случайный пивот
            int pivotIndex = low + random.nextInt(high - low + 1);
            swap(a, low, pivotIndex);

            // 3-way partitioning
            int[] pivotRange = partition3Way(a, low, high, metrics);
            int lt = pivotRange[0];
            int gt = pivotRange[1];

            // Smaller side first (ограничение глубины стека)
            if (lt - low < high - gt) {
                sort(a, low, lt - 1, metrics);
                metrics.exitRecursion();
                low = gt + 1;
            } else {
                sort(a, gt + 1, high, metrics);
                metrics.exitRecursion();
                high = lt - 1;
            }
        }
    }

    public static int[] partition3Way(int[] a, int low, int high, Metrics metrics) {
        int pivot = a[low];
        int lt = low;
        int gt = high;
        int i = low + 1;

        while (i <= gt) {
            metrics.incrementComparisons();
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else if (a[i] > pivot) {
                metrics.incrementComparisons();
                swap(a, i, gt--);
            } else {
                metrics.incrementComparisons();
                i++;
            }
        }

        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}