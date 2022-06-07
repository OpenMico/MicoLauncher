package com.xiaomi.smarthome.library.common.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class BitmapUtils {
    public static final int UNCONSTRAINED = -1;

    private BitmapUtils() {
    }

    public static int nextPowerOf2(int i) {
        if (i <= 0 || i > 1073741824) {
            throw new IllegalArgumentException();
        }
        int i2 = i - 1;
        int i3 = i2 | (i2 >> 16);
        int i4 = i3 | (i3 >> 8);
        int i5 = i4 | (i4 >> 4);
        int i6 = i5 | (i5 >> 2);
        return (i6 | (i6 >> 1)) + 1;
    }

    public static int prevPowerOf2(int i) {
        if (i > 0) {
            return Integer.highestOneBit(i);
        }
        throw new IllegalArgumentException();
    }

    public static int computeSampleSize(int i, int i2, int i3, int i4) {
        int a = a(i, i2, i3, i4);
        return a <= 8 ? nextPowerOf2(a) : ((a + 7) / 8) * 8;
    }

    private static int a(int i, int i2, int i3, int i4) {
        int i5 = 1;
        if (i4 == -1 && i3 == -1) {
            return 1;
        }
        if (i4 != -1) {
            i5 = (int) Math.ceil(Math.sqrt((i * i2) / i4));
        }
        return i3 == -1 ? i5 : Math.max(Math.min(i / i3, i2 / i3), i5);
    }

    public static int computeSampleSizeLarger(int i, int i2, int i3) {
        int max = Math.max(i / i3, i2 / i3);
        if (max <= 1) {
            return 1;
        }
        return max <= 8 ? prevPowerOf2(max) : (max / 8) * 8;
    }

    public static int computeSampleSizeLarger(float f) {
        int floor = (int) Math.floor(1.0f / f);
        if (floor <= 1) {
            return 1;
        }
        return floor <= 8 ? prevPowerOf2(floor) : (floor / 8) * 8;
    }

    public static int computeSampleSize(float f) {
        int max = Math.max(1, (int) Math.ceil(1.0f / f));
        return max <= 8 ? nextPowerOf2(max) : ((max + 7) / 8) * 8;
    }

    public static Bitmap resizeBitmapByScale(Bitmap bitmap, float f, boolean z) {
        int round = Math.round(bitmap.getWidth() * f);
        int round2 = Math.round(bitmap.getHeight() * f);
        if (round == bitmap.getWidth() && round2 == bitmap.getHeight()) {
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(round, round2, a(bitmap));
        Canvas canvas = new Canvas(createBitmap);
        canvas.scale(f, f);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint(6));
        if (z) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    private static Bitmap.Config a(Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig();
        return config == null ? Bitmap.Config.ARGB_8888 : config;
    }

    public static Bitmap resizeDownBySideLength(Bitmap bitmap, int i, boolean z) {
        float f = i;
        float min = Math.min(f / bitmap.getWidth(), f / bitmap.getHeight());
        return min >= 1.0f ? bitmap : resizeBitmapByScale(bitmap, min, z);
    }

    public static Bitmap resizeAndCropCenter(Bitmap bitmap, int i, boolean z) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == i && height == i) {
            return bitmap;
        }
        float min = i / Math.min(width, height);
        Bitmap createBitmap = Bitmap.createBitmap(i, i, a(bitmap));
        int round = Math.round(bitmap.getWidth() * min);
        int round2 = Math.round(bitmap.getHeight() * min);
        Canvas canvas = new Canvas(createBitmap);
        canvas.translate((i - round) / 2.0f, (i - round2) / 2.0f);
        canvas.scale(min, min);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint(6));
        if (z) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    public static Bitmap loadBitmap(Resources resources, int i, int i2, int i3) {
        return loadBitmap(resources, i, i2, i3, 1);
    }

    public static Bitmap loadBitmap(Resources resources, int i, int i2, int i3, int i4) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i, options);
        options.inJustDecodeBounds = false;
        if (i4 > 0) {
            options.inSampleSize = computeSampleSize(options, -1, (i2 > 0 ? (int) (i2 / i4) : options.outWidth) * (i3 > 0 ? (int) (i3 / i4) : options.outHeight));
        } else {
            options.inSampleSize = 2;
        }
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeStream(resources.openRawResource(i), null, options);
    }

    public static int computeSampleSize(BitmapFactory.Options options, int i, int i2) {
        int computeInitialSampleSize = computeInitialSampleSize(options, i, i2);
        if (computeInitialSampleSize > 8) {
            return 8 * ((computeInitialSampleSize + 7) / 8);
        }
        int i3 = 1;
        while (i3 < computeInitialSampleSize) {
            i3 <<= 1;
        }
        return i3;
    }

    public static int computeInitialSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3;
        double d = options.outWidth;
        double d2 = options.outHeight;
        int ceil = i2 == -1 ? 1 : (int) Math.ceil(Math.sqrt((d * d2) / i2));
        if (i == -1) {
            i3 = 128;
        } else {
            double d3 = i;
            i3 = (int) Math.min(Math.floor(d / d3), Math.floor(d2 / d3));
        }
        if (i3 < ceil) {
            return ceil;
        }
        if (i2 == -1 && i == -1) {
            return 1;
        }
        return i == -1 ? ceil : i3;
    }

    public static void recycleSilently(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                bitmap.recycle();
            } catch (Throwable th) {
                Log.w("BitmapUtils", "unable recycle bitmap", th);
            }
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i, boolean z) {
        if (i == 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(i);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        if (z) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    public static Bitmap createVideoThumbnail(String str) {
        Throwable th;
        Object obj;
        Class cls;
        Object obj2;
        Method method;
        InstantiationException e;
        InvocationTargetException e2;
        ClassNotFoundException e3;
        NoSuchMethodException e4;
        IllegalAccessException e5;
        Bitmap decodeByteArray;
        try {
            try {
                try {
                    cls = Class.forName("android.media.MediaMetadataRetriever");
                } catch (ClassNotFoundException e6) {
                    e3 = e6;
                    cls = null;
                    obj2 = null;
                } catch (IllegalAccessException e7) {
                    e5 = e7;
                    cls = null;
                    obj2 = null;
                } catch (IllegalArgumentException unused) {
                    cls = null;
                    obj2 = null;
                } catch (InstantiationException e8) {
                    e = e8;
                    cls = null;
                    obj2 = null;
                } catch (NoSuchMethodException e9) {
                    e4 = e9;
                    cls = null;
                    obj2 = null;
                } catch (RuntimeException unused2) {
                    cls = null;
                    obj2 = null;
                } catch (InvocationTargetException e10) {
                    e2 = e10;
                    cls = null;
                    obj2 = null;
                } catch (Throwable th2) {
                    th = th2;
                    cls = null;
                    obj = null;
                }
            } catch (Throwable th3) {
                th = th3;
            }
            try {
                obj2 = cls.newInstance();
                try {
                    cls.getMethod("setDataSource", String.class).invoke(obj2, str);
                    if (Build.VERSION.SDK_INT <= 9) {
                        Bitmap bitmap = (Bitmap) cls.getMethod("captureFrame", new Class[0]).invoke(obj2, new Object[0]);
                        if (obj2 != null) {
                            try {
                                cls.getMethod("release", new Class[0]).invoke(obj2, new Object[0]);
                            } catch (Exception unused3) {
                            }
                        }
                        return bitmap;
                    }
                    byte[] bArr = (byte[]) cls.getMethod("getEmbeddedPicture", new Class[0]).invoke(obj2, new Object[0]);
                    if (bArr == null || (decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length)) == null) {
                        Bitmap bitmap2 = (Bitmap) cls.getMethod("getFrameAtTime", new Class[0]).invoke(obj2, new Object[0]);
                        if (obj2 != null) {
                            try {
                                cls.getMethod("release", new Class[0]).invoke(obj2, new Object[0]);
                            } catch (Exception unused4) {
                            }
                        }
                        return bitmap2;
                    }
                    if (obj2 != null) {
                        try {
                            cls.getMethod("release", new Class[0]).invoke(obj2, new Object[0]);
                        } catch (Exception unused5) {
                        }
                    }
                    return decodeByteArray;
                } catch (ClassNotFoundException e11) {
                    e3 = e11;
                    Log.e("BitmapUtils", "createVideoThumbnail", e3);
                    if (obj2 != null) {
                        method = cls.getMethod("release", new Class[0]);
                        method.invoke(obj2, new Object[0]);
                    }
                    return null;
                } catch (IllegalAccessException e12) {
                    e5 = e12;
                    Log.e("BitmapUtils", "createVideoThumbnail", e5);
                    if (obj2 != null) {
                        method = cls.getMethod("release", new Class[0]);
                        method.invoke(obj2, new Object[0]);
                    }
                    return null;
                } catch (IllegalArgumentException unused6) {
                    if (obj2 != null) {
                        method = cls.getMethod("release", new Class[0]);
                        method.invoke(obj2, new Object[0]);
                    }
                    return null;
                } catch (InstantiationException e13) {
                    e = e13;
                    Log.e("BitmapUtils", "createVideoThumbnail", e);
                    if (obj2 != null) {
                        method = cls.getMethod("release", new Class[0]);
                        method.invoke(obj2, new Object[0]);
                    }
                    return null;
                } catch (NoSuchMethodException e14) {
                    e4 = e14;
                    Log.e("BitmapUtils", "createVideoThumbnail", e4);
                    if (obj2 != null) {
                        method = cls.getMethod("release", new Class[0]);
                        method.invoke(obj2, new Object[0]);
                    }
                    return null;
                } catch (RuntimeException unused7) {
                    if (obj2 != null) {
                        method = cls.getMethod("release", new Class[0]);
                        method.invoke(obj2, new Object[0]);
                    }
                    return null;
                } catch (InvocationTargetException e15) {
                    e2 = e15;
                    Log.e("BitmapUtils", "createVideoThumbnail", e2);
                    if (obj2 != null) {
                        method = cls.getMethod("release", new Class[0]);
                        method.invoke(obj2, new Object[0]);
                    }
                    return null;
                }
            } catch (ClassNotFoundException e16) {
                e3 = e16;
                obj2 = null;
            } catch (IllegalAccessException e17) {
                e5 = e17;
                obj2 = null;
            } catch (IllegalArgumentException unused8) {
                obj2 = null;
            } catch (InstantiationException e18) {
                e = e18;
                obj2 = null;
            } catch (NoSuchMethodException e19) {
                e4 = e19;
                obj2 = null;
            } catch (RuntimeException unused9) {
                obj2 = null;
            } catch (InvocationTargetException e20) {
                e2 = e20;
                obj2 = null;
            } catch (Throwable th4) {
                th = th4;
                obj = null;
                if (obj != null) {
                    try {
                        cls.getMethod("release", new Class[0]).invoke(obj, new Object[0]);
                    } catch (Exception unused10) {
                    }
                }
                throw th;
            }
        } catch (Exception unused11) {
        }
    }

    public static byte[] compressToBytes(Bitmap bitmap) {
        return compressToBytes(bitmap, 90);
    }

    public static byte[] compressToBytes(Bitmap bitmap, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(65536);
        bitmap.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static boolean isSupportedByRegionDecoder(String str) {
        if (str == null) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        return lowerCase.startsWith("image/") && !lowerCase.equals("image/gif") && !lowerCase.endsWith("bmp");
    }

    public static boolean isRotationSupported(String str) {
        if (str == null) {
            return false;
        }
        return str.toLowerCase().equals("image/jpeg");
    }
}
