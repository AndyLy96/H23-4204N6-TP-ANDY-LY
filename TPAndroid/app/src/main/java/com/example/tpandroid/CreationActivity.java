package com.example.tpandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tpandroid.databinding.ActivityCreationBinding;
import com.example.tpandroid.databinding.ConnexionMainBinding;
import com.google.android.material.navigation.NavigationView;

public class CreationActivity extends AppCompatActivity {

    ActivityCreationBinding binding;
    ActionBarDrawerToggle barToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.versAcceuil3.setOnClickListener(view -> {
            Intent i = new Intent(CreationActivity.this, AcceuilActivity.class);
            startActivity(i);
        });

        NavigationView nv = binding.navView;
        DrawerLayout d1 = binding.drawerLayout;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barToggle = new ActionBarDrawerToggle(this,d1, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_open);
                Toast.makeText(CreationActivity.this, "OUVERT", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_close);
                Toast.makeText(CreationActivity.this, "FERMER", Toast.LENGTH_SHORT).show();
            }
        };
        d1.addDrawerListener(barToggle);
        barToggle.syncState();

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.accueil:
                        Intent i = new Intent(CreationActivity.this, AcceuilActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.tache:
                        Intent u = new Intent(CreationActivity.this, CreationActivity.class);
                        startActivity(u);
                        return true;
                    case R.id.deconnexion:
                        Intent d = new Intent(CreationActivity.this, ConnexionActivity.class);
                        startActivity(d);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(barToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        barToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        barToggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }
}