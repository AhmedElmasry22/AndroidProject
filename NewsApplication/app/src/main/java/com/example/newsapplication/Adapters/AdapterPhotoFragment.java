package com.example.newsapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapplication.Models.ModelOfHome;
import com.example.newsapplication.Models.ModelofPhotos;
import com.example.newsapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPhotoFragment extends RecyclerView.Adapter<AdapterPhotoFragment.Holder> {
    List<ModelofPhotos.AllPhotosBean> list;
    Context context;
    AdapterPhotoFragment adapterPhotoFragment;
    public AdapterPhotoFragment(Context context, List list) {
        this.list = list;
        this.context=context;
    }
    @NonNull
    @Override
    public AdapterPhotoFragment.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_galary, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPhotoFragment.Holder holder, int position) {
        adapterPhotoFragment = new AdapterPhotoFragment(context, list);
        Glide.with(context).load(list.get(position).getPhoto()).into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageviewgalary);
        }
    }
}
