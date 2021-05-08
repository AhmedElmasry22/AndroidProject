package com.example.bloodbank;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment =new FragmentForAll();
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,fragment,"All ");
        transaction.commitNow();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.fisrt) {
            fragment =new FragmentForAll();
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment,"All ");
            transaction.commitNow();
            return true;

        }
       else if (id==R.id.seconed)
        {
            fragment =new FramentForABSeconed();
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment,"AB+ ");
            transaction.commitNow();
            return true;
        }
        else if (id==R.id.third)
        {
            fragment =new FragmetForABThird();
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment,"AB- ");
            transaction.commitNow();
            return true;
        }
        else if (id==R.id.four)
        {
            fragment =new FrgmentForA_Four();
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment,"A+ ");
            transaction.commitNow();
            return true;
        }
        else if (id==R.id.five)
        {
            fragment =new FragmentForA_Five();
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment,"A- ");
            transaction.commitNow();
            return true;
        }
        else if (id==R.id.six)
        {
            fragment =new FragmentForB_Six();
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment,"B+ ");
            transaction.commitNow();
            return true;
        }
        else if (id==R.id.seven)
        {
            fragment =new FrgamentForB_Seven();
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment,"B- ");
            transaction.commitNow();
            return true;
        }
        else if (id==R.id.eight)
        {
            fragment =new FragmentForEight_O();
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment,"O+ ");
            transaction.commitNow();
            return true;
        }
        else if (id==R.id.nine)
        {
            fragment =new FragmentForNine_O();
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout,fragment,"O- ");
            transaction.commitNow();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.registeritem)
        {
            Intent intenttoRegisteration = new Intent(MainActivity.this,Registeration.class);
            startActivity(intenttoRegisteration);

        } else if (id == R.id.loginitem)
        {
            Intent intenttoLogin = new Intent(MainActivity.this,Login.class);
            startActivity(intenttoLogin);

        } else if (id == R.id.profileitem)
        {
            Intent intent = new Intent(MainActivity.this,Profile.class);
            startActivity(intent);

        }  else if (id == R.id.logoutitem) {
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
