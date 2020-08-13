package com.example.qbapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qbapp.Adapter.ItemDelieveredFormAdapter;
import com.example.qbapp.Classes.Constant;
import com.example.qbapp.Fragments.DetailsFragment;
import com.example.qbapp.Fragments.LastOrderFragment;
import com.example.qbapp.Helper.BluetoothHandler;
import com.example.qbapp.Helper.DatabaseHelper;
import com.example.qbapp.Helper.ItemClickSupport;
import com.example.qbapp.Helper.PrinterCommands;
import com.example.qbapp.Helper.Utils;
import com.example.qbapp.model.Po_Item;
import com.example.qbapp.model.Po_item_for_log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.zj.btsdk.BluetoothService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks,
        BluetoothHandler.HandlerInterface {
    Date Cdate=null , Tooodate=null;

    EditText et_Barcode;
    TextView txt_title_of_print,txt_total_price,tvStatus,
            txt_descripation, txt_price_item, txt_tax_item,txt_discount,txt_netprice,
            txt_total_items_deliver,txt_total_qty_deliver;
    ImageView Barcode_image , image_close;
    LinearLayout linear_delete;
    private final String TAG = MainActivity.class.getSimpleName();
    public static final int RC_BLUETOOTH = 0;
    public static final int RC_CONNECT_DEVICE = 1;
    public static final int RC_ENABLE_BLUETOOTH = 2;
    DatabaseHelper databaseHelper;
    private BluetoothService mService = null;
    private boolean isPrinterReady = false;
    BitSet dots;
    Bitmap bitmap = null;
    String Content="\r";
    Button print_barcode_image,btn_back,btn_okdelete_local_database,
            btn_print_barcode_again;
    LinearLayout line_of_details,linear_edit_barcode;
    public StringRequest request=null;
    private RecyclerView recyclerView;
    List<Po_Item> Po_Item_For_Recycly;
    ItemDelieveredFormAdapter itemDelieveredFormAdapter;
    public VolleyError volleyErrorPublic=null;
    Double totalprice=0.0;
    int totalpecies=0;
    Double totalCategories=0.0;
    String UserName,Branch, Serialnum;
    List<Po_item_for_log> Po_Items_For_LogsArray;
    Button btn_get_detials_for_barcod;
    String editbarcode;
    int numberofBarcodeImages=0, numberofBarcodeImagesToprint = 0 ,nuberOfImgeForPrint=0 ;
    Double totalofBarcodes=0.0 , testforitemssize=0.0;
    int SumOfBarcodesadded=0 ;
    String Depart="";
    String TotalQTYFor23 ="",KQTY="",GQTY="";
    String BarcodeFor23="" ;
    ImageView image_decrease,image_increase,last_order_image;
    int CountChecked=0;
    String TotalPriceForPrint="" , UpdateQTY ="" ;
    public  String LastOrderId="";
    int CountCheckedP=0 ,postionForsave=-1;
    int PrintAgainNumber=0;
    // TSCActivity TscDll = new TSCActivity();
    Boolean Adding_For_First_Time=false;
    ProgressBar progressBar;
    int BQN=7;
   // ArrayList<String> LastOrderArry;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setupBluetooth();

        if (getIntent().getExtras() !=null) {
            UserName = getIntent().getExtras().getString("UserName");
            Branch = getIntent().getExtras().getString("Branch");
           // LastOrderArry = (ArrayList<String>) getIntent().getExtras().getSerializable("LastOrderIdArray");
        }else {
            UserName="Null";
            Branch="Null";
//            LastOrderId="Null";
        }

//        assert LastOrderId != null;
//        if (!LastOrderId.equalsIgnoreCase("Null")){
//            Toast.makeText(this, ""+LastOrderId, Toast.LENGTH_SHORT).show();
//            RetrieveFromSqlServer(LastOrderId);
//
//        }else {
//            Toast.makeText(this, "NoRetrieve", Toast.LENGTH_SHORT).show();
//        }
        btn_get_detials_for_barcod=findViewById(R.id.btn_get_detials_for_barcod);
        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line_of_details.setVisibility(View.VISIBLE);
                linear_edit_barcode.setVisibility(View.VISIBLE);
                Barcode_image.setVisibility(View.GONE);
                btn_back.setVisibility(View.GONE);
                txt_title_of_print.setVisibility(View.GONE);
                linear_delete.setVisibility(View.GONE);
                et_Barcode.requestFocus();
            }
        });
        progressBar=findViewById(R.id.progressBar);

        image_close=findViewById(R.id.image_close);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Po_Item_For_Recycly = new ArrayList<>();

                Po_Item_For_Recycly = databaseHelper.Get_Items();
                if (Po_Item_For_Recycly.size() !=0) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(getString(R.string.want_to_deleteall))
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    databaseHelper.DeletePo_itemsTable();
                                    CreateORUpdateRecycleView(-1);
                                    et_Barcode.requestFocus();


                                }
                            })
                            .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                    //CreateORUpdateRecycleView(-1);
                                    et_Barcode.requestFocus();

                                }
                            }).show();

                }else {
                    Toast.makeText(MainActivity.this, "لا يوجد بيانات للحذف", Toast.LENGTH_SHORT).show();
                }
            }
        });
        print_barcode_image=findViewById(R.id.btn_print_barcode_image);
        print_barcode_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Po_Item_For_Recycly = new ArrayList<>();
//        databaseHelper =new DatabaseHelper(this);
//
//        Po_Item_For_Recycly = databaseHelper.Get_Po_Item_That_Has_PDNewQTy();
                Po_Item_For_Recycly = databaseHelper.Get_Items();
                if (Po_Item_For_Recycly.size() !=0) {
                    if (!isPrinterReady) {
                        connectifthereisnoconnection();
                        print_barcode_image.setVisibility(View.GONE);
                    }else {
                        PrintAgainNumber =0 ;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:MM");
                        Serialnum = sdf.format(new Date());
                        List<Po_Item> itemslist = new ArrayList<>();

                        itemslist = databaseHelper.Get_Items();
                        printImage(itemslist);
                        WriteIntableOfSqlServer();
                        print_barcode_image.setVisibility(View.VISIBLE);
                    }

                }else {
                    print_barcode_image.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "لا يوجد بيانات لطباعه", Toast.LENGTH_SHORT).show();
                }


            }

        });
        linear_delete=findViewById(R.id.linear_delete);
        txt_title_of_print=findViewById(R.id.txt_title_of_print);
        et_Barcode=findViewById(R.id.et_text);
        txt_total_qty_deliver=findViewById(R.id.txt_total_qty_deliver);
        txt_total_qty_deliver.setText("0");
        btn_get_detials_for_barcod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDetialsForBarcod();

            }
        });
        et_Barcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent == null
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER
                        || keyEvent.getAction() == KeyEvent.KEYCODE_NUMPAD_ENTER
                        || keyEvent.getAction() == KeyEvent.KEYCODE_DPAD_CENTER){

                    //execute our method for searching
                    GetDetialsForBarcod();

                }

                return false;
            }
        });
        et_Barcode.requestFocus();
        Barcode_image=findViewById(R.id.barcde_image);
        line_of_details=findViewById(R.id.line_of_details);
        linear_edit_barcode=findViewById(R.id.linear_edit_barcode);
        btn_okdelete_local_database=findViewById(R.id.btn_okdelete_local_database);
        btn_print_barcode_again =findViewById(R.id.btn_print_barcode_again);
        txt_total_items_deliver=findViewById(R.id.txt_total_items_deliver);
        recyclerView =  findViewById(R.id.recycle_items_view);

        databaseHelper=new DatabaseHelper(this);

        btn_okdelete_local_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.DeletePo_itemsTable();

                line_of_details.setVisibility(View.VISIBLE);
                linear_edit_barcode.setVisibility(View.VISIBLE);
                Barcode_image.setVisibility(View.GONE);
                btn_back.setVisibility(View.GONE);
                txt_title_of_print.setVisibility(View.GONE);
                linear_delete.setVisibility(View.GONE);
                CreateORUpdateRecycleView(-1);
                et_Barcode.requestFocus();
            }
        });
        btn_print_barcode_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPrinterReady) {
                    connectifthereisnoconnection();
                }else {
                    PrintAgainNumber++;
                    List<Po_Item> itemslist = new ArrayList<>();
                    itemslist = databaseHelper.Get_Items();
                    printImage(itemslist);
                    WriteInprinttableOfSqlServer();
                }
            }
        });
        tvStatus=findViewById(R.id.tv_status);
//        txt_descripation=findViewById(R.id.txt_descripation);
//        txt_price_item=findViewById(R.id.txt_price_item);
//        txt_tax_item=findViewById(R.id.txt_tax_item);
//        txt_discount=findViewById(R.id.txt_discount);
        txt_total_price=findViewById(R.id.txt_total_price);
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                List<Po_Item> Po_Item_List = itemDelieveredFormAdapter.ReturnListOfItems();
//               Toast.makeText(MainActivity.this, ""+Po_Item_List.get(position).getQuantity1(), Toast.LENGTH_SHORT).show();

                DetailsFragment detialsfragment=new DetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putString("Barcode",Po_Item_List.get(position).getBarcode1());
                bundle.putString("UserName",UserName);
                bundle.putString("Branch",Branch);
               // bundle.putSerializable("LastOrderIdArray",LastOrderArry);

                detialsfragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content,detialsfragment);

                // databaseHelper.update_PDNEWQTY(Po_Item_List.get(position).getBarcode1(),String.valueOf(Double.valueOf(Po_Item_List.get(position).getQuantity1())-1));

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                // fragmentTransaction.remove(detialsfragment);

                /*List<Po_Item> Po_Item_List_of_detials =databaseHelper.SellectPo_itemForBarcode(Po_Item_List.get(position).getBarcode1());
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("hjfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsfds"+Po_Item_List_of_detials.get(0).getPrice1()+"\n"+Po_Item_List_of_detials.get(0).getTax1()+"\n"
                        +Po_Item_List_of_detials.get(0).getDiscount1()+"\n")
                        .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).show();*/
                return false;
            }

        });

        last_order_image=findViewById(R.id.last_order_image);

        //LastOrderArry =new ArrayList<>();

        last_order_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHelper.getlastOrders().size() != 0) {
                    LastOrderFragment lastOrderFragment = new LastOrderFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("UserName", UserName);
                    bundle.putString("Branch", Branch);
//                bundle.putSerializable("LastOrderIdArray",LastOrderArry);
//                Log.e("zzzlmainactivity",""+LastOrderArry.size());
                    lastOrderFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_content, lastOrderFragment);
                    // databaseHelper.update_PDNEWQTY(Po_Item_List.get(position).getBarcode1(),String.valueOf(Double.valueOf(Po_Item_List.get(position).getQuantity1())-1));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(MainActivity.this, "لا يوجد بيانات سابقة", Toast.LENGTH_SHORT).show();
                }
            }
        });
        CreateORUpdateRecycleView(-1);
        image_increase=findViewById(R.id.image_increase);
        image_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Po_Item> Po_Item_For_P = new ArrayList<>();
                Adding_For_First_Time = false;
                Po_Item_For_P = itemDelieveredFormAdapter.ReturnListOfItems();


                String BarCodeChecked="" , UpdateQTY ="";
                CountCheckedP =0;
                if (Po_Item_For_P.size() != 0) {
                    for (int i = 0; i < Po_Item_For_P.size(); i++) {
                        Boolean Checked = Po_Item_For_P.get(i).getIschecked();

                        //Log.e("btn_editChecked",""+Checked);
                        if (Checked == true) {
                            postionForsave=i;
                            //Log.e("btn_editCheckedif",""+Checked);
                            CountCheckedP += 1;
                            BarCodeChecked =Po_Item_For_P.get(i).getBarcode1();
                            UpdateQTY   =Po_Item_For_P.get(i).getQuantity1();

                        }if (i == (Po_Item_For_P.size()-1)){
                            if (CountCheckedP < 1 ) {   //|| CountCheckedP > 1
                                Toast.makeText(MainActivity.this, "قم بأختيار صنف", Toast.LENGTH_LONG).show();

                            }
                            else if (CountCheckedP == 1 ) {  //&& !BarCodeChecked.isEmpty()
                                Depart=BarCodeChecked.substring(0,2);
                                if (!Depart.equalsIgnoreCase("23")) {
//                                Toast.makeText(MainActivity.this, ""+BarCodeChecked, Toast.LENGTH_LONG).show();

                                    databaseHelper.update_PDNEWQTY(BarCodeChecked,String.valueOf(Double.valueOf(UpdateQTY)+1));
                                    CreateORUpdateRecycleView(postionForsave);

                                    break;
                                }

                                else {
                                    Toast.makeText(MainActivity.this, "صنف موزون", Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                        }
                    }

                }
            }
        });
        image_decrease=findViewById(R.id.image_decrease);
        image_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Po_Item> Po_Item_For_P = new ArrayList<>();
                // databaseHelper =new DatabaseHelper(this);
                Adding_For_First_Time = false;

//        Po_Item_For_Recycly = databaseHelper.Get_Po_Item_That_Has_PDNewQTy();
//
                Po_Item_For_P = itemDelieveredFormAdapter.ReturnListOfItems();

                String BarCodeChecked="" , UpdateQTY ="";
                CountCheckedP =0;
                if (Po_Item_For_P.size() != 0) {
                    for (int i = 0; i < Po_Item_For_P.size(); i++) {
                        Boolean Checked = Po_Item_For_P.get(i).getIschecked();
                        //Log.e("btn_editChecked",""+Checked);
                        if (Checked==true) {
                            postionForsave=i;
                            Log.e("btn_editCheckedif", "" + Checked);
                            CountCheckedP += 1;
                            BarCodeChecked = Po_Item_For_P.get(i).getBarcode1();
                            Log.e("btn_editCheckedif", "" + BarCodeChecked);
                            UpdateQTY = Po_Item_For_P.get(i).getQuantity1();
                        }
                        if (i == (Po_Item_For_P.size()-1)){
                            if (CountCheckedP < 1 ) {   //|| CountCheckedP > 1
                                Toast.makeText(MainActivity.this, "قم بأختيار صنف", Toast.LENGTH_LONG).show();


                            } else if (CountCheckedP == 1 ) {  //&& !BarCodeChecked.isEmpty()
                                Depart=BarCodeChecked.substring(0,2);
                                if (!Depart.equalsIgnoreCase("23")) {

                                    if ((Double.valueOf(UpdateQTY) - 1) ==0){
                                        final String finalBarCodeChecked = BarCodeChecked;
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle(getString(R.string.want_to_delete))
                                                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        databaseHelper.DeletePo_item(finalBarCodeChecked);
                                                        postionForsave=-1;
                                                        CreateORUpdateRecycleView(postionForsave);

                                                    }
                                                })
                                                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        dialog.cancel();
                                                        CreateORUpdateRecycleView(postionForsave);

                                                    }
                                                }).show();
                                    }else {
                                        databaseHelper.update_PDNEWQTY(BarCodeChecked, String.valueOf(Double.valueOf(UpdateQTY) - 1));
                                        CreateORUpdateRecycleView(postionForsave);

                                    }
                                    break;
                                }else {
                                    Toast.makeText(MainActivity.this, "صنف موزون", Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                        }
                    }

                }
            }
        });
    }

    public void CreateORUpdateRecycleView(int postion){
        Po_Item_For_Recycly = new ArrayList<>();
//        databaseHelper =new DatabaseHelper(this);
//
//        Po_Item_For_Recycly = databaseHelper.Get_Po_Item_That_Has_PDNewQTy();
        Po_Item_For_Recycly = databaseHelper.Get_Items();



        itemDelieveredFormAdapter = new ItemDelieveredFormAdapter(MainActivity.this,Po_Item_For_Recycly);

        recyclerView.setHasFixedSize(true);
        //  if (Po_Item_For_Recycly.size() !=0) {
//        Log.e("scrollToPosition",""+Po_Item_For_Recycly.size());
        //   }
        Double totalprice=0.0;
        int totalpecies=0;
        Double totalCategories=0.0;
        if (Po_Item_For_Recycly.size() !=0) {
            for (int i = 0; i < itemDelieveredFormAdapter.getItemCount(); i++) {
                totalprice += (Double.valueOf(Po_Item_For_Recycly.get(i).getQuantity1()) *
                        Double.valueOf(Po_Item_For_Recycly.get(i).getNetprice1().replace(",","")));
//                Log.e("totalprice", "totalprice" + totalprice);

//                totalCategories++;
                if (Po_Item_For_Recycly.get(i).getBarcode1().substring(0,2).equalsIgnoreCase("23")) {
                    totalpecies++;
                }else {
                    totalpecies +=Double.valueOf(Po_Item_For_Recycly.get(i).getQuantity1());
                }
            }
            txt_total_qty_deliver.setText(String.valueOf(totalpecies));
//            Log.e("totalprice", "totalprice" + totalprice);
//            Log.e("totalpriceeeee", "totalprice" +String.valueOf(totalprice).substring( String.valueOf(totalprice).indexOf(".")+1,
//                    String.valueOf(totalprice).length()));
//            Log.e("totalpriceeeee", "totalprice" +String.valueOf(totalprice).substring( String.valueOf(totalprice).indexOf(".")+1,
//                    String.valueOf(totalprice).length()).length());
            if (String.valueOf(totalprice).substring( String.valueOf(totalprice).indexOf("."),
                    String.valueOf(totalprice).length()).length()>2){
                String tt=String.valueOf(totalprice).substring(0, String.valueOf(totalprice).indexOf(".") + 3);

                txt_total_price.setText(new DecimalFormat("###,##0.00").format(totalprice));
                // TotalPriceForPrint=new DecimalFormat("###,##0.00").format(totalprice);

                Log.e("totalprice", "this is ifff from" + totalprice);
                Log.e("totalprice", "this is iff222f from" + String.valueOf(totalprice).substring(0,
                        String.valueOf(totalprice).indexOf(".") + 3));
                // Log.e("totalprice", "this is ifftuuf from" + String.format("%.2f",totalprice));
                Log.e("totalprice", "this is ifftuuf from" + new DecimalFormat("0.00").format(totalprice));
            }else if (String.valueOf(totalprice).substring(String.valueOf(totalprice).indexOf(".")+1,
                    String.valueOf(totalprice).length()).length()<2) {

//                for (int i=0;String.valueOf(totalprice).substring(String.valueOf(totalprice).indexOf(".")+1,
//                        String.valueOf(totalprice).length()).length()< 2;i++) {
//                    String.valueOf(totalprice)=String.valueOf(totalprice)+"0";
//                }
                Log.e("totalprice", "this is elssssifff from" + totalprice);

                txt_total_price.setText(new DecimalFormat("###,##0.00").format(totalprice));
                //  TotalPriceForPrint=new DecimalFormat("###,##0.00").format(totalprice)
            } else {
                Log.e("totalprice", "this is elssssifff from" + totalprice);

                txt_total_price.setText(new DecimalFormat("###,##0.00").format(totalprice));
                //  TotalPriceForPrint=new DecimalFormat("###,##0.00").format(totalprice);

            }

//            Log.e("totalpricethousand", "this is from" +
//                    String.valueOf(totalprice).substring(0,String.valueOf(totalprice).length()));

//            if (String.valueOf(totalprice).substring(0,String.valueOf(totalprice).indexOf(".")).length()>3){
//                String thousand=String.valueOf(totalprice).substring(0,1)
//                        +","+String.valueOf(totalprice).substring(1,String.valueOf(totalprice).length()-1);
//                txt_total_price.setText(String.valueOf(thousand));
//            }
            totalprice = 0.0;

            txt_total_items_deliver.setText(new DecimalFormat("###,##0.##").format(Double.valueOf(databaseHelper.Get_DistinctBarcodes())));

            //  txt_total_qty_deliver.setText();
        }else {
            txt_total_price.setText("0.0");
            txt_total_items_deliver.setText("0.0");
            txt_total_qty_deliver.setText("0");
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemDelieveredFormAdapter);

        if (postion !=-1 && Po_Item_For_Recycly.size() !=0) {
            if (postion < Po_Item_For_Recycly.size()) {
                recyclerView.scrollToPosition(postion);

                Po_Item_For_Recycly.set(postion, new Po_Item(Po_Item_For_Recycly.get(postion).getBarcode1(), Po_Item_For_Recycly.get(postion).getDescribtion1(),
                        Po_Item_For_Recycly.get(postion).getPrice1(), Po_Item_For_Recycly.get(postion).getTax1(), Po_Item_For_Recycly.get(postion).getDiscount1(),
                        Po_Item_For_Recycly.get(postion).getNetprice1(), Po_Item_For_Recycly.get(postion).getQuantity1(), true));

            }else {
//                itemDelieveredFormAdapter.notifyItemInserted(Po_Item_For_Recycly.size()-1);
                recyclerView.scrollToPosition(Po_Item_For_Recycly.size()-1);

                Po_Item_For_Recycly.set(Po_Item_For_Recycly.size()-1, new Po_Item(Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getBarcode1(), Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getDescribtion1(),
                        Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getPrice1(), Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getTax1(), Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getDiscount1(),
                        Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getNetprice1(), Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getQuantity1(), true));

            }
        }else if (postion == -1 && Po_Item_For_Recycly.size() !=0){
            recyclerView.scrollToPosition(Po_Item_For_Recycly.size()-1);

            Po_Item_For_Recycly.set(Po_Item_For_Recycly.size()-1, new Po_Item(
                    Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getBarcode1(),
                    Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getDescribtion1(),
                    Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getPrice1(),
                    Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getTax1(),
                    Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getDiscount1(),
                    Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getNetprice1(),
                    Po_Item_For_Recycly.get(Po_Item_For_Recycly.size()-1).getQuantity1(), true));

        }
    }

    public void GetDetialsForBarcod() {
        Log.e("editbarcode",""+et_Barcode.getImeActionId());
        Log.e("editbarcode",""+et_Barcode.getId());
        Log.e("editbarcode",""+et_Barcode.getImeActionLabel());

        View view = new View(MainActivity.this);

        Adding_For_First_Time = true ;
        postionForsave = -1 ;
        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        line_of_details.setVisibility(View.VISIBLE);
        linear_edit_barcode.setVisibility(View.VISIBLE);

        Barcode_image.setVisibility(View.GONE);
        btn_back.setVisibility(View.GONE);
        txt_title_of_print.setVisibility(View.GONE);
        linear_delete.setVisibility(View.GONE);
        if (et_Barcode.getText().toString().isEmpty()) {
            et_Barcode.setError("من فضلك أدخل الباركود");
            et_Barcode.setText("");
            et_Barcode.requestFocus();


        }else  {
            Depart="";
            KQTY="00";
            GQTY="000";
            TotalQTYFor23="";
            BarcodeFor23="";
            Depart=et_Barcode.getText().toString().substring(0,2);
            if (Depart.equalsIgnoreCase("23")
                    && et_Barcode.getText().toString().length() ==13) {
                KQTY = et_Barcode.getText().toString().substring(7, 9);
                GQTY = et_Barcode.getText().toString().substring(9, 12);
                TotalQTYFor23 = KQTY + "." + GQTY;

                //                BarcodeFor23 = et_Barcode.getText().toString().replace(TotalQTYFor23.replace(".", ""), "00000");
                BarcodeFor23 = et_Barcode.getText().toString().substring(0,7);


            }else if (Depart.equalsIgnoreCase("23")
                    && et_Barcode.getText().toString().length() !=13) {
                et_Barcode.setError("الباركود أقل أو أكبر من 13");
                et_Barcode.setText("");
                et_Barcode.requestFocus();
            }
            if (Depart.equalsIgnoreCase("23") && Double.valueOf(KQTY+GQTY) <10){
                et_Barcode.setError("تم إدخال قيمه أقل من 10 جرام");
                et_Barcode.setText("");
                et_Barcode.requestFocus();
            }
            else {
                Log.e("editbarcodeeeee", "" + et_Barcode.getText().toString().length());
                Log.e("editbarcodeeeee", "" + KQTY + "." + GQTY);

                editbarcode = et_Barcode.getText().toString();
                Log.e("editbarcode", "" + BarcodeFor23);
                progressBar.setVisibility(View.VISIBLE);
                et_Barcode.setEnabled(false);
                btn_get_detials_for_barcod.setEnabled(false);
                RequestQueue queue = Volley.newRequestQueue(this);
                request = new StringRequest(Request.Method.POST, Constant.GetDetialsURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                String encodedstring = null;
                                try {
                                    encodedstring = URLEncoder.encode(response, "ISO-8859-1");
                                    response = URLDecoder.decode(encodedstring, "UTF-8");

                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                Log.e("onResponse", "response" + response);
                                Log.e("onResponse", "request" + request);
                                try {
                                    progressBar.setVisibility(View.GONE);
                                    et_Barcode.setEnabled(true);
                                    et_Barcode.requestFocus();
                                    btn_get_detials_for_barcod.setEnabled(true);
                                    JSONObject object = new JSONObject(response);
                                    //Log.e("onResponse", "object"+object);

                                    //JSONObject object2 = object.getJSONObject("user");
                                    //String username = object.getString("status");
                                    // Log.e("onResponse", "object2"+object2);

                                    String status = object.getString("status");
                                    Log.d("onResponse", status);

                                    if (status.equalsIgnoreCase("1")) {

                                        List<Po_Item> itemslist = new ArrayList<>();
                                        itemslist = databaseHelper.SellectPo_itemForBarcode(object.getString("barcode"));
                                        DecimalFormat dwith0 = new DecimalFormat("######.00");
                                        dwith0.setRoundingMode(RoundingMode.HALF_UP);
                                        Log.e("onResponse", "responseDepart" + editbarcode);
                                        Log.e("onResponse", "responseDepart" + itemslist.size());

                                        String discounttype = object.getString("discounttype");

                                        if (itemslist.size() == 0 && !Depart.equalsIgnoreCase("23")) {
                                            String netPrice="0";
                                            Double Discountvalue=0.0;
                                            if (discounttype.equalsIgnoreCase("3")) {
//                                                if (Cdate.before(Tooodate) || Cdate.equals(Tooodate)){
//                                                    Log.e("before or eq", "before or eq" );
                                                Discountvalue=Double.valueOf(object.getString("discountV"));

//                                                }else {
//                                                    Discountvalue=0.0;
//                                                    Log.e("before or eq", "not" );
//                                                }
                                                netPrice = String.valueOf((Double.valueOf(object.getString("sell_price"))
                                                        - Double.valueOf(Discountvalue))
                                                        * (1 + (Double.valueOf(object.getString("vatrate")) / 100)));
                                            }else if(discounttype.equalsIgnoreCase("101")){

                                                Discountvalue =Double.valueOf(object.getString("discountV")) *0.01
                                                        *Double.valueOf(object.getString("sell_price"));


                                                netPrice = String.valueOf((Double.valueOf(object.getString("sell_price"))
                                                        - Discountvalue)
                                                        * (1 + (Double.valueOf(object.getString("vatrate")) / 100)));
                                            }else {
                                                Discountvalue=0.0;
                                                netPrice = String.valueOf((Double.valueOf(object.getString("sell_price"))
                                                        - Discountvalue)
                                                        * (1 + (Double.valueOf(object.getString("vatrate")) / 100)));
                                            }
                                            Log.e("onResponse", "response" + netPrice.indexOf("."));
                                            Log.e("onResponse", "response" + netPrice);


                                            databaseHelper.insertPo_Item(object.getString("barcode"),
                                                    object.getString("a_name"), object.getString("sell_price"),
                                                    object.getString("vatrate"), String.valueOf(dwith0.format(Discountvalue)),
                                                    String.valueOf(Double.valueOf(netPrice)), "1.0");
                                            et_Barcode.setText(null);
                                            et_Barcode.requestFocus();
                                            // edit_current_deliver.setText(null);
                                            CreateORUpdateRecycleView(postionForsave);
                                            Toast.makeText(MainActivity.this, "تم", Toast.LENGTH_SHORT).show();

                                        } else if (itemslist.size() != 0 && !Depart.equalsIgnoreCase("23")) {

                                            String NewQTY = String.valueOf(Double.valueOf(itemslist.get(0).getQuantity1())
                                                    + Double.valueOf("1.0"));
                                            databaseHelper.update_PDNEWQTY(object.getString("barcode"), NewQTY);
                                            CreateORUpdateRecycleView(Integer.valueOf(itemslist.get(0).getID1())-1);
                                            Log.e("beforeID", "before or eq"+Integer.valueOf(itemslist.get(0).getID1()) );
                                            et_Barcode.setText(null);
                                            et_Barcode.requestFocus();
                                            Toast.makeText(MainActivity.this, "تم", Toast.LENGTH_SHORT).show();

                                        } else if (Depart.equalsIgnoreCase("23")) {

                                            String netPrice="0.0";
                                            Double Discountvalue=0.0;
                                            if (discounttype.equalsIgnoreCase("3")) {

                                                Discountvalue=Double.valueOf(object.getString("discountV"));

                                                netPrice = String.valueOf((Double.valueOf(object.getString("sell_price"))
                                                        - Double.valueOf(Discountvalue))
                                                        * (1 + (Double.valueOf(object.getString("vatrate")) / 100)));
                                            }else if(discounttype.equalsIgnoreCase("101")){
//
                                                Discountvalue =Double.valueOf(object.getString("discountV")) *0.01
                                                        *Double.valueOf(object.getString("sell_price"));

                                                netPrice = String.valueOf((Double.valueOf(object.getString("sell_price"))
                                                        - Discountvalue)
                                                        * (1 + (Double.valueOf(object.getString("vatrate")) / 100)));
                                            }else {
                                                Discountvalue=0.0;
                                                netPrice = String.valueOf((Double.valueOf(object.getString("sell_price"))
                                                        - Discountvalue)
                                                        * (1 + (Double.valueOf(object.getString("vatrate")) / 100)));
                                            }

                                            Log.e("onResponse", "response" + netPrice.indexOf("."));
                                            Log.e("onResponse", "response" + netPrice);


                                            KQTY = et_Barcode.getText().toString().substring(7, 9);
                                            GQTY = et_Barcode.getText().toString().substring(9, 12);
                                            TotalQTYFor23 = KQTY + "." + GQTY;
                                            Log.e("editbarcodeeeee", "" + KQTY + "." + GQTY);
                                            Log.e("editbarcodeeeee", "" + TotalQTYFor23);
                                            String BarcodeFor23ForInsert = object.getString("barcode").toString().substring(0, 7)
                                                    + KQTY
                                                    + GQTY
                                                    + object.getString("barcode").toString().substring(12, 13);

                                            Log.e("BarcodeFor23ForInsert", BarcodeFor23ForInsert);

                                            databaseHelper.insertPo_Item(BarcodeFor23ForInsert,
                                                    object.getString("a_name"), object.getString("sell_price"),
                                                    object.getString("vatrate"), String.valueOf(dwith0.format(Discountvalue)),
                                                    String.valueOf(Double.valueOf(netPrice)), TotalQTYFor23);
                                            et_Barcode.setText(null);
                                            et_Barcode.requestFocus();
                                            // edit_current_deliver.setText(null);
                                            CreateORUpdateRecycleView(postionForsave);
                                            Toast.makeText(MainActivity.this, "تم", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "الباركود غير موجود", Toast.LENGTH_SHORT).show();
                                        et_Barcode.setText("");
                                        et_Barcode.requestFocus();
                                    }

                                } catch (JSONException e) {
                                    progressBar.setVisibility(View.GONE);
                                    btn_get_detials_for_barcod.setEnabled(true);
                                    et_Barcode.setEnabled(true);

                                    et_Barcode.setError("لم يتم أضافة الباركود .. حاول مره اخرى");
                                    et_Barcode.setText("");
                                    et_Barcode.requestFocus();
                                    Toast.makeText(getBaseContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                NetworkResponse response = error.networkResponse;
                                String errorMsg = "";
                                if (response != null && response.data != null) {
                                    String errorString = new String(response.data);
                                    et_Barcode.setEnabled(true);
                                    btn_get_detials_for_barcod.setEnabled(true);
                                    et_Barcode.setError("لم يتم أضافة الباركود .Net. حاول مره اخرى");
                                    et_Barcode.setText("");
                                    et_Barcode.requestFocus();
                                    Log.i("log error", errorString);
                                }
                            }
                        }
                ) {
                    @Override
                    protected VolleyError parseNetworkError(VolleyError volleyError) {
                        Log.i("log error no respon", "se6");
                        Log.i("log error no respon", ""+volleyError);
                        volleyErrorPublic=volleyError;
                        return super.parseNetworkError(volleyError);
                    }

                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<String, String>();
                        if (Depart.equalsIgnoreCase("23")) {
                            params.put("Scales", "23");
                            params.put("Barcode", BarcodeFor23 + "00000");
                        //    params.put("Branch", BarcodeFor23 + "00000");
                            Log.e("bbbbbbbbbbbb", "ifff" + et_Barcode.getText().toString().replace(TotalQTYFor23, "00000"));
                        } else {
                            params.put("Scales", "00");
                            params.put("Barcode", editbarcode);
                            Log.e("bbbbbbbbbbbb", "ifff" + Depart);
                        }


                        if (Branch.equalsIgnoreCase("1")) {
                            params.put("Branch", "00001");
                        }else if (Branch.equalsIgnoreCase("2")) {
                            params.put("Branch", "00002");
                        }else if (Branch.equalsIgnoreCase("3")) {
                            params.put("Branch", "00003");
                        }
                        Log.i("sending ", params.toString());
                        // Log.i("sending ", ""+request);

                        return params;
                    }

                };


                // Add the realibility on the connection.
                request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
                queue.add(request);

                if (volleyErrorPublic != null) {
                    Toast.makeText(MainActivity.this, "لم يتم الاتصال بالسيرفر", Toast.LENGTH_SHORT).show();

                }

            }
        }
    }
    private Bitmap textToImage(String text, int width, int height) throws WriterException, NullPointerException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.DATA_MATRIX.QR_CODE,
                    width, height, null);
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        int colorWhite = 0xFFFFFFFF;
        int colorBlack = 0xFF000000;

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colorBlack : colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, width, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


    @AfterPermissionGranted(RC_BLUETOOTH)
    private void setupBluetooth() {
        String[] params = {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};
        if (!EasyPermissions.hasPermissions(this, params)) {
            EasyPermissions.requestPermissions(this, "You need bluetooth permission",
                    RC_BLUETOOTH, params);
            return;
        }
        mService = new BluetoothService(this, new BluetoothHandler(this));


    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @Override
    public void onDeviceConnected() {
        isPrinterReady = true;
        print_barcode_image.setVisibility(View.VISIBLE);
        tvStatus.setText(" تم الاتصال");
        tvStatus.setTextColor(getResources().getColor(R.color.third_white));

        if (!isPrinterReady) {
            connectifthereisnoconnection();
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:MM");
            Serialnum = sdf.format(new Date());
            List<Po_Item> itemslist = new ArrayList<>();

            itemslist = databaseHelper.Get_Items();
            printImage(itemslist);
            WriteIntableOfSqlServer();
        }
    }

    @Override
    public void onDeviceConnecting() {
        tvStatus.setText(" محاولة الاتصال...");
        print_barcode_image.setVisibility(View.GONE);
    }

    @Override
    public void onDeviceConnectionLost() {
        isPrinterReady = false;
        tvStatus.setText("من فضلك تأكد من تشغيل الطابعة");
        Toast.makeText(this, "تم فصل الطابعة .. من فضلك تأكد من تشغيل الطابعة", Toast.LENGTH_SHORT).show();

        tvStatus.setTextColor(getResources().getColor(R.color.redcolor));
        if (!isPrinterReady) {
            connectifthereisnoconnection();
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:MM");
            Serialnum = sdf.format(new Date());
            List<Po_Item> itemslist = new ArrayList<>();

            itemslist = databaseHelper.Get_Items();
            printImage(itemslist);
            WriteIntableOfSqlServer();
        }
    }

    @Override
    public void onDeviceUnableToConnect() {
        Toast.makeText(this, " لم يتم الاتصال .. تأكد من تشغيل الطابعة", Toast.LENGTH_SHORT).show();
        tvStatus.setText(" لم يتم الاتصال");
        print_barcode_image.setVisibility(View.VISIBLE);
        tvStatus.setTextColor(getResources().getColor(R.color.redcolor));
        if (!isPrinterReady) {
            connectifthereisnoconnection();
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:MM");
            Serialnum = sdf.format(new Date());
            List<Po_Item> itemslist = new ArrayList<>();

            itemslist = databaseHelper.Get_Items();
            printImage(itemslist);
            WriteIntableOfSqlServer();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_ENABLE_BLUETOOTH:


                if (resultCode == RESULT_OK) {
                    Log.e(TAG, "onActivityResult: bluetooth active");
                    connectifthereisnoconnection();
                } else
                    Log.e(TAG, "onActivityResult: bluetooth must be active to use this feature");
                connectifthereisnoconnection();
                break;
            case RC_CONNECT_DEVICE:
                if (resultCode == RESULT_OK) {
                    String address = data.getExtras().getString(DeviceActivity.EXTRA_DEVICE_ADDRESS);
                    Log.e("onActivi","address.isEmpty()"+address);

                    if (address.contains("device connected!") || address.contains("Backpressed") ||
                            address.contains("يوجد طابعة متصلة!")){
                        print_barcode_image.setVisibility(View.VISIBLE);
                        Log.e("onActivi","address.isEmpty()"+address);
                    }else {
                        BluetoothDevice mDevice = mService.getDevByMac(address);
                        mService.connect(mDevice);
                    }
                }
                break;
        }
    }

    public void ConnectBluetooth() {
        if (!mService.isAvailable()) {
            Log.i(TAG, "printText: device not support bluetooth");
            return;
        }
        if (isPrinterReady) {
            if (et_Barcode.getText().toString().isEmpty()) {
                Toast.makeText(this, "Cant print null text", Toast.LENGTH_SHORT).show();
                return;
            }else {

                Content += editbarcode+"\r";
                et_Barcode.setText("");
            }
        } else {
            if (mService.isBTopen())
                startActivityForResult(new Intent(this, DeviceActivity.class),
                        RC_CONNECT_DEVICE);
            else
                requestBluetooth();

        }
    }

    //print byte[]
    private void printText22(byte[] msg,List<Po_Item> itemslist) {
        if (PrintAgainNumber>0 && numberofBarcodeImagesToprint ==numberofBarcodeImages){
            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            mService.sendMessage("Copy " +PrintAgainNumber , "");
        }
        if (numberofBarcodeImagesToprint ==numberofBarcodeImages) {

            SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss:MM");
            String Time = data.format(new Date());
            mService.write(PrinterCommands.ESC_ALIGN_CENTER);
            if (Branch.equalsIgnoreCase("1")){
                mService.sendMessage(UserName + "    " + "Zayed", "");
            }else if (Branch.equalsIgnoreCase("3")){
                mService.sendMessage(UserName + "    " + "Asher", "");
            }else {
                mService.sendMessage(UserName + "    " + Branch, "");
            }
            mService.sendMessage( Time,"");

        }

        mService.write(PrinterCommands.ESC_ENTER);

        mService.sendMessage( "( "+nuberOfImgeForPrint+" )"+"of"+"( "+numberofBarcodeImagesToprint+" )","");

        mService.write(PrinterCommands.FEED_LINE);
        mService.write(PrinterCommands.ESC_ALIGN_CENTER);
        mService.write(msg);

        //mService.write(PrinterCommands.FEED_LINE);
        if (numberofBarcodeImages ==1){
            mService.write(PrinterCommands.ESC_ALIGN_LEFT);
            mService.sendMessage(" Num of Pieces   "+totalofBarcodes,"");

            mService.write(PrinterCommands.ESC_ALIGN_LEFT);

        //    mService.sendMessage(" Total Price     "+txt_total_price.getText().toString()+" L.E","");
            mService.sendMessage("\n"+"\n"+"\n"+"\n","");

        }else
            mService.sendMessage("\n"+"\n"+"\n"+"\n","");


    }

    public void printImage(List<Po_Item> itemslist) {
        nuberOfImgeForPrint=0;
        totalofBarcodes = 0.0;
        numberofBarcodeImages = 0;
        numberofBarcodeImagesToprint = 0;



        for (int i = 0; i < itemslist.size(); i++) {
            if (itemslist.get(i).getBarcode1().substring(0,2).equalsIgnoreCase("23")) {
                totalofBarcodes++;
            }else {
                totalofBarcodes += Double.valueOf(itemslist.get(i).getQuantity1());

            }
        }


        if (Double.valueOf(String.valueOf(totalofBarcodes /30).substring(
                String.valueOf(totalofBarcodes /30).indexOf("."), String.valueOf(totalofBarcodes /30).length())) > 0) {

            if (String.valueOf(totalofBarcodes /30).substring(0,
                    String.valueOf(totalofBarcodes /30).indexOf(".")).equalsIgnoreCase("0")) {

                numberofBarcodeImages = 1;

            } else {

                numberofBarcodeImages = (Integer.valueOf((int) (totalofBarcodes /30))) + 1;

            }
        } else {
            numberofBarcodeImages = (int) (totalofBarcodes /30);
        }
        numberofBarcodeImagesToprint = numberofBarcodeImages;
        testforitemssize = 0.0;
        SumOfBarcodesadded = 0;

        Log.d("mmmmmmmmmfromfun", ""+itemslist.size());

        for (int i = 0; i < itemslist.size(); i++) {
//             if (itemslist.size()==50*p)break;
             if (itemslist.get(i).getBarcode1().substring(0,2).equalsIgnoreCase("23")) {

                Content += itemslist.get(i).getBarcode1() + "\r";
                SumOfBarcodesadded++;
                testforitemssize++;
                if (SumOfBarcodesadded ==30) {
                    PrintBarcode(Content,itemslist);
                    numberofBarcodeImages--;
                    Content = "\r";
                    SumOfBarcodesadded = 0;
//                    Toast.makeText(this, "if123", Toast.LENGTH_SHORT).show();
                }
            }else {
                if (itemslist.get(i).getQuantity1().contains(".")) {
                    if (Integer.valueOf(itemslist.get(i).getQuantity1().substring(0, itemslist.get(i).getQuantity1().indexOf("."))) == 1) {
                        Content += itemslist.get(i).getBarcode1() + "\r";
                        SumOfBarcodesadded++;
                        testforitemssize++;
                        if (SumOfBarcodesadded ==30) {
                            PrintBarcode(Content,itemslist);
                            numberofBarcodeImages--;
                            Content = "\r";
                            SumOfBarcodesadded = 0;

                        }
                        Log.e("PrinterCo1", "" + Integer.valueOf(itemslist.get(i).getQuantity1().substring(0, itemslist.get(i).getQuantity1().indexOf("."))));
                    } else if (Integer.valueOf(itemslist.get(i).getQuantity1().substring(0, itemslist.get(i).getQuantity1().indexOf("."))) == 0) {
                        if (Integer.valueOf(itemslist.get(i).getQuantity1().substring(
                                itemslist.get(i).getQuantity1().indexOf(".") + 1
                                , itemslist.get(i).getQuantity1().length())) > 0) {
                            // Log.e("PrinterCo2", "" + Integer.valueOf(itemslist.get(i).getQuantity1().substring(0, itemslist.get(i).getQuantity1().indexOf("."))));
                            Content += itemslist.get(i).getBarcode1() + "\r";
                            SumOfBarcodesadded++;
                            testforitemssize++;
                            if (SumOfBarcodesadded ==30) {
                                PrintBarcode(Content,itemslist);
                                numberofBarcodeImages--;
                                Content = "\r";
                                SumOfBarcodesadded = 0;

                            }
                        }
                    } else {
                        for (int j = 0; j < Integer.valueOf(itemslist.get(i).getQuantity1().substring(0, itemslist.get(i).getQuantity1().indexOf("."))); j++) {
                            Content += itemslist.get(i).getBarcode1() + "\r";
                            SumOfBarcodesadded++;
                            testforitemssize++;
                            if (SumOfBarcodesadded ==30) {
                                PrintBarcode(Content,itemslist);
                                numberofBarcodeImages--;
                                Content = "\r";
                                SumOfBarcodesadded = 0;

                            }
                        }
                    }
                } else {
                    for (int j = 0; j < Integer.valueOf(itemslist.get(i).getQuantity1()); j++) {
                        Content += itemslist.get(i).getBarcode1() + "\r";
                        SumOfBarcodesadded++;
                        testforitemssize++;

                        if (SumOfBarcodesadded ==30) {
                            PrintBarcode(Content,itemslist);
                            numberofBarcodeImages--;
                            Content = "\r";
                            SumOfBarcodesadded = 0;

                        }
                    }
            }
        }
    }
            if (!Content.isEmpty() && numberofBarcodeImages == 1) {
                PrintBarcode(Content,itemslist);
                numberofBarcodeImages--;
                Content = "\r";
                SumOfBarcodesadded = 0;
            }
    }

public void PrintBarcode(String ContentP,List<Po_Item> itemslist){
    nuberOfImgeForPrint++;
    if (!mService.isAvailable()) {
        Log.i(TAG, "printText: device not support bluetooth");
        return;
    }
    if (isPrinterReady) {
                      Log.e("Print Photo error", "the"+ContentP.length());
            //   String ContentP="87303322";
            Log.e("Print Photo error", "the"+ContentP.charAt(ContentP.length()-1)+ContentP.length());
            ContentP=ContentP.substring(0,ContentP.length()-1);
            Log.e("Print Photo error", "the"+ContentP.charAt(ContentP.length()-1)+ContentP.length());

            try {
                bitmap = textToImage(ContentP, 250, 250);
                ContentP="";
            } catch (WriterException e) {
                e.printStackTrace();
            }
            line_of_details.setVisibility(View.GONE);
        linear_edit_barcode.setVisibility(View.GONE);

        Barcode_image.setVisibility(View.VISIBLE);
        btn_back.setVisibility(View.VISIBLE);
            txt_title_of_print.setVisibility(View.VISIBLE);

            linear_delete.setVisibility(View.VISIBLE);

        if (numberofBarcodeImages ==1){
             Barcode_image.setImageBitmap(bitmap);
            }
            try {
                if(  bitmap !=null){
                    byte[] command = Utils.decodeBitmap(bitmap);
                    Log.d("BitmapFa", ""+bitmap.getWidth()+""+bitmap.getHeight());
                    printText22(command,itemslist);
                }else{
                    Log.e("Print Photo error", "the file isn't exists");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("PrintTools", "the file isn't exists");
            }

    } else {

        //connectifthereisnoconnection();
    }

}
public void connectifthereisnoconnection(){
     if (mService.isBTopen()) {
         startActivityForResult(new Intent(this, DeviceActivity.class),
                 RC_CONNECT_DEVICE);
     } else
        requestBluetooth();
}
    public String convertBitmap(Bitmap inputBitmap) {

        int mWidth = inputBitmap.getWidth();
        int mHeight = inputBitmap.getHeight();

        convertArgbToGrayscale(inputBitmap, mWidth, mHeight);
        String mStatus = "ok";
        return mStatus;

    }

    private void convertArgbToGrayscale(Bitmap bmpOriginal, int width,
                                        int height) {
        int pixel;
        int k = 0;
        int B = 0, G = 0, R = 0;
        dots = new BitSet();
        try {

            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    // get one pixel color
                    pixel = bmpOriginal.getPixel(y, x);

                    // retrieve color of all channels
                    R = Color.red(pixel);
                    G = Color.green(pixel);
                    B = Color.blue(pixel);
                    // take conversion up to one single value by calculating
                    // pixel intensity.
                    R = G = B = (int) (0.299 * R + 0.587 * G + 0.114 * B);
                    // set bit into bitset, by calculating the pixel's luma
                    if (R < 55) {
                        dots.set(k);//this is the bitset that i'm printing
                    }
                    k++;

                }


            }


        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void requestBluetooth() {
        if (mService != null) {
            if (!mService.isBTopen()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, RC_ENABLE_BLUETOOTH);
            }
        }
    }
    public void ViewItems(View view) {
        Intent Gotoviewitems=new Intent(MainActivity.this,CheckItemFormActivity.class);
        startActivity(Gotoviewitems);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.want_to_signin))
                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent Go_Back= new Intent(MainActivity.this,LoginActivity.class);
                        Go_Back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(Go_Back);

                    }
                })
                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();


                    }
                }).show();


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void WriteIntableOfSqlServer(){

        RequestQueue queue = Volley.newRequestQueue(this);
        // String URL = Constant.LoginURL;
        request = new StringRequest(Request.Method.POST, Constant.WriteIntableURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("onResponseUmmm", response);
                        Log.e("onResponseUR", " "+request);

                        try {

                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");
                            Log.d("onResponse", status);

                            String message = object.getString("message");
                            Log.d("onResponse", message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse response = error.networkResponse;
                        String errorMsg = " ";
                        if (response != null && response.data != null) {
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                JSONObject obj=null;
                JSONArray array=new JSONArray();
                ArrayList arrayreqest=new ArrayList();

                Po_Items_For_LogsArray=new ArrayList<Po_item_for_log>();
                Po_Item_For_Recycly=new ArrayList<Po_Item>();
                Po_Item_For_Recycly=databaseHelper.Get_Items();

                //if (!LastOrderArry.contains(Serialnum))
                databaseHelper.insertSerialNumber(Serialnum);
               // LastOrderArry.add(Serialnum);

                for (int i=0;i<Po_Item_For_Recycly.size();i++) {

                    Po_item_for_log Po_item_for_log= new Po_item_for_log();
                    Po_item_for_log.setTime( Serialnum);
                    Po_item_for_log.setBarcode(Po_Item_For_Recycly.get(i).getBarcode1());
                    Po_item_for_log.setDescription( Po_Item_For_Recycly.get(i).getDescribtion1());
                    Po_item_for_log.setSell_price( Po_Item_For_Recycly.get(i).getPrice1());
                    Po_item_for_log.setTax( Po_Item_For_Recycly.get(i).getTax1());
                    Po_item_for_log.setDiscount_Value(Po_Item_For_Recycly.get(i).getDiscount1());
                    Po_item_for_log.setTotalPrice(String.valueOf(Double.valueOf(Po_Item_For_Recycly.get(i).getQuantity1())
                            * Double.valueOf(Po_Item_For_Recycly.get(i).getNetprice1())));
                    Po_item_for_log.setQuantity( Po_Item_For_Recycly.get(i).getQuantity1());


                    Log.d("Build.MODELMacRCV", "RCV");

                    Po_Items_For_LogsArray.add(Po_item_for_log);

                }

                Gson gson = new GsonBuilder().create();
                JsonArray equipmentJsonArray = gson.toJsonTree(Po_Items_For_LogsArray).getAsJsonArray();

                //From_Sap_Or_Not=false;
                params.put("RequestArray", equipmentJsonArray.toString());

                params.put("Time",  Serialnum);
                params.put("Branch",  Branch);
                params.put("UserName", UserName);

                return params;
            }

        };

        // Add the realibility on the connection.
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

        // Start the request immediately
        queue.add(request);

    }


    public void RetrieveFromSqlServer(final String LastOrderId1){

        RequestQueue queue = Volley.newRequestQueue(this);
        // String URL = Constant.LoginURL;
        request = new StringRequest(Request.Method.POST, Constant.RetrieveFromttableURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("onResponseUmmm", response);
                        Log.e("onResponseUR", " "+request);

                        try {

                            JSONObject object = new JSONObject(response);
                            String Barcodesize = object.getString("Barcodesize");
                            Log.d("onResponsemmmm", Barcodesize);
                            List<Po_Item> itemslist = new ArrayList<>();


                            if (Double.valueOf(Barcodesize)>0){

                                  for (int i=0;i<Double.valueOf(Barcodesize);i++){
                                      itemslist.add(new Po_Item(object.getString("Barcode"+i),object.getString("Quantity"+i)));
                                  }
                                Log.d("mmmmmmmmm", ""+itemslist.size());

                                if (!isPrinterReady) {
                                    connectifthereisnoconnection();
                                }else {
                                    printImage(itemslist);
                                }

                            }else {
                                Toast.makeText(MainActivity.this, "لايوجد بيانات لهذا السيريال", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse response = error.networkResponse;
                        String errorMsg = " ";
                        if (response != null && response.data != null) {
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                
                params.put("Time", LastOrderId1);


                //Log.e("Requestparams",""+obj);
                return params;
            }

        };

        // Add the realibility on the connection.
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

        // Start the request immediately
        queue.add(request);

    }


    public void WriteInprinttableOfSqlServer(){

        RequestQueue queue = Volley.newRequestQueue(this);
        // String URL = Constant.LoginURL;
        request = new StringRequest(Request.Method.POST, Constant.WriteInprinttableURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("onResponseU", response);
                        Log.e("onResponseUR", " "+request);

                        try {

                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");
                            Log.d("onResponse", status);

                            String message = object.getString("message");
                            Log.d("onResponse", message);

                               /* if (status.equalsIgnoreCase("1")){
                                    Intent gotomain =new Intent(UploadForTransferActivity.this,MainActivity.class);
                                    startActivity(gotomain);
                                }else {
                                    Toast.makeText(UploadForTransferActivity.this, "Your User Or Password Is Wrong", Toast.LENGTH_SHORT).show();
                                }*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse response = error.networkResponse;
                        String errorMsg = " ";
                        if (response != null && response.data != null) {
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                JSONObject obj = null;
                JSONArray array = new JSONArray();
                ArrayList arrayreqest = new ArrayList();


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:MM");
                String Date= sdf.format(new Date());


                /*params.put("Barcode", Date);
                params.put("Description", Date);
                params.put("Sell_price", Date);
                params.put("Tax", Date);
                params.put("Discount_Value", Date);
                params.put("Quantity", Date);
                params.put("TotalPrice", Date);*/
                params.put("Serialnum", Serialnum);
                params.put("Time", Date);
                params.put("Username", UserName);

                //Log.e("Requestparams",""+obj);
                return params;
            }

        };

        // Add the realibility on the connection.
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

        // Start the request immediately
        queue.add(request);

    }


}