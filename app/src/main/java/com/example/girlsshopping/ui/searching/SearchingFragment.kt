package com.example.girlsshopping.ui.searching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.girlsshopping.R

class SearchingFragment : Fragment() {
    private val recyclerView: RecyclerView? = null
    private val productRecyclerViewAdapter: Adapter? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragments_searching, container, false)
        val simpleSearchView = view.findViewById<View>(R.id.simpleSearchView) as SearchView // inititate a search view
        val queryHint = simpleSearchView.queryHint // get the hint text that will be displayed in the query text field
        return view
    }
}