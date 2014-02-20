package com.example.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.R;
import com.example.fragments.HomeFragment;
import com.example.fragments.ListFragment;
import com.example.fragments.MapFragment;

public class MainActivity extends Activity implements HomeFragment.onItemClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.main_activity, new HomeFragment()).commit();
        }
    }

    @Override
    public void onListButtonClicked(){
        ListFragment listFragment = new ListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity, listFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onMapButtonClicked(){
        MapFragment mapFragment = new MapFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.main_activity, mapFragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }
}
