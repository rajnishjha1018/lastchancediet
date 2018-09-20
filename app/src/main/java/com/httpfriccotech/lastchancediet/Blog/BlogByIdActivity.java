package com.httpfriccotech.lastchancediet.Blog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.DashboardnewActivity;
import com.httpfriccotech.lastchancediet.DownLoadImageTask;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.Recepies.RecepieActivity;
import com.httpfriccotech.lastchancediet.Workout.WorkoutActivity;
import com.httpfriccotech.lastchancediet.global.GlobalManage;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BlogByIdActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer<List<BlogByIdResponseData>> {
    Context context;
    String UserId, UserName,profileImage,blogId;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_by_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        bundle = getIntent().getExtras();
        blogId = getIntent().getExtras().getString("blogId");
        UserId = GlobalManage.getInstance().getUserId();
        UserName = GlobalManage.getInstance().getUserName();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.currentuser);
        TextView email = (TextView) header.findViewById(R.id.useremail);
        name.setText(this.UserName);
        email.setText("rajnish1018@gmail.com");
        getSupportActionBar().setTitle("");
        ((TextView) findViewById(R.id.toolbar_title)).setText("Blog");
        header.findViewById(R.id.navClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        getData(this.blogId);
    }
    private void getData(String blogId ) {
        APIClient.startQuery().doGetBlogById(blogId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BlogByIdActivity.this);
    }
    @Override
    public void onSubscribe(Disposable d)
    {

    }

    @Override
    public void onNext(List<BlogByIdResponseData> data) {
        if (data != null){
            ImageView imageView = (ImageView) findViewById(R.id.profileImage);
            new DownLoadImageTask(imageView).execute(data.get(0).getBlogThumbUrl());

            TextView PostName = (TextView) findViewById(R.id.PostName);
            PostName.setText(data.get(0).getTitle());

            TextView PostContent = (TextView) findViewById(R.id.PostContent);
            PostContent.setText(data.get(0).getContent());

            TextView PostAuthor = (TextView) findViewById(R.id.PostAuthor);
            PostAuthor.setText("By "+data.get(0).getAuthor()+" "+data.get(0).getPostDate());
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_DASHBOARD) {
            bundle.putString("userId", UserId);
            bundle.putString("userName", UserName);
            Intent intent = new Intent(context, DashboardnewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (id == R.id.nav_RECIPES) {
            bundle.putString("userId", UserId);
            bundle.putString("userName", UserName);
            Intent intent = new Intent(context, RecepieActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (id == R.id.nav_WORKOUTS) {
            bundle.putString("userId", UserId);
            bundle.putString("userName", UserName);
            Intent intent = new Intent(context, WorkoutActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (id == R.id.nav_BLOG) {
            bundle.putString("userId", UserId);
            bundle.putString("userName", UserName);
            Intent intent = new Intent(context, BlogActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (id == R.id.nav_PROFILE) {

        } else if (id == R.id.nav_SCIENCEBEHINDUS) {

        } else if (id == R.id.nav_LOGOUT) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
