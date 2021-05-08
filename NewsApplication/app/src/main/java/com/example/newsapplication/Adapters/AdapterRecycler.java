package com.example.newsapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.newsapplication.Models.ModelOfHome;
import com.example.newsapplication.R;

import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.Holder> {
    List<ModelOfHome.NewsBean> list;
    Context context;
    AdapterOfViewPager adapter;

    public AdapterRecycler(Context context, List list) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemviewpager, parent, false);
        return new Holder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        adapter = new AdapterOfViewPager(context, list.get(position).getCategory_posts());
        holder.viewPager.setAdapter(adapter);
        holder.textView.setText(list.get(position).getCategory_title());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class Holder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.view_pager);
            textView = itemView.findViewById(R.id.newstitle);

        }
    }
}


