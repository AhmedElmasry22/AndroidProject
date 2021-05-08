package com.example.powerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.powerapp.Models.PostModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    PostViewModel postViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        postViewModel.getposts();

        RecyclerView recyclerView = findViewById(R.id.recycler);

        MyAdapter myAdapter = new MyAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        postViewModel.postmutablelivedata.observe(this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {

                myAdapter.setList(postModels);

            }
        });








    }
}
