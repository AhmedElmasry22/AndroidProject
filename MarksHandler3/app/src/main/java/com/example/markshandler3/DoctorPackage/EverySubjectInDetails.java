package com.example.markshandler3.DoctorPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.markshandler3.DoctorPackage.AdapterPackageDoctor.TabsAccessorAdapter;
import com.example.markshandler3.DoctorPackage.ListPackageDoctor.SubjectsListForDoctor;
import com.example.markshandler3.R;
import com.google.android.material.tabs.TabLayout;



    public class EverySubjectInDetails extends AppCompatActivity {
        private Toolbar mToolbar;
        private ViewPager myViewPager;
        private TabLayout myTabLayout;
        private TabsAccessorAdapter myTabsAccessorAdapter;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_every_subject_in_details);

            mToolbar =  findViewById(R.id.main_page_toolbar);
            setSupportActionBar(mToolbar);
            String sub_name = SubjectsListForDoctor.subject_name;
            getSupportActionBar().setTitle(sub_name);
            myViewPager =  findViewById(R.id.main_tabs_pager);
            myTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
            myViewPager.setAdapter(myTabsAccessorAdapter);


            myTabLayout = findViewById(R.id.main_tabs);
            myTabLayout.setupWithViewPager(myViewPager);

        }

    }
