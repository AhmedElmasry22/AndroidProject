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
import com.example.newsapplication.Models.ModelofDetails;
import com.example.newsapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterofDetails extends RecyclerView.Adapter<AdapterofDetails.Holder_details> {
    List<ModelofDetails> list=new ArrayList<>();
    Context context;


    public AdapterofDetails(Context context, List list) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterofDetails.Holder_details onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemdetails, parent, false);
        return new Holder_details(v);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterofDetails.Holder_details holder, int position) {

        holder.textView.setText(list.get(position).getPost_title());
        holder.textView1.setText(list.get(position).getLong_description());
        holder.textView2.setText(list.get(position).getShort_description());
        Glide.with(context).load(list.get(position).getPost_img()).into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder_details extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textView1,textView2;
        public Holder_details(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagedetails);
            textView = itemView.findViewById(R.id.titledetails);
            textView1=itemView.findViewById(R.id.describtionLongDetails);
            textView2=itemView.findViewById(R.id.describtionShortDetails);

        }
    }
}
