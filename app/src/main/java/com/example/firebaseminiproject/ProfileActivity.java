package com.example.firebaseminiproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firebaseminiproject.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ProfileAdapter adapter;
    ArrayList<Recipe> recipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recipes = new ArrayList<>();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            binding.userName.setText(currentUser.getDisplayName());
            binding.userEmail.setText(currentUser.getEmail());
            loadUserRecipes(currentUser.getUid());  // ⬅️ استدعاء لتحميل وصفاته
        }

        adapter = new ProfileAdapter(ProfileActivity.this, recipes, new ProfileAdapter.OnItemClicked() {
            @Override
            public void onClickEdit(int position) {
                Intent intent = new Intent(ProfileActivity.this, EditRecipeActivity.class);
                intent.putExtra("recipe", recipes.get(position));
                startActivity(intent);
            }

            @Override
            public void onClickDelete(int position) {
                Recipe recipe = recipes.get(position);
                firestore.collection("recipes")
                        .document(recipe.getId())
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            recipes.remove(position);
                            adapter.notifyItemRemoved(position);
                        })
                        .addOnFailureListener(e -> {
                            // يمكن عرض Toast في حالة الفشل
                        });
            }
        });
        binding.recipesRecyclerView.setAdapter(adapter);
        binding.recipesRecyclerView.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));


        binding.logoutBtm.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void loadUserRecipes(String userId) {
        firestore.collection("recipes")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    recipes.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Recipe recipe = doc.toObject(Recipe.class);
                        recipe.setId(doc.getId()); // تأكد أن لديك setId() في Recipe
                        recipes.add(recipe);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // عرض رسالة فشل التحميل
                });
    }

}