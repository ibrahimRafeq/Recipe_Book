package com.example.firebaseminiproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebaseminiproject.databinding.ActivityRecipeDetailsBinding;


public class RecipeDetailsActivity extends AppCompatActivity {

    ActivityRecipeDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}