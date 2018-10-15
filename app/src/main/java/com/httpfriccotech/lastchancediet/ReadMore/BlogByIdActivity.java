package com.httpfriccotech.lastchancediet.ReadMore;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.DownLoadImageTask;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BlogByIdActivity extends AppCompatActivity implements Observer<List<BlogByIdResponseData>> {
    Context context;
    String UserId, UserName, profileImage, id, postType;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_by_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        bundle = getIntent().getExtras();
        id = getIntent().getExtras().getString("blogId");
        postType = getIntent().getExtras().getString("postType");
        UserId = SharedPref.getUserId(this);
        UserName = SharedPref.getUserName(this);
        if (postType.equalsIgnoreCase("blog")) {
            getBlogData(this.id);
        } else if (postType.equalsIgnoreCase("work")) {
            getWorkData(id);
        } else if (postType.equalsIgnoreCase("recipe")) {
            getRecipeData(id);
        }
    }

    private void getBlogData(String blogId) {
        APIClient.startQuery().doGetBlogById(blogId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(BlogByIdActivity.this);
    }

    private void getWorkData(String blogId) {
        APIClient.startQuery().doGetWorkoutById(blogId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(BlogByIdActivity.this);
    }

    private void getRecipeData(String blogId) {
        APIClient.startQuery().doGetRecipeById(blogId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(BlogByIdActivity.this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(List<BlogByIdResponseData> data) {
        if (data != null) {
            ImageView imageView = (ImageView) findViewById(R.id.profileImage);
            new DownLoadImageTask(imageView).execute(data.get(0).getBlogThumbUrl());

            TextView PostName = (TextView) findViewById(R.id.PostName);
            PostName.setText(data.get(0).getTitle());

            TextView PostContent = (TextView) findViewById(R.id.PostContent);
            PostContent.setText(data.get(0).getContent());

            TextView PostAuthor = (TextView) findViewById(R.id.PostAuthor);
            PostAuthor.setText("By " + data.get(0).getAuthor() + " " + data.get(0).getPostDate());
           /* TextView PostDate = (TextView) findViewById(R.id.PostDate);
            PostDate.setText(data.get(0).getPostDate());*/
            TextView PostView = (TextView) findViewById(R.id.PostView);
            PostView.setText(data.get(0).getPostViews());
        }

    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
        super.onBackPressed();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
