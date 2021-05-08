package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopaplication.Models.Pralvant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
private EditText edit_name,edit_number,edit_address,edit_city;
private Button btn_confirm;
DatabaseReference databaseReference;
String name,number,address,city;
String totalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        edit_name=findViewById(R.id.edit_text_name_confirm);
        edit_number=findViewById(R.id.edit_text_number_confirm);
        edit_address=findViewById(R.id.edit_text_address_confirm);
        edit_city=findViewById(R.id.edit_text_city_confirm);
        btn_confirm=findViewById(R.id.btn_Confirm);

        Intent intent=getIntent();
        totalAmount=intent.getStringExtra("TotalAmount");
        databaseReference= FirebaseDatabase.getInstance().getReference();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });




    }

    private void Check() {
        name=edit_name.getText().toString();
        number=edit_number.getText().toString();
        address=edit_address.getText().toString();
        city=edit_city.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(number)){
            Toast.makeText(this, "Please Enter Number", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Please Enter Address", Toast.LENGTH_SHORT).show();
    }else if(TextUtils.isEmpty(city)){
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();

        }else{
            UploadOrder();
        }
    }

    private void UploadOrder() {
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM ddd,yyy");
        String save_data=simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("HHH:mmm:ss a");
        String save_time=simpleDateFormat.format(calendar.getTime());

        String key_random=save_data+save_time;

        databaseReference.child("Orders").child(Pralvant.user_Current.getNumper());

        HashMap<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("number",number);
        map.put("address",address);
        map.put("city",city);
        map.put("pid",key_random);
        map.put("save_data",save_data);
        map.put("save_time",save_time);
        map.put("state","no shipped");
        map.put("totalAmount",totalAmount);
        databaseReference.child("Orders").child(Pralvant.user_Current.getNumper()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View").child(Pralvant.user_Current.getNumper())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ConfirmFinalOrderActivity.this, "Confirm Success", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(ConfirmFinalOrderActivity.this,Home.class);
                                    startActivity(intent);
                                }
                        }
                    });
                }
                }
        });




    }
}