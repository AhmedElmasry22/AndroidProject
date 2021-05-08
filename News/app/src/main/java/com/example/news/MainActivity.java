package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.Adapters.Adapter_Articles;
import com.example.news.Models.Model_Articles;
import com.example.news.Models.Model_News;
import com.example.news.Retrofit.APIinterface;
import com.example.news.Retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    public static final String API_KEY = "82a079fdc7c24513b273a486eab05278";
    private RecyclerView recyclerView;
    private List<Model_Articles> articles;
    private Adapter_Articles adapter_articles;
    private RecyclerView.LayoutManager layoutManager;
    private String TAG = MainActivity.class.getSimpleName();
    TextView toHeadLinde;
    APIinterface apIinterface;
    String country , language;
    SwipeRefreshLayout swipeRefreshLayout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        swipeRefreshLayout  =findViewById(R.id.swap_refresh);
        swipeRefreshLayout.setOnRefreshListener( this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);


        articles = new ArrayList<>();
        toHeadLinde = findViewById(R.id.topHeadLine);
        recyclerView = findViewById(R.id.recycleview);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        //Load_Jason("");
        OnLoadingSwabRefresh("");

    }
    // Loading Jason ********************************************************

    public void Load_Jason( final  String keyword) {
        apIinterface = ApiClient.getApiClient().create(APIinterface.class);
        toHeadLinde.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(true);


        country = Utils.getCountry();
        Call<Model_News> call;
        language = Utils.getLanguage();

        if (keyword.length() > 0 )
        {
            call = apIinterface.getNewsSearch(keyword , language , "publishedAt" , API_KEY);
        }
        else
            {
                call = apIinterface.getNews(country, API_KEY);

            }


        call.enqueue(new Callback<Model_News>() {
            @Override
            public void onResponse(Call<Model_News> call, Response<Model_News> response) {

                if (response.isSuccessful() && response.body().getArticles() !=null)
                {
                    if(!articles.isEmpty())
                    {
                        articles.clear();
                    }
                    articles = response.body().getArticles();
                    adapter_articles = new Adapter_Articles(articles , MainActivity.this);
                    recyclerView.setAdapter(adapter_articles);
                    adapter_articles.notifyDataSetChanged();

                    initLisner();

                    toHeadLinde.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                }
                else
                    {
                        toHeadLinde.setVisibility(View.INVISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "Not Result", Toast.LENGTH_SHORT).show();
                    }

            }
            @Override
            public void onFailure(Call<Model_News> call, Throwable t) {

                toHeadLinde.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    // End Loading Jason *******************************************************


    private void initLisner()
    {
        adapter_articles.setOnItemClickListener(new Adapter_Articles.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);

                intent.putExtra("url" , articles.get(position).getUrl() );
                intent.putExtra("title" ,  articles.get(position).getTitle());
                intent.putExtra("img" ,  articles.get(position).getUrlToImages());
                intent.putExtra("date" ,  articles.get(position).getPublishedAt());
                intent.putExtra("source" ,  articles.get(position).getSource().getName());
                intent.putExtra("author" ,  articles.get(position).getAuthor());

                startActivity(intent);

            }
        });
    }

    //  Seacrhing For Data *******************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main , menu);
        SearchManager searchManager =(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchmenuItme = menu.findItem(R.id.action_search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News ... ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (s.length() > 2)
                {
                    OnLoadingSwabRefresh(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //OnLoadingSwabRefresh(s);
                return false;
            }
        });

        searchmenuItme.getIcon().setVisible(false , false);

        return true;
    }

    // End Searching *********************************************************
    // Refresh Data *************************************************

    @Override
    public void onRefresh() {
        Load_Jason("");

    }

    private void OnLoadingSwabRefresh(final String keyword)
    {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
           Load_Jason(keyword);
            }
        });
    }

    //****************************************************************************
}
