package com.example.newsapplication.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.newsapplication.Helpers.mysharedprefernce;
import com.example.newsapplication.Models.ModelofLogin;
import com.example.newsapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    EditText mail,pass;
    Button loging,creat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mail=findViewById(R.id.loginemail);
        pass=findViewById(R.id.loginpassword);
        loging=findViewById(R.id.login);
        creat=findViewById(R.id.creatnewaccount);

        mysharedprefernce.init(this);

        loging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCreatFreeAccount = new Intent(Login.this, Registeration.class);
                startActivity(toCreatFreeAccount);
            }
        });
    }
    public void getData(){

        JSONObject object = new JSONObject();
        try {
            object.put("email",mail.getText().toString().trim() );
            object.put("password", pass.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post("https://cizaro.net/2030/api/login")
                .addJSONObjectBody(object)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        ModelofLogin array = gson.fromJson(response.toString(), ModelofLogin.class);
                        String userinformation =gson.toJson(array.getUser_info());
                        mysharedprefernce.setUserOBJ(userinformation);


                        Intent toMainActivity = new Intent(Login.this, MainActivity.class);
                        startActivity(toMainActivity);
                        Toast.makeText(Login.this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Login.this, "البيانات التى ادخلتها غير صحيحة", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}
