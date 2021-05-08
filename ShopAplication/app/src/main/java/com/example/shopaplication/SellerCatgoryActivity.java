package com.example.shopaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SellerCatgoryActivity extends AppCompatActivity implements  View.OnClickListener {
    //private Button btn_Order,btn_logout,btn_maintian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_catagory);


    }


    @Override
    public void onClick(View v) {
        Intent i = new Intent(SellerCatgoryActivity.this, AdminAddNewProduct.class);
        switch (v.getId()){
            case R.id.tshirt:
                i.putExtra("product","tshirt");
                break;
            case R.id.sweather:
                i.putExtra("product","sweather");
                break;
            case R.id.female_dresses:
                i.putExtra("product","female_dresses");
                break;
            case R.id.sports:
                i.putExtra("product","sports");
                break;
            case R.id.glasses:
                i.putExtra("product","glasses");
                break;
            case R.id.purses_bags:
                i.putExtra("product","purses_bags");
                break;
            case R.id.hats:
                i.putExtra("product","hats");
                break;

            case R.id.shoess:
                i.putExtra("product","shoess");
                break;
            case R.id.laptops:
                i.putExtra("product","laptops");
                break;

            case R.id.watches:
                i.putExtra("product","watches");
                break;

            case R.id.mobiles:
                i.putExtra("product","mobiles");
                break;

        }
        startActivity(i);


    }
}

