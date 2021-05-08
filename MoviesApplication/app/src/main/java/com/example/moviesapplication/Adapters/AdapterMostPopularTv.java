package com.example.moviesapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapplication.Models.ModelMovies;
import com.example.moviesapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMostPopularTv extends RecyclerView.Adapter<AdapterMostPopularTv.Holder_detals>{
    Context context;
    List<ModelMovies.TvShowsBean> list;
    OnItemClickListner listener;

    public interface OnItemClickListner{
        void OnItemCLick(int postion);
    }
    public void setOnItemClickListner(OnItemClickListner listener){
        this.listener=listener;

    }

    public AdapterMostPopularTv(Context context, List list){
        this.context=context;
        this.list=list;
        this.listener=listener;


    }
    @NonNull
    @Override
    public Holder_detals onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_most_popular_tv,parent,false);

        return new Holder_detals(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_detals holder, int position) {

        holder.text_name_movie.setText(list.get(position).getName());
        holder.text_country_movie.setText(list.get(position).getCountry());
        Picasso.get().load(list.get(position).getImage_thumbnail_path()).into(holder.imageView_movie);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder_detals extends RecyclerView.ViewHolder {

        TextView text_name_movie,text_country_movie;
        ImageView imageView_movie;
        public Holder_detals(@NonNull View itemView,OnItemClickListner listener) {
            super(itemView);
            text_name_movie=itemView.findViewById(R.id.text_name_movie);
            text_country_movie=itemView.findViewById(R.id.text_country_movie);
            imageView_movie= itemView.findViewById(R.id.image_movie_item_popular);
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
