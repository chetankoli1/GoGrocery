package com.codewithsk.gogrocery.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codewithsk.gogrocery.Adapters.AdapterProduct;
import com.codewithsk.gogrocery.Models.Products;
import com.codewithsk.gogrocery.R;
import com.codewithsk.gogrocery.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ArrayList<Products> productList;
    AdapterProduct adpater;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        productList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Products");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Products products = ds.getValue(Products.class);
                    productList.add(products);
                }
                adpater = new AdapterProduct(getContext(),productList);
                binding.itesRv.setAdapter(adpater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}