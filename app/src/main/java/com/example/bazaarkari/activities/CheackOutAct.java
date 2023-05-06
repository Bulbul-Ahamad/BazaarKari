package com.example.bazaarkari.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bazaarkari.databinding.ActivityCheackOutBinding;

public class CheackOutAct extends AppCompatActivity {

    ActivityCheackOutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheackOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}