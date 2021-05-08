package com.example.newsapplication.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.newsapplication.FrageMents.PhototFragment;
import com.example.newsapplication.FrageMents.VideoFragment;
import com.example.newsapplication.R;

public class GallaryActivity extends AppCompatActivity {
    Button buttonphtot,buttonvideo;
    Fragment fragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);

        buttonphtot=findViewById(R.id.phtotab);
        buttonvideo=findViewById(R.id.videotab);



        buttonphtot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment=new PhototFragment();
                transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutgalary,fragment,"Photo");
                transaction.commitNow();
            }
        });
        buttonvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment=new VideoFragment();
                transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutgalary,fragment,"Video");
                transaction.commitNow();

            }
        });

    }
}
