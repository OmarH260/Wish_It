package com.example.wishit.SingUpLogin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wishit.FirebaseServices;
import com.example.wishit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    FirebaseServices fbs;
    Button btnLogin, btnSignup, btnForgot;
    EditText etEmail, etPassword;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        connectComponents();
    }

    private void connectComponents(){
        fbs = FirebaseServices.getInstance();
        etPassword = getView().findViewById(R.id.etPasswordLogin);
        etEmail = getView().findViewById(R.id.etEmailLogin);
        btnForgot = getView().findViewById(R.id.btnForgotLogin);
        btnLogin = getView().findViewById(R.id.btnLoginLogin);
        btnSignup = getView().findViewById(R.id.btnSignupLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                //Data validation
                if (email.trim().isEmpty() && password.trim().isEmpty()){
                    Toast.makeText(getActivity(), "Some Fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Login procedure
                fbs.getAuth().signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getActivity(), "Succeeded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Check the email or password you entered!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignupFragment();
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoForgotFragment();
            }
        });
    }

    private void gotoSignupFragment(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new SignupFragment());
        ft.commit();
    }

    private void gotoForgotFragment(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new ForgotFragment());
        ft.commit();
    }
}