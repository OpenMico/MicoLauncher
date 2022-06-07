package com.blankj.utilcode.util;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.FileProvider;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class UriUtils {
    private UriUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Uri res2Uri(String str) {
        return Uri.parse("android.resource://" + Utils.getApp().getPackageName() + "/" + str);
    }

    public static Uri file2Uri(File file) {
        if (!b.b(file)) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 24) {
            return Uri.fromFile(file);
        }
        return FileProvider.getUriForFile(Utils.getApp(), Utils.getApp().getPackageName() + ".utilcode.fileprovider", file);
    }

    public static File uri2File(Uri uri) {
        if (uri == null) {
            return null;
        }
        File a = a(uri);
        return a != null ? a : b(uri);
    }

    private static File a(Uri uri) {
        Uri uri2;
        File a;
        boolean z;
        String str;
        File file;
        Log.d("UriUtils", uri.toString());
        String authority = uri.getAuthority();
        String scheme = uri.getScheme();
        String path = uri.getPath();
        if (Build.VERSION.SDK_INT >= 24 && path != null) {
            String[] strArr = {"/external/", "/external_path/"};
            for (String str2 : strArr) {
                if (path.startsWith(str2)) {
                    File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + path.replace(str2, "/"));
                    if (file2.exists()) {
                        Log.d("UriUtils", uri.toString() + " -> " + str2);
                        return file2;
                    }
                }
            }
            if (path.startsWith("/files_path/")) {
                file = new File(Utils.getApp().getFilesDir().getAbsolutePath() + path.replace("/files_path/", "/"));
            } else if (path.startsWith("/cache_path/")) {
                file = new File(Utils.getApp().getCacheDir().getAbsolutePath() + path.replace("/cache_path/", "/"));
            } else if (path.startsWith("/external_files_path/")) {
                file = new File(Utils.getApp().getExternalFilesDir(null).getAbsolutePath() + path.replace("/external_files_path/", "/"));
            } else if (path.startsWith("/external_cache_path/")) {
                file = new File(Utils.getApp().getExternalCacheDir().getAbsolutePath() + path.replace("/external_cache_path/", "/"));
            } else {
                file = null;
            }
            if (file != null && file.exists()) {
                Log.d("UriUtils", uri.toString() + " -> " + path);
                return file;
            }
        }
        if ("file".equals(scheme)) {
            if (path != null) {
                return new File(path);
            }
            Log.d("UriUtils", uri.toString() + " parse failed. -> 0");
            return null;
        } else if (Build.VERSION.SDK_INT < 19 || !DocumentsContract.isDocumentUri(Utils.getApp(), uri)) {
            if ("content".equals(scheme)) {
                return a(uri, "2");
            }
            Log.d("UriUtils", uri.toString() + " parse failed. -> 3");
            return null;
        } else if ("com.android.externalstorage.documents".equals(authority)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(Constants.COLON_SEPARATOR);
            String str3 = split[0];
            if ("primary".equalsIgnoreCase(str3)) {
                return new File(Environment.getExternalStorageDirectory() + "/" + split[1]);
            }
            StorageManager storageManager = (StorageManager) Utils.getApp().getSystemService("storage");
            try {
                Class<?> cls = Class.forName("android.os.storage.StorageVolume");
                Method method = storageManager.getClass().getMethod("getVolumeList", new Class[0]);
                Method method2 = cls.getMethod("getUuid", new Class[0]);
                Method method3 = cls.getMethod("getState", new Class[0]);
                Method method4 = cls.getMethod("getPath", new Class[0]);
                Method method5 = cls.getMethod("isPrimary", new Class[0]);
                Method method6 = cls.getMethod("isEmulated", new Class[0]);
                Object invoke = method.invoke(storageManager, new Object[0]);
                int length = Array.getLength(invoke);
                for (int i = 0; i < length; i++) {
                    Object obj = Array.get(invoke, i);
                    if (!"mounted".equals(method3.invoke(obj, new Object[0])) && !"mounted_ro".equals(method3.invoke(obj, new Object[0]))) {
                        z = false;
                        if (z && ((!((Boolean) method5.invoke(obj, new Object[0])).booleanValue() || !((Boolean) method6.invoke(obj, new Object[0])).booleanValue()) && (str = (String) method2.invoke(obj, new Object[0])) != null && str.equals(str3))) {
                            return new File(method4.invoke(obj, new Object[0]) + "/" + split[1]);
                        }
                    }
                    z = true;
                    if (z) {
                        return new File(method4.invoke(obj, new Object[0]) + "/" + split[1]);
                    }
                }
            } catch (Exception e) {
                Log.d("UriUtils", uri.toString() + " parse failed. " + e.toString() + " -> 1_0");
            }
            Log.d("UriUtils", uri.toString() + " parse failed. -> 1_0");
            return null;
        } else if ("com.android.providers.downloads.documents".equals(authority)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if (TextUtils.isEmpty(documentId)) {
                Log.d("UriUtils", uri.toString() + " parse failed(id is null). -> 1_1");
                return null;
            } else if (documentId.startsWith("raw:")) {
                return new File(documentId.substring(4));
            } else {
                if (documentId.startsWith("msf:")) {
                    documentId = documentId.split(Constants.COLON_SEPARATOR)[1];
                }
                try {
                    long parseLong = Long.parseLong(documentId);
                    for (String str4 : new String[]{"content://downloads/public_downloads", "content://downloads/all_downloads", "content://downloads/my_downloads"}) {
                        try {
                            a = a(ContentUris.withAppendedId(Uri.parse(str4), parseLong), "1_1");
                        } catch (Exception unused) {
                        }
                        if (a != null) {
                            return a;
                        }
                    }
                    Log.d("UriUtils", uri.toString() + " parse failed. -> 1_1");
                    return null;
                } catch (Exception unused2) {
                    return null;
                }
            }
        } else if ("com.android.providers.media.documents".equals(authority)) {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(Constants.COLON_SEPARATOR);
            String str5 = split2[0];
            if (MimeTypes.BASE_TYPE_IMAGE.equals(str5)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(str5)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(str5)) {
                uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            } else {
                Log.d("UriUtils", uri.toString() + " parse failed. -> 1_2");
                return null;
            }
            return a(uri2, "_id=?", new String[]{split2[1]}, "1_2");
        } else if ("content".equals(scheme)) {
            return a(uri, "1_3");
        } else {
            Log.d("UriUtils", uri.toString() + " parse failed. -> 1_4");
            return null;
        }
    }

    private static File a(Uri uri, String str) {
        return a(uri, null, null, str);
    }

    private static File a(Uri uri, String str, String[] strArr, String str2) {
        if ("com.google.android.apps.photos.content".equals(uri.getAuthority())) {
            if (!TextUtils.isEmpty(uri.getLastPathSegment())) {
                return new File(uri.getLastPathSegment());
            }
        } else if ("com.tencent.mtt.fileprovider".equals(uri.getAuthority())) {
            String path = uri.getPath();
            if (!TextUtils.isEmpty(path)) {
                return new File(Environment.getExternalStorageDirectory(), path.substring(10, path.length()));
            }
        } else if ("com.huawei.hidisk.fileprovider".equals(uri.getAuthority())) {
            String path2 = uri.getPath();
            if (!TextUtils.isEmpty(path2)) {
                return new File(path2.replace("/root", ""));
            }
        }
        Cursor query = Utils.getApp().getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
        try {
            if (query == null) {
                Log.d("UriUtils", uri.toString() + " parse failed(cursor is null). -> " + str2);
                return null;
            } else if (query.moveToFirst()) {
                int columnIndex = query.getColumnIndex("_data");
                if (columnIndex > -1) {
                    return new File(query.getString(columnIndex));
                }
                Log.d("UriUtils", uri.toString() + " parse failed(columnIndex: " + columnIndex + " is wrong). -> " + str2);
                return null;
            } else {
                Log.d("UriUtils", uri.toString() + " parse failed(moveToFirst return false). -> " + str2);
                return null;
            }
        } catch (Exception unused) {
            Log.d("UriUtils", uri.toString() + " parse failed. -> " + str2);
            return null;
        } finally {
            query.close();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0062 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.io.File b(android.net.Uri r7) {
        /*
            java.lang.String r0 = "UriUtils"
            java.lang.String r1 = "copyUri2Cache() called"
            android.util.Log.d(r0, r1)
            r0 = 0
            android.app.Application r1 = com.blankj.utilcode.util.Utils.getApp()     // Catch: FileNotFoundException -> 0x004f, all -> 0x004a
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch: FileNotFoundException -> 0x004f, all -> 0x004a
            java.io.InputStream r7 = r1.openInputStream(r7)     // Catch: FileNotFoundException -> 0x004f, all -> 0x004a
            java.io.File r1 = new java.io.File     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            android.app.Application r2 = com.blankj.utilcode.util.Utils.getApp()     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            java.io.File r2 = r2.getCacheDir()     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            r3.<init>()     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            java.lang.String r4 = ""
            r3.append(r4)     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            long r4 = java.lang.System.currentTimeMillis()     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            r3.append(r4)     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            java.lang.String r3 = r3.toString()     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            r1.<init>(r2, r3)     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            java.lang.String r2 = r1.getAbsolutePath()     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            com.blankj.utilcode.util.b.a(r2, r7)     // Catch: FileNotFoundException -> 0x0048, all -> 0x005f
            if (r7 == 0) goto L_0x0047
            r7.close()     // Catch: IOException -> 0x0043
            goto L_0x0047
        L_0x0043:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0047:
            return r1
        L_0x0048:
            r1 = move-exception
            goto L_0x0051
        L_0x004a:
            r7 = move-exception
            r6 = r0
            r0 = r7
            r7 = r6
            goto L_0x0060
        L_0x004f:
            r1 = move-exception
            r7 = r0
        L_0x0051:
            r1.printStackTrace()     // Catch: all -> 0x005f
            if (r7 == 0) goto L_0x005e
            r7.close()     // Catch: IOException -> 0x005a
            goto L_0x005e
        L_0x005a:
            r7 = move-exception
            r7.printStackTrace()
        L_0x005e:
            return r0
        L_0x005f:
            r0 = move-exception
        L_0x0060:
            if (r7 == 0) goto L_0x006a
            r7.close()     // Catch: IOException -> 0x0066
            goto L_0x006a
        L_0x0066:
            r7 = move-exception
            r7.printStackTrace()
        L_0x006a:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.UriUtils.b(android.net.Uri):java.io.File");
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0040 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] uri2Bytes(android.net.Uri r4) {
        /*
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            android.app.Application r1 = com.blankj.utilcode.util.Utils.getApp()     // Catch: FileNotFoundException -> 0x0026, all -> 0x0021
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch: FileNotFoundException -> 0x0026, all -> 0x0021
            java.io.InputStream r4 = r1.openInputStream(r4)     // Catch: FileNotFoundException -> 0x0026, all -> 0x0021
            byte[] r0 = com.blankj.utilcode.util.b.a(r4)     // Catch: FileNotFoundException -> 0x001f, all -> 0x003d
            if (r4 == 0) goto L_0x001e
            r4.close()     // Catch: IOException -> 0x001a
            goto L_0x001e
        L_0x001a:
            r4 = move-exception
            r4.printStackTrace()
        L_0x001e:
            return r0
        L_0x001f:
            r1 = move-exception
            goto L_0x0028
        L_0x0021:
            r4 = move-exception
            r3 = r0
            r0 = r4
            r4 = r3
            goto L_0x003e
        L_0x0026:
            r1 = move-exception
            r4 = r0
        L_0x0028:
            r1.printStackTrace()     // Catch: all -> 0x003d
            java.lang.String r1 = "UriUtils"
            java.lang.String r2 = "uri to bytes failed."
            android.util.Log.d(r1, r2)     // Catch: all -> 0x003d
            if (r4 == 0) goto L_0x003c
            r4.close()     // Catch: IOException -> 0x0038
            goto L_0x003c
        L_0x0038:
            r4 = move-exception
            r4.printStackTrace()
        L_0x003c:
            return r0
        L_0x003d:
            r0 = move-exception
        L_0x003e:
            if (r4 == 0) goto L_0x0048
            r4.close()     // Catch: IOException -> 0x0044
            goto L_0x0048
        L_0x0044:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0048:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.UriUtils.uri2Bytes(android.net.Uri):byte[]");
    }
}
