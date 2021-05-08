package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Registeration extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance() ;
    EditText name, mail , pass , number , city , country ;
    Button Register;
    ProgressDialog progressDialog;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Spinner spinner ;
    String uid;
    String typeboold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        spinner =findViewById(R.id.spinner1);
        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        final String[] item ={"Check your type of boold","AB+", "AB-", "A+","A-","B+","B-","O+","O-"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        spinner=findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeboold=item[i].toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        name=findViewById(R.id.registerName);
        mail=findViewById(R.id.registerEmail);
        pass=findViewById(R.id.registerPassword);
        number=findViewById(R.id.registerNumberPhone);
        city=findViewById(R.id.registerCity);
        country=findViewById(R.id.registerCountry);
        progressDialog=new ProgressDialog(this);
        Register=findViewById(R.id.registerbutton);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ModelofDetails modelofDetails = new ModelofDetails();
                String email = mail.getText().toString();
                String password = pass.getText().toString();
                Regist(email,password);
                modelofDetails.setEmail(email);
                modelofDetails.setPassword(password);

            }
        });

    }
    private void  Regist(String email, String password)
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
            progressDialog.setTitle(" Registeration");
            progressDialog.setMessage("pleas Wait until make sure from data");
            progressDialog.show();
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Registeration.this, "Register Success", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                ModelofDetails modelofDetails = new ModelofDetails();

                                String Name = name.getText().toString();
                                String thePhone = number.getText().toString();
                                String theCity = city.getText().toString();
                                String theCountry = country.getText().toString();
                                String email = mail.getText().toString();

                                modelofDetails.setEmail(email);
                                modelofDetails.setTypeofboold(typeboold);
                                modelofDetails.setName(Name);
                                modelofDetails.setCity(theCity);
                                modelofDetails.setNumberphone(thePhone);
                                modelofDetails.setCountry(theCountry);

                                uid = task.getResult().getUser().getUid();
                                Toast.makeText(Registeration.this, typeboold+"", Toast.LENGTH_SHORT).show();
                                // ref.child("AllInfo").child(typeboold).child(uid).push().setValue(modelofDetails);

                                ref.child("AllInfo").child(typeboold).push().setValue(modelofDetails);
                                //
                                ref.child("AllInfo").child(uid).push().setValue(modelofDetails);
                                //
                                ref.child("AllInfo").child("All").push().setValue(modelofDetails);

                                Intent intent = new Intent(Registeration.this,Login.class);
                                startActivity(intent);

                            }
                            else
                            {
                                Toast.makeText(Registeration.this, "Faild", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }




}
