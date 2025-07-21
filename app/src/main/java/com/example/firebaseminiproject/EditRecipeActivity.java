package com.example.firebaseminiproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebaseminiproject.databinding.ActivityEditRecipeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EditRecipeActivity extends AppCompatActivity {
    ActivityEditRecipeBinding binding;
    FirebaseFirestore firestore;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        if (recipe.getIngredients() != null && !recipe.getIngredients().isEmpty()) {
            binding.ingredientsEt.setText(TextUtils.join(", ", recipe.getIngredients()));
        } else {
            binding.ingredientsEt.setText("");
        }

        if (recipe.getDescription() != null && !recipe.getDescription().isEmpty()) {
            binding.stepsEt.setText(TextUtils.join(", ", recipe.getDescription()));
        } else {
            binding.stepsEt.setText("");
        }

        if (recipe != null) {
            binding.titleEt.setText(recipe.getName());
            binding.ingredientsEt.setText(TextUtils.join(", ", recipe.getIngredients()));
//            binding.stepsEt.setText(TextUtils.join(", ", recipe.getDescription()));
            binding.categoryEt.setText(recipe.getCategory());
            binding.videoUrlEt.setText(recipe.getVideoUrl());
        }

        binding.editRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecipe();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditRecipeActivity.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void updateRecipe() {
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

        List<String> ingredientsList = Arrays.asList(ingredients.split("\\s*,\\s*"));
        List<String> stepsList = Arrays.asList(steps.split("\\s*,\\s*"));

        Map<String, Object> updatedRecipe = new HashMap<>();
        updatedRecipe.put("name", title);
        updatedRecipe.put("ingredients", ingredientsList);
        updatedRecipe.put("description", stepsList);
        updatedRecipe.put("category", category);
        updatedRecipe.put("videoUrl", videoUrl);

        firestore.collection("recipes").document(recipe.getId())
                .update(updatedRecipe).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditRecipeActivity.this, "Recipe updated successfully ✅", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}