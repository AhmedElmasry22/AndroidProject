package com.example.markshandler3.DoctorPackage.FragmentPackageDoctor;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.markshandler3.DoctorPackage.AdapterPackageDoctor.AdapterAL;
import com.example.markshandler3.DoctorPackage.ListPackageDoctor.ListViewForAllStudentsWhoOpenedTheAssignment;
import com.example.markshandler3.DoctorPackage.ListPackageDoctor.SubjectsListForDoctor;
import com.example.markshandler3.DoctorPackage.ModelsPackageDoctor.ModelLA;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.example.markshandler3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentsFragmentForDoctorSubject extends Fragment {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Subjects"), ref_ass;
    public static DatabaseReference reference_ass;
    ArrayList<ModelLA> ass_name;
    ModelLA modelLA;
    ListView customListViewJAVA;


    public AssignmentsFragmentForDoctorSubject() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_assignments_fragment_for_doctor_subject, container, false);
        customListViewJAVA = view.findViewById(R.id.listView1);
        String sub_name = SubjectsListForDoctor.subject_name;
        ref_ass = ref.child(sub_name).child("assignments");
        ass_name = new ArrayList<>();



        ref_ass.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    modelLA = new ModelLA();
                    String name = d.getKey();
                    modelLA.setName(name);
                    String state = d.child("assignment_access").getValue().toString();
                    modelLA.setState(state);
                    ass_name.add(modelLA);
                    reference_ass = ref_ass.child(name);
                }
                int xmlFile = R.layout.row_assignment_doctor;
                AdapterAL adapter = new AdapterAL(getContext(), xmlFile, ass_name);
                customListViewJAVA.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // when you click on item in list view
        customListViewJAVA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent newActivity = new Intent(getContext(), ListViewForAllStudentsWhoOpenedTheAssignment.class);
                startActivity(newActivity);
            }
        });
        //**************************************************************
        return  view;

    }

}
