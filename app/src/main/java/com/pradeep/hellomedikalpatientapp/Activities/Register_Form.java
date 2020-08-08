package com.pradeep.hellomedikalpatientapp.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pradeep.hellomedikalpatientapp.Interface.ApiLogin_Interface;
import com.pradeep.hellomedikalpatientapp.POJO.ModelRegister;
import com.pradeep.hellomedikalpatientapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register_Form extends AppCompatActivity {

    LinearLayout layout_male,layout_female;
    ImageView img_male, img_female;
    TextView txtvw_male, txtvw_female,txtvw_signup_form_login,txtvw_signup_form_dob;
    String gender,checked;
    EditText edtxt_signup_form_email,edtxt_signup_form_name,edtxt_signup_form_phone,edtxt_signup_form_password;
    Button btn_signup_form_register;
    CheckBox checkBox1;
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form);

        layout_male = findViewById(R.id.layout_male);
        layout_female = findViewById(R.id.layout_female);
        img_male  = findViewById(R.id.img_male);
        img_female  = findViewById(R.id.img_female);
        txtvw_male  = findViewById(R.id.txtvw_male);
        txtvw_female  = findViewById(R.id.txtvw_female);
        txtvw_signup_form_login = findViewById(R.id.txtvw_signup_form_login);
        btn_signup_form_register = findViewById(R.id.btn_signup_form_register);
        edtxt_signup_form_email  = findViewById(R.id.edtxt_signup_form_email);
        edtxt_signup_form_name  = findViewById(R.id.edtxt_signup_form_name);
        edtxt_signup_form_phone  = findViewById(R.id.edtxt_signup_form_phone);
        edtxt_signup_form_password  = findViewById(R.id.edtxt_signup_form_password);
        txtvw_signup_form_dob  = findViewById(R.id.txtvw_signup_form_dob);
        checkBox1 = findViewById(R.id.checkBox1);


        /*********************************************** SETTING STATUS BAR WHITE ******************************************************************/

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        /********************************************************************************************************************************************/


        String email = Paper.book().read("email");
        String phone = Paper.book().read("user_phone");

        if (email != null){

            edtxt_signup_form_email.setText(email);
            Paper.book().write("email","");
            Paper.book().write("phn","");
        }

        if (phone != null){

            edtxt_signup_form_phone.setText(phone);
            Paper.book().write("email","");
            Paper.book().write("phn","");
        }



        txtvw_signup_form_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Register_Form.this,Login.class);
                startActivity(intent);


            }
        });


        gender= "male";

        layout_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender= "male";
                txtvw_male.setTextColor(getResources().getColor(R.color.themecolor));
                txtvw_female.setTextColor(getResources().getColor(R.color.textcolor));
                img_male.setBackground(getResources().getDrawable(R.drawable.ic_male_blue_icon));
                img_female.setBackground(getResources().getDrawable(R.drawable.ic_female_grey_icon));

            }
        });

        layout_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender= "female";
                txtvw_male.setTextColor(getResources().getColor(R.color.textcolor));
                txtvw_female.setTextColor(getResources().getColor(R.color.themecolor));
                img_male.setBackground(getResources().getDrawable(R.drawable.ic_male_grey));
                img_female.setBackground(getResources().getDrawable(R.drawable.ic_female_blue));

            }
        });

        Paper.book().write("checked","0");

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked){

                    Paper.book().write("checked","1");

                }
                else{

                    Paper.book().write("checked","0");

                }
            }
        });

        txtvw_signup_form_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();

                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Register_Form.this,R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                        String del_date = dateFormat.format(calendar.getTime());

                        txtvw_signup_form_dob.setText(del_date);


                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime() - 10000);
                datePickerDialog.show();


            }
        });

        btn_signup_form_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checked =Paper.book().read("checked");

                if(!TextUtils.isEmpty(edtxt_signup_form_email.getText().toString())){

                    if(!TextUtils.isEmpty(edtxt_signup_form_name.getText().toString())){

                        if(!TextUtils.isEmpty(edtxt_signup_form_phone.getText().toString())){

                            if (edtxt_signup_form_phone.getText().toString().length()== 10){



                            if(!TextUtils.isEmpty(edtxt_signup_form_password.getText().toString())){

                                if(!txtvw_signup_form_dob.getText().toString().equals("Date of Birth")){

                                    if (checked.equals("1")){

                                        Register();

                                    }

                                    else {

                                        Toast.makeText(getApplicationContext(),"Please tick the terms and condition checkbox",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {

                                    Toast.makeText(getApplicationContext(),"Please Enter Date of Birth",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {

                                Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
                            }


                            }
                            else {

                                Toast.makeText(getApplicationContext(),"Please Enter Correct Phone number",Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {

                            Toast.makeText(getApplicationContext(),"Please Enter Phone Number",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Please Enter Your Name",Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(getApplicationContext(),"Please Enter E-mail Address",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void Register() {


        final ProgressDialog progressDialogs = new ProgressDialog(Register_Form.this,R.style.AlertDialogCustom);
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

        Log.e("data1",edtxt_signup_form_email.getText().toString()+"");
        Log.e("data1",edtxt_signup_form_name.getText().toString()+"");
        Log.e("data1",edtxt_signup_form_phone.getText().toString()+"");
        Log.e("data1",edtxt_signup_form_password.getText().toString()+"");
        Log.e("data1",txtvw_signup_form_dob.getText().toString()+"");
        Log.e("data1",gender+"");

        service.REGISTER_CALL("adeladmin","Basic YWRtaW46MTIzNA==","application/x-www-form-urlencoded",edtxt_signup_form_email.getText().toString(),edtxt_signup_form_password.getText().toString(),edtxt_signup_form_phone.getText().toString(),
                edtxt_signup_form_name.getText().toString(),txtvw_signup_form_dob.getText().toString(),gender).enqueue(new Callback<ModelRegister>() {
            @Override
            public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {

                if (response.body().getStatusCode().equals(200)){

                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Register_Form.this,Sample_Home_Screen.class);
                    startActivity(intent);

                }

                else {

                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                }

                progressDialogs.dismiss();

            }

            @Override
            public void onFailure(Call<ModelRegister> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                progressDialogs.dismiss();
            }
        });


    }
}
