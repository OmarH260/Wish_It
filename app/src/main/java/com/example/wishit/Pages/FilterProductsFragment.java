package com.example.wishit.Pages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wishit.Adapters.ProductAdapter;
import com.example.wishit.AddDataFire.Card;
import com.example.wishit.AddDataFire.FirebaseServices;
import com.example.wishit.AddDataFire.Product;
import com.example.wishit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    private ArrayList<Product> products;
    private ProductAdapter productAdapter;


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
        setupProductAdapter();
    }

    private void setupProductAdapter() {
        fbs = FirebaseServices.getInstance();
        products = new ArrayList<>();
        rvProducts = getView().findViewById(R.id.rvProductsFilterProducts);
        productAdapter = new ProductAdapter(getActivity(), products);
        rvProducts.setAdapter(productAdapter);
        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        fbs.getFire().collection("Type").document("Products").collection(tvTittle.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    Product product = dataSnapshot.toObject(Product.class);

                    products.add(product);
                }
                productAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                Log.e("AllProductsFragment", e.getMessage());
            }
        });

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                Bundle args = new Bundle();
                args.putParcelable("product", products.get(position)); // or use Parcelable for better performance
                ProductDetailsFragment cd = new ProductDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain,cd);
                ft.commit();
            }
        });
    }

    private void connectComponents() {
        fbs = FirebaseServices.getInstance();
        tvTittle = getView().findViewById(R.id.tvTypeNameFilterProducts);
        rvProducts = getView().findViewById(R.id.rvProductsFilterProducts);

        Bundle args = getArguments();
         if (args != null){
              card = args.getParcelable("Cards");
                if (card != null){
                    tvTittle.setText(card.getTitle());
                }
            }
        }
    }