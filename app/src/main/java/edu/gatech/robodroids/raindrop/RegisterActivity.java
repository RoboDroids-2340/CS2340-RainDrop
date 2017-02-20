package edu.gatech.robodroids.raindrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Button registerButton = (Button) findViewById(R.id.email_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        TextView name = (TextView) findViewById(R.id.email);
        TextView password = (TextView) findViewById(R.id.password);
        TextView id = (TextView) findViewById(R.id.userid);
        UserModel user = new UserModel(name.getText().toString(), id.getText().toString(),
                password.getText().toString());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //TODO allow for attempted duplicates
        mDatabase.child("users").child(id.getText().toString()).setValue(user);
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }


}

