package com.httpfriccotech.lastchancediet.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.R;
import com.httpfriccotech.lastchancediet.network.APIClient;
import com.httpfriccotech.lastchancediet.util.SharedPref;

import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgotActivity extends AppCompatActivity implements Observer<Object>{
    Context context;
    TextView textViewEmailid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        context = this;
        initView();

    }
    private void initView() {

        textViewEmailid = (TextView) findViewById(R.id.et_email);

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValidEmailId(textViewEmailid.getText().toString())) {
                    Toast.makeText(ForgotActivity.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }if (textViewEmailid.getText().toString().isEmpty()){
                    Toast.makeText(ForgotActivity.this, "Email address can't be Empty", Toast.LENGTH_SHORT).show();

                }
                sendEmail(textViewEmailid.getText().toString());
            }
        });

    }
    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    protected void sendEmail(String emailid) {
        APIClient.startQuery().resetPass(emailid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        if (o instanceof JsonObject){
            JsonObject jsonObject=(JsonObject)o;
            if(jsonObject.has("success")){
                if (jsonObject.get("success").getAsBoolean()){
                    if (jsonObject.has("message")){
                        Toast.makeText(getBaseContext(),jsonObject.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                        finish();
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
