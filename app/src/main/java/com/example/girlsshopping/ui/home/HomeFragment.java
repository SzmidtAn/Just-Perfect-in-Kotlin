package com.example.girlsshopping.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.girlsshopping.MainActivity;
import com.example.girlsshopping.R;
import com.example.girlsshopping.products.Product;
import com.example.girlsshopping.products.ProductCategory;
import com.example.girlsshopping.products.ProductRecyclerViewAdapter;
import com.example.girlsshopping.products.ProductsDataBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;

    private HomeFragment.OnProductClickedListener onProductClickedListener;

    private RecyclerView recyclerView;


    List<Product> products;

    private ProductRecyclerViewAdapter productRecyclerViewAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);


        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



      products = ProductsDataBase.getDataBase(view.getContext()).getProductDao().findAll();
            List<Product> expenseSort=new ArrayList<>();




            products.retainAll(products);

        productRecyclerViewAdapter = new ProductRecyclerViewAdapter(products);
        recyclerView.setAdapter(productRecyclerViewAdapter);






        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onProductClickedListener = (OnProductClickedListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnAnimalClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onProductClickedListener = null;
    }

    public OnProductClickedListener getOnProductClickedListener() {
        return onProductClickedListener;
    }

    @Override
    public void onStart() {
        super.onStart();

        refreshAdapterData();




    }



    public interface OnProductClickedListener {
        void onProductClicked(int id);
    }


    public void refreshAdapterData() {
        List<Product> expenses = ProductsDataBase.getDataBase(getContext()).getProductDao().findAll();
        if (expenses!= null){

            productRecyclerViewAdapter.clear();
            productRecyclerViewAdapter.addAll(expenses);
            productRecyclerViewAdapter.notifyDataSetChanged();
        }



    }





    @Override
    public void onRefresh() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        swipeRefreshLayout.setRefreshing(false);

    }

    }

