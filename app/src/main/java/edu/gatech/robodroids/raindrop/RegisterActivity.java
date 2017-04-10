package edu.gatech.robodroids.raindrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Spinner;

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
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    /**
     * Method to register a new user, uses firebase as a database to store registered users.
     */
    private void registerUser() {
        TextView name = (TextView) findViewById(R.id.email);
        TextView password = (TextView) findViewById(R.id.password);
        TextView id = (TextView) findViewById(R.id.userid);
        Spinner userTypeSpinner = (Spinner) findViewById(R.id.user_type_spinner);
        UserModel user = new UserModel(UserBuilder.buildUser(name.getText().toString(), id.getText().toString(),
                password.getText().toString(), userTypeSpinner.getSelectedItem().toString()));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.userid).setValue(user);
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }


}

