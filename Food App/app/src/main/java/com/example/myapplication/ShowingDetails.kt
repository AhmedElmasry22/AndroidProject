package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_showing_details.*

class ShowingDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showing_details)

        val data = intent.extras

        ivFoodImage.setImageResource(data!!.getInt("image"))
        tvName.text = data!!.getString("name")
        tvDetails.text = data!!.getString("des")

    }
}