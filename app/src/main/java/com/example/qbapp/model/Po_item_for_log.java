package com.example.qbapp.model;

public class Po_item_for_log {
    public  String  Time;
    public  String Barcode ;
    public  String Description;
    public  String Sell_price ;
    public  String Tax;
    public  String Discount_Value ;
    public  String Quantity;
    public  String TotalPrice ;

    public Po_item_for_log() {

    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSell_price() {
        return Sell_price;
    }

    public void setSell_price(String sell_price) {
        Sell_price = sell_price;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getDiscount_Value() {
        return Discount_Value;
    }

    public void setDiscount_Value(String discount_Value) {
        Discount_Value = discount_Value;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
