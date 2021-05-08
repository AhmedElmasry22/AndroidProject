package com.example.markshandler3.DoctorPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markshandler3.DoctorPackage.ListPackageDoctor.SubjectsListForDoctor;
import com.example.markshandler3.DoctorPackage.ModelsPackageDoctor.AssModel;
import com.example.markshandler3.MainActivity;
import com.example.markshandler3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartAssignmentsForDoctors extends AppCompatActivity {
    TextView namesofassignment;
    EditText questiontostudent,answerone,answertwo,answerthree,answerfour,rightanswers;
    Button uploading;
    int flag = 1;
    AssModel assModel;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Subjects");
    String key_id = MainActivity.key_id;
    String sub_name = SubjectsListForDoctor.subject_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_assignments_for_doctors);
        assModel = new AssModel();

        namesofassignment=findViewById(R.id.nameofassignment);
        questiontostudent=findViewById(R.id.questions);
        answerone=findViewById(R.id.ans1);
        answertwo=findViewById(R.id.ans2);
        answerthree=findViewById(R.id.ans3);
        answerfour=findViewById(R.id.ans4);
        rightanswers = findViewById(R.id.rightans);
        uploading=findViewById(R.id.upload);
        final DatabaseReference ref_ass = ref.child(sub_name).child("assignments");
        uploading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questiontostudent.getText().toString().equals("")||namesofassignment.getText().toString().equals("")|| answerone.getText().toString().equals("")|| answertwo.getText().toString().equals("")|| answerthree.getText().toString().equals("")|| answerfour.getText().toString().equals("")||rightanswers.getText().toString().equals(""))
                {
                    Toast.makeText(StartAssignmentsForDoctors.this, "enter whole data", Toast.LENGTH_SHORT).show();
                }
                else {
                    assModel.setAnswer_one(answerone.getText().toString());
                    assModel.setAnswer_two(answertwo.getText().toString());
                    assModel.setAnswer_three(answerthree.getText().toString());
                    assModel.setAnswer_four(answerfour.getText().toString());
                    assModel.setRight_answer(answerfour.getText().toString());
                    assModel.setQuestion(questiontostudent.getText().toString());
                    ref_ass.child(namesofassignment.getText().toString()).child("assignment_access").setValue("true");

                    ref_ass.child(namesofassignment.getText().toString()).child("ass_questions").push().setValue(assModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            answerone.setText("");
                            answertwo.setText("");
                            answerthree.setText("");
                            answerfour.setText("");
                            answerfour.setText("");
                            rightanswers.setText("");
                            questiontostudent.setText("");
                            namesofassignment.setText("");
                            Toast.makeText(StartAssignmentsForDoctors.this, "uploaded", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });


    }
}

