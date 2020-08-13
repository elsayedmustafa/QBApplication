package com.example.qbapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qbapp.Adapter.LastItemDelieveredAdapter;
import com.example.qbapp.Helper.DatabaseHelper;
import com.example.qbapp.Helper.ItemClickSupport;
import com.example.qbapp.MainActivity;
import com.example.qbapp.R;
import com.example.qbapp.model.LastOrdersModule;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LastOrderFragment extends Fragment {
    List<LastOrdersModule>  Po_Item_For_Recycly=new ArrayList<>();
    LastItemDelieveredAdapter lastItemDelieveredAdapter;
    RecyclerView recyclerView;
    String UserName,Branch ,LastOrderId="";
ImageView image_close;
DatabaseHelper databaseHelper;
    public LastOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       // Po_Item_For_Recycly=(ArrayList<String>) getArguments().getSerializable("LastOrderIdArray");
       // Log.e("zzzlastorder",""+Po_Item_For_Recycly.size());
        UserName =getArguments().getString("UserName");
        Branch  =getArguments().getString("Branch");

        return inflater.inflate(R.layout.fragment_last_order, container, false);


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper =new DatabaseHelper(getContext());

        recyclerView=view.findViewById(R.id.recycle_last_items_view);
        image_close=view.findViewById(R.id.image_close);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent GoBackToRetriveData=new Intent(getActivity(),MainActivity.class);
//                GoBackToRetriveData.putExtra("UserName", UserName);
//                GoBackToRetriveData.putExtra("Branch", Branch);
//                GoBackToRetriveData.putExtra("LastOrderId", "");
//                startActivity(GoBackToRetriveData);

                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.popBackStack();


            }
        });
        for (int i = 0 ; i<Po_Item_For_Recycly.size() ; i++){
            Log.e("mmmmmmmmm",""+Po_Item_For_Recycly.get(i));
        }

        CreateORUpdateRecycleView();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                List<LastOrdersModule> Po_Item_List = lastItemDelieveredAdapter.ReturnListOfItems();

//                Intent GoToBackToMainactivity = new Intent(getActivity(), MainActivity.class);
//                GoToBackToMainactivity.putExtra("UserName", UserName);
//                GoToBackToMainactivity.putExtra("Branch", Branch);
//
//                GoToBackToMainactivity.putExtra("LastOrderId", Po_Item_List.get(position));
//                Log.e("mmmmmmmmm",""+Po_Item_List.get(position));
//                startActivity(GoToBackToMainactivity);

                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.popBackStack();

                MainActivity mainActivity=(MainActivity) getActivity();
                if (mainActivity != null){
               ///     mainActivity.LastOrderId=Po_Item_List.get(position);
                    mainActivity.RetrieveFromSqlServer(Po_Item_List.get(position).getSerialNumber1());
                    Log.e("nnnnnnnnn","");
                }

            }
        });
    }
    public void CreateORUpdateRecycleView(){
//        databaseHelper =new DatabaseHelper(this);
//
//        Po_Item_For_Recycly = databaseHelper.Get_Po_Item_That_Has_PDNewQTy();
//        Po_Item_For_Recycly = databaseHelper.Get_Items();

        // TODO: user what comming from main activity

      //   Po_Item_For_Recycly.add("2019/09/18 16:55:34:09");
        Log.e("zzzlastorder",""+Po_Item_For_Recycly.size());
        Po_Item_For_Recycly=databaseHelper.getlastOrders();
        Log.e("zzzlastorderDB",""+Po_Item_For_Recycly.size());
        Log.e("zzzlastorder",""+databaseHelper.getlastOrders().size());
        lastItemDelieveredAdapter = new LastItemDelieveredAdapter(getActivity(),Po_Item_For_Recycly);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(lastItemDelieveredAdapter);




    }

}
