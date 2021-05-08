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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shopaplication.Models.Products;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProduct extends AppCompatActivity {
    private String x;
    private EditText edit_name_product,edit_descrepation,edit_price;
    private String name_product,descrepation,price;
    private Button bt1_add_product;
    private ImageView imageView_select;
    private static final int PICK_IMAGE_REQUEST_CODE=1;
    private Uri image_uri;
    private String save_data,save_time,key_random,download_url;
    private StorageReference storageReference;
    private DatabaseReference databaseReference,databaseReferenceSeller;
    private ProgressDialog progressDialog;
    private String seller_uid,seller_email,seller_address,seller_name,seller_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        x=getIntent().getExtras().get("product").toString();
        storageReference= FirebaseStorage.getInstance().getReference().child("Product Image");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Products");
        databaseReferenceSeller=FirebaseDatabase.getInstance().getReference().child("Seller");
        Toast.makeText(this, x+"", Toast.LENGTH_SHORT).show();
        progressDialog = new ProgressDialog(this);
        edit_name_product=findViewById(R.id.name_product);
        edit_descrepation=findViewById(R.id.description);
        edit_price=findViewById(R.id.price);
        bt1_add_product=findViewById(R.id.btn_add);
        imageView_select=findViewById(R.id.select_image);


        imageView_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        bt1_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });
        GetInfromationSeller();

    }

    private void OpenGallery(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST_CODE&&resultCode==RESULT_OK&&data!=null){
            image_uri=data.getData();
            imageView_select.setImageURI(image_uri);
        }else {
            Toast.makeText(this, "Please Choase Image", Toast.LENGTH_SHORT).show();
        }
    }
    private void UploadImage() {
        name_product = edit_name_product.getText().toString();
        descrepation = edit_descrepation.getText().toString();
        price = edit_price.getText().toString();

        if (TextUtils.isEmpty(name_product)) {
            Toast.makeText(this, "Please Enter Name Product", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(descrepation)) {
            Toast.makeText(this, "Please Enter Descrepation", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(price)){
            Toast.makeText(this, "Please Enter Price", Toast.LENGTH_SHORT).show();
        }else if(image_uri==null) {
            Toast.makeText(this, "Please Chose Image", Toast.LENGTH_SHORT).show();
        }else {
            StoregProductInformation();
        }
    }

    private void StoregProductInformation() {

        progressDialog.setTitle("Adding New Product");
        progressDialog.setMessage("please Wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM ddd,yyy");
        save_data=simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("HHH:mmm:ss a");
        save_time=simpleDateFormat.format(calendar.getTime());

        key_random=save_data+save_time;

        StorageReference filePath=storageReference.child(image_uri.getLastPathSegment()+key_random+".jpg");
        final UploadTask uploadTask=filePath.putFile(image_uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminAddNewProduct.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProduct.this, "Successfully Uploaded Image", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        download_url=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();


                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            download_url=task.getResult().toString();
                            progressDialog.dismiss();
                            Intent intent=new Intent(AdminAddNewProduct.this, SellerCatgoryActivity.class);
                            startActivity(intent);

                            Toast.makeText(AdminAddNewProduct.this, "Product Successful Upload to Database", Toast.LENGTH_SHORT).show();
                            UploadDatabase();
                        }

                    }
                });
            }
        });

    }

    private void UploadDatabase() {
        HashMap<String,Object> map=new HashMap<>();
        map.put("pid",key_random);
        map.put("data",save_data);
        map.put("time",save_time);
        map.put("descreption",descrepation);
        map.put("price",price);
        map.put("image",download_url);
        map.put("product_name",name_product);
        map.put("product_type",x);
        map.put("sellerName",seller_name);
        map.put("sellerAddress",seller_address);
        map.put("sellerPhone",seller_phone);
        map.put("sellerUid",seller_uid);
        map.put("sellerEmail",seller_email);
        map.put("Producte state","Not Approved");




        databaseReference.child(key_random).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminAddNewProduct.this, "Product Add", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminAddNewProduct.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void GetInfromationSeller(){
        databaseReferenceSeller.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(AdminAddNewProduct.this, ""+snapshot.child("name").getValue().toString(), Toast.LENGTH_SHORT).show();

                seller_name=snapshot.child("name").getValue().toString();
                seller_address=snapshot.child("address").getValue().toString();
                seller_email=snapshot.child("email").getValue().toString();
                seller_uid=snapshot.child("uid").getValue().toString();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminAddNewProduct.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });

    }
}