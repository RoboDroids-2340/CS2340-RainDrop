package edu.gatech.robodroids.raindrop;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
public class ViewQualityReports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quality_reports);
        final LinearLayout reports = (LinearLayout) findViewById(R.id.scrollview_list2);
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
                        TextView info = new TextView(ctx);
                        info.setText(qualityReport.toString());
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
