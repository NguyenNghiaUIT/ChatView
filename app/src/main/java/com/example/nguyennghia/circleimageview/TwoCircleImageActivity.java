package com.example.nguyennghia.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TwoCircleImageActivity extends AppCompatActivity {
    ChatView circleImage;
    private static final String TAG = "TwoCircleImageActivity";

    List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_circle_image);
        final Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ava1);

        if(mList == null){
            mList = new ArrayList<>();
            Log.e(TAG, "onCreate: Init" );
        }else{
            Log.e(TAG, "onCreate: Save State");
        }

        circleImage = (ChatView) findViewById(R.id.cv_two_image);
        circleImage.setBitmapUrl("url", "1");
        CircleBitmapDrawable circleBitmapDrawable = new CircleBitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.default_ava));
        ColorDrawable circleColorDrawable = new ColorDrawable(getResources().getColor(R.color.colorAccent));
        circleImage.setDefaultDrawable(circleColorDrawable);
        circleImage.setUnreadText("N");

        circleImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                circleImage.setBitmapAt(bm, 0, true);
            }
        }, 1000);

    }
}
