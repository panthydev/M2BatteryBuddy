package com.panthydev.m2batteryapp.DataBase;

public class QueryBuilder
{

    public static String SelectTable(String table) { return "SELECT * FROM " + table;}

    private static String SelectTableWhere(String table, String conditionColumn, String condition)
    {
        return SelectTable(table) + " WHERE " + conditionColumn + " " + condition;
    }


    /**
     * Creates a SQL command to select all data from a table between two dates.
     * @param table what table to select from
     * @param RangeStartDate start date
     * @param RangeEndDate end date
     * @return an SQL command String
     */
    public static String SelectTableDataFromTimeRange(String table, String RangeStartDate, String RangeEndDate){
        return SelectTable(table) + " WHERE " + BatteryTable.TIMESTAMP_COL + " BETWEEN " + "'"+RangeStartDate+"'" + " AND " + "'" + RangeEndDate +"'";
    }

    public static String SelectTableDataSince(String table, String startDate){
        if (startDate == null || startDate.isEmpty()) {
            return SelectTable(table);
        }

        return SelectTableWhere(table, BatteryTable.TIMESTAMP_COL, ">= '" + startDate + "'");
    }

    public static String SelectAppDataFromTimeRange(String appName, String rangeStartDate, String rangeEndDate) {
        String query = SelectTable(AppTable.TABLE_NAME);

        if (appName != null && !appName.isEmpty()) {
            query += " WHERE " + AppTable.APP_NAME_COL + " = '" + appName + "'";
        }

        if (rangeStartDate != null && !rangeStartDate.isEmpty() && rangeEndDate != null && !rangeEndDate.isEmpty()) {
            query += (query.contains(" WHERE ") ? " AND " : " WHERE ")
                    + AppTable.TIMESTAMP_COL + " BETWEEN '" + rangeStartDate + "' AND '" + rangeEndDate + "'";
        }

        return query;
    }

    public static String SelectAppDataSince(String appName, String startDate) {
        String condition = "";

        if (appName != null && !appName.isEmpty()) {
            condition += AppTable.APP_NAME_COL + " = '" + appName + "'";
        }

        if (startDate != null && !startDate.isEmpty()) {
            condition += (condition.isEmpty() ? "" : " AND ") + AppTable.TIMESTAMP_COL + " >= '" + startDate + "'";
        }

        if (condition.isEmpty()) {
            return SelectTable(AppTable.TABLE_NAME);
        }

        return SelectTable(AppTable.TABLE_NAME) + " WHERE " + condition;
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

    public static String CreateAppTable(){
        String query = "CREATE TABLE " + AppTable.TABLE_NAME + " ("
                + AppTable.ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AppTable.APP_NAME_COL + " TEXT, "
                + AppTable.APP_CATEGORY_COL + " INTEGER, "
                + AppTable.APP_DISCHARGE_COL + " INTEGER, "
                + AppTable.TIMESTAMP_COL + " TEXT)";
        return query;
    }



}



