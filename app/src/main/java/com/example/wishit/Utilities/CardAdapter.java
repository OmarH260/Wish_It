package com.example.wishit.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishit.AddDataFire.Card;
import com.example.wishit.AddDataFire.FirebaseServices;
import com.example.wishit.AddDataFire.Product;
import com.example.wishit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Card> cardsList;
    private Context context;
    private FirebaseServices fbs;


    public CardAdapter(Context context, ArrayList<Card> cardsList) {
        this.cardsList = cardsList;
        this.context = context;
        this.fbs = FirebaseServices.getInstance();
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
        holder.tvTitle.setText(card.getTitle());

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
        TextView tvTitle;
        ImageView ivPhoto;
        int position;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleCard);
            ivPhoto = itemView.findViewById(R.id.ivPhotoCard);
        }

        @Override
        public void onClick(View v) {
            //((MainActivity)context).get().showMessageDialog(context, productList.get(position).get());

        }
    }


}
