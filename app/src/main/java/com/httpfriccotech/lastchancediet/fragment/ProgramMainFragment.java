package com.httpfriccotech.lastchancediet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.base.BaseFragment;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.program.ProgramData;
import com.httpfriccotech.lastchancediet.program.ProgramListAdapter;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by manish on 27-06-2017.
 */

public class ProgramMainFragment extends BaseFragment implements Observer<Object>,View.OnClickListener {

    private View rootView;
    private RecyclerView recyclerView;
    private RelativeLayout progressLayout;
    private List<ProgramData> itemList;
    private ProgramListAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(
                R.layout.fragment_program, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressLayout=(RelativeLayout)rootView.findViewById(R.id.progressLayout);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler);
        itemList=new ArrayList<ProgramData>();
        getData();
        showProgress();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
    private void getData() {
        APIClient.startQuery().getProgramList(SharedPref.getUserId(getActivity())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object data) {
        if (!isVisible())
            return;
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
                myAdapter = new ProgramListAdapter(getActivity(), itemList);
                RecyclerView recycle = ((RecyclerView) rootView.findViewById(R.id.recycleview));
                recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycle.setAdapter(myAdapter);
            }else{
                Toast.makeText(getContext(),jsonObject.get("message").getAsString(),Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onError(Throwable e) {
        hideProgress();
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete() {
        hideProgress();
    }
    private void showProgress(){
        if (progressLayout!=null)
            progressLayout.setVisibility(View.VISIBLE);
    }
    private void hideProgress(){
        if (progressLayout!=null)
            progressLayout.setVisibility(View.GONE);
    }

}
