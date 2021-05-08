package com.example.chatapp.Fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Adapter.AdapterSearch
import com.example.chatapp.ChatMessegeActivity
import com.example.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_search.view.*

class Search : Fragment() {
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private lateinit var searchViewModel:ViewModelSearchFragment
    private lateinit var firebaseUser:FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {




        val v= inflater.inflate(R.layout.fragment_search, container, false)
        firebaseUser=FirebaseAuth.getInstance().currentUser!!

        searchViewModel = ViewModelProvider(this@Search).get(ViewModelSearchFragment::class.java)


        mLayoutManager = LinearLayoutManager(v.context)
        v.recycle_search.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(v.context)
        v.recycle_search.layoutManager=mLayoutManager


       var adapter = AdapterSearch(v.context)
        v.recycle_search.adapter = adapter
        val edit_search1=v.findViewById<EditText>(R.id.edit_search)

        edit_search1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                  if(s==""){
                      searchViewModel.getData("")
                  }else{
                      searchViewModel.getData(s.toString())

                  }
            }
        })

        searchViewModel.getData("")







        searchViewModel.post.observe(viewLifecycleOwner, Observer {


            adapter.setList2(it)


            adapter.setOnItemClickListner(object: AdapterSearch.OnItemClickListner {
                override fun OnItemCLick(postion: Int) {
                    Toast.makeText(v.context,it[postion].uid,Toast.LENGTH_SHORT).show()

                    val option= arrayOf<CharSequence>(
                            "Send Message",
                            "Visit Profile"
                    )
                    val builder:AlertDialog.Builder=AlertDialog.Builder(v.context)
                    builder.setTitle("What do you want")
                    builder.setItems(option,DialogInterface.OnClickListener{dialog,postion2 ->

                        if (postion2==0){
                            val intent= Intent(context,ChatMessegeActivity::class.java)
                            intent.putExtra("visit_id",it[postion].uid)
                            startActivity(intent)

                        }else{

                        }

                    })
                    builder.create()
                    builder.show()


                }

            })



        })







        return v
    }

    }
