package com.thedeveloperworldisyours.carouselviewpager;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ExtraSecure extends AppCompatActivity {
    ImageView backButton,menu;
    TextView tv;
    Switch alarm,lockDevice,deleteContacts,deleteMessages,deleteGallery,resetFactory;
    AudioManager am;
    private static final int REQUEST_CODE_ENABLE_ADMIN =1234 ;
    Vibrator v;
    private ComponentName componentName;
    private DevicePolicyManager policyManager;
    public static final int ADMIN_INTENT=1;
    Context ctx=this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_secure);

        policyManager=(DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName=new ComponentName(this,Pattern.class);



        Log.d("Sms Activity","Started");
        v=(Vibrator)getSystemService(getApplicationContext().VIBRATOR_SERVICE);


        if (isPermissionGranted()){
                readSms();
        }else {
            isPermissionGranted();
        }

        final MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.go_fast);
        mp.setLooping(true);

        alarm=(Switch)findViewById(R.id.alarmSwitch);
        lockDevice=(Switch)findViewById(R.id.lockSwitch);
        deleteContacts=(Switch)findViewById(R.id.contactSwitch);
        deleteMessages=(Switch)findViewById(R.id.messageSwitch);
        deleteGallery=(Switch)findViewById(R.id.gallerySwitch);
        resetFactory=(Switch)findViewById(R.id.factorySwitch);

        if (policyManager != null && policyManager.isAdminActive(componentName)){
            lockDevice.setChecked(true);
            Log.d("From policy","");
        }
        /*alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                *//*if (isChecked){
                    am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    mp.start();
                }else{
                    mp.stop();
                }*//*

                if ((alarm).isChecked()){
                    Intent intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"Administrator description");
                    startActivityForResult(intent,ADMIN_INTENT);
                }else{
                    policyManager.removeActiveAdmin(componentName);
                }

            }
        });*/

        if (getAlarm(getApplicationContext()).equals("on")){
            alarm.setChecked(true);
        }else{
            alarm.setChecked(false);
        }

        if (getLock(getApplicationContext()).equals("on")){
            lockDevice.setChecked(true);
        }else{
            lockDevice.setChecked(false);
        }

        if (getDeleteContacts(getApplicationContext()).equals("on")){
            deleteContacts.setChecked(true);
        }else {
            deleteContacts.setChecked(false);
        }

        alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                /*if (isChecked){
                    am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    mp.start();
                }else{
                    mp.stop();
                }*/


                if (isChecked){
                    setAlarm(getApplicationContext(),"on");
                }else{
                    setAlarm(getApplicationContext(),"off");
                }


            }
        });

        lockDevice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if ((lockDevice).isChecked()){
                    Intent intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"Administrator description");
                    startActivityForResult(intent,ADMIN_INTENT);
                }else{
                    policyManager.removeActiveAdmin(componentName);
                }

                if (isChecked){
                    setLock(getApplicationContext(),"on");
                }else{
                    setLock(getApplicationContext(),"off");
                }
            }
        });

        deleteContacts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setDeleteContacts(getApplicationContext(),"on");
                }else{
                    setDeleteContacts(getApplicationContext(),"off");
                }
            }
        });



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

        menu=(ImageView)findViewById(R.id.esMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ExtraSecure.this,RegMob.class);
                intent.putExtra("tooltitle",title);
                startActivity(intent);
            }
        });


    }

    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_CONTACTS)==PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.READ_PHONE_STATE)==PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                int PERMISSION_ALL = 1;
                String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_SMS};

                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                Log.v("TAG","Permission is revoked");
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==ADMIN_INTENT){
            Log.d("result code",Integer.toString(requestCode));
            if (resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Registered As Admin", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "Device admin process cancelled", Toast.LENGTH_SHORT).show();
                alarm.setChecked(false);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    readSms();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        try {
            Intent i=getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.thedeveloperworldisyours.carouselviewpager");
            getApplicationContext().startActivity(i);
            Log.d("Ondestroy","Called");
        }catch (Exception r){

        }
        super.onDestroy();

    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    private void readSms() {

    }

    private String getAlarm(Context context) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        return prefs.getString("Alarm","");

    }

    private void setAlarm(Context context, String mobValue) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("Alarm",mobValue);
        editor.commit();
    }

    private void setLock(Context context, String mobValue) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("Lock",mobValue);
        editor.commit();
    }
    private String getLock(Context context) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        return prefs.getString("Lock","");

    }

    private void setDeleteContacts(Context context, String mobValue) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("deleteContacts",mobValue);
        editor.commit();
    }

    private String getDeleteContacts(Context context) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        return prefs.getString("deleteContacts","");

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
