package com.tahirsallie.smartpantry;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tahirsallie.smartpantry.ui.PantryFragment;
import com.tahirsallie.smartpantry.ui.SuggestedRecipesFragment;
import com.tahirsallie.smartpantry.ui.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.bottom_nav);

        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_pantry) {
                showFragment(new PantryFragment(), "My Pantry");
                return true;
            } else if (id == R.id.nav_suggested) {
                showFragment(new SuggestedRecipesFragment(), "Suggested Recipes");
                return true;
            } else if (id == R.id.nav_settings) {
                showFragment(new SettingsFragment(), "Settings");
                return true;
            }
            return false;
        });

        // First launch only: select the Pantry tab (this also triggers the listener above)
        if (savedInstanceState == null) {
            nav.setSelectedItemId(R.id.nav_pantry);
        }
    }

    private void showFragment(Fragment fragment, String title) {
        setTitle(title);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}