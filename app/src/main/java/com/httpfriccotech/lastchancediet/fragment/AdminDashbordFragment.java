package com.httpfriccotech.lastchancediet.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.adapters.UserListAdapterForAdmin;
import com.httpfriccotech.lastchancediet.base.BaseFragment;
import com.httpfriccotech.lastchancediet.model.UserList;
import com.httpfriccotech.lastchancediet.network.APIClient;
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

public class AdminDashbordFragment extends BaseFragment implements  View.OnClickListener , Observer<Object> {

    private View rootView;
    private RecyclerView userListRecyclerView;
    private RecyclerView partnerListRecyclerView;
    List<UserList> userLists;
    List<UserList> partnerList;
    TextView totalUserCountTv,partenerCountTV,newUserCountTv,userLeftCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(
                R.layout.fragment_dashbord_admin, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userLists=new ArrayList<UserList>();
        partnerList=new ArrayList<UserList>();
        userListRecyclerView=(RecyclerView) rootView.findViewById(R.id.recycler_user_list);
        partnerListRecyclerView=(RecyclerView) rootView.findViewById(R.id.recycler_partner_list);
        newUserCountTv=(TextView)rootView.findViewById(R.id.tv_total_user_new);
        totalUserCountTv=(TextView)rootView.findViewById(R.id.tv_total_user);
        partenerCountTV=(TextView)rootView.findViewById(R.id.tv_partner_count);
        userLeftCount=(TextView)rootView.findViewById(R.id.tv_user_count_left);
        setUserAdapter();
        setPartnerAdapter();
        getData();
    }

    private void getData() {
        APIClient.startQuery().getAdminDashBord(SharedPref.getUserId(getActivity())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

    }

    private void setPartnerAdapter() {
        partnerListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        UserListAdapterForAdmin recepiesAdaper=new UserListAdapterForAdmin(getActivity(),partnerList);
        partnerListRecyclerView.setAdapter(recepiesAdaper);
    }

    private void setUserAdapter() {
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        UserListAdapterForAdmin recepiesAdaper=new UserListAdapterForAdmin(getActivity(),userLists);
        userListRecyclerView.setAdapter(recepiesAdaper);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        if (o instanceof JsonObject){
            JsonObject jsonObject=(JsonObject)o;
            if (jsonObject.get("success").getAsBoolean()){
                JsonObject data=jsonObject.get("data").getAsJsonObject();
                JsonObject count=data.get("CountList").getAsJsonObject();
                totalUserCountTv.setText(count.get("TOTALUSERS").getAsString());
                partenerCountTV.setText(count.get("PARTNER").getAsString());
                newUserCountTv.setText(count.get("NEWUSERS").getAsString());
                userLeftCount.setText(count.get("USERSLEFT").getAsString());
            }
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
