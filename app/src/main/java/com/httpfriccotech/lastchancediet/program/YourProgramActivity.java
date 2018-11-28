package com.httpfriccotech.lastchancediet.program;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class YourProgramActivity extends AppCompatActivity implements Observer<Object> ,View.OnClickListener {

    private RecyclerView recyclerView;
    private RelativeLayout progressLayout;
    private List<ProgramData> itemList;
    private ProgramListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_program);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        progressLayout=(RelativeLayout)findViewById(R.id.progressLayout);
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        itemList=new ArrayList<ProgramData>();
        getData();
        showProgress();
    }

    private void getData() {
        APIClient.startQuery().getProgramList(SharedPref.getUserId(this)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object data) {
        hideProgress();
        if (data != null && data instanceof JsonObject) {
            JsonObject jsonObject=(JsonObject)data;
            if (jsonObject.get("success").getAsBoolean()) {
                JsonArray jsonElements=jsonObject.getAsJsonArray("data");
                for (int i=0;i<jsonElements.size();i++){
                    ProgramData programData=new ProgramData();
                    programData.setTitle(jsonElements.get(i).getAsJsonObject().get("title").getAsString());
                    programData.setContent(jsonElements.get(i).getAsJsonObject().get("content").getAsString());
                    programData.setPostId(jsonElements.get(i).getAsJsonObject().get("postId").getAsInt());
                    programData.setParmlink(jsonElements.get(i).getAsJsonObject().get("parmlink").getAsString());
                    programData.setActiveProgram(jsonElements.get(i).getAsJsonObject().get("isActiveProgram").getAsBoolean());
                    programData.setIsAllowedProgram(jsonElements.get(i).getAsJsonObject().get("isAllowedProgram").getAsInt());
                    itemList.add(programData);
                }
                myAdapter = new ProgramListAdapter(this, itemList);
                RecyclerView recycle = ((RecyclerView) findViewById(R.id.recycleview));
                recycle.setLayoutManager(new LinearLayoutManager(this));
                recycle.setAdapter(myAdapter);
            }else{
                Toast.makeText(getApplicationContext(),jsonObject.get("message").getAsString(),Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onError(Throwable e) {
        hideProgress();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete() {
        hideProgress();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void showProgress(){
        if (progressLayout!=null)
            progressLayout.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        if (progressLayout!=null)
            progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

    }
}
