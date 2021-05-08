package com.example.bloodbank;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrgamentForB_Seven extends Fragment {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ModelofDetails modelofDetails;
    ArrayList list = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterRecycle adapterRecycle;

    public FrgamentForB_Seven() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_frgament_for_b__seven, container, false);


        recyclerView=v.findViewById(R.id.resc3);

        ref.child("AllInfo").child("B-").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    modelofDetails = dataSnapshot1.getValue(ModelofDetails.class);
                    list.add(modelofDetails);
                }
                res();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }
    private void  res()
    {
        adapterRecycle = new AdapterRecycle(getContext(), list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterRecycle);
    }

}
