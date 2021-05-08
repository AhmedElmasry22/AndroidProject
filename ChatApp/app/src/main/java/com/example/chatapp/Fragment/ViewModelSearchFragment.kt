package com.example.chatapp.Fragment

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.Models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ViewModelSearchFragment : ViewModel() {
    var post = MutableLiveData<ArrayList<Users>>()
    private var databaseReference:DatabaseReference=FirebaseDatabase.getInstance().getReference()

    var firebaseUser: FirebaseUser= FirebaseAuth.getInstance().currentUser!!



    fun getData(s: CharSequence) {

        if (s=="") {
            databaseReference.child("Users").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var list1 = ArrayList<Users>()
                    for (snapshot1 in snapshot.children) {

                        val user = snapshot1.getValue(Users::class.java)

                        if (user != null) {

                            list1.add(user)


                        }


                    }
                    post.value = list1


                }

                override fun onCancelled(error: DatabaseError) {
                    post.value = null

                }

            })


        }else{
            databaseReference.child("Users").orderByChild("search").startAt(s.toString()).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var list1 = ArrayList<Users>()
                    for (snapshot1 in snapshot.children) {

                        val user = snapshot1.getValue(Users::class.java)

                        if (user != null) {

                            list1.add(user)


                        }


                    }
                    post.value = list1


                }

                override fun onCancelled(error: DatabaseError) {
                    post.value = null

                }

            })


        }
    }





}