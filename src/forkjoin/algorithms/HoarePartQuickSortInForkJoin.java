package forkjoin.algorithms;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class HoarePartQuickSortInForkJoin extends RecursiveAction{

        private final int[] hoarePartQuicksrtArray;
        private final int lowerIndex;
        private final int upperIndex;
        public HoarePartQuickSortInForkJoin(int[] hoarePartQuicksrtArray, int lowerIndex, int end) {
            this.hoarePartQuicksrtArray = hoarePartQuicksrtArray;
            this.lowerIndex = lowerIndex;
            this.upperIndex = end;
        }

        @Override
        protected void compute() {
            if (upperIndex - lowerIndex > UtilityMethods.THRESHOLD_SIZE) {
                int pivotInd = UtilityMethods.hoarePart(hoarePartQuicksrtArray, lowerIndex, upperIndex);
                invokeAll(
                        new HoarePartQuickSortInForkJoin(hoarePartQuicksrtArray, lowerIndex, pivotInd ),
                        new HoarePartQuickSortInForkJoin(hoarePartQuicksrtArray, pivotInd + 1, upperIndex));
            } else {
                Arrays.sort(hoarePartQuicksrtArray, lowerIndex, upperIndex+1);//this is inbuilt method
            }
        }


    public static void main(String[] args) {
        int[] hoarePartQuicksrtArray = UtilityMethods.randomlyGeneratedArray(10_000);//it will create randomly generated array of size 10,000

        ForkJoinPool pool = new ForkJoinPool();

        long start = System.nanoTime();//time in nanoseconds to get decimal places of milliseconds
        pool.invoke(new HoarePartQuickSortInForkJoin(hoarePartQuicksrtArray, 0, hoarePartQuicksrtArray.length-1));
        long end = System.nanoTime();
        System.out.println("Hoare Part Quick Sort Time: " + (end - start) / 1e6 + " milliseconds for input size: "+hoarePartQuicksrtArray.length);

        //System.out.print(Arrays.toString(mergeArray)); //to check if it is sorted or not
        pool.shutdown();

        System.out.println("");
        System.out.println(UtilityMethods.isArraySrted(hoarePartQuicksrtArray));
    }
}
