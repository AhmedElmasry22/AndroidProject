package com.example.chatapp.Fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Notification
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.chatapp.Models.Users
import com.example.chatapp.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_setteing.view.*
import java.net.URI
import java.net.URL


class SettingFragment : Fragment() {
    lateinit var databaseReference: DatabaseReference;
    lateinit var firebaseUser: FirebaseUser;
    lateinit var v:View
    val requsetCode:Int=120
    lateinit var image_uri:Uri
    var storageReference:StorageReference ? = null
    var linkCheck:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v= inflater.inflate(R.layout.fragment_setteing, container, false)
        databaseReference=FirebaseDatabase.getInstance().getReference()
        storageReference=FirebaseStorage.getInstance().getReference("Users Image")
        firebaseUser= FirebaseAuth.getInstance().currentUser!!

        getInfo()

        v.image_setting.setOnClickListener({
            pickImage()

        })

        v.image_facebock.setOnClickListener({
            linkCheck="Faceboock"
            setSocialLink()

        })

        v.image_instagram.setOnClickListener({
            linkCheck="Instagram"
            setSocialLink()

        })
        v.image_google.setOnClickListener({
            linkCheck="Google"
            setSocialLink()

        })







        return v
    }

    private fun setSocialLink() {

        val builder:AlertDialog.Builder=AlertDialog.Builder(v.context,R.style.Theme_AppCompat_DayNight_Dialog_Alert)

        if (linkCheck=="Google"){
            builder.setTitle("Enter URL")
        }else{
            builder.setTitle("Enter user name")

        }
        val editText=EditText(context)

        if(linkCheck=="Google"){
            editText.hint="e.www.google.com"

        }else{
            editText.hint="e.alizb123"

        }
        builder.setView(editText)
        builder.setPositiveButton("Create",DialogInterface.OnClickListener({dialog, which ->
            val str:String=editText.text.toString()
            if (str==""){
                Toast.makeText(context,"Please Write Something....",Toast.LENGTH_SHORT)
            }else{
                saveLink(str)
            }
        }))

        builder.setPositiveButton("Cancel",DialogInterface.OnClickListener({dialog, which ->
            dialog.cancel()
            }))

        val alertDialog = builder.create()
        alertDialog.show()


        }

    private fun saveLink(str: String) {
       val mapSocial=HashMap<String,Any>()
        when(linkCheck){
            "Faceboock"->{
                mapSocial["facebook"]="https://m.facebook.com/$str"
            }
            "Instagram"->{
                mapSocial["instagram"]="https://m.instagram.com/$str"

            }
            "Google"->{
                mapSocial["website"]="https://$str"

            }


        }
        databaseReference.child("Users").child(firebaseUser.uid)
                .updateChildren(mapSocial).addOnCompleteListener({task->
                    if(task.isSuccessful){
                        Toast.makeText(v.context,"Updataed successful",Toast.LENGTH_SHORT).show()

                    }

        })


    }


    fun getInfo(){
        databaseReference.child("Users").child(firebaseUser.uid).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {


            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user: Users? =snapshot.getValue(Users::class.java)
                Picasso.get().load(user!!.profile_photo).into(v.image_setting)


            }

        })

    }

    private fun pickImage() {
        val intent=Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,requsetCode)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==requsetCode&&resultCode==Activity.RESULT_OK&&data?.data!=null){
            image_uri = data.data!!
            Toast.makeText(v.context,"Uploading...........",Toast.LENGTH_SHORT).show()
            UploadImage()

        }
    }

    private fun UploadImage() {
        val progressBar=ProgressDialog(v.context)
        progressBar.setMessage("Image Uploading please wait......")
        progressBar.show()
        if (image_uri!=null){
            val filRef=storageReference!!.child(System.currentTimeMillis().toString()+".jpg")
            val uploadTask=filRef.putFile(image_uri)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot,Task<Uri>> {task ->
               if (!task.isSuccessful){
                   task.exception.let{
                       throw it!!
                   }
               }
                return@Continuation filRef.downloadUrl
            }).addOnCompleteListener({task->
                if (task.isSuccessful){
                    val downloadUrl=task.result.toString()
                    val hashmapImage=HashMap<String,Any>()

                    hashmapImage["profile_photo"]=downloadUrl
                    databaseReference.child("Users").child(firebaseUser.uid)
                            .updateChildren(hashmapImage).addOnCompleteListener({task->
                                if (task.isSuccessful){
                                    progressBar.dismiss()
                                    Toast.makeText(v.context,"Image Uploading Succefuly",Toast.LENGTH_SHORT)
                                }else{
                                    progressBar.dismiss()
                                    Toast.makeText(v.context,"Image Uploading Succefuly",Toast.LENGTH_SHORT)
                                }
                            })
                }
            })



        }

    }


}