package com.example.markshandler3.DoctorPackage.AdapterPackageDoctor;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.markshandler3.DoctorPackage.Student;
import com.example.markshandler3.R;

import java.util.ArrayList;

public class AdapterForStudentAssignmentList extends ArrayAdapter {
    ArrayList<String> student_names;
    public static String id_s_d;
    public AdapterForStudentAssignmentList(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        student_names = objects;
    }


    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.row_of_student_who_opened_the_assignment, parent, false);

        final TextView student_list = convertView.findViewById(R.id.student_item);
        student_list.setText(student_names.get(position));

        student_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                id_s_d = student_names.get(position);
                Intent intent = new Intent(parent.getContext(), Student.class);
                parent.getContext().startActivity(intent);

                return false;
            }
        });

        return convertView;
    }
}
