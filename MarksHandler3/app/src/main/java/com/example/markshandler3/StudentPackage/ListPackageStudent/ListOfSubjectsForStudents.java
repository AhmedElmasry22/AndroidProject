package com.example.markshandler3.StudentPackage.ListPackageStudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.markshandler3.MainActivity;
import com.example.markshandler3.R;
import com.example.markshandler3.StudentPackage.AdapterPackageStudent.AdapterSubjectsOfStudents;
import com.example.markshandler3.StudentPackage.AttendanceAndAssignment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfSubjectsForStudents extends AppCompatActivity {

    ListView listView;
    EditText subject_name,subject_code;
    int flag = 1;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Button join;
    ArrayList<String> listOfStudent;
    public static String SubjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_subjects_for_students);

        listView =findViewById(R.id.list_student);
        listOfStudent = new ArrayList<>();
        subject_name = findViewById(R.id.name_subject);
        subject_code = findViewById(R.id.code_subject);
        join = findViewById(R.id.join);
        final String key_id = MainActivity.key_id;
        ref.child("Students").child(key_id).child("subjects").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot d:dataSnapshot.getChildren())
                    {
                        listOfStudent.add(d.getKey().toString());
                        AdapterSubjectsOfStudents adapterObject = new AdapterSubjectsOfStudents(ListOfSubjectsForStudents.this,0,listOfStudent);
                        listView.setAdapter(adapterObject);

                    }}
                else
                {
                    Toast.makeText(ListOfSubjectsForStudents.this, "no subjects yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sub_name = subject_name.getText().toString();
                final String sub_code = subject_code.getText().toString();
                ref.child("Subjects").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (final DataSnapshot d : dataSnapshot.getChildren()) {
                            String key = d.getKey();
                            String code = d.child("sub_code").getValue().toString();
                            if (sub_name.equals(key) && sub_code.equals(code)) {
                                flag = 2;
                                ref.child("Students").child(key_id).child("subjects").child(sub_name).setValue("").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        subject_code.setText("");
                                        subject_name.setText("");
                                        Toast.makeText(ListOfSubjectsForStudents.this, "joined", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            if(flag == 1)
                            {
                                subject_code.setText("");
                                subject_name.setText("");
                                Toast.makeText(ListOfSubjectsForStudents.this, "incorrect data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                SubjectName = item;
                Intent toAttendanceAndAssignment = new Intent(ListOfSubjectsForStudents.this, AttendanceAndAssignment.class);
                toAttendanceAndAssignment .putExtra("Position",position);
                startActivity(toAttendanceAndAssignment);
            }
        });


    }
}
