package com.example.sidrajawaid.demofirebase.MainApplicationContent;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sidrajawaid.demofirebase.MainApplicationContent.LogIn;
import com.example.sidrajawaid.demofirebase.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager frgmng=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=frgmng.beginTransaction();
        LogIn fragment1=new LogIn();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.parentframelayout,fragment1);
        fragmentTransaction.commit();
    }
}
