package com.httpfriccotech.lastchancediet.Recepies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.DashboardnewActivity;
import com.httpfriccotech.lastchancediet.RecepieFragment;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.Workout.WorkoutActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class RecepieActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        TextView textView;
        MyAdapter myAdapter;
        Context context;
        String UserId, UserName,profileImage;
        Bundle bundle;
        ArrayList<RecepieItem> recepieList = new ArrayList<RecepieItem>();

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie);
        context = this;
        bundle = getIntent().getExtras();
        UserId = bundle.getString("userId");
        UserName = bundle.getString("userName");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        ((TextView) findViewById(R.id.toolbar_title)).setText("Recepies");

        getData();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecepieFragment(),"All");
        adapter.addFragment(new RecepieFragment(), "Category 1");
        adapter.addFragment(new RecepieFragment(), "Category 2");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                RecepieFragment f1 = RecepieFragment.newInstance(filter(position));
                return f1;
            } else if (position == 1) {
                RecepieFragment f1 = RecepieFragment.newInstance(filter(position));
                return f1;
            } else {
                RecepieFragment f1 = RecepieFragment.newInstance(filter(position));
                return f1;
            }
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public ArrayList<RecepieItem> filter(int charText) {

            ArrayList<RecepieItem> FilterrecepieList = new ArrayList<RecepieItem>();
            {
                if (charText > 0) {
                    for (RecepieItem wp : recepieList) {
                        if (wp.getRecepiePostId() < 200) {
                            FilterrecepieList.add(wp);
                        }
                    }
                    return FilterrecepieList;
                }
                else
                {
                    return recepieList;
                }
            }
        }
    }
    private void getData() {
        String url = context.getString(R.string.ServiceURL)+"/wp-json/users/v1/getRecipes";
        Log.i("url", url);
        Ion.with(context)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(result!=null) {
                            JsonArray GetRecipesResult = result.getAsJsonArray();
                            setData(GetRecipesResult);
                        }
                    }
                });
    }

    private void setData(JsonArray GetGetRecipesResult) {
        int size = GetGetRecipesResult.size();

        if (size <= 0) {
            showMessage("No data found");
            recepieList.clear();
            myAdapter.notifyDataSetChanged();
            return;
        }
        for (int i = 0; i < size; i++) {
            JsonObject jsonObject = GetGetRecipesResult.get(i).getAsJsonObject();
            String RecipeName=jsonObject.get("title").getAsString();
            String RecepieDescription=jsonObject.get("content").getAsString();
            String RecepieImage=jsonObject.get("url").getAsString();
            Integer RecepiePostId=Integer.parseInt(jsonObject.get("postId").getAsString());

            RecepieItem recepieItem = new RecepieItem(RecipeName, RecepieDescription, RecepieImage, RecepiePostId);
            if (!recepieList.contains(recepieItem)) {
                recepieList.add(recepieItem);
            }
        }
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.reciepy_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
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
        } else if (id == R.id.nav_PROFILE) {
        } else if (id == R.id.nav_SCIENCEBEHINDUS) {
        } else if (id == R.id.nav_LOGOUT) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
