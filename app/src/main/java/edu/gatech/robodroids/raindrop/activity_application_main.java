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
}
