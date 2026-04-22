package com.panthydev.m2batteryapp.DataBase;

public class QueryBuilder
{

    public static String SelectTable(String table) { return "SELECT * FROM " + table;}
    private String SelectTableWhere(String table, String ConditionColumn, String Condition)
    {
        return SelectTable(table) + " WHERE " + ConditionColumn + " " + Condition;
    }


    public static String CreateBatteryTable(){
        String query = "CREATE TABLE " + BatteryTable.TABLE_NAME + " ("
                + BatteryTable.ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BatteryTable.CAPACITY_COL + " REAL, "
                + BatteryTable.MAX_CHARGE_COL + " REAL, "
                + BatteryTable.MIN_CHARGE_COL + " REAL, "
                + BatteryTable.POWER_REMAINING_COL + " REAL, "
                + BatteryTable.TEMPERATURE_COL + " REAL, "
                + BatteryTable.TIMESTAMP_COL + " TEXT)";

        return query;
    }



}



