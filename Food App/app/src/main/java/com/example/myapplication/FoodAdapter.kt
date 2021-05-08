package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.item_grid_view.view.*

class FoodAdapter: BaseAdapter {

    var lista = ArrayList<Food>()
    var context: Context?=null


    constructor(context: Context, lista: ArrayList<Food>):super(){
        this.context = context
        this.lista = lista
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val food = this.lista[position]
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var foodView= inflator.inflate(R.layout.item_grid_view,null)
        foodView.tvName.text = food.name
        foodView.ivFoodImage.setImageResource(food.image!!)

        foodView.ivFoodImage.setOnClickListener{

            Toast.makeText(context, ""+food.name, Toast.LENGTH_LONG).show()

            val intent = Intent(context,ShowingDetails::class.java)
            intent.putExtra("name",food.name!!)
            intent.putExtra("des",food.description!!)
            intent.putExtra("image",food.image!!)
            context!!.startActivity(intent)

        }

        return foodView

    }

    override fun getItem(position: Int): Any
    {
        return lista[position]
    }

    override fun getItemId(position: Int): Long
    {
        return position.toLong()
    }

    override fun getCount(): Int
    {
        return lista.size
    }

    // Deleting item

    fun Delet(index:Int )
    {
        lista.removeAt(index)
        notifyDataSetChanged()

    }

    // Adding item
    fun Adding(index: Int)
    {
        lista.add(index , lista[index])
        notifyDataSetChanged()
    }

}