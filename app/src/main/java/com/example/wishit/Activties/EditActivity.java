package com.example.wishit.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.wishit.Data.FirebaseServices;
import com.example.wishit.Pages.ForgotFragment;
import com.example.wishit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class EditActivity extends AppCompatActivity {
    EditText etLastName, etFirstName, etPhone;
    TextView tvChangePassword;
    Button btnSave;
    FirebaseServices fbs;
    String userID;
    boolean isGuest;

    private String PUBLISHABLE_KEY = "pk_live_51Oc7KTJOFcueWEXiprglF06IgeL40if39h5BdscKRJki1eLHKpd9KrTGSLYPVLKYvQM6Rq7vp7Vk2eaZ46jyIaJk00rL7xErIu";
    private String SECRET_KEY = "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";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etLastName = findViewById(R.id.etLastNameEdit);
        etFirstName = findViewById(R.id.etFirstNameEdit);
        etPhone = findViewById(R.id.etPhoneEdit);
        tvChangePassword = findViewById(R.id.tvChangePasswordEdit);
        btnSave = findViewById(R.id.btnSaveEdit);
        fbs = FirebaseServices.getInstance();
        userID = fbs.getAuth().getCurrentUser().getUid();
        DocumentReference documentReference = fbs.getFire().collection("Users").document(userID);


        //Check if Guest
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                isGuest = value.getBoolean("guest");
                if (isGuest) {
                    tvChangePassword.setVisibility(View.GONE);
                }
            }
        });


        //Get Data
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                etLastName.setText(value.getString("lastName"));
                etFirstName.setText(value.getString("firstName"));
                etPhone.setText(value.getString("phoneNumber"));
            }
        });

        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoForgotPassword();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastName = etLastName.getText().toString();
                String firstName = etFirstName.getText().toString();
                String phoneNumber = etPhone.getText().toString();

                //Update Data
                DocumentReference documentReference = fbs.getFire().collection("Users").document(userID);
                documentReference.update("lastName", lastName).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        documentReference.update("firstName", firstName).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                documentReference.update("phoneNumber", phoneNumber).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(EditActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                        gotoShowProfileActivity();
                                    }
                                });

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });
    }
    private void gotoForgotPassword() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new ForgotFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
    private void gotoShowProfileActivity() {
        Intent in = new Intent(this, ShowProfileActivity.class);
        startActivity(in);
    }
}