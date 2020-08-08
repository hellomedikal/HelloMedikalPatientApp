package com.pradeep.hellomedikalpatientapp.Fragment.Signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pradeep.hellomedikalpatientapp.Activities.Login;
import com.pradeep.hellomedikalpatientapp.Activities.Otp_Screen;
import com.pradeep.hellomedikalpatientapp.Activities.Sample_Home_Screen;
import com.pradeep.hellomedikalpatientapp.Interface.ApiLogin_Interface;
import com.pradeep.hellomedikalpatientapp.POJO.ModelEmailVerify;
import com.pradeep.hellomedikalpatientapp.R;

import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Signup_Email  extends Fragment {

    public Button btn_register_email;
    EditText email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_email, container, false);

        btn_register_email = view.findViewById(R.id.btn_register_email);
        email = view.findViewById(R.id.edtxt_signup_via_email_emailaddress);

        btn_register_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(email.getText().toString())){

                    Paper.book().write("email_address",email.getText().toString());
                    email_verification();

                }

                else {

                    Toast.makeText(getActivity(),"Please Enter Email-Address",Toast.LENGTH_SHORT).show();

                }

            }
        });


        return view;
    }

    public void email_verification() {


        final ProgressDialog progressDialogs = new ProgressDialog(getActivity(),R.style.AlertDialogCustom);
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

        service.EMAIL_VERIFY_CALL("adeladmin","Basic YWRtaW46MTIzNA==","application/x-www-form-urlencoded",email.getText().toString()).enqueue(new Callback<ModelEmailVerify>() {
            @Override
            public void onResponse(Call<ModelEmailVerify> call, Response<ModelEmailVerify> response) {
                if (response.body().getStatusCode().equals(200)){

                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), Otp_Screen.class);

                    Paper.book().write("otp",response.body().getOtp().toString());
                    Paper.book().write("email",email.getText().toString());
                    Paper.book().write("phn","");

                    startActivity(intent);

                }

                else {

                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                }

                progressDialogs.dismiss();
            }

            @Override
            public void onFailure(Call<ModelEmailVerify> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                progressDialogs.dismiss();

            }
        });
    }
}
