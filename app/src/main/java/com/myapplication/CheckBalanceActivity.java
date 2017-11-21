package com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class CheckBalanceActivity extends AppCompatActivity {

    String mFileName="data.txt";
    TextView Balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);
        Balance = (TextView)findViewById(R.id.TextBalance);
        read();
    }

    private void read()
    {

        FileInputStream stream = null;
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            stream = openFileInput(mFileName);

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } finally {
                stream.close();
            }

             Balance.setText(sb.toString()+" eth");

        } catch (Exception e)
        {

        }
    }
}
