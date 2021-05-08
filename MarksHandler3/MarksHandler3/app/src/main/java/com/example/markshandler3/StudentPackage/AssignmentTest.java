package com.example.markshandler3.StudentPackage;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.markshandler3.DoctorPackage.ModelsPackageDoctor.AssModel;
import com.example.markshandler3.MainActivity;
import com.example.markshandler3.R;
import com.example.markshandler3.StudentPackage.AdapterPackageStudent.AdapterAssignmentOfStudent;
import com.example.markshandler3.StudentPackage.ListPackageStudent.ListOfSubjectsForStudents;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssignmentTest extends AppCompatActivity {
    ListView list;
    TextView ass_name_view , question;
    RadioGroup rgb;
    RadioButton b1,b2,b3,b4;
    Button next;
    String text;
    String key_id = MainActivity.key_id;
    DatabaseReference reference , ref;
    AssModel assModel;
    ArrayList<AssModel> listassginmentstest;

    int count = 0,score = 0;
    String ass_name = AdapterAssignmentOfStudent.ass_name;
    String sub_name = ListOfSubjectsForStudents.SubjectName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_of_assignments_test);
        ass_name_view = findViewById(R.id.ass_name);
        question = findViewById(R.id.ass_question);
        rgb = findViewById(R.id.radio_edit_text);
        b1 = findViewById(R.id.txt1);
        b2 = findViewById(R.id.txt2);
        b3 = findViewById(R.id.txt3);
        b4 = findViewById(R.id.txt4);
        list = findViewById(R.id.assignments_test);
        final int xmlFile = R.layout.row_of_assignments_test;
        next = findViewById(R.id.buttontxt);



        listassginmentstest = new ArrayList<>();
        ass_name_view.setText(ass_name);

        reference = FirebaseDatabase.getInstance().getReference();
        ref = reference.child("Subjects").child(sub_name).child("assignments").child(ass_name).child("ass_questions");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren())
                {
                    assModel = new AssModel();
                    assModel = d.getValue(AssModel.class);
                    Toast.makeText(AssignmentTest.this, d.getValue() + "", Toast.LENGTH_SHORT).show();
                    listassginmentstest.add(assModel);
                }
                question.setText(listassginmentstest.get(count).getQuestion());
                b1.setText(listassginmentstest.get(count).getAnswer_one());
                b2.setText(listassginmentstest.get(count).getAnswer_two());
                b3.setText(listassginmentstest.get(count).getAnswer_three());
                b4.setText(listassginmentstest.get(count).getAnswer_four());
                rgb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton r = (RadioButton) findViewById(i);
                        text = r.getText().toString();
                    }
                });
                int selected = rgb.getCheckedRadioButtonId();
                final RadioButton gender = (RadioButton) findViewById(selected);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(text.equals(listassginmentstest.get(count).getRight_answer()))
                        {
                            score++;
                        }
                        b1.setChecked(false);
                        b2.setChecked(false);
                        b3.setChecked(false);
                        b4.setChecked(false);
                        Toast.makeText(AssignmentTest.this, text .toString(), Toast.LENGTH_SHORT).show();
                        count++;
                        if(count < listassginmentstest.size()&&count!=(listassginmentstest.size()-1)) {
                            question.setText(listassginmentstest.get(count).getQuestion());
                            b1.setText(listassginmentstest.get(count).getAnswer_one());
                            b2.setText(listassginmentstest.get(count).getAnswer_two());
                            b3.setText(listassginmentstest.get(count).getAnswer_three());
                            b4.setText(listassginmentstest.get(count).getAnswer_four());
                            rgb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    RadioButton r = (RadioButton) findViewById(i);
                                    text = r.getText().toString();
                                }
                            });
                        }
                        else if(count == listassginmentstest.size()-1)
                        {
                            next.setText("finish");
                            question.setText(listassginmentstest.get(count).getQuestion());
                            b1.setText(listassginmentstest.get(count).getAnswer_one());
                            b2.setText(listassginmentstest.get(count).getAnswer_two());
                            b3.setText(listassginmentstest.get(count).getAnswer_three());
                            b4.setText(listassginmentstest.get(count).getAnswer_four());
                            rgb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    RadioButton r = (RadioButton) findViewById(i);
                                    text = r.getText().toString();
                                }
                            });
                        }
                        else
                        {
                            reference.child("Subjects").child(sub_name).child("assignments").child(ass_name).child("students").child(key_id).child("st_score").setValue(score);
                            reference.child("Subjects").child(sub_name).child("assignments").child(ass_name).child("students").child(key_id).child("to_score").setValue(listassginmentstest.size()+"");
                            Toast.makeText(AssignmentTest.this, "done", Toast.LENGTH_SHORT).show();

                            question.setText("");
                            b1.setText("");
                            b2.setText("");
                            b3.setText("");
                            b4.setText("");
                            b1.setChecked(false);
                            b2.setChecked(false);
                            b3.setChecked(false);
                            b4.setChecked(false);
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // final AdapterAssignmentsTest adapterAssignmentsTest = new AdapterAssignmentsTest(getBaseContext(),xmlFile,listassginmentstest);
        // list.setAdapter(adapterAssignmentsTest);



    }
}
