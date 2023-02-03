package com.example.tpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tpandroid.databinding.ActivityInscriptionBinding;

public class InscriptionActivity extends AppCompatActivity {

    ActivityInscriptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
    }
}