package com.panthydev.m2batteryapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.renderer.BarChartRenderer;

import java.util.ArrayList;
import java.util.Objects;

public class graphActivity extends AppCompatActivity
{

    HorizontalBarChart horizontalBarChart;
    BarDataSet barDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        horizontalBarChart = findViewById(R.id.idBarChart);


        //Arraylist for the data of each app
        ArrayList<BarEntry> barEntries = getBarEntries();

        //Arraylist for the names of apps
        ArrayList<String> labelEntries = getStrings();

        barDataSet = new BarDataSet(getBarEntries(),"");
        BarData data = new BarData(barDataSet);
        horizontalBarChart.setData(data);


        // Set different colors for each bar
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE,
                Color.YELLOW, Color.CYAN};
        barDataSet.setColors(colors);

        //Set animation for the bars and make the graph and text fir so it is readable
        horizontalBarChart.animateY(1000);
        horizontalBarChart.setFitBars(true);

        barDataSet.setValueTextSize(15f);


        //The little description in the corner
        Description description = new Description();
        description.setText("MapH Since last charge");
        horizontalBarChart.setDescription(description);
        horizontalBarChart.getXAxis().setLabelCount(5);
        horizontalBarChart.setExtraRightOffset(5f);

        Legend legend = horizontalBarChart.getLegend();
        legend.setEnabled(true);

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
    }

    @NonNull
    private static ArrayList<BarEntry> getBarEntries() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(4, 100));
        barEntries.add(new BarEntry(3, 8));
        barEntries.add(new BarEntry(2, 6));
        barEntries.add(new BarEntry(1, 4));
        barEntries.add(new BarEntry(0, 2));
        return barEntries;
    }

}