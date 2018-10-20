package com.httpfriccotech.lastchancediet.Recepies;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.fragment.RecepieFragment;
import com.httpfriccotech.lastchancediet.global.GlobalManage;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecepieActivity extends AppCompatActivity implements Observer<Object> {
    TextView textView;
    MyAdapter myAdapter;
    Context context;
    String UserId, UserName, profileImage;
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
        UserId = GlobalManage.getInstance().getUserId();
        UserName = GlobalManage.getInstance().getUserName();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        ((TextView) findViewById(R.id.toolbar_title)).setText("Recepies");
        getData();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecepieFragment(), "All");
        adapter.addFragment(new RecepieFragment(), "Category 1");
        adapter.addFragment(new RecepieFragment(), "Category 2");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        if (o instanceof JsonArray) {
            JsonArray result = (JsonArray) o;
            JsonArray GetRecipesResult = result.getAsJsonArray();
            setData(GetRecipesResult);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

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
                } else {
                    return recepieList;
                }
            }
        }
    }

    private void getData() {
        APIClient.startQuery().doGetRecipieList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

//        String url = context.getString(R.string.ServiceURL)+"/wp-json/users/v1/getRecipes";
//        Log.i("url", url);
//        Ion.with(context)
//                .load(url)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        if(result!=null) {
//                            JsonArray GetRecipesResult = result.getAsJsonArray();
//                            setData(GetRecipesResult);
//                        }
//                    }
//                });
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
            String RecipeName = jsonObject.get("title").getAsString();
            String RecepieDescription = jsonObject.get("content").getAsString();
            String RecepieImage = jsonObject.get("url").getAsString();
            Integer RecepiePostId = Integer.parseInt(jsonObject.get("postId").getAsString());

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
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
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
