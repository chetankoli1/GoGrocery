package com.codewithsk.gogrocery.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithsk.gogrocery.Adapters.AdapterCart;
import com.codewithsk.gogrocery.Models.Cart;
import com.codewithsk.gogrocery.Models.Order;
import com.codewithsk.gogrocery.R;
import com.codewithsk.gogrocery.databinding.FragmentCartBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class CartFragment extends Fragment {
    AdapterCart adapterCart;
    ArrayList<Cart> cartList;
    int total = 0;

    TextView tPrice;
    RecyclerView recyclerView;
    Button btnPlaceOrder;
    RelativeLayout mainLayout,noData;
    Button btnCleanCart;

    String phone,addr;


    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        tPrice = root.findViewById(R.id.cTotal);
        recyclerView = root.findViewById(R.id.cartItemRv);
        btnPlaceOrder = root.findViewById(R.id.finalOrder);
        btnCleanCart  = root.findViewById(R.id.clearCart);
        mainLayout = root.findViewById(R.id.mainLayot);
        noData = root.findViewById(R.id.noData);
        cartList = new ArrayList<>();
        btnCleanCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("MyCart");

                ref.removeValue();
            }
        });
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("MyCart");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    mainLayout.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                    cartList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Cart cart = ds.getValue(Cart.class);
                        cartList.add(cart);
                        String uid = FirebaseAuth.getInstance().getUid();
                        int priceTotal = Integer.valueOf(cart.getTotalprice());
                        total = total + priceTotal;
                        tPrice.setText("Total Price:- "+total+"Rs");
                    }
                    adapterCart = new AdapterCart(getContext(),cartList);
                    recyclerView.setAdapter(adapterCart);
                    adapterCart.notifyDataSetChanged();
                }else {
                    mainLayout.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timetamp = ""+System.currentTimeMillis();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders")
                        .child(timetamp);
                Order order = new Order();
                order.setUid(FirebaseAuth.getInstance().getUid());
                order.setTotal_price(tPrice.getText().toString());
                order.setUser_contact(phone);
                order.setDeleveryAddress(addr);
                order.setStatus("pending");
                order.setOrderId(timetamp);
                order.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                ref.setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            for (int i = 0; i < cartList.size(); i++){
                                String title = cartList.get(i).getTitle();
                                String id = cartList.get(i).getId();
                                String price = cartList.get(i).getTotalprice();
                                String quantity = cartList.get(i).getQuantity();
                                HashMap<String,Object> getItems = new HashMap<>();
                                getItems.put("id",id);
                                getItems.put("title",title);
                                getItems.put("price",price);
                                getItems.put("quantity",quantity);
                                ref.child("Items").child(id).setValue(getItems);
                            }
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .child("MyCart");
                            Toast.makeText(getContext(), "order Placed check Notifications", Toast.LENGTH_SHORT).show();
                            ref.removeValue();
                        }else {
                            Toast.makeText(getContext(), ""+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(auth.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mName = ""+snapshot.child("name").getValue();
                String mEmail = ""+snapshot.child("email").getValue();
                phone = ""+snapshot.child("phone").getValue();
                addr = ""+snapshot.child("address").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
}