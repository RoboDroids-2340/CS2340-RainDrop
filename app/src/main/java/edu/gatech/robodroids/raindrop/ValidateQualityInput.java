package edu.gatech.robodroids.raindrop;

/**
 * Created by jviszlai on 4/9/17.
 */
public class ValidateQualityInput {

    /**
     * Takes in the necessary data for a quality report before it's converted
     * to different data types. Checks to see if all data is valid and can be
     * converted to necessary types for the quality report object. If so,
     * creates a new quality report object and returns it.
     * @param mLat Latitude
     * @param mLon Longitude
     * @param selectedCondition Water condition
     * @param virusPPM Virus PPM
     * @param contaminantPPM Contaminant PPM
     * @param name Reporter name
     * @param time Time of report creation
     * @return Created quality report
     */
    public static QualityReportModel inputToQualityReport(
            String mLat, String mLon, Object selectedCondition, String virusPPM,
            String contaminantPPM, String name, String time) {
        try {
            if (selectedCondition == null) {
                throw new NullPointerException();
            }
            return new QualityReportModel(
                    Double.parseDouble(mLat),
                    Double.parseDouble(mLon),
                    selectedCondition.toString(),
                    Double.parseDouble(virusPPM),
                    Double.parseDouble(contaminantPPM),
                    name,
                    time);
        } catch(Exception e) {
            return null;
        }
    }
}
