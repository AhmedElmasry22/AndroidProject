package com.example.markshandler3.DoctorPackage.AdapterPackageDoctor;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.markshandler3.DoctorPackage.FragmentPackageDoctor.AssignmentsFragmentForDoctorSubject;
import com.example.markshandler3.DoctorPackage.ListPackageDoctor.ListViewForAllStudentsWhoOpenedTheAssignment;
import com.example.markshandler3.DoctorPackage.ModelsPackageDoctor.ModelLA;
import com.example.markshandler3.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AdapterAL extends ArrayAdapter {
    ArrayList<ModelLA> data;
    Context adapterContext;
    int adapterRescources;
    public static String ass_name;

    DatabaseReference d ;

    public AdapterAL(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        this.adapterContext = context;
        this.adapterRescources = resource;
        data = objects;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        LayoutInflater rowInflater = LayoutInflater.from(adapterContext);
        row = rowInflater.inflate(adapterRescources ,parent,false);

        TextView nameJAVA = (TextView) row.findViewById(R.id.mainText);
        final Button Finish = row.findViewById(R.id.finish_assignment);
        d = AssignmentsFragmentForDoctorSubject.reference_ass;

        final String subject1 = data.get(position).getName();
        nameJAVA.setText(subject1);
        if(data.get(position).getState().equals("false"))
        {
            Finish.setBackgroundResource(R.drawable.c);
        }
        else
        {
            Finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Finish.setBackgroundResource(R.drawable.c);
                    d.child("assignment_access").setValue("false");
                }
            });}
        nameJAVA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ass_name = subject1;
                Intent newActivity = new Intent(getContext(), ListViewForAllStudentsWhoOpenedTheAssignment.class);
                getContext().startActivity(newActivity);
                return false;
            }
        });

        return row;
    }
}
