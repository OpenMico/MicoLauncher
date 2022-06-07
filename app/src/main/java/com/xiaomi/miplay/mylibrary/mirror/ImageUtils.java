package com.xiaomi.miplay.mylibrary.mirror;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.alibaba.fastjson.asm.Opcodes;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.fourthline.cling.support.model.dlna.DLNAProfiles;

/* loaded from: classes4.dex */
public class ImageUtils {
    private static String a = "MiPlayQuickImageUtils";

    public int getImageInfo(Context context, String str, ImageInfo imageInfo) {
        Throwable th;
        SecurityException e;
        AssetFileDescriptor openAssetFileDescriptor;
        imageInfo.setPhotoUri(str);
        String str2 = a;
        Log.i(str2, "getImageInfo uri:" + str);
        AssetFileDescriptor assetFileDescriptor = null;
        if (imageInfo.getData() != null) {
            imageInfo.setData(null);
        }
        if (!str.startsWith("content://")) {
            String[] split = str.split("\\.");
            if (a(split[split.length - 1])) {
                return a(str, imageInfo);
            }
            return a(str, (AssetFileDescriptor) null, imageInfo);
        }
        Uri parse = Uri.parse(str);
        if ("content".equals(parse.getScheme()) && "settings".equals(parse.getAuthority()) && (parse = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.getDefaultType(parse))) == null) {
            try {
                throw new FileNotFoundException("Failed to resolve default ringtone");
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        try {
            try {
                try {
                    openAssetFileDescriptor = context.getContentResolver().openAssetFileDescriptor(parse, "r");
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e3) {
                e3.printStackTrace();
                return 0;
            }
        } catch (IOException unused) {
        } catch (SecurityException e4) {
            e = e4;
        }
        try {
        } catch (IOException unused2) {
            assetFileDescriptor = openAssetFileDescriptor;
            String str3 = a;
            Log.i(str3, "IOException :" + parse.getScheme());
            if (assetFileDescriptor == null) {
                return 0;
            }
            assetFileDescriptor.close();
            return 0;
        } catch (SecurityException e5) {
            e = e5;
            assetFileDescriptor = openAssetFileDescriptor;
            String str4 = a;
            Log.i(str4, "SecurityException, continue:" + e);
            if (assetFileDescriptor == null) {
                return 0;
            }
            assetFileDescriptor.close();
            return 0;
        } catch (Throwable th3) {
            th = th3;
            assetFileDescriptor = openAssetFileDescriptor;
            if (assetFileDescriptor != null) {
                try {
                    assetFileDescriptor.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
        if (openAssetFileDescriptor == null) {
            String str5 = a;
            Log.e(str5, "open " + parse.getScheme() + " failed");
            if (openAssetFileDescriptor != null) {
                try {
                    openAssetFileDescriptor.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            return -1;
        }
        if (openAssetFileDescriptor.getDeclaredLength() < 0) {
            a((String) null, openAssetFileDescriptor, imageInfo);
        } else {
            Log.d(a, "[TODO] FileDescriptor + offset");
        }
        if (openAssetFileDescriptor == null) {
            return 0;
        }
        openAssetFileDescriptor.close();
        return 0;
    }

    private boolean a(String str) {
        return str.equalsIgnoreCase("3g2") || str.equalsIgnoreCase("3gp") || str.equalsIgnoreCase("avi") || str.equalsIgnoreCase("asf") || str.equalsIgnoreCase("f4v") || str.equalsIgnoreCase("flv") || str.equalsIgnoreCase("mp4") || str.equalsIgnoreCase("mkv") || str.equalsIgnoreCase("m4v") || str.equalsIgnoreCase("mov") || str.equalsIgnoreCase("mpg") || str.equalsIgnoreCase("wmv") || str.equalsIgnoreCase("rmvb") || str.equalsIgnoreCase("ts") || str.equalsIgnoreCase("webm");
    }

    private int a(String str, ImageInfo imageInfo) {
        Throwable th;
        long currentTimeMillis = System.currentTimeMillis();
        Bitmap b = b(str, imageInfo);
        if (b == null) {
            imageInfo.setImageType(12);
            imageInfo.setData("UNSUPPORT_VIDEO".getBytes());
            imageInfo.setSize("UNSUPPORT_VIDEO".getBytes().length);
            imageInfo.setWidth(0);
            imageInfo.setHeight(0);
            imageInfo.setCompressed(false);
        } else {
            ByteArrayOutputStream byteArrayOutputStream = null;
            try {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                try {
                    b.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
                    try {
                        byteArrayOutputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int length = byteArrayOutputStream2.toByteArray().length;
                    int width = b.getWidth();
                    int height = b.getHeight();
                    imageInfo.setImageType(10);
                    imageInfo.setData(byteArrayOutputStream2.toByteArray());
                    imageInfo.setSize(length);
                    imageInfo.setWidth(width);
                    imageInfo.setHeight(height);
                    imageInfo.setCompressed(false);
                } catch (Throwable th2) {
                    th = th2;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        String str2 = a;
        Log.d(str2, "getVideoFrame cost: " + (currentTimeMillis2 - currentTimeMillis) + "ms");
        return 0;
    }

    private Bitmap b(String str, ImageInfo imageInfo) {
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
                    try {
                        obj2 = cls.newInstance();
                        try {
                            cls.getMethod("setDataSource", String.class).invoke(obj2, str);
                            if (Build.VERSION.SDK_INT <= 9) {
                                Bitmap bitmap = (Bitmap) cls.getMethod("captureFrame", new Class[0]).invoke(obj2, new Object[0]);
                                if (obj2 != null) {
                                    try {
                                        cls.getMethod("release", new Class[0]).invoke(obj2, new Object[0]);
                                    } catch (Exception unused) {
                                    }
                                }
                                return bitmap;
                            } else if (Build.VERSION.SDK_INT >= 28) {
                                int imageBestWidth = imageInfo.getImageBestWidth();
                                int imageBestHeight = imageInfo.getImageBestHeight();
                                int parseInt = Integer.parseInt((String) cls.getMethod("extractMetadata", Integer.TYPE).invoke(obj2, 24));
                                if (parseInt == 90 || parseInt == 270) {
                                    imageBestWidth = imageInfo.getImageBestHeight();
                                    imageBestHeight = imageInfo.getImageBestWidth();
                                }
                                Log.d(a, "createVideoFrame rotation:" + parseInt + " width:" + imageBestWidth + " height:" + imageBestHeight);
                                Bitmap bitmap2 = (Bitmap) cls.getMethod("getScaledFrameAtTime", Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE).invoke(obj2, 0, 0, Integer.valueOf(imageBestWidth), Integer.valueOf(imageBestHeight));
                                if (obj2 != null) {
                                    try {
                                        cls.getMethod("release", new Class[0]).invoke(obj2, new Object[0]);
                                    } catch (Exception unused2) {
                                    }
                                }
                                return bitmap2;
                            } else {
                                byte[] bArr = (byte[]) cls.getMethod("getEmbeddedPicture", new Class[0]).invoke(obj2, new Object[0]);
                                if (bArr == null || (decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length)) == null) {
                                    Bitmap bitmap3 = (Bitmap) cls.getMethod("getFrameAtTime", new Class[0]).invoke(obj2, new Object[0]);
                                    if (obj2 != null) {
                                        try {
                                            cls.getMethod("release", new Class[0]).invoke(obj2, new Object[0]);
                                        } catch (Exception unused3) {
                                        }
                                    }
                                    return bitmap3;
                                }
                                if (obj2 != null) {
                                    try {
                                        cls.getMethod("release", new Class[0]).invoke(obj2, new Object[0]);
                                    } catch (Exception unused4) {
                                    }
                                }
                                return decodeByteArray;
                            }
                        } catch (ClassNotFoundException e6) {
                            e3 = e6;
                            Log.e(a, "createVideoThumbnail", e3);
                            if (obj2 != null) {
                                method = cls.getMethod("release", new Class[0]);
                                method.invoke(obj2, new Object[0]);
                            }
                            return null;
                        } catch (IllegalAccessException e7) {
                            e5 = e7;
                            Log.e(a, "createVideoThumbnail", e5);
                            if (obj2 != null) {
                                method = cls.getMethod("release", new Class[0]);
                                method.invoke(obj2, new Object[0]);
                            }
                            return null;
                        } catch (IllegalArgumentException unused5) {
                            if (obj2 != null) {
                                method = cls.getMethod("release", new Class[0]);
                                method.invoke(obj2, new Object[0]);
                            }
                            return null;
                        } catch (InstantiationException e8) {
                            e = e8;
                            Log.e(a, "createVideoThumbnail", e);
                            if (obj2 != null) {
                                method = cls.getMethod("release", new Class[0]);
                                method.invoke(obj2, new Object[0]);
                            }
                            return null;
                        } catch (NoSuchMethodException e9) {
                            e4 = e9;
                            Log.e(a, "createVideoThumbnail", e4);
                            if (obj2 != null) {
                                method = cls.getMethod("release", new Class[0]);
                                method.invoke(obj2, new Object[0]);
                            }
                            return null;
                        } catch (RuntimeException unused6) {
                            if (obj2 != null) {
                                method = cls.getMethod("release", new Class[0]);
                                method.invoke(obj2, new Object[0]);
                            }
                            return null;
                        } catch (InvocationTargetException e10) {
                            e2 = e10;
                            Log.e(a, "createVideoThumbnail", e2);
                            if (obj2 != null) {
                                method = cls.getMethod("release", new Class[0]);
                                method.invoke(obj2, new Object[0]);
                            }
                            return null;
                        }
                    } catch (ClassNotFoundException e11) {
                        e3 = e11;
                        obj2 = null;
                    } catch (IllegalAccessException e12) {
                        e5 = e12;
                        obj2 = null;
                    } catch (IllegalArgumentException unused7) {
                        obj2 = null;
                    } catch (InstantiationException e13) {
                        e = e13;
                        obj2 = null;
                    } catch (NoSuchMethodException e14) {
                        e4 = e14;
                        obj2 = null;
                    } catch (RuntimeException unused8) {
                        obj2 = null;
                    } catch (InvocationTargetException e15) {
                        e2 = e15;
                        obj2 = null;
                    } catch (Throwable th2) {
                        th = th2;
                        obj = null;
                        if (obj != null) {
                            try {
                                cls.getMethod("release", new Class[0]).invoke(obj, new Object[0]);
                            } catch (Exception unused9) {
                            }
                        }
                        throw th;
                    }
                } catch (ClassNotFoundException e16) {
                    e3 = e16;
                    cls = null;
                    obj2 = null;
                } catch (IllegalAccessException e17) {
                    e5 = e17;
                    cls = null;
                    obj2 = null;
                } catch (IllegalArgumentException unused10) {
                    cls = null;
                    obj2 = null;
                } catch (InstantiationException e18) {
                    e = e18;
                    cls = null;
                    obj2 = null;
                } catch (NoSuchMethodException e19) {
                    e4 = e19;
                    cls = null;
                    obj2 = null;
                } catch (RuntimeException unused11) {
                    cls = null;
                    obj2 = null;
                } catch (InvocationTargetException e20) {
                    e2 = e20;
                    cls = null;
                    obj2 = null;
                } catch (Throwable th3) {
                    th = th3;
                    cls = null;
                    obj = null;
                }
            } catch (Exception unused12) {
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b3  */
    /* JADX WARN: Type inference failed for: r12v4, types: [int, boolean] */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int a(java.lang.String r19, android.content.res.AssetFileDescriptor r20, com.xiaomi.miplay.mylibrary.mirror.ImageInfo r21) {
        /*
            Method dump skipped, instructions count: 480
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.mirror.ImageUtils.a(java.lang.String, android.content.res.AssetFileDescriptor, com.xiaomi.miplay.mylibrary.mirror.ImageInfo):int");
    }

    private int a(BitmapFactory.Options options, int i, int i2) {
        int i3;
        int i4 = options.outHeight;
        int i5 = options.outWidth;
        if (i4 > i2 || i5 > i) {
            int round = Math.round(i4 / i2);
            i3 = Math.round(i5 / i);
            if (round >= i3) {
                i3 = round;
            }
        } else {
            i3 = 1;
        }
        while (i4 / i3 > 4096) {
            i3++;
        }
        int i6 = i5 / i3;
        while (i6 > 4096) {
            i3++;
            i6 = i5 / i3;
        }
        return i3;
    }

    private int b(String str, AssetFileDescriptor assetFileDescriptor) {
        ExifInterface exifInterface;
        try {
            if (str != null) {
                exifInterface = new ExifInterface(str);
            } else if (assetFileDescriptor == null || Build.VERSION.SDK_INT < 24) {
                return 0;
            } else {
                exifInterface = new ExifInterface(assetFileDescriptor.getFileDescriptor());
            }
            int attributeInt = exifInterface.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                return Opcodes.GETFIELD;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    int a(FileInputStream fileInputStream, byte[] bArr) throws IOException {
        byte[] bArr2 = new byte[4];
        if (fileInputStream.read(bArr2, 0, 4) != 4) {
            return 0;
        }
        int i = ((bArr2[0] << 24) & ViewCompat.MEASURED_STATE_MASK) + ((bArr2[1] << 16) & 16711680) + ((bArr2[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + ((bArr2[3] << 0) & 255);
        if (fileInputStream.read(bArr, 0, 4) != 4) {
            return 0;
        }
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0033, code lost:
        r1 = a(r7, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0037, code lost:
        if (r1 >= 8) goto L_0x003a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0039, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0045, code lost:
        if (new java.lang.String(r0).equals("ipco") == false) goto L_0x006d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0047, code lost:
        r1 = a(r7, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004b, code lost:
        if (r1 >= 8) goto L_0x004e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004d, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0059, code lost:
        if (new java.lang.String(r0).equals("irot") == false) goto L_0x0066;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0065, code lost:
        return 360 - ((r7.read() & 3) * 90);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0066, code lost:
        r7.skip(r1 - 8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006d, code lost:
        r7.skip(r1 - 8);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int a(java.io.FileInputStream r7) throws java.io.IOException {
        /*
            r6 = this;
            r0 = 4
            byte[] r0 = new byte[r0]
        L_0x0003:
            int r1 = r6.a(r7, r0)
            r2 = 0
            r3 = 8
            if (r1 >= r3) goto L_0x000d
            return r2
        L_0x000d:
            java.lang.String r4 = new java.lang.String
            r4.<init>(r0)
            java.lang.String r5 = "meta"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x007b
            r4 = 4
            r7.skip(r4)
        L_0x001f:
            int r1 = r6.a(r7, r0)
            if (r1 >= r3) goto L_0x0026
            return r2
        L_0x0026:
            java.lang.String r4 = new java.lang.String
            r4.<init>(r0)
            java.lang.String r5 = "iprp"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0074
        L_0x0033:
            int r1 = r6.a(r7, r0)
            if (r1 >= r3) goto L_0x003a
            return r2
        L_0x003a:
            java.lang.String r4 = new java.lang.String
            r4.<init>(r0)
            java.lang.String r5 = "ipco"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x006d
        L_0x0047:
            int r1 = r6.a(r7, r0)
            if (r1 >= r3) goto L_0x004e
            return r2
        L_0x004e:
            java.lang.String r4 = new java.lang.String
            r4.<init>(r0)
            java.lang.String r5 = "irot"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0066
            int r7 = r7.read()
            r7 = r7 & 3
            int r7 = r7 * 90
            int r7 = 360 - r7
            return r7
        L_0x0066:
            int r1 = r1 + (-8)
            long r4 = (long) r1
            r7.skip(r4)
            goto L_0x0047
        L_0x006d:
            int r1 = r1 + (-8)
            long r4 = (long) r1
            r7.skip(r4)
            goto L_0x0033
        L_0x0074:
            int r1 = r1 + (-8)
            long r4 = (long) r1
            r7.skip(r4)
            goto L_0x001f
        L_0x007b:
            int r1 = r1 + (-8)
            long r1 = (long) r1
            r7.skip(r1)
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.mirror.ImageUtils.a(java.io.FileInputStream):int");
    }

    int a(String str, AssetFileDescriptor assetFileDescriptor) {
        int i = 0;
        try {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (str != null) {
            FileInputStream fileInputStream = new FileInputStream(str);
            i = a(fileInputStream);
            fileInputStream.close();
        } else {
            if (assetFileDescriptor != null) {
                FileInputStream fileInputStream2 = new FileInputStream(assetFileDescriptor.getFileDescriptor());
                i = a(fileInputStream2);
                fileInputStream2.close();
            }
            return i;
        }
        return i;
    }

    private Bitmap a(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(i);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    private int b(String str) {
        if (str == null || str.isEmpty()) {
            return 11;
        }
        if (str.equals("image/jpg")) {
            return 0;
        }
        if (str.equals("image/jpeg")) {
            return 1;
        }
        if (str.equals("image/gif")) {
            return 2;
        }
        if (str.equals(DLNAProfiles.DLNAMimeTypes.MIME_IMAGE_PNG)) {
            return 3;
        }
        if (str.equals("image/x-ms-bmp") || str.equals("image/bmp")) {
            return 4;
        }
        if (str.equals("image/webp")) {
            return 5;
        }
        if (str.equals("image/vnd.wap.wbmp")) {
            return 6;
        }
        if (str.equals("image/x-adobe-dng")) {
            return 7;
        }
        if (str.equals("image/heic")) {
            return 8;
        }
        return str.equals("image/heif") ? 9 : 11;
    }

    private byte[] a(String str, AssetFileDescriptor assetFileDescriptor, BitmapFactory.Options options, int i, int i2, int i3) {
        Bitmap bitmap;
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        options.inSampleSize = a(options, i2, i3);
        options.inJustDecodeBounds = false;
        if (str != null) {
            bitmap = BitmapFactory.decodeFile(str, options);
        } else {
            bitmap = assetFileDescriptor != null ? BitmapFactory.decodeFileDescriptor(assetFileDescriptor.getFileDescriptor(), null, options) : null;
        }
        if (bitmap == null) {
            Log.e(a, str + " decode failed");
            return null;
        }
        Bitmap a2 = a(bitmap, i);
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                a2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                a2.recycle();
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return byteArrayOutputStream.toByteArray();
            } catch (Throwable th2) {
                th = th2;
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
        }
    }

    private byte[] a(File file) {
        int read;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[(int) file.length()];
            int i = 0;
            while (i < bArr.length && (read = fileInputStream.read(bArr, i, bArr.length - i)) >= 0) {
                i += read;
            }
            if (i == bArr.length) {
                fileInputStream.close();
                return bArr;
            }
            throw new IOException("Could not completely read file " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] a(AssetFileDescriptor assetFileDescriptor) {
        int read;
        try {
            FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
            byte[] bArr = new byte[(int) assetFileDescriptor.getLength()];
            int i = 0;
            while (i < bArr.length && (read = fileInputStream.read(bArr, i, bArr.length - i)) >= 0) {
                i += read;
            }
            if (i == bArr.length) {
                fileInputStream.close();
                return bArr;
            }
            throw new IOException("Could not completely read " + assetFileDescriptor);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
