package com.thedeveloperworldisyours.carouselviewpager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegMob extends AppCompatActivity {

    ImageView backButton;
    TextView tv;
    Button submit,cancel;
    EditText mob;
    String mobValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_mob);
        submit=(Button)findViewById(R.id.submit_mob);
        cancel=(Button)findViewById(R.id.cancel_mob);
        tv=(TextView)findViewById(R.id.toolberdashboard);
        mob=(EditText)findViewById(R.id.mobReg);
        


        String title= getIntent().getStringExtra("tooltitle").toString().trim();
        tv.setText(title);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobValue=mob.getText().toString().trim();
                setMobNo(RegMob.this,mobValue);
                Log.d("mob",mobValue);
                Toast.makeText(getApplicationContext(),getMobNo(getApplicationContext()),Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        backButton=(ImageView)findViewById(R.id.iv_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);*/
                onBackPressed();
            }
        });
    }

    private void setMobNo(Context context, String mobValue) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("RegMobNo",mobValue);
        editor.commit();
    }

    private String getMobNo(Context context) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        return prefs.getString("RegMobNo","");

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
