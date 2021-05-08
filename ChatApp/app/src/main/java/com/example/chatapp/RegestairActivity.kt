package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_regestair.*

class RegestairActivity : AppCompatActivity() {

    private  lateinit var  faireauth:FirebaseAuth
    private  lateinit var databaseuser:DatabaseReference
    private var fairebaseUid:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regestair)

        val toolbar:androidx.appcompat.widget.Toolbar=findViewById(R.id.toolbar_regestair)
        setSupportActionBar(toolbar)
        supportActionBar?.title="Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener() {
            finish()
        }

        btn_regstair.setOnClickListener({
            register()








        })





    }

    private fun register() {
        faireauth= FirebaseAuth.getInstance()
        databaseuser=FirebaseDatabase.getInstance().getReference()
        var name:String=edit_name_regestair.text.toString();
        var email:String=edit_email_regstair.text.toString();
        var password:String=edit_password_regstair.text.toString();

        if(name==""){
            Toast.makeText(this,"Enter Name",Toast.LENGTH_SHORT).show()
        }else if(email==""){
            Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show()

        }else if(password==""){
            Toast.makeText(this,"Enter pass",Toast.LENGTH_SHORT).show()

        }else{
            faireauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task->
                if (task.isSuccessful){
                    fairebaseUid=faireauth.currentUser!!.uid

                    var  map=HashMap<String,Any>()
                    map["uid"]=fairebaseUid.toString()
                    map["name"]=name
                    map["password"]=password.toString()
                    map["profile_photo"]="https://firebasestorage.googleapis.com/v0/b/secand-f1daf.appspot.com/o/download.png?alt=media&token=8b9e6106-f47d-40b9-ac87-fa1549d55318"
                    map["statue"]="offline"
                    map["search"]=name.toLowerCase()
                    map["facebook"]="https://m.facebook.com"
                    map["instagram"]="https://instagram.com"
                    map["website"]="https://wwww.google.com"

                    databaseuser.child("Users").child(fairebaseUid.toString()).updateChildren(map).addOnCompleteListener({task->
                       val intent= Intent(this,LoginActivity::class.java)
                       startActivity(intent)
                    })


                }else{
                    Toast.makeText(this,"Error"+task.exception.toString(),Toast.LENGTH_SHORT).show()

                }
            }

        }



    }
}