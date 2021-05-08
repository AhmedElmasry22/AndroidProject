package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopaplication.Models.Pralvant;
import com.example.shopaplication.Models.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class HomeDetail extends AppCompatActivity {
private TextView text_name,text_des,tex_price;
private ImageView imageView_detail;
private Button btn_buy;
private String id;
private String productId;
String state="normal state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);



        text_name =findViewById(R.id.text_name_detail);
        text_des=findViewById(R.id.text_des_detail);
        tex_price=findViewById(R.id.text_price_detail);
        imageView_detail=findViewById(R.id.image_home_detail);

        btn_buy=findViewById(R.id.button_buy);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        productId=intent.getStringExtra("productId");


        GetData();
        CheckShipped();


        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();


       btn_buy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if((state.equals("Order Shipped")||state.equals("Order placed"))){
                   Toast.makeText(HomeDetail.this, "you can add purchase more products once your order is shipped or confirm", Toast.LENGTH_SHORT).show();


               }else {
                   addBuy();
               }

           }
       });


    }
    private void GetData(){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Products").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Products products=snapshot.getValue(Products.class);

                Picasso.get().load(products.getImage()).into(imageView_detail);
                tex_price.setText(products.getPrice());
                text_des.setText(products.getDescreption());
                text_name.setText(products.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void addBuy(){
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM ddd,yyy");
        String save_data=simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("HHH:mmm:ss a");
        String save_time=simpleTimeFormat.format(calendar.getTime());
        if(productId==null) {
            productId = save_data + save_time;
        }
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object> map=new HashMap<>();
        map.put("id_product",id);
        map.put("pid",productId);
        map.put("pname",text_name.getText().toString());
        map.put("price",tex_price.getText().toString());
        map.put("data",save_data);
        map.put("time",save_time);
        map.put("discount","");
        databaseReference.child("User View").child(Pralvant.user_Current.getNumper()).child("Products_buy").child(productId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    databaseReference.child("Admin View").child(Pralvant.user_Current.getNumper()).child("Products_buy").child(productId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(HomeDetail.this, "Cart  is Added", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(HomeDetail.this,Home.class);
                            startActivity(intent);
                        }
                    });

                }
            }
        });

    }
    private void CheckShipped(){
        FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Pralvant.user_Current.getNumper()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    state = snapshot.child("state").getValue().toString();
                    String user_name = snapshot.child("name").getValue().toString();
                    if(state.equals("shipped")){
                        state="Order Shipped";

                    }else if(state.equals("no shipped")){
                        state="Order placed";






                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}