package com.example.markshandler3.StudentPackage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.markshandler3.R;
import com.example.markshandler3.StudentPackage.ListPackageStudent.AssignmentsList;
import com.example.markshandler3.StudentPackage.ListPackageStudent.ListOfSubjectsForStudents;

public class AttendanceAndAssignment extends AppCompatActivity {

    TextView txtView;
    ImageButton imageBtnAttandance ,imageBtnAssignments ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_and_assignment);

        String sub_name = ListOfSubjectsForStudents.SubjectName;
        ListOfSubjectsForStudents listOfStudent = new ListOfSubjectsForStudents();

        txtView = findViewById(R.id.txt_subject_name);
        txtView.setText(sub_name);


        imageBtnAttandance = findViewById(R.id.Attendance_image);
        imageBtnAttandance.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent toAttendance = new Intent(AttendanceAndAssignment.this,lecture_student.class);
                startActivity(toAttendance);

            }
        });

        imageBtnAssignments = findViewById(R.id.Assignment_image);
        imageBtnAssignments.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent toAssignments = new Intent(AttendanceAndAssignment.this, AssignmentsList.class);
                toAssignments.putExtra("Position_Name" , txtView.getText());
                startActivity(toAssignments);

            }
        });




    }
}
