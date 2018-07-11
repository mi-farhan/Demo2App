package com.thedeveloperworldisyours.carouselviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TrackAndRecover extends AppCompatActivity {
    ImageView backButton,settings;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_and_recover);

        tv=(TextView)findViewById(R.id.toolberdashboard);
        final String title=tv.getText().toString().trim();

        backButton=(ImageView)findViewById(R.id.iv_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);*/

                onBackPressed();
            }
        });

        settings=(ImageView)findViewById(R.id.esMenu);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TrackAndRecover.this,RegMob.class);
                intent.putExtra("tooltitle",title);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
