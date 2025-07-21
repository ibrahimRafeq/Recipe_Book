package com.example.firebaseminiproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firebaseminiproject.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private FirebaseFirestore firestore;
    private ArrayList<Recipe> recipeList;
    private RecipeAdapter adapter;
    String selectedCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        recipeList = new ArrayList<>();
        loadRecipesByCategory(selectedCategory);

        adapter = new RecipeAdapter(HomeActivity.this, recipeList, new RecipeAdapter.OnItemClicked() {
            @Override
            public void onClickEdit(int position) {
                Recipe selectedRecipe  = recipeList.get(position);
                Intent intent = new Intent(HomeActivity.this, EditRecipeActivity.class);
                intent.putExtra("recipe", (Serializable) selectedRecipe);
                startActivity(intent);
            }

            @Override
            public void onClickCard(int position) {
                Recipe selectedRecipe  = recipeList.get(position);
                Intent intent = new Intent(HomeActivity.this, RecipeDetailsActivity.class);
                intent.putExtra("recipe", (Serializable) selectedRecipe);
                startActivity(intent);
            }
        });


        binding.recipesRv.setAdapter(adapter);
        binding.recipesRv.setLayoutManager(new LinearLayoutManager(this));

        setupTabs();

        binding.profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        binding.shareNewRecipe.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, AddRecipeActivity.class)));
        searchView();
    }

    private void loadRecipesByCategory(String category) {
        recipeList.clear();
        if (category.equals("All")) {
            firestore.collection("recipes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                         public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Recipe recipe = document.toObject(Recipe.class);
                                    recipe.setId(document.getId());
                                    recipeList.add(recipe);
                                }
                                adapter.updateData(recipeList);

                            } else {
                                Toast.makeText(HomeActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {
            firestore.collection("recipes")
                    .whereEqualTo("category", selectedCategory)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Recipe recipe = document.toObject(Recipe.class);
                                    recipeList.add(recipe);
                                }
                                adapter.updateData(recipeList);

                            } else {
                                Toast.makeText(HomeActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }


    private void setupTabs() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("All"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Breakfast"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lunch"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Dinner"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Candy"));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedCategory = tab.getText().toString();
                loadRecipesByCategory(selectedCategory);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private void searchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterRecipes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes(newText);
                return true;
            }
        });

        binding.searchView.setOnCloseListener(() -> {
            binding.searchView.setQuery("", false);
            loadRecipesByCategory(selectedCategory);
            return false;
        });
    }


    public void filterRecipes(String query) {
        ArrayList<Recipe> filtered = new ArrayList<>();
        for (Recipe r : recipeList) {
            boolean matchCategory = selectedCategory.equals("All") || r.getCategory().equalsIgnoreCase(selectedCategory);
            boolean matchQuery = r.getName().toLowerCase().contains(query.toLowerCase());

            if (matchCategory && matchQuery) {
                filtered.add(r);
            }
        }
        adapter.updateData(filtered);
    }

}