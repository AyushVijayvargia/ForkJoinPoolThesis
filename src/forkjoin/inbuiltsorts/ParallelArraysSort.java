package forkjoin.inbuiltsorts;

import forkjoin.algorithms.UtilityMethods;

import java.util.Arrays;

public class ParallelArraysSort {

    public static void main(String[] args) {

        int[] arrSrt = UtilityMethods.randomlyGeneratedArray(10_000);//it will create randomly generated array of size 10,000

        long start = System.nanoTime();
        Arrays.parallelSort(arrSrt);
        long end = System.nanoTime();
        System.out.println("Parallel arrays Sort Time: " + (end - start) / 1e6 + " ms");


        System.out.println("Is array sorted? " + UtilityMethods.isArraySrted(arrSrt));

    }
}
