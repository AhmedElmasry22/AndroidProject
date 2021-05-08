package com.example.bloodbank;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText mail , pass ;
    Button log , Rg;
    ProgressDialog progressDialog;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.loginEmail);
        pass = findViewById(R.id.loginPassword);
        log = findViewById(R.id.buttonlogin);
        Rg=findViewById(R.id.buttonRegist);
        progressDialog=new ProgressDialog(this);

        Rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Registeration.class);
                startActivity(intent);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String email = mail.getText().toString();
            String password = pass.getText().toString();

            SingIn(email,password);
            }
        });
    }

    private void SingIn(String email , String password )
    {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please Enter Email .. ", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter password .. ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.setTitle("Login Sucsses");
            progressDialog.setMessage("pleas Wait until make sure from data");
            progressDialog.show();

            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(Login.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Login.this, "Faild", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }

}
