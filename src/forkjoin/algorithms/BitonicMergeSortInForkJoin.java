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

        bitonicMergSortParalel(bitMergArray, lowerIndex, lenCount, asc);
    }

    private void bitonicMergSortParalel(int[] bitMergArray, int low, int lenCount, boolean asc) {
        if (lenCount <= 1) return;

        int midIndx = lenCount / 2;

        for (int i = low; i < low + midIndx; i++) {
            if ((asc && bitMergArray[i] > bitMergArray[i + midIndx]) || (!asc && bitMergArray[i] < bitMergArray[i + midIndx])) {
                UtilityMethods.swapping(bitMergArray,i,i+midIndx);
            }
        }

        bitonicMergSortParalel(bitMergArray, low, midIndx, asc);
        bitonicMergSortParalel(bitMergArray, low + midIndx, lenCount - midIndx, asc);
    }

    public static void main(String[] args) {
        int[] bitMergeSort = UtilityMethods.randomlyGeneratedArray(16384);//2^14 : only power of 2 used for this algo

        ForkJoinPool pool = new ForkJoinPool();
        long start = System.nanoTime();
        pool.invoke(new BitonicMergeSortInForkJoin(bitMergeSort, 0, bitMergeSort.length, true));
        long end = System.nanoTime();

        System.out.println("Bitonic Sort Time: " + (end - start) / 1e6 + " ms and size of input " + bitMergeSort.length);
        //System.out.print(Arrays.toString(mergeArray)); //to check if it is sorted or not

        pool.shutdown();

        System.out.println("Is array sorted? " + UtilityMethods.isArraySrted(bitMergeSort));
    }
}
