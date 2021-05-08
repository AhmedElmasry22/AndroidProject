package com.example.markshandler3.DoctorPackage.AdapterPackageDoctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.markshandler3.DoctorPackage.ListPackageDoctor.ListViewLecturesForDoctors;
import com.example.markshandler3.DoctorPackage.ListPackageDoctor.SubjectsListForDoctor;
import com.example.markshandler3.DoctorPackage.ModelsPackageDoctor.Modelforattendence;
import com.example.markshandler3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterListViewLecturesForDoctors extends ArrayAdapter {

    DatabaseReference d,d1,d2;
    ArrayList <Modelforattendence> list;

    public AdapterListViewLecturesForDoctors(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.row_lectures_listview_for_doctors, parent,false);

        list = ListViewLecturesForDoctors.list;

        TextView textname = convertView.findViewById(R.id.thenameofstudent);
        final TextView textid = convertView.findViewById(R.id.theidofstudent);
        Button delete_btn = convertView.findViewById(R.id.buttontodelet);

        d1 = FirebaseDatabase.getInstance().getReference();

        d = ListViewLecturesForDoctors.ref_s;

        final String lec = AdapterLA.lect_name;
        final String sub_name = SubjectsListForDoctor.subject_name;
        d2 = d1.child("Students").child(list.get(position).getId()).child("subjects").child(sub_name).child("lectures");
        textname.setText(list.get(position).getNmae());
        textid.setText(list.get(position).getId());
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d : dataSnapshot.getChildren())
                        {
                            if(d.getKey().equals(lec))
                            {
                                d2.child(lec).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                d.child(list.get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "removed", Toast.LENGTH_SHORT).show();
                    }
                });

                list.remove(position);
            }
        });
        return convertView;
    }
}
