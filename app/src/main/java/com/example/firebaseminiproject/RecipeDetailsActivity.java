package com.example.firebaseminiproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.firebaseminiproject.databinding.ActivityRecipeDetailsBinding;
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

        if (recipe != null) {
            binding.recipeNameTv.setText(recipe.getName());
            binding.categoryTv.setText(recipe.getCategory());

            binding.ingredientsTv.setText(TextUtils.join("\n", recipe.getIngredients()));
            binding.stepsTv.setText(TextUtils.join("\n", recipe.getDescription()));

            binding.videoUrlTv.setText(recipe.getVideoUrl());

            binding.videoUrlTv.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getVideoUrl()));
                startActivity(browserIntent);
            });

            // If image URL stored in Firestore
            // Glide.with(this).load(recipe.getImageUrl()).into(recipeImage);

            // Show edit/delete buttons if user is the creator
            // Assume you stored "creatorId" in the Recipe model
            if (auth.getCurrentUser() != null && auth.getCurrentUser().getUid().equals(recipe.getId())) {
                binding.editDeleteLayout.setVisibility(View.VISIBLE);
            }

            binding.editIcon.setOnClickListener(v -> {
                Intent editIntent = new Intent(this, EditRecipeActivity.class);
                editIntent.putExtra("recipe", recipe);
                startActivity(editIntent);
            });

            binding.deleteIcon.setOnClickListener(v -> {
                firestore.collection("recipes").document(recipe.getId()).delete().addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Recipe deleted", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        }

    }
}