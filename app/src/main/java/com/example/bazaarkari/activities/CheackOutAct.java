package com.example.bazaarkari.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bazaarkari.adapters.CartAdapter;
import com.example.bazaarkari.databinding.ActivityCheackOutBinding;
import com.example.bazaarkari.model.Product;
import com.example.bazaarkari.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CheackOutAct extends AppCompatActivity {

    ActivityCheackOutBinding binding;
    CartAdapter cartAdapter;
    ArrayList<Product> products;
    double totalPrice = 0;
    final double tax = 7.5;
    ProgressDialog progressDialog;
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheackOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing your order");

        products = new ArrayList<>();
        cart = TinyCartHelper.getCart();
        for (Map.Entry<Item,Integer> item : cart.getAllItemsWithQty().entrySet()){
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);
            products.add(product);
        }
        cartAdapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subtotal.setText(String.format("BDT %.2f",cart.getTotalPrice()));
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(cartAdapter);
        binding.subtotal.setText(String.format("BDT %.2f",cart.getTotalPrice()));

        totalPrice = (cart.getTotalPrice().doubleValue() * tax / 100) + cart.getTotalPrice().doubleValue();
        binding.total.setText(String.format("BDT %.2f",totalPrice));

        binding.checkoutBtn.setOnClickListener(view -> {
            if (binding.nameBox.getText().toString().equals("")){
                Toast.makeText(this, "Please Enter Your Name !", Toast.LENGTH_SHORT).show();
            }else if (binding.emailBox.getText().toString().equals("")){
                Toast.makeText(this, "Please Enter Your Email !", Toast.LENGTH_SHORT).show();
            }else if (binding.phoneBox.getText().toString().equals("")){
                Toast.makeText(this, "Please Enter Your Phone Number !", Toast.LENGTH_SHORT).show();
            }else if (binding.addressBox.getText().toString().equals("")){
                Toast.makeText(this, "Please Enter Your Address !", Toast.LENGTH_SHORT).show();
            }else if (binding.dateBox.getText().toString().equals("")){
                Toast.makeText(this, "Please Enter Your Shipping Date !", Toast.LENGTH_SHORT).show();
            }else
                processOrder();
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void processOrder(){
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject productOrder = new JSONObject();
        JSONObject dataObject = new JSONObject();
        try {
            productOrder.put("address",binding.addressBox.getText().toString());
            productOrder.put("buyer",binding.nameBox.getText().toString());
            productOrder.put("comment", binding.commentBox.getText().toString());
            productOrder.put("created_at", Calendar.getInstance().getTimeInMillis());
            productOrder.put("last_update", Calendar.getInstance().getTimeInMillis());
            productOrder.put("date_ship", Calendar.getInstance().getTimeInMillis());
            productOrder.put("email", binding.emailBox.getText().toString());
            productOrder.put("phone", binding.phoneBox.getText().toString());
            productOrder.put("serial", "cab8c1a4e4421a3b");
            productOrder.put("shipping", "");
            productOrder.put("shipping_location", "");
            productOrder.put("shipping_rate", "0.0");
            productOrder.put("status", "WAITING");
            productOrder.put("tax", tax);
            productOrder.put("total_fees", totalPrice);

            JSONArray product_order_detail = new JSONArray();
            for (Map.Entry<Item,Integer> item : cart.getAllItemsWithQty().entrySet()){
                Product product = (Product) item.getKey();
                int quantity = item.getValue();
                product.setQuantity(quantity);

                JSONObject productObj = new JSONObject();
                productObj.put("amount", quantity);
                productObj.put("price_item", product.getPrice());
                productObj.put("product_id", product.getId());
                productObj.put("product_name", product.getName());

                product_order_detail.put(productObj);
            }

            dataObject.put("product_order",productOrder);
            dataObject.put("product_order_detail",product_order_detail);

            Log.e("err",dataObject.toString());

        }catch (JSONException e){}

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.POST_ORDER_URL,dataObject, response -> {
            try {
                if (response.getString("status").equals("success")){
                    Toast.makeText(CheackOutAct.this, "Success Order", Toast.LENGTH_SHORT).show();
                    String orderNumber = response.getJSONObject("data").getString("code");
                    new AlertDialog.Builder(CheackOutAct.this)
                            .setTitle("Order Successful")
                            .setMessage("Your Order Number is : "+ orderNumber)
                            .setCancelable(false)
                            .setPositiveButton("Close", (dialog, which) -> {

                            }).show();
                }else{
                    new AlertDialog.Builder(CheackOutAct.this)
                            .setTitle("Order Failed")
                            .setMessage("Something went wrong, Please try again.")
                            .setCancelable(false)
                            .setPositiveButton("Close", (dialog, which) -> {

                            }).show();
                    progressDialog.dismiss();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }, error -> {
            progressDialog.dismiss();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Security","secure_code");
                return headers;
            }
        };
        queue.add(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}