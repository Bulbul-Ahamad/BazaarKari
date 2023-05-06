package com.example.bazaarkari.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bazaarkari.databinding.ActivityProductDetailsBinding;
import com.example.bazaarkari.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetails extends AppCompatActivity {

    ActivityProductDetailsBinding binding;

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
        binding.productPrice.setText("BDT "+price);
        binding.productNameTop.setText(name);

        setListener();

    }

    private void setListener() {
        binding.backBtn.setOnClickListener(view -> onBackPressed());
        binding.addToCartIcon.setOnClickListener(view -> startActivity(new Intent(this,Cart.class)));
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
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {

                });

        queue.add(stringRequest);
    }

}