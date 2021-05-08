package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shopaplication.Models.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminMaintainActivity extends AppCompatActivity {
   private Button btn_apply_change,btn_delete_item;
   private EditText name,price,descrepation;
   private ImageView imageView;
   DatabaseReference databaseReference;
   Products products;
   String id;
   String name2,price2,descrepation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain);
        btn_apply_change=findViewById(R.id.aplay_change);
        btn_delete_item=findViewById(R.id.delete_item_admin_maintain);
        name=findViewById(R.id.edit_name_admin_maintain);
        price=findViewById(R.id.edit_price_admin_maintain);
        descrepation=findViewById(R.id.edit_descrepation_admin_maintain);
        imageView=findViewById(R.id.image_admin_Maintain);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        Get_Product();
        btn_apply_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditText();
            }
        });
        btn_delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteItem();
            }
        });

    }
    public void Get_Product(){
        databaseReference.child("Products").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products=snapshot.getValue(Products.class);
                name.setText(products.getName());
                price.setText(products.getPrice());
                descrepation.setText(products.getDescreption());
                Picasso.get().load(products.getImage()).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void CheckEditText(){
        name2=name.getText().toString();
        descrepation2=descrepation.getText().toString();
        price2=price.getText().toString();

        if(TextUtils.isEmpty(name2)){
            Toast.makeText(this, "Please Enter Name Product", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(descrepation2)){
            Toast.makeText(this, "Please Enter Name Product ", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(price2)){
            Toast.makeText(this, "Please Enter Price", Toast.LENGTH_SHORT).show();
        }else{
            ApplyChange();


        }

    }

    private void ApplyChange() {
        products.setDescreption(descrepation2);
        products.setPrice(price2);
        products.setProduct_name(name2);
        databaseReference.child("Products").child(id).setValue(products).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminMaintainActivity.this, "Changing Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminMaintainActivity.this, SellerCatgoryActivity.class);
                startActivity(intent);

            }
        });

    }
    private void DeleteItem(){
        databaseReference.child("Products").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminMaintainActivity.this, "Success Delete", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AdminMaintainActivity.this, SellerCatgoryActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}