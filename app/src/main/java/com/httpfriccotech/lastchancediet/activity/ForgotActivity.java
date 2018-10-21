package com.httpfriccotech.lastchancediet.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.httpfriccotech.lastchancediet.R;

public class ForgotActivity extends AppCompatActivity {
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
                if (textViewEmailid.length() <= 0 ) {
                    Toast.makeText(ForgotActivity.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendEmail(textViewEmailid.getText().toString());
            }
        });

    }
    protected void sendEmail(String emailid) {
       // Log.i("Send email", "");
        String[] TO = {"rajnish1018@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Forgot Password");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your Password is: HelloPass");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
           // Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ForgotActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

}
}
