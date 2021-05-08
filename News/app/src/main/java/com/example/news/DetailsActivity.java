package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private ImageView imageView;
    private TextView appBarTitle , appBarSubTitle , date , time ,title;
    private boolean isHideToolBarView = false;
    private FrameLayout date_behavoir;
    private LinearLayout titleApper;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String mUrl , mImg , mTitle , mDate , mSource , mAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Intialize Variable ************************
        toolbar =findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        appBarLayout =findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(this);


        date_behavoir = findViewById(R.id.date_behavior);
        titleApper  =findViewById(R.id.title_appbar);
        imageView = findViewById(R.id.backdrop);
        appBarTitle = findViewById(R.id.title_on_appbar);
        appBarSubTitle = findViewById(R.id.subtitle_on_appbar);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        title = findViewById(R.id.title);

        //*****************************************

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mImg = intent.getStringExtra("img");
        mTitle = intent.getStringExtra("title");
        mDate = intent.getStringExtra("date");
        mSource = intent.getStringExtra("source");
        mAuthor = intent.getStringExtra("author");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());

        Glide.with(this).load(mImg)
                .apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);

        appBarSubTitle.setText(mUrl);
        appBarTitle.setText(mSource);
        title.setText(mTitle);
        date.setText(Utils.DateFormat(mDate));

        String author = null;
        if (mAuthor != null || mAuthor != "")
        {
            mAuthor = "\u2022" + mAuthor;
        }else {
            author = " " ;
        }

        time.setText(mSource + author + "\2022" + Utils.DateFormat(mDate));

        initWebView(mUrl);
    }

    // Connect With Web ********************************


    private void initWebView( String url) {
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }

    public void onBackPressed(){
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

        int maxScroll = appBarLayout.getTotalScrollRange();
        float percntage = (float) Math.abs(i) / (float) maxScroll;

        if (percntage == 1f && isHideToolBarView)
        {
            date_behavoir.setVisibility(View.GONE);
            titleApper.setVisibility(View.VISIBLE);
            isHideToolBarView =! isHideToolBarView;
        }
        else if  (percntage < 1f && isHideToolBarView )
        {
            date_behavoir.setVisibility(View.VISIBLE);
            titleApper.setVisibility(View.GONE);
            isHideToolBarView =! isHideToolBarView;
        }
    }
    //*************************************
    // Shaare **************************


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.view_web)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mUrl));
            startActivity(intent);
            return true;
        }
        else if (id == R.id.share)
        {
            try
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plan");
                intent.putExtra(Intent.EXTRA_SUBJECT , mSource);
                String body = mTitle + "\n" + mUrl + "\n" + "Share From The News App" + "\n";
                intent.putExtra(Intent.EXTRA_TEXT , body);
                startActivity(Intent.createChooser(intent , "Share With :"));
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Sorry Can't Open Share", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
