package com.example.tpandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tpandroid.databinding.ConnexionMainBinding;
import com.example.tpandroid.http.RetrofitCookie;
import com.example.tpandroid.http.RetrofitUtil;
import com.example.tpandroid.http.Service;
import com.example.tpandroid.http.ServiceCookie;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.example.tpandroid.databinding.ActivityAcceuilBinding;

import org.kickmyb.transfer.HomeItemResponse;
import org.kickmyb.transfer.SigninResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AcceuilActivity extends AppCompatActivity {

    ActivityAcceuilBinding binding;

    ItemAdapter adapter;
    ActionBarDrawerToggle barToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAcceuilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        ServiceCookie service = RetrofitCookie.get();

        // initialisation du recycler
        this.initRecycler();
        this.remplirRecycler();



        NavigationView nv = binding.navView;
        View header = nv.getHeaderView(0);
        TextView txt = (TextView) header.findViewById(R.id.navHeader);
        txt.setText(UtilStatic.username);
        DrawerLayout d1 = binding.drawerLayout;

        RetrofitCookie.get().acceuil().enqueue(new Callback<List<HomeItemResponse>>() {
            @Override
            public void onResponse(Call<List<HomeItemResponse>> call, Response<List<HomeItemResponse>> response) {
                // j'ai recu la liste, l faut que je l'affiche dans mon recycler
                List<HomeItemResponse> maListeDuServeur = response.body();
                remplacer(maListeDuServeur);

            }

            @Override
            public void onFailure(Call<List<HomeItemResponse>> call, Throwable t) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barToggle = new ActionBarDrawerToggle(this,d1, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_open);
                Toast.makeText(AcceuilActivity.this, "OUVERT", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_close);
                Toast.makeText(AcceuilActivity.this, "FERMER", Toast.LENGTH_SHORT).show();
            }
        };
        d1.addDrawerListener(barToggle);
        barToggle.syncState();

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.accueil:
                        Intent i = new Intent(AcceuilActivity.this, AcceuilActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.tache:
                        Intent u = new Intent(AcceuilActivity.this, CreationActivity.class);
                        startActivity(u);
                        return true;
                    case R.id.deconnexion:
                        service.signout().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.isSuccessful()){
                                    UtilStatic.username = "";
                                    Intent d = new Intent(AcceuilActivity.this, ConnexionActivity.class);
                                    startActivity(d);
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.i("RETROFIT", t.getMessage());
                                Toast.makeText(AcceuilActivity.this, "Déconnexion échoué" , Toast.LENGTH_LONG).show();
                            }
                        });
                        return true;
                }
                return false;
            }
        });


    }

    private void remplacer(List<HomeItemResponse> laliste){
        adapter.list.clear();
        for (HomeItemResponse item : laliste) {
            Item p = new Item();
//            LocalDateTime date = LocalDateTime.now();
//            DateTimeFormatter datetwo = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            String dateform = date.format(datetwo);
            p.nom = item.name;
            p.date = item.deadline.toString();
//            p.date  =  dateform;
//            p.pourcentage = new Random().nextInt(20);
//            p.tempsEcouler = new Random().nextInt(20);
            adapter.list.add(0,p);
        }
        adapter.notifyDataSetChanged();
    }

    private void remplirRecycler() {
        for (int i = 0 ; i < 1000 ; i++) {
            Item p = new Item();
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter datetwo = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateform = date.format(datetwo);
            p.nom = "Bob " + i;
            p.date  = dateform;
            p.pourcentage = new Random().nextInt(20);
            p.tempsEcouler = new Random().nextInt(20);
            adapter.list.add(p);
        }
        adapter.notifyDataSetChanged();
    }

    private void initRecycler(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.goToCreation) {
            Intent i = new Intent(AcceuilActivity.this, CreationActivity.class);
            startActivity(i);
            return true;
        }

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