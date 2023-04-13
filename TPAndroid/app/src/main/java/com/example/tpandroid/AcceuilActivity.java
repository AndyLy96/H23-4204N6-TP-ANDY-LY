package com.example.tpandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tpandroid.http.RetrofitCookie;
import com.example.tpandroid.http.ServiceCookie;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import com.example.tpandroid.databinding.ActivityAcceuilBinding;

import org.kickmyb.transfer.HomeItemResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AcceuilActivity extends AppCompatActivity {

    ActivityAcceuilBinding binding;

    ItemAdapter adapter;
    ActionBarDrawerToggle barToggle;

    ProgressDialog progressD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAcceuilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        ServiceCookie service = RetrofitCookie.get();

        // initialisation du recycler
        this.initRecycler();

        progressD = ProgressDialog.show(AcceuilActivity.this, "Please wait",
                "Long operation starts...", true);

        NavigationView nv = binding.navView;
        View header = nv.getHeaderView(0);
        TextView txt = (TextView) header.findViewById(R.id.navHeader);
        txt.setText(UtilStatic.username);
        DrawerLayout d1 = binding.drawerLayout;

        loadingInitialListe();

        initDrawer(service, nv, d1);


    }

    private void loadingInitialListe() {
        RetrofitCookie.get().acceuil().enqueue(new Callback<List<HomeItemResponse>>() {
            @Override
            public void onResponse(Call<List<HomeItemResponse>> call, Response<List<HomeItemResponse>> response) {
                // j'ai recu la liste, l faut que je l'affiche dans mon recycler
                progressD.dismiss();
                List<HomeItemResponse> maListeDuServeur = response.body();
                remplacer(maListeDuServeur);

            }

            @Override
            public void onFailure(Call<List<HomeItemResponse>> call, Throwable t) {
                progressD.dismiss();

            }
        });
    }

    private void initDrawer(ServiceCookie service, NavigationView nv, DrawerLayout d1) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barToggle = new ActionBarDrawerToggle(this, d1, R.string.drawer_open, R.string.drawer_close){
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
                        progressD = ProgressDialog.show(AcceuilActivity.this, "Please wait",
                                "Long operation starts...", true);
                        service.signout().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.isSuccessful()){
                                    progressD.dismiss();
                                    Intent d = new Intent(AcceuilActivity.this, ConnexionActivity.class);
                                    startActivity(d);
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                progressD.dismiss();
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

            adapter.list.add(0,item);
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