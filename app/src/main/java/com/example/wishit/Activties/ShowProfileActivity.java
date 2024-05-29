package com.example.wishit.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wishit.Data.FirebaseServices;
import com.example.wishit.Pages.LoginFragment;
import com.example.wishit.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ShowProfileActivity extends AppCompatActivity {
    TextView tvName, tvPhone, tvSignOut, tvEdit;
    FirebaseServices fbs;
    boolean guest = false;
    boolean signOut = false;
    DocumentReference documentReference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        tvName = findViewById(R.id.tvNameProfile);
        tvPhone = findViewById(R.id.tvPhoneProfile);
        tvSignOut = findViewById(R.id.tvSignOutProfile);
        tvEdit = findViewById(R.id.tvEditProfile);
        fbs = FirebaseServices.getInstance();
        userID = fbs.getAuth().getCurrentUser().getUid();
        documentReference = fbs.getFire().collection("Users").document(userID);



        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                tvName.setText(value.getString("firstName") + " " + value.getString("lastName"));
                tvPhone.setText(value.getString("phoneNumber"));
                if (tvPhone.getText().toString().trim().isEmpty()) {
                    tvPhone.setVisibility(View.GONE);


                }
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ShowProfileActivity.this, EditActivity.class);
                startActivity(in);
            }
        });
        tvSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbs.getAuth().signOut();
                Toast.makeText(ShowProfileActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(ShowProfileActivity.this, MainActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
}