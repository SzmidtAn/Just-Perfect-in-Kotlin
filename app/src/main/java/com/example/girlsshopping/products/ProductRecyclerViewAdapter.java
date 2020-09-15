package com.example.girlsshopping.products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.girlsshopping.MainActivity;
import com.example.girlsshopping.R;

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.RecyclerViewHolder> {

    private List<Product> products;
    ImageButton shareCheckBox;
    ImageButton likeCheckBox;
    ImageButton messageCheckBox;

    public ProductRecyclerViewAdapter(List<Product> animals) {
        this.products = animals;
    }

    public void setExpenses(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.products_list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        final View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_list, parent, false);

        shareCheckBox = v.findViewById(R.id.shareButton);
       messageCheckBox = v.findViewById(R.id.messageButton);
       likeCheckBox=v.findViewById(R.id.likeCkeckBox);

        messageCheckBox.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, "a.szmidt95@gmail.com");
            intent.setType("message/rfc822");
            Intent chooser=Intent.createChooser(intent, context.getString(R.string.send_email));
            context.startActivity(chooser);

            Toast.makeText(parent.getContext(), R.string.send_message, Toast.LENGTH_SHORT).show();
        });



        return new RecyclerViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        final Product product = products.get(position);

        if (product !=null){

            if (product.isFavourite()) {
                likeCheckBox.setImageResource(R.drawable.red_heartt_t_foreground);

            } else if (!product.isFavourite()){
                likeCheckBox.setImageResource(R.mipmap.heart_foreground);
            }


            holder.textViewNameProd.setText(product.getName());
            holder.productsPrice.setText(product.getPrice());
            holder.currency.setText(R.string.currency);

            Glide.with(holder.viewImage.getContext())
                    .asBitmap()
                    .load(product.getPhotoString())
                    .centerCrop()
                    .into(holder.viewImage);





     likeCheckBox.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
           updateProductLike(product);

             ProductsDataBase.getDataBase(view.getContext()).getProductDao().update(product);
             Toast.makeText(view.getContext(), R.string.add_delete_from_favourites, Toast.LENGTH_SHORT).show();

             notifyItemChanged(position);

         }
     });



            shareCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ProductsDataBase.getDataBase(view.getContext()).getProductDao().delete(product);
                    Toast.makeText(view.getContext(), R.string.delete_item, Toast.LENGTH_SHORT).show();

                    Context context = view.getContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });


        }
    }

    private void updateProductLike(Product product) {

        if (product.isFavourite()) {
            product.setFavourite(false);
            likeCheckBox.setImageResource(R.mipmap.heart_foreground);

        } else if (!product.isFavourite()){
            product.setFavourite(true);
            likeCheckBox.setImageResource(R.drawable.red_heartt_t_foreground);
        }
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView viewImage;
        public TextView textViewNameProd;
        public TextView productsPrice;
        public TextView currency;

        public RecyclerViewHolder (View itemView) {
            super(itemView);
            viewImage = (ImageView) itemView.findViewById(R.id.productsImageView);
            textViewNameProd = (TextView) itemView.findViewById(R.id.nameTeview);
            productsPrice=itemView.findViewById(R.id.price);
            currency=itemView.findViewById(R.id.currency);

            itemView.setOnClickListener(this);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + textViewNameProd.getText();
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, ProductDetailActivity.class);
            Bundle bundle = new Bundle();

            int i=getLayoutPosition();
            bundle.putInt(ProductDetailFragment.PRODUCTS_ID, i);

            intent.putExtras(bundle);
            context.startActivity(intent);
        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void clear() {
        products.clear();

    }

    public void addAll(List<Product> list) {
        products.addAll(list);
        notifyDataSetChanged();
    }


}
