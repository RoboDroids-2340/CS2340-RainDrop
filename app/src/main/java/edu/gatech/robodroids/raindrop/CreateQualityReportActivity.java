package edu.gatech.robodroids.raindrop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created By: RoboDroids
 */
public class CreateQualityReportActivity extends AppCompatActivity {

    private UserModel user;
    private EditText mLat;
    private EditText mLon;
    private EditText virusPPM;
    private EditText contaminantPPM;
    private Spinner conditionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocationRequest mLocationRequest;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quality_report);
        user = getIntent().getParcelableExtra("user");

        conditionSpinner = (Spinner) findViewById(R.id.condition_spinner);
        String[] waterConditions = new String[]{"Safe", "Treatable", "Unsafe"};
        ArrayAdapter<String> conditionAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, waterConditions);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);

        virusPPM = (EditText) findViewById(R.id.virus_ppm);
        contaminantPPM = (EditText) findViewById(R.id.cont_ppm);

        Button submit = (Button) findViewById(R.id.submit_report);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        mLat = (EditText) findViewById(R.id.lat);
        mLon = (EditText) findViewById(R.id.lon);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Creates a new quality report.
     */
    private void submit() {
        try {
            if (conditionSpinner.getSelectedItem() == null) {
                throw new Exception();
            }
            QualityReportModel report = new QualityReportModel(
                    Double.parseDouble(mLat.getText().toString()),
                    Double.parseDouble(mLon.getText().toString()),
                    conditionSpinner.getSelectedItem().toString(),
                    Double.parseDouble(virusPPM.getText().toString()),
                    Double.parseDouble(contaminantPPM.getText().toString()),
                    user.name,
                    System.currentTimeMillis()+"");

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("quality_reports").child(report.getReportNumber()+"").setValue(report);
            Toast.makeText(getApplicationContext(),
                    "Successfully created a report with report number: " + report.getReportNumber(),
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, activity_application_main.class);
            intent.putExtra("user", user);
            intent.putExtra("userid", user.userid);
            startActivity(intent);
        } catch(NumberFormatException e) {
            Toast.makeText(getApplicationContext(),
                    "You didn't fill out the report correctly!",
                    Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(),
                    "You need to finish filling out the report!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
