package com.example.qbapp.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qbapp.EditItemActivity;
import com.example.qbapp.Helper.DatabaseHelper;
import com.example.qbapp.MainActivity;
import com.example.qbapp.R;
import com.example.qbapp.model.Po_Item;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    TextView tvStatus, txt_descripation, txt_price_item,
            txt_tax_item,txt_discount,txt_netprice,txt_total_netprice,
            txt_code_item,txt_total_qty;
    ArrayList<String> LastOrderArry;
Context context;
    String Barcode,UserName,Branch,total_price,LastOrderId="";
    ImageView closeimage;
    DatabaseHelper databaseHelper;
    Button btn_edit,btn_delete;
    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         Barcode = getArguments().getString("Barcode");
        UserName = getArguments().getString("UserName");
        Branch= getArguments().getString("Branch");
       // LastOrderArry = (ArrayList<String>) getArguments().getSerializable("LastOrderIdArray");
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeimage=view.findViewById(R.id.image_close);
        txt_code_item=view.findViewById(R.id.txt_code_item);
        txt_descripation=view.findViewById(R.id.txt_descripation);
        txt_price_item=view.findViewById(R.id.txt_price_item);
        txt_tax_item=view.findViewById(R.id.txt_tax_item);
        txt_discount=view.findViewById(R.id.txt_discount);
        txt_netprice=view.findViewById(R.id.txt_netprice);
        txt_total_qty=view.findViewById(R.id.txt_total_qty);
        txt_total_netprice=view.findViewById(R.id.txt_total_netprice);
        btn_edit=view.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_PDNEWQTY();
            }
        });
        btn_delete=view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_PDNEWQTY();
            }
        });
         databaseHelper=new DatabaseHelper(getActivity());
        List<Po_Item> Po_Item_List = databaseHelper.SellectPo_itemForBarcode(Barcode);

        txt_code_item.setText(Barcode);
        if (Po_Item_List.size()==0){
            Intent goback=new Intent(getActivity(), MainActivity.class);
            goback.putExtra("UserName",UserName);
            goback.putExtra("Branch",Branch);
            goback.putExtra("LastOrderId",LastOrderId);

            startActivity(goback);
        }else {
            DecimalFormat dwith0 = new DecimalFormat("###,##0.00");
            dwith0.setRoundingMode(RoundingMode.HALF_UP);
            txt_descripation.setText(Po_Item_List.get(0).getDescribtion1());
            txt_price_item.setText(new DecimalFormat("###,##0.000").format(Double.valueOf(Po_Item_List.get(0).getPrice1())));
            txt_tax_item.setText("%"+Po_Item_List.get(0).getTax1());
            txt_discount.setText(dwith0.format(Double.valueOf(Po_Item_List.get(0).getDiscount1())));
            txt_netprice.setText(new DecimalFormat("###,##0.000").format(Double.valueOf(Po_Item_List.get(0).getNetprice1().replace(",",""))));
            txt_total_qty.setText(new DecimalFormat("###,##0.000").format(Double.valueOf(Po_Item_List.get(0).getQuantity1())));

            total_price = String.valueOf(Double.valueOf(Po_Item_List.get(0).getNetprice1().replace(",","")) *
                    Double.valueOf(Po_Item_List.get(0).getQuantity1()));

            if (total_price.substring(String.valueOf(total_price).indexOf("."),
                    String.valueOf(total_price).length()).length() > 2) {

                total_price = String.valueOf(total_price).substring(0,
                        String.valueOf(total_price).indexOf(".") + 3);

                txt_total_netprice.setText(String.valueOf(dwith0.format((Double.valueOf(total_price)))));

            } else if (total_price.substring(total_price.indexOf(".") + 1,
                    String.valueOf(total_price).length()).length() < 2) {

//                for (int i=0;String.valueOf(totalprice).substring(String.valueOf(totalprice).indexOf(".")+1,
//                        String.valueOf(totalprice).length()).length()< 2;i++) {
//                    String.valueOf(totalprice)=String.valueOf(totalprice)+"0";
//                }
                Log.e("totalprice", "this is from" + total_price);

                txt_total_netprice.setText(String.valueOf(dwith0.format(Double.valueOf(total_price))));
            }
        }
        closeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.popBackStack();

                MainActivity mainActivity=(MainActivity) getActivity();
                if (mainActivity != null){
                    mainActivity.CreateORUpdateRecycleView(2);
                    Log.e("nnnnnnnnn","");
                }
//                Intent goback=new Intent(getActivity(), MainActivity.class);
//                goback.putExtra("UserName",UserName);
//                goback.putExtra("Branch",Branch);
//                goback.putExtra("LastOrderId",LastOrderId);
//
//                startActivity(goback);
            }
        });

    }

    public void Delete_PDNEWQTY() {

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.want_to_delete))
                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        databaseHelper.DeletePo_item(Barcode);
//                        FragmentManager fm=getActivity().getSupportFragmentManager();
//                        fm.popBackStack();
//                        Intent goback=new Intent(getActivity(), MainActivity.class);
//                        goback.putExtra("UserName",UserName);
//                        goback.putExtra("Branch",Branch);
//                        goback.putExtra("LastOrderId",LastOrderId);
//
//                        startActivity(goback);

                        FragmentManager fm=getActivity().getSupportFragmentManager();
                        fm.popBackStack();

                        MainActivity mainActivity=(MainActivity) getActivity();
                        if (mainActivity != null){
                            mainActivity.CreateORUpdateRecycleView(2);
                            Log.e("nnnnnnnnn","");
                        }

                    }
                })
                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();

                    }
                }).show();

    }

    public void Edit_PDNEWQTY() {

        String Depart=Barcode.substring(0,2);
        if (!Depart.equalsIgnoreCase("23")) {
            Intent GoToEdit = new Intent(getActivity(), EditItemActivity.class);
            GoToEdit.putExtra("BarCode",Barcode);
            GoToEdit.putExtra("UserName",UserName);
            GoToEdit.putExtra("Branch",Branch);
            GoToEdit.putExtra("LastOrderId",LastOrderId);
            startActivity(GoToEdit);

        }else {
            Toast.makeText(getActivity(), "صنف موزون", Toast.LENGTH_LONG).show();

            Intent goback=new Intent(getActivity(), MainActivity.class);
            goback.putExtra("UserName",UserName);
            goback.putExtra("Branch",Branch);
            goback.putExtra("LastOrderId",LastOrderId);
            startActivity(goback);

        }
    }
}
