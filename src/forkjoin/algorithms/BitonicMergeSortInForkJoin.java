package forkjoin.algorithms;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class BitonicMergeSortInForkJoin extends RecursiveAction {

    private final int[] bitMergArray;
    private final int lowerIndex;
    private final int lenCount;
    private final boolean asc;

    public BitonicMergeSortInForkJoin(int[] bitMergArray, int lowerIndex, int lenCount, boolean asc) {
        this.bitMergArray = bitMergArray;
        this.lowerIndex = lowerIndex;
        this.lenCount = lenCount;
        this.asc = asc;
    }

    @Override
    protected void compute() {
        if (lenCount <= 1) return;

        int midIndx = lenCount / 2;

        invokeAll(
                new BitonicMergeSortInForkJoin(bitMergArray, lowerIndex, midIndx, true),
                new BitonicMergeSortInForkJoin(bitMergArray, lowerIndex + midIndx, lenCount - midIndx, false)
        );

        bitonicMerge(bitMergArray, lowerIndex, lenCount, asc);
    }

    private void bitonicMerge(int[] array, int low, int lenCount, boolean asc) {
        if (lenCount <= 1) return;

        int midIndx = lenCount / 2;

        for (int i = low; i < low + midIndx; i++) {
            if ((asc && array[i] > array[i + midIndx]) || (!asc && array[i] < array[i + midIndx])) {
                int temp = array[i];
                array[i] = array[i + midIndx];
                array[i + midIndx] = temp;
            }
        }

        bitonicMerge(array, low, midIndx, asc);
        bitonicMerge(array, low + midIndx, lenCount - midIndx, asc);
    }

    public static void main(String[] args) {
        int[] bitMergeSort = UtilityMethods.randomlyGeneratedArray(16384);

        ForkJoinPool pool = new ForkJoinPool();
        long start = System.nanoTime();
        pool.invoke(new BitonicMergeSortInForkJoin(bitMergeSort, 0, bitMergeSort.length, true));
        long end = System.nanoTime();

        System.out.println("Bitonic Sort Time: " + (end - start) / 1e6 + " ms and size of input " + bitMergeSort.length);
        //System.out.println("Is array sorted? " + UtilityMethods.isArraySrted(bitMergeSort));

        pool.shutdown();
    }
}
