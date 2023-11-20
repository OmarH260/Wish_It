package com.example.wishit.AddDataFire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishit.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{
    private List<Product> productList;
    private Context context;
    private FirebaseServices fbs;

    public ProductAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.context = context;
        this.fbs = FirebaseServices.getInstance();
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
        holder.tvDescription.setText(product.getDescription());
        holder.tvPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPrice, tvTittle, tvDescription;
        int position;

        ViewHolder(View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tvTittleProItem);
            tvPrice = itemView.findViewById(R.id.tvPriceProItem);
            tvDescription = itemView.findViewById(R.id.tvDescriptionProItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //((MainActivity)context).get().showMessageDialog(context, productList.get(position).get());

        }
    }
}