package com.example.newsapplication.FrageMents;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.newsapplication.Activites.Details;
import com.example.newsapplication.Activites.MainActivity;
import com.example.newsapplication.Adapters.AdapterOfViewPager;
import com.example.newsapplication.Adapters.AdapterRecycler;
import com.example.newsapplication.Models.ModelOfHome;
import com.example.newsapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    List<ModelOfHome.NewsBean> list=new ArrayList<>();
    AdapterRecycler adapterRecycler;




    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recylehome);

        /*
        AdapterOfViewPager adapterOfViewPager=new AdapterOfViewPager();
        adapterOfViewPager.setOnClickListener(new AdapterOfViewPager.OnnClick() {
            @Override
            public void click(int pos) {

                id=list.get(pos).getCategory_id();

                Toast.makeText(getContext(), id+"", Toast.LENGTH_SHORT).show();
            }
        });
        */

        getData();
        return view;
    }
    public void getData()
    {
        AndroidNetworking.get("https://cizaro.net/2030/api/allnews")

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        ModelOfHome array = gson.fromJson(response.toString(), ModelOfHome.class);
                        list =array.getNews();
                        res();
                    }
                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }
    private void  res()
    {
        adapterRecycler = new AdapterRecycler(getContext(), list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterRecycler);
    }

}
