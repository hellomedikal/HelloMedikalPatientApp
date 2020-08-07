package com.pradeep.hellomedikalpatientapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pradeep.hellomedikalpatientapp.Interface.ApiLogin_Interface;
import com.pradeep.hellomedikalpatientapp.POJO.ModelLogin;
import com.pradeep.hellomedikalpatientapp.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText edtxt_login_email, edtxt_login_password;
    Button btn_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn_login = findViewById(R.id.btn_login);
        edtxt_login_email =findViewById(R.id.edtxt_login_email);
        edtxt_login_password = findViewById(R.id.edtxt_login_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(edtxt_login_email.getText().toString())){

                    if (!TextUtils.isEmpty(edtxt_login_password.getText().toString())){

                        login();

                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(getApplicationContext(),"Please Enter Registered E-mail Address",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void login() {

        final ProgressDialog progressDialogs = new ProgressDialog(Login.this,R.style.AlertDialogCustom);
        progressDialogs.setCancelable(false);
        progressDialogs.setMessage("Please Wait.......");
        progressDialogs.show();


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.LOGIN_CALL("adeladmin","Basic YWRtaW46MTIzNA==","application/x-www-form-urlencoded",edtxt_login_email.getText().toString(),edtxt_login_password.getText().toString()).enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {

                if (response.body().getStatusCode().equals(200)){

                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Login.this,Sample_Home_Screen.class);
                    startActivity(intent);

                }

                else {

                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                }

                progressDialogs.dismiss();
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                progressDialogs.dismiss();
            }
        });

    }
}
