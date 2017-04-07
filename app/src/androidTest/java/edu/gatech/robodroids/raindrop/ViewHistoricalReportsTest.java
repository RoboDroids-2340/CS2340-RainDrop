package edu.gatech.robodroids.raindrop;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Randy on 4/7/2017.
 */
public class ViewHistoricalReportsTest {
    @Test
    public void yearFromDate() throws Exception {
        assertEquals(ViewHistoricalReports.yearFromDate("2017 "), "2017");
        assertEquals(ViewHistoricalReports.yearFromDate("1234567890 0987654321"), "1234567890");
        assertEquals(ViewHistoricalReports.yearFromDate("1234567890 /0987654321"), "1234567890");
        assertEquals(ViewHistoricalReports.yearFromDate("2017/ "), "");
        assertEquals(ViewHistoricalReports.yearFromDate(" "), "");
    }

}