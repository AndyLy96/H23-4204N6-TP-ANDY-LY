package com.example.tpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.example.tpandroid.databinding.ActivityCreationBinding;
import com.example.tpandroid.databinding.ConnexionMainBinding;

public class CreationActivity extends AppCompatActivity {

    ActivityCreationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.versAcceuil3.setOnClickListener(view -> {
            Intent i = new Intent(CreationActivity.this, AcceuilActivity.class);
            startActivity(i);
        });
    }
}