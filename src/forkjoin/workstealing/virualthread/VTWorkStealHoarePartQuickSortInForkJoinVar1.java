package forkjoin.workstealing.virualthread;

import forkjoin.algorithms.UtilityMethods;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import static forkjoin.algorithms.UtilityMethods.RANDOM_VT;


public class VTWorkStealHoarePartQuickSortInForkJoinVar1 {

        private final int[] workStealHoarePartQuicksrtArrayVar1;
        private final int lowerIndex;
        private final int upperIndex;
        public VTWorkStealHoarePartQuickSortInForkJoinVar1(int[] workStealHoarePartQuicksrtArrayVar1, int lowerIndex, int end) {
            this.workStealHoarePartQuicksrtArrayVar1 = workStealHoarePartQuicksrtArrayVar1;
            this.lowerIndex = lowerIndex;
            this.upperIndex = end;
        }

        protected void compute() {
            if (upperIndex - lowerIndex > UtilityMethods.THRESHOLD_SIZE) {
                int pivotInd = UtilityMethods.hoarePart(workStealHoarePartQuicksrtArrayVar1, lowerIndex, upperIndex);

                try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
                    if (RANDOM_VT.nextBoolean()) {
                        var subtask1 = executor.submit(() -> new VTWorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar1, pivotInd + 1, upperIndex).compute());
                        new VTWorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar1, lowerIndex, pivotInd).compute();
                        subtask1.get();
                    } else {
                        var subtask2 = executor.submit(() -> new VTWorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar1, lowerIndex, pivotInd).compute());
                        new VTWorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar1, pivotInd + 1, upperIndex).compute();
                        subtask2.get();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                Arrays.sort(workStealHoarePartQuicksrtArrayVar1, lowerIndex, upperIndex+1);//this is inbuilt method
            }
        }


    public static void main(String[] args) {
        int[] workStealHoarePartQuicksrtArrayVar1 = UtilityMethods.randomlyGeneratedArray(10_000);//it will create randomly generated array of size 10,000

      //  ForkJoinPool pool = new ForkJoinPool();

        long start = System.nanoTime();//time in nanoseconds to get decimal places of milliseconds
     //   pool.invoke(new VTWorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar1, 0, workStealHoarePartQuicksrtArrayVar1.length-1));
        new VTWorkStealHoarePartQuickSortInForkJoinVar1(workStealHoarePartQuicksrtArrayVar1, 0, workStealHoarePartQuicksrtArrayVar1.length - 1).compute();


        long end = System.nanoTime();
        System.out.println("VT Hoare Part Quick Sort Time with work Steal Variation 1: " + (end - start) / 1e6 + " milliseconds for input size: "+workStealHoarePartQuicksrtArrayVar1.length);

        //System.out.print(Arrays.toString(mergeArray)); //to check if it is sorted or not
        //pool.shutdown();
        System.out.println("");
        System.out.println(UtilityMethods.isArraySrted(workStealHoarePartQuicksrtArrayVar1));
    }
}
