package com.example.shopaplication.Adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopaplication.Models.ModelCart;
import com.example.shopaplication.Models.Products;
import com.example.shopaplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.Holder_detals> {

    Context context;
    List<ModelCart> list;
    AdapterCart.OnItemClickListner listener;

    public interface OnItemClickListner{
        void OnItemCLick(int postion);
        void OnEditMenu(int postion);
        void OnDeleteMenu(int postion);
    }
    public void setOnItemClickListner(AdapterCart.OnItemClickListner listener){
        this.listener=listener;

    }

    public AdapterCart(Context context, List list){
        this.context=context;
        this.list=list;
        this.listener=listener;


    }
    @NonNull
    @Override
    public AdapterCart.Holder_detals onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);

        return new AdapterCart.Holder_detals(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCart.Holder_detals holder, int position) {

        holder.text_product_name.setText(list.get(position).getPname());
        holder.text_product_price.setText(list.get(position).getPrice());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder_detals extends RecyclerView.ViewHolder {

        TextView text_product_name,text_product_price;
        ImageView imageView;
        public Holder_detals(@NonNull View itemView, AdapterCart.OnItemClickListner listener) {
            super(itemView);
            text_product_name=itemView.findViewById(R.id.text_name_cart);
            text_product_price=itemView.findViewById(R.id.text_price_cart);
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
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.setHeaderTitle("Select Option");
                    MenuItem edit_menu=menu.add(Menu.NONE,1,1,"Edit");
                    MenuItem delete_menu=menu.add(Menu.NONE,2,1,"Delete");

                    edit_menu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int postion=getAdapterPosition();
                            listener.OnEditMenu(postion);
                            return false;
                        }
                    });
                    delete_menu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int postion=getAdapterPosition();
                            listener.OnDeleteMenu(postion);
                            return false;
                        }
                    });

                }
            });
        }


    }
}
