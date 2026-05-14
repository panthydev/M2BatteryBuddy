package com.panthydev.m2batteryapp;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    HorizontalBarChart horizontalBarChart;
    BarDataSet barDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        horizontalBarChart = findViewById(R.id.idBarChart);


        //Arraylist for the data of each app
        ArrayList<BarEntry> barEntries = getBarEntries();

        //Arraylist for the names of apps
        ArrayList<String> labelEntries = getStrings();

        barDataSet = new BarDataSet(getBarEntries(), "");
        BarData data = new BarData(barDataSet);
        horizontalBarChart.setData(data);


        // Set different colors for each bar
        int[] colors = {getResources().getColor(R.color.bar_red),
                getResources().getColor(R.color.bar_orange),
                getResources().getColor(R.color.bar_ora_yell),
                getResources().getColor(R.color.bar_yellow),
                getResources().getColor(R.color.bar_green)};
        barDataSet.setColors(colors);

        // set the text color within the graph for large numbers
        barDataSet.setValueTextColor(getResources().getColor(R.color.text_light_gray));

        //Set animation for the bars and make the graph and text fit so it is readable
        horizontalBarChart.animateY(1000);
        horizontalBarChart.setFitBars(true);

        barDataSet.setValueTextSize(15f);


        //The little description in the corner + color and shape of background
        Description description = new Description();
        description.setText("mAh drainage");
        description.setTextColor(getResources().getColor(R.color.text_light_gray));
        horizontalBarChart.setDescription(description);
        horizontalBarChart.getXAxis().setLabelCount(5);
        barDataSet.setValueTextColor(getResources().getColor(R.color.text_light_gray));
        horizontalBarChart.setExtraRightOffset(5f);
        horizontalBarChart.setBackground(getDrawable(R.drawable.shapeofcontainerlight));

        Legend legend = horizontalBarChart.getLegend();
        legend.setEnabled(true);
        Legend colorShape = horizontalBarChart.getLegend();
        colorShape.setForm(Legend.LegendForm.NONE);


        // Set bar width
        data.setBarWidth(0.9f);

        // X-Axis Data for the visuals
        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1);
        xAxis.setDrawLabels(true);
        xAxis.setXOffset(1);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawGridLines(true);
        //This sets the color for axis (it sucks ass), Philipp Jahoda if i ever meet you, i'm losing a boot up your ass(respectfully)
        xAxis.setTextColor(getResources().getColor(R.color.text_light_gray));
        YAxis topAxis = horizontalBarChart.getAxisLeft();
        topAxis.setTextColor(getResources().getColor(R.color.text_light_gray));
        YAxis botAxis = horizontalBarChart.getAxisRight();
        botAxis.setTextColor(getResources().getColor(R.color.text_light_gray));


        // Sets app names for each bar
        horizontalBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getStrings()));
        horizontalBarChart.getXAxis().setGranularity(0.2f);
        horizontalBarChart.getXAxis().setGranularityEnabled(true);
        horizontalBarChart.setVisibleXRangeMaximum(5);


        // Invalidate the chart to refresh and makes sure it isn't clickable
        horizontalBarChart.invalidate();
        horizontalBarChart.setClickable(false);
        horizontalBarChart.setTouchEnabled(false);

    }

    @NonNull
    private static ArrayList<String> getStrings() {

        ArrayList<String> labelEntries = new ArrayList<>();

        labelEntries.add("App Name5");
        labelEntries.add("App Name4");
        labelEntries.add("App Name3");
        labelEntries.add("App Name2");
        labelEntries.add("App Name1");
        return labelEntries;
        //Vi skal have dataen rangeret og navnene på den skal ind her
    }

    @NonNull
    private static ArrayList<BarEntry> getBarEntries() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(4, 10));
        barEntries.add(new BarEntry(3, 8));
        barEntries.add(new BarEntry(2, 6));
        barEntries.add(new BarEntry(1, 4));
        barEntries.add(new BarEntry(0, 2));
        return barEntries;
        //Her skal vi have dataen hvor y er den værdi der skal være dynamisk x4 er den øverste på grafen
    }
//
//    public void GraphChanger() {
//        DataManager.GetAppDataAsync(this, new Callback<DataPack<App>>()
//        {
//            String[] AppNames = Result.datalist.appName;
//            int[] AppDischarge = Result.datalist.appDischarge;
//            @Override
//            public void OnResult(DataPack<App> Result)
//            {
//
//            }
//        });
//    }


    //Dette er til at gå tilbage til main activity.
    public void Back(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
        finish();
    }
}