package com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Your_rate extends AppCompatActivity {

    TextView YourRateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_rate);
        YourRateText=(TextView)findViewById(R.id.YourRateText);
        String Rate=getIntent().getStringExtra("json");
        String TextBalance=getIntent().getStringExtra("balance");
        TextBalance=TextBalance.substring(0,TextBalance.indexOf("DOGE"));
        float balance=Float.parseFloat(TextBalance);
        YourRateText.setText(Parse(Rate,balance));
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-7985661347006943~7217309032");
        AdView myAdView=(AdView)findViewById(R.id.AdYourRate);
        AdRequest adRequest=new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);
    }

    private String Parse(String json, float balance)
    {
        float factor;
        String usd="USD:",eur="EUR:",gbp="GBP:",chf="CHF:",cny="CNY:",jpy="JPY:";
        String result="";
        factor=Find(json,usd);
        result+=usd+" "+ToString(factor,balance)+"\n";
        factor=Find(json,eur);
        result+=eur+" "+ToString(factor,balance)+"\n";
        factor=Find(json,gbp);
        result+=gbp+" "+ToString(factor,balance)+"\n";
        factor=Find(json,chf);
        result+=chf+" "+ToString(factor,balance)+"\n";
        factor=Find(json,cny);
        result+=cny+" "+ToString(factor,balance)+"\n";
        factor=Find(json,jpy);
        result+=jpy+" "+ToString(factor,balance)+"\n";
        return result;
    }

    private String ToString(float factor, float balance)
    {
        String StringRes=String.format("%.2f", balance*factor);
        StringRes=StringRes.replace(',','.');
        return StringRes;
    }

    private float Find(String json,String Cur)
    {
        int start=json.indexOf(Cur)+Cur.length();
        int end=json.indexOf("\n",start);
        return Float.parseFloat(json.substring(start,end));
    }

    public void BackFromYourRate (View view)
    {
        finish();
    }
}
