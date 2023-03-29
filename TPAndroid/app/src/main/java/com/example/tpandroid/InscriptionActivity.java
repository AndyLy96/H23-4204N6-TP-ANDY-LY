package com.example.tpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tpandroid.databinding.ActivityCreationBinding;
import com.example.tpandroid.databinding.ActivityInscriptionBinding;
import com.example.tpandroid.http.RetrofitCookie;
import com.example.tpandroid.http.RetrofitUtil;
import com.example.tpandroid.http.Service;
import com.example.tpandroid.http.ServiceCookie;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscriptionActivity extends AppCompatActivity {

    ActivityInscriptionBinding binding;


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

                service.signup(s).enqueue(new Callback<SigninResponse>() {
                    @Override
                    public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                        if(response.isSuccessful()){
                            Intent i = new Intent(InscriptionActivity.this, AcceuilActivity.class);
//                            i.putExtra("intent", response.body().username);
                            UtilStatic.username = response.body().username;
                            startActivity(i);
                        }
                    }
                    @Override
                    public void onFailure(Call<SigninResponse> call, Throwable t) {
                        Log.i("RETROFIT", t.getMessage());
                    }
                });
            }

        });
    }
}