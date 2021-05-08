package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {
    var fairbase:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val btn_Login: Button=findViewById(R.id.btn_login_welcome)
        val btn_register: Button=findViewById(R.id.btn_register_welcome)

        fairbase= FirebaseAuth.getInstance()

        if (fairbase!!.currentUser!=null){
            val intent:Intent=Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
        btn_Login.setOnClickListener {
            val intent1:Intent=Intent(this,LoginActivity::class.java)
            startActivity(intent1)

        }
        btn_register.setOnClickListener {
            val intent2:Intent=Intent(this,RegestairActivity::class.java)
            startActivity(intent2)

        }
    }
    }

