package edu.gatech.robodroids.raindrop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_application_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_main);
        final Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        });
        final Button edit_button = (Button) findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                edit();
            }
        });
        final Button report_button = (Button) findViewById(R.id.report_button);
        report_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createReport();
            }
        });
        final Button view_report_button = (Button) findViewById(R.id.buttonview);
        view_report_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewReports();
            }
        });
    }

    /**
     * Starts the welcome screen activity.
     */
    private void logout() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    /**
     *  Starts the edit profile activity.
     */
    private void edit() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("userid", getIntent().getStringExtra("userid"));
        startActivity(intent);
    }

    /**
     *  Opens the water report creator activity.
     */
    private void createReport() {
        Intent intent = new Intent(this, CreateReportActivity.class);
        intent.putExtra("user", getIntent().getParcelableExtra("user"));
        startActivity(intent);
    }

    private void viewReports() {
        Intent intent = new Intent(this, ViewWaterReports.class);
        startActivity(intent);
    }
}
