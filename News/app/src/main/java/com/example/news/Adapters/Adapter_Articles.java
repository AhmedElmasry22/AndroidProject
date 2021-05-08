package com.example.news.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.news.Models.Model_Articles;
import com.example.news.R;
import com.example.news.Utils;

import java.util.List;

import okhttp3.internal.Util;

public class Adapter_Articles extends RecyclerView.Adapter<Adapter_Articles.ViewHolder>{

    private List<Model_Articles> articles;
    private Context context;
    private OnItemClickListener onItemClickListener ;

    public Adapter_Articles(List<Model_Articles> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item , parent, false);
        return new ViewHolder(view , onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final ViewHolder viewHolder = holder;
        Model_Articles model = articles.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context).load(model.getUrlToImages())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade()).into(holder.imageView);


        holder.title.setText(model.getTitle());
        holder.source.setText(model.getSource().getName());
        holder.author.setText(model.getAuthor());
        holder.desc.setText(model.getDescribtion());

        holder.publishedAt.setText(Utils.DateFormat(model.getPublishedAt()));
        holder.time.setText("\2022" + Utils.DateFormat(model.getPublishedAt()));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void OnItemClick (View view , int position);

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView title , desc , author , publishedAt , source , time ;
        ImageView imageView;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView ,  OnItemClickListener onItemClickListener ) {
            super(itemView);

            itemView.setOnClickListener(this);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.prgrass_load_photo);

            this.onItemClickListener = onItemClickListener;

        }

        @Override
        public void onClick(View view) {

            onItemClickListener.OnItemClick(view , getAdapterPosition());
        }
    }

}
