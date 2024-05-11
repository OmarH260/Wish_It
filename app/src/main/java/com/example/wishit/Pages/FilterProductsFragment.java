package com.example.wishit.Pages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wishit.AddDataFire.Card;
import com.example.wishit.AddDataFire.FirebaseServices;
import com.example.wishit.AddDataFire.Product;
import com.example.wishit.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterProductsFragment extends Fragment {
    private TextView tvTittle;
    private RecyclerView rvProducts;
    private FirebaseServices fbs;
    private Card card;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilterProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterProductsFragment newInstance(String param1, String param2) {
        FilterProductsFragment fragment = new FilterProductsFragment();
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
        return inflater.inflate(R.layout.fragment_filter_products, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectComponents();
    }

    private void connectComponents() {
        fbs = FirebaseServices.getInstance();
        tvTittle = getView().findViewById(R.id.tvTypeNameFilterProducts);
        rvProducts = getView().findViewById(R.id.rvProductsFilterProducts);

        Bundle args = getArguments();
        if (args != null){
            card = args.getParcelable("Type/Cards");
            if (card != null){
                tvTittle.setText(card.getTitle());
            }
        }
    }
}