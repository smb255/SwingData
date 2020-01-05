//SwingDataMIN

/*
 This is a minimized version of SwingData.
 The methods here are condensed with the help of a few additional methods
 */

import java.util.*;
import java.util.function.*;
import java.io.*;
import java.lang.Object;

public class SwingDataMIN {
    
    public static int searchContinuityAboveValue(double[] data, int indexBegin, int indexEnd,
                                                 double threshold, int winLength) {
        
        ArrayList<Predicate<Double>> groupPredicates = new ArrayList<Predicate<Double>>();
        groupPredicates.add(greaterThan(threshold));
        ArrayList<Integer> list = primeHelper(indexBegin, indexEnd, groupPredicates, winLength, false, data);
        int index = -1;
        if (!list.isEmpty()) {
            index = list.get(0);
        }
        
        return index;
    }
    
    public static int backSearchContinuityWithinRange(double[] data, int indexBegin, int indexEnd,
                                                      double thresholdLo, double thresholdHi, int winLength) {
        
        ArrayList<Predicate<Double>> groupPredicates = new ArrayList<Predicate<Double>>();
        groupPredicates.add(between(thresholdLo, thresholdHi));
        ArrayList<Integer> list = primeHelper(indexBegin, indexEnd, groupPredicates, winLength, true, data);
        int index = -1;
        if (!list.isEmpty()) {
            index = list.get(0);
        }
        
        return index;
    }
    
    public static int searchContinuityAboveValueTwoSignals(double[] data1, double[] data2, int indexBegin, int indexEnd,
                                                           double threshold1, double threshold2, int winLength) {
        
        ArrayList<Predicate<Double>> groupPredicates = new ArrayList<Predicate<Double>>();
        groupPredicates.add(greaterThan(threshold1));
        groupPredicates.add(greaterThan(threshold2));
        ArrayList<Integer> list = primeHelper(indexBegin, indexEnd, groupPredicates, winLength, false, data1, data2);
        int index = -1;
        if (!list.isEmpty()) {
            index = list.get(0);
        }
        
        return index;
    }
    
    public static ArrayList<Integer> searchMultiContinuityWithinRange(double[] data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength) {
        
        ArrayList<Predicate<Double>> groupPredicates = new ArrayList<Predicate<Double>>();
        groupPredicates.add(between(thresholdLo, thresholdHi));
        ArrayList<Integer> list = primeHelper(indexBegin, indexEnd, groupPredicates, winLength, false, data);
        
        return list;
    }
    
    public static ArrayList<Integer> primeHelper(int indexBegin, int indexEnd, ArrayList<Predicate<Double>> predicates,
                                                 int winLength, boolean backwards, double[]... dataList) {
        
        //Declaring local variables
        int count = 0;
        int[] indexTemp = new int[2];
        indexTemp[0] = -1;
        indexTemp[1] = -1;
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        
        //Throwing Exceptions
        if (backwards) {
            if (indexBegin < indexEnd) {
                throw new IllegalArgumentException("The Starting Index needs to be greater than the Ending Index.");
            }
            if (indexBegin > dataList[0].length - 1) {
                throw new IllegalArgumentException("The Starting Index needs to be less than " + dataList[0].length);
            }
            if (indexEnd < 0) {
                throw new IllegalArgumentException("The Ending Index needs to be 0 or greater.");
            }
        } else {
            if (indexBegin > indexEnd) {
                throw new IllegalArgumentException("The Starting Index needs to be less than the Ending Index.");
            }
            if (indexBegin < 0) {
                throw new IllegalArgumentException("The Starting Index needs to be 0 or greater.");
            }
            if (indexEnd > dataList[0].length - 1) {
                throw new IllegalArgumentException("The Ending Index needs to be less than " + dataList[0].length);
            }
        }
        
        for (int i = indexBegin; loopCondition(backwards, i, indexEnd); i = backwards ? i - 1 : i + 1) {
            if (thresholdCondition(dataList, predicates, i)) {
                if (count == 0) {
                    indexTemp[0] = i;
                }
                count++;
            } else {
                //Check if we hit our winLength
                if (count >= winLength) {
                    indexTemp[1] = i - 1;
                    indexList.add(indexTemp[0]);
                    indexList.add(indexTemp[1]);
                }
                count = 0;
            }
            //Check if we hit our winLength only on the last iteration
            if (count >= winLength && i == indexEnd) {
                indexTemp[1] = i;
                indexList.add(indexTemp[0]);
                indexList.add(indexTemp[1]);
                break;
            }
        }
        
        return indexList;
    }
    
    public static boolean thresholdCondition(double[][] dataList, ArrayList<Predicate<Double>> predicates, int i) {
        
        boolean condition = true;
        for (int j = 0; j < dataList.length; j++) {
            if (!(predicates.get(j).test(dataList[j][i]))) {
                condition = false;
            }
        }
        
        return condition;
    }
    
    public static Predicate<Double> greaterThan(double threshold) {
        return x -> x > threshold;
    }
    
    public static Predicate<Double> between(double thresholdLo, double thresholdHi) {
        return x -> x > thresholdLo && x < thresholdHi;
    }
    
    public static boolean loopCondition(boolean backwards, int i, int indexEnd) {
        return (backwards && (i > indexEnd - 1)) || (!backwards && (i < indexEnd + 1));
    }
    
}
