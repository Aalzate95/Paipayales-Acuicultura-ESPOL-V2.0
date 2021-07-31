package com.paipayales.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DataDetail extends AppCompatActivity {


    Intent intent;
    Bundle extras;

    String name;
    String[] ox;
    String[] ph;
    String[] temp;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);

        intent =getIntent();
        extras=intent.getExtras();
        if(extras != null){
            name = extras.getString("name");
            ph = extras.getStringArray("ph");
            ox = extras.getStringArray("ox");
            temp = extras.getStringArray("temp");

            date = extras.getString("date");




            ((TextView)findViewById(R.id.data_ox)).setText(ox[0]+" mg/l");

            ((TextView)findViewById(R.id.data_ph)).setText(ph[0]);

            ((TextView)findViewById(R.id.data_temp)).setText(temp[0]+" °C");

            long seconds = Long.parseLong(date);
            long millis = seconds * 1000;

            Date date = new Date(millis);
            //SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm",Locale.ENGLISH);

            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            String formattedDate = sdf.format(date);

            ((TextView)findViewById(R.id.data_date)).setText(formattedDate);


            if(!ox[1].equals("OK")){
                ((TextView)findViewById(R.id.data_ox)).setBackgroundColor(ContextCompat.getColor(this, R.color.alert));
            }

            if(!ph[1].equals("OK")){
                ((TextView)findViewById(R.id.data_ph)).setBackgroundColor(ContextCompat.getColor(this, R.color.alert));
            }

            if(!temp[1].equals("OK")){
                ((TextView)findViewById(R.id.data_temp)).setBackgroundColor(ContextCompat.getColor(this, R.color.alert));
            }


        }

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(name);
        }



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}