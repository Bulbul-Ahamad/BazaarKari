package com.example.bazaarkari.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bazaarkari.R;
import com.example.bazaarkari.databinding.CartItemContainerBinding;
import com.example.bazaarkari.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<Product> products;
    CartListener cartListener;
    Cart cart;

    public interface CartListener {
        public void onQuantityChanged();
    }

    public CartAdapter(Context context, ArrayList<Product> products,CartListener cartListener) {
        this.context = context;
        this.products = products;
        this.cartListener = cartListener;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item_container, parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);
        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.image);

        holder.binding.name.setText(product.getName());
        holder.binding.price.setText(String.format("BDT %.2f",product.getPrice()));
        holder.binding.quantity.setText(String.valueOf(product.getQuantity()));
        int stock = product.getStock();


        holder.binding.plusBtn.setOnClickListener(view -> {
            int quantity = product.getQuantity();
            quantity++;
            if (quantity>product.getStock()){
                Toast.makeText(context, "Reached Max Stock", Toast.LENGTH_SHORT).show();
            }else {
                product.setQuantity(quantity);
                holder.binding.quantity.setText(String.valueOf(quantity));

            }
            notifyDataSetChanged();
            cart.updateItem(product,product.getQuantity());
            cartListener.onQuantityChanged();
        });
        holder.binding.minusBtn.setOnClickListener(view -> {
            int quantity = product.getQuantity();
            if (quantity>1)
                quantity--;
            product.setQuantity(quantity);
            holder.binding.quantity.setText(String.valueOf(quantity));
            notifyDataSetChanged();
            cart.updateItem(product,product.getQuantity());
            cartListener.onQuantityChanged();
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
