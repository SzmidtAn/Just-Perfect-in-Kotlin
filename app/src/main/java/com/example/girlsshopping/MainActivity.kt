package com.example.girlsshopping

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.girlsshopping.products.AddProductActivity
import com.example.girlsshopping.products.ProductDetailActivity
import com.example.girlsshopping.products.ProductDetailFragment
import com.example.girlsshopping.ui.favourites.FavouritesFragment
import com.example.girlsshopping.ui.home.HomeFragment
import com.example.girlsshopping.ui.message.MessageFragment
import com.example.girlsshopping.ui.searching.SearchingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), HomeFragment.OnProductClickedListener {
    private val mAppBarConfiguration: AppBarConfiguration? = null
    var bottomNavigation: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.addFloatingActionBar)
        setSupportActionBar(toolbar)
        floatingActionButton.setOnClickListener {
            val intent = Intent(applicationContext, AddProductActivity::class.java)
            startActivity(intent)
        }
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation!!.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

    fun openFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment!!)
        transaction.commit()
    }

    var navigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home -> {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_searching -> {
                val searchingFragment = SearchingFragment()
                openFragment(searchingFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_favoriter -> {
                val favouritesFragment = FavouritesFragment()
                openFragment(favouritesFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_messages -> {
                val messageFragment = MessageFragment()
                openFragment(messageFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(applicationContext, R.string.empty_aviseringar, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }

    override fun onProductClicked(id: Int) {
        val bundle = Bundle()
        bundle.putInt(ProductDetailFragment.PRODUCTS_ID, id)
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}