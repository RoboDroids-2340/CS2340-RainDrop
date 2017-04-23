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
        QualityReportModel report = ValidateQualityInput.inputToQualityReport(
                mLat.getText().toString(),
                mLon.getText().toString(),
                conditionSpinner.getSelectedItem(),
                virusPPM.getText().toString(),
                contaminantPPM.getText().toString(),
                user.getName(),
                System.currentTimeMillis()+"");
        if (report == null) {
            Toast.makeText(getApplicationContext(),
                    "You didn't fill out the report correctly!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Successfully created a report", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, activity_application_main.class);
            intent.putExtra("user", user);
            intent.putExtra("userid", user.getUserid());
            startActivity(intent);
        }
    }


}
