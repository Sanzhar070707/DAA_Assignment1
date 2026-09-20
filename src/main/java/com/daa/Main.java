package com.daa;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Main {

    private static final Random random = new Random();

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 50000, 100000};
        int trials = 5;
        String[] inputTypes = {"Random", "Sorted", "Reversed"};

        System.out.println("Algorithm    | Size     | InputType  | Comparisons  | MaxDepth   | Time (ms)");
        System.out.println("----------------------------------------------------------------------------------");

        try (PrintWriter writer = new PrintWriter(new FileWriter("results.csv"))) {
            writer.println("Algorithm,Size,InputType,Comparisons,MaxDepth,TimeNs");

            // 1. MergeSort
            for (int size : sizes) {
                for (String inputType : inputTypes) {
                    long totalComp = 0, totalDepth = 0, totalTime = 0;

                    for (int t = 0; t < trials; t++) {
                        int[] base = generateArray(size, inputType);
                        Metrics m = new Metrics();
                        MergeSort.sort(base, m);

                        totalComp += m.getComparisons();
                        totalDepth += m.getMaxDepth();
                        totalTime += m.getElapsedTimeNs();
                    }

                    recordResult(writer, "MergeSort", size, inputType,
                            totalComp / trials, totalDepth / trials, totalTime / trials);
                }
            }

            System.out.println("----------------------------------------------------------------------------------");

            // 2. QuickSort
            for (int size : sizes) {
                for (String inputType : inputTypes) {
                    long totalComp = 0, totalDepth = 0, totalTime = 0;

                    for (int t = 0; t < trials; t++) {
                        int[] base = generateArray(size, inputType);
                        Metrics m = new Metrics();
                        QuickSort.sort(base, m);

                        totalComp += m.getComparisons();
                        totalDepth += m.getMaxDepth();
                        totalTime += m.getElapsedTimeNs();
                    }

                    recordResult(writer, "QuickSort", size, inputType,
                            totalComp / trials, totalDepth / trials, totalTime / trials);
                }
            }

            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("Эксперименты завершены! Данные сохранены в results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void recordResult(PrintWriter writer, String algo, int size, String type, long comp, long depth, long time) {
        System.out.printf("%-12s | %-8d | %-10s | %-12d | %-10d | %-12.2f ms%n",
                algo, size, type, comp, depth, time / 1_000_000.0);

        writer.printf("%s,%d,%s,%d,%d,%d%n", algo, size, type, comp, depth, time);
    }

    private static int[] generateArray(int size, String type) {
        int[] arr = new int[size];
        switch (type) {
            case "Random":
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(100000);
                break;
            case "Sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "Reversed":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
        }
        return arr;
    }
}