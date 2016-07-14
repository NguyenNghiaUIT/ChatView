package com.example.nguyennghia.circleimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private CircleColorDrawable mCircleColorDrawable;
    private ColorDrawable mColorDrawable;
    private BitmapDrawable mNotifyDrawable;
    private BitmapDrawable mFailDrawable;
    // private CircleBitmapDrawable mCircleBitmapDrawable;

    public ChatViewAdapter(Context context, List<ChatInfo> objects) {
        super(context, 0, objects);
        mContext = context;
        mChatInfos = objects;
        mCircleColorDrawable = new CircleColorDrawable(context.getResources().getColor(R.color.colorAccent));
        mColorDrawable = new ColorDrawable(context.getResources().getColor(R.color.colorPrimary));
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
            viewHolder.ciAvaAuthor = (ChatView) convertView.findViewById(R.id.ci_ava_author);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.ciAvaAuthor.reset();
        }
        viewHolder.ciAvaAuthor.setBitmapUrls(picture.getUrls());
        viewHolder.ciAvaAuthor.setUnreadText("N");
        viewHolder.ciAvaAuthor.setStatusTextBold(false);
        viewHolder.ciAvaAuthor.setTitleTextBold(false);
        viewHolder.ciAvaAuthor.setContentTextBold(false);

        viewHolder.ciAvaAuthor.setTitle(chatInfo.getTitle());
        viewHolder.ciAvaAuthor.setContent(chatInfo.getContent());
        viewHolder.ciAvaAuthor.setStatus(chatInfo.getStatus());
        viewHolder.ciAvaAuthor.setIconNotifyDrawable(mNotifyDrawable);
        viewHolder.ciAvaAuthor.setIconFailDrawable(mFailDrawable);
        viewHolder.ciAvaAuthor.setDefaultDrawable(mColorDrawable);
        viewHolder.ciAvaAuthor.setDrawTypeTotalMember(ChatView.TOTAL_MEMBER_SQUARE);

        int size = picture.getUrls().size() > 4 ? 3 : picture.getUrls().size();
        for (int i = 0; i < size; i++) {
            if (!picture.getLoadeds()[i]) {
                Log.i(TAG, "getView: " + "load new");
                Bitmap bitmap = ZaloAvatarManager.getBitmap(picture.getUrls().get(i));
                if (bitmap == null)
                    picture.setBitmap(ZaloAvatarManager.decodeBitmapFromFile(picture.getUrls().get(i), false), i);
                else {
                    if (bitmap.isRecycled())
                        picture.setBitmap(ZaloAvatarManager.decodeBitmapFromFile(picture.getUrls().get(i), true), i);
                    else
                        picture.setBitmap(bitmap, i);
                }
                picture.getLoadeds()[i] = true;
            }

            Log.i(TAG, "getView: " + "load new");
            if (picture.getIsAnimation()[i]) {
                viewHolder.ciAvaAuthor.setBitmapAt(picture.getBitmaps()[i], i, true);
                picture.getIsAnimation()[i] = false;
            } else {
                viewHolder.ciAvaAuthor.setBitmapAt(picture.getBitmaps()[i], i, false);
            }
        }

        return convertView;
    }

    static class ViewHolder {
        private ChatView ciAvaAuthor;
    }

    class DownloadAvatarBoxTask extends AsyncTask<Picture, Void, Void> {
        private Picture picture;
        private List<Bitmap> bitmaps = new ArrayList<>();
        private int size;

        @Override
        protected Void doInBackground(Picture... params) {
            picture = params[0];
            List<String> url = picture.getUrls();
            size = url.size() > 4 ? 3 : url.size();
            for (int i = 0; i < size; i++) {
                Bitmap bm = null;
                try {
                    if (picture.getLoadeds()[i]) {
                        bm = picture.getBitmaps()[i];
                    } else {
                        URL aURL = new URL(url.get(i));
                        URLConnection conn = aURL.openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        bm = BitmapFactory.decodeStream(bis);
                        bis.close();
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmaps.add(bm);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            for (int i = 0; i < size; i++) {
                Bitmap bm = bitmaps.get(i);
                if (!picture.getLoadeds()[i] && bm != null) {
                    picture.setBitmap(bm, i);
                }
            }
        }
    }

}
