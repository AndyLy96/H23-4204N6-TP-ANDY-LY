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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tpandroid.databinding.ActivityConsultationBinding;
import com.example.tpandroid.http.RetrofitCookie;
import com.example.tpandroid.http.ServiceCookie;
import com.google.android.material.navigation.NavigationView;

import org.kickmyb.transfer.TaskDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationActivity extends AppCompatActivity {

    ActionBarDrawerToggle barToggle;

    ActivityConsultationBinding binding;
    ProgressDialog progressD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ServiceCookie service = RetrofitCookie.get();




        Long id = getIntent().getLongExtra("id", -1L);





//        Service service = RetrofitUtil.get();
        NavigationView nv = binding.navView;
        View header = nv.getHeaderView(0);
        TextView txt = (TextView) header.findViewById(R.id.navHeader);
        txt.setText(UtilStatic.username);
        DrawerLayout d1 = binding.drawerLayout;
        TextView taskname = findViewById(R.id.taskNameDetail);
        TextView taskDateDue = findViewById(R.id.taskdueDateDetail);
        TextView taskTempsÉcoulé = findViewById(R.id.taskElapsedTimeDetail);
        EditText edPercentage = findViewById(R.id.taskPourcentageDetail);
        Button btnProgres = findViewById(R.id.confirmProgress);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressD = ProgressDialog.show(ConsultationActivity.this, "Please wait",
                "Long operation starts...", true);
        service.taskDetail(id).enqueue(new Callback<TaskDetailResponse>() {

            @Override
            public void onResponse(Call<TaskDetailResponse> call, Response<TaskDetailResponse> response) {
                // gerer les erreurs
                progressD.dismiss();
                taskname.setText(response.body().name);
                taskDateDue.setText(response.body().deadline.toString());
                taskTempsÉcoulé.setText("" + response.body().percentageTimeSpent);
                edPercentage.setText("" + response.body().percentageDone);
            }

            @Override
            public void onFailure(Call<TaskDetailResponse> call, Throwable t) {
                progressD.dismiss();
                Log.i("RETROFIT", t.getMessage());
                Toast.makeText(ConsultationActivity.this, "Création de la tache échoué" , Toast.LENGTH_LONG).show();
            }
        });


        binding.confirmProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressD = ProgressDialog.show(ConsultationActivity.this, "Please wait",
                        "Long operation starts...", true);
                service.taskProgress(id, Integer.parseInt(edPercentage.getText().toString())).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            progressD.dismiss();
                            Intent i = new Intent(ConsultationActivity.this, AcceuilActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.i("RETROFIT", t.getMessage());
                        progressD.dismiss();
                        Toast.makeText(ConsultationActivity.this, "Progress change échoué" , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        barToggle = new ActionBarDrawerToggle(this,d1, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_open);
                Toast.makeText(ConsultationActivity.this, "OUVERT", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_close);
                Toast.makeText(ConsultationActivity.this, "FERMER", Toast.LENGTH_SHORT).show();
            }
        };
        d1.addDrawerListener(barToggle);
        barToggle.syncState();

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.accueil:
                        Intent i = new Intent(ConsultationActivity.this, AcceuilActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.tache:
                        Intent u = new Intent(ConsultationActivity.this, CreationActivity.class);
                        startActivity(u);
                        return true;
                    case R.id.deconnexion:
                        progressD = ProgressDialog.show(ConsultationActivity.this, "Please wait",
                                "Long operation starts...", true);
                        service.signout().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.isSuccessful()){
                                    progressD.dismiss();
                                    UtilStatic.username = "";
                                    Intent d = new Intent(ConsultationActivity.this, ConnexionActivity.class);
                                    startActivity(d);
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                progressD.dismiss();
                                Log.i("RETROFIT", t.getMessage());
                                Toast.makeText(ConsultationActivity.this, "Déconnexion échoué" , Toast.LENGTH_LONG).show();
                            }
                        });
//                        Intent d = new Intent(ConsultationActivity.this, ConnexionActivity.class);
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