package edu.gatech.robodroids.raindrop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created By: Joshua Jacob
 */
public class activity_application_main extends AppCompatActivity {

    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_main);
        final Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        final Button edit_button = (Button) findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });
        final Button report_button = (Button) findViewById(R.id.report_button);
        report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createReport();
            }
        });
        final Button view_report_button = (Button) findViewById(R.id.buttonview);
        view_report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewReports();
            }
        });
        final Button view_map_button = (Button) findViewById(R.id.view_map_button);
        view_map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMap();
            }
        });
        final Button create_quality_report = (Button) findViewById(R.id.quality_report);
        create_quality_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQualityReport();
            }
        });
        final Button quality_report_view = (Button) findViewById(R.id.quality_report_view);
        quality_report_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewQualityReports();
            }
        });
        final Button historical_report_view = (Button) findViewById(R.id.historical_report_view);
        historical_report_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHistoricalReports();
            }
        });

        user = getIntent().getParcelableExtra("user");
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
        intent.putExtra("userId", user.userid);
        startActivity(intent);
    }

    /**
     *  Opens the water report creator activity.
     */
    private void createReport() {
        Intent intent = new Intent(this, CreateReportActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    /**
     * Opens the water report viewer activity.
     */
    private void viewReports() {
        Intent intent = new Intent(this, ViewWaterReports.class);
        startActivity(intent);
    }

    /**
     * Opens the map viewing activity.
     */
    private void viewMap() {
        Intent intent = new Intent(this, ViewMap.class);
        startActivity(intent);
    }

    /**
     * Opens quality report creator activity, only if user has proper access level.
     */
    private void createQualityReport() {
        if (checkAccessLevel(user, "User")) {
            Toast.makeText(getApplicationContext(),
                    "You do not have the proper access level for that!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, CreateQualityReportActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

    /**
     * Opens quality report viewer activity.
     */
    private void viewQualityReports() {
        Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG );
        if (!checkAccessLevel(user, "Manager")) {
            Toast.makeText(getApplicationContext(),
                    "You do not have the proper access level for that!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ViewQualityReports.class);
            startActivity(intent);
        }
    }

    /**
     * Opens historical report viewer activity.
     */
    private void viewHistoricalReports() {
        if (!checkAccessLevel(user, "Manager")) {
            Toast.makeText(getApplicationContext(),
                    "You do not have the proper access level for that!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ViewHistoricalReports.class);
            startActivity(intent);
        }
    }

    /**
     * Access level helper method
     */
    public static boolean checkAccessLevel(UserModel user, String level) {
        if (user == null || level == null) {
            throw new IllegalArgumentException("Neither user or level arguments can be null");
        }

        if (user.type.equals(level)) {
            return true;
        }
        return false;
    }
}
