package com.example.nguyennghia.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatViewTestActivity extends AppCompatActivity {
    private static final String TAG = "ChatViewTestActivity";
    private DialogItemView ciChatView;

    CircleColorDrawable circleColorDrawable = new CircleColorDrawable(Color.parseColor("#16a085"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view_test);

        final Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ava3);
        ciChatView = (DialogItemView) findViewById(R.id.ci_chat_view);
        List<String> mUrl = new ArrayList<>();
        mUrl.add("url");
        mUrl.add("url");
        mUrl.add("url");
        mUrl.add("url");
        mUrl.add("url");
        mUrl.add("url");
//        mUrl.add("url");

        ciChatView.setBitmapUrls(null, mUrl);
//
//        ciChatView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ciChatView.setBitmapAt(bm, 0, true);
//            }
//        }, 1000);
//
//        ciChatView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ciChatView.setBitmapAt(bm, 1, false);
//            }
//        }, 2000);
//
//        ciChatView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ciChatView.setBitmapAt(bm, 2, true);
//            }
//        }, 3000);
//
//        ciChatView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ciChatView.setBitmapAt(bm, 3, true);
//            }
//        }, 3000);

        ciChatView.setDefaultDrawable(circleColorDrawable);


        ciChatView.setUnreadText("5");
        ciChatView.setStatus("1 day");
        ciChatView.setTitle("Nguyễn Nghĩa, Hoàng Ánh, Hoàng Xoan");
        ciChatView.setContent("Update frmTraCuuTiecCuoi + SQL_QuanLyTiecCuoi_NEW.sql");
    }
}
