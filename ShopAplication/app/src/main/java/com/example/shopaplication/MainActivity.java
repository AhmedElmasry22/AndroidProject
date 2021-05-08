package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopaplication.Models.Model_Login;
import com.example.shopaplication.Models.Pralvant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
Button button_login,button_join;
TextView become_seller;

private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_login=findViewById(R.id.main_login_btn);
        button_join=findViewById(R.id.main_register_btn);
        become_seller=findViewById(R.id.becoming_seller_login);

        Paper.init(this);
        progressDialog = new ProgressDialog(this);

        String number=Paper.book().read(Pralvant.UserPhoneKey);

        String pass=Paper.book().read(Pralvant.UserPassKey);


        if(pass!=null && number!=null){

            Toast.makeText(this, ""+number, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+pass, Toast.LENGTH_SHORT).show();
            LoginDatabase("01022008737",pass);

        }

        button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegestairActivity.class);
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        become_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SellerRegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void LoginDatabase(String number2, String pass) {

        final DatabaseReference ref;
        ref= FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("users").child(number2).exists()){
                    Model_Login login=snapshot.child("users").child(number2).getValue(Model_Login.class);
                    if(login.getPass().equals(pass)){
                            Intent intent=new Intent(MainActivity.this,Home.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Login Sucees", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Pralvant.user_Current=login;
                        Toast.makeText(MainActivity.this, ""+login.getName(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });
    }
}