package com.daa;

import java.util.Random;

public class QuickSelect {

    private static final Random random = new Random();

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid input array or index k");
        }
        metrics.startTimer();
        int[] copy = a.clone();
        int result = select(copy, 0, copy.length - 1, k, metrics);
        metrics.stopTimer();
        return result;
    }

    private static int select(int[] a, int low, int high, int k, Metrics metrics) {
        while (low <= high) {
            if (low == high) {
                return a[low];
            }

            metrics.enterRecursion();

            int pivotIndex = low + random.nextInt(high - low + 1);
            swap(a, low, pivotIndex);

            int[] pivotRange = QuickSort.partition3Way(a, low, high, metrics);
            int lt = pivotRange[0];
            int gt = pivotRange[1];

            metrics.exitRecursion();

            if (k >= lt && k <= gt) {
                return a[k];
            } else if (k < lt) {
                high = lt - 1;
            } else {
                low = gt + 1;
            }
        }
        return a[k];
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}