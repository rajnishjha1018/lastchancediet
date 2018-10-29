package com.httpfriccotech.lastchancediet.Food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class SelectFoodActivity extends AppCompatActivity implements Observer<List<SelectFoodData>> {

    private PopupFoodAdapter adapter;
    private PopupWindow popupWindow;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private RadioButton radioButton;
    private  String selectedRadio="Breakfast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food);
        findViewById(R.id.search_recycle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=(TextView)findViewById(R.id.title);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        SearchView simpleSearchView = (SearchView) findViewById(R.id.search_view); // inititate a search view
        simpleSearchView.setIconifiedByDefault(false);
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query))
                    getData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do something when text changes
                return false;
            }
        });
        getData("");
        findViewById(R.id.addFood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                Fragment frag = manager.findFragmentByTag("fragment_edit_name");
                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }
                Bundle bundle = new Bundle();
                String fType = selectedRadio;
                bundle.putString("fType", fType );
                AddFoodPopupFragment editNameDialog = new AddFoodPopupFragment();
                editNameDialog.setArguments(bundle);
                editNameDialog.show(manager, "fragment_edit_name");
                //close the popup window on button click

            }
        });

        RadioGroup rg = (RadioGroup) findViewById(R.id.rFoodType);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(RadioGroup group, int checkedId)
                                                  {
                                                      radioButton = (RadioButton) findViewById(checkedId);
                                                      selectedRadio = radioButton.getText().toString();
                                                     // Toast.makeText(getBaseContext(),radioButton.getText(), Toast.LENGTH_SHORT).show();
                                                      //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                                                  }
                                              }
        );
    }

    private void getData(String query) {

        APIClient.startQuery().doGetRecipies(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SelectFoodActivity.this);

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
    public void onNext(@NonNull List<SelectFoodData> data) {
        if (data != null && data.size() > 0) {
            if (adapter == null) {
                ((RecyclerView) findViewById(R.id.search_recycle)).setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));

                adapter = new PopupFoodAdapter(this, data, new PopupFoodAdapter.OnItemClicked() {
                    @Override
                    public void onItemClick(SelectFoodData data) {

                        RadioGroup rg = (RadioGroup) findViewById(R.id.rFoodType);
                        String value = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                        Intent intent = new Intent();
                        intent.putExtra("data", data);
                        intent.putExtra("foodType", value);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
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
