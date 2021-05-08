package com.example.shopaplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopaplication.Adapters.AdapterHome;
import com.example.shopaplication.Models.Pralvant;
import com.example.shopaplication.Models.Products;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    androidx.appcompat.widget.Toolbar toolbar;
    private DatabaseReference databaseReference;
    private List<Products> list = new ArrayList<>();


    RecyclerView recyclerView;
    private AdapterHome mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String type="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = findViewById(R.id.home_recycle);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        Paper.init(this);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            type= getIntent().getExtras().get("Admin").toString();
        }


        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(type=="") {
            View headerview = navigationView.getHeaderView(0);
            TextView textView = headerview.findViewById(R.id.text_user);
            CircleImageView circleImageView = headerview.findViewById(R.id.circle_nav);
            textView.setText(Pralvant.user_Current.getName());
            Picasso.get().load(Pralvant.user_Current.getImageurl()).into(circleImageView);
        }


        Get_Item();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_cart:
                if(type=="") {
                    Intent i = new Intent(Home.this, CartActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.nav_orders:
                break;

            case R.id.nav_setteing:
                if(type!=null) {
                    Intent intent = new Intent(Home.this, Setting.class);
                    startActivity(intent);
                }
                break;
            case R.id.nav_logout:
                Paper.book().destroy();
                Intent intent=new Intent(Home.this,LoginActivity.class);
                startActivity(intent);


        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menue_search, menu);
        MenuItem search_item = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) search_item.getActionView();

       if(searchView.isIconified()||search_item.collapseActionView()==false) {
           searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String query) {
                   if (query.equals(null) || query.length() == 0) {
                       Get_Item();
                   } else {
                       Search(query);
                   }

                   return false;
               }

               @Override
               public boolean onQueryTextChange(String newText) {


                   Search(newText);

                   return false;
               }
           });
       }


        return true;
    }








    private  void Get_Item(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Products products=dataSnapshot.getValue(Products.class);
                    list.add(products);

                }
                mAdapter = new AdapterHome(Home.this,list);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListner(new AdapterHome.OnItemClickListner() {
                    @Override
                    public void OnItemCLick(int postion) {

                        if(type!=""){
                            Intent intent = new Intent(Home.this, AdminMaintainActivity.class);
                            intent.putExtra("id", list.get(postion).getPid());
                            startActivity(intent);


                        }else {
                            Intent intent = new Intent(Home.this, HomeDetail.class);
                            intent.putExtra("id", list.get(postion).getPid());
                            startActivity(intent);
                        }
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  String Search(String query) {

        databaseReference.orderByChild("name").startAt(query).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Products> list_change = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Products products = dataSnapshot.getValue(Products.class);
                    list_change.add(products);

                }
                mAdapter = new AdapterHome(Home.this, list_change);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListner(new AdapterHome.OnItemClickListner() {
                    @Override
                    public void OnItemCLick(int postion) {
                        Intent intent=new Intent(Home.this,HomeDetail.class);
                        intent.putExtra("id",list_change.get(postion).getPid());
                        startActivity(intent);
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return query;
    }

    }
