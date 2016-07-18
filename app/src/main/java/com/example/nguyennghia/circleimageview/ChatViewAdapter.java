package com.example.nguyennghia.circleimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.nguyennghia.circleimageview.model.ChatInfo;
import com.example.nguyennghia.circleimageview.model.Picture;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyennghia on 05/07/2016.
 */

public class ChatViewAdapter extends ArrayAdapter<ChatInfo> {
    private static final String TAG = "ChatViewAdapter";
    private Context mContext;
    private List<ChatInfo> mChatInfos;
    private ColorDrawable mColorDrawable;
    private CircleColorDrawable circleColorDrawable;
    private BitmapDrawable mNotifyDrawable;
    private BitmapDrawable mFailDrawable;

    public ChatViewAdapter(Context context, List<ChatInfo> objects) {
        super(context, 0, objects);
        mContext = context;
        mChatInfos = objects;
        mColorDrawable = new ColorDrawable(context.getResources().getColor(R.color.colorPrimary));
        circleColorDrawable = new CircleColorDrawable(Color.parseColor("#16a085"));
        mNotifyDrawable = new BitmapDrawable(context.getResources(), BitmapFactory.decodeResource(context.getResources(), R.drawable.notify));
        mFailDrawable = new BitmapDrawable(context.getResources(), BitmapFactory.decodeResource(context.getResources(), R.drawable.fail));
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Log.i(TAG, "getView: " + position);
        final ViewHolder viewHolder;
        ChatInfo chatInfo = mChatInfos.get(position);
        final Picture picture = chatInfo.getPictures();

        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.author_row_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.messageView = (DialogItemView) convertView.findViewById(R.id.ci_ava_author);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.messageView.reset();
        }

        viewHolder.messageView.setBitmapUrls(picture.getTotalMemberText(), picture.getUrls());
        viewHolder.messageView.setUnreadText("2");

        viewHolder.messageView.setTitle(picture.getTotalMemberText());
        viewHolder.messageView.setContent(picture.getUrls().size() + "");
        viewHolder.messageView.setStatus(chatInfo.getStatus());
        viewHolder.messageView.setStatusTextBold(false);
        viewHolder.messageView.setTitleTextBold(true);
        viewHolder.messageView.setContentTextBold(false);

        viewHolder.messageView.setIconNotifyDrawable(mNotifyDrawable);
        viewHolder.messageView.setIconFailDrawable(mFailDrawable);
        viewHolder.messageView.setDefaultDrawable(circleColorDrawable);

//        int size = picture.getUrls().size();
//        for (int i = 0; i < size; i++) {
//            if (!picture.getLoadeds()[i]) {
//                Bitmap bitmap = ZaloAvatarManager.getBitmap(picture.getUrls().get(i));
//                if (bitmap == null)
//                    picture.setBitmap(ZaloAvatarManager.decodeBitmapFromFile(picture.getUrls().get(i), false), i);
//                else {
//                    if (bitmap.isRecycled())
//                        picture.setBitmap(ZaloAvatarManager.decodeBitmapFromFile(picture.getUrls().get(i), true), i);
//                    else
//                        picture.setBitmap(bitmap, i);
//                }
//                picture.getLoadeds()[i] = true;
//            }
//
//            if (picture.getIsAnimation()[i]) {
//                viewHolder.messageView.setBitmapAt(picture.getBitmaps()[i], i, true);
//                picture.getIsAnimation()[i] = false;
//            } else {
//                viewHolder.messageView.setBitmapAt(picture.getBitmaps()[i], i, false);
//            }
//        }


        return convertView;
    }


    static class ViewHolder {
        private DialogItemView messageView;
    }
}
