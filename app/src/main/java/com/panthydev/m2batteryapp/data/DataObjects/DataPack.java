package com.panthydev.m2batteryapp.data.DataObjects;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * <pre>Container used to transport a list of dataObjects
 * Contains "dataList" which is the thing you probably want
 * </pre>
 * @param <T> T should be a subclass of DataObject
 */
public class DataPack<T extends DataObject>{
    public ArrayList<T> dataList;
    public DataPack(){
        dataList = new ArrayList<T>();
    }

    /**
     * <pre>Adds data to the dataList. T Must be a subclass of DataObject
     * @param data DataObject to add to the dataList
     */
    public void AddData(T data){
        dataList.add(data);
    }

    public void PrepareForTransport(){
        if (dataList == null || dataList.size() < 2) {
            return;
        }

        dataList.sort(new Comparator<T>(){
            @Override
            public int compare(T o1, T o2) {
                if (o1 == null || o1.Timestamp == null) {
                    return (o2 == null || o2.Timestamp == null) ? 0 : -1;
                }
                if (o2 == null || o2.Timestamp == null) {
                    return 1;
                }
                return o1.Timestamp.compareTo(o2.Timestamp);
            }
        });
    }



}
