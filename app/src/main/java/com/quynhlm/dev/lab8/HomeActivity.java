package com.quynhlm.dev.lab8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.quynhlm.dev.lab8.Adapter.AdapterProduct;
import com.quynhlm.dev.lab8.Model.Product;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rcv;

    ArrayList<Product> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rcv = findViewById(R.id.rcv_Product);
        list.add(new Product("1", "fruit", "Apple", "Fresh and juicy apple", 4.5, 2.99, R.drawable.apple));
        list.add(new Product("2", "fruit", "Banana", "Ripe banana", 4.2, 1.99, R.drawable.apple));
        list.add(new Product("3", "fruit", "Orange", "Sweet and tangy orange", 4.0, 3.49, R.drawable.apple));
        list.add(new Product("4", "fruit", "Grape", "Delicious grapes", 4.3, 4.99, R.drawable.apple));
        AdapterProduct adapterProduct = new AdapterProduct(list,HomeActivity.this);
        rcv.setLayoutManager(new GridLayoutManager(this, 2));
        rcv.setAdapter(adapterProduct);
    }
}