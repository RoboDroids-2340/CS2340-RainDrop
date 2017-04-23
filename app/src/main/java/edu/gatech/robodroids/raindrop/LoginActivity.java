package edu.gatech.robodroids.raindrop;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button loginButton = (Button) findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        final Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    /**
     * Stops the login activity and opens the welcome screen activity.
     */
    private void cancel() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    /**
     * Checks if the login provided is registered.
     */
    private void checkLogin() {
        final TextView username = (TextView) findViewById(R.id.email);
        final TextView password = (TextView) findViewById(R.id.password);
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener usersListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Context ctx = getApplicationContext();
                UserModel user = dataSnapshot.child("users").child(
                        username.getText().toString().replaceAll("\\.", ","))
                                                    .getValue(UserModel.class);
                if ((user != null) && (user.getPass().equals(password.getText().toString()))) {
                    Intent intent = new Intent(ctx, activity_application_main.class);
                    intent.putExtra("user", user);
                    intent.putExtra("userid", user.getUserid());
                    startActivity(intent);
                } else {
                    Toast.makeText(ctx, "Invalid login!", Toast.LENGTH_SHORT).show();
                }
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabase.addValueEventListener(usersListener);
    }

}

