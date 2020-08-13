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

import com.example.qbapp.R;
import com.example.qbapp.model.LastOrdersModule;
import com.example.qbapp.model.Po_Item;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class LastItemDelieveredAdapter extends RecyclerView.Adapter<LastItemDelieveredAdapter.MyViewHolder> {

    private List<LastOrdersModule> ItemsList;
    Po_Item  po_item;
Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        LinearLayout linear_of_recycle;
        public TextView  txt_id_of_last_order;

        public MyViewHolder(View view) {
            super(view);
            txt_id_of_last_order = view.findViewById(R.id.txt_id_of_last_order);
        }
    }


    public LastItemDelieveredAdapter(Context context , List<LastOrdersModule> moviesList) {
        this.context=context;
        this.ItemsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_for_last_po_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txt_id_of_last_order.setText(ItemsList.get(position).getSerialNumber1());
    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

    public List<LastOrdersModule> ReturnListOfItems(){


        return ItemsList;

    }

}
