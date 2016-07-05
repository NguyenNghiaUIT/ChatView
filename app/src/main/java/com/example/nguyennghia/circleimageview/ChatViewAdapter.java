package com.example.nguyennghia.circleimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


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
    // private CircleBitmapDrawable mCircleBitmapDrawable;

    public ChatViewAdapter(Context context, List<ChatInfo> objects) {
        super(context, 0, objects);
        mContext = context;
        mChatInfos = objects;
        mCircleColorDrawable = new CircleColorDrawable(context.getResources().getColor(R.color.colorAccent));
        //mCircleBitmapDrawable = new CircleBitmapDrawable(BitmapFactory.decodeResource(context.getResources() ,R.drawable.default_ava));
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

        viewHolder.ciAvaAuthor.setBitmapUrl(picture.getUrls());
        viewHolder.ciAvaAuthor.drawUnRead("2");
        viewHolder.ciAvaAuthor.setStatus(chatInfo.getStatus());
        viewHolder.ciAvaAuthor.setDrawableDefault(mCircleColorDrawable);
//        viewHolder.ciAvaAuthor.setDrawableDefault(mCircleBitmapDrawable);
        viewHolder.ciAvaAuthor.setTitle(chatInfo.getTitle());
        viewHolder.ciAvaAuthor.setContent(chatInfo.getContent());

        int size = picture.getUrls().size() > 4 ? 3 : picture.getUrls().size();
        for (int i = 0; i < size; i++) {
            if (!picture.getLoadeds()[i]) {
                picture.setBitmap(ZaloAvatarManager.decodeBitmapFromFile(picture.getUrls().get(i)), i);
                picture.getLoadeds()[i] = true;
            }

            if (picture.getIsAnimation()[i]) {
                viewHolder.ciAvaAuthor.drawBitmapAt(picture.getBitmaps()[i], i, true);
                picture.getIsAnimation()[i] = false;
            }else{
                viewHolder.ciAvaAuthor.drawBitmapAt(picture.getBitmaps()[i], i, false);
            }

        }

//        for (int i = 0; i < size; i++) {
//            if (picture.getLoadeds()[i]) {
//                viewHolder.ciAvaAuthor.drawBitmapAt(picture.getBitmaps()[i], i, false);
//                picture.getIsAnimation()[i] = false;
//            } else {
//                new DownloadAvatarBoxTask().execute(picture);
//            }
//        }

//        picture.setOnDownloadBitmapListener(new Picture.OnDownloadBitmap() {
//            @Override
//            public void onSuscess(Bitmap bitmap, int index, int type) {
//                if (viewHolder.ciAvaAuthor.getImageType() == type && picture.getLoadeds()[index]) {
//                    viewHolder.ciAvaAuthor.drawBitmapAt(bitmap, index, true);
//                    picture.getIsAnimation()[index] = false;
//                }
//            }
//        });

        return convertView;
    }

    static class ViewHolder {
        private TextView tvAuthor;
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
