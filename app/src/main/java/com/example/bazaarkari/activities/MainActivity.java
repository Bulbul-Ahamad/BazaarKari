package com.example.bazaarkari.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bazaarkari.adapters.CategoryAdapter;
import com.example.bazaarkari.adapters.ProductAdapter;
import com.example.bazaarkari.databinding.ActivityMainBinding;
import com.example.bazaarkari.model.Category;
import com.example.bazaarkari.model.Product;
import com.example.bazaarkari.utils.Constants;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        binding.addToCartIcon.setOnClickListener(view -> startActivity(new Intent(this,CartAct.class)));

        initCategory();
        initProducts();
        initSlider();


    }

    void initCategory(){
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this,categories);

        getCategories();

        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
    }

    void getCategories(){
        RequestQueue queue = Volley.newRequestQueue(this);
        @SuppressLint("NotifyDataSetChanged") StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, response -> {
            try {
                JSONObject mainObj = new JSONObject(response);
                if (mainObj.getString("status").equals("success")){
                    JSONArray categoriesArray = mainObj.getJSONArray("categories");
                    for (int i = 0; i <categoriesArray.length(); i++) {
                        JSONObject obj = categoriesArray.getJSONObject(i);
                        Category category = new Category(
                                obj.getString("name"),
                                Constants.CATEGORIES_IMAGE_URL + obj.getString("icon"),
                                obj.getString("color"),
                                obj.getString("brief"),
                                obj.getInt("id")
                        );
                        categories.add(category);
                    }
                    categoryAdapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(request);
    }

    void initSlider() {
        getRecentOffers();
    }

    void getRecentOffers() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.GET_OFFERS_URL,
            (Response.Listener<String>) response -> {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    if (mainObj.getString("status").equals("success")){
                        JSONArray offerArray = mainObj.getJSONArray("news_infos");
                        for (int i = 0; i < offerArray.length(); i++) {
                            JSONObject childObj = offerArray.getJSONObject(i);
                            binding.carousel.addData(
                                    new CarouselItem(
                                            Constants.NEWS_IMAGE_URL + childObj.getString("image"),
                                            childObj.getString("title")
                                    )
                            );
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, (Response.ErrorListener) error -> {

            });
        queue.add(stringRequest);
    }

    void initProducts(){
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this,products);

        getProducts();

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);
    }

    void getProducts(){
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Constants.GET_PRODUCTS_URL + "?count=40";
            @SuppressLint("NotifyDataSetChanged") StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    JSONObject Obj = new JSONObject(response);
                    if (Obj.getString("status").equals("success")){
                        JSONArray productsArray = Obj.getJSONArray("products");
                        for (int i = 0; i < productsArray.length(); i++) {
                            JSONObject childObj = productsArray.getJSONObject(i);
                            Product product = new Product(
                                    childObj.getString("name"),
                                    Constants.PRODUCTS_IMAGE_URL+childObj.getString("image"),
                                    childObj.getString("status"),
                                    childObj.getDouble("price"),
                                    childObj.getDouble("price_discount"),
                                    childObj.getInt("stock"),
                                    childObj.getInt("id")
                            );
                            products.add(product);
                        }
                        productAdapter.notifyDataSetChanged();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }, error -> {

            });
            queue.add(request);
        }

}