package edu.gatech.robodroids.raindrop;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Quality Report Class
 * Created by jviszlai on 3/25/17.
 */
class QualityReportModel {

    private String submissionTime;
    private long reportNumber;
    private double lat;
    private double lon;
    private double virusPPM;
    private double contaminantPPM;
    private String waterCondition;
    private String reporterName;

    /**
     * Create new quality report
     * @param lat latitude of water source
     * @param lon longitude of water source
     * @param condition condition of water source
     * @param virusPPM PPM of virus
     * @param contaminantPPM PPM of contaminants
     * @param name name of who created report
     * @param subTime submission time
     */
    public QualityReportModel(double lat, double lon, String condition, double virusPPM,
                                double contaminantPPM, String name, String subTime) {
        submissionTime = subTime;
        this.lat = lat;
        this.lon = lon;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        waterCondition = condition;
        reporterName = name;
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("num_quality_reports")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        long numReports = (long) snapshot.getValue();
                        reportNumber = numReports + 1;
                        mDatabase.child("num_quality_reports").setValue(reportNumber);
                        addToDatabase();
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.d("error", "error");
                    }
                });

    }

    /**
     * No arg default constructor for a quality report.
     */
    public QualityReportModel() {

    }

    private void addToDatabase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("quality_reports").child(reportNumber + "").setValue(this);
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
    public long getReportNumber() {
        return reportNumber;
    }

    /**
     * Get water condition.
     * @return Water condition.
     */
    public String getWaterCondition() {
        return waterCondition;
    }

    /**
     * Get virus PPM.
     * @return virus PPM.
     */
    public double getVirusPPM() {
        return virusPPM;
    }

    /**
     * Get contaminant PPM.
     * @return contaminant PPM.
     */
    public double getContaminantPPM() {
        return contaminantPPM;
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
     * @return Quality Report info.
     */
    public String toString() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(submissionTime));
        String actualDate = formatter.format(calendar.getTime());
        return String.format(Locale.ENGLISH,
                "Created by: %s. Latitude: %f. Longitude: %f. Condition: %s. "
                + "VirusPPM: %f. ContaminantPPM: %f. Date Received: %s. Report Number: %d",
                reporterName, lat, lon, waterCondition, virusPPM, contaminantPPM,
                                                actualDate, reportNumber);
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
