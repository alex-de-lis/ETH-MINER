package com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class CheckBalanceActivity extends AppCompatActivity {

    TextView Balance;
    String Rate, YourBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);
        Balance = (TextView)findViewById(R.id.TextBalance);
        Rate=getIntent().getStringExtra("json");
        YourBalance=getIntent().getStringExtra("balance")+" DOGE";
        Balance.setText(YourBalance);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-7985661347006943~7217309032");
        AdView myAdView=(AdView)findViewById(R.id.AdBalance);
        AdRequest adRequest=new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);
    }

    public void BackFromBalance(View view)
    {
        finish();
    }

    public void TodayRate(View view)
    {
        Intent intent = new Intent(CheckBalanceActivity.this,Your_rate.class);
        intent.putExtra("json",Rate);
        intent.putExtra("balance",YourBalance);
        startActivity(intent);
    }

}
