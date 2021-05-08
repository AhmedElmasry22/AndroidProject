package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shopaplication.Adapters.AdapterOrderAdmin;
import com.example.shopaplication.Models.ModelOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminOrders extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    List<ModelOrder> list=new ArrayList<>();
    private AdapterOrderAdmin mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);
        recyclerView=findViewById(R.id.recycle_order_admin);
        mLayoutManager = new LinearLayoutManager(this);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    ModelOrder modelOrder=snapshot1.getValue(ModelOrder.class);
                    list.add(modelOrder);
                }
                mAdapter = new AdapterOrderAdmin(AdminOrders.this,list);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListner(new AdapterOrderAdmin.OnItemClickListner() {


                    @Override
                    public void OnButtonCLick(int postion) {

                        Intent intent=new Intent(AdminOrders.this, ItemOrderActivity.class);
                        intent.putExtra("number",list.get(postion).getNumber());
                        startActivity(intent);

                    }

                    @Override
                    public void deleteItem(int postion) {
                        FirebaseDatabase.getInstance().getReference().child("Orders").child(list.get(postion).getNumber()).removeValue();
                        Toast.makeText(AdminOrders.this, ""+list.get(postion).getNumber(), Toast.LENGTH_SHORT).show();
                        list.remove(postion);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminOrders.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });

    }
}