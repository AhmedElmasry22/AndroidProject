package com.example.newsapplication.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.newsapplication.Helpers.mysharedprefernce;
import com.example.newsapplication.Models.ModelofRegister;
import com.example.newsapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class Registeration extends AppCompatActivity {
    EditText name,pass,email;
    Button creat;
    public static int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);


        name=findViewById(R.id.registername);
        pass=findViewById(R.id.registerpassword);
        email=findViewById(R.id.registeremail);
        creat=findViewById(R.id.login);

        mysharedprefernce.init(this);

        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(name.getText().toString().trim(), email.getText().toString().trim(),
                        pass.getText().toString().trim() );

            }
        });

    }


    private void sendData(String name , String email , String password ){
        JSONObject object = new JSONObject();
        try {
            object.put("user_name", name);
            object.put("email", email);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://cizaro.net/2030/api/registration")
                .addJSONObjectBody(object)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        ModelofRegister array = gson.fromJson(response.toString(), ModelofRegister.class);

                        String userinformation =gson.toJson(array.getUser_info());

                        mysharedprefernce.setUserOBJ(userinformation);

                        ModelofRegister.UserInfoBean model = gson.fromJson(mysharedprefernce.getUserOBJ(),
                                ModelofRegister.UserInfoBean.class);

                      user_id= model.getUserId();


                        Intent tologin = new Intent(Registeration.this, Login.class);
                        startActivity(tologin);
                        Toast.makeText(Registeration.this, "تم ملئ البيانات", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Registeration.this, "يجب ملئ البيانات السابقة", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
