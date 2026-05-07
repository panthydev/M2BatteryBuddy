package com.panthydev.m2batteryapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class percentageGraph
{

    private View viewUse3, viewUse2, viewUse1, viewOther;
    private TextView textCat1, textCat2, textCat3;
    private TextView textProc1, textProc2, textProc3, textProcOther;
    private double max = 800; //tjek om der størrelsen virker med andre phones.



    public percentageGraph(MainActivity mainActivity)
    {
        viewUse3 = mainActivity.findViewById(R.id.viewUse3);
        viewUse2 = mainActivity.findViewById(R.id.viewUse2);
        viewUse1 = mainActivity.findViewById(R.id.viewUse1);
        viewOther = mainActivity.findViewById(R.id.viewOther);

        textCat1 = mainActivity.findViewById(R.id.Category1);
        textCat2 = mainActivity.findViewById(R.id.Category2);
        textCat3 = mainActivity.findViewById(R.id.Category3);

        textProc1 = mainActivity.findViewById(R.id.procentage1);
        textProc2 = mainActivity.findViewById(R.id.procentage2);
        textProc3 = mainActivity.findViewById(R.id.procentage3);
        textProcOther = mainActivity.findViewById(R.id.procentageOther);

        //This will have to be changed to get the numbers from our data
        //intOther will end being the all the others - the total, as it is what is left
        double intUse1 = 400;
        double intUse2 = 300;
        double intUse3 = 200;
        double intOther = 100;

        //This will have to be changed as we will be getting the total(Ev) from our data
        double Ev = intUse3+intUse2+intUse1+intOther;
        double p3 = intUse3/Ev;
        double p2 = intUse2/Ev;
        double p1 = intUse1/Ev;
        double pO = intOther/Ev;

        ViewGroup.LayoutParams layoutParams3 = viewUse3.getLayoutParams();
        layoutParams3.width = (int) Math.round(p3*max);
        viewUse3.setLayoutParams(layoutParams3);

        ViewGroup.LayoutParams layoutParams2 = viewUse2.getLayoutParams();
        layoutParams2.width = (int) Math.round(p2*max);
        viewUse2.setLayoutParams(layoutParams2);

        ViewGroup.LayoutParams layoutParams1 = viewUse1.getLayoutParams();
        layoutParams1.width = (int) Math.round(p1*max);
        viewUse1.setLayoutParams(layoutParams1);

        ViewGroup.LayoutParams layoutParamsO = viewOther.getLayoutParams();
        layoutParamsO.width = (int) Math.round(pO*max);
        viewOther.setLayoutParams(layoutParamsO);

        // This takes the double value from usage and converts it procent before parsing
        // it as a string, to the textViews.
        textProc1.setText(String.valueOf(Math.round(p1*100)+"%"));
        textProc2.setText(String.valueOf(Math.round(p2*100)+"%"));
        textProc3.setText(String.valueOf(Math.round(p3*100)+"%"));
        textProcOther.setText(String.valueOf(Math.round(pO*100)+"%"));

        // Will set the text for each category, once we get them.
        //    textCat1.setText();
        //    textCat2.setText();
        //    textCat3.setText();
    }
}

