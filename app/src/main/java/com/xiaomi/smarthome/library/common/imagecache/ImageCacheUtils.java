package com.xiaomi.smarthome.library.common.imagecache;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import com.xiaomi.idm.api.IDMServer;
import java.io.Closeable;
import java.io.File;

/* loaded from: classes4.dex */
public class ImageCacheUtils {
    public static final int IO_BUFFER_SIZE = 8192;

    private ImageCacheUtils() {
    }

    public static void disableConnectionReuseIfNecessary() {
        if (hasHttpConnectionBug()) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    @SuppressLint({"NewApi"})
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    @SuppressLint({"NewApi"})
    public static boolean isExternalStorageRemovable() {
        return Build.VERSION.SDK_INT < 9 || Environment.isExternalStorageRemovable();
    }

    @SuppressLint({"NewApi"})
    public static File getExternalCacheDir(Context context) {
        File externalCacheDir;
        if (hasExternalCacheDir() && (externalCacheDir = context.getExternalCacheDir()) != null) {
            return externalCacheDir;
        }
        return new File(Environment.getExternalStorageDirectory().getPath() + ("/Android/data/" + context.getPackageName() + "/cache/"));
    }

    @SuppressLint({"NewApi"})
    public static long getUsableSpace(File file) {
        if (Build.VERSION.SDK_INT >= 9) {
            return file.getUsableSpace();
        }
        StatFs statFs = new StatFs(file.getPath());
        return statFs.getBlockSize() * statFs.getAvailableBlocks();
    }

    public static int getMemoryClass(Context context) {
        return ((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getMemoryClass();
    }

    public static boolean hasHttpConnectionBug() {
        return Build.VERSION.SDK_INT < 8;
    }

    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= 8;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }
}
