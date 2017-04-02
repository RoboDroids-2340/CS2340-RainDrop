package edu.gatech.robodroids.raindrop;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jjacob on 4/1/17.
 */

public class ViewHistoricalReports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_historical_reports);
        final Button generate_graph = (Button) findViewById(R.id.generate_graph);
        generate_graph.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                generate();
            }
        });
        populateSpinner();
    }

    private void generate() {
        final List<DataPoint> pointList = new ArrayList<>();
        final TextView yearString = (TextView) findViewById(R.id.year);
        if (yearString.getText() == null) {
        //TODO TOAST
            int x = 0;
        } else {
            final DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            ValueEventListener usersListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final Spinner locationSpinner = (Spinner) findViewById(R.id.location_spinner);
                    GraphView graph = (GraphView) findViewById(R.id.graph);
                    graph.removeAllSeries();
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    double[] monthTotal = new double[12];
                    int[] monthCounter = new int[12];
                    for (DataSnapshot snapshot : dataSnapshot.child("quality_reports").getChildren()) {
                        QualityReportModel qualityReport = snapshot.getValue(QualityReportModel.class);
                        if (qualityReport != null) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(Long.parseLong(qualityReport.getSubmissionTime()));
                            String actualDate = formatter.format(calendar.getTime());
                            int diff = Integer.parseInt(yearFromDate(actualDate)) - Integer.parseInt(yearString.getText().toString());
                            String coord = "Lat: " + Double.toString(qualityReport.getLat());
                            coord +=  " Lon: " + Double.toString(qualityReport.getLon());
                            if (diff == 0 && locationSpinner.getSelectedItem().toString().equals(coord)) {
                                int month = calendar.get(Calendar.MONTH);
                                monthTotal[month - 1] += getSelectedPPM(qualityReport);
                                monthCounter[month - 1] += 1;
                            }
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
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                    graph.addSeries(series);
                    graph.setTitle("History Report for " + yearString.getText());
                    graph.getGridLabelRenderer().setVerticalAxisTitle(getSelectedPPMString());
                    graph.getGridLabelRenderer().setHorizontalAxisTitle("Month");
                    graph.getGridLabelRenderer().setNumHorizontalLabels(12); // only 4 because of the space
                    graph.getGridLabelRenderer().setPadding(1);
                    //series.setShape(PointsGraphSeries.Shape.POINT);
                    mDatabase.removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
            mDatabase.addValueEventListener(usersListener);
        }

    }

    private double getSelectedPPM(QualityReportModel qualityReport) {
        Spinner dataSpinner = (Spinner) findViewById(R.id.data_spinner);
        if (dataSpinner.getSelectedItem().toString().equals("Virus")) {
            return qualityReport.getVirusPPM();
        }
        return qualityReport.getContaminantPPM();
    }

    private String getSelectedPPMString() {
        Spinner dataSpinner = (Spinner) findViewById(R.id.data_spinner);
        if (dataSpinner.getSelectedItem().toString().equals("Virus")) {
            return "Virus PPM";
        }
        return "Contaminent PPM";
    }

    private String yearFromDate(String dateString) {
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
    private void populateSpinner() {
        final Spinner locationSpinner = (Spinner) findViewById(R.id.location_spinner);
        final List<String> spinnerArray =  new ArrayList<String>();
        final Spinner dataSpinner = (Spinner) findViewById(R.id.data_spinner);
        ArrayAdapter<String>  dataAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item, new String[] {"Contaminent", "Virus"}
        );
        dataSpinner.setAdapter(dataAdapter);
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener usersListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Context ctx = getApplicationContext();
                int i = 0;
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
                        getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);

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
