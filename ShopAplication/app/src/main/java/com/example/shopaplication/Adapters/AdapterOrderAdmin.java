package com.example.shopaplication.Adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopaplication.Models.ModelOrder;
import com.example.shopaplication.Models.Products;
import com.example.shopaplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterOrderAdmin extends RecyclerView.Adapter<AdapterOrderAdmin.Holder_detals> {

    Context context;
    List<ModelOrder> list;
    AdapterOrderAdmin.OnItemClickListner listener;

    public interface OnItemClickListner{

        void OnButtonCLick(int postion);
        void deleteItem(int postion);
    }
    public void setOnItemClickListner(AdapterOrderAdmin.OnItemClickListner listener){
        this.listener=listener;

    }

    public AdapterOrderAdmin(Context context, List list){
        this.context=context;
        this.list=list;
        this.listener=listener;


    }
    @NonNull
    @Override
    public AdapterOrderAdmin.Holder_detals onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_admin_order,parent,false);

        return new AdapterOrderAdmin.Holder_detals(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrderAdmin.Holder_detals holder, int position) {

        holder.text_name.setText(list.get(position).getName());
        holder.text_address.setText(list.get(position).getAddress());
        holder.text_number.setText(list.get(position).getNumber());
        holder.text_city.setText(list.get(position).getCity());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder_detals extends RecyclerView.ViewHolder {

        TextView text_name,text_address,text_number,text_city;
        Button btn_show_detail;
        public Holder_detals(@NonNull View itemView, AdapterOrderAdmin.OnItemClickListner listener) {
            super(itemView);
            text_name=itemView.findViewById(R.id.text_name_user_admin_order);
            text_address=itemView.findViewById(R.id.text_address_user_admin_order);
            text_number=itemView.findViewById(R.id.text_number_user_admin_order);
            text_city=itemView.findViewById(R.id.text_city_user_admin_order);
            btn_show_detail=itemView.findViewById(R.id.button_showd_detial_admin_order);

            btn_show_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(listener==null)){
                        int position=getAdapterPosition();
                        listener.OnButtonCLick(position);
                    }

                }
            });
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.setHeaderTitle("Delete");
                    MenuItem delete_menu=menu.add(Menu.NONE,1,1,"yes");
                    MenuItem cancel_delete_menu=menu.add(Menu.NONE,2,1,"no");
                    delete_menu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(!(listener==null)){
                                int position=getAdapterPosition();
                                listener.deleteItem(position);
                            }

                            return false;
                        }
                    });
                }
            });


        }



    }

}
