package com.example.wishit.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishit.AddDataFire.Card;
import com.example.wishit.AddDataFire.FirebaseServices;
import com.example.wishit.MainActivity;
import com.example.wishit.Pages.FilterProductsFragment;
import com.example.wishit.Pages.ProductDetailsFragment;
import com.example.wishit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>  {
    private List<Card> cardsList;
    private Context context;
    private FirebaseServices fbs;
    private CardAdapter.OnItemClickListener itemClickListener;


    public CardAdapter(Context context, ArrayList<Card> cardsList) {
        this.cardsList = cardsList;
        this.context = context;
        this.fbs = FirebaseServices.getInstance();

        this.itemClickListener = new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /*
                String selectedItem = filteredList.get(position).getNameCar();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show(); */
                Bundle args = new Bundle();
                args.putParcelable("Cards", cardsList.get(position)); // or use Parcelable for better performance
                FilterProductsFragment cd = new FilterProductsFragment();
                cd.setArguments(args);
                FragmentTransaction ft= ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain,cd);
                ft.commit();
            }
        };
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new CardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cardsList.get(position);
        holder.tvTittle.setText(card.getTitle());

        holder.tvTittle.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });

        holder.ivPhoto.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });

        if (card.getPhoto() == null || card.getPhoto().isEmpty())
        {
            Picasso.get().load(R.drawable.ic_menu_gallery).into(holder.ivPhoto);
        }
        else {
            Picasso.get().load(card.getPhoto()).into(holder.ivPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTittle;
        ImageView ivPhoto;
        int position;

        ViewHolder(View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tvTitleCardItem);
            ivPhoto = itemView.findViewById(R.id.ivPhotoCardItem);
        }

        @Override
        public void onClick(View v) {
            //((MainActivity)context).get().showMessageDialog(context, productList.get(position).get());

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CardAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

}
