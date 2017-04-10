package edu.gatech.robodroids.raindrop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
/**
 * Created By: RoboDroids
 */
public class ViewHistoricalReports extends AppCompatActivity {
    private final int MONTHS = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_historical_reports);
        final Button generate_graph = (Button) findViewById(R.id.generate_graph);
        generate_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generate();
            }
        });
        populateSpinner();
    }

    /**
     * Generates historical report graph.
     */
    private void generate() {
        final List<DataPoint> pointList = new ArrayList<>();
        final TextView yearString = (TextView) findViewById(R.id.year);
        if (yearString.getText() != null) {
            final DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            ValueEventListener usersListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final Spinner locationSpinner = (Spinner) findViewById(R.id.location_spinner);
                    GraphView graph = (GraphView) findViewById(R.id.graph);
                    graph.removeAllSeries();
                    double[] monthTotal = new double[MONTHS];
                    int[] monthCounter = new int[MONTHS];
                    for (DataSnapshot snapshot : dataSnapshot.child("quality_reports")
                                        .getChildren()) {
                        QualityReportModel qualityReport = snapshot.getValue(
                                                                    QualityReportModel.class);
                        if (qualityReport != null) {
                            String query = locationSpinner.getSelectedItem()
                                    .toString();
                            updateTotalAndCounter(qualityReport, monthTotal, monthCounter, query);
                        }
                    }
                    for (int i = 0; i < monthTotal.length; i++) {
                        double y = 0;
                        if (monthCounter[i] != 0) {
                            y = monthTotal[i] / monthCounter[i];
                        }
                        pointList.add(new DataPoint(i + 1, y));

                    }

                    DataPoint[] dataPoints = pointList.toArray(new DataPoint[pointList.size()]);
                    Series<DataPoint> series = new LineGraphSeries<>(dataPoints);
                    graph.addSeries(series);
                    graph.setTitle("History Report for " + yearString.getText());
                    graph.getGridLabelRenderer().setVerticalAxisTitle(getSelectedPPMString());
                    graph.getGridLabelRenderer().setHorizontalAxisTitle("Month");
                    graph.getGridLabelRenderer().setNumHorizontalLabels(MONTHS);
                    graph.getGridLabelRenderer().setPadding(1);
                    //series.setShape(PointsGraphSeries.Shape.POINT);
                    mDatabase.removeEventListener(this);
                }

                private void updateTotalAndCounter(QualityReportModel qualityReport,
                                                   double[] monthTotal,
                                                   int[] monthCounter, String query) {
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS"
                            ,Locale.US);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long
                            .parseLong(qualityReport.getSubmissionTime()));
                    String actualDate = formatter.format(calendar.getTime());
                    int diff = Integer.parseInt(yearFromDate(actualDate))
                            - Integer.parseInt(yearString.getText().toString());
                    String coord = "Lat: " + Double.toString(qualityReport.getLat());
                    coord +=  " Lon: " + Double.toString(qualityReport.getLon());
                    if ((diff == 0) && (query.equals(coord))) {
                        int month = calendar.get(Calendar.MONTH);
                        monthTotal[month - 1] += getSelectedPPM(qualityReport);
                        monthCounter[month - 1] += 1;
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
            mDatabase.addValueEventListener(usersListener);
        }

    }

    /**
     * Get's the virus or contaminant PPM based on whichever is selected.
     * @param qualityReport The quality report the PPM is coming from.
     * @return The PPM.
     */
    private double getSelectedPPM(QualityReportModel qualityReport) {
        Spinner dataSpinner = (Spinner) findViewById(R.id.data_spinner);
        if ("Virus".equals(dataSpinner.getSelectedItem().toString())) {
            return qualityReport.getVirusPPM();
        }
        return qualityReport.getContaminantPPM();
    }

    /**
     * Returns virus or contaminant PPM as a string.
     * @return PPM as a string.
     */
    private String getSelectedPPMString() {
        Spinner dataSpinner = (Spinner) findViewById(R.id.data_spinner);
        if ("Virus".equals(dataSpinner.getSelectedItem().toString())) {
            return "Virus PPM";
        }
        return "Contaminant PPM";
    }

    /**
     * Takes the date string and returns the year.
     * @param dateString Date of report.
     * @return Year of report.
     */
    private String yearFromDate(CharSequence dateString) {
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

    /**
     * Spinners for location selection and PPM selector.
     */
    private void populateSpinner() {
        final Spinner locationSpinner = (Spinner) findViewById(R.id.location_spinner);
        final List<String> spinnerArray =  new ArrayList<>();
        final Spinner dataSpinner = (Spinner) findViewById(R.id.data_spinner);
        SpinnerAdapter dataAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item, new String[] {"Contaminant", "Virus"}
        );
        dataSpinner.setAdapter(dataAdapter);
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener usersListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("quality_reports").getChildren()) {
                    QualityReportModel qualityReport = snapshot.getValue(QualityReportModel.class);
                    if (qualityReport != null) {
                        String coord = "Lat: " + Double.toString(qualityReport.getLat());
                        coord +=  " Lon: " + Double.toString(qualityReport.getLon());
                        if (!spinnerArray.contains(coord)) {
                            spinnerArray.add(coord);
                        }
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getApplicationContext(), android.R.layout
                                                        .simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                locationSpinner.setAdapter(adapter);
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabase.addValueEventListener(usersListener);
    }

}
