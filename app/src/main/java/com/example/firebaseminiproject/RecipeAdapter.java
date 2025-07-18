package com.example.firebaseminiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseminiproject.databinding.ItemRecipeBinding;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Recipe> recipeList;
    private Context context;
    private OnItemClicked onItemClicked;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList, OnItemClicked onItemClicked) {
        this.context = context;
        this.recipeList = recipeList;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecipeBinding binding = ItemRecipeBinding.inflate(LayoutInflater.from(context), parent, false);
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
        myViewHolder.itemRecipeBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked.onClickCard(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void updateData(ArrayList<Recipe> newList) {
        this.recipeList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRecipeBinding itemRecipeBinding;

        public MyViewHolder(ItemRecipeBinding itemRecipeBinding) {
            super(itemRecipeBinding.getRoot());
            this.itemRecipeBinding = itemRecipeBinding;
        }
    }

    public interface OnItemClicked {
        void onClickEdit(int position);
        void onClickCard(int position);
    }
}
