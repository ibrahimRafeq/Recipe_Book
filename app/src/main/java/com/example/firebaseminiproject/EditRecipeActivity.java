package com.example.firebaseminiproject;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebaseminiproject.databinding.ActivityEditRecipeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class EditRecipeActivity extends AppCompatActivity {
    ActivityEditRecipeBinding binding;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();


        firestore.collection("recipes")
//                .whereEqualTo("title", "secondPost")   // (secondPost)يرجع البيانات حسب شرط معين والشرط هنا كان ان يتم جلب البيانات التي يكون فيها العنوان هو المنشور الثاني
//                .orderBy("id")    //   (id)يرجع البيانات بالترتيب على حسب ال
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        binding.titleEt.setText("");
                        binding.ingredientsEt.setText("");
                        binding.stepsEt.setText("");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Recipe recipe = document.toObject(Recipe.class);
                        }
                    }
                });


        binding.editRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.titleEt.getText().toString().trim();
                String ingredients = binding.ingredientsEt.getText().toString().trim();
                String steps = binding.stepsEt.getText().toString().trim();
                String category = binding.categoryEt.getText().toString().trim();
                String videoUrl = binding.videoUrlEt.getText().toString().trim();
            }
        });

    }
}