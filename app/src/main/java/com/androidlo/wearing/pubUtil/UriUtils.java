package com.androidlo.wearing.pubUtil;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by 62361 on 2019/4/3.
 */

public class UriUtils {


    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    /**
     * 转换uri数组为file类型的uri数组
     */
    public static void processUriArrayToFileUriArray(Context context, Uri[] uris) {
        if (uris == null) {
            return;
        }
        Uri[] uriArray = new Uri[uris.length];
        for (int i = 0; i < uris.length; i++) {
            uris[i] = getFileUri(context, uris[i]);
        }
    }

    /**
     * 转换uri为file类型的uri
     */
    public static Uri getFileUri(final Context context, final Uri uri) {
        String realFilePath = getRealFilePath(context, uri);
        if (TextUtils.isEmpty(realFilePath)) {
            return null;
        }
        return Uri.fromFile(new File(realFilePath));
    }

    /**
     * 得到图片uri的实际地址
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}
