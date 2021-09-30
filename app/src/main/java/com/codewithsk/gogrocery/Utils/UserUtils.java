package com.codewithsk.gogrocery.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codewithsk.gogrocery.Activites.DashboardActivity;
import com.codewithsk.gogrocery.Activites.LoginActivity;
import com.codewithsk.gogrocery.Activites.RegisterActivity;
import com.codewithsk.gogrocery.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserUtils {
    public static void createUser(Context context, String  name, String email,
                                  String phone, String address, String password){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("loading....");
        dialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User users = new User();
                            users.setName(name);
                            users.setEmail(email);
                            users.setAddress(address);
                            users.setPhone(phone);
                            users.setUid(auth.getUid());
                            userRef.child(auth.getUid()).setValue(users)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                dialog.dismiss();
                                                Toast.makeText(context, "Registration Success", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(context, DashboardActivity.class);
                                                // intent.putExtra("name",name);
                                                context.startActivity(intent);
                                                ((RegisterActivity)context).finish();
                                            }else {
                                                dialog.dismiss();
                                                Toast.makeText(context, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else {
                            dialog.dismiss();
                            Toast.makeText(context, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loginUser(Context context,String email, String password){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("loading....");
        dialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();


        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context,DashboardActivity.class);
                            context.startActivity(intent);
                            ((LoginActivity)context).finish();
                        }else {
                            dialog.dismiss();
                            Toast.makeText(context, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void forgotPassword(Context context,String email) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("loading....");
        dialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(context, "Check Your Mail box! reset password email are send", Toast.LENGTH_LONG).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(context, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public static void getUserInformation(TextView name,TextView email,TextView phone,
                                          TextView city){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(auth.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mName = ""+snapshot.child("name").getValue();
                String mEmail = ""+snapshot.child("email").getValue();
                String mPhone = ""+snapshot.child("phone").getValue();
                String mCity = ""+snapshot.child("address").getValue();

                name.setText("Name is: "+mName);
                email.setText("Email is: "+mEmail);
                phone.setText("Phone is: "+mPhone);
                city.setText("City is: "+mCity);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
