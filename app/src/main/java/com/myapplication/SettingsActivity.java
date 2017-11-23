package com.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingsActivity extends AppCompatActivity {

    Button SaveBtn,BackBtn;
    TextView ETH_Adr,Your_Money,Int_Lang;
    EditText Wallet;
    MediaType JSON;
    String x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final Spinner spinner = (Spinner) findViewById(R.id.Language);
        SaveBtn=(Button) findViewById(R.id.SaveBtn);
        BackBtn=(Button)findViewById(R.id.BackBtn);
        Wallet=(EditText)findViewById(R.id.Adress);
        ETH_Adr=(TextView) findViewById(R.id.YourEthAdress);
        Your_Money=(TextView) findViewById(R.id.SetMes);
        Int_Lang=(TextView) findViewById(R.id.InterfaceLang);
        String language=getIntent().getStringExtra("Lang");
        x=getIntent().getStringExtra("x");
        y=getIntent().getStringExtra("y");
        if(language.equals("ru")) spinner.setSelection(0);
        else spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId)
            {
                String Lang=spinner.getSelectedItem().toString();
                if(Lang.equals("Русский")) Lang="ru";
                else Lang="en";
                Locale myLocale = new Locale(Lang);
                Locale.setDefault(myLocale);
                android.content.res.Configuration config = new android.content.res.Configuration();
                config.locale = myLocale;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                Intent intent = new Intent();
                intent.putExtra("Lang", Lang);
                setResult(RESULT_OK, intent);
                SaveBtn.setText((getResources().getString(R.string.Save)));
                BackBtn.setText((getResources().getString(R.string.Back)));
                ETH_Adr.setText((getResources().getString(R.string.Your_Adress)));
                Your_Money.setText((getResources().getString(R.string.Your_Money)));
                Int_Lang.setText((getResources().getString(R.string.Choose_Int_Lang)));
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void Save(View view)
    {
        AsyncTaskForMining myTask = new AsyncTaskForMining();
        myTask.execute();
    }

    public void BackFromSettings(View view)
    {
        finish();
    }

    private class AsyncTaskForMining extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String myurl = "http://ethonline.site/users/wallet";
            String an = "ETH Miner";
            String w=Wallet.getText().toString();
            JSON = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();

            try {
                postdata.put("x", x);
                postdata.put("y", y);
                postdata.put("an", an);
                postdata.put("w", w);
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
            if(result.contains("true")) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.Success, Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.Fail, Toast.LENGTH_SHORT);
                toast.show();
            }
        }


        @Override
        protected void onPreExecute() {

        }
    }
}
