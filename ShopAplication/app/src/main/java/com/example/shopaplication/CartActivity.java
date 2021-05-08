package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopaplication.Adapters.AdapterCart;
import com.example.shopaplication.Adapters.AdapterHome;
import com.example.shopaplication.Models.ModelCart;
import com.example.shopaplication.Models.Pralvant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView_cart;
    private Button button_next;
    private TextView textView_total,textView_invisable;

    private AdapterCart mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    DatabaseReference databaseReference;

    List<ModelCart> list = new ArrayList<>();

    int totalprice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_actvity);
        textView_total = findViewById(R.id.text_total_price_cart);
        button_next = findViewById(R.id.btn_next_cart);
        textView_invisable=findViewById(R.id.text_invisable);

        recyclerView_cart = findViewById(R.id.recycle_cart);
        recyclerView_cart.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        CheckShipped();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Cart List").child("User View").child(Pralvant.user_Current.getNumper()).
                child("Products_buy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ModelCart modelCart = snapshot1.getValue(ModelCart.class);
                    list.add(modelCart);
                    totalprice += Integer.parseInt(modelCart.getPrice());


                }

                mAdapter = new AdapterCart(CartActivity.this, list);
                recyclerView_cart.setLayoutManager(mLayoutManager);
                recyclerView_cart.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                if (totalprice != 0) {
                    textView_total.setText("Total Price = " + totalprice);
                }


                mAdapter.setOnItemClickListner(new AdapterCart.OnItemClickListner() {
                    @Override
                    public void OnItemCLick(int postion) {

                    }

                    @Override
                    public void OnEditMenu(int postion) {
                        Intent intent = new Intent(CartActivity.this, HomeDetail.class);
                        intent.putExtra("id", list.get(postion).getId_product());
                        intent.putExtra("productId", list.get(postion).getPid());
                        startActivity(intent);
                    }

                    @Override
                    public void OnDeleteMenu(int postion) {
                        databaseReference.child("Cart List").child("User View").child(Pralvant.user_Current.getNumper()).
                                child("Products_buy").child(list.get(postion).getPid()).removeValue();
                        list.remove(postion);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                intent.putExtra("TotalAmount", String.valueOf(totalprice));
                startActivity(intent);
            }
        });
    }
    private void CheckShipped(){
        FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Pralvant.user_Current.getNumper()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String state = snapshot.child("state").getValue().toString();
                    String user_name = snapshot.child("name").getValue().toString();
                    if(state.equals("shipped")){
                        textView_total.setText("Dear "+user_name+"\n order is shipped Successfully");
                        recyclerView_cart.setVisibility(View.INVISIBLE);
                        button_next.setVisibility(View.INVISIBLE);
                        textView_invisable.setVisibility(View.VISIBLE);
                        textView_invisable.setText("Congratultion ,your Final Order has been shipped Successfully Soon you Will receive your Order at your door step");
                        Toast.makeText(CartActivity.this, "you can purchase more products, one you received first Product", Toast.LENGTH_SHORT).show();

                    }else if(state.equals("no shipped")){
                        textView_total.setText("Shipping state not Shipped");
                        recyclerView_cart.setVisibility(View.INVISIBLE);
                        button_next.setVisibility(View.INVISIBLE);
                        textView_invisable.setVisibility(View.VISIBLE);





                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
