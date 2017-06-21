package com.example.deeppatel.mosersuggestionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick_Catering(View v ) {
        startActivity(new Intent(MainActivity.this, Catering.class));
    }
    public void onClick_TM(View v) {
        startActivity(new Intent(MainActivity.this, Team_Managers.class));
    }
    public void onClick_HR(View v){
        startActivity(new Intent(MainActivity.this, HR.class ));
    }
    public void onClick_Finance(View v){
        startActivity(new Intent(MainActivity.this, Finance.class));
    }

}
