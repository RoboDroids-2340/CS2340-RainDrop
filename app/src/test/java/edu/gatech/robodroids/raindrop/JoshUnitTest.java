package edu.gatech.robodroids.raindrop;


/**
 * Created by jjacob on 4/9/17.
 */

import org.junit.Test;
import static org.junit.Assert.*;


public class JoshUnitTest {
    private final int MONTHS = 12;
    @Test
    public void testUpdateTotalAndCounter() throws Exception {
        String coord = "Lat: 1.0 Lon: 1.0";
        String time = "1491789305860";
        QualityReportModel testReport = new QualityReportModel(1.0, 1.0, "Bad", 1.0, 1.0,"Sad",time);
        GraphAssistant test = new GraphAssistant(testReport.getContaminantPPM());
        double[] monthTotal = new double[MONTHS];
        int[] monthCounter = new int[MONTHS];
        test.updateTotalAndCounter(testReport, monthTotal, monthCounter, coord, "2017");

        double[] correctTotalAfterNormalInput = new double[MONTHS];
        correctTotalAfterNormalInput[2] = 1.0;

        int[] correctCounterAfterNormalInput= new int[MONTHS];
        correctCounterAfterNormalInput[2] = 1;

        assertArrayEquals(correctTotalAfterNormalInput, monthTotal, 0.0);
        assertArrayEquals(correctCounterAfterNormalInput, monthCounter);

        //Now test invalid update
        coord = "Lat: 1.0 Lon: 2.0";
        test.updateTotalAndCounter(testReport, monthTotal, monthCounter, coord, "2017");

        assertArrayEquals(correctTotalAfterNormalInput, monthTotal, 0.0);
        assertArrayEquals(correctCounterAfterNormalInput, monthCounter);

        coord = "Lat: 1.0 Lon: 1.0";
        test.updateTotalAndCounter(testReport, monthTotal, monthCounter, coord, "2016");

        assertArrayEquals(correctTotalAfterNormalInput, monthTotal, 0.0);
        assertArrayEquals(correctCounterAfterNormalInput, monthCounter);


    }
}
