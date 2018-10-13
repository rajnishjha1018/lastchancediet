package com.httpfriccotech.lastchancediet.Blog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.global.GlobalManage;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class BlogActivity extends AppCompatActivity
        implements  Observer<List<BlogData>> {
    BlogAdapter myAdapter;
    Context context;
    String UserId, UserName, profileImage;
    Bundle bundle;
    GridView gridView;
    ArrayList<BlogData> blogData = new ArrayList<BlogData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_blog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        context = this;
        UserId = GlobalManage.getInstance().getUserId();
        UserName = GlobalManage.getInstance().getUserName();
        ((TextView) findViewById(R.id.toolbar_title)).setText("Blog List");
        getData();
    }

    private void getData() {
        APIClient.startQuery().doGetBlogs().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BlogActivity.this);
    }

        @Override
    public void onBackPressed() {

            super.onBackPressed();
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

    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onSubscribe(Disposable d) {

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onNext(List<BlogData> data) {
        this.blogData = (ArrayList<BlogData>) data;
        myAdapter = new BlogAdapter(BlogActivity.this, this.blogData);
        RecyclerView recycle=((RecyclerView)findViewById(R.id.recycleview));
        recycle.setLayoutManager(new GridLayoutManager(this,2));
        recycle.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
