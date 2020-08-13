package com.example.qbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qbapp.Helper.DatabaseHelper;
import com.example.qbapp.model.Po_Item;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditItemActivity extends AppCompatActivity {
    List<Po_Item> Po_Item_list;
    DatabaseHelper databaseHelper;
    Double Current_U_Deliver=0.0 , total_price=0.0;
    EditText edit_current_deliver;
    TextView txt_barcode, txt_descripation, txt_price_item, txt_tax_item,txt_discount, txt_total_price ,txt_total_qty;
    String Barcode ,UserName,Branch, TotalQuantity, VendorName;
    View view;
    ArrayList<String> LastOrderArry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent getDta= getIntent();
        Barcode = getDta.getExtras().getString("BarCode");
        UserName = getDta.getExtras().getString("UserName");
        Branch = getDta.getExtras().getString("Branch");
       // LastOrderArry = (ArrayList<String>) getDta.getExtras().getSerializable("LastOrderIdArray");

        Po_Item_list = new ArrayList<>();

        databaseHelper =new DatabaseHelper(this);

        txt_barcode=findViewById(R.id.txt_barcode);
        txt_descripation=findViewById(R.id.txt_descripation);
        txt_price_item=findViewById(R.id.txt_price_item);
        txt_tax_item=findViewById(R.id.txt_tax_item);
        txt_discount=findViewById(R.id.txt_discount);
        txt_total_qty =findViewById(R.id.txt_total_qty);
        txt_total_price =findViewById(R.id.txt_total_price);
        edit_current_deliver=findViewById(R.id.edit_current_deliver);

        Po_Item_list = databaseHelper.SellectPo_itemForBarcode(Barcode);
        txt_barcode.setText(Barcode);
        txt_descripation.setText(Po_Item_list.get(0).getDescribtion1());
        txt_price_item.setText(new DecimalFormat("###,###.00").format(Double.valueOf(Po_Item_list.get(0).getPrice1())));
        txt_tax_item.setText("%"+Po_Item_list.get(0).getTax1());
        txt_discount.setText(Po_Item_list.get(0).getDiscount1());
        txt_total_qty.setText(new DecimalFormat("###,###.000").format(Double.valueOf(Po_Item_list.get(0).getQuantity1())));
         total_price=Double.valueOf(Po_Item_list.get(0).getNetprice1().replace(",",""))
                 *Double.valueOf(Po_Item_list.get(0).getQuantity1());
        if (String.valueOf(String.valueOf(total_price).substring(String.valueOf(total_price).indexOf("."),
                String.valueOf(total_price).length())).length()>2) {

            total_price =  Double.valueOf(String.valueOf(total_price).substring(0,
                    String.valueOf(total_price).indexOf(".") + 3));

            txt_total_price.setText(new DecimalFormat("###,###.##").format(Double.valueOf(total_price)));

        }else if (String.valueOf(total_price).substring(String.valueOf(total_price).indexOf(".")+1,
                String.valueOf(total_price).length()).length()<2) {

//                for (int i=0;String.valueOf(totalprice).substring(String.valueOf(totalprice).indexOf(".")+1,
//                        String.valueOf(totalprice).length()).length()< 2;i++) {
//                    String.valueOf(totalprice)=String.valueOf(totalprice)+"0";
//                }
            Log.e("totalprice", "this is from" + total_price);

            txt_total_price.setText(new DecimalFormat("###,###.00").format(total_price) );
        }

        //edit_current_deliver.setText(Po_Item_list.get(0).getQuantity1());
        edit_current_deliver.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        ||keyEvent == null
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    SaveUpdatePDNEWQTY(view);
                }

                return false;
            }
        });
//        Log.e("BarCodeChecked",""+Po_Item_list.get(0).getSHORT_TEXT1());
//        Log.e("BarCodeChecked",""+Po_Item_list.get(0).getMEINH1());
//        Log.e("BarCodeChecked",""+Po_Item_list.get(0).getPDNEWQTY1());
//        txt_barcode.setText(Barcode);
//        txt_descripation.setText(Po_Item_list.get(0).getSHORT_TEXT1());
//        txt_unite_item.setText(Po_Item_list.get(0).getMEINH1());
//        edit_last_deliver.setText(Po_Item_list.get(0).getPDNEWQTY1());
    }

    public void SaveUpdatePDNEWQTY(View view) {
        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        String editQTY=edit_current_deliver.getText().toString();
        if (edit_current_deliver.getText().toString().isEmpty()) {
            edit_current_deliver.setError("من فضلك أدخل الكمية");
        }else {
            if (!editQTY.contains(".")) {
                editQTY = editQTY + ".000";
               txt_total_qty.setText(new DecimalFormat("###,###.000").format(Double.valueOf(editQTY)));
            }else if (editQTY.substring(editQTY.indexOf(".")+1,editQTY.length()).length()>3)
            {
                Log.e("vvvvvvvvvvifQ", "" + editQTY);
//                editQTY = editQTY.substring(0, String.valueOf(Double.valueOf(editQTY)).indexOf(".") + 4);
                Log.e("vvvvvvvvvvifQ", "" + editQTY);
                txt_total_qty.setText(new DecimalFormat("###,###.###").format(Double.valueOf(editQTY)));

            }

            databaseHelper.update_PDNEWQTY(Barcode, (txt_total_qty.getText().toString()).replace(",",""));

            total_price=0.0;
            total_price=Double.valueOf(Po_Item_list.get(0).getNetprice1().replace(",",""))
                    *Double.valueOf(editQTY);
            if (String.valueOf(String.valueOf(total_price).substring(String.valueOf(total_price).indexOf("."),
                    String.valueOf(total_price).length())).length()>2) {
                total_price =  Double.valueOf(String.valueOf(total_price).substring(0,
                        String.valueOf(total_price).indexOf(".") + 3));
            }

            txt_total_price.setText(new DecimalFormat("###,###.00").format(Double.valueOf(total_price)));

            edit_current_deliver.setText("");
            edit_current_deliver.setHint("Done");
           // edit_current_deliver.setHintTextColor(getResources().getColor(R.color.first_blue));

             /*Intent GoTocheckItems = new Intent(EditItemActivity.this, MainActivity.class);
            GoTocheckItems.putExtra("UserName",UserName);
            GoTocheckItems.putExtra("BarCode",Barcode);
            GoTocheckItems.putExtra("Branch",Branch);

            startActivity(GoTocheckItems);*/
        }
       // Log.d("TotalDeliver",""+TotalDeliver);

    }


    @Override
    public void onBackPressed() {
        Intent Go_Back= new Intent(EditItemActivity.this,MainActivity.class);
        Go_Back.putExtra("UserName",UserName);
        Go_Back.putExtra("BarCode",Barcode);
        Go_Back.putExtra("Branch",Branch);

        startActivity(Go_Back);
       /* List<PO_SERIAL> po_serialslist = new ArrayList<>();

        Log.e("onBackPressedlog",""+Current_U_Deliver);
        Log.e("onBackPressedlogs",""+po_serialslist.size());
        po_serialslist = databaseHelper.selectPo_Serials(Barcode);
        if (Current_U_Deliver ==  po_serialslist.size()){
            Intent Go_Back= new Intent(EditItemActivity.this,CheckItemFormActivity.class);
            startActivity(Go_Back);
           // super.onBackPressed();
        }else {
            edit_last_deliver.setError("عدد السيريالات اكبر أو أقل من الكميه المكتوبه  .. قم بتعديلها اولا");
           // super.onBackPressed();
        }*/

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void Back(View view) {
        Intent Go_Back= new Intent(EditItemActivity.this,MainActivity.class);
        Go_Back.putExtra("UserName",UserName);
        Go_Back.putExtra("BarCode",Barcode);
        Go_Back.putExtra("Branch",Branch);
        startActivity(Go_Back);
    }

//    public void EditSerial(View view) {
//        String SeralForMaterial  = Po_Item_list.get(0).getSERNP1();
//
//        if (SeralForMaterial.equalsIgnoreCase("anytype{}")){
//            Toast.makeText(EditItemActivity.this,"لا يوجد سيريال لهذا الباركود",Toast.LENGTH_LONG).show();
//        }else {
//            Intent GoToSerialForm=new Intent(EditItemActivity.this,SerialViewFormActivity.class);
//            GoToSerialForm.putExtra("PDNEWQTY",Po_Item_list.get(0).getPDNEWQTY1());
//            GoToSerialForm.putExtra("barCode",Barcode);
//            GoToSerialForm.putExtra("Po_item",Po_Item_list.get(0).getPO_ITEM1());
//            //Log.d("Po_Item_list",""+Po_Item_list.get(0).getEAN111());
//            GoToSerialForm.putExtra("Material",Po_Item_list.get(0).getMATERIAL1());
//            //Log.d("Po_Item_list",""+Po_Item_list.get(0).getMATERIAL1());
//            startActivity(GoToSerialForm);
//        }
//    }
}
