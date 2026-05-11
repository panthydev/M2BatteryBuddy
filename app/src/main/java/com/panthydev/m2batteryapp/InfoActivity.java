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
import com.panthydev.m2batteryapp.AccordionMenu;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
//                (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        for (int i = 1; i <= 8; i++) { // i < number of accordions.
            int arrow = getResources().getIdentifier("arrow_button_" + i, "id", getPackageName());
            int hiddenView = getResources().getIdentifier("hidden_view_" + i, "id", getPackageName());
            int baseCard = getResources().getIdentifier("base_cardview_" + i, "id", getPackageName());

            AccordionMenu.changeView(findViewById(arrow), findViewById(hiddenView), findViewById(baseCard));
            // Each cardview menu needs to have individual ids, which are then called here to define what buttons and cardview
            // correspond to each other.
        }



        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bar);
        bottomNavigationView.setSelectedItemId(R.id.info_butt);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_butt)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            } else if (itemId == R.id.info_butt)
            {
                return true;
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
    }
}