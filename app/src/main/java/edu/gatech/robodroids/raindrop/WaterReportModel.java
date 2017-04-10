package edu.gatech.robodroids.raindrop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Water Report Class
 * Created by jviszlai on 3/2/17.
 */
class WaterReportModel {

    private String submissionTime;
    private int reportNumber;
    private static int numOfReports;
    private double lat;
    private double lon;
    private String waterType;
    private String waterCondition;
    private String reporterName;

    /**
     * Create new water report
     * @param lat latitude of water source
     * @param lon longitude of water source
     * @param type type of water source.
     * @param condition condition of water source.
     * @param name name of who created report
     */
    public WaterReportModel(double lat, double lon, String type, String condition
                            ,String name, String subTime) {
        submissionTime = subTime;
        this.lat = lat;
        this.lon = lon;
        waterType = type;
        waterCondition = condition;
        reportNumber = ++numOfReports;
        reporterName = name;
    }

    /**
     * No arg default constructor for a water report.
     */
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
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(submissionTime));
        String actualDate = formatter.format(calendar.getTime());
        return String.format(Locale.ENGLISH,
                "Created by: %s. Latitude: %f. Longitude: %f. Type: %s. " +
                        "Condition: %s. Date Received: %s. Report Number: %d",
                reporterName, lat, lon, waterType, waterCondition, actualDate, reportNumber);
    }

    /**
     * Returns the longitudinal value of the water source's location.
     * @return longitudinal value.
     */
    public double getLon() {
        return lon;
    }

    /**
     * Returns the latitudinal value of the water source's location.
     * @return latitudinal value.
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets the longitudinal value of the water source's location.
     * @param lon longitudinal value.
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Returns the latitudinal value of the water source's location.
     * @param lat latitudinal value.
     */
    public void setLat(double lat) {
        this.lat = lat;
    }
}
