package com.example.newsapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapplication.Models.ModelofDetails;
import com.example.newsapplication.Models.ModelofSearch;
import com.example.newsapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterofSearch extends RecyclerView.Adapter<AdapterofSearch.SearchHolder> {
    List<ModelofSearch.PostsBean> list = new ArrayList<>();
    Context context;
    OnnClick onClick;

    //1
    public interface OnnClick {
        void click(int pos);
    }
    //--------
//2
    public void setOnClickListener(OnnClick onClick1) {
        onClick = onClick1;
    }
//-------------

    public AdapterofSearch(){}

    public AdapterofSearch(Context context, List list) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterofSearch.SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemofsearch, parent, false);
        return new SearchHolder(v,onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterofSearch.SearchHolder holder, int position) {

        holder.textView.setText( list.get(position).getPost_title());
        Glide.with(context).load(list.get(position).getPost_img()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class SearchHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public SearchHolder(@NonNull View itemView , OnnClick click  ) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imagesearch);
            textView=itemView.findViewById(R.id.textsearch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClick != null) {
                        onClick.click(getAdapterPosition());
                    }
                }
            });
        }
    }
}
