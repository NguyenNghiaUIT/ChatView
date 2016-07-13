package com.example.nguyennghia.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Created by nguyennghia on 05/07/2016.
 */

public class ZaloAvatarManager {
    private static HashMap<String, Bitmap> mCacheBitmaps = new HashMap<>();
    public static Bitmap decodeBitmapFromFile(String filePath) {

        if(mCacheBitmaps.containsKey(filePath))
            return  mCacheBitmaps.get(filePath);

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        File file = new File(filePath);
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mCacheBitmaps.put(filePath, bitmap);
        return bitmap;
    }

    public static File[] getAllFiles() {
        String path = Environment.getExternalStorageDirectory().toString() + "/ZaloAvatar";
        File zaloAvatarFolder = new File(path);
        return zaloAvatarFolder.listFiles();
    }



}
