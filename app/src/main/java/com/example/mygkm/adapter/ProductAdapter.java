package com.example.mygkm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygkm.R;
import com.example.mygkm.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder>{
    List<Product>productList;
    private OnItemClickListener onItemClickListener;


    public ProductAdapter(List<Product> productList, OnItemClickListener onItemClickListener){
        this.productList = productList;
        this.onItemClickListener =onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Product wahana);
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_card, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Product product =productList.get(position);
        String type = product.getType() == Product.Type.FOOD?"Makanan":"Minuman";
        holder.itemView.setOnClickListener(v->{
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(product);
            }
        });
        holder.name.setText(product.getName());
        holder.type.setText(type);
        Picasso.get().load(product.getImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, type;
        public Holder(@NonNull View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productName);
            type = itemView.findViewById(R.id.productType);
        }
    }
}
