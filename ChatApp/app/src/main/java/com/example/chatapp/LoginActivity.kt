package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private  lateinit var  faireauth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener({
            login()
        })
    }

    private fun login() {
        faireauth= FirebaseAuth.getInstance()
        var email=edit_email_login.text.toString()
        var password =edit_password_login.text.toString()
        if(email==""){
            Toast.makeText(this,"Enter Email", Toast.LENGTH_SHORT).show()
        }else if(password==""){
            Toast.makeText(this,"Enter password", Toast.LENGTH_SHORT).show()


        }else{
            faireauth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                if(task.isSuccessful){
                    val intent= Intent(this,MainActivity2::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,""+task.getException().toString(), Toast.LENGTH_SHORT).show()


                }
            }

        }
    }
}