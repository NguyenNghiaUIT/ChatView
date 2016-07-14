package com.example.nguyennghia.circleimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
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
        lvAuthor = (ListView) findViewById(R.id.lv_author);

        File[] files = ZaloAvatarManager.getAllFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                Picture p = new Picture();
//                int value = mRandom.nextInt(5);
//                if (value == 0) {
                p.setUrl(files[i].getAbsolutePath());
//                } else if (value == 1) {
//                    p.setUrl(files[1].getAbsolutePath(), files[2].getAbsolutePath());
//                } else if (value == 2) {
//                    p.setUrl(files[3].getAbsolutePath(), files[4].getAbsolutePath(), files[5].getAbsolutePath());
//                } else if (value == 3) {
//                    p.setUrl(files[6].getAbsolutePath(), files[7].getAbsolutePath(), files[8].getAbsolutePath(), files[9].getAbsolutePath());
//                } else {
//                    p.setUrl(files[10].getAbsolutePath(), files[11].getAbsolutePath(), files[12].getAbsolutePath(), files[13].getAbsolutePath(), files[14].getAbsolutePath());
//                }
                mChatInfos.add(new ChatInfo(files[i].getName(), files[i].length() + "", i + " minutes", p));
            }
            mAdapter = new ChatViewAdapter(this, mChatInfos);
            lvAuthor.setAdapter(mAdapter);

            lvAuthor.setRecyclerListener(new AbsListView.RecyclerListener() {
                @Override
                public void onMovedToScrapHeap(View view) {
                    Log.e(TAG, "onMovedToScrapHeap: " + view);
                }
            });

        }
    }
}
