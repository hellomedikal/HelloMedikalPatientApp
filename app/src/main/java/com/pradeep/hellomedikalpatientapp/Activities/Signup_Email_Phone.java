package com.pradeep.hellomedikalpatientapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.pradeep.hellomedikalpatientapp.Fragment.Signup.Signup_Email;
import com.pradeep.hellomedikalpatientapp.Fragment.Signup.Signup_Phone;
import com.pradeep.hellomedikalpatientapp.R;

import io.paperdb.Paper;

public class Signup_Email_Phone extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    LinearLayout layout_signup_type_email_inner,layout_signup_type_email_outer,layout_signup_type_phn_outer,layout_signup_type_phn_inner;
    ImageView imgvw_signup_type_email,imgvw_signup_type_phn;
    TextView txtvw_signup_type_email,txtvw_signup_type_phn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_email_phone);

        layout_signup_type_email_inner = findViewById(R.id.layout_signup_type_email_inner);
        layout_signup_type_email_outer = findViewById(R.id.layout_signup_type_email_outer);
        layout_signup_type_phn_outer = findViewById(R.id.layout_signup_type_phn_outer);
        layout_signup_type_phn_inner = findViewById(R.id.layout_signup_type_phn_inner);
        imgvw_signup_type_email = findViewById(R.id.imgvw_signup_type_email);
        imgvw_signup_type_phn = findViewById(R.id.imgvw_signup_type_phn);
        txtvw_signup_type_email = findViewById(R.id.txtvw_signup_type_email);
        txtvw_signup_type_phn = findViewById(R.id.txtvw_signup_type_phn);


        /*********************************************** SETTING STATUS BAR WHITE ******************************************************************/

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        /********************************************************************************************************************************************/




        fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("category").replace(R.id.containerr,new Signup_Email());
        fragmentTransaction.commit();

        layout_signup_type_email_outer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout_signup_type_email_outer.setBackgroundResource(R.drawable.shape_left_rounded_signup_blue);
                layout_signup_type_email_inner.setBackgroundResource(R.drawable.shape);
                layout_signup_type_phn_outer.setBackgroundResource(R.drawable.shape_right_rouded_signup_grey);
                layout_signup_type_phn_inner.setBackgroundResource(R.drawable.shape_grey);
                imgvw_signup_type_email.setBackground(getResources().getDrawable(R.drawable.ic_email_icon_white));
                imgvw_signup_type_phn.setBackground(getResources().getDrawable(R.drawable.ic_phone_icon_blue));
                txtvw_signup_type_email.setTextColor(getResources().getColor(R.color.white));
                txtvw_signup_type_phn.setTextColor(getResources().getColor(R.color.themecolor));

                Paper.book().write("verify_type","email");

                fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("category").replace(R.id.containerr,new Signup_Email());
                fragmentTransaction.commit();

            }
        });

        layout_signup_type_phn_outer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout_signup_type_email_outer.setBackgroundResource(R.drawable.shape_left_rounded_signup_blue);
                layout_signup_type_email_inner.setBackgroundResource(R.drawable.shape_grey);
                layout_signup_type_phn_outer.setBackgroundResource(R.drawable.shape_right_rouded_signup_grey);
                layout_signup_type_phn_inner.setBackgroundResource(R.drawable.shape);
                imgvw_signup_type_email.setBackground(getResources().getDrawable(R.drawable.ic_email_blue));
                imgvw_signup_type_phn.setBackground(getResources().getDrawable(R.drawable.ic_phone_white));
                txtvw_signup_type_email.setTextColor(getResources().getColor(R.color.themecolor));
                txtvw_signup_type_phn.setTextColor(getResources().getColor(R.color.white));

                Paper.book().write("verify_type","phone");


                fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("category").replace(R.id.containerr,new Signup_Phone());
                fragmentTransaction.commit();

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Signup_Email_Phone.this,Splash_Next.class);
        startActivity(intent);
    }
}


