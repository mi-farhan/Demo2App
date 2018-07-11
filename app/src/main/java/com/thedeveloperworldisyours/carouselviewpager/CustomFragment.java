package com.thedeveloperworldisyours.carouselviewpager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class CustomFragment extends Fragment {

    private PopupWindow POPUP_WINDOW_SCORE = null;

    ImageView backgroundImage;
    TextView textView;
    ImageView imageView;

    private int[] imageArray = new int[]{ R.drawable.protect_data, R.drawable.anti_theft,
            R.drawable.extra_secure, R.drawable.track_recover, R.drawable.backup,R.drawable.booster,
            R.drawable.app_analizier,R.drawable.secure_browser,R.drawable.notification_manager,R.drawable.app_locker};


    private int[] textArray = new int[]{ R.string.protect_data,R.string.blocker,
            R.string.ExtraSecure, R.string.tracking, R.string.backup,
             R.string.booster,R.string.app_blocker,R.string.SecureBrowser,R.string.NotificationManager,R.string.app_locker};



    public static Fragment newInstance(Activity context, int position, float scale) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putFloat("scale", scale);
        return Fragment.instantiate(context, CustomFragment.class.getName(), bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout linearLayout = (LinearLayout)
                inflater.inflate(R.layout.item, container, false);

        final int position = this.getArguments().getInt("position");
        textView = (TextView) linearLayout.findViewById(R.id.item_text);
        imageView = (ImageView) linearLayout.findViewById(R.id.pagerImg);


        CustomLinearLayout root = (CustomLinearLayout) linearLayout.findViewById(R.id.item_root);
        float scale = this.getArguments().getFloat("scale");

        //textView.setText("Carousel item: " + postion);
        imageView.setImageResource(imageArray[position]);

        textView.setText(textArray[position]);


        root.setScaleBoth(scale);

        root.addOnLayoutChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "clicked"+position, Toast.LENGTH_SHORT).show();
                ShowPopup(position);
                /*if (position==2){
                    *//*Intent intent=new Intent(getContext(),ExtraSecure.class);
                    startActivity(intent);*//*


                }else if (position==1){
                    Intent intent=new Intent(getContext(),AntiTheft.class);
                    startActivity(intent);
                }else if(position==3){
                    Intent intent=new Intent(getContext(),TrackAndRecover.class);
                    startActivity(intent);
                }*//*else if(position==9){
                    Intent intent=new Intent(getContext(),Pattern.class);
                    startActivity(intent);
                }*//*else{
                    Toast.makeText(getContext(), "clicked"+position, Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        return linearLayout;
    }

    private void ShowPopup(final Integer n)
    {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.pass_popup, null);

        // Creating the PopupWindow
        POPUP_WINDOW_SCORE = new PopupWindow();
        POPUP_WINDOW_SCORE.setContentView(layout);
        POPUP_WINDOW_SCORE.setWidth(width);
        POPUP_WINDOW_SCORE.setHeight(height);
        POPUP_WINDOW_SCORE.setFocusable(true);

        // prevent clickable background
        POPUP_WINDOW_SCORE.setBackgroundDrawable(null);

        POPUP_WINDOW_SCORE.showAtLocation(layout, Gravity.CENTER, 1, 1);


        // Getting a reference to button one and do something
        Button butOne = (Button) layout.findViewById(R.id.submitPass);
        butOne.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Do Something

                //Close Window
                POPUP_WINDOW_SCORE.dismiss();
                if (n==1){
                    startActivity(new Intent(getContext(),AntiTheft.class));
                }else if (n==2){
                    startActivity(new Intent(getContext(),ExtraSecure.class));
                }else if (n==3){
                    startActivity(new Intent(getContext(),TrackAndRecover.class));
                }else{
                    Toast.makeText(getContext(), "clicked"+n, Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Getting a reference to button two and do something
        Button butTwo = (Button) layout.findViewById(R.id.cancelPass);
        butTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Do Something

                //Close Window
                POPUP_WINDOW_SCORE.dismiss();
            }
        });
    }
}
