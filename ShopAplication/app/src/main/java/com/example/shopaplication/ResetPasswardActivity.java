package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopaplication.Models.Pralvant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswardActivity extends AppCompatActivity {

    private EditText edit_phone,edit_first_question,edit_second_question;
    private Button btn_verify;
    TextView text_answer_question,text_reset;
    String type,first_question,second_question,phone;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passward);
        edit_phone=findViewById(R.id.edit_phone_reset_activity);
        edit_first_question=findViewById(R.id.edit_first_question_reset_activity);
        edit_second_question=findViewById(R.id.edit_second_question_reset_activity);
        text_answer_question=findViewById(R.id.text_answer_question);
        text_reset=findViewById(R.id.text_reset);
        btn_verify=findViewById(R.id.btn_verify_reset);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        Intent intent=getIntent();
        type=intent.getStringExtra("reset");
        if(type.equals("setting")){
            ShowInformation();
            edit_phone.setVisibility(View.GONE);
            btn_verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    first_question=edit_first_question.getText().toString().toLowerCase();
                    second_question=edit_second_question.getText().toString().toLowerCase();
                    if(first_question.equals("")){
                        Toast.makeText(ResetPasswardActivity.this, "Please Answer First Question", Toast.LENGTH_SHORT).show();
                    }else if(second_question.equals("")){
                        Toast.makeText(ResetPasswardActivity.this, "Please Answer First Question", Toast.LENGTH_SHORT).show();

                    }else {
                        UploadAnswer();
                    }


                }
            });


        }else if(type.equals("login")){
            btn_verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    phone=edit_phone.getText().toString();
                    first_question=edit_first_question.getText().toString().toLowerCase();
                    second_question=edit_second_question.getText().toString().toLowerCase();
                    if(phone.equals("")){
                        Toast.makeText(ResetPasswardActivity.this, "Enter Phone", Toast.LENGTH_SHORT).show();
                    }else if(first_question.equals("")){
                        Toast.makeText(ResetPasswardActivity.this, "Enter First Answer", Toast.LENGTH_SHORT).show();

                    }else if (second_question.equals("")){
                        Toast.makeText(ResetPasswardActivity.this, "Enter Second Answer", Toast.LENGTH_SHORT).show();
                    }else {
                        CheckAnswer();
                    }
                }
            });



        }





    }


    private void UploadAnswer() {

        HashMap<String,Object> map=new HashMap<>();
        map.put("firstAnswer",first_question);
        map.put("secondAnswer",second_question);
        databaseReference.child("users").child(Pralvant.user_Current.getNumper()).child("AnswerQuestion").updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPasswardActivity.this, "Verify Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ResetPasswardActivity.this,Home.class);
                    startActivity(intent);
                }
            }
        });
    }
 private void ShowInformation(){
     databaseReference.child("users").child(Pralvant.user_Current.getNumper())
             .child("AnswerQuestion").addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             if(snapshot.exists()){
                 text_answer_question.setText("Change Answer");
                 edit_first_question.setText(snapshot.child("firstAnswer").getValue().toString());
                 edit_second_question.setText(snapshot.child("secondAnswer").getValue().toString());
             }

         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });
   }

    private void CheckAnswer() {
        DatabaseReference databaseReference1=databaseReference.child("users")
                .child(phone);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    if (snapshot.hasChild("AnswerQuestion")) {
                        String first = snapshot.child("AnswerQuestion").child("firstAnswer").getValue().toString();
                        String second = snapshot.child("AnswerQuestion").child("secondAnswer").getValue().toString();
                        if (!(first.equals(first_question))) {

                            Toast.makeText(ResetPasswardActivity.this, "First Answer wrong", Toast.LENGTH_SHORT).show();
                        } else if (!(second.equals(second_question))) {
                            Toast.makeText(ResetPasswardActivity.this, "Second Answer wrong", Toast.LENGTH_SHORT).show();

                        } else {
                            AlertDialog.Builder builder=new AlertDialog.Builder(ResetPasswardActivity.this);
                            EditText newpassword=new EditText(ResetPasswardActivity.this);
                            builder.setTitle("New Password");
                            newpassword.setHint("Enter new Password");
                            builder.setView(newpassword);
                            builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (newpassword.equals("")){
                                        Toast.makeText(ResetPasswardActivity.this, "Enter new Password", Toast.LENGTH_SHORT).show();
                                    }else {
                                        databaseReference1.child("pass").setValue(newpassword.toString());
                                        Intent intent=new Intent(ResetPasswardActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }

                                }
                            });
                            builder.show();

                        }

                    }else {
                        Toast.makeText(ResetPasswardActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(ResetPasswardActivity.this, "Phone is not exsist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}