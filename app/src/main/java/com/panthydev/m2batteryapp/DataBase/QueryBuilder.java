package com.panthydev.m2batteryapp.DataBase;

public class QueryBuilder
{

    public static String SelectTable(String table) { return "SELECT * FROM " + table;}
    private String SelectTableWhere(String table, String ConditionColumn, String Condition)
    {
        return SelectTable(table) + " WHERE " + ConditionColumn + " " + Condition;
    }

    public static String SelectTableDataFromTimeRange(String table, String RangeStartDate, String RangeEndDate){
        return SelectTable(table) + " WHERE " + BatteryTable.TIMESTAMP_COL + " BETWEEN " + "'"+RangeStartDate+"'" + " AND " + "'" + RangeEndDate +"'";
    }


    public static String CreateBatteryTable(){
        String query = "CREATE TABLE " + BatteryTable.TABLE_NAME + " ("
                + BatteryTable.ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BatteryTable.PERCENT_REMAINING_COL + " REAL, "
                + BatteryTable.CURRENT_MAH_COL + " REAL, "
                + BatteryTable.ESTIMATED_TIME_LEFT_COL + " REAL, "
                + BatteryTable.MAX_CAPACITY_MAH_COL + " REAL, "
                + BatteryTable.POWER_SAVING_ON_COL + " INTEGER, " // 1/0 for true/false
                + BatteryTable.TIMESTAMP_COL + " TEXT)";

        return query;
    }



}



