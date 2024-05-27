package com.example.wishit.Pages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wishit.Data.FirebaseServices;
import com.example.wishit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    TextView tvName, tvPhone, tvSignOut, tvEdit;
    FirebaseServices fbs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        tvName = getView().findViewById(R.id.tvNameProfile);
        tvPhone = getView().findViewById(R.id.tvPhoneProfile);
        tvSignOut = getView().findViewById(R.id.tvSignOutProfile);
        tvEdit = getView().findViewById(R.id.tvEditProfile);
        fbs = FirebaseServices.getInstance();

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoEditFragment();
            }
        });
        tvSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbs.getAuth().signOut();
                Toast.makeText(getActivity(), "Successfully Signed Out", Toast.LENGTH_SHORT).show();
                gotoLoginFragment();
            }
        });
    }

    private void gotoLoginFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        // 1. Pop all existing fragments from the back stack
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // 2. Start a new FragmentTransaction
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // 3. Replace the content of the FrameLayoutMain with LoginFragment
        ft.replace(R.id.frameLayoutMain, new LoginFragment());

        // 4. Commit the transaction
        ft.commit();
    }

    private void gotoEditFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new EditFragment());
        ft.addToBackStack(null);
        ft.commit();
    }


}