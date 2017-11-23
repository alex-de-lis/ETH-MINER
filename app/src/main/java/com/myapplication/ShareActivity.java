package com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    TextView Promo, Activ,Mods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Promo=(TextView)findViewById(R.id.YourPromo);
        Activ=(TextView)findViewById(R.id.ActivatedApp);
        Mods=(TextView)findViewById(R.id.Coeff);
        Promo.setText(R.string.YourPromo);
        String promoText= Promo.getText().toString()+getIntent().getStringExtra("Promo");
        Promo.setText(promoText);
        Activ.setText(R.string.Activated);
        String activText= Activ.getText().toString()+getIntent().getStringExtra("Ref");
        Activ.setText(activText);
        Mods.setText(R.string.Coeff);
        String modText= Mods.getText().toString()+getIntent().getStringExtra("Mod");
        Mods.setText(modText);
    }

    public void BackToMain(View view)
    {
        finish();
    }

    public void SendMyPromo(View view)
    {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String textToSend=getResources().getString(R.string.Text_to_Share)+" "+getIntent().getStringExtra("Promo");
        intent.putExtra(Intent.EXTRA_TEXT, textToSend);
        try
        {
            startActivity(Intent.createChooser(intent, "Описание действия"));
        }
        catch (android.content.ActivityNotFoundException ex)
        {

        }
    }
}
