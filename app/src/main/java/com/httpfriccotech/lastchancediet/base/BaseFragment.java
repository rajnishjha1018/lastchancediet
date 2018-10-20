package com.httpfriccotech.lastchancediet.base;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;

/**
 * Created by manish singh on 06/05/17.
 */
public class BaseFragment extends DialogFragment {

    public static final int ERROR_REQUEST_CODE = 007;
    private static final int STATUS_UNAUTH = 401;
    private static final int ID_LOGIN = 2000401;
    protected boolean isVisibleToUser;
    private Handler mHandler;
    private boolean isPaused = false;
    //NetworkChangeListener networkChangeListener;

    public BaseFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        // networkChangeListener = new NetworkChangeListener();
    }

    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void onResume() {
        super.onResume();
        // getContext().registerReceiver(networkChangeListener, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        isPaused = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isPaused = true;
        try {
            //getContext().unregisterReceiver(networkChangeListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onNetworkChange(NetworkInfo.State networkState) {
        if (networkState.equals(NetworkInfo.State.CONNECTED)) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    protected void addFragment(BaseFragment fragment) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).addFragment(fragment);
        }
    }

    protected void replaceFragment(BaseFragment fragment) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).replaceFragment(fragment);
        }
    }

    protected void showFragment(BaseFragment fragment) {
        if (getActivity() instanceof BaseActivity) {
            // ((BaseActivity) getActivity()).showFragment(fragment);
        }
    }

    protected void showChildFragment(BaseFragment fragment) {
        if (getActivity() instanceof BaseActivity) {
            fragment.show(getChildFragmentManager(), fragment.getClass().getName());
        }
    }


    public void dismiss() {
        super.dismiss();
        if (getDialog() != null) {
            getDialog().dismiss();
        }
    }


    public String getStackName() {
        return getClass().getName();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
    }

    public String getTitle() {
        return null;
    }

    protected void onNetworkChangeInfo(NetworkInfo.State networkState) {
        //Child fragment will override this method to get the network state
    }
}
