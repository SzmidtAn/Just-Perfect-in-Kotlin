package com.example.girlsshopping.products

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageInstaller
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.nfc.NfcAdapter
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.girlsshopping.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class AddProductActivity : AppCompatActivity() {
    var bitmap: Bitmap? = null
    var product: Product? = null
    private var fileUri: Uri? = null
    var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product)
        val categorySpinner = findViewById<Spinner>(R.id.expense_category)
        val sizeSpinner = findViewById<Spinner>(R.id.productSize)
        val brandSpinner = findViewById<Spinner>(R.id.productBrand)
        val conditionSpinner = findViewById<Spinner>(R.id.productCondition)
        val categories = ProductCategory.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, categories)
        categorySpinner.adapter = adapter

        // Spinner Drop down elements
        val size: MutableList<String> = ArrayList()
        size.add("XS")
        size.add("S")
        size.add("M ")
        size.add("L")
        size.add("XL")

        // Creating adapter for spinner
        val sizeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, size)
        // Drop down layout style - list view with radio button
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // attaching data adapter to spinner
        sizeSpinner.adapter = sizeAdapter


        // Spinner Drop down elements
        val brand: MutableList<String> = ArrayList()
        brand.add("Reserved")
        brand.add("H&M")
        brand.add("Nike")
        brand.add("Adidas")
        brand.add("Other")

        // Creating adapter for spinner
        val brandAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, brand)
        // Drop down layout style - list view with radio button
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // attaching data adapter to spinner
        brandSpinner.adapter = brandAdapter


        // Spinner Drop down elements
        val condition: MutableList<String> = ArrayList()
        condition.add("Nowe")
        condition.add("Dobry")
        condition.add("Idealny")
        condition.add("Mocno używane")

        // Creating adapter for spinner
        val conditionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, condition)
        // Drop down layout style - list view with radio button
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // attaching data adapter to spinner
        conditionSpinner.adapter = conditionAdapter
        val newExpenseButton = findViewById<Button>(R.id.add_expense)
        newExpenseButton.setOnClickListener { addNewExpense() }
        val rotationButton = findViewById<Button>(R.id.rotationButton)
        rotationButton.setOnClickListener { rotationImage() }
    }

    private fun addNewExpense() {
        val nameEditText = findViewById<EditText>(R.id.expense_name)
        val descriptionEditText = findViewById<EditText>(R.id.product_description)
        val priceEditText = findViewById<EditText>(R.id.expense_price)
        val categorySpinner = findViewById<Spinner>(R.id.expense_category)
        val sizeSpinner = findViewById<Spinner>(R.id.productSize)
        val brandSpinner = findViewById<Spinner>(R.id.productBrand)
        val conditionSpinner = findViewById<Spinner>(R.id.productCondition)
        val title = nameEditText.text.toString()
        val price = priceEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val size = sizeSpinner.selectedItem.toString()
        val brand = brandSpinner.selectedItem.toString()
        val condition = conditionSpinner.selectedItem.toString()
        val category = categorySpinner.selectedItem as ProductCategory
        product = Product(title, description, category, price, fileUri.toString(), size, brand, condition)
        ProductsDataBase.getDataBase(this)?.productDao?.insert(product)
        var id = PackageInstaller.SessionInfo.INVALID_ID.toLong()
        if (intent.extras != null) {
            id = intent.extras!!.getLong(NfcAdapter.EXTRA_ID, PackageInstaller.SessionInfo.INVALID_ID.toLong())
        }
        product!!.id = id
        Toast.makeText(applicationContext, R.string.object_ok, Toast.LENGTH_SHORT).show()
        finish()
    }

    fun makePhotoButtonPressed(view: View?) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        assert(data != null)
        fileUri = data!!.data
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data.data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var photoFile: File? = null
            photoFile = saveToInternalStorage(bitmap)
            fileUri = Uri.fromFile(photoFile)
            imageView = findViewById(R.id.photo)
            Glide.with(this).asBitmap().load(bitmap)
                    .into(imageView!!)
        }
    }

    private fun rotationImage() {
        if (bitmap != null) {
            val matrix = Matrix()
            matrix.postRotate(90f)
            bitmap = Bitmap.createBitmap(bitmap!!, 0, 0, bitmap!!.width, bitmap!!.height, matrix, true)
            Glide.with(this).asBitmap().load(bitmap)
                    .centerCrop()
                    .into(imageView!!)
            fileUri = Uri.fromFile(saveToInternalStorage(bitmap))
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap?): File {
        val cw = ContextWrapper(applicationContext)
        val randomName = UUID.randomUUID().toString()

        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir(randomName, Context.MODE_PRIVATE)
        // Create imageDir
        val mypath = File(directory, "profile.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            val matrix = Matrix()
            matrix.postRotate(90f)
            bitmapImage!!.compress(Bitmap.CompressFormat.JPEG, 90, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                assert(fos != null)
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return mypath
    }

    companion object {
        const val PICK_IMAGE = 1
    }
}