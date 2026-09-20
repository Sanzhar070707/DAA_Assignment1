# Assignment 1 Analysis Report

## 1. Experimental Setup
* **Environment:** OpenJDK 21/26, Linux Ubuntu
* **Data Sizes ($n$):** 100, 1000, 10 000, 50 000, 100 000
* **Input Types:** Random, Sorted, Reversed
* **Metrics Tracked:** Number of key comparisons, maximum recursion depth, execution time (nanoseconds)

---

## 2. Benchmark Data Summary
Below is a summary derived from the executed benchmark runs (`results.csv`):

| Algorithm | Size ($n$) | Input Type | Avg Comparisons | Max Depth | Avg Time (ms) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **MergeSort** | 100 000 | Random | ~1 639 803 | 14 | ~9.62 ms |
| **QuickSort** | 100 000 | Random | ~2 867 682 | 10 | ~11.15 ms |
| **MergeSort** | 100 000 | Sorted | ~744 016 | 14 | ~22.03 ms |
| **QuickSort** | 100 000 | Sorted | ~2 988 475 | 10 | ~10.73 ms |
| **MergeSort** | 100 000 | Reversed | ~1 208 816 | 14 | ~14.31 ms |
| **QuickSort** | 100 000 | Reversed | ~2 990 556 | 10 | ~8.58 ms |

---

## 3. Key Findings & Discussion

### A. MergeSort Efficiency
* **Comparisons:** MergeSort achieves around $O(n \log_2 n)$ comparisons across all input arrangements.
* **Cutoff & Buffer Optimization:** Using an insertion sort cutoff ($k = 15$) for small sub-arrays significantly reduces recursion overhead. Allocating a single auxiliary buffer array upfront avoids $O(n \log n)$ object allocations, preventing frequent Garbage Collection pauses.

### B. QuickSort Recursion Guard & Depth Limits
* **Recursion Depth:** Thanks to recursing into the **smaller partition first** and converting the larger partition into a loop, the max recursion depth strictly stays bounded by $O(\log_2 n)$. On $n = 100\,000$, depth never exceeds **10** (well below the $2 \cdot \log_2 n \approx 33$ limit).
* **3-Way Partitioning:** Effectively eliminates duplicate-key performance degradation, preventing $O(n^2)$ worst cases on highly repetitive inputs.

### C. QuickSelect Performance
* **Theoretical vs Practical:** QuickSelect averages $O(n)$ time complexity by only recurring into the partition containing the target rank $k$.

---

## 4. Conclusion
* **MergeSort** provides stable $O(n \log n)$ performance and fewer total comparisons, making it ideal when comparison cost is high or stable sorting is required.
* **QuickSort** with randomized 3-way partitioning and bounded recursion depth achieves lower recursion overhead and space complexity ($O(\log n)$ stack space) while fully avoiding worst-case stack overflows.