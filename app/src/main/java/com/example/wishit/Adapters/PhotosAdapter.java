package com.example.wishit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishit.AddDataFire.Card;
import com.example.wishit.AddDataFire.FirebaseServices;
import com.example.wishit.R;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    private List<String> photosList;
    private Context context;
    private FirebaseServices fbs;

    public PhotosAdapter(Context context, ArrayList<String> photosList){
        this.photosList = photosList;
        this.context = context;
        this.fbs = FirebaseServices.getInstance();
    }
    @NonNull
    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_photos,parent,false);
        return new PhotosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosAdapter.ViewHolder holder, int position) {
        String photo = photosList.get(position);
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhotos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhotos = itemView.findViewById(R.id.ivPhotoProductPhotos);
        }
    }
}
