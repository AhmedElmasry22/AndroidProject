package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shopaplication.Adapters.AdapterHome;
import com.example.shopaplication.Models.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductsApproveActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<Products> list = new ArrayList<>();
    private AdapterHome mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    AlertDialog.Builder builder1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_approve);
        recyclerView = findViewById(R.id.recycle_product_approve);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Confirm Approve");
        builder1.setCancelable(true);
        Get_Item();


    }

    private void Get_Item() {
        databaseReference.orderByChild("producte_state").equalTo("Not Approved").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Products products = dataSnapshot.getValue(Products.class);
                    list.add(products);

                }
                mAdapter = new AdapterHome(ProductsApproveActivity.this, list);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListner(new AdapterHome.OnItemClickListner() {
                    @Override
                    public void OnItemCLick(int postion) {
                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        databaseReference.child(list.get(postion).getPid())
                                                .child("producte_state").setValue("Approved");
                                        list.remove(postion);
                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();


                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductsApproveActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}