package com.example.markshandler3.DoctorPackage.FragmentPackageDoctor;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.markshandler3.DoctorPackage.StartAssignmentsForDoctors;
import com.example.markshandler3.DoctorPackage.StartAttendanceForDoctors;
import com.example.markshandler3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragmentForDoctorSubject extends Fragment {

    Button btn_add_lecture;
    Button btn_add_assignments;


    public StartFragmentForDoctorSubject() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_start_fragment_for_doctor_subject, container, false);

        btn_add_lecture = view.findViewById(R.id.button1);
        btn_add_assignments = view.findViewById(R.id.button2);
        //***********************************************************

        btn_add_lecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StartAttendanceForDoctors.class);
                startActivity(intent);
            }
        });

        //*********************************************************
        btn_add_assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(), StartAssignmentsForDoctors.class);
                startActivity(intent);
            }
        });
        //*********************************************************
        return view;


    }

}
