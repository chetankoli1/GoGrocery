package com.codewithsk.gogrocery.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codewithsk.gogrocery.Activites.DashboardActivity;
import com.codewithsk.gogrocery.Activites.LoginActivity;
import com.codewithsk.gogrocery.R;
import com.codewithsk.gogrocery.Utils.UserUtils;
import com.codewithsk.gogrocery.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        UserUtils.getUserInformation(
                binding.upName,
                binding.upEmail,
                binding.upPhone,
                binding.upCity
        );
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                ((DashboardActivity)getContext()).finish();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}