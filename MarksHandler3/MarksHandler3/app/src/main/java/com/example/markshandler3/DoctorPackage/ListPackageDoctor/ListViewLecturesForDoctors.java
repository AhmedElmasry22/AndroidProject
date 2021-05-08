package com.example.markshandler3.DoctorPackage.ListPackageDoctor;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markshandler3.DoctorPackage.AdapterPackageDoctor.AdapterLA;
import com.example.markshandler3.DoctorPackage.AdapterPackageDoctor.AdapterListViewLecturesForDoctors;
import com.example.markshandler3.DoctorPackage.ModelsPackageDoctor.Modelforattendence;
import com.example.markshandler3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListViewLecturesForDoctors extends AppCompatActivity {
    TextView txt1,txt2;
    ListView listView;
    Button delete,remove_all;
    public static ArrayList<Modelforattendence> list;
    Modelforattendence modelforattendence;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(), ref;
    public static DatabaseReference ref_s;
    String sub_name = SubjectsListForDoctor.subject_name;
    String lec_name = AdapterLA.lect_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_lectures_for_doctors);

        remove_all = findViewById(R.id.remove_all);
        delete = findViewById(R.id.buttontodelet);
        txt1 = findViewById(R.id.thenameofstudent);
        txt2 = findViewById(R.id.theidofstudent);



        list = new ArrayList<>();
        listView = findViewById(R.id.listviewstudentshasbeenattended);
        ref_s = reference.child("Subjects").child(sub_name).child("lectures").child(lec_name).child("students");
        ref_s.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren())
                {

                    modelforattendence = new Modelforattendence();
                    modelforattendence.setId(d.getKey());
                    modelforattendence.setNmae("student");
                    list.add(modelforattendence);
                    Toast.makeText(ListViewLecturesForDoctors.this,
                            modelforattendence.getId() , Toast.LENGTH_SHORT).show();

                }
                if(list.size()==0)
                {
                    Toast.makeText(ListViewLecturesForDoctors.this, "no one attend", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        AdapterListViewLecturesForDoctors adapterlis = new AdapterListViewLecturesForDoctors(ListViewLecturesForDoctors.this , 0, list);
        listView.setAdapter(adapterlis);
        /*remove_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref_s.setValue(null);
                list.clear();
                reference.child("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.getChildren())
                        {

                            if(d.child("subjects").child(sub_name).child("lectures").child(lec_name).exists())
                            {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });*/

    }

}

