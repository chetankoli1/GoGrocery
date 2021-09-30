package com.codewithsk.gogrocery.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codewithsk.gogrocery.R;
import com.codewithsk.gogrocery.Utils.UserUtils;
import com.codewithsk.gogrocery.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.forgotEdtEmail.getText().toString().trim();

                if (email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Some Feilds are empty!!", Toast.LENGTH_SHORT).show();
                }else {
                    UserUtils.forgotPassword(ForgotPasswordActivity.this,email);
                }
            }
        });


    }
}