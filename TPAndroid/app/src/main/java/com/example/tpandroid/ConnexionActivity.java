package com.example.tpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tpandroid.databinding.ConnexionMainBinding;
import com.example.tpandroid.http.RetrofitCookie;
import com.example.tpandroid.http.ServiceCookie;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnexionActivity extends AppCompatActivity {

    ConnexionMainBinding binding;
    ProgressDialog progressD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ConnexionMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EditText user = findViewById(R.id.signInUser);
        EditText passw = findViewById(R.id.signInPassW);


        ServiceCookie service = RetrofitCookie.get();

//        Service service = RetrofitUtil.get();


        binding.versInscription.setOnClickListener(view -> {
            Intent i = new Intent(ConnexionActivity.this, InscriptionActivity.class);
            startActivity(i);
        });
        //compte deja crée user andy passw andyly123

        binding.versAcceuil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SigninRequest s = new SigninRequest();
                s.username = user.getText().toString();
                s.password = passw.getText().toString();
                progressD = ProgressDialog.show(ConnexionActivity.this,getString(R.string.loading),
                        getString(R.string.waiting), true);
                service.signin(s).enqueue(new Callback<SigninResponse>() {
                    @Override
                    public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                        progressD.dismiss();
                        if(response.isSuccessful()){
                            Intent i = new Intent(ConnexionActivity.this, AcceuilActivity.class);
//                            i.putExtra("intent", response.body().username);
                            UtilStatic.username = response.body().username;
                            startActivity(i);
                        }else {
                            // ERROR ERROR ERROR
                            try {
                                String corpsErreur = response.errorBody().string();
                                Log.i("RETROFIT", "le code " + response.code());
                                Log.i("RETROFIT", "le message " + response.message());
                                Log.i("RETROFIT", "le corps " + corpsErreur);
                                if (corpsErreur.contains("BadCredentialsException")) {
                                    // TODO remplacer par un objet TropCourtgraphique mieux qu'un toast
                                    Toast.makeText(ConnexionActivity.this, "Ce message est trop court", Toast.LENGTH_SHORT).show();
                                }
                                else if (corpsErreur.contains("InternalAuthenticationServiceException")){
                                    Toast.makeText(ConnexionActivity.this, "Ce message est trop court", Toast.LENGTH_SHORT).show();

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<SigninResponse> call, Throwable t) {
                        Log.i("RETROFIT", t.getMessage());
                        progressD.dismiss();

                        Toast.makeText(ConnexionActivity.this, "Connexion échoué" , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
