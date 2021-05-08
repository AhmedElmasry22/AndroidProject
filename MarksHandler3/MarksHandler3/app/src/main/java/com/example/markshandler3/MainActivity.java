package com.example.markshandler3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.markshandler3.DoctorPackage.ListPackageDoctor.SubjectsListForDoctor;
import com.example.markshandler3.StudentPackage.ListPackageStudent.ListOfSubjectsForStudents;

public class MainActivity extends AppCompatActivity {
    Button btnlogin;
    Button btnChat;
    public static String key_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText text_user = findViewById(R.id.txtuser);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnChat = (Button) findViewById(R.id.chat);



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = text_user.getText().toString();
                if(name.equals("1001")||name.equals("1002")||name.equals("1003")||name.equals("1004")||name.equals("1005")||name.equals("1006")||name.equals("1007")||name.equals("1008")||name.equals("1009")||name.equals("1010"))
                {
                    key_id = name;
                 Intent toSubjectOfStudent = new Intent(MainActivity.this, ListOfSubjectsForStudents.class);
                   startActivity(toSubjectOfStudent);
                }
                else if(name.equals("1011")||name.equals("1012")||name.equals("1013")||name.equals("1014")||name.equals("1015")||name.equals("1016")||name.equals("1017")||name.equals("1018")||name.equals("1019")||name.equals("1020"))

                {
                    key_id = name;
                    Intent toSubjectOfDoctors = new Intent(MainActivity.this, SubjectsListForDoctor.class);
                    startActivity(toSubjectOfDoctors);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "enter correct id ", Toast.LENGTH_SHORT).show();
                }


            }

        });


        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toChatRoom = new Intent(MainActivity.this, ChatRoomActivity.class);
                startActivity(toChatRoom);

            }
        });


    }
}
