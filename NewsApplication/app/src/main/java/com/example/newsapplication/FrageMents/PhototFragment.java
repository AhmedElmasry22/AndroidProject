package com.example.newsapplication.FrageMents;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.newsapplication.Adapters.AdapterPhotoFragment;
import com.example.newsapplication.Adapters.AdapterRecycler;
import com.example.newsapplication.Models.ModelOfHome;
import com.example.newsapplication.Models.ModelofPhotos;
import com.example.newsapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhototFragment extends Fragment {
    RecyclerView recyclerView;
    List<ModelofPhotos.AllPhotosBean> list=new ArrayList<>();
    AdapterPhotoFragment adpterphtot;
    public PhototFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_ptot, container, false);
        recyclerView=view.findViewById(R.id.recyclegalary);
        getData();
        return view;
    }
    public void getData()
    {
        AndroidNetworking.get("https://cizaro.net/2030/api/photos")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        ModelofPhotos array = gson.fromJson(response.toString(), ModelofPhotos.class);
                        list =array.getAll_photos();
                        adpterphtot = new AdapterPhotoFragment(getContext(), list);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adpterphtot);

                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), "Faild", Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
