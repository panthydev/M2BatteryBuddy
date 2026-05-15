package com.panthydev.m2batteryapp;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

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
import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.data.DataObjects.App;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphActivity extends AppCompatActivity
{

    HorizontalBarChart horizontalBarChart;
    BarDataSet barDataSet;

    TextView topApp1;
    TextView topApp2;
    TextView topApp3;
    TextView topApp4;
    TextView topApp5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        horizontalBarChart = findViewById(R.id.idBarChart);

        topApp1 = findViewById(R.id.TopApp1);
        topApp2 = findViewById(R.id.TopApp2);
        topApp3 = findViewById(R.id.TopApp3);
        topApp4 = findViewById(R.id.TopApp4);
        topApp5 = findViewById(R.id.TopApp5);


        //Arraylist for the data of each app
        ArrayList<BarEntry> barEntries = getBarEntries();

        //Arraylist for the names of apps
        ArrayList<String> labelEntries = getStrings();

        barDataSet = new BarDataSet(getBarEntries(),"");
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
        description.setText("Comparable Discharge");
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


        // Sets app names for each bar
        horizontalBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getStrings()));
        horizontalBarChart.getXAxis().setGranularity(0.2f);
        horizontalBarChart.getXAxis().setGranularityEnabled(true);
        horizontalBarChart.setVisibleXRangeMaximum(5);

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


        // Invalidate the chart to refresh and makes sure it isn't clickable
        horizontalBarChart.invalidate();
        horizontalBarChart.setClickable(false);
        horizontalBarChart.setTouchEnabled(false);

        CalculateSumOfApps();

    }

    @NonNull
    private static ArrayList<String> getStrings() {

        ArrayList<String> labelEntries = new ArrayList<>();

        labelEntries.add(""); //App Name5
        labelEntries.add(""); //App Name4
        labelEntries.add(""); //App Name3
        labelEntries.add(""); //App Name2
        labelEntries.add(""); //App Name1
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

    //Dette er til at gå tilbage til main activity.
    public void Back(View v)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
        finish();
    }

    public void CalculateSumOfApps () {
        DataManager.GetAppDataAsync(this, 72, new Callback<Map<String, ArrayList<App>>>() {
            @Override
            public void OnResult(Map<String, ArrayList<App>> Result) {
                Set<String> ListOfKeys = Result.keySet();
                float SumDischarge = 0;
                float appAverageDischarge = 0;
                LinkedHashMap<String, Float> allAppsDischargeAverage = new LinkedHashMap<>();

                for (String Key: ListOfKeys) {
                   var AppList = Result.get(Key);

                   for(App app : AppList) {
                       SumDischarge += app.getAppDischarge();
                   }

                   appAverageDischarge = SumDischarge / AppList.size();

                    allAppsDischargeAverage.put(Key, appAverageDischarge);
                }

                List<Map.Entry<String, Float>> list = new ArrayList<>(allAppsDischargeAverage.entrySet());
                list.sort(Map.Entry.comparingByValue());



                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        ArrayList<String> labelEntries = new ArrayList<>();
                        TextView[] appLabelEntries = new TextView[5];
                        appLabelEntries[0]=topApp1;
                        appLabelEntries[1]=topApp2;
                        appLabelEntries[2]=topApp3;
                        appLabelEntries[3]=topApp4;
                        appLabelEntries[4]=topApp5;
                        int i = 0;

                        for(Map.Entry entry: list){
                            appLabelEntries[i].setText(entry.getKey().toString());
                            i++;
                        }



                    }
                });

            }
        });
    }
}