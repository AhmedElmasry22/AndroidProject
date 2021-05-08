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

public class AdapterItemSeller extends RecyclerView.Adapter<AdapterItemSeller.Holder_detals> {
    Context context;
    List<Products> list;
    AdapterItemSeller.OnItemClickListner listener;

    public interface OnItemClickListner{
        void OnItemCLick(int postion);
    }
    public void setOnItemClickListner(AdapterItemSeller.OnItemClickListner listener){
        this.listener=listener;

    }

    public AdapterItemSeller(Context context, List list){
        this.context=context;
        this.list=list;
        this.listener=listener;


    }
    @NonNull
    @Override
    public AdapterItemSeller.Holder_detals onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_seller,parent,false);

        return new AdapterItemSeller.Holder_detals(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemSeller.Holder_detals holder, int position) {

        holder.text_name.setText(list.get(position).getProduct_name());
        holder.text_descrepation.setText(list.get(position).getDescreption());
        holder.text_price.setText(list.get(position).getPrice());
        holder.text_state.setText("Product State"+list.get(position).getProducte_state());

        Picasso.get().load(list.get(position).getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder_detals extends RecyclerView.ViewHolder {

        TextView text_name,text_descrepation,text_price,text_state;
        ImageView imageView;
        public Holder_detals(@NonNull View itemView, AdapterItemSeller.OnItemClickListner listener) {
            super(itemView);
            text_name=itemView.findViewById(R.id.text_name_product_seller);
            text_descrepation=itemView.findViewById(R.id.text_descrepation_product);
            imageView= itemView.findViewById(R.id.image_product_seller);
            text_descrepation=itemView.findViewById(R.id.text_descrepation_product_seller);
            text_price=itemView.findViewById(R.id.text_price_product_seller);
            text_state=itemView.findViewById(R.id.text_state_product_seller);
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
