package com.example.girlsshopping.products

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.girlsshopping.R
import com.example.girlsshopping.dialog.DialogMail
import com.example.girlsshopping.dialog.ShopDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductDetailFragment : Fragment() {
    var bottomNavigation: BottomNavigationView? = null
    private var title: TextView? = null
    private var price: TextView? = null
    private var currency: TextView? = null
    private var imageView: ImageView? = null
    private var description: TextView? = null
    private var button: Button? = null
    private var checkBox: ImageButton? = null
    private var category: TextView? = null
    private var size: TextView? = null
    private var brand: TextView? = null
    private var condition: TextView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_product_detail, container, false)
        title = view.findViewById<View>(R.id.ttitle) as TextView
        imageView = view.findViewById<View>(R.id.imageDetailFragment) as ImageView
        description = view.findViewById<View>(R.id.description) as TextView
        price = view.findViewById(R.id.priceDetail)
        currency = view.findViewById(R.id.currencyDetail)
        button = view.findViewById(R.id.buttonShop)
        category = view.findViewById(R.id.category)
        size = view.findViewById(R.id.size)
        brand = view.findViewById(R.id.brand)
        condition = view.findViewById(R.id.condition)
        val fabMail: FloatingActionButton = view.findViewById(R.id.fabMail)
        checkBox = view.findViewById(R.id.likeCkeckBox)
        fabMail.setOnClickListener {
            val manager = parentFragmentManager
            val dialogMail = DialogMail()
            dialogMail.show(manager, "DialogMail")
        }
        button!!.setOnClickListener(View.OnClickListener {
            val manager = parentFragmentManager
            val shopDialogFragment = ShopDialogFragment()
            shopDialogFragment.show(manager, "ShopDialogFragment")
        })
        if (arguments != null) {
            val animalId = requireArguments().getInt(PRODUCTS_ID)
            showAnimal(ProductsDataBase.getDataBase(context).productDao.findAll()[animalId])
        }
        return view
    }

    private fun updateProductLike(product: Product) {
        if (product.isFavourite) {
            product.isFavourite = false
            checkBox!!.setImageResource(R.mipmap.heart_foreground)
        } else if (!product.isFavourite) {
            product.isFavourite = true
            checkBox!!.setImageResource(R.drawable.red_heartt_t_foreground)
        }
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    fun showAnimal(product: Product) {
        title!!.text = product.name
        price!!.text = product.price
        currency!!.setText(R.string.currency)
        category!!.text = getString(R.string.category) + product.category
        size!!.text = getString(R.string.size) + product.size
        brand!!.text = getString(R.string.brand) + product.brand
        condition!!.text = getString(R.string.condition) + product.condition
        description!!.text = product.description
        Glide.with(this)
                .asBitmap()
                .load(product.photoString)
                .centerCrop()
                .into(imageView!!)
        button!!.setText(R.string.kup_teraz)
        if (product.isFavourite) {
            checkBox!!.setImageResource(R.drawable.red_heartt_t_foreground)
        } else if (!product.isFavourite) {
            checkBox!!.setImageResource(R.mipmap.heart_foreground)
        }
        checkBox!!.setOnClickListener { view ->
            updateProductLike(product)
            ProductsDataBase.getDataBase(view.context).productDao.update(product)
            Toast.makeText(view.context, R.string.add_delete_from_favourites, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val PRODUCTS_ID = "extra.product_id"
    }
}