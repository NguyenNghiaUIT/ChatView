package com.example.nguyennghia.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class TwoCircleImageActivity extends AppCompatActivity {
    DialogItemView messageView;
    private static final String TAG = "TwoCircleImageActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_circle_image);

        Log.e(TAG, "onCreate: " + savedInstanceState);
        final Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ava1);

        messageView = (DialogItemView) findViewById(R.id.cv_two_image);
        messageView.setBitmapUrl("1", "url");
//        messageView.setDrawTypeTotalMember(DialogItemView.TOTAL_MEMBER_SQUARE);
        CircleColorDrawable circleColorDrawable = new CircleColorDrawable(Color.parseColor("#16a085"));
        messageView.setDefaultDrawable(circleColorDrawable);
        messageView.setUnreadText("N");

//        messageView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                messageView.setBitmapAt(bm, 0, true);
//            }
//        }, 1000);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
    }
}
