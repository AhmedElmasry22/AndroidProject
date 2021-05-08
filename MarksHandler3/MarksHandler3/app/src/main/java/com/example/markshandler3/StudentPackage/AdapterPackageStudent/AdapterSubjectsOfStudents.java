package com.example.markshandler3.StudentPackage.AdapterPackageStudent;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.markshandler3.R;

import java.util.ArrayList;


public class AdapterSubjectsOfStudents extends ArrayAdapter{
    ArrayList<String> dataList;

    public AdapterSubjectsOfStudents(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        dataList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.row_of_subjects_of_students, parent,false);


        TextView txtname= convertView.findViewById(R.id.txtname);
        txtname.setText(dataList.get(position));

        return convertView;
    }
}
