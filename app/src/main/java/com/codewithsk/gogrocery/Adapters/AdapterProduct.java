package com.codewithsk.gogrocery.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithsk.gogrocery.Models.Cart;
import com.codewithsk.gogrocery.Models.Products;
import com.codewithsk.gogrocery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.Hold;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.NeumorphImageView;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.HolderProducts> {
    ArrayList<Products> productsList;
    Context context;
    int finalCost = 0;
    int quantity = 1;
    public AdapterProduct(Context context,ArrayList<Products> productsList)
    {
        this.context = context;
        this.productsList = productsList;
    }
    @NonNull
    @Override
    public HolderProducts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.maiin_item_list,parent,false);
        return new HolderProducts(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull HolderProducts holder, int position) {
        Products products = productsList.get(position);
        try {
            Picasso.get().load(products.getImg()).placeholder(R.drawable.loading).into(holder.imgLogo);
        } catch (Exception e) {
            holder.imgLogo.setImageResource(R.drawable.icon);
        }

        holder.mTitle.setText(products.getTitle());
        holder.mDesc.setText(products.getDesc());
        holder.mPrice.setText("Price: "+products.getPrice()+"Rs");
        holder.mQuantity.setText(products.getQuantity()+products.getQuantityIn());
        holder.tvQun.setText(""+quantity);

        finalCost = Integer.valueOf(products.getPrice());
        int price = Integer.valueOf(products.getPrice());
        quantity = Integer.valueOf(products.getQuantity());
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalCost = finalCost + price;
                quantity++;
                holder.mQuantity.setText(""+quantity+products.getQuantityIn());
                holder.tvQun.setText(""+quantity);
                holder.mPrice.setText("Price: "+finalCost+"Rs");
            }
        });
        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > Integer.valueOf(products.getQuantity())){
                    finalCost = finalCost - price;
                    quantity--;
                    holder.mQuantity.setText(""+quantity+products.getQuantityIn());
                    holder.tvQun.setText(""+quantity);
                    holder.mPrice.setText("Price: "+finalCost+"Rs");
                }
            }
        });
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("MyCart");
                String id = ""+System.currentTimeMillis();
                Cart cart = new Cart();
                cart.setId(id);
                cart.setQuantity(""+quantity+products.getQuantityIn());
                cart.setTotalprice(""+finalCost);
                cart.setTitle(products.getTitle());
                cart.setUserId(FirebaseAuth.getInstance().getUid());
                ref.child(id).setValue(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "Item Added To Cart", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class HolderProducts extends RecyclerView.ViewHolder {
        NeumorphImageView imgLogo;
        TextView mTitle,mDesc,mPrice,mQuantity,tvQun,qntIn;
        ImageButton increment,decrement;
        NeumorphCardView addBtn;
        public HolderProducts(@NonNull View itemView) {
            super(itemView);
            addBtn = itemView.findViewById(R.id.addCartBtn);
            increment = itemView.findViewById(R.id.incrementBtn);
            decrement = itemView.findViewById(R.id.decrementBtn);
            tvQun = itemView.findViewById(R.id.quantityTv);
            mQuantity = itemView.findViewById(R.id.Quantiy);
            mPrice = itemView.findViewById(R.id.price);
            mDesc = itemView.findViewById(R.id.desc);
            mTitle = itemView.findViewById(R.id.title);
            imgLogo = itemView.findViewById(R.id.icon);
            qntIn = itemView.findViewById(R.id.QuantiyIn);
        }
    }
}
