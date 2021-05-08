package com.example.markshandler3.DoctorPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.markshandler3.MainActivity;
import com.example.markshandler3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddSubject extends AppCompatActivity {
    EditText sub_name,sub_code;
    Button upload_subject;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        ref = FirebaseDatabase.getInstance().getReference("Subjects");
        sub_name = findViewById(R.id.subject_name);
        sub_code = findViewById(R.id.subject_code);
        upload_subject = findViewById(R.id.upload_subject);

        upload_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key_id = MainActivity.key_id;
                String name = sub_name.getText().toString();
                String code = sub_code.getText().toString();

                ref.child(name).child("sub_code").setValue(code);
                ref.child(name).child("id_doctor").setValue(key_id).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddSubject.this, "uploaded", Toast.LENGTH_SHORT).show();
                        sub_name.setText("");
                        sub_code.setText("");
                    }
                });
            }
        });
    }
}
