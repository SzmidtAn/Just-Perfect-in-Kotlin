package com.example.girlsshopping.products

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.girlsshopping.MainActivity
import com.example.girlsshopping.R
import com.example.girlsshopping.products.ProductRecyclerViewAdapter.RecyclerViewHolder

class ProductRecyclerViewAdapter(private var products: MutableList<Product>) : RecyclerView.Adapter<RecyclerViewHolder>() {
    private val favourites: List<Product>? = null
    var shareCheckBox: ImageButton? = null
    var likeCheckBox: ImageButton? = null
    var messageCheckBox: ImageButton? = null
    fun setExpenses(products: MutableList<Product>) {
        this.products = products
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.products_list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.products_list, parent, false)
        shareCheckBox = v.findViewById(R.id.shareButton)
        messageCheckBox = v.findViewById(R.id.messageButton)
        likeCheckBox = v.findViewById(R.id.likeCkeckBox)
        messageCheckBox!!.setOnClickListener(View.OnClickListener { view: View ->
            val context = view.context
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, "a.szmidt95@gmail.com")
            intent.type = "message/rfc822"
            val chooser = Intent.createChooser(intent, context.getString(R.string.send_email))
            context.startActivity(chooser)
            Toast.makeText(parent.context, R.string.send_message, Toast.LENGTH_SHORT).show()
        })
        return RecyclerViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val product = products[position]
        if (product != null) {
            if (product.isFavourite) {
                likeCheckBox!!.setImageResource(R.drawable.red_heartt_t_foreground)
            } else if (!product.isFavourite) {
                likeCheckBox!!.setImageResource(R.mipmap.heart_foreground)
            }
            holder.textViewNameProd.text = product.name
            holder.productsPrice.text = product.price
            holder.currency.setText(R.string.currency)
            Glide.with(holder.viewImage.context)
                    .asBitmap()
                    .load(product.photoString)
                    .centerCrop()
                    .into(holder.viewImage)
            likeCheckBox!!.setOnClickListener { view ->
                updateProductLike(product)
                ProductsDataBase.getDataBase(view.context).productDao.update(product)
                Toast.makeText(view.context, R.string.add_delete_from_favourites, Toast.LENGTH_SHORT).show()
                notifyItemChanged(position)
            }
            shareCheckBox!!.setOnClickListener { view ->
                ProductsDataBase.getDataBase(view.context).productDao.delete(product)
                Toast.makeText(view.context, R.string.delete_item, Toast.LENGTH_SHORT).show()
                val context = view.context
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    private fun updateProductLike(product: Product) {
        if (product.isFavourite) {
            product.isFavourite = false
            likeCheckBox!!.setImageResource(R.mipmap.heart_foreground)
        } else if (!product.isFavourite) {
            product.isFavourite = true
            likeCheckBox!!.setImageResource(R.drawable.red_heartt_t_foreground)
        }
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var viewImage: ImageView
        var textViewNameProd: TextView
        var productsPrice: TextView
        var currency: TextView
        override fun toString(): String {
            return super.toString() + " '" + textViewNameProd.text
        }

        override fun onClick(v: View) {
            val context = v.context
            val intent = Intent(context, ProductDetailActivity::class.java)
            val bundle = Bundle()
            val i = layoutPosition
            bundle.putInt(ProductDetailFragment.PRODUCTS_ID, i)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

        init {
            viewImage = itemView.findViewById<View>(R.id.productsImageView) as ImageView
            textViewNameProd = itemView.findViewById<View>(R.id.nameTeview) as TextView
            productsPrice = itemView.findViewById(R.id.price)
            currency = itemView.findViewById(R.id.currency)
            itemView.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun clear() {
        products.clear()
    }

    fun addAll(list: List<Product?>?) {
        products.addAll(list as MutableList<Product>)
        notifyDataSetChanged()
    }

}


