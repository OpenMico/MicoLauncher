package com.xiaomi.mico.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import com.blankj.utilcode.constant.CacheConstants;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.passport.ui.internal.PassportUI;
import com.xiaomi.smarthome.library.common.network.Network;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public abstract class CommonUtils {
    public static final int DAY = 86400000;
    public static final int HOUR = 3600000;
    public static final int IMAGE_COMPRESS_MODE_QUALITY = 1;
    public static final int IMAGE_COMPRESS_MODE_SIZE = 2;
    public static final int IMAGE_HEIGHT_THRESHOLD = 800;
    public static final int IMAGE_WIDTH_THRESHOLD = 480;
    public static final int MINUTE = 60000;
    public static final int SECOND = 1000;
    private static String a = "CommonUtils";
    private static String b = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static Pattern c = Pattern.compile(b);

    public static String getDayString(int i) {
        switch (i) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            default:
                return "";
        }
    }

    public static boolean isLetter(char c2) {
        return (c2 >= 'a' && c2 <= 'z') || (c2 >= 'A' && c2 <= 'Z');
    }

    public static void restartAppTask(Context context, Uri uri, Bundle bundle) {
    }

    public static boolean shouldVoIPEntryVisible(Context context) {
        return true;
    }

    public static void smoothScrollListViewToTop(ListView listView) {
        smoothScrollListView(listView, 0, 0, 100);
    }

    @SuppressLint({"NewApi"})
    public static void smoothScrollListView(ListView listView, int i, int i2, int i3) {
        if (Build.VERSION.SDK_INT >= 11) {
            listView.smoothScrollToPositionFromTop(i, i2, i3);
        } else {
            listView.setSelectionFromTop(i, i2);
        }
    }

    public static boolean isValidUrl(String str) {
        if (str == null) {
            return false;
        }
        try {
            new URL(str);
            return true;
        } catch (MalformedURLException unused) {
            return false;
        }
    }

    protected static URL getUrlIfValid(String str) {
        if (str == null) {
            return null;
        }
        try {
            return new URL(str);
        } catch (MalformedURLException unused) {
            return null;
        }
    }

    public static boolean isValidMusicUrl(String str) {
        if (str != null) {
            return str.endsWith(".m4a") || str.endsWith(".mp4") || str.endsWith(".mp3") || str.endsWith(".wma") || str.endsWith(".wav") || str.endsWith(".ogg");
        }
        return false;
    }

    public static String readToString(InputStream inputStream) {
        StringWriter stringWriter = new StringWriter();
        char[] cArr = new char[1024];
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while (true) {
                int read = bufferedReader.read(cArr);
                if (read == -1) {
                    return stringWriter.toString();
                }
                stringWriter.write(cArr, 0, read);
            }
        } catch (IOException e) {
            Log.e(a, Log.getStackTraceString(e));
            return "";
        }
    }

    public static void createTable(SQLiteDatabase sQLiteDatabase, String str, String[] strArr) {
        String str2 = "CREATE TABLE " + str + "(_id INTEGER  PRIMARY KEY ,";
        for (int i = 0; i < strArr.length - 1; i += 2) {
            if (i != 0) {
                str2 = str2 + Constants.ACCEPT_TIME_SEPARATOR_SP;
            }
            str2 = str2 + strArr[i] + StringUtils.SPACE + strArr[i + 1];
        }
        sQLiteDatabase.execSQL(str2 + ");");
    }

    public static String getMd5Digest(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(getBytes(str));
            return String.format("%1$032X", new BigInteger(1, instance.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getBytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        }
    }

    public static String[] toStrArray(List<String> list) {
        String[] strArr = new String[list.size()];
        list.toArray(strArr);
        return strArr;
    }

    public static long[] toLongArray(List<Long> list) {
        long[] jArr = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            jArr[i] = list.get(i).longValue();
        }
        return jArr;
    }

    public static int[] toIntArray(List<Integer> list) {
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = list.get(i).intValue();
        }
        return iArr;
    }

    public static String getMD5(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject.getString("bind_type").equalsIgnoreCase(str2)) {
                    return jSONObject.getString("contact_value");
                }
            }
        } catch (JSONException unused) {
        }
        return null;
    }

    public static void printCallStack(String str) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println(str);
        printWriter.println(String.format("Current thread id (%s); thread name (%s)", Long.valueOf(Thread.currentThread().getId()), Thread.currentThread().getName()));
        new Throwable("Call stack").printStackTrace(printWriter);
        Log.v(a, stringWriter.toString());
    }

    public static String getCallStack(Exception exc) {
        StringWriter stringWriter = new StringWriter();
        exc.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static void printCursor(Cursor cursor) {
        Log.v(a, "Print out the cursor info");
        Log.v(a, String.format("Cursor.count = %d", Integer.valueOf(cursor.getCount())));
        String[] columnNames = cursor.getColumnNames();
        Log.v(a, "Columns");
        Log.v(a, XMStringUtils.join(columnNames, Constants.ACCEPT_TIME_SEPARATOR_SP));
        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                Log.v(a, String.format("Row %d", Integer.valueOf(i)));
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                for (int i2 = 0; i2 < cursor.getColumnCount(); i2++) {
                    printWriter.print(cursor.getColumnName(i2));
                    printWriter.print("=");
                    printWriter.print(cursor.getString(i2));
                    printWriter.println();
                }
                Log.v(a, stringWriter.toString());
                i++;
            } while (cursor.moveToNext());
        }
    }

    public static String partialNormalizePhoneNum(String str) {
        return str.replace(PassportUI.CHINA_COUNTRY_CODE, "");
    }

    public static String getRecipientsDivider() {
        return "samsung".equalsIgnoreCase(Build.BRAND) ? Constants.ACCEPT_TIME_SEPARATOR_SP : (TextUtils.isEmpty(Build.MODEL) || !Build.MODEL.toUpperCase().contains("OMS")) ? ";" : Constants.ACCEPT_TIME_SEPARATOR_SP;
    }

    public static boolean isSDCardUnavailable() {
        return Environment.getExternalStorageState().equals("removed");
    }

    public static boolean isSDCardBusy() {
        return !Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isSDCardFull() {
        return getSDCardAvailableBytes() <= 102400;
    }

    public static boolean isSDCardUseful() {
        return !isSDCardBusy() && !isSDCardFull() && !isSDCardUnavailable();
    }

    public static long getSDCardAvailableBytes() {
        if (isSDCardBusy()) {
            return 0L;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return statFs.getBlockSize() * (statFs.getAvailableBlocks() - 4);
    }

    public static long getSDCardTotalSize() {
        if (isSDCardBusy()) {
            return 0L;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return statFs.getBlockSize() * statFs.getBlockCount();
    }

    public static boolean isWIFIConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || 1 != activeNetworkInfo.getType()) ? false : true;
    }

    public static String getActiveConnPoint(Context context) {
        NetworkInfo activeNetworkInfo;
        if (isWIFIConnected(context)) {
            return Network.NETWORK_TYPE_WIFI;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) ? "" : activeNetworkInfo.getExtraInfo();
    }

    public static boolean saveBitmap(Bitmap bitmap, String str) {
        return saveBitmap(bitmap, str, Bitmap.CompressFormat.PNG, 100);
    }

    public static boolean saveBitmap(Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat, int i) {
        Throwable th;
        FileOutputStream fileOutputStream;
        IOException e;
        FileOutputStream fileOutputStream2;
        try {
            fileOutputStream = null;
            try {
                createDirForNewFile(str);
                File file = new File(str);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fileOutputStream2 = new FileOutputStream(file);
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            boolean compress = bitmap.compress(compressFormat, i, fileOutputStream2);
            try {
                fileOutputStream2.close();
            } catch (IOException e3) {
                Log.e(a, Log.getStackTraceString(e3));
            }
            return compress;
        } catch (IOException e4) {
            e = e4;
            fileOutputStream = fileOutputStream2;
            Log.e(a, Log.getStackTraceString(e));
            if (fileOutputStream == null) {
                return false;
            }
            try {
                fileOutputStream.close();
                return false;
            } catch (IOException e5) {
                Log.e(a, Log.getStackTraceString(e5));
                return false;
            }
        } catch (Throwable th3) {
            th = th3;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e6) {
                    Log.e(a, Log.getStackTraceString(e6));
                }
            }
            throw th;
        }
    }

    public static boolean cropImageFileAsSquare(String str, File file, int i) throws IOException {
        Rect rect;
        Bitmap decodeFile = decodeFile(str, i, i);
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        int width = decodeFile.getWidth();
        int height = decodeFile.getHeight();
        Paint paint = new Paint();
        if (width > height) {
            rect = new Rect((width - height) / 2, 0, (width + height) / 2, height);
        } else {
            rect = new Rect(0, (height - width) / 2, width, (height + width) / 2);
        }
        canvas.drawBitmap(decodeFile, rect, new Rect(0, 0, i, i), paint);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        boolean compress = createBitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
        fileOutputStream.close();
        return compress;
    }

    public static boolean cropImageFile(String str, File file, int i, int i2, Bitmap.CompressFormat compressFormat) throws IOException {
        Rect rect;
        Bitmap decodeFile = decodeFile(str, i, i2);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        int width = decodeFile.getWidth();
        int height = decodeFile.getHeight();
        Paint paint = new Paint();
        if (width > height) {
            rect = new Rect((width - height) / 2, 0, (width + height) / 2, height);
        } else {
            rect = new Rect(0, (height - width) / 2, width, (height + width) / 2);
        }
        canvas.drawBitmap(decodeFile, rect, new Rect(0, 0, i, i2), paint);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        boolean compress = createBitmap.compress(compressFormat, 0, fileOutputStream);
        fileOutputStream.close();
        return compress;
    }

    public static Bitmap compressBitmap(Bitmap bitmap, int i, int i2, Bitmap.Config config) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width < i && height < i) {
            return bitmap;
        }
        Rect rect = new Rect(0, 0, width, height);
        Rect rect2 = new Rect(0, 0, i, i2);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, config);
        new Canvas(createBitmap).drawBitmap(bitmap, rect, rect2, new Paint());
        return createBitmap;
    }

    public static Bitmap compressBitmapWithNoDistortion(Bitmap bitmap, int i, int i2, Bitmap.Config config) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width < i && height < i) {
            return bitmap;
        }
        double d = width;
        double d2 = d / i;
        double d3 = height;
        double d4 = d3 / i2;
        if (d2 > d4) {
            d4 = d2;
        }
        return compressBitmap(bitmap, (int) (d / d4), (int) (d3 / d4), config);
    }

    public static void compressWithNoDistortion(String str, int i, int i2) {
        Bitmap bitmap;
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        if (i <= 0 || i <= 0 || (decodeFile.getWidth() <= i && decodeFile.getHeight() <= i)) {
            bitmap = decodeFile;
        } else {
            bitmap = compressBitmapWithNoDistortion(decodeFile, i, i2, Bitmap.Config.ARGB_8888);
            if (bitmap != decodeFile) {
                decodeFile.recycle();
            }
        }
        saveBitmap(bitmap, str);
        bitmap.recycle();
    }

    public static Bitmap compressBitmapWithNoDistortion(Bitmap bitmap, int i, int i2) {
        return (i <= 0 || i <= 0) ? bitmap : (bitmap.getWidth() > i || bitmap.getHeight() > i) ? compressBitmapWithNoDistortion(bitmap, i, i2, Bitmap.Config.ARGB_8888) : bitmap;
    }

    public static Bitmap decodeFile(String str, int i, int i2) {
        return decodeFile(str, i, i2, false);
    }

    public static Bitmap decodeFile(String str, int i, int i2, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i3 = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            int i4 = options.outWidth;
            int i5 = options.outHeight;
            while (i4 > i && i5 > i2) {
                i3++;
                i4 = options.outWidth / i3;
                i5 = options.outHeight / i3;
            }
            options.inSampleSize = i3;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            return BitmapFactory.decodeFile(str, options);
        } catch (OutOfMemoryError e) {
            if (!z) {
                Log.e(a, Log.getStackTraceString(e));
                return null;
            }
            throw e;
        }
    }

    public static Bitmap decodeResource(int i, int i2, int i3, Context context) {
        return decodeResource(i, i2, i3, context, Bitmap.Config.RGB_565);
    }

    public static Bitmap decodeResource(int i, int i2, int i3, Context context, Bitmap.Config config) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i4 = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), i, options);
            int i5 = options.outWidth;
            int i6 = options.outHeight;
            while (true) {
                if (i5 <= i2 && i6 <= i3) {
                    options.inSampleSize = i4;
                    options.inJustDecodeBounds = false;
                    options.inPreferredConfig = config;
                    return BitmapFactory.decodeResource(context.getResources(), i, options);
                }
                i4++;
                i5 = options.outWidth / i4;
                i6 = options.outHeight / i4;
            }
        } catch (OutOfMemoryError e) {
            Log.e(a, Log.getStackTraceString(e));
            return null;
        }
    }

    public static Bitmap decodeFile(String str, int i) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i2 = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            int i3 = options.outWidth;
            int i4 = options.outHeight;
            while ((i3 * i3) + (i4 * i4) > i * i) {
                i2++;
                i3 = options.outWidth / i2;
                i4 = options.outHeight / i2;
            }
            options.inSampleSize = i2;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            return BitmapFactory.decodeFile(str, options);
        } catch (OutOfMemoryError e) {
            Log.e(a, "decodeFile", e);
            return null;
        }
    }

    public static Bitmap decodeFile2(String str, int i, int i2) throws IOException {
        Bitmap bitmap;
        int i3 = 0;
        try {
            if (i <= 0 || i2 <= 0) {
                bitmap = BitmapFactory.decodeFile(str, null);
            } else {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(str, options);
                int i4 = options.outWidth;
                int i5 = 1;
                for (int i6 = options.outHeight; i4 > i * 1.5d && i6 > i2 * 1.5d; i6 >>= 1) {
                    i5 <<= 1;
                    i4 >>= 1;
                }
                options.inSampleSize = i5;
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(str, options);
            }
            if (bitmap == null) {
                return null;
            }
            try {
                i3 = (int) ImageExifUtils.exifOrientationToDegrees(new ExifInterface(str).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1));
            } catch (Exception e) {
                Log.e(a, Log.getStackTraceString(e));
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(i3);
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (createBitmap != bitmap) {
                bitmap.recycle();
            }
            return createBitmap;
        } catch (OutOfMemoryError unused) {
            throw new IOException("decode file out of memory");
        }
    }

    public static boolean mergeBitmap(Bitmap bitmap, String str, Rect rect, Paint paint) {
        try {
            return mergeBitmap(bitmap, BitmapFactory.decodeFile(str), rect, paint);
        } catch (OutOfMemoryError e) {
            Log.e(a, Log.getStackTraceString(e));
            return false;
        }
    }

    public static boolean mergeBitmap(Bitmap bitmap, Bitmap bitmap2, Rect rect, Paint paint) {
        if (bitmap2 == null) {
            return false;
        }
        try {
            new Canvas(bitmap).drawBitmap(bitmap2, new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight()), rect, paint);
            return true;
        } catch (OutOfMemoryError e) {
            Log.e(a, Log.getStackTraceString(e));
            return false;
        }
    }

    public static boolean mergeBitmap(Bitmap bitmap, Bitmap bitmap2, Paint paint) {
        return mergeBitmap(bitmap, bitmap2, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);
    }

    public static double getColorDistance(int i, int i2) {
        return Math.sqrt(Math.pow(Color.red(i) - Color.red(i2), 2.0d) + Math.pow(Color.green(i) - Color.green(i2), 2.0d) + Math.pow(Color.blue(i) - Color.blue(i2), 2.0d));
    }

    /* loaded from: classes3.dex */
    public static final class TitleAndListBitmaps {
        private final Bitmap a;
        private final Bitmap b;

        public TitleAndListBitmaps(Bitmap bitmap, Bitmap bitmap2) {
            this.a = bitmap;
            this.b = bitmap2;
        }

        public Bitmap getTitleBitmap() {
            return this.a;
        }

        public Bitmap getListBitmap() {
            return this.b;
        }
    }

    public static boolean mergeBitmap(Bitmap bitmap, Bitmap bitmap2, Rect rect) {
        return mergeBitmap(bitmap, bitmap2, rect, new Paint());
    }

    public static boolean mergeBitmap(Bitmap bitmap, Bitmap bitmap2) {
        return mergeBitmap(bitmap, bitmap2, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
    }

    public static void createDirForNewFile(String str) {
        File file = new File(str.substring(0, str.lastIndexOf("/")));
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getStrangerDisplayName(int i) {
        return "<" + i + ">";
    }

    public static String getStrangerDisplayName(String str) {
        return "<" + str + ">";
    }

    public static boolean isIntentAvailable(Context context, String str) {
        return context.getPackageManager().queryIntentActivities(new Intent(str), 65536).size() > 0;
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    public static void copyFile(File file, File file2) throws IOException {
        Throwable th;
        FileInputStream fileInputStream;
        if (!file.getAbsolutePath().equals(file2.getAbsolutePath())) {
            FileOutputStream fileOutputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read >= 0) {
                                fileOutputStream2.write(bArr, 0, read);
                            } else {
                                fileInputStream.close();
                                fileOutputStream2.close();
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                th = th4;
                fileInputStream = null;
            }
        }
    }

    public static void scanMediaFile(Context context, String str) {
        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(str))));
    }

    public static boolean isSIMCardReady(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimState() == 5;
    }

    public static String getImei(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getImei();
    }

    public static boolean compressBitmap(String str, int i) throws IOException {
        Throwable th;
        Bitmap bitmap;
        File file = new File(str + ".temp");
        int i2 = 80;
        FileOutputStream fileOutputStream = null;
        try {
            if (1 == i) {
                bitmap = BitmapFactory.decodeFile(str);
                i2 = 50;
            } else {
                bitmap = decodeFile2(str, IMAGE_WIDTH_THRESHOLD, 800);
            }
            if (bitmap == null) {
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                } catch (IOException unused) {
                }
                return false;
            }
            fileOutputStream = new FileOutputStream(file);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, i2, fileOutputStream);
                try {
                    fileOutputStream.close();
                    bitmap.recycle();
                    File file2 = new File(str);
                    if (file2.exists()) {
                        file2.delete();
                    }
                    file.renameTo(file2);
                    try {
                        fileOutputStream.close();
                        if (file.exists()) {
                            file.delete();
                        }
                    } catch (IOException unused2) {
                    }
                    return true;
                } catch (IOException e) {
                    throw e;
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused3) {
                        throw th;
                    }
                }
                if (file.exists()) {
                    file.delete();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static void zip(ZipOutputStream zipOutputStream, File file, String str, FileFilter fileFilter) throws IOException {
        File[] fileArr;
        if (str == null) {
            str = "";
        }
        try {
            if (file.isDirectory()) {
                if (fileFilter != null) {
                    fileArr = file.listFiles(fileFilter);
                } else {
                    fileArr = file.listFiles();
                }
                zipOutputStream.putNextEntry(new ZipEntry(str + File.separator));
                String str2 = TextUtils.isEmpty(str) ? "" : str + File.separator;
                for (int i = 0; i < fileArr.length; i++) {
                    zip(zipOutputStream, fileArr[i], str2 + fileArr[i].getName(), null);
                }
                return;
            }
            if (!TextUtils.isEmpty(str)) {
                zipOutputStream.putNextEntry(new ZipEntry(URLEncoder.encode(str)));
            } else {
                Date date = new Date();
                zipOutputStream.putNextEntry(new ZipEntry(String.valueOf(date.getTime()) + ".txt"));
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    zipOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    return;
                }
            }
        } catch (IOException e) {
            Log.e(a, Log.getStackTraceString(e));
        }
    }

    public static void highlightKeyword(TextView textView, String str, String str2, int i) {
        highlightKeyword(textView, str, new String[]{str2}, i, false);
    }

    public static void highlightKeyword(TextView textView, CharSequence charSequence, CharSequence charSequence2, int i) {
        highlightKeyword(textView, charSequence, new CharSequence[]{charSequence2}, i, false);
    }

    public static void highlightKeyword(TextView textView, CharSequence charSequence, CharSequence[] charSequenceArr, int i, boolean z) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        for (CharSequence charSequence2 : charSequenceArr) {
            int i2 = 0;
            while (true) {
                int a2 = a(charSequence, charSequence2, i2, z);
                if (a2 > -1) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(i), a2, charSequence2.length() + a2, 33);
                    i2 = a2 + charSequence2.length();
                }
            }
        }
        textView.setText(spannableStringBuilder);
    }

    private static int a(CharSequence charSequence, CharSequence charSequence2, int i, boolean z) {
        int i2;
        while (i < charSequence.length()) {
            for (int i3 = 0; i3 < charSequence2.length() && (i2 = i + i3) < charSequence.length() && a(charSequence.charAt(i2), charSequence2.charAt(i3), z); i3++) {
                if (i3 == charSequence2.length() - 1) {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

    private static boolean a(char c2, char c3, boolean z) {
        return z ? c2 == c3 || Character.toLowerCase(c2) == Character.toLowerCase(c3) : c2 == c3;
    }

    public static CharSequence addClickableSpan(final Context context, String str, String str2, final View.OnClickListener onClickListener, final boolean z, final int i) {
        int indexOf = str.indexOf(str2);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        if (indexOf >= 0) {
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.xiaomi.mico.base.utils.CommonUtils.1
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    onClickListener.onClick(view);
                }
            }, indexOf, str2.length() + indexOf, 33);
            spannableStringBuilder.setSpan(new CharacterStyle() { // from class: com.xiaomi.mico.base.utils.CommonUtils.2
                @Override // android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setUnderlineText(z);
                    textPaint.setColor(context.getResources().getColor(i));
                }
            }, indexOf, str2.length() + indexOf, 33);
        }
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder addClickableSpanToMark(String str, final int i, int i2, final boolean z, final View.OnClickListener onClickListener) {
        int indexOf = str.indexOf(91);
        int lastIndexOf = str.lastIndexOf(93);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str.replaceAll("\\[|\\]", ""));
        if (onClickListener != null) {
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.xiaomi.mico.base.utils.CommonUtils.3
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    onClickListener.onClick(view);
                }
            }, Math.max(0, indexOf), Math.max(0, lastIndexOf - 1), 33);
        }
        int i3 = lastIndexOf - 1;
        spannableStringBuilder.setSpan(new CharacterStyle() { // from class: com.xiaomi.mico.base.utils.CommonUtils.4
            @Override // android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(z);
                textPaint.setColor(i);
            }
        }, Math.max(0, indexOf), Math.max(0, i3), 33);
        spannableStringBuilder.setSpan(new BackgroundColorSpan(i2), Math.max(0, indexOf), Math.max(0, i3), 33);
        return spannableStringBuilder;
    }

    public static boolean unZip(String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return false;
        }
        if (!str2.endsWith("/")) {
            str2 = str2 + "/";
        }
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
            byte[] bArr = new byte[4096];
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    String name = nextEntry.getName();
                    File file = new File(str2 + name);
                    if (!name.endsWith("/")) {
                        File file2 = new File(file.getParent());
                        if (!file2.exists() || !file2.isDirectory()) {
                            file2.mkdirs();
                            hideFromMediaScanner(file2);
                        }
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), 4096);
                        while (true) {
                            int read = zipInputStream.read(bArr, 0, 4096);
                            if (read == -1) {
                                break;
                            }
                            bufferedOutputStream.write(bArr, 0, read);
                        }
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                    }
                } else {
                    zipInputStream.close();
                    return true;
                }
            }
        } catch (IOException e) {
            Log.e(a, Log.getStackTraceString(e));
            return false;
        }
    }

    public static void hideFromMediaScanner(File file) {
        File file2 = new File(file, ".nomedia");
        if (!file2.exists() || !file2.isFile()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                Log.e(a, Log.getStackTraceString(e));
            }
        }
    }

    public static String getUniqueFileName(File file, String str) {
        File file2 = new File(file, str);
        if (!file2.exists()) {
            return file2.getAbsolutePath();
        }
        int lastIndexOf = str.lastIndexOf(46);
        String str2 = "";
        if (lastIndexOf > 0) {
            String substring = str.substring(0, lastIndexOf);
            str2 = str.substring(lastIndexOf + 1);
            str = substring;
        }
        int i = 1;
        while (true) {
            File file3 = new File(file, String.format("%s_%d.%s", str, Integer.valueOf(i), str2));
            if (!file3.exists()) {
                return file3.getAbsolutePath();
            }
            i++;
        }
    }

    public static boolean isStupidLephone() {
        return !TextUtils.isEmpty(Build.PRODUCT) && Build.PRODUCT.contains("lephone");
    }

    public static String getCountryISO(Context context) {
        return getCountryISOFromSimCard(context);
    }

    public static boolean isAllChineseSimCard(Context context) {
        String countryISO = getCountryISO(context);
        return "CN".equalsIgnoreCase(countryISO) || "TW".equalsIgnoreCase(countryISO) || "HK".equalsIgnoreCase(countryISO);
    }

    public static boolean isChineseISO(Context context) {
        return "CN".equalsIgnoreCase(getCountryISO(context));
    }

    public static String getCountryISOFromSimCard(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimCountryIso();
    }

    public static boolean isChinaMobile(Context context) {
        String simOperator = ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
        return "46000".equals(simOperator) || "46002".equals(simOperator) || "46007".equals(simOperator);
    }

    public static boolean isChinaUnicom(Context context) {
        return "46001".equals(((TelephonyManager) context.getSystemService("phone")).getSimOperator());
    }

    public static boolean isChinaTelecom(Context context) {
        return "46003".equals(((TelephonyManager) context.getSystemService("phone")).getSimOperator());
    }

    public static boolean isChineseSimCard(Context context) {
        return "CN".equalsIgnoreCase(getCountryISOFromSimCard(context));
    }

    public static boolean isChineseLocale(Context context) {
        return Locale.CHINA.toString().equalsIgnoreCase(Locale.getDefault().toString()) || Locale.CHINESE.toString().equalsIgnoreCase(Locale.getDefault().toString());
    }

    public static boolean isGreatChinaLocale(Context context) {
        return isChineseLocale(context) || Locale.TAIWAN.toString().equalsIgnoreCase(Locale.getDefault().toString()) || "zh_HK".equalsIgnoreCase(Locale.getDefault().toString()) || Locale.TRADITIONAL_CHINESE.toString().equalsIgnoreCase(Locale.getDefault().toString());
    }

    public static boolean isRichNoise() {
        return "Desire HD".equals(Build.MODEL);
    }

    public static boolean isMIUIRom(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.miui.cloudservice", 16384) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static <T> boolean IsTwoArrayListContainsSameElemet(ArrayList<T> arrayList, ArrayList<T> arrayList2) {
        if (arrayList == null || arrayList2 == null || arrayList.isEmpty() || arrayList2.isEmpty()) {
            return false;
        }
        Iterator<T> it = arrayList.iterator();
        while (it.hasNext()) {
            if (arrayList2.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static int getCheckSumCRC32(File file) throws IOException {
        Throwable th;
        CheckedInputStream checkedInputStream = null;
        try {
            CheckedInputStream checkedInputStream2 = new CheckedInputStream(new FileInputStream(file), new CRC32());
            try {
                while (checkedInputStream2.read(new byte[1024]) >= 0) {
                }
                int value = (int) checkedInputStream2.getChecksum().getValue();
                checkedInputStream2.close();
                return value;
            } catch (Throwable th2) {
                th = th2;
                checkedInputStream = checkedInputStream2;
                if (checkedInputStream != null) {
                    checkedInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static String getFromAssets(Context context, String str, String str2) {
        String str3 = "";
        byte[] bArr = new byte[8192];
        try {
            InputStream open = context.getResources().getAssets().open(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = open.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    open.close();
                    str3 = byteArrayOutputStream.toString();
                    byteArrayOutputStream.close();
                    return str3;
                }
            }
        } catch (IOException e) {
            Log.e(a, Log.getStackTraceString(e));
            return str3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v16, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.io.InputStream] */
    public static Bitmap getBitmapFromAssets(Context context, String str) {
        Throwable th;
        Bitmap bitmap;
        IOException e;
        OutOfMemoryError e2;
        try {
            try {
                bitmap = null;
            } catch (IOException e3) {
                String str2 = a;
                context = Log.getStackTraceString(e3);
                Log.e(str2, context);
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            context = context.getResources().getAssets().open(str);
        } catch (IOException e4) {
            e = e4;
            context = 0;
        } catch (OutOfMemoryError e5) {
            e2 = e5;
            context = 0;
        } catch (Throwable th3) {
            th = th3;
            context = 0;
            if (context != 0) {
                try {
                    context.close();
                } catch (IOException e6) {
                    Log.e(a, Log.getStackTraceString(e6));
                }
            }
            throw th;
        }
        try {
            bitmap = BitmapFactory.decodeStream(context);
        } catch (IOException e7) {
            e = e7;
            Log.e(a, Log.getStackTraceString(e));
            if (context != 0) {
                context.close();
                context = context;
            }
            return bitmap;
        } catch (OutOfMemoryError e8) {
            e2 = e8;
            Log.e(a, Log.getStackTraceString(e2));
            if (context != 0) {
                context.close();
                context = context;
            }
            return bitmap;
        }
        if (context != 0) {
            context.close();
            context = context;
        }
        return bitmap;
    }

    public static boolean isXMPhone() {
        return Build.MODEL.contains("mione") || Build.MODEL.contains("MI-ONE");
    }

    public static boolean isAudioVoiceCallDisabled() {
        return Build.MODEL.contains("ZTE");
    }

    public static String getCurrentCallstack() {
        StringWriter stringWriter = new StringWriter();
        new Exception().printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static String[] getImageInfo(Context context, Uri uri) {
        if (uri.getScheme().equalsIgnoreCase("file")) {
            String path = uri.getPath();
            return new String[]{path, getImageMimeType(path)};
        }
        Cursor query = context.getContentResolver().query(uri, new String[]{"_data", "mime_type"}, null, null, null);
        if (query == null) {
            return null;
        }
        try {
            query.moveToFirst();
            return new String[]{query.getString(0), query.getString(1)};
        } finally {
            if (query != null && !query.isClosed()) {
                query.close();
            }
        }
    }

    public static String getImageMimeType(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        String substring = lastIndexOf < 0 ? "" : str.substring(lastIndexOf + 1);
        return "image/" + substring;
    }

    public static void createHomeScreenShortcut(Context context, Intent intent, String str, int i, boolean z) {
        Intent intent2 = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent2.putExtra("android.intent.extra.shortcut.INTENT", intent);
        intent2.putExtra("android.intent.extra.shortcut.NAME", str);
        intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(context, i));
        intent2.putExtra("duplicate", z);
        context.sendBroadcast(intent2);
    }

    public static boolean isWifiEnabled(Context context) {
        return ((WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI)).isWifiEnabled();
    }

    public static String getCurrentWifiSSID(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI)).getConnectionInfo();
        if (connectionInfo == null) {
            return null;
        }
        String ssid = connectionInfo.getSSID();
        return (ssid == null || !ssid.startsWith("\"") || !ssid.endsWith("\"")) ? ssid : ssid.substring(1, ssid.length() - 1);
    }

    public static byte[] getFileSha1Digest(String str) throws NoSuchAlgorithmException, IOException {
        MessageDigest instance = MessageDigest.getInstance("SHA1");
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        byte[] bArr = new byte[4096];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read == -1) {
                return instance.digest();
            }
            instance.update(bArr, 0, read);
        }
    }

    public static String byte2hex(byte[] bArr) {
        String str = "";
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            str = hexString.length() == 1 ? str + "0" + hexString : str + hexString;
        }
        return str;
    }

    public static boolean isValidEmailAddress(String str) {
        return c.matcher(str).matches();
    }

    public static boolean isPackageInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 16384);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void openMapByGoogle(Activity activity, double d, double d2, String str, String str2) {
        boolean isPackageInstalled = isPackageInstalled(activity, "com.google.android.apps.maps");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://maps.google.com/maps?q=loc:");
        stringBuffer.append(d);
        stringBuffer.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        stringBuffer.append(d2);
        if (isPackageInstalled) {
            stringBuffer.append("(");
            stringBuffer.append(str);
            stringBuffer.append("@");
            stringBuffer.append(str2);
            stringBuffer.append(")");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            activity.startActivity(intent);
            return;
        }
        activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString())));
    }

    public static void restartAppTask(Context context) {
        restartAppTask(context, null, null);
    }

    public static void deleteDirs(File file) {
        Log.v(a, "deleteDirs filePath = " + file.getAbsolutePath());
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    if (file2.isFile()) {
                        file2.delete();
                    } else {
                        deleteDirs(file2);
                    }
                }
            }
            file.delete();
        }
    }

    public static <T> T[] newArrayByClass(Class<T> cls, int i) {
        return (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
    }

    public static <T> void copyArray(T[] tArr, int i, int i2, T[] tArr2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            tArr2[i3 - i] = tArr[i3];
        }
    }

    public static <T> void addAll(T[] tArr, Collection<T> collection) {
        for (T t : tArr) {
            collection.add(t);
        }
    }

    public static <T> T[] toArray(Class<T> cls, Collection<T> collection) {
        return (T[]) collection.toArray(newArrayByClass(cls, 0));
    }

    public static <T> T[] createCopyOfArray(Class<T> cls, T[] tArr, int i, int i2) {
        T[] tArr2 = (T[]) newArrayByClass(cls, i2);
        copyArray(tArr, i, i2, tArr2);
        return tArr2;
    }

    public static int[] createCopyOfIntArray(int[] iArr, int i, int i2) {
        int[] iArr2 = new int[i2];
        for (int i3 = i; i3 < i + i2; i3++) {
            iArr2[i3 - i] = iArr[i3];
        }
        return iArr2;
    }

    public static <T> ArrayList<T> toArrayList(T[] tArr) {
        ArrayList<T> arrayList = new ArrayList<>(tArr.length);
        for (T t : tArr) {
            arrayList.add(t);
        }
        return arrayList;
    }

    public static String generateImageThumbUrl(String str, int i) {
        return generateImageThumbUrl(str, i, true);
    }

    public static String generateImageThumbUrl(String str, int i, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("?thumb=");
        sb.append(String.valueOf(i));
        sb.append("x");
        sb.append(String.valueOf(i));
        sb.append(z ? "&scale=auto" : "");
        return sb.toString();
    }

    public static int getCurrentVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(a, Log.getStackTraceString(e));
            return -1;
        }
    }

    public static long versionCodeToLong(String str, int i) {
        long j = 0;
        if (str == null || str.length() == 0) {
            return 0L;
        }
        String[] split = str.split("\\.");
        int i2 = 1;
        for (int length = split.length - 1; length >= 0; length--) {
            j += Integer.valueOf(split[length]).intValue() * i2;
            i2 *= i;
        }
        return j;
    }

    public static Bitmap getResourceBitmap(Context context, LruCache<String, Bitmap> lruCache, String str, int i) {
        return getResourceBitmap(context, lruCache, str, i, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap getResourceBitmap(Context context, LruCache<String, Bitmap> lruCache, String str, int i, Bitmap.Config config) {
        Bitmap bitmap = lruCache == null ? null : lruCache.get(str);
        if (bitmap == null) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = config;
                bitmap = BitmapFactory.decodeResource(context.getResources(), i, options);
                if (!(lruCache == null || bitmap == null)) {
                    lruCache.put(str, bitmap);
                }
            } catch (OutOfMemoryError e) {
                Log.e(a, Log.getStackTraceString(e));
            }
        }
        return bitmap;
    }

    public static BitmapDrawable getResourceBitmapDrawable(Context context, LruCache<String, Bitmap> lruCache, String str, int i) {
        return getResourceBitmapDrawable(context, lruCache, str, i, Bitmap.Config.ARGB_8888);
    }

    public static BitmapDrawable getResourceBitmapDrawable(Context context, LruCache<String, Bitmap> lruCache, String str, int i, Bitmap.Config config) {
        Bitmap resourceBitmap = getResourceBitmap(context, lruCache, str, i, config);
        if (resourceBitmap != null) {
            return new BitmapDrawable(resourceBitmap);
        }
        return null;
    }

    public static void disableRotation(Activity activity) {
        int i;
        int i2 = activity.getResources().getConfiguration().orientation;
        int orientation = activity.getWindowManager().getDefaultDisplay().getOrientation();
        int i3 = 8;
        if (Build.VERSION.SDK_INT <= 8) {
            i3 = 0;
            i = 1;
        } else {
            i = 9;
        }
        if (orientation == 0 || orientation == 1) {
            if (i2 == 1) {
                activity.setRequestedOrientation(1);
            } else if (i2 == 2) {
                activity.setRequestedOrientation(0);
            }
        } else if (orientation != 2 && orientation != 3) {
        } else {
            if (i2 == 1) {
                activity.setRequestedOrientation(i3);
            } else if (i2 == 2) {
                activity.setRequestedOrientation(i);
            }
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable th) {
                Log.e(a, Log.getStackTraceString(th));
            }
        }
    }

    public static Activity getRootActivity(Activity activity) {
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        return activity;
    }

    public static boolean isEmpty(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public static byte[] byteArraysConcat(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static DecimalFormat getDecimalFormat(double d) {
        DecimalFormat decimalFormat = new DecimalFormat("###");
        if (d >= 100.0d) {
            return new DecimalFormat("###");
        }
        if (d >= 10.0d) {
            return new DecimalFormat("##.0");
        }
        return d >= 0.0d ? new DecimalFormat("0.00") : decimalFormat;
    }

    public static void setFormatStorageSize(long j, TextView textView, TextView textView2) {
        String str;
        float f;
        DecimalFormat decimalFormat = new DecimalFormat("###");
        if (j < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            f = (float) j;
            if (f >= 1000.0f) {
                f /= 1024.0f;
                str = "K";
                decimalFormat = getDecimalFormat(f);
            } else {
                str = "B";
            }
        } else {
            f = ((float) j) / 1024.0f;
            if (f < 1024.0f) {
                if (f >= 1000.0f) {
                    f /= 1024.0f;
                    str = "M";
                } else {
                    str = "K";
                }
                decimalFormat = getDecimalFormat(f);
            } else {
                f /= 1024.0f;
                if (f < 1024.0f) {
                    if (f >= 1000.0f) {
                        f /= 1024.0f;
                        str = "G";
                    } else {
                        str = "M";
                    }
                    decimalFormat = getDecimalFormat(f);
                } else {
                    f /= 1024.0f;
                    str = "G";
                    decimalFormat = getDecimalFormat(f);
                }
            }
        }
        textView.setText(decimalFormat.format(f));
        textView2.setText(str);
    }

    public static String getFormatStorageSizeText(long j) {
        String str;
        float f;
        DecimalFormat decimalFormat = new DecimalFormat("###");
        if (j < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            f = (float) j;
            if (f >= 1000.0f) {
                f /= 1024.0f;
                str = "K";
                decimalFormat = getDecimalFormat(f);
            } else {
                str = "B";
            }
        } else {
            f = ((float) j) / 1024.0f;
            if (f < 1024.0f) {
                if (f >= 1000.0f) {
                    f /= 1024.0f;
                    str = "M";
                } else {
                    str = "K";
                }
                decimalFormat = getDecimalFormat(f);
            } else {
                f /= 1024.0f;
                if (f < 1024.0f) {
                    if (f >= 1000.0f) {
                        f /= 1024.0f;
                        str = "G";
                    } else {
                        str = "M";
                    }
                    decimalFormat = getDecimalFormat(f);
                } else {
                    f /= 1024.0f;
                    str = "G";
                    decimalFormat = getDecimalFormat(f);
                }
            }
        }
        return decimalFormat.format(f) + str;
    }

    public static String getFormatSize(long j) {
        String str;
        float f;
        if (j < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            f = (float) j;
            str = "B";
        } else {
            f = ((float) j) / 1024.0f;
            if (f < 1024.0f) {
                str = "KB";
            } else {
                f /= 1024.0f;
                if (f < 1024.0f) {
                    str = "MB";
                } else {
                    f /= 1024.0f;
                    str = "GB";
                }
            }
        }
        if (str.equals("B")) {
            return String.valueOf(j) + str;
        }
        DecimalFormat decimalFormat = new DecimalFormat("####.#");
        return decimalFormat.format(f) + str;
    }

    public static String[] getFormatSizeSeparate(long j) {
        String str;
        float f;
        if (j < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            f = (float) j;
            str = "B";
        } else {
            f = ((float) j) / 1024.0f;
            if (f < 1024.0f) {
                str = "KB";
            } else {
                f /= 1024.0f;
                if (f < 1024.0f) {
                    str = "MB";
                } else {
                    f /= 1024.0f;
                    str = "GB";
                }
            }
        }
        return str.equals("B") ? new String[]{String.valueOf(j), str} : new String[]{new DecimalFormat("####.#").format(f), str};
    }

    public static String getFormatSpeed(long j) {
        String str;
        float f;
        if (j < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            f = (float) j;
            str = "B/S";
        } else {
            f = ((float) j) / 1024.0f;
            if (f < 1024.0f) {
                str = "KB/S";
            } else {
                f /= 1024.0f;
                if (f < 1024.0f) {
                    str = "MB/S";
                } else {
                    f /= 1024.0f;
                    str = "GB/S";
                }
            }
        }
        if (str.equals("B/S")) {
            return String.valueOf(j) + str;
        }
        DecimalFormat decimalFormat = new DecimalFormat("####.#");
        return decimalFormat.format(f) + str;
    }

    public static String[] getFormatSpeedSeparate(long j) {
        String str;
        float f;
        if (j < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            f = (float) j;
            str = "B/S";
        } else {
            f = ((float) j) / 1024.0f;
            if (f < 1024.0f) {
                str = "KB/S";
            } else {
                f /= 1024.0f;
                if (f < 1024.0f) {
                    str = "MB/S";
                } else {
                    f /= 1024.0f;
                    str = "GB/S";
                }
            }
        }
        return str.equals("B/S") ? new String[]{String.valueOf(j), str} : new String[]{new DecimalFormat("####.#").format(f), str};
    }

    public static String secondToTime(long j) {
        if (j <= 0) {
            return "00:00";
        }
        int i = (int) (j / 60);
        if (i < 60) {
            return a(i) + Constants.COLON_SEPARATOR + a((int) (j % 60));
        }
        int i2 = i / 60;
        if (i2 > 99) {
            return "99:59:59";
        }
        int i3 = i % 60;
        return a(i2) + Constants.COLON_SEPARATOR + a(i3) + Constants.COLON_SEPARATOR + a((int) ((j - (i2 * CacheConstants.HOUR)) - (i3 * 60)));
    }

    private static String a(int i) {
        if (i < 0 || i >= 10) {
            return Integer.toString(i);
        }
        return "0" + Integer.toString(i);
    }

    public static String getUnifiedPath(String str) {
        return (str == null || !str.endsWith("/")) ? str : str.substring(0, str.length() - 1);
    }

    public static boolean isMainProcess(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningAppProcesses();
        String packageName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == myPid && packageName.equals(runningAppProcessInfo.processName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isForeground(Context context) {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.processName.equals(context.getPackageName())) {
                return runningAppProcessInfo.importance == 100;
            }
        }
        return false;
    }

    public static String getCurProcessName(Context context) {
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public static int comparePackageVersion(Context context, String str, int i) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 0);
            if (packageInfo.versionCode > i) {
                return 1;
            }
            return packageInfo.versionCode == i ? 0 : -1;
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    public static String getPackageNameOfApkFile(Context context, String str) {
        try {
            PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(str, 16);
            if (packageArchiveInfo != null) {
                return packageArchiveInfo.packageName;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void gotoLocationSetting(Context context) {
        Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    public static boolean equals(Object obj, Object obj2) {
        return (obj == null && obj2 == null) || !(obj == null || obj2 == null || !obj.equals(obj2));
    }

    public static boolean downloadFile(InputStream inputStream, String str) throws IOException {
        FileOutputStream fileOutputStream;
        Throwable th;
        File file = new File(str);
        if (!file.exists()) {
            file.createNewFile();
        }
        byte[] bArr = new byte[2048];
        try {
            fileOutputStream = new FileOutputStream(file);
            while (true) {
                try {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                } catch (Exception unused) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused4) {
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused5) {
                        }
                    }
                    throw th;
                }
            }
            fileOutputStream.flush();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused6) {
                }
            }
            try {
                fileOutputStream.close();
                return true;
            } catch (IOException unused7) {
                return true;
            }
        } catch (Exception unused8) {
            fileOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
    }

    public static boolean isNumber(String str) {
        return Pattern.compile("^[-\\+]?[\\d]*$").matcher(str).matches();
    }

    public static void unregisterReceiver(Context context, BroadcastReceiver broadcastReceiver) {
        if (context != null && broadcastReceiver != null) {
            try {
                context.unregisterReceiver(broadcastReceiver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
