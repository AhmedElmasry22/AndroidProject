package com.example.chatapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Models.Messges
import com.example.chatapp.Models.Users
import com.example.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_serach_fragment.view.*

class AdapterChat(context: Context,list:ArrayList<Messges>,imageUrl:String) : RecyclerView.Adapter<AdapterChat.ViewHolder>() {
    var list= ArrayList<Messges>()
    var context:Context?=null
    var imageUrl:String?=""
    var firebaseUser2: FirebaseUser?=null


    init {
        this.list=list
        this.context=context
        this.imageUrl=imageUrl

    }

    interface OnItemClickListner {
        fun OnItemCLick(postion: Int)
    }

    var listener: OnItemClickListner? = null

    fun setOnItemClickListner(listener: OnItemClickListner?) {
        this.listener = listener
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if(viewType==1){
            var view= LayoutInflater.from(context).inflate(R.layout.item_right_chat, parent, false)

            ViewHolder(view,listener)
        }else{
              var view= LayoutInflater.from(context).inflate(R.layout.item_left_chat, parent, false)
            ViewHolder(view,listener)


        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val firebaseUser: FirebaseUser? =FirebaseAuth.getInstance().currentUser

        val messge=list[position]

        if(!firebaseUser?.uid.equals(messge.sender)){
            Picasso.get().load(imageUrl).into(holder.profileImage)

                }





        if(messge.messege.equals("sent image")){
            if(firebaseUser?.uid.equals(messge.sender)){
                holder.text_messege_right?.visibility=View.GONE
                holder.image_right?.visibility=View.VISIBLE
                Picasso.get().load(list[position].Uri).into(holder.image_right)

            }else if(!firebaseUser?.uid.equals(messge.sender)){
                holder.text_messege_left?.visibility=View.GONE
                holder.image_Left?.visibility=View.VISIBLE
                Picasso.get().load(messge.Uri).into(holder.image_Left)

            }
        }else{
            holder.text_messege_left?.text=messge.messege
            holder.text_messege_right?.text=messge.messege
        }
        if (list.size-1==position){
           if (messge.isSeen==true){
               holder.text_messege_seen?.visibility=View.VISIBLE
           }


        }




    }

    override fun getItemCount(): Int {

        return list.size

    }

    class ViewHolder(itemView: View, listener: OnItemClickListner?):RecyclerView.ViewHolder(itemView) {

        var profileImage:CircleImageView?=null
        var text_messege_left:TextView?=null
        var text_messege_right:TextView?=null
        var image_Left:ImageView?=null
        var image_right:ImageView?=null
        var text_messege_seen:TextView?=null
        init {

            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    listener.OnItemCLick(position)
                }
            }

            profileImage =itemView.findViewById(R.id.image_profile_left_chat)
            text_messege_left=itemView.findViewById(R.id.text_messge_left)
            text_messege_right=itemView.findViewById(R.id.text_messeg_right)
            image_Left=itemView.findViewById(R.id.image_chat_left)
            image_right=itemView.findViewById(R.id.image_chat_right)
            text_messege_seen=itemView.findViewById(R.id.text_seen_left)


        }

    }

    override fun getItemViewType(position: Int): Int {

         firebaseUser2 =FirebaseAuth.getInstance().currentUser
        if (firebaseUser2!!.uid.equals(list[position].sender)){

            return 1
        }else{

            return 0
        }

    }
}


