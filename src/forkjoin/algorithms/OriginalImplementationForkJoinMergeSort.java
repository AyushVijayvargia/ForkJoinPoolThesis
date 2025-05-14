package forkjoin.algorithms;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class OriginalImplementationForkJoinMergeSort extends RecursiveAction{

        private final int[] mergeArray;
        private final int lowerIndex;
        private final int upperIndex;
        public OriginalImplementationForkJoinMergeSort(int[] mergeArray, int lowerIndex, int end) {
            this.mergeArray = mergeArray;
            this.lowerIndex = lowerIndex;
            this.upperIndex = end;
        }

        @Override
        protected void compute() {
            if (upperIndex - lowerIndex > UtilityMethods.THRESHOLD_SIZE) {
                var middleIndex = (lowerIndex + upperIndex) / 2;
                invokeAll(
                        new OriginalImplementationForkJoinMergeSort(mergeArray, middleIndex, upperIndex),
                        new OriginalImplementationForkJoinMergeSort(mergeArray, lowerIndex, middleIndex)
                );
                merge(mergeArray, lowerIndex, middleIndex, upperIndex);
            } else {
                Arrays.sort(mergeArray, lowerIndex, upperIndex);//this is inbuilt method
            }
        }

        private void merge(int[] array, int lowerIndex, int middleIndex, int upperIndex) {
            int[] temporaryArray = UtilityMethods.copyArray(array, lowerIndex, upperIndex);
            int i = 0, j = middleIndex - lowerIndex, k = lowerIndex;
            while (i < middleIndex - lowerIndex && j < upperIndex - lowerIndex) {
                if (temporaryArray[i] <= temporaryArray[j]) {
                    array[k++] = temporaryArray[i++];
                } else {
                    array[k++] = temporaryArray[j++];
                }
            }
            while (i < middleIndex - lowerIndex) {
                array[k++] = temporaryArray[i++];
            }
            while (j < upperIndex - lowerIndex) {
                array[k++] = temporaryArray[j++];
            }
        }

    public static void main(String[] args) {
        int[] mergeArray = UtilityMethods.randomlyGeneratedArray(10_000);//it will create randomly generated array of size 10,000

        ForkJoinPool pool = new ForkJoinPool();

        long start = System.nanoTime();//time in nanoseconds to get decimal places of milliseconds
        pool.invoke(new OriginalImplementationForkJoinMergeSort(mergeArray, 0, mergeArray.length));
        long end = System.nanoTime();
        System.out.println("Parallel Merge Sort Time: " + (end - start) / 1e6 + " milliseconds for input size: "+mergeArray.length);

        //System.out.print(Arrays.toString(mergeArray)); //to check if it is sorted or not
        pool.shutdown();

        System.out.println("");
        System.out.println(UtilityMethods.isArraySrted(mergeArray));
    }
}
