package com.codewithsk.gogrocery.Adapters;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithsk.gogrocery.Models.Cart;
import com.codewithsk.gogrocery.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.HolderAdapter> {
    Context context;
    ArrayList<Cart> cartList;
    public AdapterCart(Context context,ArrayList<Cart> cartList){
        this.context = context;
        this.cartList = cartList;
    }
    @NonNull
    @Override
    public HolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View r = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        return new HolderAdapter(r);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAdapter holder, int position) {
        Cart cart = cartList.get(position);

        holder.title.setText("Title: "+cart.getTitle());
        holder.price.setText("Price: "+cart.getTotalprice()+"Rs");
        holder.quantity.setText("Quantity: "+cart.getQuantity());

//        holder.delet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
//                        .child(FirebaseAuth.getInstance().getUid())
//                        .child("MyCart").child(cart.getId());
//                total = 0;
//                ref.removeValue();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class HolderAdapter extends RecyclerView.ViewHolder {
        TextView title,price,quantity;
        public HolderAdapter(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cartItemTitle);
            quantity = itemView.findViewById(R.id.cartItemQuantity);
            price = itemView.findViewById(R.id.cartItemPrice);
        }
    }
}
