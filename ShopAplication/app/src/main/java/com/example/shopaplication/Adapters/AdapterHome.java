package com.example.shopaplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopaplication.Models.Products;
import com.example.shopaplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.Holder_detals> {
Context context;
List<Products> list;
OnItemClickListner listener;

public interface OnItemClickListner{
    void OnItemCLick(int postion);
}
public void setOnItemClickListner(OnItemClickListner listener){
    this.listener=listener;

}

public AdapterHome(Context context, List list){
    this.context=context;
    this.list=list;
    this.listener=listener;


}
    @NonNull
    @Override
    public Holder_detals onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.home_item,parent,false);

        return new Holder_detals(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_detals holder, int position) {

        holder.text_name.setText(list.get(position).getProduct_name());
        holder.text_descrepation.setText(list.get(position).getDescreption());
        Picasso.get().load(list.get(position).getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder_detals extends RecyclerView.ViewHolder {

    TextView text_name,text_descrepation;
    ImageView imageView;
      public Holder_detals(@NonNull View itemView,OnItemClickListner listener) {
            super(itemView);
            text_name=itemView.findViewById(R.id.text_name_product);
            text_descrepation=itemView.findViewById(R.id.text_descrepation_product);
            imageView= itemView.findViewById(R.id.image_product);
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
