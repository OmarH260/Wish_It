package com.example.wishit.Pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.wishit.AddDataFire.Product;
import com.example.wishit.AddDataFire.FirebaseServices;
import com.example.wishit.R;
import com.example.wishit.Utilities.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private float starRating;
    private static final int GALLERY_REQUEST_CODE = 123;
    private FirebaseServices fbs;
    private RecyclerView rvProducts;
    private EditText etTittle, etDescription, etPrice;
    private ImageView ivShow;
    private Utils utils;
    private Button btnAdd;
    private ArrayList<Product> products;
    private RatingBar rbProduct;
    private CardView cvAddPhotos;
    private ArrayList<Uri> photos;
    private ImageView image;

    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
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

        FirebaseApp.initializeApp(getActivity());
        photos = new ArrayList<>();

        cvAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        connectComponents();

    }

    private void connectComponents() {
        fbs = FirebaseServices.getInstance();
        etTittle = getView().findViewById(R.id.etTittleAddProductFragment);
        utils = Utils.getInstance();
        etDescription = getView().findViewById(R.id.etDescAddProductFragment);
        etPrice = getView().findViewById(R.id.etPriceAddProductFragment);
        rvProducts = getView().findViewById(R.id.rvProductsProFragment);
        ivShow = getView().findViewById(R.id.ivShowAddProduct);
        btnAdd = getView().findViewById(R.id.btnAddAddProductFragment);
        rbProduct = getView().findViewById(R.id.rbProductProductDetails);
        cvAddPhotos = getView().findViewById(R.id.cvAddPhotosAddProduct);
        image = getView().findViewById(R.id.image);

        ivShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        cvAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data from screen
                String tittle = etTittle.getText().toString();
                String description = etDescription.getText().toString();
                String price = etPrice.getText().toString();
                //RatingBar productRatingBar = getView().findViewById(R.id.rbProductProductDetails);
                float rating = 0;//productRatingBar.getRating();
                // data validation
                if (tittle.trim().isEmpty() || description.trim().isEmpty() ||
                        price.trim().isEmpty())
                {
                    Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                // add data to firestore
                Product product = new Product(fbs.getSelectedImageURL().toString(), tittle, description, price, 0);

                fbs.getFire().collection("product").add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Successfully added your product!", Toast.LENGTH_SHORT).show();
                        // gotoHomeFragment
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.frameLayoutMain,new HomeFragment());
                            ft.commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Failure AddProduct: ", e.getMessage());
                    }
                });


            }
        });
        btnAdd.setText("Uploading ...");
        btnAdd.setEnabled(false);

        uploadImages(new ArrayList<>());
    }

    private void uploadImages(ArrayList<String> imagesUriList) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images").child(UUID.randomUUID().toString());
        Uri uri = photos.get(imagesUriList.size());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String url = task.getResult().toString();
                        imagesUriList.add(url);

                        if (photos.size() == imagesUriList.size()){
                            Toast.makeText(getActivity(), "Images uploaded successfully", Toast.LENGTH_SHORT).show();
                            btnAdd.setText("Add");
                            btnAdd.setEnabled(true);
                        } else {
                            uploadImages(imagesUriList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to upload Images", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            ivShow.setImageURI(selectedImageUri);
            utils.uploadImage(getActivity(), selectedImageUri);
        }
    }
    //
    final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getData() != null && o.getData().getClipData() != null){
                int count = o.getData().getClipData().getItemCount();
                for (int i = 0; i < count; i++){
                    Uri imageUri = o.getData().getClipData().getItemAt(i).getUri();
                    photos.add(imageUri);
                }
                if (photos.size() >= 1){
                    btnAdd.setEnabled(true);
                }
            }
        }
    });
    //
    public void calculateStarRating()
    {
        float sum = 0;
        for(Product c : products)
        {
            sum += c.getRating();
        }

        starRating = sum / products.size();
        rbProduct.setRating(starRating);
    }
}