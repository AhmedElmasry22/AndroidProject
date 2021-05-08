package com.example.chatapp

import android.app.Activity
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Adapter.AdapterChat
import com.example.chatapp.Models.Messges
import com.example.chatapp.Models.Users
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat_messege.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class ChatMessegeActivity : AppCompatActivity() {
    lateinit var firebaseUser:FirebaseUser
    val not:NotificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    lateinit var reicvedUid:String
    var image_uri:Uri ?=null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private  var list:ArrayList<Messges>?=null
    private var adapterChat:AdapterChat?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_messege)



        val intent=intent
        reicvedUid=intent.getStringExtra("visit_id").toString()


        val toolbar:androidx.appcompat.widget.Toolbar=findViewById(R.id.toolbar_chat_messeges)
        setSupportActionBar(toolbar)
        supportActionBar?.title=""

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setOnClickListener({
            finish()
        })




        firebaseUser=FirebaseAuth.getInstance().currentUser!!

        mLayoutManager = LinearLayoutManager(this)
        recycle_chat_messege.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        recycle_chat_messege.layoutManager=mLayoutManager




        getInfoReceive()
        seenMassege()



        btn_send_messege.setOnClickListener({
            val messege:String=edit_chat_messege.text.toString()
            if(messege==""){
                Toast.makeText(this@ChatMessegeActivity,"Enter Messege",Toast.LENGTH_SHORT).show()
            }else{
                sendMessge(firebaseUser.uid,reicvedUid,messege)
            }

        })

        btn_attach_file_chat_messege.setOnClickListener({

            pickImage()

        })

  var seenListener:ValueEventListener?=null



    }

    private fun seenMassege(){
        var referance:DatabaseReference= FirebaseDatabase.getInstance().reference.child("Chats")
        var fairebase:FirebaseUser=FirebaseAuth.getInstance().currentUser!!
        referance.addValueEventListener(object :ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot in snapshot.children) {
                    val chats:Messges?= datasnapshot.getValue(Messges::class.java)

                    Toast.makeText(this@ChatMessegeActivity,"ZZ",Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@ChatMessegeActivity,""+firebaseUser.uid,Toast.LENGTH_SHORT).show()

                   Toast.makeText(this@ChatMessegeActivity,""+chats!!.received,Toast.LENGTH_SHORT).show()
                    if (chats!!.received==fairebase!!.uid){
                        Toast.makeText(this@ChatMessegeActivity,"XX",Toast.LENGTH_SHORT).show()

                        var  map:HashMap<String,Any> = HashMap()
                        map["isSeen"]=true
                        referance.child(chats!!.messegeUid).updateChildren(map)
                    }
                }

            }




        })


    }

    private fun sendMessge(senderUid: String, receivedUid: String?, messege: String) {
        val reference=FirebaseDatabase.getInstance().reference
        val messegeKey=reference.push().key
        val hasMapMessge=HashMap<String,Any>()
        hasMapMessge["sender"]=senderUid
        hasMapMessge["received"]=receivedUid.toString()
        hasMapMessge["messege"]=messege
        hasMapMessge["isSeen"]=false
        hasMapMessge["Uri"]=""
        hasMapMessge["messegeUid"]=messegeKey.toString()
        reference.child("Chats").child(messegeKey!!).setValue(hasMapMessge)
            .addOnCompleteListener({task->
            val chatReferance=FirebaseDatabase.getInstance().reference
                .child("ChatList").child(firebaseUser.uid)
                .child(reicvedUid)

                chatReferance.addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {

 }

                    override fun onDataChange(snapshot: DataSnapshot) {
                     if(!snapshot.exists()) {
                         chatReferance.child("id").setValue(receivedUid)
                     }
                        val chatReciveReferance=FirebaseDatabase.getInstance().reference
                            .child("ChatList").child(reicvedUid)
                            .child(firebaseUser.uid)
                        chatReciveReferance.child("id").setValue(firebaseUser.uid)
                        edit_chat_messege.setText("")






                    }

                })



})




    }
    private fun getInfoReceive(){
        val database_visit=FirebaseDatabase.getInstance().reference
        database_visit.child("Users").child(reicvedUid.toString())
            .addValueEventListener(object:ValueEventListener{
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val user=snapshot.getValue(Users::class.java)
                    Picasso.get().load(user!!.profile_photo).into(image_user_chat_messege)
                    text_user_name_chat_messege.text=user.name

                    getMesseges(firebaseUser!!.uid,reicvedUid,user.profile_photo)
                }

            })
    }
    private fun getMesseges(sender: String, reicvedUid: String, profilePhoto: String) {
        list= ArrayList()
        val referance=FirebaseDatabase.getInstance().reference.child("Chats")

        referance.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                list!!.clear()
                for (snapshot1 in snapshot.children){
                    val messges: Messges? =snapshot1.getValue(Messges::class.java)
                    if (messges?.sender.equals(reicvedUid)&&messges?.received.equals(sender)||
                            messges?.sender.equals(sender)&&messges?.received.equals(reicvedUid)){
                            list!!.add(messges!!)

                    }
                    adapterChat= AdapterChat(this@ChatMessegeActivity, list!!,profilePhoto)
                    recycle_chat_messege.adapter=adapterChat
                    recycle_chat_messege.scrollToPosition(list!!.size-1)
                }

            }

        })


    }


    private fun pickImage() {
        val intent= Intent()
        intent.type="image/*"
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,122)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==122&&resultCode== Activity.RESULT_OK&&data?.data!=null){
            image_uri = data.data!!
            Toast.makeText(this,"Uploading...........",Toast.LENGTH_SHORT).show()
            UploadImage()

        }
    }

    private fun UploadImage() {
        val progressBar= ProgressDialog(this)
        progressBar.setMessage(" please wait Image is sending......")
        progressBar.show()
        val storageReference=FirebaseStorage.getInstance().reference

        if (image_uri!=null){
            val database=FirebaseDatabase.getInstance().reference
            val imageKey=database.push().key
            val filRef=storageReference.child("Images Chat")
                .child(imageKey.toString()+".jpg")
            val uploadTask=filRef.putFile(image_uri!!)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful){
                    task.exception.let{
                        throw it!!
                    }
                }
                return@Continuation filRef.downloadUrl
            }).addOnCompleteListener({ task->
                if (task.isSuccessful){
                    val downloadUrl=task.result.toString()
                    val reference=FirebaseDatabase.getInstance().reference
                    val messegeKey=reference.push().key
                    val hasMapMessge=HashMap<String,Any>()
                    hasMapMessge["sender"]=firebaseUser.uid
                    hasMapMessge["received"]=reicvedUid
                    hasMapMessge["messege"]="sent image"
                    hasMapMessge["isSeen"]=false
                    hasMapMessge["Uri"]=downloadUrl
                    hasMapMessge["messegeUid"]=imageKey.toString()

                    reference.child("Chats")
                        .child(imageKey.toString()).setValue(hasMapMessge).addOnSuccessListener {
                            progressBar.dismiss()
                        }


                }
            })






        }

    }





}