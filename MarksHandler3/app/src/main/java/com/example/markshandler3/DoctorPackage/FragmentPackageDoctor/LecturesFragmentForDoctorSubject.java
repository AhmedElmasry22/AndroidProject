package com.example.markshandler3.DoctorPackage.FragmentPackageDoctor;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.markshandler3.DoctorPackage.AdapterPackageDoctor.AdapterLA;
import com.example.markshandler3.DoctorPackage.ListPackageDoctor.SubjectsListForDoctor;
import com.example.markshandler3.DoctorPackage.ModelsPackageDoctor.ModelLA;
import com.example.markshandler3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LecturesFragmentForDoctorSubject extends Fragment {


    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Subjects"),ref_lec;
    ArrayList<ModelLA> lec_name;
    ListView customListViewJAVA;
    Button finish;
    ModelLA modelLA;
    public static DatabaseReference reference_lec;

    public LecturesFragmentForDoctorSubject() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lectures_fragment_for_doctor_subject, container, false);
        customListViewJAVA = view.findViewById(R.id.listView1);
        String sub_name = SubjectsListForDoctor.subject_name;
        finish = view.findViewById(R.id.finish_lecture);
        lec_name = new ArrayList<>();
        ref_lec = ref.child(sub_name).child("lectures");
        ref_lec.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    modelLA = new ModelLA();
                    String name = d.getKey();
                    modelLA.setName(name);
                    String state = d.child("lecture_access").getValue().toString();
                    modelLA.setState(state);
                    lec_name.add(modelLA);
                    reference_lec = ref_lec.child(name);
                }
                int xmlFile = R.layout.row_lecture_doctor;
                AdapterLA adapter = new AdapterLA(getContext(), xmlFile, lec_name);
                customListViewJAVA.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // when you click on item in list view
        /*customListViewJAVA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finish = parent.findViewById(R.id.finish_lecture);
                finish.setBackgroundResource(R.drawable.ic_done_black_24dp);
                ref_lec.child("lec_timer").setValue("f");


            }
        });*/
        //**************************************************************

        return  view;



    }

}
