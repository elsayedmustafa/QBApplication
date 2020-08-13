package com.example.qbapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qbapp.MainActivity;
import com.example.qbapp.R;
import com.example.qbapp.model.Po_Item;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class ItemDelieveredFormAdapter extends RecyclerView.Adapter<ItemDelieveredFormAdapter.MyViewHolder> {

    private List<Po_Item> ItemsList;
    Po_Item  po_item;
Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        LinearLayout linear_of_recycle;
        public TextView txt_netprice_for_all_quantity, txt_descripation, txt_price_item,
                txt_tax_item,txt_discount,txt_quantity,txtean11;

        public MyViewHolder(View view) {
            super(view);
            linear_of_recycle=view.findViewById(R.id.linear_of_recycle);
//            checkBox = view.findViewById(R.id.checkbox_item);
//            txtean11 =  view.findViewById(R.id.txt_ean11);
            txt_descripation = view.findViewById(R.id.txt_descripation);
            //txt_price_item =  view.findViewById(R.id.txt_price_item);
            //txt_tax_item =  view.findViewById(R.id.txt_tax_item);
          //  txt_discount = view.findViewById(R.id.txt_discount);
            txt_quantity =  view.findViewById(R.id.txt_quantity);
            txt_netprice_for_all_quantity =  view.findViewById(R.id.txt_netprice_for_all_quantity);
        }
    }


    public ItemDelieveredFormAdapter(Context context , List<Po_Item> moviesList) {
        this.context=context;
        this.ItemsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_for_po_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
         po_item = ItemsList.get(position);

//        if (po_item.getIschecked()){
//            holder.checkBox.setChecked(true);
//        }else {
//            holder.checkBox.setChecked(false);
//        }
//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.checkBox.isChecked()){
//                    //po_item.setChecked_Item(true);
//                    ItemsList.get(position).setIschecked(true);
//                  //  Log.e("editChecked",""+ItemsList.get(position).getMATERIAL1());
//
//                }else if (!holder.checkBox.isChecked()){
//                    //po_item.setChecked_Item(false);
//                    ItemsList.get(position).setIschecked(false);
//                   // Log.e("editCheckedUN",""+ItemsList.get(position).getMATERIAL1());
//                }
//            }
//        });
//        ItemsList.get(position).setIschecked(false);
//        Log.e("mmmmmmmmmmiddd",""+holder.getItemId());

//        holder.linear_of_recycle.setBackgroundColor(context.getResources().getColor(R.color.redcolor));

        if (ItemsList.get(position).getIschecked() == true) {
            holder.linear_of_recycle.setBackgroundColor(context.getResources().getColor(R.color.main_background_color));
            holder.txt_descripation.setTextColor(context.getResources().getColor(R.color.third_white));
            holder.txt_quantity.setTextColor(context.getResources().getColor(R.color.third_white));
            holder.txt_netprice_for_all_quantity.setTextColor(context.getResources().getColor(R.color.third_white));

        }else if (ItemsList.get(position).getIschecked() == false){
            holder.linear_of_recycle.setBackground(context.getResources().getDrawable(R.drawable.module_bg_items));
            holder.txt_descripation.setTextColor(context.getResources().getColor(R.color.black));
            holder.txt_quantity.setTextColor(context.getResources().getColor(R.color.black));
            holder.txt_netprice_for_all_quantity.setTextColor(context.getResources().getColor(R.color.black));
        }

            holder.linear_of_recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.
                if (ItemsList.get(position).getIschecked() == false) {
                   // holder.linear_of_recycle.setBackgroundColor(context.getResources().getColor(R.color.first_blue));
                    //po_item.setIschecked(true);
                   for (int i=0 ; i<ItemsList.size() ; i++){
                       Log.e("mmmmmmmmmm",""+ItemsList.get(i).getIschecked());
//                       v.notify();
                       if (i==position){
                           Log.e("mmmmmmmmmmif",""+position);
                           Log.e("mmmmmmmmmmif","sellected"+v.isSelected());
                           ItemsList.get(position).setIschecked(true);
                         //  holder.linear_of_recycle.setBackgroundColor(context.getResources().getColor(R.color.first_blue));
//                            v.setBackgroundColor(context.getResources().getColor(R.color.first_blue));
                           Log.e("mmmmmmmmmmI","I"+v.getTag());
//                           v.findViewById(R.id.linear_of_recycle)
//                                   .setOnClickListener(new View.OnClickListener(){
//                                       @Override
//                                       public void onClick(View v) {
//                                           holder.linear_of_recycle.setBackgroundColor(context.getResources().getColor(R.color.first_blue));
//                                       }
//                           });

                       }else if (i != position){

                           ItemsList.get(i).setIschecked(false);
                           Log.e("mmmmmmmmmmI","I"+v.getTag());

                           holder.linear_of_recycle.setBackgroundColor(context.getResources().getColor(R.color.third_white));

//                           v.findViewById(R.id.linear_of_recycle)
//                                   .setOnClickListener(new View.OnClickListener(){
//                                       @Override
//                                       public void onClick(View v) {
//                                           holder.linear_of_recycle.setBackgroundColor(context.getResources().getColor(R.color.third_white));
//                                       }
//                                   });
                       }
//                       Log.e("mmmmmmmmmm",""+ItemsList.get(i).getIschecked());
                   }
                    Log.e("mmmmmmmmmm",""+position);
                }else if (ItemsList.get(position).getIschecked() == true){
//                    holder.linear_of_recycle.setBackgroundColor(context.getResources().getColor(R.color.third_white));
                   // po_item.setIschecked(false);
                    ItemsList.get(position).setIschecked(false);
                }
                notifyDataSetChanged();
            }
        });
       // holder.checkBox.setVisibility(View.VISIBLE);
        holder.txt_descripation.setText(po_item.getDescribtion1());
//        holder.txtean11.setText(po_item.getBarcode1());
//        holder.txt_price_item.setText(po_item.getPrice1());
//        holder.txt_discount.setText(po_item.getDiscount1());
//        holder.txt_tax_item.setText(po_item.getTax1());

        DecimalFormat dwith0 = new DecimalFormat("###,##0.00");
        dwith0.setRoundingMode(RoundingMode.HALF_UP);

        String Toatalprice = String.valueOf(Double.valueOf(po_item.getQuantity1())* Double.valueOf(po_item.getNetprice1().replace(",","")));
        Log.e("vvvvvvvvvv",""+Toatalprice);
        Log.e("vvvvvvvvvv0lk",""+Toatalprice.substring(Toatalprice.indexOf("."),Toatalprice.length()).length());
        Log.e("vvvvvvvvvv02k",""+Toatalprice.substring(Toatalprice.indexOf("."),Toatalprice.length()));
        if (Toatalprice.substring(Toatalprice.indexOf("."),Toatalprice.length()).length()>2) {

//            Toatalprice =  Toatalprice.substring(0, String.valueOf(Double.valueOf(po_item.getQuantity1()) *
//                    Double.valueOf(po_item.getNetprice1())).indexOf(".") + 3);
            Log.e("vvvvvvvvvvif",""+Toatalprice);
            holder.txt_netprice_for_all_quantity.setText(dwith0.format(Double.valueOf(Toatalprice)));

        }else if (Toatalprice.substring(Toatalprice.indexOf(".")+1,Toatalprice.length()).length()<2) {
//            for (int i=0;Toatalprice.substring(Toatalprice.indexOf(".")+1,Toatalprice.length()).length()< 2;i++) {
//                Toatalprice=Toatalprice+"0";
//            }
            Log.e("vvvvvvvvvvifelmmmmse",""+Toatalprice);
            holder.txt_netprice_for_all_quantity.setText(dwith0.format(Double.valueOf(Toatalprice)));
        }


        String TotalQTY=po_item.getQuantity1();
        Log.e("vvvvvvvvvvifQ",""+TotalQTY);
        if (TotalQTY.substring(TotalQTY.indexOf("."),TotalQTY.length()).length()>3
        && !po_item.getBarcode1().substring(0,2).equalsIgnoreCase("23")
        ) {
//            Log.e("vvvvvvvvvvifbbbbQ",""+TotalQTY.substring(TotalQTY.indexOf("."),TotalQTY.length()).length());
//            TotalQTY =  TotalQTY.substring(0, String.valueOf(Double.valueOf(TotalQTY)).indexOf(".") + 4);
//            Log.e("vvvvvvvvvvifQ",""+TotalQTY);

            holder.txt_quantity.setText(new DecimalFormat("###,##0.000").format(Double.valueOf(TotalQTY)));

        }else if (TotalQTY.substring(TotalQTY.indexOf("."),TotalQTY.length()).length()>3
                && po_item.getBarcode1().substring(0,2).equalsIgnoreCase("23")){
            TotalQTY = TotalQTY;

            holder.txt_quantity.setText(TotalQTY);

        }else if (TotalQTY.substring(TotalQTY.indexOf(".")+1,TotalQTY.length()).length()<3) {
            for (int i=0;TotalQTY.substring(TotalQTY.indexOf(".")+1,TotalQTY.length()).length()< 3;i++) {
                TotalQTY = TotalQTY + "0";
            }
            Log.e("vvvvvvvvvvifelseQ",""+TotalQTY);

            holder.txt_quantity.setText(new DecimalFormat("###,##0.000").format(Double.valueOf(TotalQTY)));

        }



       // Log.e("btn_editChecked000",""+ItemsList.size());

    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

    public List<Po_Item> ReturnListOfItems(){
        /*Log.e("btn_editChecked/",""+ItemsList.size());
        List<Po_Item> ItemsList0000 = new ArrayList<>();
        Po_Item  po_item_Of_Return =new Po_Item();
        int CountChecked=0;
        Boolean Checked =false;
        for (int i = 0; i < ItemsList.size(); i++) {

            po_item_Of_Return = ItemsList.get(i);
            ItemsList0000.add(po_item_Of_Return);

             Checked = ItemsList.get(i).getChecked_Item();
            Log.e("btn_editChecked",""+Checked);

            Log.e("btn_editCheckedI",""+i);

            if (Checked == true) {
                Log.e("btn_editCheckedif",""+Checked);
                Log.e("btn_editCheckedifMater",""+ItemsList0000.get(i).getMATERIAL1());
                CountChecked ++;
                //BarCodeChecked =Po_Item_List.get(i).getMEINH1();
                Log.e("btn_editCheckedCount",""+CountChecked);
                Checked =false;
            }


        }*/

        return ItemsList;

    }

    public int NumOfSelectionOfItems(){
        /*Log.e("btn_editChecked/",""+ItemsList.size());
        List<Po_Item> ItemsList0000 = new ArrayList<>();
        Po_Item  po_item_Of_Return =new Po_Item();*/
        int CountChecked=0;
        Boolean Checked =false;
        for (int i = 0; i < ItemsList.size(); i++) {

//            po_item_Of_Return = ItemsList.get(i).getIschecked();
//            ItemsList0000.add(po_item_Of_Return);

             Checked = ItemsList.get(i).getIschecked();
            Log.e("btn_editChecked",""+Checked);

            Log.e("btn_editCheckedI",""+i);

            if (Checked == true) {
                Log.e("btn_editCheckedif",""+Checked);
                //Log.e("btn_editCheckedifMater",""+ItemsList0000.get(i).getMATERIAL1());
                CountChecked ++;
                //BarCodeChecked =Po_Item_List.get(i).getMEINH1();
                Log.e("btn_editCheckedCount",""+CountChecked);
                Checked =false;
            }


        }

        return CountChecked;

    }
}
