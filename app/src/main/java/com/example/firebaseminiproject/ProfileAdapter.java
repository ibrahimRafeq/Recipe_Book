package com.example.firebaseminiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseminiproject.databinding.ItemRecipe2Binding;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Recipe> recipeList;
    OnItemClicked onItemClicked;


    public ProfileAdapter(Context context, ArrayList<Recipe> recipeList, OnItemClicked onItemClicked) {
        this.context = context;
        this.recipeList = recipeList;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecipe2Binding binding = ItemRecipe2Binding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Recipe recipe = recipeList.get(position);

        myViewHolder.itemRecipeBinding.recipeName.setText(recipe.getName());
        myViewHolder.itemRecipeBinding.recipeCategory.setText(recipe.getCategory());
        myViewHolder.itemRecipeBinding.editRecipeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked.onClickEdit(position);
            }
        });
        myViewHolder.itemRecipeBinding.deleteRecipeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked.onClickDelete(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRecipe2Binding itemRecipeBinding;

        public MyViewHolder(ItemRecipe2Binding itemRecipeBinding) {
            super(itemRecipeBinding.getRoot());
            this.itemRecipeBinding = itemRecipeBinding;
        }
    }

    public interface OnItemClicked {
        void onClickEdit(int position);

        void onClickDelete(int position);
    }
}
