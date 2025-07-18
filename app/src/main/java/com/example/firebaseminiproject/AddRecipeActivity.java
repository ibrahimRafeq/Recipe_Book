package com.example.firebaseminiproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebaseminiproject.databinding.ActivityAddRecipeBinding;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddRecipeActivity extends AppCompatActivity {

    ActivityAddRecipeBinding binding;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        binding.addRecipeBtn.setOnClickListener(v -> addRecipe());
    }

    private void addRecipe() {
        String title = binding.titleEt.getText().toString().trim();
        String ingredients = binding.ingredientsEt.getText().toString().trim();
        String steps = binding.stepsEt.getText().toString().trim();
        String category = binding.categoryEt.getText().toString().trim();
        String videoUrl = binding.videoUrlEt.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(ingredients)
                || TextUtils.isEmpty(steps) || TextUtils.isEmpty(category) || videoUrl.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> recipe = new HashMap<>();
        recipe.put("name", title);
        recipe.put("ingredients", Arrays.asList(ingredients.split("\\s*,\\s*")));
        recipe.put("steps", Arrays.asList(steps.split("\\s*,\\s*")));
        recipe.put("category", category);
        recipe.put("videoUrl", videoUrl);

        firestore.collection("recipes").add(recipe)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Recipe added successfully ✅", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to add recipe ❌", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}