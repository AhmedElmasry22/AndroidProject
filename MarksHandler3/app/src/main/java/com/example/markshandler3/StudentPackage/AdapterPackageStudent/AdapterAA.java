package com.example.markshandler3.StudentPackage.AdapterPackageStudent;



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

import com.example.markshandler3.DoctorPackage.ModelsPackageDoctor.ModelLA;
import com.example.markshandler3.R;
import com.example.markshandler3.StudentPackage.Attandance;
import com.example.markshandler3.StudentPackage.ListPackageStudent.AssignmentsList;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AdapterAA extends ArrayAdapter {
    ArrayList<ModelLA> data;
    Context adapterContext;
    public static String lec_name;
    int adapterRescources;
    DatabaseReference reference;
    DatabaseReference d;

    public AdapterAA(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        this.adapterContext = context;
        this.adapterRescources = resource;
        data = objects;
    }
    public View getView(int position, View convertView, final ViewGroup parent) {
        View row;
        LayoutInflater rowInflater = LayoutInflater.from(adapterContext);
        row = rowInflater.inflate(adapterRescources ,parent,false);

        TextView nameJAVA = (TextView) row.findViewById(R.id.mainText);
        final Button Attend = row.findViewById(R.id.attend_lecture);
        reference = AssignmentsList.reference_ass;

        final String subject1 = data.get(position).getName();
        String subject2 = data.get(position).getState();
        nameJAVA.setText(subject1);

        if(subject2.equals("false"))
        {
            Attend.setBackgroundResource(R.drawable.c);
        }
        else
        {
            Attend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lec_name = subject1;
                    Intent intent = new Intent(getContext(), Attandance.class);
                    getContext().startActivity(intent);
                }
            });

            nameJAVA.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    lec_name = subject1;
                    Intent intent = new Intent(getContext(),Attandance.class);
                    getContext().startActivity(intent);
                    return false;
                }
            });}

        return row;
    }
}

