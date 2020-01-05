//SwingDataDRIVER

/* 
 USAGE: Compile: javac SwingDataDRIVER.java
 Run: 	java SwingDataDRIVER [fileName]
 (The fileName entry is optional, if not entered
 "latestSwing.csv" will be used)
 */

import java.util.*;
import java.io.*;
import java.lang.*;

public class SwingDataDRIVER {
    
    //Declaring Global Variables
    static ArrayList<double[]> totalData = new ArrayList<double[]>();
    static int[] timestamp;
    static Scanner kb = new Scanner(System.in);
    static SwingData classInstanceOrigin = new SwingData();
    static SwingDataMIN classInstanceMin = new SwingDataMIN();
    static String[] columns = {"ax", "ay", "az", "wx", "wy", "wz"};
    static String[] classes = {"SwingData", "SwingDataMIN", "Both Classes"};
    
    public static void main(String[] args) throws IOException {
        //Reading CSV File
        String fileName = "latestSwing.csv";
        if (args.length == 1) {
            fileName = args[0];
        } else if (args.length > 1) {
            throw new IllegalArgumentException("USAGE: java SwingDataDRIVER [fileName || NoEntry]");
        }
        
        totalData = readFile(fileName);
        
        //Declaring Local Variables
        int choice;
        boolean loop = true, loop2 = true;
        
        do {
            System.out.print("\nPlease enter a number to perform an operation:\n" +
                             "1) searchContinuityAboveValue\n" +
                             "2) backSearchContinuityWithinRange\n" +
                             "3) searchContinuityAboveValueTwoSignals\n" +
                             "4) searchMultiContinuityWithinRange\n" +
                             "5) Print File\n" +
                             "6) Quit\n" +
                             "Option: ");
            choice = integerHelperChoice(6);
            System.out.println();
            if (choice == 1) {
                handleOption1();
            } else if (choice == 2) {
                handleOption2();
            } else if (choice == 3) {
                handleOption3();
            } else if (choice == 4) {
                handleOption4();
            } else if (choice == 5) {
                printFile();
            } else if (choice == 6) {
                loop = false;
            }
        } while(loop);
        
        System.out.println("Successfully exiting program!\n");
    }
    
    public static void handleOption1() {
        System.out.print("Enter an Integer representing one of the following columns: \n" +
                         "\t1)ax 2)ay 3)az 4)wx 5)wy 6)wz\n" +
                         "Option: ");
        int data = integerHelperChoice(6);
        data--;
        System.out.print("Enter an Integer representing the starting index.\n" +
                         "Entry: ");
        int indexBegin = integerHelper();
        System.out.print("Enter an Integer representing the ending index.\n" +
                         "Entry: ");
        int indexEnd = integerHelper();
        System.out.print("Enter a Double representing the threshold.\n" +
                         "Entry: ");
        double threshold = doubleHelper();
        System.out.print("Enter an Integer representing the window length.\n" +
                         "Entry: ");
        int winLength = integerHelper();
        System.out.print("Enter an Integer representing the original SwingData class, the\n" +
                         "minimized SwingData class, or both (in order to confirm outputs)\n" +
                         "\t1)SwingData 2)SwingDataMIN 3)Both Classes\n" +
                         "Option: ");
        int classSelection = integerHelperChoice(3);
        classSelection--;
        
        System.out.println("\nMethod: searchContinuityAboveValue\n" +
                           "\tData: " + columns[data] + "\n" +
                           "\tStarting Index: " + indexBegin + "\n" +
                           "\tEnding Index: " + indexEnd + "\n" +
                           "\tThreshold: " + threshold + "\n" +
                           "\tWindow Length: " + winLength + "\n" +
                           "\tClass Selection: " + classes[classSelection] + "\n");
        
        int result1 = 0, result2 = 0;
        if (classSelection == 0) {
            result1 = classInstanceOrigin.searchContinuityAboveValue(
                                                                     totalData.get(data), indexBegin, indexEnd, threshold, winLength);
        } else if (classSelection == 1) {
            result1 = classInstanceMin.searchContinuityAboveValue(
                                                                  totalData.get(data), indexBegin, indexEnd, threshold, winLength);
        } else if (classSelection == 2) {
            result1 = classInstanceOrigin.searchContinuityAboveValue(
                                                                     totalData.get(data), indexBegin, indexEnd, threshold, winLength);
            result2 = classInstanceMin.searchContinuityAboveValue(
                                                                  totalData.get(data), indexBegin, indexEnd, threshold, winLength);
            if (result1 != result2) {
                throw new IllegalArgumentException("Values not equal!");
            }
        }
        
        if (result1 == -1) {
            System.out.println("*** No values meet this criteria for at least " +
                               winLength + " samples in a row. ***");
        } else {
            System.out.println("*** The first index is " + result1 + " for at least " +
                               winLength + " samples in a row. ***");
        }
    }
    
    public static void handleOption2() {
        System.out.print("Enter an Integer representing one of the following columns: \n" +
                         "\t1)ax 2)ay 3)az 4)wx 5)wy 6)wz\n" +
                         "Option: ");
        int data = integerHelperChoice(6);
        data--;
        System.out.print("Enter an Integer representing the starting index. (Higher than ending index)\n" +
                         "Entry: ");
        int indexBegin = integerHelper();
        System.out.print("Enter an Integer representing the ending index. (Lower than starting index)\n" +
                         "Entry: ");
        int indexEnd = integerHelper();
        System.out.print("Enter a Double representing the lower threshold.\n" +
                         "Entry: ");
        double thresholdLo = doubleHelper();
        System.out.print("Enter a Double representing the higher threshold.\n" +
                         "Entry: ");
        double thresholdHi = doubleHelper();
        System.out.print("Enter an Integer representing the window length.\n" +
                         "Entry: ");
        int winLength = integerHelper();
        System.out.print("Enter an Integer representing the original SwingData class, the\n" +
                         "minimized SwingData class, or both (in order to confirm outputs)\n" +
                         "\t1)SwingData 2)SwingDataMIN 3)Both Classes\n" +
                         "Option: ");
        int classSelection = integerHelperChoice(3);
        classSelection--;
        
        System.out.println("\nMethod: backSearchContinuityWithinRange\n" +
                           "\tData: " + columns[data] + "\n" +
                           "\tStarting Index: " + indexBegin + "\n" +
                           "\tEnding Index: " + indexEnd + "\n" +
                           "\tLower Threshold: " + thresholdLo + "\n" +
                           "\tHigher Threshold: " + thresholdHi + "\n" +
                           "\tWindow Length: " + winLength + "\n" +
                           "\tClass Selection: " + classes[classSelection] + "\n");
        
        int result1 = 0, result2 = 0;
        if (classSelection == 0) {
            result1 = classInstanceOrigin.backSearchContinuityWithinRange(
                                                                          totalData.get(data), indexBegin, indexEnd, thresholdLo, thresholdHi, winLength);
        } else if (classSelection == 1) {
            result1 = classInstanceMin.backSearchContinuityWithinRange(
                                                                       totalData.get(data), indexBegin, indexEnd, thresholdLo, thresholdHi, winLength);
        } else if (classSelection == 2) {
            result1 = classInstanceOrigin.backSearchContinuityWithinRange(
                                                                          totalData.get(data), indexBegin, indexEnd, thresholdLo, thresholdHi, winLength);
            result2 = classInstanceMin.backSearchContinuityWithinRange(
                                                                       totalData.get(data), indexBegin, indexEnd, thresholdLo, thresholdHi, winLength);
            if (result1 != result2) {
                throw new IllegalArgumentException("Values not equal!");
            }
        }
        
        if (result1 == -1) {
            System.out.println("*** No values meet this criteria for at least " +
                               winLength + " samples in a row. ***");
        } else {
            System.out.println("*** The first index is " + result1 + " for at least " +
                               winLength + " samples in a row. ***");
        }
    }
    
    public static void handleOption3() {
        System.out.print("Enter an Integer representing one of the following columns (first): \n" +
                         "\t1)ax 2)ay 3)az 4)wx 5)wy 6)wz\n" +
                         "Option: ");
        int data1 = integerHelperChoice(6);
        data1--;
        System.out.print("Enter an Integer representing one of the following columns (second): \n" +
                         "\t1)ax 2)ay 3)az 4)wx 5)wy 6)wz\n" +
                         "Option: ");
        int data2 = integerHelperChoice(6);
        data2--;
        System.out.print("Enter an Integer representing the starting index.\n" +
                         "Entry: ");
        int indexBegin = integerHelper();
        System.out.print("Enter an Integer representing the ending index.\n" +
                         "Entry: ");
        int indexEnd = integerHelper();
        System.out.print("Enter a Double representing the threshold (first).\n" +
                         "Entry: ");
        double threshold1 = doubleHelper();
        System.out.print("Enter a Double representing the threshold (second).\n" +
                         "Entry: ");
        double threshold2 = doubleHelper();
        System.out.print("Enter an Integer representing the window length.\n" +
                         "Entry: ");
        int winLength = integerHelper();
        System.out.print("Enter an Integer representing the original SwingData class, the\n" +
                         "minimized SwingData class, or both (in order to confirm outputs)\n" +
                         "\t1)SwingData 2)SwingDataMIN 3)Both Classes\n" +
                         "Option: ");
        int classSelection = integerHelperChoice(3);
        classSelection--;
        
        System.out.println("\nMethod: searchContinuityAboveValueTwoSignals\n" +
                           "\tData1: " + columns[data1] + "\n" +
                           "\tData2: " + columns[data2] + "\n" +
                           "\tStarting Index: " + indexBegin + "\n" +
                           "\tEnding Index: " + indexEnd + "\n" +
                           "\tThreshold1: " + threshold1 + "\n" +
                           "\tThreshold2: " + threshold2 + "\n" +
                           "\tWindow Length: " + winLength + "\n" +
                           "\tClass Selection: " + classes[classSelection] + "\n");
        
        int result1 = 0, result2 = 0;
        if (classSelection == 0) {
            result1 = classInstanceOrigin.searchContinuityAboveValueTwoSignals(
                                                                               totalData.get(data1), totalData.get(data2), indexBegin, indexEnd, threshold1, threshold2, winLength);
        } else if (classSelection == 1) {
            result1 = classInstanceMin.searchContinuityAboveValueTwoSignals(
                                                                            totalData.get(data1), totalData.get(data2), indexBegin, indexEnd, threshold1, threshold2, winLength);
        } else if (classSelection == 2) {
            result1 = classInstanceOrigin.searchContinuityAboveValueTwoSignals(
                                                                               totalData.get(data1), totalData.get(data2), indexBegin, indexEnd, threshold1, threshold2, winLength);
            result2 = classInstanceMin.searchContinuityAboveValueTwoSignals(
                                                                            totalData.get(data1), totalData.get(data2), indexBegin, indexEnd, threshold1, threshold2, winLength);
            if (result1 != result2) {
                throw new IllegalArgumentException("Values not equal!");
            }
        }
        
        if (result1 == -1) {
            System.out.println("*** No values meet this criteria for at least " +
                               winLength + " samples in a row. ***");
        } else {
            System.out.println("*** The first index is " + result1 + " for at least " +
                               winLength + " samples in a row. ***");
        }
    }
    
    public static void handleOption4() {
        System.out.print("Enter an Integer representing one of the following columns: \n" +
                         "\t1)ax 2)ay 3)az 4)wx 5)wy 6)wz\n" +
                         "Option: ");
        int data = integerHelperChoice(6);
        data--;
        System.out.print("Enter an Integer representing the starting index.\n" +
                         "Entry: ");
        int indexBegin = integerHelper();
        System.out.print("Enter an Integer representing the ending index.\n" +
                         "Entry: ");
        int indexEnd = integerHelper();
        System.out.print("Enter a Double representing the lower threshold.\n" +
                         "Entry: ");
        double thresholdLo = doubleHelper();
        System.out.print("Enter a Double representing the higher threshold.\n" +
                         "Entry: ");
        double thresholdHi = doubleHelper();
        System.out.print("Enter an Integer representing the window length.\n" +
                         "Entry: ");
        int winLength = integerHelper();
        System.out.print("Enter an Integer representing the original SwingData class, the\n" +
                         "minimized SwingData class, or both (in order to confirm outputs)\n" +
                         "\t1)SwingData 2)SwingDataMIN 3)Both Classes\n" +
                         "Option: ");
        int classSelection = integerHelperChoice(3);
        classSelection--;
        
        System.out.println("\nMethod: searchMultiContinuityWithinRange\n" +
                           "\tData: " + columns[data] + "\n" +
                           "\tStarting Index: " + indexBegin + "\n" +
                           "\tEnding Index: " + indexEnd + "\n" +
                           "\tLower Threshold: " + thresholdLo + "\n" +
                           "\tHigher Threshold: " + thresholdHi + "\n" +
                           "\tWindow Length: " + winLength + "\n" +
                           "\tClass Selection: " + classes[classSelection] + "\n");
        
        ArrayList<Integer> result1 = new ArrayList<Integer>();
        ArrayList<Integer> result2 = new ArrayList<Integer>();
        if (classSelection == 0) {
            result1 = classInstanceOrigin.searchMultiContinuityWithinRange(
                                                                           totalData.get(data), indexBegin, indexEnd, thresholdLo, thresholdHi, winLength);
        } else if (classSelection == 1) {
            result1 = classInstanceMin.searchMultiContinuityWithinRange(
                                                                        totalData.get(data), indexBegin, indexEnd, thresholdLo, thresholdHi, winLength);
        } else if (classSelection == 2) {
            result1 = classInstanceOrigin.searchMultiContinuityWithinRange(
                                                                           totalData.get(data), indexBegin, indexEnd, thresholdLo, thresholdHi, winLength);
            result2 = classInstanceMin.searchMultiContinuityWithinRange(
                                                                        totalData.get(data), indexBegin, indexEnd, thresholdLo, thresholdHi, winLength);
            if (!result1.equals(result2)) {
                throw new IllegalArgumentException("Values not equal!");
            }
        }
        
        if (result1.isEmpty()) {
            System.out.println("*** No values meet this criteria for at least " +
                               winLength + " samples in a row. ***");
        } else {
            System.out.println("\tContinuous Samples:");
            for (int i = 0; i < result1.size(); i++) {
                System.out.println("*** The first index is " + result1.get(i) + " and the last index is " +
                                   result1.get(++i) + " for at least " + winLength + " samples in a row. ***");
            }
            
        }
    }
    
    public static int integerHelperChoice(int upperBound) {
        boolean loop = true;
        int data = 0;
        do {
            try {
                data = kb.nextInt();
                if (data >= 1 && data <= upperBound) {
                    loop = false;
                } else {
                    System.out.println("Invalid entry, please enter a number between 1 and 6, inclusive!");
                    System.out.print("Option: ");
                }
            } catch (Exception e) {
                kb.nextLine();
                System.out.println("Invalid entry, please enter an INTEGER!");
                System.out.print("Option: ");
            }
        } while(loop);
        
        return data;
    }
    
    public static int integerHelper() {
        boolean loop = true;
        int data = 0;
        do {
            try {
                data = kb.nextInt();
                loop = false;
            } catch (Exception e) {
                kb.nextLine();
                System.out.println("Invalid entry, please enter an INTEGER!");
                System.out.print("Entry: ");
            }
        } while(loop);
        
        return data;
    }
    
    public static double doubleHelper() {
        boolean loop = true;
        double data = 0.0;
        do {
            try {
                data = kb.nextDouble();
                loop = false;
            } catch (Exception e) {
                kb.nextLine();
                System.out.println("Invalid entry, please enter a DOUBLE!");
                System.out.print("Entry: ");
            }
        } while(loop);
        
        return data;
    }
    
    public static ArrayList<double[]> readFile(String fileName) throws IOException {
        //Declaring Local Variables
        ArrayList<String> lines = new ArrayList<String>();
        String[] line;
        double[] ax, ay, az, wx, wy, wz;
        
        //Set up FileReader and Scanner
        FileReader file = null;
        try {
            file = new FileReader(fileName);
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Scanner reader = new Scanner(file);
        
        //Loop through data once to find the length
        while (reader.hasNextLine()) {
            lines.add(reader.nextLine());
        }
        reader.close();
        
        timestamp = new int[lines.size()];
        ax = new double[lines.size()];
        ay = new double[lines.size()];
        az = new double[lines.size()];
        wx = new double[lines.size()];
        wy = new double[lines.size()];
        wz = new double[lines.size()];
        
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i).split(",");
            timestamp[i] = Integer.parseInt(line[0]);
            ax[i] = Double.parseDouble(line[1]);
            ay[i] = Double.parseDouble(line[2]);
            az[i] = Double.parseDouble(line[3]);
            wx[i] = Double.parseDouble(line[4]);
            wy[i] = Double.parseDouble(line[5]);
            wz[i] = Double.parseDouble(line[6]);
        }
        
        ArrayList<double[]> totalDataTemp = new ArrayList<double[]>();
        totalDataTemp.add(ax);
        totalDataTemp.add(ay);
        totalDataTemp.add(az);
        totalDataTemp.add(wx);
        totalDataTemp.add(wy);
        totalDataTemp.add(wz);
        
        return totalDataTemp;
    }
    
    public static void printFile() {
        for(int i = 0; i < timestamp.length; i++) {
            System.out.println(timestamp[i] + ", " +
                               totalData.get(0)[i] + ", " +
                               totalData.get(1)[i] + ", " +
                               totalData.get(2)[i] + ", " +
                               totalData.get(3)[i] + ", " +
                               totalData.get(4)[i] + ", " +
                               totalData.get(5)[i]);
        }
        System.out.println("Total Lines: " + timestamp.length);
    }
}
