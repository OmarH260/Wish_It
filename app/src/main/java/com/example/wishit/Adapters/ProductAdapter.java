package com.example.wishit.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishit.AddDataFire.FirebaseServices;
import com.example.wishit.AddDataFire.Product;
import com.example.wishit.MainActivity;
import com.example.wishit.Pages.ProductDetailsFragment;
import com.example.wishit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{
    private List<Product> productList;
    private Context context;
    private FirebaseServices fbs;
    private ProductAdapter.OnItemClickListener itemClickListener;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.productList = productList;
        this.context = context;
        this.fbs = FirebaseServices.getInstance();

        this.itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /*
                String selectedItem = filteredList.get(position).getNameCar();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show(); */
                Bundle args = new Bundle();
                args.putParcelable("car", productList.get(position)); // or use Parcelable for better performance
                ProductDetailsFragment cd = new ProductDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft= ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain,cd);
                ft.commit();
            }
        } ;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pro_item,parent,false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvTittle.setText(product.getTittle());
        //holder.tvDescription.setText(product.getDescription());
        holder.rbProduct.setRating((float) (productList.get(position).getRating()));
        holder.tvPrice.setText(product.getPrice());



        holder.tvTittle.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });

        //holder.tvDescription.setOnClickListener(v -> {
          //  if (itemClickListener != null) {
            //    itemClickListener.onItemClick(position);
            //}
        //});

        holder.tvPrice.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });

        holder.ivPhoto.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });

        if (product.getPhoto() == null || product.getPhoto().isEmpty())
        {
            Picasso.get().load(R.drawable.ic_menu_gallery).into(holder.ivPhoto);
        }
        else {
            Picasso.get().load(product.getPhoto()).into(holder.ivPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPrice, tvTittle, tvDescription;
        ImageView ivPhoto;
        RatingBar rbProduct;
        int position;

        ViewHolder(View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tvTitleProItem);
            tvPrice = itemView.findViewById(R.id.tvPriceProItem);
            //tvDescription = itemView.findViewById(R.id.tvDescriptionProItem);
            rbProduct =itemView.findViewById(R.id.rbProductProItem);
            ivPhoto = itemView.findViewById(R.id.ivPhotoProItem);
        }

        @Override
        public void onClick(View v) {
            //((MainActivity)context).get().showMessageDialog(context, productList.get(position).get());

        }


    }
        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    public void setOnItemClickListener(ProductAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

}