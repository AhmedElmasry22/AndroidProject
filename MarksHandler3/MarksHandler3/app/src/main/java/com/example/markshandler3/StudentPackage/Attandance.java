package com.example.markshandler3.StudentPackage;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.markshandler3.MainActivity;
import com.example.markshandler3.R;
import com.example.markshandler3.StudentPackage.AdapterPackageStudent.AdapterAA;
import com.example.markshandler3.StudentPackage.ListPackageStudent.ListOfSubjectsForStudents;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Attandance extends AppCompatActivity {
    EditText lec_code;
    Button Attend;
    String code;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    String lec_name = AdapterAA.lec_name;
    String key_id = MainActivity.key_id;
    String sub_name = ListOfSubjectsForStudents.SubjectName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance);

        lec_code = findViewById(R.id.Attendance_code);
        Attend = findViewById(R.id.Attend_button);


        reference.child("Subjects").child(sub_name).child("lectures").child(lec_name).child("lec_code").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                code = dataSnapshot.getValue().toString();
                Toast.makeText(Attandance.this, code, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code.equals(lec_code.getText().toString()))
                {
                    reference.child("Students").child(key_id).child("subjects").child(sub_name).child("lectures").child(lec_name).setValue("");
                    reference.child("Subjects").child(sub_name).child("lectures").child(lec_name).child("students").child(key_id).setValue("").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Attandance.this, "Attended", Toast.LENGTH_SHORT).show();
                            lec_code.setText("");
                        }
                    });
                }
                else
                {
                    Toast.makeText(Attandance.this, "wrong code", Toast.LENGTH_SHORT).show();
                    lec_code.setText("");
                }

            }
        });


    }
}

