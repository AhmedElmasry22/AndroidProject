package com.example.newsapplication.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.newsapplication.FrageMents.HomeFragment;
import com.example.newsapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragment=new HomeFragment();
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,fragment,"Home");
        transaction.commitNow();

        BottomNavigationView bottomNav = findViewById(R.id.nabar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }
    //*****************************************************************************

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                                    selectedFragment).commit();
                            break;


                        case R.id.nav_gallery:
                            Intent intent = new Intent(MainActivity.this,GallaryActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.nav_search:
                            Intent toserach = new Intent(MainActivity.this,SerachForDetails.class);
                            startActivity(toserach);
                            break;

                        case R.id.nav_logout:

                            finish();
                            break;
                    }

                    return true;
                }
            };

    //*****************************************************************************

}
