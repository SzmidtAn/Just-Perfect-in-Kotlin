package com.example.girlsshopping.ui.searching;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.girlsshopping.R;
import com.example.girlsshopping.products.Product;
import com.example.girlsshopping.products.ProductDetailActivity;
import com.example.girlsshopping.products.ProductDetailFragment;
import com.example.girlsshopping.products.ProductRecyclerViewAdapter;
import com.example.girlsshopping.products.ProductsDataBase;
import com.example.girlsshopping.ui.favourites.FavouritesRecyclerViewAdapter;
import com.example.girlsshopping.ui.gallery.GalleryViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchingFragment extends Fragment {


    private RecyclerView recyclerView;
    private Adapter productRecyclerViewAdapter;
    private String FindName;
    List<Product> products;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragments_searching, container, false);
        SearchView simpleSearchView = (SearchView) view.findViewById(R.id.simpleSearchView); // inititate a search view
        CharSequence queryHint = simpleSearchView.getQueryHint(); // get the hint text that will be displayed in the query text field

        products = ProductsDataBase.getDataBase(view.getContext()).getProductDao().findAll();


        TextView textViewName=view.findViewById(R.id.findTextViewName);
        TextView textViewPrice=view.findViewById(R.id.findTextViewPrice);
        ImageView imageView=view.findViewById(R.id.findProductsImageView);

        textViewName.setText("Can't find");




        simpleSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFound) {

            }
        });

        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }



            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    //todo, would display if text is more than 2 letters

                    for (int i = 0; i <products.size() ; i++) {
                        if (products.get(i).getName().substring(0, 3).equalsIgnoreCase(newText)) {
                    textViewName.setText(products.get(i).getName());
                    textViewPrice.setText(products.get(i).getPrice());

                            Glide.with(imageView.getContext())
                                    .asBitmap()
                                    .load(products.get(i).getPhotoString())
                                    .centerCrop()
                                    .into(imageView);
                            long index=products.get(i).getId();
                            int ind=Math.toIntExact(index);

                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Context context = getContext();
                                    Intent intent = new Intent(context, ProductDetailActivity.class);
                                    Bundle bundle = new Bundle();

                                    bundle.putInt(ProductDetailFragment.PRODUCTS_ID, ind-1);
                                    System.out.println(ind);
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                }
                            });

                        }
                    }


                }
                return false;
            }
        });



        return view;
    }
}