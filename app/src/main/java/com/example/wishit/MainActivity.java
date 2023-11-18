package com.example.wishit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.wishit.AddDataFire.AddProductFragment;
import com.example.wishit.AddDataFire.AllProductsFragment;
import com.example.wishit.SingUpLogin.LoginFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseServices fbs = FirebaseServices.getInstance();

        if (fbs.getAuth().getCurrentUser() == null)
            gotoLoginFragment();
        else gotoHomeFragment();

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

    private void gotoAllProductsFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new AllProductsFragment());
        ft.commit();
    }
}