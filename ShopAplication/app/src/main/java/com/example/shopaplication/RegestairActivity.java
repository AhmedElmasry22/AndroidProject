package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegestairActivity extends AppCompatActivity {
  private EditText edit_name,edit_pass,edit_number;
  private Button btn_register;
  private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestair);
        edit_name=findViewById(R.id.edit_textname_register);
        edit_number=findViewById(R.id.edit_textnumper_register);
        edit_pass=findViewById(R.id.edit_textpass_register);
        btn_register=findViewById(R.id.btn_register);
        progressDialog=new ProgressDialog(this);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatAccount();

            }
        });

    }
    private void CreatAccount(){
        String name=edit_name.getText().toString();
        String number=edit_number.getText().toString();
        String pass=edit_pass.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(number)){
            Toast.makeText(this, "Please Enter Number", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Please Enter passwarad", Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("please Wait........");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            CreatAccountFirebase(name,number,pass);
        }

    }

    private void CreatAccountFirebase(String name, String number, String pass) {
        final DatabaseReference ref;
        ref= FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.child("users").child(number).exists())) {

                    Toast.makeText(RegestairActivity.this, "Number is exist", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegestairActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("numper", number);
                    map.put("pass", pass);
                    ref.child("users").child(number).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            Toast.makeText(RegestairActivity.this, "Create Account Sucess", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegestairActivity.this, MainActivity.class);
                            startActivity(intent);

                        }
                    });
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegestairActivity.this, "Netorwk error....", Toast.LENGTH_SHORT).show();

            }
        });


    }
}