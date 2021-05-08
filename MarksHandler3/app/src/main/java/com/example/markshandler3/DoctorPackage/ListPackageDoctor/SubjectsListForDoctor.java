package com.example.markshandler3.DoctorPackage.ListPackageDoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.markshandler3.DoctorPackage.AdapterPackageDoctor.AdapterSubjectsForDoctors;
import com.example.markshandler3.DoctorPackage.AddSubject;
import com.example.markshandler3.DoctorPackage.EverySubjectInDetails;
import com.example.markshandler3.MainActivity;
import com.example.markshandler3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubjectsListForDoctor extends AppCompatActivity {

    public static String subject_name;
    public static String id;
    Button add_subject;
    ArrayList< String > Subjects;
    DatabaseReference ref ;
    ListView customListViewJAVA;
    String key_id = MainActivity.key_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_list_for_doctor);


        customListViewJAVA =  findViewById(R.id.listView1);
        add_subject = findViewById(R.id.add_subject);
        Subjects = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Subjects");


        getData();




        //**************************************************************
        // when you click on item in list view
        customListViewJAVA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SubjectsListForDoctor.this, "TroTroTro", Toast.LENGTH_SHORT).show();
                String item = parent.getItemAtPosition(position).toString();
                subject_name = item;
                Intent newActivity = new Intent(SubjectsListForDoctor.this, EverySubjectInDetails.class);
                startActivity(newActivity);
            }
        });
        //**************************************************************

        add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(SubjectsListForDoctor.this, AddSubject.class);
                startActivity(newActivity);
            }
        });
        //**************************************************************

    }

    void getData()
    {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()) {

                    String name = d.child("id_doctor").getValue().toString();
                    String sub_name = d.getKey();
                    if(name.equals(key_id)) {
                        Subjects.add(sub_name);
                    }
                }

                int xmlFile = R.layout.row_subjects_doctors;

                AdapterSubjectsForDoctors adapter = new AdapterSubjectsForDoctors(SubjectsListForDoctor.this, xmlFile,Subjects);

                customListViewJAVA.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }}
