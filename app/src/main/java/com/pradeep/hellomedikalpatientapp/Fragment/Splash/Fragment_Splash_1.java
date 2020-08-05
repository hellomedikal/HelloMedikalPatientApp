package com.pradeep.hellomedikalpatientapp.Fragment.Splash;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pradeep.hellomedikalpatientapp.Activities.Splash_Sliders;
import com.pradeep.hellomedikalpatientapp.R;


public class Fragment_Splash_1 extends Fragment {

    Splash_Sliders splash_sliders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.splash_1, container, false);


        return view;
    }

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if(activity.getClass()== Splash_Sliders.class){
            splash_sliders = (Splash_Sliders) activity;
        }
    }
}
