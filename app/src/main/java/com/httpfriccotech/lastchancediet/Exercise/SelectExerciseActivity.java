package com.httpfriccotech.lastchancediet.Exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.httpfriccotech.lastchancediet.Blog.BlogActivity;
import com.httpfriccotech.lastchancediet.Blog.BlogData;
import com.httpfriccotech.lastchancediet.Food.AddFoodPopupFragment;
import com.httpfriccotech.lastchancediet.Food.PopupFoodAdapter;
import com.httpfriccotech.lastchancediet.Food.SelectFoodData;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class SelectExerciseActivity extends AppCompatActivity implements Observer<List<SelectExerciseData>> {

    private SelectExerciseAdapter adapter;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);
        findViewById(R.id.search_recycle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Exercise");// + getIntent().getStringExtra("foodType"));
        }
        SearchView simpleSearchView = (SearchView) findViewById(R.id.search_view); // inititate a search view
        simpleSearchView.setIconifiedByDefault(false);
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// do something on text submit
                getData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do something when text changes
                return false;
            }
        });
        getData();
        findViewById(R.id.addNewExercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                Fragment frag = manager.findFragmentByTag("fragment_edit_name");
                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }
                AddFoodPopupFragment editNameDialog = new AddFoodPopupFragment();
                editNameDialog.show(manager, "fragment_edit_name");
                //close the popup window on button click

            }
        });
    }

    private void getData() {

        APIClient.startQuery().doGetExercisrSearchList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SelectExerciseActivity.this);

        /*HashMap<String, String> headerData = new HashMap<>();
        headerData.put("uid", "1234");
        APIClient.startQuery().doAddFood("breakfast", "23", headerData).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SelectFoodActivity.this);*/
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull List<SelectExerciseData> data) {

        if (data != null && data.size() > 0) {
            if (adapter == null) {
                ((RecyclerView) findViewById(R.id.search_recycle)).setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));

               /* adapter = new SelectExerciseAdapter(this, data, new PopupFoodAdapter.OnItemClicked() {
                    @Override
                    public void onItemClick(SelectFoodData data) {
                        Intent intent = new Intent();
                        intent.putExtra("data", data);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });*/
                ((RecyclerView) findViewById(R.id.search_recycle)).setAdapter(adapter);
//                ((RecyclerView) findViewById(R.id.search_recycle))
            } else {
                adapter.updateData(data);
            }
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
