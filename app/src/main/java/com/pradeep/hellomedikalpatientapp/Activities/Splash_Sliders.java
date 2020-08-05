package com.pradeep.hellomedikalpatientapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pradeep.hellomedikalpatientapp.Adapter.Adapter_Viewpager;
import com.pradeep.hellomedikalpatientapp.R;
import com.rd.PageIndicatorView;

public class Splash_Sliders extends AppCompatActivity {

    ViewPager viewPager;
   public TextView txtvw_splash_skip;
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_slider);

        /*********************************************** SETTING STATUS BAR WHITE ******************************************************************/

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        /********************************************************************************************************************************************/

        txtvw_splash_skip=findViewById(R.id.txtvw_splash_skip);
       // tabLayout=findViewById(R.id.tablayout);
        viewPager =findViewById(R.id.splash_viewpager);


        viewPager.setAdapter(new Adapter_Viewpager(getSupportFragmentManager()));
        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setBackgroundResource(R.drawable.shape_splash);
        pageIndicatorView.setViewPager(viewPager);

        txtvw_splash_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Splash_Sliders.this, Splash_Next.class);
                startActivity(intent);
            }
        });
    }
}
