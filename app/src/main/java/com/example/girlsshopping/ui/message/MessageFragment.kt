package com.example.girlsshopping.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.girlsshopping.R

class MessageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragments_messages, container, false)
        val textView = root.findViewById<TextView>(R.id.emptyMessageTextView)
        return root
    }
}