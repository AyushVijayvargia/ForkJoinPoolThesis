package forkjoin.workstealing;

import forkjoin.algorithms.UtilityMethods;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class WorkStealHoarePartQuickSortInForkJoinVar2 extends RecursiveAction{

        private final int[] workStealHoarePartQuicksrtArrayVar2;
        private final int lowerIndex;
        private final int upperIndex;
        public WorkStealHoarePartQuickSortInForkJoinVar2(int[] workStealHoarePartQuicksrtArrayVar2, int lowerIndex, int end) {
            this.workStealHoarePartQuicksrtArrayVar2 = workStealHoarePartQuicksrtArrayVar2;
            this.lowerIndex = lowerIndex;
            this.upperIndex = end;
        }

        @Override
        protected void compute() {
            if (upperIndex - lowerIndex > UtilityMethods.THRESHOLD_SIZE) {
                  int pivotInd = UtilityMethods.hoarePart(workStealHoarePartQuicksrtArrayVar2, lowerIndex, upperIndex);

                var subtask1 =  new WorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar2, lowerIndex, pivotInd );
                var subtask2 =  new WorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar2, pivotInd + 1, upperIndex);

                var subtask1Size =upperIndex-pivotInd;
                var subtask2Size =pivotInd- lowerIndex;

                  if(subtask2Size>subtask1Size)
                  {
                        subtask2.fork();
                        subtask1.compute();
                        subtask2.join();
                  }
                  else {
                      subtask1.fork();
                      subtask2.compute();
                      subtask1.join();
                  }

            } else {
                Arrays.sort(workStealHoarePartQuicksrtArrayVar2, lowerIndex, upperIndex+1);//this is inbuilt method
            }
        }


    public static void main(String[] args) {
        int[] workStealHoarePartQuicksrtArrayVar2 = UtilityMethods.randomlyGeneratedArray(10_000);//it will create randomly generated array of size 10,000

        ForkJoinPool pool = new ForkJoinPool();

        long start = System.nanoTime();//time in nanoseconds to get decimal places of milliseconds
        pool.invoke(new WorkStealHoarePartQuickSortInForkJoinVar2(workStealHoarePartQuicksrtArrayVar2, 0, workStealHoarePartQuicksrtArrayVar2.length-1));

        long end = System.nanoTime();
        System.out.println("Hoare Part Quick Sort Time with work Steal Variation 2: " + (end - start) / 1e6 + " milliseconds for input size: "+workStealHoarePartQuicksrtArrayVar2.length);

        //System.out.print(Arrays.toString(mergeArray)); //to check if it is sorted or not
        pool.shutdown();

        System.out.println("");
        System.out.println(UtilityMethods.isArraySrted(workStealHoarePartQuicksrtArrayVar2));
    }
}
