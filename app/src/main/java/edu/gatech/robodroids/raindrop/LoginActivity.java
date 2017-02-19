package edu.gatech.robodroids.raindrop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

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
            public void onClick(View v) {
                checkLogin();
            }
        });
        final Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
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
        TextView username = (TextView) findViewById(R.id.email);
        TextView password = (TextView) findViewById(R.id.password);
        //Log.d("app, user", String.valueOf(username.getText()));
        //Log.d("app, pass", String.valueOf(password.getText()));
        if (String.valueOf(username.getText()).equals("user") && String.valueOf(password.getText()).equals("pass")) {
            Intent intent = new Intent(this, activity_application_main.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid login!", Toast.LENGTH_SHORT).show();
        }
    }

}

