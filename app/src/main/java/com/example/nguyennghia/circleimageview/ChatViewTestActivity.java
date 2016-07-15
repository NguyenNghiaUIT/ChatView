package com.example.nguyennghia.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ChatViewTestActivity extends AppCompatActivity {
    private static final String TAG = "ChatViewTestActivity";
    private DialogItemView ciChatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view_test);

        final Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ava3);
        ciChatView = (DialogItemView) findViewById(R.id.ci_chat_view);
        ciChatView.setBitmapUrls("url", "url", "url", "url", "url");

        ciChatView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ciChatView.setBitmapAt(bm, 0, true);
            }
        }, 1000);

        ciChatView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ciChatView.setBitmapAt(bm, 1, true);
            }
        }, 2000);

        ciChatView.setTitleTextBold(true);
        ciChatView.setContentTextBold(true);

        ciChatView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ciChatView.setBitmapAt(bm, 2, true);
            }
        }, 3000);

//        ciChatView.drawUnRead("5");
        ciChatView.setStatus("1 day");
        ciChatView.setUnreadText("5");
        ciChatView.setTitle("Nguyễn Nghĩa, Hoàng Ánh, Hoàng Xoan");
        ciChatView.setContent("Update frmTraCuuTiecCuoi + SQL_QuanLyTiecCuoi_NEW.sql");
    }
}
