package com.panthydev.m2batteryapp;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AccordionMenu extends AppCompatActivity
{
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_info);

//        changeView(findViewById(R.id.arrow_button), findViewById(R.id.hidden_view), findViewById(R.id.base_cardview));
        // Each cardview menu needs to have individual ids, which are then called here to define what buttons and cardview
        // correspond to each other.

    }
    static void changeView(ImageButton arrow, LinearLayout hiddenView, CardView cardView)
    {
        arrow.setOnClickListener(view ->
        {
            // If the CardView is already expanded, set its visibility
            // to gone and change the arrow_right icon to arrow_down icon.
            if (hiddenView.getVisibility() == View.VISIBLE)
            {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.outline_arrow_circle_right_24);
            }

            // If the CardView is not expanded, set its visibility to
            // visible and change the arrow_down to arrow_right icon.
            else
            {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.outline_arrow_circle_down_24);
            }
        });
    }
}
