package com.httpfriccotech.lastchancediet.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.httpfriccotech.lastchancediet.R;

/**
 * Created by Manish on 15/09/15.
 */
public class BaseActivity extends AppCompatActivity {
    private static boolean isForeground = false;
    private ProgressBar mProgressBar;
    public static String appReferrerId;
    public static int showCountInterstitialImageAndVideo = -1;
    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener myPrefListner;

    public static boolean isForeground() {
        return isForeground;
    }

    public void addFragment(BaseFragment fragment, int container) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (getFragmentInAnimation() != 0 && getFragmentOutAnimation() != 0) {
            fragmentTransaction.setCustomAnimations(getFragmentInAnimation(), getFragmentOutAnimation(),
                    getFragmentInAnimation(), getFragmentOutAnimation());
        }
        fragmentTransaction.add(container, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void addFragment(BaseFragment fragment) {
       addFragment(fragment, R.id.frame_container);
    }

    public void replaceFragment(BaseFragment fragment, int container) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (getFragmentInAnimation() != 0 && getFragmentOutAnimation() != 0) {
            fragmentTransaction.setCustomAnimations(getFragmentInAnimation(), getFragmentOutAnimation(),
                    getFragmentInAnimation(), getFragmentOutAnimation());
        }
        fragmentTransaction.replace(container, fragment, fragment.getClass().getName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void showFragment(BaseFragment fragment) {
        try {
            //fragment.show(getSupportFragmentManager(), fragment.getClass().getName());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!isForeground) { // http://stackoverflow.com/a/10261438/1909934
                fragmentTransaction.commitAllowingStateLoss();
            }
            fragment.show(fragmentTransaction, fragment.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment(BaseFragment fragment) {
        replaceFragment(fragment, R.id.frame_container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
//        prefs.registerOnSharedPreferenceChangeListener(myPrefListner);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //appReferrerId="";
//        prefs.unregisterOnSharedPreferenceChangeListener(myPrefListner);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    protected void onNetworkChange(NetworkInfo.State networkState) {

    }





    public void showLoader() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
    // Permission Implementation

    public void hideLoader() {
        mProgressBar.setVisibility(View.GONE);
        /*if (null!=progressDialog && progressDialog.isShowing()){
            progressDialog.cancel();
        }*/
    }

    protected int getFragmentInAnimation() {
        return 0;
    }

    protected int getFragmentOutAnimation() {
        return 0;
    }


    /**
     * @param permissions
     * @param grantResults
     * @return false, if any permission is not granted in the provided results array.
     */
    public boolean isPermitted(String[] permissions, int[] grantResults) {
        boolean havePermission;
        for (int i = 0, permissionsLength = permissions.length; i < permissionsLength; i++) {
            String permission = permissions[i];
            havePermission = (grantResults[i] == PackageManager.PERMISSION_GRANTED);
            if (!havePermission)
                return false;
        }

        return true;
    }
    public class NetworkListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                NetworkInfo networkInfo = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (networkInfo != null) {
                    try {
                        onNetworkChange(networkInfo.getState());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
