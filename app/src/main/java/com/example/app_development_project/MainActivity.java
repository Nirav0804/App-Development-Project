package com.example.app_development_project;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Directing the user to the login activity
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}