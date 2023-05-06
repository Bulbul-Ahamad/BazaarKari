package com.example.bazaarkari.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bazaarkari.R;
import com.example.bazaarkari.databinding.CartItemContainerBinding;
import com.example.bazaarkari.model.Product;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<Product> products;

    public CartAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);
        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.image);

        holder.binding.name.setText(product.getName());
        holder.binding.price.setText("BDT "+product.getPrice());
        int stock = product.getStock();

        holder.binding.plusBtn.setOnClickListener(view -> {

        });
        holder.binding.minusBtn.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{

        CartItemContainerBinding binding;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CartItemContainerBinding.bind(itemView);
        }
    }
}
