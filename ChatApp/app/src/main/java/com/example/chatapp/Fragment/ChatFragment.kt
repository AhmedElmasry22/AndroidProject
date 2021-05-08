package com.example.chatapp.Fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Adapter.AdapterSearch
import com.example.chatapp.ChatMessegeActivity
import com.example.chatapp.Models.ChatList
import com.example.chatapp.Models.Users
import com.example.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class ChatFragment : Fragment() {
    var listsArray:ArrayList<ChatList>?=null
    var usersArray:ArrayList<Users>?=null
    var firebaseUser:FirebaseUser?=null
    var databaseReference:DatabaseReference?=null

    private var mLayoutManager: RecyclerView.LayoutManager? = null
    var adapter:AdapterSearch?=null

    var v:View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v= inflater.inflate(R.layout.fragment_chat, container, false)

        firebaseUser=FirebaseAuth.getInstance().currentUser
        databaseReference=FirebaseDatabase.getInstance().getReference()
        listsArray= ArrayList()


        mLayoutManager = LinearLayoutManager(v!!.context)
        v!!.recycle_chat_list.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(v!!.context)
        v!!.recycle_chat_list.layoutManager=mLayoutManager

        adapter = AdapterSearch(v!!.context)
        v!!.recycle_chat_list.adapter = adapter

        databaseReference!!.child("ChatList").child(firebaseUser!!.uid).addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                listsArray!!.clear()
                for (snapshot1 in snapshot.children){

                    val chat=snapshot1.getValue(ChatList::class.java)
                    if (chat != null) {
                        listsArray!!.add(chat)
                        retiverListChat()



                    }
                }


            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
        return v
    }
    fun retiverListChat(){
        usersArray=ArrayList()
        databaseReference!!.child("Users").addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
              usersArray!!.clear()
                for (snapshot1 in snapshot.children){
                  val user=snapshot1.getValue(Users::class.java)
                  for (eachChatList in listsArray!!){
                      if(user!!.uid.equals(eachChatList.id)){
                          usersArray!!.add(user)
                      }
                  }
                    adapter!!.setList2(usersArray!!)

              }
                adapter!!.setOnItemClickListner(object: AdapterSearch.OnItemClickListner {
                    override fun OnItemCLick(postion: Int) {
                        Toast.makeText(v!!.context,usersArray!![postion].uid, Toast.LENGTH_SHORT).show()

                        val option= arrayOf<CharSequence>(
                                "Send Message",
                                "Visit Profile"
                        )
                        val builder: AlertDialog.Builder= AlertDialog.Builder(v!!.context)
                        builder.setTitle("What do you want")
                        builder.setItems(option, DialogInterface.OnClickListener{ dialog, postion2 ->

                            if (postion2==0){
                                val intent= Intent(context, ChatMessegeActivity::class.java)
                                intent.putExtra("visit_id",usersArray!![postion].uid)
                                startActivity(intent)

                            }else{

                            }

                        })
                        builder.create()
                        builder.show()


                    }

                })

            }

        })

    }


    }
