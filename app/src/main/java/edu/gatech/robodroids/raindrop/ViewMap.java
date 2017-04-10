package edu.gatech.robodroids.raindrop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Created By: RoboDroids
 */
public class ViewMap extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(this);
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener usersListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshots =
                        dataSnapshot.child("water_reports").getChildren();
                for (DataSnapshot snapshot : snapshots) {
                    WaterReportModel model = snapshot.getValue(WaterReportModel.class);
                    if (model != null) {
                        LatLng markerPos = new LatLng(model.getLat(), model.getLon());
                        googleMap.addMarker(new MarkerOptions().position(markerPos)
                                .title("Water Report").snippet(model.toString()));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerPos));
                    }
                }
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabase.addValueEventListener(usersListener);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Toast.makeText(this, marker.getSnippet(), Toast.LENGTH_SHORT).show();
        marker.showInfoWindow();
        return false;
    }
}
