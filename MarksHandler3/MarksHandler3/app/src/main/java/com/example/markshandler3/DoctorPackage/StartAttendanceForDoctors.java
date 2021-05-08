package com.example.markshandler3.DoctorPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.markshandler3.DoctorPackage.ListPackageDoctor.SubjectsListForDoctor;
import com.example.markshandler3.MainActivity;
import com.example.markshandler3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartAttendanceForDoctors extends AppCompatActivity {

    EditText lec_name, lec_code;
    String sub_name = SubjectsListForDoctor.subject_name;
    String key_id = MainActivity.key_id;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Subjects");
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_attendance_for_doctors);
        lec_name = findViewById(R.id.nameofstudents);
        lec_code = findViewById(R.id.codeofstudents);
        start = findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lec_n = lec_name.getText().toString();
                String lec_c = lec_code.getText().toString();
                if(lec_name.getText().toString().equals("")||lec_code.getText().toString().equals(""))
                {
                    Toast.makeText(StartAttendanceForDoctors.this, "enter whole data", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ref.child(sub_name)
                            .child("lectures").child(lec_n).child("lec_code").setValue(lec_c);
                    ref.child(sub_name).child("lectures").child(lec_n).child("lecture_access").setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(StartAttendanceForDoctors.this, "uploaded", Toast.LENGTH_SHORT).show();
                            lec_name.setText("");
                            lec_code.setText("");
                        }
                    });

                }}
        });


    }
}
