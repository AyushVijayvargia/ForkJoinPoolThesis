# ForkJoinPoolThesis
This is the optimization done over fork join pool framework of Java.
The key class used is ForkJoinPool of Concurrent package of Java 1.7.
Optimization done in 3 phases:
1) Using different algorithms to find the most efficient solution.
2) After best algorithm is found, manually tweaking the work stealing strategies and finding the most efficient.
3) Trying the above obtained solution with virtual thread(Java 19) instead of platform thread.
4) Benchmarking the results with currently existing logic of fork join pool.
