//SwingData

import java.util.*;
import java.io.*;

public class SwingData {
    
    public static int searchContinuityAboveValue(double[] data, int indexBegin, int indexEnd,
                                                 double threshold, int winLength) {
        //Declaring Local Variables
        int count = 0;
        int firstIndex = -1;
        
        //Throwing Exceptions
        if (indexBegin > indexEnd) {
            throw new IllegalArgumentException("The Starting Index needs to be less than the Ending Index.");
        }
        if (indexBegin < 0) {
            throw new IllegalArgumentException("The Starting Index needs to be 0 or greater.");
        }
        if (indexEnd > data.length - 1) {
            throw new IllegalArgumentException("The Ending Index needs to be less than " + data.length);
        }
        
        //Primary Loop
        for (int i = indexBegin; i < indexEnd + 1; i++) {
            if (data[i] > threshold) {
                if (count == 0) {
                    firstIndex = i;
                }
                count++;
            } else {
                count = 0;
                firstIndex = -1;
            }
            //Check if we hit out winLength
            if (count == winLength) {
                break;
            }
        }
        
        //Check if loop ended naturally and count still does not equal winLength
        if (count < winLength) {
            return -1;
        }
        
        return firstIndex;
    }
    
    public static int backSearchContinuityWithinRange(double[] data, int indexBegin, int indexEnd,
                                                      double thresholdLo, double thresholdHi, int winLength) {
        //Declaring local variables
        int count = 0;
        int firstIndex = -1;
        
        //Throwing Exceptions
        if (indexBegin < indexEnd) {
            throw new IllegalArgumentException("The Starting Index needs to be greater than the Ending Index.");
        }
        if (indexBegin > data.length - 1) {
            throw new IllegalArgumentException("The Starting Index needs to be less than " + data.length);
        }
        if (indexEnd < 0) {
            throw new IllegalArgumentException("The Ending Index needs to be 0 or greater.");
        }
        
        //Primary Loop
        for (int i = indexBegin; i > indexEnd - 1; i--) {
            if (data[i] > thresholdLo && data[i] < thresholdHi) {
                if (count == 0) {
                    firstIndex = i;
                }
                count++;
            } else {
                count = 0;
                firstIndex = -1;
            }
            //Check if we hit our winLength
            if (count == winLength) {
                break;
            }
        }
        
        //Check if loop ended naturally and count still does not equal winLength
        if (count < winLength) {
            return -1;
        }
        
        return firstIndex;
    }
    
    public static int searchContinuityAboveValueTwoSignals(double[] data1, double[] data2, int indexBegin, int indexEnd,
                                                           double threshold1, double threshold2, double winLength) {
        //Declaring local variables
        int count = 0;
        int firstIndex = -1;
        
        //Throwing Exceptions
        if (indexBegin > indexEnd) {
            throw new IllegalArgumentException("The Starting Index needs to be less than the Ending Index.");
        }
        if (indexBegin < 0) {
            throw new IllegalArgumentException("The Starting Index needs to be 0 or greater.");
        }
        if (indexEnd > data1.length - 1) {
            throw new IllegalArgumentException("The Ending Index needs to be less than " + data1.length);
        }
        
        for (int i = indexBegin; i < indexEnd + 1; i++) {
            if (data1[i] > threshold1 && data2[i] > threshold2) {
                if (count == 0) {
                    firstIndex = i;
                }
                count++;
            } else {
                count = 0;
                firstIndex = -1;
            }
            //Check if we hit our winLength
            if (count == winLength) {
                break;
            }
        }
        
        if (count < winLength) {
            return -1;
        }
        
        return firstIndex;
    }
    
    public static ArrayList<Integer> searchMultiContinuityWithinRange(double[] data, int indexBegin, int indexEnd,
                                                                      double thresholdLo, double thresholdHi, int winLength) {
        //Declaring local variables
        int count = 0;
        int[] indexTemp = new int[2];
        indexTemp[0] = -1;
        indexTemp[1] = -1;
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        
        //Throwing Exceptions
        if (indexBegin > indexEnd) {
            throw new IllegalArgumentException("The Starting Index needs to be less than the Ending Index.");
        }
        if (indexBegin < 0) {
            throw new IllegalArgumentException("The Starting Index needs to be 0 or greater.");
        }
        if (indexEnd > data.length - 1) {
            throw new IllegalArgumentException("The Ending Index needs to be less than " + data.length);
        }
        
        for (int i = indexBegin; i < indexEnd + 1; i++) {
            if (data[i] > thresholdLo && data[i] < thresholdHi) {
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
}
