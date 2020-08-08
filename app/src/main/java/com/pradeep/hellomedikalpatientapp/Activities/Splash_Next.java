package com.pradeep.hellomedikalpatientapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pradeep.hellomedikalpatientapp.R;


public class Splash_Next extends AppCompatActivity {

    TextView splash_next_link_open_driver,explore_as_guest;
    Button btn_login,btn_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_next);

        splash_next_link_open_driver=findViewById(R.id.explore_as_guest);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        explore_as_guest= findViewById(R.id.explore_as_guest);


        /*********************************************** SETTING STATUS BAR WHITE ******************************************************************/

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        /********************************************************************************************************************************************/


        splash_next_link_open_driver.setPaintFlags(splash_next_link_open_driver.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);  // SET UNDERLINE BELOW FORGOT PASSWORD

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Splash_Next.this, Login.class);
               // Paper.book().write("btn_login_signup","1");
                startActivity(intent);

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Splash_Next.this, Signup_Email_Phone.class);
                //Paper.book().write("btn_login_signup","2");
                startActivity(intent);

            }
        });

        explore_as_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Splash_Next.this, Sample_Home_Screen.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this,R.style.AlertDialogCustom)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // Splash_Next.super.onBackPressed();
                        finishAffinity();
                        System.exit(0);
                        //finish();

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
