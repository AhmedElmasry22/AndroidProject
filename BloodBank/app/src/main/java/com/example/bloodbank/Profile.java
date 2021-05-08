package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    TextView nameprofile,phoneprofile,emailprofile,addresprofile , textView;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance() ;
    ArrayList<ModelofDetails> list = new ArrayList<>();
    ModelofDetails modelofDetails ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameprofile=findViewById(R.id.yournameinprofile);
        phoneprofile=findViewById(R.id.yournumberphoneinprofile);
        emailprofile=findViewById(R.id.yorEmailinprofile);
        addresprofile=findViewById(R.id.yorAddressinprofile);
        textView=findViewById(R.id.bold);

        final String x =auth.getUid();
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();

            ref.child("AllInfo").child(x).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        modelofDetails = new ModelofDetails();
                        modelofDetails = dataSnapshot1.getValue(ModelofDetails.class);
                        list.add(modelofDetails);
                    }
                    Toast.makeText(Profile.this, list.get(0).getTypeofboold() + "", Toast.LENGTH_SHORT).show();
                    nameprofile.setText(list.get(0).getName());
                    phoneprofile.setText(list.get(0).getNumberphone());
                    addresprofile.setText(list.get(0).getCountry());
                    emailprofile.setText(list.get(0).getEmail());
                    textView.setText(list.get(0).getTypeofboold());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
}
