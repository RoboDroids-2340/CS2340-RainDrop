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
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    int i = 0;
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
                                pointList.add(new DataPoint(calendar.getTime(), qualityReport.getContaminantPPM()));
                            }
                        }
                    }
                    DataPoint[] dataPoints = pointList.toArray(new DataPoint[pointList.size()]);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                    graph.addSeries(series);
                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
                    graph.getGridLabelRenderer().setNumHorizontalLabels(pointList.size()); // only 4 because of the space
                    graph.getGridLabelRenderer().setHumanRounding(false);
                    mDatabase.removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
            mDatabase.addValueEventListener(usersListener);
        }

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
    private void generate_graph() {
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        graph.addSeries(series);
    }

}
