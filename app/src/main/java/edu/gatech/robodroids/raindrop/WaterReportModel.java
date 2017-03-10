package edu.gatech.robodroids.raindrop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Water Report Class
 * Created by jviszlai on 3/2/17.
 */
public class WaterReportModel {

    private String submissionTime;
    private int reportNumber;
    //TODO Change numOfReports to a database reference.
    private static int numOfReports;
    private String waterLocation;
    private String waterType;
    private String waterCondition;
    private String reporterName;

    /**
     * Create new water report
     * @param location location of water source.
     * @param type type of water source.
     * @param condition condition of water source.
     * @param name name of who created report
     */
    public WaterReportModel(String location, String type, String condition, String name, String subTime) {
        submissionTime = subTime;
        waterLocation = location;
        waterType = type;
        waterCondition = condition;
        reportNumber = ++numOfReports;
        reporterName = name;
    }

    public WaterReportModel() {

    }
    /**
     * Get submission time.
     * @return Report submission time.
     */
    public String getSubmissionTime() {
        return submissionTime;
    }

    /**
     * Get report number.
     * @return Report number.
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Get report location.
     * @return Report location.
     */
    public String getWaterLocation() {
        return waterLocation;
    }

    /**
     * Get water type.
     * @return Water type.
     */
    public String getWaterType() {
        return waterType;
    }

    /**
     * Get water condition.
     * @return Water condition.
     */
    public String getWaterCondition() {
        return waterCondition;
    }

    /**
     * Get reporter name.
     * @return Reporter name.
     */
    public java.lang.String getReporterName() {
        return reporterName;
    }

    /**
     * ToString
     * @return Water Report info.
     */
    public String toString() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(submissionTime));
        String actualDate = formatter.format(calendar.getTime());
        return String.format(
                "Created by: %s. Location: %s. Type: %s. Condition: %s. Date Received: %s. Report Number: %d",
                reporterName, waterLocation, waterType, waterCondition, actualDate, reportNumber);
    }
}
