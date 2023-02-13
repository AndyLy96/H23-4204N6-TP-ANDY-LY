package com.example.tpandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class AcceuilActivity extends AppCompatActivity {

    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        // initialisation du recycler
        this.initRecycler();
        this.remplirRecycler();

        this.remplacer();

    }

    private void remplacer(){
        adapter.list.clear();
        for (int i = 1 ; i <= 200 ; i++) {
            Item p = new Item();
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter datetwo = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateform = date.format(datetwo);
            p.nom = "Objet#" + i;
            p.date  =  dateform;
            p.pourcentage = new Random().nextInt(20);
            p.tempsEcouler = new Random().nextInt(20);
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
        return super.onOptionsItemSelected(item);
    }

}