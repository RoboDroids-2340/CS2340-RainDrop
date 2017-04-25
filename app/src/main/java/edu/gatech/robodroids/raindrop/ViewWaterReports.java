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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Created By: RoboDroids
 */
public class ViewWaterReports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_water_reports);
        final LinearLayout reports = (LinearLayout) findViewById(R.id.scrollview_list);
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener usersListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("bzm","attaching listener");
                Context ctx = getApplicationContext();
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.child("water_reports").getChildren()) {
                    WaterReportModel waterReport = snapshot.getValue(WaterReportModel.class);
                    if (waterReport != null) {
                        Log.d("bzm","adding");
                        TextView info = new TextView(ctx);
                        info.setText(waterReport.toString());
                        info.setTextColor(Color.BLACK);
                        reports.addView(info, i);
                        i++;
                    }
                }
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabase.addValueEventListener(usersListener);
    }
}

