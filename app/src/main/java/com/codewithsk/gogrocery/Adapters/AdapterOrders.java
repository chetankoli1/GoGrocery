package com.codewithsk.gogrocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithsk.gogrocery.Models.Order;
import com.codewithsk.gogrocery.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.HolderOrders> {
    Context context;
    ArrayList<Order> orderList;

    public AdapterOrders(Context context,ArrayList<Order> orderList){
        this.context = context;
        this.orderList = orderList;
    }
    @NonNull
    @Override
    public HolderOrders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.row_order,parent,false);
        return new HolderOrders(root);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrders holder, int position) {

        Order order = orderList.get(position);

        holder.tvTotal.setText(order.getTotal_price());
        holder.tvId.setText("Order Id"+order.getOrderId());
        String status = order.getStatus();
        if (status.equals("accepted")){
            holder.tvStatus.setText("order accepted");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.dark_green));
            holder.btnCancel.setVisibility(View.GONE);
        }else if (status.equals("rejected")){
            holder.tvStatus.setText("order rejected");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.btnCancel.setVisibility(View.VISIBLE);
        }else if (status.equals("pending")){
            holder.tvStatus.setText("order Pending");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.dark_yellow));
            holder.btnCancel.setVisibility(View.VISIBLE);
        }
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders")
                        .child(order.getOrderId());

                ref.removeValue();

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class HolderOrders extends RecyclerView.ViewHolder {
        TextView tvStatus,tvId,tvTotal;
        Button btnCancel;
        public HolderOrders(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.orderId);
            tvStatus = itemView.findViewById(R.id.orderStatus);
            tvTotal = itemView.findViewById(R.id.totalPrice);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
