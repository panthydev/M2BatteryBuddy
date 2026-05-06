package com.panthydev.m2batteryapp.data.DataObjects;

import java.util.ArrayList;
import java.util.Comparator;

public class DataPack<T extends DataObject>{
    public ArrayList<T> dataList;
    public DataPack(){
        dataList = new ArrayList<T>();
    }

    public void AddData(T data){
        dataList.add(data);
    }

    public void PrepareForTransport(){
        dataList.sort(new Comparator<T>(){
            @Override
            public int compare(T o1, T o2) {
                return o1.Timestamp.compareTo(o2.Timestamp);
            }
        });
    }



}
