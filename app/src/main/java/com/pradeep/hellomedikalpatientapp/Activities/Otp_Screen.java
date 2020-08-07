package com.pradeep.hellomedikalpatientapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pradeep.hellomedikalpatientapp.Fragment.Signup.Signup_Email;
import com.pradeep.hellomedikalpatientapp.Fragment.Signup.Signup_Phone;
import com.pradeep.hellomedikalpatientapp.R;

import io.paperdb.Paper;


public class Otp_Screen extends AppCompatActivity {

    TextView txt_resend_code,textvw_phn_number;
    LinearLayout layout_otp_submit,layout_resend_screen;
    Button btn_resend_call_me,btn_resend_text_me;
    ImageView img_otp_submit;
    Signup_Email signup_email;
    Signup_Phone signup_phone;
    String data,code,phn_number,phn_number_without_code,country,action,email,user_id,first_name,last_name,phn;
    EditText otp_first_digit,otp_sec_digit,otp_third_digit,otp_fourth_digit;
    //SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_screen);

        img_otp_submit=findViewById(R.id.img_otp_submit);
        txt_resend_code=findViewById(R.id.txt_resend_code);
        layout_resend_screen=findViewById(R.id.layout_resend_screen);
        layout_otp_submit=findViewById(R.id.layout_otp_submit);
        btn_resend_text_me=findViewById(R.id.btn_resend_text_me);
        btn_resend_call_me=findViewById(R.id.btn_resend_call_me);
        otp_first_digit=findViewById(R.id.otp_first_digit);
        otp_sec_digit=findViewById(R.id.otp_sec_digit);
        otp_third_digit=findViewById(R.id.otp_third_digit);
        otp_fourth_digit=findViewById(R.id.otp_fourth_digit);
        textvw_phn_number=findViewById(R.id.textvw_phn_number);

        signup_email = new Signup_Email();
        signup_phone = new Signup_Phone();

       data = Paper.book().read("otp");
       email = Paper.book().read("email");
       phn  = Paper.book().read("phn");

        textvw_phn_number.setText(email);

        //btn_login_signup= Paper.book().read("btn_login_signup");

        otp_first_digit.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(otp_first_digit.getText().toString().length()==1)     //size as per your requirement
                {
                    otp_sec_digit.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        otp_sec_digit.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(otp_sec_digit.getText().toString().length()==1)     //size as per your requirement
                {
                    otp_third_digit.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        otp_third_digit.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(otp_third_digit.getText().toString().length()==1)     //size as per your requirement
                {
                    otp_fourth_digit.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        otp_sec_digit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {

                    otp_first_digit.requestFocus(); //this is for backspace
                }
                return false;
            }
        });

        otp_third_digit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {

                    otp_sec_digit.requestFocus(); //this is for backspace
                }
                return false;
            }
        });

        otp_fourth_digit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {

                    otp_third_digit.requestFocus(); //this is for backspace
                }
                return false;
            }
        });

        txt_resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String verify_type= Paper.book().read("verify_type");

               if (verify_type.equals("email")){

                 //  signup_email.email_verification();

               }
               else {

                  // signup_phone.Phone_verify("1",phn);

               }

            }
        });

        btn_resend_text_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                layout_resend_screen.setVisibility(View.GONE);
                layout_otp_submit.setVisibility(View.VISIBLE);

            }
        });



        img_otp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String first_digit=otp_first_digit.getText().toString();
                String second_digit=otp_sec_digit.getText().toString();
                String third_digit=otp_third_digit.getText().toString();
                String fourth_digit=otp_fourth_digit.getText().toString();

                String final_code= first_digit + second_digit + third_digit + fourth_digit;

                Log.e("final_code",final_code+"");
                Log.e("final_code2",data+"");

                if(!TextUtils.isEmpty(otp_first_digit.getText().toString())){

                    if(!TextUtils.isEmpty(otp_sec_digit.getText().toString())){

                        if(!TextUtils.isEmpty(otp_third_digit.getText().toString())){

                            if(!TextUtils.isEmpty(otp_fourth_digit.getText().toString())){

                                if(data.equals(final_code)){


                                    Intent intent = new Intent(Otp_Screen.this,Register_Form.class);
                                    intent.putExtra("email",email);

                                    startActivity(intent);


                                }
                                else {

                                    Toast.makeText(getApplicationContext(),"OTP Wrong", Toast.LENGTH_SHORT).show();

                                }
                            }

                            else{

                                Toast.makeText(getApplicationContext(),"Please Enter Otp's Fourth Digit", Toast.LENGTH_SHORT).show();
                            }

                        }

                        else{

                            Toast.makeText(getApplicationContext(),"Please Enter Otp's Third Digit", Toast.LENGTH_SHORT).show();
                        }


                    }

                    else{

                        Toast.makeText(getApplicationContext(),"Please Enter Otp's Second Digit", Toast.LENGTH_SHORT).show();
                    }


                }

                else{

                    Toast.makeText(getApplicationContext(),"Please Enter Otp's First Digit", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

/*
    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }*/


}
