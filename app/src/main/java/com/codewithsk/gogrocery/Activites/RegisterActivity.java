package com.codewithsk.gogrocery.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codewithsk.gogrocery.R;
import com.codewithsk.gogrocery.Utils.UserUtils;
import com.codewithsk.gogrocery.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerBtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editNameUser.getText().toString().trim();
                String email = binding.edtEmailUser.getText().toString().trim();
                String phone = binding.edtPhoneUser.getText().toString().trim();
                String address = binding.edtCityUser.getText().toString().trim();
                String  pass = binding.editPasswordUser.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || pass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Some Feilds are empty!!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    UserUtils.createUser(RegisterActivity.this,
                            name,
                            email,
                            phone,
                            address,
                            pass
                    );
                }

            }
        });

    }
}