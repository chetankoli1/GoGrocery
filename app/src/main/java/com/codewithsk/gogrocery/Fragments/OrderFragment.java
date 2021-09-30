package com.codewithsk.gogrocery.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codewithsk.gogrocery.Adapters.AdapterOrders;
import com.codewithsk.gogrocery.Models.Order;
import com.codewithsk.gogrocery.R;
import com.codewithsk.gogrocery.databinding.FragmentOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderFragment extends Fragment {
    FragmentOrderBinding binding;
    ArrayList<Order> orderList;
    AdapterOrders adapterOrders;
    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        orderList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    orderList.clear();
                    binding.noFound.setVisibility(View.GONE);
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Order order = ds.getValue(Order.class);
                        orderList.add(order);
                    }
                    adapterOrders = new AdapterOrders(getContext(), orderList);
                    binding.ordersRv.setAdapter(adapterOrders);
                }else {
                    binding.noFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}