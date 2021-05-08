package com.example.moviesapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapplication.Models.ModelMovieDetail;
import com.example.moviesapplication.Models.ModelMovies;
import com.example.moviesapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterImageDetail extends RecyclerView.Adapter<AdapterImageDetail.Holder_detals> {
    Context context;
    ModelMovieDetail modelMovieDetail;
    String[] list_pictures;
    AdapterImageDetail.OnItemClickListner listener;



    public interface OnItemClickListner{
        void OnItemCLick(int postion);
    }
    public void setOnItemClickListner(AdapterImageDetail.OnItemClickListner listener){
        this.listener=listener;

    }

    public AdapterImageDetail(Context context,String[] list){
        this.context=context;
        this.list_pictures=list;

        this.listener=listener;


    }
    @NonNull
    @Override
    public AdapterImageDetail.Holder_detals onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_image_detail,parent,false);

        return new AdapterImageDetail.Holder_detals(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImageDetail.Holder_detals holder, int position) {


        Picasso.get().load(list_pictures[position]).into(holder.imageView_detail);

    }

    @Override
    public int getItemCount()
    {

        return list_pictures.length;
    }

    public class Holder_detals extends RecyclerView.ViewHolder {


        ImageView imageView_detail;
        public Holder_detals(@NonNull View itemView, AdapterImageDetail.OnItemClickListner listener) {
            super(itemView);

            imageView_detail= itemView.findViewById(R.id.image_recycle_detail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(listener==null)){
                        int position=getAdapterPosition();
                        listener.OnItemCLick(position);
                    }
                }
            });
        }


    }

}
