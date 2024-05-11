package com.example.wishit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.wishit.AddDataFire.FirebaseServices;
import com.example.wishit.Pages.AddCardFragment;
import com.example.wishit.Pages.HomeFragment;
import com.example.wishit.Pages.LoginFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseServices fbs = FirebaseServices.getInstance();

        //if (fbs.getAuth().getCurrentUser() == null)
          //  gotoLoginFragment();
          gotoHomeFragment();

        //gotoAddCardFragment();
    }

    private void gotoHomeFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain,new HomeFragment());
        ft.commit();
    }

    private void gotoLoginFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new LoginFragment());
        ft.commit();
    }


    private void gotoAddCardFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new AddCardFragment());
        ft.commit();
    }
}