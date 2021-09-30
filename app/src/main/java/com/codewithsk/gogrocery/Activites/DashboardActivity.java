package com.codewithsk.gogrocery.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.codewithsk.gogrocery.Fragments.CartFragment;
import com.codewithsk.gogrocery.Fragments.HomeFragment;
import com.codewithsk.gogrocery.Fragments.OrderFragment;
import com.codewithsk.gogrocery.Fragments.ProfileFragment;
import com.codewithsk.gogrocery.R;
import com.codewithsk.gogrocery.databinding.ActivityDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressWarnings("ALL")
public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.ic_container,new HomeFragment()).commit();
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        getWindow().setStatusBarColor(getResources().getColor(R.color.background));
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_cart:
                        getWindow().setStatusBarColor(getResources().getColor(R.color.background1));
                        fragment = new CartFragment();
                        break;
                    case R.id.nav_notification:
                        getWindow().setStatusBarColor(getResources().getColor(R.color.background));
                        fragment = new OrderFragment();
                        break;
                    case R.id.nav_profile:
                        getWindow().setStatusBarColor(getResources().getColor(R.color.background));
                        fragment = new ProfileFragment();
                        break;
                    default:
                        fragment = new HomeFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.ic_container,fragment).commit();
                return true;

            }

        });
    }
}