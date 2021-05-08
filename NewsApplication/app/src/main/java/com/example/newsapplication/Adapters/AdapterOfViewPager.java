package com.example.newsapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.newsapplication.Activites.Details;
import com.example.newsapplication.Models.ModelOfHome;
import com.example.newsapplication.R;

import java.util.List;

public class AdapterOfViewPager extends PagerAdapter {
    private Context mContext;
    List<ModelOfHome.NewsBean.CategoryPostsBean> list ;
    AdapterOfViewPager adapterOfViewPager;
            OnnClick onClick;
            public static int id;


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

    public AdapterOfViewPager (){}

    public AdapterOfViewPager(Context context, List listDate) {
        mContext = context;
        list = listDate ;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.itemhome, container, false);

        ImageView imageView = view.findViewById(R.id.newsimage);
        TextView textViewnews = view.findViewById(R.id.news);

        //Textview textViewtitle = view.findViewById(R.id.newstitle);



        textViewnews.setText(list.get(position).getPost_title());
        Glide.with(mContext).load(list.get(position).getPost_img()).into(imageView);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onClick.click(position);
                id=list.get(position).getPost_id();

                Intent intent = new Intent(mContext,Details.class);
                mContext.startActivity(intent);
                Toast.makeText(mContext, list.get(position).getPost_id()+"", Toast.LENGTH_SHORT).show();

            }
        });

        container.addView(view);
        return view;
}
}
