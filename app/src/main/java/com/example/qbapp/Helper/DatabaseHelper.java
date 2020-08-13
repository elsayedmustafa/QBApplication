package com.example.qbapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import com.example.qbapp.model.LastOrdersModule;
import com.example.qbapp.model.Po_Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    public static final String DATABASE_NAME = "QB.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create Po_Headers table
        db.execSQL(Po_Item.CREATE_Po_Item_TABLE);
        db.execSQL(LastOrdersModule.CREATE_PLastOrders_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
       // db.execSQL("DROP TABLE IF EXISTS " + Users.TABLE_User_Name);
        db.execSQL("DROP TABLE IF EXISTS " + Po_Item.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LastOrdersModule.LastOrders_TABLE_NAME);
        // Create tables again
        onCreate(db);
    }


    public long insertSerialNumber(String SerialNumber
    ) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(LastOrdersModule.SerialNumber, SerialNumber);

        // insert row
        long id = db.insert(LastOrdersModule.LastOrders_TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }



    public List<LastOrdersModule> getlastOrders() {
        List<LastOrdersModule> lastOrdersModuleList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + LastOrdersModule.LastOrders_TABLE_NAME;
        // +" ORDER BY " + Po_Header.COLUMN_PASSWORD + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LastOrdersModule lastOrdersModule = new LastOrdersModule();
                lastOrdersModule.setSerialNumber1(cursor.getString(cursor.getColumnIndex(LastOrdersModule.SerialNumber)));

                lastOrdersModuleList.add(lastOrdersModule);

            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();

        // return Po_Headers list
        return lastOrdersModuleList;
    }



    /*public long insertuser(String User_ID, String User_Name, String User_Describtion,
                           String Group_Name, String User_status,
                                String User_Department , String Company, String Group_ID, String ComplexID
                                //, String NO_MORE_GR
    ) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Users.User_ID, User_ID);
        values.put(Users.User_Name, User_Name);
        values.put(Users.User_Describtion, User_Describtion);
        values.put(Users.Group_Name, Group_Name);
        values.put(Users.User_status, User_status);
        values.put(Users.User_Department, User_Department);
        values.put(Users.Company, Company);
        values.put(Users.Group_ID, Group_ID);
        values.put(Users.ComplexID, ComplexID);
        //values.put(Users.NO_MORE_GR, NO_MORE_GR);

        // insert row
        long id = db.insert(Users.TABLE_User_Name, null, values);

        // close db connection
        db.close();
        db.close();

        // return newly inserted row id
        return id;
    }*/



    /*public List<Users> getUserData() {
        List<Users> userlist = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + Users.TABLE_User_Name;
        // +" ORDER BY " + Po_Header.COLUMN_PASSWORD + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users users = new Users();
                users.setCompany1(cursor.getString(cursor.getColumnIndex(Users.Company)));
                users.setUser_Name1(cursor.getString(cursor.getColumnIndex(Users.User_Name)));

                Po_Headerlist.add(new Po_Header(cursor.getString(cursor.getColumnIndex(Po_Header.PO_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR)),
                        cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR_NAME))));
                userlist.add(users);
            Log.d("Po_Headersclass",""+po_header.getVENDOR11());
            Log.d("Po_Headersclasslist",""+Po_Headerlist.get(0).getVENDOR11());


            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.PO_NUMBER)));
            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR)));
            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();

        // return Po_Headers list
        return userlist;
    }






    public void DeleteUserDataTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // "DELETE FROM " + Po_Header.TABLE_Po_Header_NAME;
        db.execSQL("DELETE FROM " +Users.TABLE_User_Name);

        db.close();
    }*/

    public long insertPo_Item(String Barcode, String Describtion, String price, String tax, String discount,
                              String netprice, String quantity
                              // ,String UMREZ,String UMREN
    ) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Po_Item.Barcode, Barcode);
        values.put(Po_Item.Describtion, Describtion);
        values.put(Po_Item.price, price);
        values.put(Po_Item.tax, tax);
        values.put(Po_Item.discount, discount);
        values.put(Po_Item.netprice, netprice);
        values.put(Po_Item.quantity, quantity);

        /*if (SERNP.contains("anyType{}")){
            values.put(Po_Item.SERNP, "");
        }else {
            values.put(Po_Item.SERNP, SERNP);
        }*/


        //  values.put(Po_Item.UMREZ, UMREZ);
        //  values.put(Po_Item.UMREN, UMREN);
        // insert row
        long id = db.insert(Po_Item.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<Po_Item> Get_Items() {
        List<Po_Item> itemslist = new ArrayList<>();
        /*
        // Select All Query
        String selectQuery = "SELECT * FROM " + Po_Item.TABLE_NAME + " where " + Po_Item.EAN11 +" = ?"  ;
        // +" ORDER BY " + Po_Header.COLUMN_PASSWORD + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{BarCode});
         */
        // Select All Query
        String selectQuery = "SELECT * FROM " + Po_Item.TABLE_NAME ;
        //  + " where " + Po_Item.PDNEWQTY +"!=" +"null or "+ Po_Item.PDNEWQTY +"!=" +"0.0";
        // +" ORDER BY " + Po_Header.COLUMN_PASSWORD + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Log.d("po_itemlist","empty"+cursor.getString(cursor.getColumnIndex(Po_Item.SHORT_TEXT)));

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Po_Item items = new Po_Item();
                //الوصف
                items.setBarcode1(cursor.getString(cursor.getColumnIndex(Po_Item.Barcode)));
                items.setDescribtion1(cursor.getString(cursor.getColumnIndex(Po_Item.Describtion)));
                items.setPrice1(cursor.getString(cursor.getColumnIndex(Po_Item.price)));
                items.setTax1(cursor.getString(cursor.getColumnIndex(Po_Item.tax)));
                items.setDiscount1(cursor.getString(cursor.getColumnIndex(Po_Item.discount)));
                items.setQuantity1(cursor.getString(cursor.getColumnIndex(Po_Item.quantity)));
                items.setNetprice1(cursor.getString(cursor.getColumnIndex(Po_Item.netprice)));
                items.setIschecked(false);
                itemslist.add(items);

                if (itemslist.isEmpty()){
                    Log.d("po_itemlist","empty");
                }
                //   Log.d("Po_Headersclass",""+po_item.getMEINH1());
                //Log.d("Po_Headersclasslist",""+po_itemlist.get(0).getMEINH1());


//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.PO_NUMBER)));
//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR)));
//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();

        Log.d("po_itemlistsize",""+itemslist.size());
        // return Po_item list
        return itemslist;
    }

    public int Get_DistinctBarcodes() {
        List<Po_Item> itemslist = new ArrayList<>();
        /*
        // Select All Query
        String selectQuery = "SELECT * FROM " + Po_Item.TABLE_NAME + " where " + Po_Item.EAN11 +" = ?"  ;
        // +" ORDER BY " + Po_Header.COLUMN_PASSWORD + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{BarCode});
         */
        // Select All Query
        String selectQuery = "SELECT Distinct "+Po_Item.Describtion +" FROM " + Po_Item.TABLE_NAME ;
        //  + " where " + Po_Item.PDNEWQTY +"!=" +"null or "+ Po_Item.PDNEWQTY +"!=" +"0.0";
        // +" ORDER BY " + Po_Header.COLUMN_PASSWORD + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Log.d("po_itemlist","empty"+cursor.getString(cursor.getColumnIndex(Po_Item.SHORT_TEXT)));

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Po_Item items = new Po_Item();
                //الوصف
                //items.setBarcode1(cursor.getString(cursor.getColumnIndex(Po_Item.Barcode)));
                items.setDescribtion1(cursor.getString(cursor.getColumnIndex(Po_Item.Describtion)));
//                items.setPrice1(cursor.getString(cursor.getColumnIndex(Po_Item.price)));
//                items.setTax1(cursor.getString(cursor.getColumnIndex(Po_Item.tax)));
//                items.setDiscount1(cursor.getString(cursor.getColumnIndex(Po_Item.discount)));
//                items.setQuantity1(cursor.getString(cursor.getColumnIndex(Po_Item.quantity)));
//                items.setNetprice1(cursor.getString(cursor.getColumnIndex(Po_Item.netprice)));
//                items.setIschecked(false);
                itemslist.add(items);

                if (itemslist.isEmpty()){
                    Log.d("po_itemlist","empty");
                }
                //   Log.d("Po_Headersclass",""+po_item.getMEINH1());
                //Log.d("Po_Headersclasslist",""+po_itemlist.get(0).getMEINH1());


//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.PO_NUMBER)));
//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR)));
//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();

        Log.d("po_itemlistsize",""+itemslist.size());
        // return Po_item list
        return itemslist.size();
    }

    public List<Po_Item> SellectPo_itemForBarcode(String Barcode) {
        List<Po_Item> itemslist = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        /*
        // Select All Query
        String selectQuery = "SELECT * FROM " + Po_Item.TABLE_NAME + " where " + Po_Item.EAN11 +" = ?"  ;
        // +" ORDER BY " + Po_Header.COLUMN_PASSWORD + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{BarCode});
         */
        // Select All Query

        Cursor cursor = db.query(Po_Item.TABLE_NAME,null,Po_Item.Barcode + " = ?",
                new String[]{Barcode},
                null,null,Po_Item.Barcode,"1");

        /*String selectQuery = "SELECT * FROM " + Po_Item.TABLE_NAME
         + " where " + Po_Item.Barcode +"=" +Barcode;
        // +" ORDER BY " + Po_Header.COLUMN_PASSWORD + " DESC";

        Cursor cursor = db.rawQuery(selectQuery, null);*/
        // Log.d("po_itemlist","empty"+cursor.getString(cursor.getColumnIndex(Po_Item.SHORT_TEXT)));

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Po_Item items = new Po_Item();
                //الوصف
                items.setID1(cursor.getString(cursor.getColumnIndex(Po_Item.ID)));
                items.setBarcode1(cursor.getString(cursor.getColumnIndex(Po_Item.Barcode)));
                items.setDescribtion1(cursor.getString(cursor.getColumnIndex(Po_Item.Describtion)));
                items.setPrice1(cursor.getString(cursor.getColumnIndex(Po_Item.price)));
                items.setTax1(cursor.getString(cursor.getColumnIndex(Po_Item.tax)));
                items.setDiscount1(cursor.getString(cursor.getColumnIndex(Po_Item.discount)));
                items.setQuantity1(cursor.getString(cursor.getColumnIndex(Po_Item.quantity)));
                items.setNetprice1(cursor.getString(cursor.getColumnIndex(Po_Item.netprice)));

                itemslist.add(items);

                if (itemslist.isEmpty()){
                    Log.d("po_itemlist","empty");
                }
                //   Log.d("Po_Headersclass",""+po_item.getMEINH1());
                //Log.d("Po_Headersclasslist",""+po_itemlist.get(0).getMEINH1());


//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.PO_NUMBER)));
//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR)));
//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();

        Log.d("po_itemlistsize",""+itemslist.size());
        // return Po_item list
        return itemslist;
    }

    //update للكميه المستله سابقاوتاريخ الاستلام
    public int update_PDNEWQTY(String BarCode, String PDNEWQTY) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Po_Item.quantity, PDNEWQTY);

        // updating row
        return db.update(Po_Item.TABLE_NAME, values, Po_Item.Barcode + " = ?",
                new String[]{BarCode});
    }

    public void DeletePo_item(String Barcode){
        SQLiteDatabase db = this.getWritableDatabase();

        // "DELETE FROM " + Po_Header.TABLE_Po_Header_NAME;
//        db.execSQL("DELETE  FROM " +Po_Item.TABLE_NAME +" WHERE "+Po_Item.Barcode+"="+Barcode
//              +" LIMIT 1"
//        );

  //     db.execSQL(" DELETE  FROM Po_Item.TABLE_NAME WHERE Barcode = Barcode ORDER BY Barcode LIMIT 1");
//        String selectQuery = " SELECT *FROM " + Po_Item.TABLE_NAME ;

//        String selectQuery = " DELETE  FROM " + Po_Item.TABLE_NAME
//                //+ " where " + Po_Item.Barcode +" = ?"
//                ;
//        // +" ORDER BY " + Po_Header.COLUMN_PASSWORD + " DESC";
//
        Cursor cursor = db.query(Po_Item.TABLE_NAME,new String[]{Po_Item.ID},Po_Item.Barcode + " = ?",
                new String[]{Barcode},
                 null,null,Po_Item.Barcode,null);

  //      db.close();

        List<Po_Item> itemslist = new ArrayList<>();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
          //  do {

                cursor.getString(cursor.getColumnIndex(Po_Item.ID));

            db.execSQL("DELETE FROM " +Po_Item.TABLE_NAME +" WHERE "+Po_Item.ID
                            +"="+cursor.getString(cursor.getColumnIndex(Po_Item.ID))
//              +" LIMIT 1"
        );
            Log.d("FROM DELETE",""+cursor.getString(cursor.getColumnIndex(Po_Item.ID)));
//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR)));
//            Log.d("Po_Headers",""+cursor.getString(cursor.getColumnIndex(Po_Header.VENDOR_NAME)));
           // } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();

        // return Po_item list*/

    }

    public void DeletePo_itemsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        // "DELETE FROM " + Po_Header.TABLE_Po_Header_NAME ;

        db.execSQL("DELETE FROM " +Po_Item.TABLE_NAME );
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME ='" +Po_Item.TABLE_NAME+"'" );

       // db.execSQL("ALTER TABLE " +Po_Item.TABLE_NAME +" AUTOINCREMENT =? 0");
        db.close();
    }

    public void DeletelastordersTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        // "DELETE FROM " + Po_Header.TABLE_Po_Header_NAME;
        db.execSQL("DELETE FROM " + LastOrdersModule.LastOrders_TABLE_NAME);

        db.close();
    }
}
