package com.panthydev.m2batteryapp;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        for (int i = 0; i <= 2; i++) { // i < number of accordions (starts from 0).
            int arrow = getResources().getIdentifier("arrow_button_s_" + i, "id", getPackageName());
            int hiddenView = getResources().getIdentifier("hidden_view_s_" + i, "id", getPackageName());
            int baseCard = getResources().getIdentifier("base_cardview_s_" + i, "id", getPackageName());

            AccordionMenu.changeView(findViewById(arrow), findViewById(hiddenView), findViewById(baseCard));
            // Each cardview menu needs to have individual ids, which are then called here to define what buttons and cardview
            // correspond to each other.
        }


        BottomNavigationView topNavigationView = findViewById(R.id.top_menu_bar);
        topNavigationView.setSelectedItemId(R.id.app_settings_butt);

        topNavigationView.setOnItemSelectedListener(item -> {
            int itemId= item.getItemId();
            if (itemId == R.id.notif_settings_butt)
            {
                Intent intent = new Intent(getApplicationContext(), NotifSettingsActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            } else if (itemId == R.id.app_settings_butt)
            {
                return true;
            }
            return false;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bar);
        bottomNavigationView.setSelectedItemId(R.id.settings_butt);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_butt)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            } else if (itemId == R.id.info_butt)
            {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            } else if (itemId == R.id.settings_butt)
            {
                return true;
            }
            return false;
        });
    }
}