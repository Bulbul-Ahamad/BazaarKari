package com.example.bazaarkari.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bazaarkari.R;
import com.example.bazaarkari.databinding.ActivityProductDetailsBinding;
import com.example.bazaarkari.model.Product;
import com.example.bazaarkari.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetails extends AppCompatActivity {

    ActivityProductDetailsBinding binding;
    Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        int id = getIntent().getIntExtra("id",0);
        double price  = getIntent().getDoubleExtra("price",0);

        Glide.with(this)
                .load(image)
                .into(binding.productImage);

        getProductDetails(id);
        binding.productName.setText(name);
        binding.productPrice.setText(String.format("BDT %.2f",price));
        binding.productNameTop.setText(name);

        Cart cart = TinyCartHelper.getCart();
        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                cart.addItem(currentProduct,1);
                binding.addToCartBtn.setClickable(false);
                binding.addToCartBtn.setBackgroundColor(R.color.white);
            }
        });
        setListener();

    }

    private void setListener() {
        binding.backBtn.setOnClickListener(view -> onBackPressed());
        binding.addToCartIcon.setOnClickListener(view -> startActivity(new Intent(this,CartAct.class)));
    }

    void getProductDetails(int id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.GET_PRODUCT_DETAILS_URL + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject mainobj = new JSONObject(response);
                        if (mainobj.getString("status").equals("success")){
                            JSONObject product = mainobj.getJSONObject("product");
                            String description = product.getString("description");
                            binding.productDescription.setText(
                                    Html.fromHtml(description)
                            );
                            currentProduct =new Product(
                                    product.getString("name"),
                                    Constants.PRODUCTS_IMAGE_URL+product.getString("image"),
                                    product.getString("status"),
                                    product.getDouble("price"),
                                    product.getDouble("price_discount"),
                                    product.getInt("stock"),
                                    product.getInt("id")
                            );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {

                });

        queue.add(stringRequest);
    }

}