package com.example.nguyennghia.circleimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.nguyennghia.circleimageview.model.ChatInfo;
import com.example.nguyennghia.circleimageview.model.Picture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListViewCircleImageTestActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView lvAuthor;
    private ChatViewAdapter mAdapter;
    private List<ChatInfo> mChatInfos;
    private Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_circle_image_test);
        lvAuthor = (ListView) findViewById(R.id.lv_author);


        File[] files = ZaloAvatarManager.getAllFiles();
        if(files != null){
            mChatInfos = new ArrayList<>();
            for (int i = 0; i < 200; i++) {
                Picture p = new Picture();
                int value = mRandom.nextInt(10);
                if (value == 0) {
                    p.setUrl(files[0].getAbsolutePath());
                } else if (value == 1) {
                    p.setUrl(files[1].getAbsolutePath(), files[2].getAbsolutePath());

                } else if (value == 2) {
                    p.setUrl(files[3].getAbsolutePath(), files[4].getAbsolutePath(), files[5].getAbsolutePath());
                } else if (value == 3) {
                    p.setUrl(files[6].getAbsolutePath(), files[7].getAbsolutePath(), files[8].getAbsolutePath(), files[9].getAbsolutePath());
                } else {
                    p.setUrl(files[10].getAbsolutePath(), files[11].getAbsolutePath(), files[12].getAbsolutePath(), files[13].getAbsolutePath(), files[14].getAbsolutePath());
                }
                mChatInfos.add(new ChatInfo("This is Title" + i, "This is content " + i, i + " minutes", p));
            }
            mAdapter = new ChatViewAdapter(this, mChatInfos);
            lvAuthor.setAdapter(mAdapter);

        }



    }
}
