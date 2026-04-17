package com.panthydev.m2batteryapp;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.panthydev.m2batteryapp.databinding.ActivityBaseBinding;
import com.panthydev.m2batteryapp.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    ActivityBaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //sets the starting activity fx R.layout.activity_base
        replaceFragment(new HomeFragment()); //if binding.getRoot(). Starts on this fragment

        //wat is that
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        //add bottom navigation menu things
        binding.menuBar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_butt) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.info_butt) {
                replaceFragment(new InfoFragment());
            } else if (itemId == R.id.settings_butt) {
                replaceFragment(new SettingsFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_manager,fragment);
        fragmentTransaction.commit();
    }
}