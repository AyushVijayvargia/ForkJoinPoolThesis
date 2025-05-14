package forkjoin.workstealing;

import forkjoin.algorithms.UtilityMethods;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class WorkStealHoarePartQuickSortInForkJoinVar1 extends RecursiveAction{

        private final int[] workStealHoarePartQuicksrtArrayVar1;
        private final int lowerIndex;
        private final int upperIndex;
        public WorkStealHoarePartQuickSortInForkJoinVar1(int[] workStealHoarePartQuicksrtArrayVar1, int lowerIndex, int end) {
            this.workStealHoarePartQuicksrtArrayVar1 = workStealHoarePartQuicksrtArrayVar1;
            this.lowerIndex = lowerIndex;
            this.upperIndex = end;
        }

        @Override
        protected void compute() {
            if (upperIndex - lowerIndex > UtilityMethods.THRESHOLD_SIZE) {
                  int pivotInd = UtilityMethods.hoarePart(workStealHoarePartQuicksrtArrayVar1, lowerIndex, upperIndex);

                  var subtask1 =  new WorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar1, lowerIndex, pivotInd );
                  var subtask2 =  new WorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar1, pivotInd + 1, upperIndex);
                  subtask2.fork();
                  subtask1.compute();
                  subtask2.join();
            } else {
                Arrays.sort(workStealHoarePartQuicksrtArrayVar1, lowerIndex, upperIndex+1);//this is inbuilt method
            }
        }


    public static void main(String[] args) {
        int[] workStealHoarePartQuicksrtArrayVar1 = UtilityMethods.randomlyGeneratedArray(10_000);//it will create randomly generated array of size 10,000

        ForkJoinPool pool = new ForkJoinPool();

        long start = System.nanoTime();//time in nanoseconds to get decimal places of milliseconds
        pool.invoke(new WorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar1, 0, workStealHoarePartQuicksrtArrayVar1.length-1));
        long end = System.nanoTime();
        System.out.println("Hoare Part Quick Sort Time with work Steal Variation 1: " + (end - start) / 1e6 + " milliseconds for input size: "+workStealHoarePartQuicksrtArrayVar1.length);

        //System.out.print(Arrays.toString(mergeArray)); //to check if it is sorted or not
        pool.shutdown();

        System.out.println("");
        System.out.println(UtilityMethods.isArraySrted(workStealHoarePartQuicksrtArrayVar1));
    }
}
