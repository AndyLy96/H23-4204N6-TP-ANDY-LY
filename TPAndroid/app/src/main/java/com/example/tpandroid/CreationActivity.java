package com.example.tpandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tpandroid.databinding.ActivityCreationBinding;
import com.example.tpandroid.http.RetrofitCookie;
import com.example.tpandroid.http.ServiceCookie;
import com.google.android.material.navigation.NavigationView;

import org.kickmyb.transfer.AddTaskRequest;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreationActivity extends AppCompatActivity {

    ActivityCreationBinding binding;
    ActionBarDrawerToggle barToggle;
    ProgressDialog progressD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ServiceCookie service = RetrofitCookie.get();

//        Service service = RetrofitUtil.get();
        EditText taskNametext = findViewById(R.id.taskName);
        DatePicker datePicker = findViewById(R.id.datePicker);

        binding.versAcceuil3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddTaskRequest request = new AddTaskRequest();
                request.name = taskNametext.getText().toString();
                long date = datePicker.getCalendarView().getDate();
                Date officialDate = new Date(date);
                request.deadline = officialDate;
                progressD = ProgressDialog.show(CreationActivity.this, getString(R.string.loading),
                        getString(R.string.waiting), true);
                service.addtask(request).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        // gerer les erreurs
                        progressD.dismiss();
                        if(response.isSuccessful())
                        {
                            Intent i = new Intent(CreationActivity.this, AcceuilActivity.class);
                            startActivity(i);
                        }else {
                            // ERROR ERROR ERROR
                            try {
                                String corpsErreur = response.errorBody().string();
                                Log.i("RETROFIT", "le code " + response.code());
                                Log.i("RETROFIT", "le message " + response.message());
                                Log.i("RETROFIT", "le corps " + corpsErreur);
                                if (corpsErreur.contains("TooShort")) {
                                    // TODO remplacer par un objet TropCourtgraphique mieux qu'un toast
                                    Toast.makeText(CreationActivity.this, "Ce message est trop court", Toast.LENGTH_SHORT).show();
                                }else if (corpsErreur.contains("Existing")){
                                    Toast.makeText(CreationActivity.this, "Ce message est trop court", Toast.LENGTH_SHORT).show();
                                }else if (corpsErreur.contains("Empty")){
                                    Toast.makeText(CreationActivity.this, "Ce message est trop court", Toast.LENGTH_SHORT).show();
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressD.dismiss();

                        Log.i("RETROFIT", t.getMessage());
                        Toast.makeText(CreationActivity.this, "Création de la tache échoué" , Toast.LENGTH_LONG).show();
                    }
                });


            }

        });

        NavigationView nv = binding.navView;
        View header = nv.getHeaderView(0);
        TextView txt = (TextView) header.findViewById(R.id.navHeader);
        txt.setText(UtilStatic.username);
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
                        progressD = ProgressDialog.show(CreationActivity.this, getString(R.string.loading),
                                "Long operation starts...", true);
                        service.signout().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                progressD.dismiss();
                                if(response.isSuccessful()){
                                    UtilStatic.username = "";
                                    Intent d = new Intent(CreationActivity.this, ConnexionActivity.class);
                                    startActivity(d);
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                progressD.dismiss();
                                Log.i("RETROFIT", t.getMessage());
                                Toast.makeText(CreationActivity.this, "Déconnexion échoué" , Toast.LENGTH_LONG).show();
                            }
                        });
//                        Intent d = new Intent(CreationActivity.this, ConnexionActivity.class);
//                        startActivity(d);
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