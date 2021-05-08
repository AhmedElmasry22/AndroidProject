package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginSellerActivity extends AppCompatActivity {
 private Button btn_login;
 private EditText edit_email,edit_pass;
 private String email,password;
 FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_seller);
        btn_login=findViewById(R.id.btn_login_seller);
        edit_email=findViewById(R.id.edit_email_seller_login);
        edit_pass=findViewById(R.id.edit_pass_login_seller);

        auth=FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEmail_Password();
            }
        });
    }

    private void CheckEmail_Password() {
        password=edit_pass.getText().toString();
        email=edit_email.getText().toString();
        if(email.equals("")){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }else if(password.equals("")){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }else {
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent=new Intent(LoginSellerActivity.this,SellerActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }


    }
}