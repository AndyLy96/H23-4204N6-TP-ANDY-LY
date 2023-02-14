package com.example.tpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tpandroid.databinding.ActivityConsultationBinding;

public class ConsultationActivity extends AppCompatActivity {

    ActivityConsultationBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}