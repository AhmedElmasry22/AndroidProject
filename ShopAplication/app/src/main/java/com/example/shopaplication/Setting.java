package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopaplication.Models.Model_Login;
import com.example.shopaplication.Models.Pralvant;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Setting extends AppCompatActivity {
    private CircleImageView circleImageView;
    private EditText edit_full_name, edit_pass, edit_address;
    private TextView text_close, text_update, text_change_photo,text_set_security;
    private String name,pass,address;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Uri imUri;
    private String downloadurl,uploadUrl;
    String checked = "";

    private static final int PICK_IMAGE_REQUEST_CODE = 1;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setteing);

        circleImageView = findViewById(R.id.circle_image_setting);
        edit_full_name = findViewById(R.id.edit_name_setting);
        edit_pass = findViewById(R.id.edit_pass_setting);
        edit_address = findViewById(R.id.edit_address_setting);
        text_close = findViewById(R.id.text_close_setting);
        text_update = findViewById(R.id.text_update_setting);
        text_change_photo = findViewById(R.id.text_change_photo_setting);
        text_set_security=findViewById(R.id.btn_set_security_setting);

        progressDialog=new ProgressDialog(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference().child("Profile images");

        Paper.init(this);
        Toast.makeText(this, ""+Pralvant.user_Current.getNumper(), Toast.LENGTH_SHORT).show();
        UserInfo();

        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        text_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked.equals("clicked")) {
                    userInfosaved();
                }else{
                    Model_Login model_login=new Model_Login();
                    model_login.setImageurl(downloadurl);
                    model_login.setName(edit_full_name.getText().toString());
                    model_login.setAddress(edit_address.getText().toString());
                    model_login.setPass(edit_pass.getText().toString());
                    model_login.setNumper(Pralvant.user_Current.getNumper());

                    databaseReference.child("users").child(Pralvant.user_Current.getNumper()).setValue(model_login);



                }
            }
        });


        text_change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
                checked = "clicked";
            }
        });
        text_set_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Setting.this,ResetPasswardActivity.class);
                intent.putExtra("reset","setting");
                startActivity(intent);
            }
        });


    }

    private void userInfosaved() {
        if (TextUtils.isEmpty(edit_full_name.getText().toString())) {
            Toast.makeText(this, "Name is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edit_pass.getText().toString())) {
            Toast.makeText(this, "number is mandatory ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edit_address.getText().toString())) {
            Toast.makeText(this, "address is mandatory", Toast.LENGTH_SHORT).show();
        }else if(checked.equals("clicked")){
            uploadphoto();


        }
    }

    private void uploadphoto() {
        if (imUri != null) {

            progressDialog.setTitle("Adding New Product");
            progressDialog.setMessage("please Wait........");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            StorageTask uploadTask;
            StorageReference filepath = storageReference.child(Pralvant.user_Current.getNumper()+".jpg");
            uploadTask=filepath.putFile(imUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri uri= task.getResult();
                        downloadurl=uri.toString();
                        Model_Login model_login=new Model_Login();
                        model_login.setImageurl(downloadurl);
                        model_login.setNumper(Pralvant.user_Current.getNumper());
                        model_login.setName(edit_full_name.getText().toString());
                        model_login.setAddress(edit_address.getText().toString());
                        model_login.setPass(edit_pass.getText().toString());

                        databaseReference.child("users").child(Pralvant.user_Current.getNumper()).setValue(model_login);
                        progressDialog.dismiss();
                        Paper.book().destroy();

                        Intent intent =new Intent(Setting.this,MainActivity.class);
                        startActivity(intent);


                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Setting.this, ""+e, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

        }

    }


    private void OpenGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    private void UserInfo() {
        databaseReference.child("users").child(Pralvant.user_Current.getNumper()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Model_Login model_login = snapshot.getValue(Model_Login.class);

                    if (snapshot.child("imageurl").exists()) {
                        name = model_login.getName();
                        Toast.makeText(Setting.this, ""+name, Toast.LENGTH_SHORT).show();
                        pass = model_login.getPass();
                        downloadurl = model_login.getImageurl();
                        address = model_login.getAddress();

                        Picasso.get().load(downloadurl).into(circleImageView);
                        edit_full_name.setText(name);
                        edit_pass.setText(pass);
                        edit_address.setText(address);


                    }else{
                        edit_full_name.setText(model_login.getName());
                        edit_pass.setText(model_login.getPass());
                        edit_address.setText(model_login.getAddress());

                    }
                }else{
                    Toast.makeText(Setting.this, "xxx", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Setting.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imUri = data.getData();
            circleImageView.setImageURI(imUri);
        } else {
            Toast.makeText(this, "Please Choase Image", Toast.LENGTH_SHORT).show();
        }
    }

}
