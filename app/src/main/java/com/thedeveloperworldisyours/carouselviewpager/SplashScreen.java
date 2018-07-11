package com.thedeveloperworldisyours.carouselviewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreen extends Activity {


    private SharedPreferences sharedPreferences;
    private String Userid;
    private Intent magicalintent;
    Animation animation;
    private ImageView mSplashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(SplashScreen.this);

        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Userid = sharedPreferences.getString("UserID", "0");

        mSplashView = (ImageView) findViewById(R.id.splashImage);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);

        mSplashView.startAnimation(animation);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                if (Userid.equals("0")) {
                    magicalintent = new Intent(
                            SplashScreen.this,
                            MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(magicalintent);
                    overridePendingTransition(R.anim.rotateup, R.anim.rotatedown);
                    finish();


                } else {


                    magicalintent = new Intent(SplashScreen.this,
                            MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(magicalintent);
                    finish();

                }
            }
        }, 6000);
    }
}



/*
public class SplashScreen extends AppCompatActivity {


    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashTread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        StartAnimations();

      }

    private void StartAnimations() {
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        loadAnimation.reset();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin_lay);
        linearLayout.clearAnimation();
        linearLayout.startAnimation(loadAnimation);

        loadAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
        loadAnimation.reset();
        ImageView imageView = (ImageView) findViewById(R.id.splash);
        imageView.clearAnimation();
        imageView.startAnimation(loadAnimation);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 6500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();
                } catch (InterruptedException e) {

                } finally {
                    SplashScreen.this.finish();
                }

            }
        };
        splashTread.start();

    }

}
*/

