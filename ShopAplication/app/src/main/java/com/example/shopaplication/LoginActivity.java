package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    private EditText edit_pass, edit_number;
    TextView text_admin,textNotAdmin,text_forget_pass;
    private Button btn_login;
    private ProgressDialog progressDialog;
    private CheckBox checkBox;
    private String database="users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_number = findViewById(R.id.edit_textnumper_login);
        edit_pass = findViewById(R.id.edit_textpass_login);
        text_admin=findViewById(R.id.admin);
        textNotAdmin=findViewById(R.id.not_admin);
        btn_login = findViewById(R.id.btn_login);
        checkBox=findViewById(R.id.check_login);
        text_forget_pass=findViewById(R.id.text_forget_login);
        progressDialog = new ProgressDialog(this);

        Paper.init(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();

            }
        });
        text_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(database=="Admins"){
                    database="users";
                    textNotAdmin.setVisibility(View.INVISIBLE);
                    text_admin.setVisibility(View.VISIBLE);
                }
                database="Admins";
                textNotAdmin.setVisibility(View.VISIBLE);
                text_admin.setVisibility(View.INVISIBLE);
            }
        });
        text_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ResetPasswardActivity.class);
                intent.putExtra("reset","login");
                startActivity(intent);
            }
        });

    }

    private void Login() {

        String number = edit_number.getText().toString();
        String pass = edit_pass.getText().toString();

        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please Enter passwarad", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Login ");
            progressDialog.setMessage("please Wait........");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            LoginDatabase(number,pass);

        }

    }

    private void LoginDatabase(String number, String pass) {

        final DatabaseReference ref;
        ref= FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(database).child(number).exists()) {
                     String pass_login= snapshot.child(database).child(number).child("pass").getValue().toString();
                    Toast.makeText(LoginActivity.this, "" + database, Toast.LENGTH_SHORT).show();
                    if (pass_login.equals(pass)) {

                        Toast.makeText(LoginActivity.this, "" + database, Toast.LENGTH_SHORT).show();
                        if (database.equals("Admins")) {
                            Intent intent = new Intent(LoginActivity.this, AdminHomeActivity2.class);
                            startActivity(intent);
                            progressDialog.dismiss();


                        } else {

                            if (checkBox.isChecked()) {
                                Paper.book().write(Pralvant.UserPhoneKey, number);

                                Paper.book().write(Pralvant.UserPassKey, pass);

                            }
                            Intent intent = new Intent(LoginActivity.this, Home.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "" + number, Toast.LENGTH_SHORT).show();

                            Toast.makeText(LoginActivity.this, "Login Sucees", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Model_Login login = snapshot.child(database).child(number).getValue(Model_Login.class);
                            Pralvant.user_Current = login;
                        }


                    } else {

                        Toast.makeText(LoginActivity.this, "This account dosent Exits ", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}