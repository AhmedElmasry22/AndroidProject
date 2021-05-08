package com.example.chatapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Models.Users
import com.example.chatapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_serach_fragment.view.*

class AdapterSearch(var context:Context) : RecyclerView.Adapter<AdapterSearch.ViewHolder>() {
     var list= ArrayList<Users>()

    interface OnItemClickListner {
        fun OnItemCLick(postion: Int)
    }

    var listener: OnItemClickListner? = null

    fun setOnItemClickListner(listener: OnItemClickListner?) {
        this.listener = listener
    }

    fun setList2(list: ArrayList<Users>){
        this.list=list
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.item_serach_fragment, parent, false)
        return ViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text_name.setText(list[position].name)
        holder.text_statue.setText(list[position].statue)
        Picasso.get().load(list[position].profile_photo).into(holder.imageView)


    }

    override fun getItemCount(): Int {

        return list.size

    }

    class ViewHolder(itemView: View, listener: OnItemClickListner?):RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    listener.OnItemCLick(position)
                }
            }
        }
         val text_name:TextView=itemView.name_search
         val text_statue:TextView=itemView.statue_search
        val imageView:ImageView=itemView.image_search;







    }
}