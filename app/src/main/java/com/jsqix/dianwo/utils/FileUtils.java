package com.jsqix.dianwo.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by dq on 2015/12/29.
 */
public class FileUtils {
    /**
     * SD卡最大缓存500M
     */
    private static final int MAX_SIZE = 500;
    /**
     * sd卡内存低于此值时将会清理缓存,单位是mb
     */
    private static final int NEED_TO_CLEAN = 20;

    private static final String SD_FILE_ROOT = Environment.getExternalStorageDirectory().getPath() + "/com.jsqix.dw";
    private static final String USER_FILE_ROOT = SD_FILE_ROOT + "/User";
    private static final String IMAGE_FILE_ROOT = SD_FILE_ROOT + "/Pictures";
    private static final String RECORD_FILE_ROOT = SD_FILE_ROOT + "/Record";

    public static String getSdFileRoot() {
        new File(SD_FILE_ROOT).mkdirs();
        return SD_FILE_ROOT;

    }

    public static String getUserFileRoot() {
        new File(USER_FILE_ROOT).mkdirs();
        return USER_FILE_ROOT;

    }

    public static String getImageFileRoot() {
        new File(IMAGE_FILE_ROOT).mkdirs();
        return IMAGE_FILE_ROOT;

    }

    public static String getRecordFileRoot() {
        new File(RECORD_FILE_ROOT).mkdirs();
        return RECORD_FILE_ROOT;

    }

    public static String saveCache(String path, Bitmap bitmap) {

        if (bitmap != null) {
            try {
                File fileImage = new File(path);
                if (!fileImage.exists()) {
                    fileImage.createNewFile();
                    Log.i("Snapshot", "image file created");
                }
                removeCache(path);
                FileOutputStream out = new FileOutputStream(fileImage);
                if (out != null) {
                    FormatTools.getInstance().compressByQuality(bitmap, 500).writeTo(out);
                    out.flush();
                    out.close();
                    Log.i("Snapshot", "screen image saved");
                    return path;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    /**
     * 清除40%的缓存，这些缓存被删除的优先级根据近期使用时间排列,越久没被使用，越容易被删除
     */
    private static void removeCache(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        double total_size = 0;
        for (File file : files) {
            total_size += file.length();
        }
        total_size = total_size / 1024 / 1024;
        if (total_size > MAX_SIZE || getSdCardFreeSpace() <= NEED_TO_CLEAN) {
            int removeFactor = (int) (files.length * 0.4);
            Arrays.sort(files, new FileLastModifiedComparator());
            for (int i = 0; i < removeFactor; i++) {
                files[i].delete();
            }
        }
    }

    private static class FileLastModifiedComparator implements Comparator<File> {
        @Override
        public int compare(File lhs, File rhs) {
            if (lhs.lastModified() > rhs.lastModified()) {
                return 1;
            } else if (lhs.lastModified() == rhs.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 获取sd卡总空间
     */
    private static long getSdCardTotalSpace() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        long totalSize = totalBlocks * blockSize;
        Log.v("sd totalSize", totalSize / 1024 / 1024 + "MB");
        return totalSize / 1024 / 1024;
    }

    /**
     * 获取sd卡可用空间
     */
    private static long getSdCardFreeSpace() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        long availableBlocks = stat.getAvailableBlocks();
        long totalSize = totalBlocks * blockSize;
        long availSize = availableBlocks * blockSize;
        Log.v("sd totalSize", totalSize / 1024 / 1024 + "MB");
        Log.v("sd availSize", availSize / 1024 / 1024 + "MB");
        return availSize / 1024 / 1024;
    }

    /**
     * 判断sd卡是否可用
     *
     * @return
     */
    public static boolean isSdcardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file     要删除的根目录
     * @param fileName 要保留的文件
     */
    public static void RecursionDeleteFile(File file, String fileName) {
        if (file.isFile()) {
            if (!fileName.equalsIgnoreCase(file.getName())) {
                file.delete();
            }
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f, fileName);
            }
            file.delete();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
