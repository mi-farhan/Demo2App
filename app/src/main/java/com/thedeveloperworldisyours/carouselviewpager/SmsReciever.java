package com.thedeveloperworldisyours.carouselviewpager;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;

import static android.content.Context.DEVICE_POLICY_SERVICE;

/**
 * Created by geniec3 on 19/6/18.
 */

public class SmsReciever extends BroadcastReceiver {
    private static SmsListener smsListener;
    public static final String lock="DEVICE";
    public static final String deleteContacts="DELETE CONTACTS";
    public static final String alarm="ALARM START";
    DevicePolicyManager policyManager;
    ComponentName mDeviceAdmin;
    int currentAPIVersion;
    Vibrator v;


    @Override
    public void onReceive(Context context, Intent intent) {

        currentAPIVersion = Build.VERSION.SDK_INT;

        final MediaPlayer mp=MediaPlayer.create(context,R.raw.go_fast);
        mp.setLooping(true);
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        //int maxVolumeMusic = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        mp.setVolume(100, 100);

        policyManager=(DevicePolicyManager)context.getSystemService(DEVICE_POLICY_SERVICE);
        mDeviceAdmin=new ComponentName(context,WipeDataRec.class);

        v=(Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);

        Log.d("Method","OnReceive");
        Bundle data=intent.getExtras();
        Object[] pdus=(Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String messageText=smsMessage.getMessageBody();
            String sender = smsMessage.getDisplayOriginatingAddress();
            //Check the sender to filter messages which we require to read
            Log.d("sender",sender);

            if (sender.equals(getMobNo(context))){
                Log.d("message",messageText);
                Toast.makeText(context, "msg"+messageText, Toast.LENGTH_SHORT).show();
                java.util.regex.Pattern patternAlarm= java.util.regex.Pattern.compile(alarm);
                Matcher matcherAlarm=patternAlarm.matcher(messageText);
                java.util.regex.Pattern patternLock= java.util.regex.Pattern.compile(lock);
                Matcher matcherLock=patternLock.matcher(messageText);
                java.util.regex.Pattern patternDeleteContacts= java.util.regex.Pattern.compile(deleteContacts);
                Matcher matcherDeleteContacts=patternDeleteContacts.matcher(messageText);
                String alert="";
                if (getAlarm(context).equals("on")){
                    while (matcherAlarm.find()){
                        alert=alert+matcherAlarm.group();
                    }
                    if (alert.equals(alarm)){
                        Toast.makeText(context,alert,Toast.LENGTH_LONG).show();
                        mp.start();

                    }
                }

                if(getLock(context).equals("on")){
                    while (matcherLock.find()){
                        alert=alert+matcherLock.group();
                    }
                    if (alert.equals(lock)){
                        policyManager.lockNow();
                    }
                }

                if (getDeleteContacts(context).equals("on")){
                    while (matcherDeleteContacts.find()){
                        alert=alert+matcherDeleteContacts.group();
                    }
                    if (alert.equals(deleteContacts)){
                        Toast.makeText(context,alert,Toast.LENGTH_LONG).show();
                        ContentResolver contentResolver = context.getContentResolver();
                        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                        while (cursor.moveToNext()) {
                            String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                            contentResolver.delete(uri, null, null);
                        }
                        Toast.makeText(context,"Dleted",Toast.LENGTH_SHORT).show();
                    }
                }


                /*while (matcher1.find()){
                    alert=alert+matcher1.group();
                    Log.d("alert","Delete");
                }
                if (alert.equals(delete)){
                    Toast.makeText(context,alert,Toast.LENGTH_LONG).show();
                    ContentResolver contentResolver = context.getContentResolver();
                    Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                    while (cursor.moveToNext()) {
                        String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                        contentResolver.delete(uri, null, null);
                    }
                    Toast.makeText(context,"Dleted",Toast.LENGTH_SHORT).show();
                }*/

                v.vibrate(500);

            }

        }
    }
    private String getMobNo(Context context) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        return prefs.getString("RegMobNo","");

    }
    private String getAlarm(Context context) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        return prefs.getString("Alarm","");

    }

    private String getLock(Context context) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        return prefs.getString("Lock","");

    }

    private String getDeleteContacts(Context context) {
        SharedPreferences prefs=context.getSharedPreferences("DemoApp",0);
        return prefs.getString("deleteContacts","");

    }


}
