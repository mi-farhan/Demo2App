package com.thedeveloperworldisyours.carouselviewpager;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class AntiTheft extends AppCompatActivity {
    ImageView backButton,settings;
    Button submitPass,cancelPass;
    TextView tv;
    PopupWindow popupWindow;
    LinearLayout linearLayout;
    WindowManager windowManager2;
    WindowManager.LayoutParams params;
    Switch alarm;
    AudioManager am;
    private ComponentName componentName;
    private DevicePolicyManager policyManager;
    public static final int ADMIN_INTENT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_theft);

        policyManager=(DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName=new ComponentName(this,Pattern.class);
        am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

        alarm=(Switch)findViewById(R.id.alarmSwitch);

        if (policyManager != null && policyManager.isAdminActive(componentName)){
            alarm.setChecked(true);
            Log.d("From policy","");
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

                if ((alarm).isChecked()){
                    Intent intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"Administrator description");
                    startActivityForResult(intent,ADMIN_INTENT);
                }else{
                    policyManager.removeActiveAdmin(componentName);
                }

            }
        });




        /*linearLayout=(LinearLayout)findViewById(R.id.AntiTheftLayout);

        LayoutInflater layoutInflater=(LayoutInflater) AntiTheft.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView=layoutInflater.inflate(R.layout.pass_popup,null);
        submitPass=(Button)findViewById(R.id.submitPass);
        cancelPass=(Button)findViewById(R.id.cancelPass);

        popupWindow=new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.showAtLocation(linearLayout, Gravity.CENTER,0,0);

        cancelPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submitPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });*/


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
                Intent intent=new Intent(AntiTheft.this,RegMob.class);
                intent.putExtra("tooltitle",title);
                startActivity(intent);
            }
        });

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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
