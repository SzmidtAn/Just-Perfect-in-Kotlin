package com.example.girlsshopping.products;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.girlsshopping.R;
import com.example.girlsshopping.dialog.DialogMail;
import com.example.girlsshopping.dialog.ShopDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductDetailFragment extends Fragment {
    BottomNavigationView bottomNavigation;

    public static final String PRODUCTS_ID = "extra.product_id";
    private TextView title;
    private TextView price;
    private TextView currency;
    private ImageView imageView;
    private TextView description;
    private Button button;
    private ImageButton checkBox;
    private TextView category;
    private TextView size;
    private TextView brand;
    private TextView condition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        title = (TextView) view.findViewById(R.id.ttitle);
        imageView = (ImageView) view.findViewById(R.id.imageDetailFragment);
        description = (TextView) view.findViewById(R.id.description);
        price=view.findViewById(R.id.priceDetail);
        currency=view.findViewById(R.id.currencyDetail);
        button=view.findViewById(R.id.buttonShop);
        category=view.findViewById(R.id.category);
        size=view.findViewById(R.id.size);
        brand=view.findViewById(R.id.brand);
        condition=view.findViewById(R.id.condition);
        FloatingActionButton fabMail = view.findViewById(R.id.fabMail);
        checkBox=view.findViewById(R.id.likeCkeckBox);








        fabMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager=getParentFragmentManager();
                DialogMail dialogMail =new DialogMail();
                dialogMail.show(manager, "DialogMail");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager=getParentFragmentManager();
                ShopDialogFragment shopDialogFragment=new ShopDialogFragment();
                shopDialogFragment.show(manager, "ShopDialogFragment");
            }
        });

        if(getArguments() != null) {
            int animalId = getArguments().getInt(PRODUCTS_ID);

            showAnimal(ProductsDataBase.getDataBase(getContext()).getProductDao().findAll().get(animalId));
        }

        return view;
    }





    private void updateProductLike(Product product) {

        if (product.isFavourite()) {
            product.setFavourite(false);
            checkBox.setImageResource(R.mipmap.heart_foreground);

        } else if (!product.isFavourite()){
            product.setFavourite(true);
            checkBox.setImageResource(R.drawable.red_heartt_t_foreground);

        }
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    public void showAnimal(Product product) {

        title.setText(product.getName());
        price.setText(product.getPrice() );
        currency.setText(R.string.currency);

        category.setText(getString(R.string.category) + product.getCategory() );
        size.setText(getString(R.string.size) + product.getSize() );
        brand.setText(getString(R.string.brand) + product.getBrand() );
        condition.setText(getString(R.string.condition) + product.getCondition());
        description.setText(product.getDescription());

            Glide.with(this)
                .asBitmap()
                .load(product.getPhotoString())
                    .centerCrop()
                    .into(imageView);

        button.setText(R.string.kup_teraz);

        System.out.println(product.getId());

        if (product.isFavourite()) {
            checkBox.setImageResource(R.drawable.red_heartt_t_foreground);

        } else if (!product.isFavourite()){
            checkBox.setImageResource(R.mipmap.heart_foreground);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                updateProductLike(product);

                ProductsDataBase.getDataBase(view.getContext()).getProductDao().update(product);
                Toast.makeText(view.getContext(), R.string.add_delete_from_favourites, Toast.LENGTH_SHORT).show();
            }
        });



    }



}