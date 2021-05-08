package com.example.markshandler3.DoctorPackage.ListPackageDoctor;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.markshandler3.DoctorPackage.AdapterPackageDoctor.AdapterAL;
import com.example.markshandler3.DoctorPackage.AdapterPackageDoctor.AdapterForStudentAssignmentList;
import com.example.markshandler3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListViewForAllStudentsWhoOpenedTheAssignment extends AppCompatActivity {

    ListView list;
    TextView ass;
    String ass_name = AdapterAL.ass_name;
    String sub_name = SubjectsListForDoctor.subject_name;
    DatabaseReference reference;
    public static DatabaseReference ref_d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_for_all_students_who_opened_the_assignment);

        list = findViewById(R.id.students_list_view);
        ass = findViewById(R.id.ass_name_d);

        final ArrayList<String> names = new ArrayList<>();

        ass.setText(ass_name);
        reference = FirebaseDatabase.getInstance().getReference("Subjects");
        ref_d = reference.child(sub_name).child("assignments").child(ass_name).child("students");
        ref_d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren())
                {
                    names.add(d.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AdapterForStudentAssignmentList adapter = new AdapterForStudentAssignmentList(ListViewForAllStudentsWhoOpenedTheAssignment.this,0,names);

        list.setAdapter(adapter);

    }

}
