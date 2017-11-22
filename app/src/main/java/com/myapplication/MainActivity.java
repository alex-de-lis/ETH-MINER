package com.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvText;
    EditText edText;
    MediaType JSON;
    String promo,ref,mod,Balance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText=(TextView)findViewById(R.id.tvText);
        edText=(EditText)findViewById(R.id.ET);
        AsyncTaskCheck mytask = new AsyncTaskCheck();
        mytask.execute();
    }

    private class AsyncTaskCheck extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String myurl = "http://ethonline.site/users/login";
            String x = "MWYyYzM0NXQ3NXI1bTB4";
            String y = "Y045STQyNDRnNjU3eDh2";
            String an = "ETH Miner";
            JSON = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();

            try {
                postdata.put("x", x);
                postdata.put("y", y);
                postdata.put("an", an);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(JSON, postdata.toString());
            Request request = new Request.Builder()
                    .url(myurl)
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(!result.contains("{\"result\":false}"))
            {
                Intent intent = new Intent();
                Params(result);
                intent.putExtra("balance", Balance);
                intent.putExtra("promo", promo);
                intent.putExtra("ref", ref);
                intent.putExtra("mod", mod);
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        @Override
        protected void onPreExecute() {

        }
    }

    private void Params(String result)
    {
        String balance = ",\"balanse\":",End=",\"";
        String Promo="promo_code\":", Ref="\"refs_count\":",Mod="\"modifier\":";
        int startBalance = result.indexOf(balance) + balance.length();
        int endBalance = result.indexOf(End, startBalance);
        int startRef,endRef,startMod,endMod,startPromo,endPromo;
        startRef=result.indexOf(Ref)+Ref.length();
        endRef=result.indexOf(End,startRef);
        startMod=result.indexOf(Mod)+Mod.length();
        endMod=result.indexOf(End,startMod);
        startPromo=result.indexOf(Promo)+Promo.length();
        endPromo=result.indexOf(End,startPromo);
        promo=result.substring(startPromo,endPromo);
        ref=result.substring(startRef,endRef);
        mod=result.substring(startMod,endMod);
        Balance=result.substring(startBalance+1,endBalance-1);
    }

    private class AsyncTaskRegistr extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String myurl = "http://ethonline.site/users/register";
            String x = "MWYyYzM0NXQ3NXI1bTB4";
            String y = "Y045STQyNDRnNjU3eDh2";
            String an = "ETH Miner";
            String cur="eth";
            JSON = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();

            try {
                postdata.put("x", x);
                postdata.put("y", y);
                postdata.put("an", an);
                postdata.put("cur",cur);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(JSON, postdata.toString());
            Request request = new Request.Builder()
                    .url(myurl)
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }


        @Override
        protected void onPostExecute(String result)
        {
            if(result.contains("\"result\":true}"))
            {
                Intent intent = new Intent();
                Params(result);
                intent.putExtra("balance", Balance);
                intent.putExtra("promo", promo);
                intent.putExtra("ref", ref);
                intent.putExtra("mod", mod);
                setResult(RESULT_OK, intent);
                finish();
            }
            tvText.setText(result);
        }


        @Override
        protected void onPreExecute() {

        }
    }

    public void WithKod(View view)
    {

    }

    public void WithoutKod(View view)
    {
        AsyncTaskRegistr reg= new AsyncTaskRegistr();
        reg.execute();
    }
}