package com.xiaomi.analytics.internal.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class AssetUtils {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.FileOutputStream] */
    public static void extractAssetFile(Context context, String str, String str2) {
        FileInputStream fileInputStream;
        Exception e;
        InputStream inputStream;
        Closeable closeable;
        byte[] inputStream2ByteArray;
        InputStream inputStream2 = 0;
        try {
            inputStream = context.getAssets().open(str);
            try {
                inputStream2ByteArray = IOUtil.inputStream2ByteArray(inputStream);
                File file = new File(str2);
                if (file.exists()) {
                    fileInputStream = new FileInputStream(file);
                    try {
                        String md5 = Utils.getMd5(IOUtil.inputStream2ByteArray(fileInputStream));
                        String md52 = Utils.getMd5(inputStream2ByteArray);
                        if (!TextUtils.isEmpty(md5) && md5.equals(md52)) {
                            IOUtil.closeSafely(inputStream);
                            IOUtil.closeSafely(fileInputStream);
                            IOUtil.closeSafely(null);
                            return;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        inputStream2 = 0;
                        inputStream2 = inputStream;
                        inputStream2 = inputStream2;
                        try {
                            Log.e(ALog.addPrefix("AssetUtils"), "extractAssetFile e", e);
                            IOUtil.closeSafely(inputStream2);
                            closeable = inputStream2;
                            IOUtil.closeSafely(fileInputStream);
                            IOUtil.closeSafely(closeable);
                        } catch (Throwable th) {
                            th = th;
                            inputStream = inputStream2;
                            IOUtil.closeSafely(inputStream);
                            IOUtil.closeSafely(fileInputStream);
                            IOUtil.closeSafely(inputStream2);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        IOUtil.closeSafely(inputStream);
                        IOUtil.closeSafely(fileInputStream);
                        IOUtil.closeSafely(inputStream2);
                        throw th;
                    }
                } else {
                    fileInputStream = null;
                }
                inputStream2 = new FileOutputStream(file);
            } catch (Exception e3) {
                e = e3;
                fileInputStream = null;
                inputStream2 = 0;
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
            }
        } catch (Exception e4) {
            e = e4;
            fileInputStream = null;
            inputStream2 = 0;
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
            fileInputStream = null;
        }
        try {
            inputStream2.write(inputStream2ByteArray);
            inputStream2.flush();
            IOUtil.closeSafely(inputStream);
            closeable = inputStream2;
        } catch (Exception e5) {
            e = e5;
            inputStream2 = inputStream;
            inputStream2 = inputStream2;
            Log.e(ALog.addPrefix("AssetUtils"), "extractAssetFile e", e);
            IOUtil.closeSafely(inputStream2);
            closeable = inputStream2;
            IOUtil.closeSafely(fileInputStream);
            IOUtil.closeSafely(closeable);
        } catch (Throwable th5) {
            th = th5;
            IOUtil.closeSafely(inputStream);
            IOUtil.closeSafely(fileInputStream);
            IOUtil.closeSafely(inputStream2);
            throw th;
        }
        IOUtil.closeSafely(fileInputStream);
        IOUtil.closeSafely(closeable);
    }
}
