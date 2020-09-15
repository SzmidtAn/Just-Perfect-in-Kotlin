package com.example.girlsshopping.products;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.girlsshopping.MainActivity;
import com.example.girlsshopping.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.pm.PackageInstaller.SessionInfo.INVALID_ID;
import static android.nfc.NfcAdapter.EXTRA_ID;
import static android.provider.MediaStore.Images.Media.getBitmap;
import static com.facebook.FacebookSdk.getApplicationContext;

public class AddProductActivity extends AppCompatActivity {

    Bitmap bitmap;
    Product product;
    private Uri fileUri;
    public static final int PICK_IMAGE = 1;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        Spinner categorySpinner = findViewById(R.id.expense_category);
        Spinner sizeSpinner = findViewById(R.id.productSize);
        Spinner brandSpinner = findViewById(R.id.productBrand);
        Spinner conditionSpinner = findViewById(R.id.productCondition);


        ProductCategory[] categories = ProductCategory.values();
        ArrayAdapter<ProductCategory> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, categories);
        categorySpinner.setAdapter(adapter);

        // Spinner Drop down elements
        List<String> size = new ArrayList<String>();
        size.add("XS");
        size.add("S");
        size.add("M ");
        size.add("L");
        size.add("XL");

        // Creating adapter for spinner
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, size);
        // Drop down layout style - list view with radio button
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sizeSpinner.setAdapter(sizeAdapter);


        // Spinner Drop down elements
        List<String> brand = new ArrayList<String>();
        brand.add("Reserved");
        brand.add("H&M");
        brand.add("Nike");
        brand.add("Adidas");
        brand.add("Other");

        // Creating adapter for spinner
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brand);
        // Drop down layout style - list view with radio button
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        brandSpinner.setAdapter(brandAdapter);




     // Spinner Drop down elements
        List<String> condition = new ArrayList<String>();
        condition.add("Nowe");
        condition.add("Dobry");
        condition.add("Idealny");
        condition.add("Mocno używane");

        // Creating adapter for spinner
        ArrayAdapter<String> conditionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, condition);
        // Drop down layout style - list view with radio button
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        conditionSpinner.setAdapter(conditionAdapter);




        Button newExpenseButton = findViewById(R.id.add_expense);

        newExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewExpense();
            }
        });

        Button rotationButton = findViewById(R.id.rotationButton);

        rotationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotationImage();
            }
        });

    }


    private void addNewExpense() {
        EditText nameEditText = findViewById(R.id.expense_name);
        EditText descriptionEditText = findViewById(R.id.product_description);
        EditText priceEditText = findViewById(R.id.expense_price);
        Spinner categorySpinner = findViewById(R.id.expense_category);
        Spinner sizeSpinner = findViewById(R.id.productSize);
        Spinner brandSpinner = findViewById(R.id.productBrand);
        Spinner conditionSpinner = findViewById(R.id.productCondition);


        String title = nameEditText.getText().toString();
        String price = priceEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String size = sizeSpinner.getSelectedItem().toString();
        String brand = brandSpinner.getSelectedItem().toString();
        String condition = conditionSpinner.getSelectedItem().toString();
        ProductCategory category = (ProductCategory) categorySpinner.getSelectedItem();

        product = new Product( title,  description,  category, price, fileUri.toString(), size, brand, condition);
        ProductsDataBase.getDataBase(this).getProductDao().insert(product);

        long id = INVALID_ID;
        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getLong(EXTRA_ID, INVALID_ID);
        }
        product.setId(id);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(), R.string.object_ok, Toast.LENGTH_SHORT).show();

        finish();
    }

    public void makePhotoButtonPressed(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_IMAGE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;

        try {
        fileUri=data.getData();

        }catch (NullPointerException e){
            e.printStackTrace();
        };


        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            try {
                bitmap= getBitmap(getContentResolver(), fileUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            File photoFile = null;
            photoFile= saveToInternalStorage(bitmap);
            fileUri=Uri.fromFile(photoFile);
            imageView=findViewById(R.id.photo);

            Glide.with(this).asBitmap().load(bitmap)
                    .into(imageView);
        }
    }

    private void rotationImage() {

        if (bitmap!= null) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Glide.with(this).asBitmap().load(bitmap)
                    .centerCrop()
                    .into(imageView);

           fileUri= Uri.fromFile( saveToInternalStorage(bitmap));
        }
    }

    private File saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        String randomName = UUID.randomUUID().toString();

        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(randomName, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            Matrix matrix=new Matrix();
            matrix.postRotate(90);

            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 90, fos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath;
    }

}