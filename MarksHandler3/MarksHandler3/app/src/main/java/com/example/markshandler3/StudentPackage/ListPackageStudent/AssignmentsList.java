package com.example.markshandler3.StudentPackage.ListPackageStudent;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.markshandler3.DoctorPackage.ModelsPackageDoctor.ModelLA;
import com.example.markshandler3.R;
import com.example.markshandler3.StudentPackage.AdapterPackageStudent.AdapterAssignmentOfStudent;
import com.example.markshandler3.StudentPackage.AssignmentTest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssignmentsList extends AppCompatActivity {

    ArrayList<ModelLA> ass_student;
    ModelLA modelLA;
    String sub_name = ListOfSubjectsForStudents.SubjectName;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Subjects");
    ListView listAssignmentsName ;
    public static DatabaseReference reference_ass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments_list);

        listAssignmentsName =findViewById(R.id.list_assignments);
        ass_student = new ArrayList<>();
        final int xmlFile = R.layout.row_of_assignments;

        reference_ass = reference.child(sub_name).child("assignments");

        reference.child(sub_name).child("assignments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren())
                {
                    modelLA = new ModelLA();
                    modelLA.setName(d.getKey());
                    modelLA.setState(d.child("assignment_access").getValue().toString());
                    ass_student.add(modelLA);

                }
                AdapterAssignmentOfStudent adapterObject = new AdapterAssignmentOfStudent(AssignmentsList.this,xmlFile,ass_student);
                listAssignmentsName.setAdapter(adapterObject);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        listAssignmentsName.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent toAssignmentsTest = new Intent(AssignmentsList.this, AssignmentTest.class);
                startActivity(toAssignmentsTest);



            }
        });
    }
}
