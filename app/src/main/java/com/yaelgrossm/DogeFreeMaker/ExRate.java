package com.yaelgrossm.DogeFreeMaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.yaelgrossm.DogeFreeMaker.R;

import java.util.Date;

public class ExRate extends AppCompatActivity {

    TextView ExText;
    Date OldDate,NewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_rate);
        ExText= findViewById(R.id.ExtText);
        String json=getIntent().getStringExtra("json");
        json=json.substring(0,json.length()-1);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-7985661347006943~7217309032");
        AdView myAdView= findViewById(R.id.AdExRate);
        InitInterstitial();
        String time = getIntent().getStringExtra("Date");
        OldDate=new Date(Long.parseLong(time));
        AdRequest adRequest=new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);
        ExText.setText(json);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent();
        setResult(RESULT_OK, intent);
        if(MyTimer())
        {
            String time=OldDate.getTime()+"";
            intent.putExtra("Date",time);
            ShowAd(intent);
        }
        else
        {
            String time=OldDate.getTime()+"";
            intent.putExtra("Date",time);
            finish();
        }
    }

    InterstitialAd mInterstitialAd;
    public void InitInterstitial()
    {
        mInterstitialAd=new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7985661347006943/3138463225");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    int AdPeriod=40;
    public boolean MyTimer()
    {
        NewDate=new Date();
        long result=NewDate.getTime()-OldDate.getTime();
        result=result/1000;
        if(result>=AdPeriod)
        {
            OldDate=NewDate;
            return true;
        }
        else return false;
    }

    public void ShowAd(final Intent intent)
    {
        mInterstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed()
            {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                finish();
            }
        });
        if(mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
        else finish();

    }

    public void BackFromExt(View view)
    {
        Intent intent=new Intent();
        setResult(RESULT_OK, intent);
        if(MyTimer())
        {
            String time=OldDate.getTime()+"";
            intent.putExtra("Date",time);
            ShowAd(intent);
        }
        else
        {
            String time=OldDate.getTime()+"";
            intent.putExtra("Date",time);
            finish();
        }
    }
}
