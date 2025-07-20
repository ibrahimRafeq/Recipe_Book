package com.example.firebaseminiproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebaseminiproject.databinding.ActivityProfileBinding;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();


    }
}