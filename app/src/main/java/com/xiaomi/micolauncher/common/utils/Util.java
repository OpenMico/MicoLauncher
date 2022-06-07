package com.xiaomi.micolauncher.common.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class Util {
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T dynamicCast(Object obj) {
        if (obj != 0) {
            return obj;
        }
        return null;
    }

    public static String formatFloat(float f) {
        try {
            return String.format(Locale.US, "%.1f", Float.valueOf(f));
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0";
        }
    }

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static List<String> string2List(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        string2List(str, str2, arrayList);
        return arrayList;
    }

    public static void string2List(String str, String str2, List<String> list) {
        if (!str.equals("")) {
            String[] split = str.split(str2);
            for (String str3 : split) {
                list.add(str3);
            }
        }
    }

    public static String list2String(List<String> list, String str) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            sb.append(list.get(i));
            if (i != size - 1) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static int[] integerSetToInt(Set<Integer> set) {
        int i = 0;
        if (set == null) {
            return new int[0];
        }
        int[] iArr = new int[set.size()];
        for (Integer num : set) {
            iArr[i] = num.intValue();
            i++;
        }
        return iArr;
    }

    public static String msTime2Date(long j) {
        return new SimpleDateFormat(XMPassport.SIMPLE_DATE_FORMAT, Locale.SIMPLIFIED_CHINESE).format(new Date(j));
    }

    public static boolean isToday(String str) {
        return !isEmpty(str) && str.equals(new SimpleDateFormat(XMPassport.SIMPLE_DATE_FORMAT, Locale.SIMPLIFIED_CHINESE).format(new Date(System.currentTimeMillis())));
    }

    public static boolean isYesterday(String str) {
        return !isEmpty(str) && str.equals(new SimpleDateFormat(XMPassport.SIMPLE_DATE_FORMAT, Locale.SIMPLIFIED_CHINESE).format(new Date(System.currentTimeMillis() - 86400000)));
    }

    public static Bitmap getBmpByResId(Context context, int i) {
        InputStream openRawResource = context.getResources().openRawResource(i);
        Bitmap decodeStream = BitmapFactory.decodeStream(openRawResource);
        try {
            openRawResource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decodeStream;
    }

    public static boolean fileExists(Context context, String str) {
        String str2;
        if (isEmpty(str)) {
            return false;
        }
        Uri parse = Uri.parse(str);
        if (isEmpty(parse.getScheme()) || !parse.getScheme().equals("content")) {
            str2 = str.replace("file://", "");
        } else {
            str2 = a(context, parse);
        }
        if (!isEmpty(str2)) {
            return new File(str2).exists();
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r8v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r8v4 */
    private static String a(Context context, Uri uri) {
        Throwable th;
        Exception e;
        Cursor cursor;
        try {
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            if (cursor == null) {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                return null;
            }
            try {
                int columnIndexOrThrow = cursor.getColumnIndexOrThrow("_data");
                cursor.moveToFirst();
                String string = cursor.getString(columnIndexOrThrow);
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                return string;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                return null;
            }
        } catch (Exception e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            context = 0;
            if (context != 0 && !context.isClosed()) {
                context.close();
            }
            throw th;
        }
    }

    public static void delDir(File file) {
        if (file != null) {
            try {
                delAllFiles(file);
                file.delete();
            } catch (Exception unused) {
            }
        }
    }

    public static void delDir(String str) {
        delDir(new File(str));
    }

    public static void delAllFiles(File file) {
        File file2;
        if (file.exists() && file.isDirectory()) {
            String absolutePath = file.getAbsolutePath();
            String[] list = file.list();
            for (int i = 0; i < list.length; i++) {
                if (absolutePath.endsWith(File.separator)) {
                    file2 = new File(absolutePath + list[i]);
                } else {
                    file2 = new File(absolutePath + File.separator + list[i]);
                }
                if (file2.isFile()) {
                    file2.delete();
                }
                if (file2.isDirectory()) {
                    delAllFiles(absolutePath + "/" + list[i]);
                    delDir(absolutePath + "/" + list[i]);
                }
            }
        }
    }

    public static void delAllFiles(String str) {
        delAllFiles(new File(str));
    }

    public static void showInputMethodWindow(Activity activity, View view) {
        try {
            ((InputMethodManager) activity.getSystemService("input_method")).showSoftInput(view, 1);
        } catch (Throwable unused) {
        }
    }

    public static void closeInputMethodWindow(Activity activity) {
        try {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 2);
        } catch (Throwable unused) {
        }
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static boolean hasExternalXDir() {
        return Build.VERSION.SDK_INT >= 8;
    }

    public static boolean isExternalStorageAvailable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static byte[] readInputStream(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (true) {
            try {
                int read = inputStream.read(bArr, 0, 8192);
                if (read <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } catch (Exception unused) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception unused2) {
                    }
                }
                return null;
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception unused3) {
                    }
                }
                throw th;
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception unused4) {
            }
        }
        return byteArray;
    }

    public static int dp2px(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static String formatScore(double d) {
        boolean z;
        if (d < 0.0d) {
            d = -d;
            z = true;
        } else {
            z = false;
        }
        String str = r5 + "";
        if (r5 == 0) {
            return "0.0";
        }
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        }
        if (str.length() == 1) {
            sb.append("0.");
            sb.append(str);
        } else {
            sb.append(str.substring(0, str.length() - 1));
            sb.append(".");
            sb.append(str.charAt(str.length() - 1));
        }
        return sb.toString();
    }

    public static <T> T[] concatArray(T[] tArr, T[] tArr2) {
        if (tArr == null || tArr.length == 0) {
            return tArr2;
        }
        if (tArr2 == null || tArr2.length == 0) {
            return tArr;
        }
        T[] tArr3 = (T[]) Arrays.copyOf(tArr, tArr.length + tArr2.length);
        System.arraycopy(tArr2, 0, tArr3, tArr.length, tArr2.length);
        return tArr3;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String md5sum(String str) {
        try {
            return md5sum(new FileInputStream(str));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String md5sum(InputStream inputStream) {
        byte[] bArr = new byte[1024];
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            while (true) {
                int read = inputStream.read(bArr, 0, 1024);
                if (read <= 0) {
                    return toHexString(instance.digest());
                }
                instance.update(bArr, 0, read);
            }
        } catch (Exception unused) {
            return "";
        } finally {
            closeSafely(inputStream);
        }
    }

    public static String getMD5(String str) {
        return str == null ? "" : getMD5(str.getBytes());
    }

    public static String getMD5(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            instance.update(bArr);
            return toHexString(instance.digest());
        } catch (Exception unused) {
            return "";
        }
    }

    public static String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            while (hexString.length() < 2) {
                hexString = "0" + hexString;
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static long getSDAvailaleSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = statFs.getBlockSize();
        StringBuilder sb = new StringBuilder();
        sb.append("getSDAvailaleSize ");
        long availableBlocks = statFs.getAvailableBlocks() * blockSize;
        sb.append(availableBlocks);
        Log.d("test", sb.toString());
        return availableBlocks;
    }

    public static long getSDAllSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = statFs.getBlockSize();
        StringBuilder sb = new StringBuilder();
        sb.append("getSDAllSize ");
        long blockCount = statFs.getBlockCount() * blockSize;
        sb.append(blockCount);
        Log.d("test", sb.toString());
        return blockCount;
    }

    public static String convertToFormateSize(long j) {
        String str;
        String str2;
        String str3;
        if (j > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            long j2 = j / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            if (j2 > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                long j3 = j2 / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                if (j3 > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                    long j4 = j3 / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                    long j5 = (long) ((j3 % PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / 102.4d);
                    StringBuilder sb = new StringBuilder();
                    sb.append(j4);
                    if (j5 == 0) {
                        str3 = "";
                    } else {
                        str3 = "." + j5;
                    }
                    sb.append(str3);
                    sb.append("GB");
                    return sb.toString();
                }
                long j6 = (long) ((j2 % PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / 102.4d);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(j3);
                if (j6 == 0) {
                    str2 = "";
                } else {
                    str2 = "." + j6;
                }
                sb2.append(str2);
                sb2.append("MB");
                return sb2.toString();
            }
            long j7 = (long) ((j % PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / 102.4d);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(j2);
            if (j7 == 0) {
                str = "";
            } else {
                str = "." + j7;
            }
            sb3.append(str);
            sb3.append("KB");
            return sb3.toString();
        }
        return j + "B";
    }

    public static <T> T[] list2Array(List<T> list, Class<T> cls) {
        if (list == null) {
            return null;
        }
        T[] tArr = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, list.size()));
        for (int i = 0; i < list.size(); i++) {
            tArr[i] = list.get(i);
        }
        return tArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T dynamicCast(Object obj, Class<T> cls) {
        if (obj == 0 || cls == null || !obj.getClass().equals(cls)) {
            return null;
        }
        return obj;
    }

    public static void closeSafely(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnectSafely(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPriceStr(float f) {
        return BigDecimal.valueOf(f).divide(new BigDecimal(100)).toString();
    }

    public static Observable<Boolean> asyncDeleteFile(final File file) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.common.utils.-$$Lambda$Util$tQ4687lnEgYKZ4ycwIrGvegbfKc
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                Util.a(file, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(File file, ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(Boolean.valueOf((file == null || !file.exists()) ? false : file.delete()));
        observableEmitter.onComplete();
    }
}
