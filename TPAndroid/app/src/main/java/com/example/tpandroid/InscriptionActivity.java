package com.example.tpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tpandroid.databinding.ActivityInscriptionBinding;
import com.example.tpandroid.http.RetrofitCookie;
import com.example.tpandroid.http.ServiceCookie;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscriptionActivity extends AppCompatActivity {

    ActivityInscriptionBinding binding;
    ProgressDialog progressD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInscriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        Service service = RetrofitUtil.get();
        ServiceCookie service = RetrofitCookie.get();

        EditText user = findViewById(R.id.signupUser);
        EditText passw = findViewById(R.id.signupPassw);
        EditText confirmP = findViewById(R.id.signupPasswConfirm);

//        if(passw != confirmP)
//        {
//            Toast.makeText(InscriptionActivity.this, "Password not the same" , Toast.LENGTH_LONG).show();
//
//        }



        binding.versAcceuil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SigninRequest s = new SigninRequest();
                s.username = user.getText().toString();
                if (passw.getText().toString().equals(confirmP.getText().toString()))
                {
                    s.password = passw.getText().toString();
                }
                else
                {
                    Toast.makeText(InscriptionActivity.this, "Password not the same" , Toast.LENGTH_LONG).show();
                }

                progressD = ProgressDialog.show(InscriptionActivity.this, "Please wait",
                        getString(R.string.waiting), true);
                service.signup(s).enqueue(new Callback<SigninResponse>() {
                    @Override
                    public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                        progressD.dismiss();
                        if(response.isSuccessful()){

                            Intent i = new Intent(InscriptionActivity.this, AcceuilActivity.class);
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
                                if (corpsErreur.contains("UsernameTooShort")) {
                                    // TODO remplacer par un objet TropCourtgraphique mieux qu'un toast
                                    Toast.makeText(InscriptionActivity.this, "Ce message est trop court", Toast.LENGTH_SHORT).show();
                                }
                                else if(corpsErreur.contains("PasswordTooShort")) {
                                    Toast.makeText(InscriptionActivity.this, "Ce message est trop court", Toast.LENGTH_SHORT).show();
                                }
                                else if(corpsErreur.contains("UsernameAlreadyTaken")) {
                                    Toast.makeText(InscriptionActivity.this, "Ce message est trop court", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<SigninResponse> call, Throwable t) {
                        progressD.dismiss();

                        Log.i("RETROFIT", t.getMessage());
                    }
                });
            }

        });
    }
}