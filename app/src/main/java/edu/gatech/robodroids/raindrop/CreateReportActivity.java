package edu.gatech.robodroids.raindrop;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Telephony;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class CreateReportActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private UserModel user;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private AddressResultReceiver mReceiver;
    private String mAddress;

    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 1) {
                mAddress = resultData.getString("result");
            } else {
                mAddress = "No address found.";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        user = getIntent().getParcelableExtra("user");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                startIntentService();
            }
        } else {

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

    protected void startIntentService() {
        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("location", mLastLocation);
        startService(intent);
    }

}