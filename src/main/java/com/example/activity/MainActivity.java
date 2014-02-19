package com.example.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.R;
import com.example.fragments.MainFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.main_activity, new MainFragment()).commit();
        }
    }
}
