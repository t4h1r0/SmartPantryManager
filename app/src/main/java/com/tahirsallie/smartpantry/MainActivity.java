package com.tahirsallie.smartpantry;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tahirsallie.smartpantry.ui.PantryFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My Pantry");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PantryFragment())
                    .commit();
        }
    }
}