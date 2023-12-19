package com.example.mygkm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygkm.R;
import com.example.mygkm.models.Order;
import com.example.mygkm.models.Product;
import com.example.mygkm.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Holder>{
    List<Order>orderList;
    private OnItemClickListener cardOnClickListener;

    private OnItemClickListener rebuyOnClickListener;


    public OrderAdapter(List<Order> productList, OnItemClickListener cardOnClickListener, OnItemClickListener rebuyOnClickListener){
        this.orderList = productList;
        this.cardOnClickListener =cardOnClickListener;
        this.rebuyOnClickListener = rebuyOnClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Order order);
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.riwayat_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Order order =orderList.get(position);
        Picasso.get().load(order.getProduct().getImageUrl()).into(holder.image);
        holder.name.setText(order.getProduct().getName());
        holder.merchant.setText(order.getProduct().getMerchant());
        holder.grandTotal.setText(Utils.formatRupiah(order.getPrice()));
        holder.status.setText("sukses");
        holder.dateAndTime.setText(order.getDate()+", "+order.getTime());
        holder.itemView.setOnClickListener(v -> {
            if (cardOnClickListener != null) {
                cardOnClickListener.onItemClick(order);
            }
        });
        holder.pesanLagiButton.setOnClickListener(v -> {
            if(rebuyOnClickListener!=null){
                rebuyOnClickListener.onItemClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, merchant, grandTotal, status, dateAndTime;
        AppCompatButton pesanLagiButton;
        public Holder(@NonNull View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productName);
            merchant = itemView.findViewById(R.id.merchantName);
            grandTotal = itemView.findViewById(R.id.grandTotal);
            status = itemView.findViewById(R.id.orderStatus);
            dateAndTime = itemView.findViewById(R.id.orderDateAndTime);
            pesanLagiButton = itemView.findViewById(R.id.pesanLagiButton);
        }
    }
}
