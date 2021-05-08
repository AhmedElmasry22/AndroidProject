package com.example.markshandler3.StudentPackage;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.markshandler3.DoctorPackage.ModelsPackageDoctor.ModelLA;
import com.example.markshandler3.R;
import com.example.markshandler3.StudentPackage.AdapterPackageStudent.AdapterAA;
import com.example.markshandler3.StudentPackage.ListPackageStudent.ListOfSubjectsForStudents;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class lecture_student extends AppCompatActivity {
    ArrayList<ModelLA> lec_student;
    ModelLA modelLA;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Subjects");
    ListView list;
    public static DatabaseReference reference_lec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_student);
        list = findViewById(R.id.lecture_student);

        final int xmlFile = R.layout.row_lecture_student;
        String sub_name = ListOfSubjectsForStudents.SubjectName;
        lec_student = new ArrayList<>();
        reference_lec = reference.child(sub_name).child("lectures");

        reference.child(sub_name).child("lectures").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren())
                {
                    modelLA = new ModelLA();
                    modelLA.setName(d.getKey());
                    modelLA.setState(d.child("lecture_access").getValue().toString());
                    lec_student.add(modelLA);

                }
                AdapterAA adapter = new AdapterAA(lecture_student.this,xmlFile,lec_student);
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(lecture_student.this,Attandance.class);
                startActivity(intent);
            }
        });*/
    }
}
