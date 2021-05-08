package com.example.markshandler3.DoctorPackage;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.markshandler3.DoctorPackage.AdapterPackageDoctor.AdapterForStudentAssignmentList;
import com.example.markshandler3.DoctorPackage.ListPackageDoctor.ListViewForAllStudentsWhoOpenedTheAssignment;
import com.example.markshandler3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Student extends AppCompatActivity {
    TextView s_name,s_id,s_score,t_score;
    String id = AdapterForStudentAssignmentList.id_s_d;

    DatabaseReference ref = ListViewForAllStudentsWhoOpenedTheAssignment.ref_d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        s_name = findViewById(R.id.student_name_txt_vi);
        s_id = findViewById(R.id.student_id_txt_vi);
        s_score = findViewById(R.id.student_score_txt_vi);
        t_score = findViewById(R.id.total_score_txt_vi);
        String n = "student";

        s_name.setText(n);
        s_id.setText(id);
        ref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.child("st_score").getValue().toString();
                s_score.setText(s);
                String t = dataSnapshot.child("to_score").getValue().toString();
                t_score.setText(t);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
