package com.example.bloodbank;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
public class AdapterRecycle extends  RecyclerView.Adapter<AdapterRecycle.Holder> implements AdapterRecycleInterFace {
    private static final int REQUEST_CALL = 1;
    Context contexts;
    List<ModelofDetails> lists;
    String number;

    public AdapterRecycle(Context context, List list) {
        lists = list;
        contexts = context;
    }


    @NonNull
    @Override
    public AdapterRecycle.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemofrecyle, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecycle.Holder holder, final int position) {
        holder.name.setText(lists.get(position).getName());
        holder.numberphone.setText(lists.get(position).getNumberphone());
        holder.address.setText(lists.get(position).getCountry());
        holder.type.setText(lists.get(position).getTypeofboold());
        number = lists.get(position).getNumberphone();
        holder.imagecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(contexts, lists.get(position).getNumberphone(), Toast.LENGTH_SHORT).show();
                 makePhoneCall(lists.get(position).getNumberphone());
            }
        });


    }
    @Override
    public int getItemCount() {
        return lists.size();
    }
    class Holder extends RecyclerView.ViewHolder {
        TextView name, numberphone, address, type;
        ImageView imagecall;

        public Holder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.namemainactivity);
            numberphone = itemView.findViewById(R.id.phonemainactivity);
            address = itemView.findViewById(R.id.titlemainactivity);
            type = itemView.findViewById(R.id.tttt);
            imagecall = itemView.findViewById(R.id.phone);

        }
    }
    private void makePhoneCall(String number) {

        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(contexts,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions((Activity) contexts,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
               contexts.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(contexts, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(number);
            } else {
                Toast.makeText(contexts, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



