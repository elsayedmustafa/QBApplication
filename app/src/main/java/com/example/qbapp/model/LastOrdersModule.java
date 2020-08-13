package com.example.qbapp.model;

public class LastOrdersModule {

    public static final String LastOrders_TABLE_NAME = "LastOrders";
    public static final String SerialNumber = "SerialNumber";

    public static final String ID ="ID";


    private    String SerialNumber1 ;


    // Create table SQL query
    public static final String CREATE_PLastOrders_TABLE =
            "CREATE TABLE " + LastOrders_TABLE_NAME + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + SerialNumber + " VARCHAR(48)"
                    + ")";

    public LastOrdersModule() {
    }

    public LastOrdersModule(String serialNumber1) {
        SerialNumber1 = serialNumber1;
    }

    public String getSerialNumber1() {
        return SerialNumber1;
    }

    public void setSerialNumber1(String serialNumber1) {
        SerialNumber1 = serialNumber1;
    }
}
