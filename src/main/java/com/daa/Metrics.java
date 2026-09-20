package com.daa;

public class Metrics {
    private long comparisons;
    private int maxDepth;
    private int currentDepth;
    private long startTime;
    private long elapsedTimeNs;
    public Metrics() {
        reset();
    }

    public void startTimer() {
        this.startTime = System.nanoTime();
    }

    public void stopTimer() {
        this.elapsedTimeNs = System.nanoTime() - this.startTime;
    }

    public void incrementComparisons() {
        this.comparisons++;
    }

    public void enterRecursion() {
        this.currentDepth++;
        if (this.currentDepth > this.maxDepth) {
            this.maxDepth = this.currentDepth;
        }
    }

    public void exitRecursion() {
        this.currentDepth--;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public long getElapsedTimeNs() {
        return elapsedTimeNs;
    }

    public double getElapsedTimeMs() {
        return elapsedTimeNs / 1_000_000.0;
    }

    public void reset() {
        this.comparisons = 0;
        this.maxDepth = 0;
        this.currentDepth = 0;
        this.startTime = 0;
        this.elapsedTimeNs = 0;
    }
}