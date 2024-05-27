package com.example.wishit.Pages;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wishit.Data.FirebaseServices;
import com.example.wishit.Data.Product;
import com.example.wishit.R;
import com.squareup.picasso.Picasso;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {
    private FirebaseServices fbs;
    private RatingBar rbProduct;
    private ImageView ivPhoto;
    private TextView tvTitle, tvDescription, tvPrice;
    private Product product;
    private float starRating;
    private Button btnBuy;
    PaymentSheet paymentSheet;
    private String customerID, ephemeralKey, clientSecret;
    private String PUBLISHABLE_KEY = "pk_live_51Oc7KTJOFcueWEXiprglF06IgeL40if39h5BdscKRJki1eLHKpd9KrTGSLYPVLKYvQM6Rq7vp7Vk2eaZ46jyIaJk00rL7xErIu";
    private String SECRET_KEY = "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";



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        PaymentConfiguration.init(getContext(), PUBLISHABLE_KEY);

        paymentSheet = new PaymentSheet(this, PaymentSheetResult ->{
            onPaymentResult(PaymentSheetResult);
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            customerID = jsonObject.getString("id");
                           // Toast.makeText(getContext(), customerID, Toast.LENGTH_SHORT).show();

                            getEphemeralKey(customerID);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SECRET_KEY);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(getContext(), "Payment Completed", Toast.LENGTH_SHORT).show();
            gotoHomeFragment();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectComponents();
    }

    private void connectComponents() {
        fbs = FirebaseServices.getInstance();
        starRating = 5;
        ivPhoto = getView().findViewById(R.id.ivPhotoProductDetails);
        tvTitle = getView().findViewById(R.id.tvTitleProductDetails);
        tvDescription = getView().findViewById(R.id.tvDescriptionProductDetails);
        tvPrice = getView().findViewById(R.id.tvPriceProductDetails);
        rbProduct = getView().findViewById(R.id.rbProductProductDetails);
        btnBuy = getView().findViewById(R.id.btnBuyProductDetails);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFlow();
            }
        });

        Bundle args = getArguments();
        if (args != null){
            product = args.getParcelable("product");
            if (product != null){
                tvDescription.setText(product.getDescription());
                tvPrice.setText(product.getPrice());
                tvTitle.setText(product.getTittle());
                rbProduct.setRating((float) product.getRating());
                if (product.getPhoto() == null || product.getPhoto().isEmpty())
                {
                    Picasso.get().load(R.drawable.ic_menu_gallery).into(ivPhoto);
                }
                else {
                    Picasso.get().load(product.getPhoto()).into(ivPhoto);
                }
                //Have to add recycler view for photos
            }
        }

    }

    private void getEphemeralKey(String customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            ephemeralKey = jsonObject.getString("id");
                            //Toast.makeText(getContext(), ephemeralKey, Toast.LENGTH_SHORT).show();

                            getClientSecret(customerID, ephemeralKey);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SECRET_KEY);
                headers.put("Stripe-Version", "2024-04-10 ");
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void getClientSecret(String customerID, String ephemeralKey) {
        Bundle args = getArguments();
        if (args != null){
            product = args.getParcelable("product");
            if (product != null){
                tvPrice.setText(product.getPrice());
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            clientSecret = jsonObject.getString("client_secret");
//                            Toast.makeText(getContext(), clientSecret, Toast.LENGTH_SHORT).show();
                            
                            PaymentFlow();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SECRET_KEY);
                headers.put("Stripe-Version", "2024-04-10 ");
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                params.put("amount", product.getPrice() + "00");
                params.put("currency", "usd");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void PaymentFlow() {
        paymentSheet.presentWithPaymentIntent(
                clientSecret,
                new PaymentSheet.Configuration(
                        "WishIt",
                        new PaymentSheet.CustomerConfiguration(
                                customerID,
                                ephemeralKey
                                ))
        );
    }

    private void gotoHomeFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain,new HomeFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}