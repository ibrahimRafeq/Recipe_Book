package com.example.firebaseminiproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebaseminiproject.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}