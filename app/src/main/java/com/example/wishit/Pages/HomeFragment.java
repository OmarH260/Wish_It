package com.example.wishit.Pages;

import android.graphics.Rect;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wishit.AddDataFire.Card;
import com.example.wishit.AddDataFire.FirebaseServices;
import com.example.wishit.AddDataFire.Product;
import com.example.wishit.R;
import com.example.wishit.Adapters.CardAdapter;
import com.example.wishit.Adapters.ProductAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FirebaseServices fbs;
    ArrayList<Card> cards;
    ArrayList<Product> products;
    RecyclerView rvCards,rvProducts;
    CardAdapter cardAdapter;
    ProductAdapter productAdapter;
    ImageButton btnLogoHome, btnAdd;
    Button  btnAddCard;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectComponents();
        setupCardAdapter();
        setupProductAdapter();
    }



    //Setup adapters
    private void setupCardAdapter() {
        fbs = FirebaseServices.getInstance();
        cards = new ArrayList<>();
        rvCards = getView().findViewById(R.id.rvCardsHome);
        cardAdapter = new CardAdapter(getActivity(), cards);
        rvCards.setAdapter(cardAdapter);
        rvCards.setHasFixedSize(true);

        // Set LinearLayoutManager to horizontal
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvCards.setLayoutManager(layoutManager);

        int spaceInPixels = 10; // Adjust this value as needed
        rvCards.addItemDecoration(new SpacesItemDecoration(spaceInPixels));


        fbs.getFire().collection("Cards").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot : queryDocumentSnapshots.getDocuments()) {
                    Card card = dataSnapshot.toObject(Card.class);
                    cards.add(card);
                }
                cardAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                Log.e("AllProductsFragment", e.getMessage());
            }
        });
    }

    private void setupProductAdapter() {
        fbs = FirebaseServices.getInstance();
        products = new ArrayList<>();
        rvProducts = getView().findViewById(R.id.rvProductsHome);
        productAdapter = new ProductAdapter(getActivity(), products);
        rvProducts.setAdapter(productAdapter);
        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        fbs.getFire().collection("product").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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

    //Designing
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }



    private void connectComponents() {
        btnAdd = getView().findViewById(R.id.btnAddHome);
        btnAddCard = getView().findViewById(R.id.btnAddCardHome);
        btnLogoHome = getView().findViewById(R.id.btnLogoHome);

        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddCardsFragment();
            }
        });

        btnLogoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddProductsFragment();
            }
        });

    }

    private void gotoAddProductsFragment(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new AddProductFragment());
        ft.commit();
    }

    private void gotoAddCardsFragment(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new AddCardFragment());
        ft.commit();
    }
}