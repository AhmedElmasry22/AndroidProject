package com.example.shopaplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class SellerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.navigation_dashboard){
                    Intent intent=new Intent(SellerActivity.this,SellerCatgoryActivity.class);
                    startActivity(intent);
                }else if(item.getItemId()==R.id.navigation_home){
                    Intent intent=new Intent(SellerActivity.this,SellerProductActivity.class);
                    startActivity(intent);

                }

                return false;
            }
        });



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.navigation_dashboard){
            Toast.makeText(this, "xx", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}

