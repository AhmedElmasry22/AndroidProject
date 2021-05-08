package com.example.markshandler3.DoctorPackage.AdapterPackageDoctor;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markshandler3.R;

import java.util.ArrayList;

public class AdapterSubjectsForDoctors  extends ArrayAdapter {


    Context adapterContext;
    int adapterRescources;
    ArrayList<String> adapterSubjects;

    public AdapterSubjectsForDoctors(Context context, int resource, ArrayList personsData) {
        super(context, resource, personsData);
        this.adapterContext = context;
        this.adapterRescources = resource;
        this.adapterSubjects = personsData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        LayoutInflater rowInflater = LayoutInflater.from(adapterContext);
        row = rowInflater.inflate(adapterRescources ,parent,false);
        TextView nameJAVA = (TextView) row.findViewById(R.id.mainText);

        ImageView imageViewJAVA = (ImageView) row.findViewById(R.id.image1);
        String subject1 = adapterSubjects.get(position);
        nameJAVA.setText(subject1);
        return row;
    }
}

