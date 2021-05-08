package com.example.newsapplication.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.newsapplication.Adapters.AdapterOfViewPager;
import com.example.newsapplication.Adapters.AdapterofDetails;
import com.example.newsapplication.Models.ModelofDetails;
import com.example.newsapplication.Models.ModelofRegister;
import com.example.newsapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.newsapplication.Adapters.AdapterOfViewPager.id;

public class Details extends AppCompatActivity {
    List<ModelofDetails> list = new ArrayList<>();
    ImageView imageView;
    TextView writer,title,desc,desclong;
    RecyclerView recyclerView;
    AdapterofDetails adapterofDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView=findViewById(R.id.imagedetails);
        writer=findViewById(R.id.writerdetails);
        title=findViewById(R.id.titledetails);
        recyclerView=findViewById(R.id.recyledetails);


        getData();

    }

    public void getData(){

        JSONObject object = new JSONObject();
        try {
            object.put("id", AdapterOfViewPager.id);
            object.put("user_id", Registeration.user_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post("https://cizaro.net/2030/api/singel_post")
                .addJSONObjectBody(object)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        ModelofDetails array = gson.fromJson(response.toString(), ModelofDetails.class);

                        list.add(array);

                        Toast.makeText(Details.this, "تم", Toast.LENGTH_SHORT).show();

                        adapterofDetails = new AdapterofDetails(Details.this, list);
                        LinearLayoutManager manager = new LinearLayoutManager(Details.this);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapterofDetails);


                        //  desc.setText(array.getLong_description());
                        // title.setText(array.getPost_title());
                    }
                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(Details.this, "فشل ", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}
