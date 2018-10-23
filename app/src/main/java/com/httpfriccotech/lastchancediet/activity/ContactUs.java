package com.httpfriccotech.lastchancediet.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.network.APIClient;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactUs extends AppCompatActivity /*implements OnMapReadyCallback*/ implements Observer<Object>,View.OnClickListener{
    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String address;
    private String phone;
    private String email;
    private String headquarters;
    private String content;
    private String latitude;
    private String longitude;
    EditText nameET,emailET,subjectET,messageEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
//        mapView = findViewById(R.id.map_view);
//        Bundle mapViewBundle = null;
//        if (savedInstanceState != null) {
//            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
//        }
//        mapView.onCreate(mapViewBundle);
//        mapView.getMapAsync(this);
        nameET=(EditText)findViewById(R.id.et_username);
        emailET=(EditText)findViewById(R.id.et_email);
        subjectET=(EditText)findViewById(R.id.et_subject);
        messageEt=(EditText)findViewById(R.id.et_message);
        getData();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void getData() {
        APIClient.startQuery().getContactUs().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        if (o instanceof JsonObject){
            JsonObject jsonObject=(JsonObject)o;
            if (jsonObject.get("success").getAsBoolean()){
                JsonObject jsonObject1=jsonObject.get("data").getAsJsonObject();
                address=jsonObject1.get("address").getAsString();
                phone=jsonObject1.get("phone").getAsString();
                email=jsonObject1.get("email").getAsString();
                headquarters=jsonObject1.get("headquarters").getAsString();
                content=jsonObject1.get("content").getAsString();
                latitude=jsonObject1.get("latitude").getAsString();
                longitude=jsonObject1.get("longitude").getAsString();
                if (findViewById(R.id.title)!=null){
                    ((TextView)findViewById(R.id.title)).setText(headquarters);
                }if (findViewById(R.id.address)!=null){
                    ((TextView)findViewById(R.id.address)).setText(address+"\n \n"+email+"\n"+phone);
                }
            }else{
                Toast.makeText(getBaseContext(), jsonObject.get("message").getAsString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tv_submit){
            if (TextUtils.isEmpty(nameET.getText().toString())
                    || TextUtils.isEmpty(emailET.getText().toString())
            || TextUtils.isEmpty(subjectET.getText().toString())
            || TextUtils.isEmpty(messageEt.getText().toString())){
                Toast.makeText(getBaseContext(),"All fields are mandatory",Toast.LENGTH_LONG).show();
            }else{
                sendEmail(emailET.getText().toString(),nameET.getText().toString(),subjectET.getText().toString(),messageEt.getText().toString());
            }
        }
    }
    private void sendEmail(String email, String name, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { this.email });
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, name+"\n"+message);
        startActivity(Intent.createChooser(intent, "Send Email"));
    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
//        if (mapViewBundle == null) {
//            mapViewBundle = new Bundle();
//            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
//        }
//
//        mapView.onSaveInstanceState(mapViewBundle);
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mapView.onStop();
//    }
//    @Override
//    protected void onPause() {
//        mapView.onPause();
//        super.onPause();
//    }
//    @Override
//    protected void onDestroy() {
//        mapView.onDestroy();
//        super.onDestroy();
//    }
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        gmap = googleMap;
//        gmap.setMinZoomPreference(12);
//        LatLng ny = new LatLng(40.7143528, -74.0059731);
//        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
//    }
}
