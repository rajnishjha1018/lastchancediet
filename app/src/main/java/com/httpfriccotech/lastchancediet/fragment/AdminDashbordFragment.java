package com.httpfriccotech.lastchancediet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.adapters.UserListAdapterForAdmin;
import com.httpfriccotech.lastchancediet.base.BaseFragment;
import com.httpfriccotech.lastchancediet.model.UserList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manish on 27-06-2017.
 */

public class AdminDashbordFragment extends BaseFragment implements  View.OnClickListener {

    private View rootView;
    private RecyclerView userListRecyclerView;
    private RecyclerView partnerListRecyclerView;
    List<UserList> userLists;
    List<UserList> partnerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        setUserAdapter();
        setPartnerAdapter();
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

}
