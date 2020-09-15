package com.example.girlsshopping.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.girlsshopping.MainActivity;
import com.example.girlsshopping.R;
import com.example.girlsshopping.ui.favourites.FavouritesFragment;
import com.example.girlsshopping.ui.home.HomeFragment;
import com.example.girlsshopping.ui.message.MessageFragment;
import com.example.girlsshopping.ui.searching.SearchingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductDetailActivity extends AppCompatActivity implements HomeFragment.OnProductClickedListener {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ProductDetailFragment productDetailFragment=new ProductDetailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        productDetailFragment.setArguments(getIntent().getExtras());
        transaction.add(R.id.container, productDetailFragment);
        transaction.commit();



        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);




        FloatingActionButton floatingActionButton=findViewById(R.id.addFloatingActionBar);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        finish();
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            return true;
                        case R.id.action_searching:
                            SearchingFragment searchingFragment=new SearchingFragment();
                            openFragment(searchingFragment);
                            return true;
                        case R.id.action_favoriter:
                            FavouritesFragment favouritesFragment=new FavouritesFragment();
                            openFragment(favouritesFragment);
                            return true;
                        case R.id.action_messages:
                            MessageFragment messageFragment=new MessageFragment();
                            openFragment(messageFragment);
                            return true;

                    }
                    return false;
                }
            };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(), R.string.message, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProductClicked(int id) {

    }

}
