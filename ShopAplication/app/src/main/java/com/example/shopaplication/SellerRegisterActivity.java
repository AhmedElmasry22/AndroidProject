package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegisterActivity extends AppCompatActivity {
private EditText edit_number,edit_pass,edit_email,edit_address,edit_name;
private Button btn_login,btn_register;
FirebaseAuth auth;
DatabaseReference databaseReference;
private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_regestier);
        edit_number=findViewById(R.id.edit_phone_registair_seller);
        edit_pass=findViewById(R.id.edit_password_registair_seller);
        edit_email=findViewById(R.id.edit_email_registair_seller);
        edit_address=findViewById(R.id.edit_address_registair_seller);
        edit_name=findViewById(R.id.edit_name_seller_registair);
        btn_register=findViewById(R.id.btn_register_seller);
        btn_login=findViewById(R.id.btn_login_seller_register);
        progressDialog = new ProgressDialog(this);


        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEmail();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SellerRegisterActivity.this,LoginSellerActivity.class);
                startActivity(intent);
            }
        });


    }

    private void CreateEmail() {
        String name,password,email,address,number;
        name=edit_name.getText().toString();
        password=edit_pass.getText().toString();
        email=edit_email.getText().toString();
        address=edit_address.getText().toString();
        number=edit_number.getText().toString();
        if (name.equals("")&&password.equals("")&&email.equals("")&&address.equals("")&&number.equals("")){
            Toast.makeText(this, "Please Complete Form", Toast.LENGTH_SHORT).show();
        }else{
            progressDialog.setTitle("Register ");
            progressDialog.setMessage("please Wait........");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String uid = auth.getCurrentUser().getUid().toString();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", name);
                        map.put("password", password);
                        map.put("email", email);
                        map.put("address", address);
                        map.put("number", number);
                        map.put("uid", uid);
                        databaseReference.child("Seller").child(uid).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(SellerRegisterActivity.this, SellerActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();

                            }
                        });
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(SellerRegisterActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}