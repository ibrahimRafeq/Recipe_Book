package com.example.firebaseminiproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebaseminiproject.databinding.ActivityRecipeDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class RecipeDetailsActivity extends AppCompatActivity {

    ActivityRecipeDetailsBinding binding;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        if (recipe.getIngredients() != null && !recipe.getIngredients().isEmpty()) {
            binding.ingredientsTv.setText(TextUtils.join(", ", recipe.getIngredients()));
        } else {
            binding.ingredientsTv.setText("");
        }

        if (recipe.getDescription() != null && !recipe.getDescription().isEmpty()) {
            binding.stepsTv.setText(TextUtils.join(", ", recipe.getDescription()));
        } else {
            binding.stepsTv.setText("");
        }

        if (recipe != null) {
            binding.recipeNameTv.setText(recipe.getName());
            binding.ingredientsTv.setText(TextUtils.join(", ", recipe.getIngredients()));
//            binding.stepsEt.setText(TextUtils.join(", ", recipe.getDescription()));
            binding.categoryTv.setText(recipe.getCategory());
            binding.videoUrlTv.setText(recipe.getVideoUrl());
        }

        binding.videoUrlTv.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getVideoUrl()));
            startActivity(browserIntent);
        });

        if (auth.getCurrentUser() != null && auth.getCurrentUser().getUid().equals(recipe.getId())) {
            binding.editDeleteLayout.setVisibility(View.VISIBLE);
        }

        binding.editRecipe.setOnClickListener(v -> {
            Intent eIntent = new Intent(this, EditRecipeActivity.class);
            eIntent.putExtra("recipe", recipe);
            startActivity(eIntent);
        });

        binding.deleteRecipe.setOnClickListener(v -> {
            firestore.collection("recipes").document(recipe.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(RecipeDetailsActivity.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}