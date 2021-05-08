package com.example.newsapplication.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.newsapplication.Adapters.AdapterofDetails;
import com.example.newsapplication.Adapters.AdapterofSearch;
import com.example.newsapplication.Models.ModelofSearch;
import com.example.newsapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SerachForDetails extends AppCompatActivity {

    List<ModelofSearch.PostsBean> list = new ArrayList<ModelofSearch.PostsBean>();
    ImageView imageView;
    TextView title;
    RecyclerView recyclerView;
    AdapterofSearch adapterofSearch;
    EditText editText;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_for_details);
        imageView=findViewById(R.id.imagesearch);
        title=findViewById(R.id.textsearch);
        recyclerView=findViewById(R.id.recyclesearch);
        editText=findViewById(R.id.searcabouthdata);

         keyword=editText.getText().toString().trim();

         //******************************************* change in search
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2  ) {
                String x ;
                if(charSequence.length()==0){
                    getData("");
                }
                else {
                    x=editText.getText().toString();
                    getData(x);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        }) ;
        //*********************************************************

    }
    public void getData(String y){

        JSONObject object = new JSONObject();
        try {
            object.put("keyword",y);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://cizaro.net/2030/api/search_result")
                .addJSONObjectBody(object)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        ModelofSearch array = gson.fromJson(response.toString(), ModelofSearch.class);

                        list=array.getPosts();

                        adapterofSearch = new AdapterofSearch(SerachForDetails.this, list);
                        LinearLayoutManager manager = new LinearLayoutManager(SerachForDetails.this);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapterofSearch);
                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }


}
