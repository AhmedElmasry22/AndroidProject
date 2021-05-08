package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.shopaplication.Adapters.AdapterHome;
import com.example.shopaplication.Adapters.AdapterItemOrder;
import com.example.shopaplication.Models.ModelCart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ModelCart> list=new ArrayList<>();
    private AdapterItemOrder mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference databaseReference;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_ordrer);
        recyclerView=findViewById(R.id.recycle_item_order);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        Intent intent=getIntent();
        id=intent.getStringExtra("number");
        databaseReference= FirebaseDatabase.getInstance().getReference();


        databaseReference.child("Cart List").child("Admin View")
                .child(id).child("Products_buy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    ModelCart modelCart=snapshot1.getValue(ModelCart.class);
                    list.add(modelCart);
                }
                mAdapter = new AdapterItemOrder(ItemOrderActivity.this,list);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}