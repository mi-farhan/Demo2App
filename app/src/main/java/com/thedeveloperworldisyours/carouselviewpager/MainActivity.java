package com.thedeveloperworldisyours.carouselviewpager;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static int PAGES = 10;
    public final static int FIRST_PAGE = 1  ;

    public CustomPagerAdapter mAdapter;
    public ViewPager mViewPager;
    LinearLayout ll_bgcolor;
    ImageView circularImage;
    Button scann;
    TextView scan_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        mViewPager = (ViewPager) findViewById(R.id.activity_main_view_pager);
        ll_bgcolor = (LinearLayout) findViewById(R.id.ll_bgcolor);



       // circularImage = (CircleImageView) findViewById(R.id.iv_circularImage);

          /*scann = (Button) findViewById(R.id.btn_scann);
        scann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "button", Toast.LENGTH_SHORT).show();
            }
        });*/


        ll_bgcolor.setBackgroundResource(R.drawable.screen3111);


        mAdapter = new CustomPagerAdapter(this, this.getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(false, mAdapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        mViewPager.setCurrentItem(FIRST_PAGE);

        // Necessary or the mViewPager will only have one extra page to show
        // make this at least however many pages you can see
        mViewPager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        mViewPager.setPageMargin(-470);


        /*circularImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Scan Now button clicked", Toast.LENGTH_SHORT).show();
            }
        });*/
    }


}
