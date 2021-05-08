package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.chatapp.Adapter.AdapterViewPager
import com.example.chatapp.Fragment.ChatFragment
import com.example.chatapp.Fragment.Search
import com.example.chatapp.Fragment.SettingFragment
import com.example.chatapp.Models.Messges
import com.example.chatapp.Models.Users
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    lateinit var  databaseReference:DatabaseReference
    lateinit var firebaseUser:FirebaseUser
    lateinit var data: Users
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val toolbar:androidx.appcompat.widget.Toolbar=findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title=""

        auth= FirebaseAuth.getInstance()
        val tableLayout:TabLayout=findViewById(R.id.tap_main)
        firebaseUser= FirebaseAuth.getInstance().currentUser!!





        val database:DatabaseReference=FirebaseDatabase.getInstance().getReference("Chats")
        database.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val viewPager:ViewPager=findViewById(R.id.view_pager_main)
                val adapterViewPager: AdapterViewPager = AdapterViewPager(supportFragmentManager)
                var count=0
                for (datasnapshot in snapshot.children){
                    val messges:Messges?=datasnapshot.getValue(Messges::class.java)

                    if (firebaseUser.uid.equals(messges!!.received)){
                        Toast.makeText(this@MainActivity2,""+messges!!.isSeen,Toast.LENGTH_SHORT).show()
                        count++
                    }

                }
                if (count==0){
                    adapterViewPager.addFragment(ChatFragment(),"Chat")

                }else{
                    adapterViewPager.addFragment(ChatFragment(),"($count) Chat")

                }
                adapterViewPager.addFragment(Search(),"Search")

                adapterViewPager.addFragment(SettingFragment(),"Setting")

                viewPager.adapter=adapterViewPager
                tableLayout.setupWithViewPager(viewPager)






            }

        })


        databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user =snapshot.getValue(Users::class.java)
                    Toast.makeText(this@MainActivity2,user?.name.toString(),Toast.LENGTH_SHORT).show()
                    val x= snapshot.child("name").value
                    text_user_name_main.setText(x.toString())
                    Picasso.get().load(snapshot.child("profile_photo").value.toString()).into(image_user_main)



                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity2,"xx",Toast.LENGTH_SHORT).show()


            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menue, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.signout){
            auth.signOut()
            val intent= Intent(this,WelcomeActivity::class.java)
            startActivity(intent)


        }
        return false

    }
}