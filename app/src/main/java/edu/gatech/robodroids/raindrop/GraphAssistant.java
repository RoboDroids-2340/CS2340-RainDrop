package edu.gatech.robodroids.raindrop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jjacob on 4/9/17.
 */

public class GraphAssistant {
    private double ppm;

    public GraphAssistant(double ppm) {
        this.ppm = ppm;
    }
    /**
     * Helper method to keep track of graph stats
     * @param qualityReport the quality report to check
     * @param monthTotal an array with the total PPM for each month
     * @param monthCounter an array with the total reports for each month
     * @param query the target location
     * @param yearString the target year
     */
    protected void updateTotalAndCounter(QualityReportModel qualityReport,
                                         double[] monthTotal,
                                         int[] monthCounter, String query, String yearString) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS"
                , Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long
                .parseLong(qualityReport.getSubmissionTime()));
        String actualDate = formatter.format(calendar.getTime());
        int diff = Integer.parseInt(yearFromDate(actualDate))
                - Integer.parseInt(yearString);
        String coord = "Lat: " + Double.toString(qualityReport.getLat());
        coord +=  " Lon: " + Double.toString(qualityReport.getLon());
        if ((diff == 0) && (query.equals(coord))) {
            int month = calendar.get(Calendar.MONTH);
            monthTotal[month - 1] += ppm;
            monthCounter[month - 1] += 1;
        }
    }

    /**
     * Takes the date string and returns the year.
     * @param dateString Date of report.
     * @return Year of report.
     */
    protected String yearFromDate(CharSequence dateString) {
        String stringYear = "";
        for (int i = 0; dateString.charAt(i) != ' '; i++) {
            if (dateString.charAt(i) == '/') {
                stringYear = "";
            } else {
                stringYear += dateString.charAt(i);
            }
        }
        return stringYear;
    }
}
