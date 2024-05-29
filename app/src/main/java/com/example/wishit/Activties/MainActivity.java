package com.example.wishit.Activties;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wishit.Data.FirebaseServices;
import com.example.wishit.Data.User;
import com.example.wishit.Pages.AddCardFragment;
import com.example.wishit.Pages.AddProductFragment;
import com.example.wishit.Pages.HomeFragment;
import com.example.wishit.Pages.LoginFragment;
import com.example.wishit.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    Boolean isAdmin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseServices fbs = FirebaseServices.getInstance();
        String userID = fbs.getAuth().getCurrentUser().getUid();

        DocumentReference documentReference = fbs.getFire().collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                isAdmin = value.getBoolean("admin");
            }
        });


        if(fbs.getAuth().getCurrentUser() == null){
            gotoLoginFragment();
        }
        else if(!isAdmin){
            gotoHomeFragment();
        }
        else {

        }

        //gotoAddCardFragment();
        //gotoAddProductFragment();
    }

    private void gotoAddProductFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain,new AddProductFragment());
        ft.commit();
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