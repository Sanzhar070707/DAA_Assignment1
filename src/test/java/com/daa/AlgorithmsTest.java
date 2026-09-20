package com.daa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmsTest {

    private Metrics metrics;
    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
    }

    @Test
    void testMergeSortCorrectnessOnRandomArrays() {
        for (int t = 0; t < 100; t++) {
            int n = random.nextInt(500) + 1;
            int[] actual = random.ints(n, -10000, 10000).toArray();
            int[] expected = actual.clone();

            Arrays.sort(expected);
            MergeSort.sort(actual, metrics);

            assertArrayEquals(expected, actual, "MergeSort failed on random array");
        }
    }

    @Test
    void testQuickSortCorrectnessOnRandomArrays() {
        for (int t = 0; t < 100; t++) {
            int n = random.nextInt(500) + 1;
            int[] actual = random.ints(n, -10000, 10000).toArray();
            int[] expected = actual.clone();

            Arrays.sort(expected);
            QuickSort.sort(actual, metrics);

            assertArrayEquals(expected, actual, "QuickSort failed on random array");
        }
    }

    @Test
    void testEdgeCases() {
        // Пустой массив
        int[] empty = new int[0];
        MergeSort.sort(empty, metrics);
        QuickSort.sort(empty, metrics);
        assertArrayEquals(new int[0], empty);

        // Один элемент
        int[] single = new int[]{42};
        MergeSort.sort(single, metrics);
        QuickSort.sort(single, metrics);
        assertArrayEquals(new int[]{42}, single);

        // Все элементы одинаковые
        int[] allEqual = new int[]{7, 7, 7, 7, 7, 7, 7};
        int[] expectedEqual = allEqual.clone();
        MergeSort.sort(allEqual, metrics);
        assertArrayEquals(expectedEqual, allEqual);

        allEqual = new int[]{7, 7, 7, 7, 7, 7, 7};
        QuickSort.sort(allEqual, metrics);
        assertArrayEquals(expectedEqual, allEqual);

        // Отсортированный массив
        int[] sorted = new int[]{1, 2, 3, 4, 5, 6, 7};
        int[] expectedSorted = sorted.clone();
        MergeSort.sort(sorted, metrics);
        assertArrayEquals(expectedSorted, sorted);

        sorted = new int[]{1, 2, 3, 4, 5, 6, 7};
        QuickSort.sort(sorted, metrics);
        assertArrayEquals(expectedSorted, sorted);
    }

    @Test
    void testQuickSortRecursionDepthOnSortedArray() {
        int n = 100_000;
        int[] sortedArray = new int[n];
        for (int i = 0; i < n; i++) {
            sortedArray[i] = i;
        }

        QuickSort.sort(sortedArray, metrics);

        // Глубина рекурсии должна быть <= 2 * log2(n)
        double limit = 2 * (Math.log(n) / Math.log(2));
        assertTrue(metrics.getMaxDepth() <= limit,
                "QuickSort recursion depth (" + metrics.getMaxDepth() + ") exceeded limit (" + limit + ")");
    }

    @Test
    void testQuickSelectCorrectnessOnRandomArrays() {
        for (int t = 0; t < 100; t++) {
            int n = random.nextInt(500) + 1;
            int[] array = random.ints(n, -10000, 10000).toArray();
            int k = random.nextInt(n);

            int[] sorted = array.clone();
            Arrays.sort(sorted);

            int actual = QuickSelect.select(array, k, metrics);
            assertEquals(sorted[k], actual, "QuickSelect failed for k=" + k);
        }
    }

    @Test
    void testQuickSelectInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[0], 0, metrics));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, -1, metrics));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, 3, metrics));
    }
}