package com.httpfriccotech.lastchancediet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.adapters.PartnerListAdapterForAdmin;
import com.httpfriccotech.lastchancediet.adapters.UserListAdapterForAdmin;
import com.httpfriccotech.lastchancediet.base.BaseFragment;
import com.httpfriccotech.lastchancediet.model.AdminDashBordModel;
import com.httpfriccotech.lastchancediet.model.DataList;
import com.httpfriccotech.lastchancediet.model.UserListModel;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PartnerDashBord extends BaseFragment implements  View.OnClickListener , Observer<Object> {

    private View rootView;
    private RecyclerView userListRecyclerView;
    private RecyclerView partnerListRecyclerView;
    List<UserListModel> userLists;
    List<UserListModel> partnerList;
    TextView totalUserCountTv,partenerCountTV,newUserCountTv,userLeftCount;
    private UserListAdapterForAdmin userListAdapterForAdmin;
    private PartnerListAdapterForAdmin partnerListAdapterForAdmin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(
                R.layout.fragment_dashbord_partner, container, false);
        userLists=new ArrayList<UserListModel>();
        userListRecyclerView=(RecyclerView) rootView.findViewById(R.id.recycler_user_list);
        newUserCountTv=(TextView)rootView.findViewById(R.id.tv_total_user_new);
        totalUserCountTv=(TextView)rootView.findViewById(R.id.tv_total_user);
        partenerCountTV=(TextView)rootView.findViewById(R.id.tv_partner_count);
        userLeftCount=(TextView)rootView.findViewById(R.id.tv_user_count_left);
        setUserAdapter();
        getData();
        return rootView;
    }

    private void getData() {
        APIClient.startQuery().getPartnerDashBord(SharedPref.getUserId(getActivity())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

    }

    private void setUserAdapter() {
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        userListAdapterForAdmin=new UserListAdapterForAdmin(getActivity(),userLists);
        userListRecyclerView.setAdapter(userListAdapterForAdmin);
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
        if (!isVisible())
            return;
        if (o instanceof AdminDashBordModel){
            AdminDashBordModel adminDashBordModel=(AdminDashBordModel)o;
            if (adminDashBordModel.isSuccess()){
                DataList dataList=adminDashBordModel.getData();
                totalUserCountTv.setText(dataList.getCountList().getTOTALUSERS());
                partenerCountTV.setText(dataList.getCountList().getPARTNER());
                newUserCountTv.setText(dataList.getCountList().getNEWUSERS());
                userLeftCount.setText(dataList.getCountList().getUSERSLEFT());
                if (dataList.getUserList().size()>0){
                    if (userListAdapterForAdmin!=null){
                        userListAdapterForAdmin.updateData(dataList.getUserList());
                    }
                }
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