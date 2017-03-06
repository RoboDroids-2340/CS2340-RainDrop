package edu.gatech.robodroids.raindrop;

import java.util.Date;
/**
 * Water Report Class
 * Created by jviszlai on 3/2/17.
 */
public class WaterReportModel {

    private Date submissionTime;
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
    public WaterReportModel(String location, String type, String condition, String name) {
        submissionTime = new Date();
        waterLocation = location;
        waterType = type;
        waterCondition = condition;
        reportNumber = ++numOfReports;
        reporterName = name;
    }

    /**
     * Get submission time.
     * @return Report submission time.
     */
    public Date getSubmissionTime() {
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
        return String.format(
                "Created by: %s. Location: %s. Type: %s. Condition: %s. Date Received: %s. Report Number: %d",
                reporterName, waterLocation, waterType, waterCondition, submissionTime.toString(), reportNumber);
    }
}
