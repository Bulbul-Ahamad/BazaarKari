package com.example.bazaarkari.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.bazaarkari.adapters.CategoryAdapter;
import com.example.bazaarkari.adapters.ProductAdapter;
import com.example.bazaarkari.databinding.ActivityMainBinding;
import com.example.bazaarkari.model.Category;
import com.example.bazaarkari.model.Product;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

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

        initCategory();
        initProducts();
        initSlider();


    }

    void initCategory(){
        categories = new ArrayList<>();
        categories.add(new Category("Walking shoes","https://tutorials.mianasad.com/ecommerce/uploads/category/1683204743625.png","#9d4c3d","Best sneakers for walking.",1));
        categories.add(new Category("Walking shoes","https://tutorials.mianasad.com/ecommerce/uploads/category/1683204743625.png","#9d4c3d","Best sneakers for walking.",1));
        categories.add(new Category("Walking shoes","https://tutorials.mianasad.com/ecommerce/uploads/category/1683204743625.png","#9d4c3d","Best sneakers for walking.",1));
        categories.add(new Category("Walking shoes","https://tutorials.mianasad.com/ecommerce/uploads/category/1683204743625.png","#9d4c3d","Best sneakers for walking.",1));
        categories.add(new Category("Walking shoes","https://tutorials.mianasad.com/ecommerce/uploads/category/1683204743625.png","#9d4c3d","Best sneakers for walking.",1));
        categories.add(new Category("Walking shoes","https://tutorials.mianasad.com/ecommerce/uploads/category/1683204743625.png","#9d4c3d","Best sneakers for walking.",1));
        categories.add(new Category("Walking shoes","https://tutorials.mianasad.com/ecommerce/uploads/category/1683204743625.png","#9d4c3d","Best sneakers for walking.",1));
        categories.add(new Category("Walking shoes","https://tutorials.mianasad.com/ecommerce/uploads/category/1683204743625.png","#9d4c3d","Best sneakers for walking.",1));
        categoryAdapter = new CategoryAdapter(this,categories);

        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
    }

    void initSlider() {
        binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/mens%20offers.jpg","sales offer for men"));
        binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/summer%20sales%20offer.jpg","summer season"));
        binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/summer%20sales%20offer.jpg","summer season"));
    }

    void initProducts(){
        products = new ArrayList<>();
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        products.add(new Product("Himalayan Face Wash","https://tutorials.mianasad.com/ecommerce/uploads/product/1683270685719.jpg","READY STOCK",12,12,1,1));
        productAdapter = new ProductAdapter(this,products);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);
    }

}