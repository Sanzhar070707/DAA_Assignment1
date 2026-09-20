package com.daa;

public class MergeSort {

    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }
        metrics.startTimer();
        int[] aux = new int[a.length]; // Reusable buffer (1 allocation)
        sort(a, aux, 0, a.length - 1, metrics);
        metrics.stopTimer();
    }

    private static void sort(int[] a, int[] aux, int low, int high, Metrics metrics) {
        metrics.enterRecursion();

        if (high - low <= CUTOFF) {
            insertionSort(a, low, high, metrics);
            metrics.exitRecursion();
            return;
        }

        int mid = low + (high - low) / 2;
        sort(a, aux, low, mid, metrics);
        sort(a, aux, mid + 1, high, metrics);

        merge(a, aux, low, mid, high, metrics);

        metrics.exitRecursion();
    }

    private static void merge(int[] a, int[] aux, int low, int mid, int high, Metrics metrics) {
        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else {
                metrics.incrementComparisons();
                if (aux[j] < aux[i]) {
                    a[k] = aux[j++];
                } else {
                    a[k] = aux[i++];
                }
            }
        }
    }

    private static void insertionSort(int[] a, int low, int high, Metrics metrics) {
        for (int i = low + 1; i <= high; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= low) {
                metrics.incrementComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}