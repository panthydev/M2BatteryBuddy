package com.panthydev.m2batteryapp;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

import android.content.Intent;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
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

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ActivityBaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); //hvorfor er den her linjer her enlig?
        setContentView(R.layout.activity_main);

        //wat is this. No "main" id in R, so makes error.
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bar);
        bottomNavigationView.setSelectedItemId(R.id.home_butt);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_butt)
            {
                return true;
            } else if (itemId == R.id.info_butt)
            {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();                return true;
            } else if (itemId == R.id.settings_butt)
            {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            }
            return false;
        });

        percentageGraph percentageGraph = new percentageGraph(this);

        ConstraintLayout bar = findViewById(R.id.barGraphContainer);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "DIller", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), graphActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
            }
        });

    }
}