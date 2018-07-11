package com.thedeveloperworldisyours.carouselviewpager;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by geniec3 on 9/7/18.
 */

public class Pattern extends DeviceAdminReceiver {

    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "Device Administrator: Activated");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "This is an optional message to warn the user about disabling.";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "Device Administrator: Deactivated");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        final MediaPlayer mp=MediaPlayer.create(context,R.raw.go_fast);
        mp.setLooping(true);
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        //int maxVolumeMusic = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        mp.setVolume(100, 100);
        showToast(context, "Sample Device Admin: pw failed");
        Vibrator v = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        mp.start();
        v.vibrate(2000);


    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        final MediaPlayer mp=MediaPlayer.create(context,R.raw.go_fast);
        mp.setLooping(true);
        showToast(context, "Welcome Wise Device Owner");
        Vibrator v = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(2000);
        mp.stop();

    }
}
