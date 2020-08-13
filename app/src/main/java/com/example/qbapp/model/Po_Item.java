package com.example.qbapp.model;

public class Po_Item {

    public static final String TABLE_NAME = "Po_Item";
    public static final String ID= "ID";
    public static final String Barcode = "Barcode";
    public static final String Describtion = "Describtion";
    public static final String price = "price";
    public static final String tax = "tax";
    public static final String discount = "discount";
    public static final String netprice = "netprice";
    public static final String quantity = "quantity";



    private    String ID1 ;
    private    String Barcode1 ;
    private   String Describtion1 ;
    private   String price1 ;
    private   String tax1 ;
    private   String discount1 ;
    private   String netprice1 ;
    private   String quantity1 ;

private  Boolean Ischecked;

    // Create table SQL query
    public static final String CREATE_Po_Item_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + Barcode + " VARCHAR(18),"
                    + Describtion + " VARCHAR(40),"
                    + price + " VARCHAR(13),"
                    + tax + " VARCHAR(3),"
                    + discount + " VARCHAR(3),"
                    + netprice + " VARCHAR(13),"
                    + quantity + " VARCHAR(3)"

                   // + UMREZ + " VARCHAR(5),"   //>>> this column isn't in document image
                  //  + UMREN + " VARCHAR(5)"    //>>> this column isn't in document image


                    + ")";

    public Po_Item(Boolean ischecked) {
    }

    public Po_Item() {
    }

    public Po_Item(String barcode1, String quantity1) {
        Barcode1 = barcode1;
        this.quantity1 = quantity1;
    }

    public Po_Item(String barcode1, String describtion1, String price1,
                   String tax1, String discount1, String netprice1, String quantity1, Boolean ischecked) {
        Barcode1 = barcode1;
        Describtion1 = describtion1;
        this.price1 = price1;
        this.tax1 = tax1;
        this.discount1 = discount1;
        this.netprice1 = netprice1;
        this.quantity1 = quantity1;
        Ischecked = ischecked;
    }

    public Po_Item(String barcode1, String describtion1, String price1, String tax1, String discount1, String netprice1, String quantity1) {
        Barcode1 = barcode1;
        Describtion1 = describtion1;
        this.price1 = price1;
        this.tax1 = tax1;
        this.discount1 = discount1;
        this.netprice1 = netprice1;
        this.quantity1 = quantity1;
    }

    public String getID1() {
        return ID1;
    }

    public void setID1(String ID1) {
        this.ID1 = ID1;
    }

    public Boolean getIschecked() {
        return Ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        Ischecked = ischecked;
    }

    public String getBarcode1() {
        return Barcode1;
    }

    public void setBarcode1(String barcode1) {
        Barcode1 = barcode1;
    }

    public String getDescribtion1() {
        return Describtion1;
    }

    public void setDescribtion1(String describtion1) {
        Describtion1 = describtion1;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getTax1() {
        return tax1;
    }

    public void setTax1(String tax1) {
        this.tax1 = tax1;
    }

    public String getDiscount1() {
        return discount1;
    }

    public void setDiscount1(String discount1) {
        this.discount1 = discount1;
    }

    public String getNetprice1() {
        return netprice1;
    }

    public void setNetprice1(String netprice1) {
        this.netprice1 = netprice1;
    }

    public String getQuantity1() {
        return quantity1;
    }

    public void setQuantity1(String quantity1) {
        this.quantity1 = quantity1;
    }
}
