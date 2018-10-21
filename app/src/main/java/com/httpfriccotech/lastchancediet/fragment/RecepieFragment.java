package com.httpfriccotech.lastchancediet.fragment;

/**
 * Created by RAJNISH on 08/18/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.Recepies.RecepieItem;
import com.httpfriccotech.lastchancediet.adapters.RecepiesAdaper;

import java.util.ArrayList;


public class RecepieFragment extends Fragment{
    private String name;
    private int age;
    ArrayList<RecepieItem> recepieItemList;
    private TextView mNameTextView;
    private TextView mAgeTextView;

    public static RecepieFragment newInstance(ArrayList<RecepieItem> recepieList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("keyArray", recepieList);
        RecepieFragment fragment = new RecepieFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            recepieItemList = (ArrayList<RecepieItem>)getArguments().getSerializable("keyArray");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_recepies, container, false);
            readBundle(getArguments());

            RecyclerView recycle=((RecyclerView)view.findViewById(R.id.recycleview));
            recycle.setLayoutManager(new GridLayoutManager(getActivity(),2));

        RecepiesAdaper recepiesAdaper=new RecepiesAdaper(getActivity(),recepieItemList);
            recycle.setAdapter(recepiesAdaper);
        recepiesAdaper.notifyDataSetChanged();
            return view;

    }

}