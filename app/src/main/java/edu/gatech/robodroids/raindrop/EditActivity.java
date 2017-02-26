package edu.gatech.robodroids.raindrop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jjacob on 2/24/17.
 */

public class EditActivity extends AppCompatActivity {

    TextView emailText;
    TextView passwordText;
    TextView nameText;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");
    String userid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        emailText = (TextView) findViewById(R.id.current_email);
        passwordText = (TextView) findViewById(R.id.current_password);
        nameText = (TextView) findViewById(R.id.current_name);
        userid = getIntent().getStringExtra("userid");

        final Button confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirm();
            }
        });
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener usersListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.child("users").child(
                        userid).getValue(UserModel.class);
                nameText.setText("Name: " + user.name);
                emailText.setText( "Email: " + user.userid);
                passwordText.setText("Password:  " + user.pass);
                mDatabase.removeEventListener(this);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabase.addValueEventListener(usersListener);


    }

    private void confirm() {
        final TextView name = (TextView) findViewById(R.id.name);
        final TextView password = (TextView) findViewById(R.id.password);
        userid = getIntent().getStringExtra("userid");
        String newuserid = userid;
        final Map<String, Object> userUpdates = new HashMap<>();
        if (!name.getText().toString().equals("")) {
            userUpdates.put("name", name.getText().toString());
        }
        //if (!email.getText().toString().equals("")) {
          //  userUpdates.put("userid", email.getText().toString());
           // newuserid = email.getText().toString();
        //}
        if (!password.getText().toString().equals("")) {
            userUpdates.put("pass", password.getText().toString());
        }
        ref.child(userid).updateChildren(userUpdates);
        userid = newuserid;
        Context ctx = getApplicationContext();
        Intent intent = new Intent(ctx, activity_application_main.class);
        intent.putExtra("userid", userid);
        startActivity(intent);
    }
}
