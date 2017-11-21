package com.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainScreen extends AppCompatActivity {

    GraphView graph;
    MediaType JSON;
    Button btn;
    TextView Mytest;
    String Test="";
    Timer timer;
    int IntTest=0;
    int onClick=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        graph = (GraphView) findViewById(R.id.MyGraph);
        btn =(Button) findViewById(R.id.PlayBtn);
        Mytest=(TextView)findViewById(R.id.TestText);
        AsyncTaskForGraph task = new AsyncTaskForGraph();
        task.execute();
    }

    public void MiningActivity(View view)
    {
        if(onClick==0) {
            onClick++;
            timer = new Timer();
            timer.scheduleAtFixedRate(new MyTask(), 0, 5 * 1000);
            Mytest.setText(Test);
            btn.setText("Стоп");
        }
        else
        {
            onClick=0;
            timer.cancel();
            Mytest.setText(Test);
            IntTest=0;
            Test="";
            btn.setText("Начать");
        }

    }

    public void CheckBalance(View view)
    {
        Intent intent = new Intent(MainScreen.this,CheckBalanceActivity.class);
        startActivity(intent);
    }

    private class AsyncTaskForGraph extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String myurl = "https://min-api.cryptocompare.com/data/histoday?fsym=ETH&tsym=USD&limit=30";
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(myurl)
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
            StringChange(result);
        }


        @Override
        protected void onPreExecute() {

        }

        private Date StringFormat(String time) {
            Date dt = new Date(Long.parseLong(time + "000"));
            return dt;
        }

        private void StringChange(String json) {
            String mas[][] = new String[2][32];
            int i = 0;
            Date FTime;
            String time = "{\"time\":", Sep = ",\"", close = "\"close\":";
            int startTime = json.indexOf(time) + time.length();
            int endTime = json.indexOf(Sep, startTime);
            int startClose = json.indexOf(close) + close.length();
            int endClose = json.indexOf(Sep, startClose);

            mas[0][0] = json.substring(startTime, endTime);
            Date dt = new Date(Long.parseLong(mas[0][0] + "000"));
            mas[1][0] = json.substring(startClose, endClose);

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(dt, Float.parseFloat(mas[1][0]))});
            while (i != 30) {
                startTime = json.indexOf(time, startTime) + time.length();
                endTime = json.indexOf(Sep, startTime);
                startClose = json.indexOf(close, endClose) + close.length();
                endClose = json.indexOf(Sep, startClose);
                i++;
                mas[0][i] = json.substring(startTime, endTime);
                FTime = StringFormat(mas[0][i]);
                mas[1][i] = json.substring(startClose, endClose);
                DataPoint dp = new DataPoint(FTime, Float.parseFloat(mas[1][i]));

                series.appendData(dp, true, 31);
            }
            series.setDrawDataPoints(true);
            graph.addSeries(series);
            graph.getViewport().setScalable(true);
            graph.getViewport().setScrollable(true);

            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        // show normal x values
                        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM");
                        return format1.format(value);
                    } else {
                        // show currency for y values
                        return super.formatLabel(value, isValueX);
                    }
                }
            });
        }

    }

    private class AsyncTaskForMining extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String myurl = "http://ethonline.site/users/activity";
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
            String s=result;
            s="";
        }


        @Override
        protected void onPreExecute() {

        }
    }

    private class MyTask extends TimerTask
    {
        public void run()
        {
            //AsyncTaskForMining AT= new AsyncTaskForMining();
            //AT.execute();
            IntTest++;
            Test=Test+" "+IntTest;
        }
    }
}
