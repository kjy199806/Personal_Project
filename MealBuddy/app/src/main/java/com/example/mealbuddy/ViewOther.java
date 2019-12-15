package com.example.mealbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ViewOther extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_other);
        String data = getIntent().getExtras().getString("username");
        // Data value
        System.out.println("Data = " + data);
    }

}
