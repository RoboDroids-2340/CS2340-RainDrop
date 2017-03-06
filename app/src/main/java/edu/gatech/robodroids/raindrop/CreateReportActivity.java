package edu.gatech.robodroids.raindrop;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class CreateReportActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private UserModel user;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private AddressResultReceiver mReceiver;
    private EditText mAddress;
    private LocationRequest mLocationRequest;
    private Spinner typeSpinner;
    private Spinner conditionSpinner;

    /**
     * Inner class to receive results from address service.
     */
    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 1) {
                mAddress.setText(resultData.getString("result"));
            } else {
                mAddress.setText("No address found.");
                Log.d("AddressError: ", resultData.getString("msg"));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        user = getIntent().getParcelableExtra("user");

        typeSpinner = (Spinner) findViewById(R.id.type_spinner);
        String[] waterTypes = new String[]{"Bottled", "Well", "Stream", "Lake", "Spring", "Other"};
        ArrayAdapter<String> typeAdapter =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, waterTypes);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        conditionSpinner = (Spinner) findViewById(R.id.condition_spinner);
        String[] waterConditions = new String[]{"Waste", "Treatable-Clear", "Treatable-Muddy", "Potable"};
        ArrayAdapter<String> conditionAdapter =
                new ArrayAdapter(this,android.R.layout.simple_spinner_item, waterConditions);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);

        Button submit = (Button) findViewById(R.id.submit_report);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        mAddress = (EditText) findViewById(R.id.address_field);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mReceiver = new AddressResultReceiver(new Handler());
    }

    /**
     * Creates a new water report.
     * TODO send water report to database.
     */
    private void submit() {
        if (typeSpinner.getSelectedItem() != null && conditionSpinner.getSelectedItem() != null) {
            WaterReportModel report = new WaterReportModel(
                    mAddress.getText().toString(),
                    typeSpinner.getSelectedItem().toString(),
                    conditionSpinner.getSelectedItem().toString(),
                    user.name);

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("water_reports").child(report.getReportNumber()+"").setValue(report);
            Toast.makeText(getApplicationContext(),
                    "Successfully created a report with report number: " + report.getReportNumber(),
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, activity_application_main.class);
            intent.putExtra("user", user);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "You need to finish filling out the report!",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("Location Service", "Successful connection.");
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } else {
            mAddress.setText("Location permissions not enabled");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mLastLocation != null) {
            startIntentService();
        } else {
            Log.d("LocationError:", "No location found");
            mAddress.setText("No location found");
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.d("Location Service", "Suspended connection.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d("Location Service", "Failed connection.");
    }

    /**
     * Starts the location to address service.
     */
    protected void startIntentService() {
        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("location", mLastLocation);
        startService(intent);
    }

}