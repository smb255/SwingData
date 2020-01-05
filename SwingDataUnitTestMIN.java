//SwingDataUnitTestMIN

/* 
 USAGE: Compile: javac -cp .:junit-4.13-rc-2.jar:hamcrest-core-1.3.jar SwingDataUnitTestMIN.java
 Run: 	java -cp .:junit-4.13-rc-2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore SwingDataUnitTestMIN
 */

import java.util.*;
import java.io.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.BeforeClass;

public class SwingDataUnitTestMIN {
    
    static ArrayList<double[]> testData;
    
    @BeforeClass
    public static void readFileTest() throws IOException {
        testData = SwingDataDRIVER.readFile("latestSwing.csv");
        
    }
    
    //All location check methods just check that the data itself is correct
    @Test
    public void locationCheck1() {
        assertEquals(-1.136719, testData.get(0)[1], 0);
    }
    
    @Test
    public void locationCheck2() {
        assertEquals(0.014913, testData.get(3)[354], 0);
    }
    
    @Test
    public void locationCheck3() {
        assertEquals(-9.450898, testData.get(5)[1275], 0);
    }
    
    @Test
    public void locationCheck4() {
        assertEquals(-0.43457, testData.get(1)[1211], 0);
    }
    
    @Test
    public void locationCheck5() {
        assertEquals(-6.593897, testData.get(5)[0], 0);
    }
    
    @Test
    public void searchContinuityAboveValue1() {
        int index = SwingDataMIN.searchContinuityAboveValue(testData.get(0), 0, 5, -1.0, 2);
        assertEquals(4, index);
    }
    
    @Test
    public void searchContinuityAboveValue2() {
        int index = SwingDataMIN.searchContinuityAboveValue(testData.get(3), 0, 1275, 3.0, 40);
        assertEquals(1196, index);
    }
    
    @Test
    public void searchContinuityAboveValue3() {
        int index = SwingDataMIN.searchContinuityAboveValue(testData.get(5), 0, 1275, 4.0, 40);
        assertEquals(786, index);
    }
    
    @Test
    public void searchContinuityAboveValue4() {
        int index = SwingDataMIN.searchContinuityAboveValue(testData.get(0), 1275, 1275, 4.0, 0);
        assertEquals(-1, index);
    }
    
    //EDGE CASE
    @Test
    public void searchContinuityAboveValue5() {
        int index = SwingDataMIN.searchContinuityAboveValue(testData.get(4), 1275, 1275, -1.36, 1);
        assertEquals(1275, index);
    }
    
    @Test
    public void backSearchContinuityWithinRange1() {
        int index = SwingDataMIN.backSearchContinuityWithinRange(testData.get(0), 90, 0, 1.0, 3.0, 25);
        assertEquals(72, index);
    }
    
    @Test
    public void backSearchContinuityWithinRange2() {
        int index = SwingDataMIN.backSearchContinuityWithinRange(testData.get(2), 100, 0, -2.0, 1.0, 5);
        assertEquals(100, index);
    }
    
    @Test
    public void backSearchContinuityWithinRange3() {
        int index = SwingDataMIN.backSearchContinuityWithinRange(testData.get(4), 1000, 200, -2.0, 1.0, 20);
        assertEquals(631, index);
    }
    
    @Test
    public void backSearchContinuityWithinRange4() {
        int index = SwingDataMIN.backSearchContinuityWithinRange(testData.get(2), 400, 200, -1.0, 2.0, 3);
        assertEquals(400, index);
    }
    
    //EDGE CASE
    @Test
    public void backSearchContinuityWithinRange5() {
        int index = SwingDataMIN.backSearchContinuityWithinRange(testData.get(1), 0, 0, 0.22, 0.24, 1);
        assertEquals(0, index);
    }
    
    @Test
    public void searchContinuityAboveValueTwoSignals1() {
        int index = SwingDataMIN.searchContinuityAboveValueTwoSignals(testData.get(2), testData.get(5),
                                                                      45, 100, -1.0, -1.0, 20);
        assertEquals(50, index);
    }
    
    @Test
    public void searchContinuityAboveValueTwoSignals2() {
        int index = SwingDataMIN.searchContinuityAboveValueTwoSignals(testData.get(1), testData.get(5),
                                                                      0, 1000, -1.0, 1.0, 10);
        assertEquals(748, index);
    }
    
    @Test
    public void searchContinuityAboveValueTwoSignals3() {
        int index = SwingDataMIN.searchContinuityAboveValueTwoSignals(testData.get(4), testData.get(0),
                                                                      200, 800, -2.0, -1.0, 10);
        assertEquals(200, index);
    }
    
    @Test
    public void searchContinuityAboveValueTwoSignals4() {
        int index = SwingDataMIN.searchContinuityAboveValueTwoSignals(testData.get(2), testData.get(3),
                                                                      0, 500, -1.0, 3.0, 5);
        assertEquals(-1, index);
    }
    
    //EDGE CASE
    @Test
    public void searchContinuityAboveValueTwoSignals5() {
        int index = SwingDataMIN.searchContinuityAboveValueTwoSignals(testData.get(1), testData.get(2),
                                                                      1275, 1275, 0.0, -1.0, 1);
        assertEquals(1275, index);
    }
    
    @Test
    public void searchMultiContinuityWithinRange1() {
        ArrayList<Integer> index = SwingDataMIN.searchMultiContinuityWithinRange(testData.get(0), 0, 1275, -1.0,
                                                                                 2.0, 20);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(4);
        temp.add(777);
        temp.add(956);
        temp.add(1275);
        assertEquals(temp, index);
    }
    
    @Test
    public void searchMultiContinuityWithinRange2() {
        ArrayList<Integer> index = SwingDataMIN.searchMultiContinuityWithinRange(testData.get(2), 0, 1200, -2.0,
                                                                                 3.0, 10);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(0);
        temp.add(823);
        temp.add(890);
        temp.add(907);
        temp.add(911);
        temp.add(1200);
        assertEquals(temp, index);
    }
    
    @Test
    public void searchMultiContinuityWithinRange3() {
        ArrayList<Integer> index = SwingDataMIN.searchMultiContinuityWithinRange(testData.get(4), 0, 1200, -3.0,
                                                                                 5.0, 20);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(61);
        temp.add(728);
        temp.add(951);
        temp.add(1189);
        assertEquals(temp, index);
    }
    
    @Test
    public void searchMultiContinuityWithinRange4() {
        ArrayList<Integer> index = SwingDataMIN.searchMultiContinuityWithinRange(testData.get(5), 500, 600, -5.0,
                                                                                 -2.0, 40);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        assertEquals(temp, index);
    }
    
    @Test
    public void searchMultiContinuityWithinRange5() {
        ArrayList<Integer> index = SwingDataMIN.searchMultiContinuityWithinRange(testData.get(0), 0, 1275, -100.0,
                                                                                 100.0, 1);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(0);
        temp.add(1275);
        assertEquals(temp, index);
    }
    
}
