package com.xiaomi.miplay.mylibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class BitMapUtils {
    private static final String a = "BitMapUtils";

    public static String bitmapToBase64(Bitmap bitmap) {
        Throwable th;
        IOException e;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        String str = null;
        byteArrayOutputStream = null;
        try {
            if (bitmap != null) {
                try {
                    try {
                        byteArrayOutputStream = new ByteArrayOutputStream();
                    } catch (IOException e2) {
                        e = e2;
                        str = null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
                try {
                    bitmap.compress(Bitmap.CompressFormat.WEBP, 75, byteArrayOutputStream);
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    Logger.i(a, "base64 encode before:" + byteArray.length, new Object[0]);
                    str = Base64.encodeToString(byteArray, 0);
                    Logger.i(a, "base64 encode after:" + str.getBytes().length, new Object[0]);
                    str = str;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                } catch (IOException e3) {
                    e = e3;
                    byteArrayOutputStream = byteArrayOutputStream;
                    e.printStackTrace();
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.flush();
                        byteArrayOutputStream.close();
                    }
                    return str;
                } catch (Throwable th3) {
                    th = th3;
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.flush();
                            byteArrayOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } else {
                str = null;
            }
            if (byteArrayOutputStream2 != null) {
                byteArrayOutputStream2.flush();
                byteArrayOutputStream2.close();
            }
        } catch (IOException e5) {
            e5.printStackTrace();
        }
        return str;
    }

    public static Bitmap base64ToBitmap(String str) {
        String str2 = a;
        Logger.i(str2, "base64 decode before:" + str.getBytes().length, new Object[0]);
        byte[] decode = Base64.decode(str, 0);
        String str3 = a;
        Logger.i(str3, "base64 decode after:" + decode.length, new Object[0]);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    public static void saveBitmap(String str, Bitmap bitmap, Context context) {
        Logger.i("Save Bitmap", "Ready to save picture", new Object[0]);
        String str2 = context.getFilesDir() + "/images/";
        Logger.i("Save Bitmap", "Save Path=" + str2, new Object[0]);
        if (!a(str2)) {
            Logger.i("Save Bitmap", "TargetPath isn't exist", new Object[0]);
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str2, str));
            bitmap.compress(Bitmap.CompressFormat.WEBP, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Logger.i("Save Bitmap", "The picture is save to your phone!", new Object[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSDPath() {
        return (Environment.getExternalStorageState().equals("mounted") ? Environment.getExternalStorageDirectory() : null).toString();
    }

    static boolean a(String str) {
        File file = new File(str);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    public static Bitmap resizeBitmapByScale(Bitmap bitmap, float f, float f2, boolean z) {
        Logger.i(a, "resizeBitmapByScale.", new Object[0]);
        int round = Math.round(bitmap.getWidth() * f);
        int round2 = Math.round(bitmap.getHeight() * f2);
        if (round == bitmap.getWidth() && round2 == bitmap.getHeight()) {
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(round, round2, a(bitmap));
        Canvas canvas = new Canvas(createBitmap);
        canvas.scale(f, f2);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint(6));
        if (z) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    public static Bitmap roundCornerImage(Bitmap bitmap, int i) {
        Logger.i(a, "roundCornerImage.", new Object[0]);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        float f = i;
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, (Rect) null, rect, paint);
        bitmap.recycle();
        return createBitmap;
    }

    private static Bitmap.Config a(Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig();
        return config == null ? Bitmap.Config.ARGB_8888 : config;
    }

    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
