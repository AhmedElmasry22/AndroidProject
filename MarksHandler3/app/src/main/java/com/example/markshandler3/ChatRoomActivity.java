package com.example.markshandler3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ChatRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_room);


        AlertDialog dialog = new AlertDialog.Builder(this).create();
        final EditText editText = new EditText(this);
        dialog.setTitle("Enter Your Name");
        dialog.setView(editText);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText.getText().toString();
                if (!TextUtils.isEmpty(name)) {

                    Intent intent = new Intent(ChatRoomActivity.this, ChateRoom.class);
                    intent.putExtra("Name", name);

                    startActivity(intent);
                }else{
                    Toast.makeText(ChatRoomActivity.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();



    }
}
