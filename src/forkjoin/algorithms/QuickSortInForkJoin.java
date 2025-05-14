package forkjoin.algorithms;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class QuickSortInForkJoin extends RecursiveAction{

        private final int[] quicksrtArray;
        private final int lowerIndex;
        private final int upperIndex;
        public QuickSortInForkJoin(int[] quicksrtArray, int lowerIndex, int end) {
            this.quicksrtArray = quicksrtArray;
            this.lowerIndex = lowerIndex;
            this.upperIndex = end;
        }

        @Override
        protected void compute() {
            if (upperIndex - lowerIndex > UtilityMethods.THRESHOLD_SIZE) {
                int pivotInd = partition(quicksrtArray, lowerIndex, upperIndex);
                invokeAll(
                        new QuickSortInForkJoin(quicksrtArray, lowerIndex, pivotInd - 1),
                        new QuickSortInForkJoin(quicksrtArray, pivotInd + 1, upperIndex));
            } else {
                Arrays.sort(quicksrtArray, lowerIndex, upperIndex+1);//this is inbuilt method
            }
        }
            private int partition(int[] array, int lowerIndex, int upperIndex) {
                int pivotInd = array[upperIndex];
                int towardsRight = lowerIndex - 1;
                for (int j = lowerIndex; j < upperIndex; j++) {
                    if (array[j] <= pivotInd) {
                        towardsRight++;
                        UtilityMethods.swapping(array, towardsRight, j);
                    }
                }
                UtilityMethods.swapping(array, towardsRight + 1, upperIndex);
                return towardsRight + 1;
            }

    public static void main(String[] args) {
        int[] quicksrtArray = UtilityMethods.randomlyGeneratedArray(10_000);//it will create randomly generated array of size 10,000

        ForkJoinPool pool = new ForkJoinPool();

        long start = System.nanoTime();//time in nanoseconds to get decimal places of milliseconds
        pool.invoke(new QuickSortInForkJoin(quicksrtArray, 0, quicksrtArray.length-1));
        long end = System.nanoTime();
        System.out.println("Quick Sort Time: " + (end - start) / 1e6 + " milliseconds for input size: "+quicksrtArray.length);

        //System.out.print(Arrays.toString(mergeArray)); //to check if it is sorted or not
        pool.shutdown();

        System.out.println("");
        System.out.println(UtilityMethods.isArraySrted(quicksrtArray));
    }
}
