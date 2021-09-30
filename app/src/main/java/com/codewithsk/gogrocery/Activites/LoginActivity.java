package com.codewithsk.gogrocery.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codewithsk.gogrocery.R;
import com.codewithsk.gogrocery.Utils.UserUtils;
import com.codewithsk.gogrocery.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });

        binding.goToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.ledtEmail.getText().toString().trim();
                String password = binding.ledtPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Some Feils are empty!!", Toast.LENGTH_SHORT).show();
                }else {
                    UserUtils.loginUser(LoginActivity.this,email,password);
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
            finish();
        }
    }
}