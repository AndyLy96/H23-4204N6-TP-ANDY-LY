package com.example.tpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tpandroid.databinding.ConnexionMainBinding;

public class ConnexionActivity extends AppCompatActivity {

    ConnexionMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ConnexionMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.versInscription.setOnClickListener(view -> {
            Intent i = new Intent(ConnexionActivity.this, InscriptionActivity.class);
            startActivity(i);
        });

        binding. versAcceuil1.setOnClickListener(view -> {
            Intent i = new Intent(ConnexionActivity.this, AcceuilActivity.class);
            startActivity(i);
        });

    }
}