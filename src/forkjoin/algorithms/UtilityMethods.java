package forkjoin.algorithms;

import java.util.Arrays;
import java.util.Random;

public class UtilityMethods {

    public static final int THRESHOLD_SIZE = 1_000;

    public static final Random RANDOM_VT = new Random();

    public static void swapping(int[] array, int firstNumberIndex, int secondNumberIndex) {
        int temporaryVariable = array[firstNumberIndex];
        array[firstNumberIndex] = array[secondNumberIndex];
        array[secondNumberIndex] = temporaryVariable;
    }

    public static int [] randomlyGeneratedArray(int size)
    {
        int[] array = new int[size];

        for(int i=0;i<size;i++)
        {
            array[i]=(int) (Math.random() * size);
        }
        return array;
    }

    public static int [] copyArray(int array[],int lowerIndex, int upperIndex)
    {
        int temporaryArray[];
        temporaryArray= Arrays.copyOfRange(array, lowerIndex, upperIndex);
        return temporaryArray;
    }


    public static int hoarePart(int[] hoarePartQuicksrtArray, int low, int high) {
        int pivValue = hoarePartQuicksrtArray[low];  // Hoare's scheme chooses the low index as the pivot
        int rightIndx = low - 1;
        int leftIndx = high + 1;

        while (true) {
            // Move towards left, while finding an element smaller than or equal to the pivot
            do {
                leftIndx--;
            } while (hoarePartQuicksrtArray[leftIndx] > pivValue);

            // Move towards right, while finding an element greater than or equal to the pivot
            do {
                rightIndx++;
            } while (hoarePartQuicksrtArray[rightIndx] < pivValue);

            if (rightIndx >= leftIndx) {
                return leftIndx;  // Return the partition index
            }

            UtilityMethods.swapping(hoarePartQuicksrtArray, rightIndx, leftIndx);
        }
    }

    public static boolean isArraySrted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }
}
