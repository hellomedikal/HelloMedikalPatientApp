package com.pradeep.hellomedikalpatientapp.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.pradeep.hellomedikalpatientapp.Fragment.Splash.Fragment_Splash_1;
import com.pradeep.hellomedikalpatientapp.Fragment.Splash.Fragment_Splash_2;
import com.pradeep.hellomedikalpatientapp.Fragment.Splash.Fragment_Splash_3;
import com.pradeep.hellomedikalpatientapp.Fragment.Splash.Fragment_Splash_4;
import com.pradeep.hellomedikalpatientapp.Fragment.Splash.Fragment_Splash_5;
import com.pradeep.hellomedikalpatientapp.Fragment.Splash.Fragment_Splash_6;


public class Adapter_Viewpager extends FragmentStatePagerAdapter {

    int tabcount;

    public Adapter_Viewpager(FragmentManager fragmentManager) {

        super(fragmentManager);

    }

    public Adapter_Viewpager(FragmentManager supportFragmentManager, int tabCount) {
        super(supportFragmentManager);
        this.tabcount=tabCount;
    }

    @Override
    public Fragment getItem(int i) {

        switch ( i) {

            case 0:
        {
            return new Fragment_Splash_1();
        }
            case 1:
        {
            return new Fragment_Splash_2();
        }
            case 2:
        {
            return new Fragment_Splash_3();
        }
            case 3:
        {
            return new Fragment_Splash_4();
        }
            case 4:
        {
            return new Fragment_Splash_5();
        }
            case 5:
         {
             return new Fragment_Splash_6();
         }

        default:

            return new Fragment_Splash_1();

      }
    }

    @Override
    public int getCount() {
        return 6;
    }

}
