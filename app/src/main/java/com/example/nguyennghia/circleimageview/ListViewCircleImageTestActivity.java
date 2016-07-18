package com.example.nguyennghia.circleimageview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.nguyennghia.circleimageview.model.ChatInfo;
import com.example.nguyennghia.circleimageview.model.Picture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListViewCircleImageTestActivity extends AppCompatActivity {

    private static final String TAG = "ListViewCircleImage";
    private ListView lvAuthor;
    private ChatViewAdapter mAdapter;
    private List<ChatInfo> mChatInfos = new ArrayList<>();
    private Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_list_view_circle_image_test);
        lvAuthor = (ListView) findViewById(R.id.lv_message_view);

        File[] files = ZaloAvatarManager.getAllFiles();
        if (files != null) {
            for (int i = 0; i < 100; i++) {
                Picture p = new Picture();
                int value = mRandom.nextInt(6);
                if (value == DialogItemView.AVATAR_ONE_BITMAP) {
                    p.setUrl(null, files[0].getAbsolutePath());
                } else if (value == DialogItemView.AVATAR_ONE_BITMAP_AND_TEXT) {
                    p.setUrl("1", files[1].getAbsolutePath());
                } else if (value == DialogItemView.AVATAR_TWO_BITMAP) {
                    p.setUrl(null, files[4].getAbsolutePath(), files[5].getAbsolutePath());
                } else if (value == DialogItemView.AVATAR_THREE_BITMAP) {
                    p.setUrl(null, files[7].getAbsolutePath(), files[8].getAbsolutePath(), files[9].getAbsolutePath());
                } else if(value == DialogItemView.AVATAR_THREE_BITMAP_AND_TEXT){
                    p.setUrl(String.valueOf(value), files[10].getAbsolutePath(), files[11].getAbsolutePath(), files[12].getAbsolutePath());
                }else if(value == DialogItemView.AVATAR_FOUR_BITMAP){
                    p.setUrl(null, files[10].getAbsolutePath(), files[11].getAbsolutePath(), files[12].getAbsolutePath(), files[14].getAbsolutePath());
                }
                mChatInfos.add(new ChatInfo(files[i].getName(), files[i].length() + "", i + " minutes", p));
            }
            mAdapter = new ChatViewAdapter(this, mChatInfos);
            lvAuthor.setAdapter(mAdapter);

        }
    }
}
