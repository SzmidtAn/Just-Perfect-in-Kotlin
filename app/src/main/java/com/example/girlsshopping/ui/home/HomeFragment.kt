package com.example.girlsshopping.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.girlsshopping.MainActivity
import com.example.girlsshopping.R
import com.example.girlsshopping.products.Product
import com.example.girlsshopping.products.ProductRecyclerViewAdapter
import com.example.girlsshopping.products.ProductsDataBase
import com.facebook.FacebookSdk
import java.util.*

class HomeFragment : Fragment(), OnRefreshListener {
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    var onProductClickedListener: OnProductClickedListener? = null
        private set
    private var recyclerView: RecyclerView? = null
    var expenses: List<Product?>? = null
    private var productRecyclerViewAdapter: ProductRecyclerViewAdapter? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        swipeRefreshLayout = view.findViewById<View>(R.id.swipe_refresh_layout) as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener(this)
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.setLayoutManager(LinearLayoutManager(view.context))
        expenses = ProductsDataBase.getDataBase(view.context).productDao.findAll()
        val expenseSort: List<Product> = ArrayList()
        println(expenses)
        Collections.reverse(expenses)
        println(expenses)
        println(expenseSort)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter((expenses as MutableList<Product>?)!!)
        recyclerView!!.setAdapter(productRecyclerViewAdapter)
        return view
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        onProductClickedListener = try {
            activity as OnProductClickedListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnAnimalClickedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        onProductClickedListener = null
    }

    override fun onStart() {
        super.onStart()
        refreshAdapterData()
    }

    interface OnProductClickedListener {
        fun onProductClicked(id: Int)
    }

    fun refreshAdapterData() {
        val expenses = ProductsDataBase.getDataBase(context).productDao.findAll()
        if (expenses != null) {
            productRecyclerViewAdapter!!.clear()
            productRecyclerViewAdapter!!.addAll(expenses)
            productRecyclerViewAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onRefresh() {
        val intent = Intent(FacebookSdk.getApplicationContext(), MainActivity::class.java)
        startActivity(intent)
        swipeRefreshLayout!!.isRefreshing = false
    }


}