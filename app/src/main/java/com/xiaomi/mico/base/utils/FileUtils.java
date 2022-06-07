package com.xiaomi.mico.base.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public class FileUtils {
    public static int getFileCount(String str) {
        File file = new File(str);
        if (!file.exists()) {
            return 0;
        }
        return getFileCount(file);
    }

    public static int getFileCount(File file) {
        File[] listFiles;
        if (file == null || !file.isDirectory() || (listFiles = file.listFiles()) == null) {
            return 0;
        }
        int i = 0;
        for (File file2 : listFiles) {
            if (!file2.isHidden()) {
                i++;
            }
        }
        return i;
    }

    public static boolean isSameFilePath(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return new File(str).getPath().equals(new File(str2).getPath());
    }

    public static String getPathFromFilePath(String str) {
        int lastIndexOf = str.lastIndexOf(47);
        return lastIndexOf != -1 ? str.substring(0, lastIndexOf) : "";
    }

    public static String getNameFromFilePath(String str) {
        int lastIndexOf = str.lastIndexOf(47);
        return lastIndexOf != -1 ? str.substring(lastIndexOf + 1) : str;
    }

    public static String getNameFromFileName(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf != -1 ? str.substring(0, lastIndexOf) : str;
    }

    public static String getExtensionFromFileName(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf != -1 ? str.substring(lastIndexOf + 1) : "";
    }

    public static String getExtensionFromUrl(String str) {
        try {
            Uri parse = Uri.parse(str);
            if (!TextUtils.isEmpty(parse.getPath())) {
                return getExtensionFromFilePath(parse.getPath());
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getExtensionFromFilePath(String str) {
        return getExtensionFromFileName(getNameFromFilePath(str));
    }

    public static Bitmap getImageThumbnail(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i3 = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i4 = options.outWidth / i;
        int i5 = options.outHeight / i2;
        if (i4 >= i5) {
            i4 = i5;
        }
        if (i4 > 0) {
            i3 = i4;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = i3;
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(str, options), i, i2, 2);
    }

    public static boolean isFileNameTooLength(String str) {
        return str != null && str.length() > 50;
    }

    @TargetApi(19)
    public static String getFilePathFromUri(Context context, Uri uri) {
        Uri uri2 = null;
        if (uri != null) {
            if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(context, uri)) {
                if ("content".equalsIgnoreCase(uri.getScheme())) {
                    return a(context, uri, null, null);
                }
                if ("file".equalsIgnoreCase(uri.getScheme())) {
                    return uri.getPath();
                }
            } else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String[] split = DocumentsContract.getDocumentId(uri).split(Constants.COLON_SEPARATOR);
                String str = split[0];
                if (MimeTypes.BASE_TYPE_IMAGE.equals(str)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                return a(context, uri2, "_id=?", new String[]{split[1]});
            }
        }
        return null;
    }

    private static String a(Context context, Uri uri, String str, String[] strArr) {
        Throwable th;
        Cursor cursor;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                        }
                        return string;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
        }
    }

    public static File getFileFromUri(Context context, Uri uri) {
        String filePathFromUri = getFilePathFromUri(context, uri);
        if (!TextUtils.isEmpty(filePathFromUri)) {
            return new File(filePathFromUri);
        }
        return null;
    }

    public static byte[] readFileFromUri(Context context, Uri uri) throws IOException {
        File fileFromUri = getFileFromUri(context, uri);
        if (fileFromUri != null) {
            return readFileToByteArray(fileFromUri);
        }
        return new byte[0];
    }

    public static byte[] readFileToByteArray(File file) throws IOException {
        Throwable th;
        FileInputStream fileInputStream;
        try {
            fileInputStream = a(file);
            try {
                long length = file.length();
                if (length <= 2147483647L) {
                    int i = (int) length;
                    int i2 = 0;
                    if (i == 0) {
                        byte[] bArr = new byte[0];
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return bArr;
                    }
                    byte[] bArr2 = new byte[i];
                    while (i2 < i) {
                        int read = fileInputStream.read(bArr2, i2, i - i2);
                        if (read == -1) {
                            break;
                        }
                        i2 += read;
                    }
                    if (i2 == i) {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return bArr2;
                    }
                    throw new IOException("Unexpected read size. current: " + i2 + ", expected: " + i);
                }
                throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + length);
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
        }
    }

    private static FileInputStream a(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        } else if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        } else if (file.canRead()) {
            return new FileInputStream(file);
        } else {
            throw new IOException("File '" + file + "' cannot be read");
        }
    }

    public static boolean isVideo(String str) {
        String mimeType = getMimeType(str);
        return !TextUtils.isEmpty(mimeType) && mimeType.startsWith("video/");
    }

    public static String getMimeType(String str) {
        String guessMimeTypeFromExtension;
        int lastIndexOf = str.lastIndexOf(46);
        return (lastIndexOf == -1 || (guessMimeTypeFromExtension = MimeUtils.guessMimeTypeFromExtension(str.substring(lastIndexOf + 1, str.length()).toLowerCase())) == null) ? "*/*" : guessMimeTypeFromExtension;
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

    public static boolean deleteFile(String str) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory() || isSymlink(file)) {
            return file.delete();
        }
        File[] listFiles = file.listFiles();
        boolean z = true;
        if (listFiles != null) {
            boolean z2 = true;
            for (File file2 : listFiles) {
                z2 = z2 && deleteFile(file2.getAbsolutePath());
            }
            z = z2;
        }
        return z ? file.delete() : z;
    }

    public static String getExtension(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf == -1 ? "" : str.substring(lastIndexOf + 1);
    }

    public static String getExtensionInLowerCase(String str) {
        String extension = getExtension(str);
        return ContainerUtil.isEmpty(extension) ? "" : extension.toLowerCase();
    }

    public static String getFileName(String str) {
        int lastIndexOf = str.lastIndexOf(File.separator);
        return lastIndexOf == -1 ? str : str.substring(lastIndexOf + 1);
    }

    public static boolean createDir(String str) {
        if (str == null) {
            return false;
        }
        File file = new File(str);
        if (file.exists() && file.isFile()) {
            Log.d("FileUtils", String.format("%s exists and is file", str));
            return false;
        } else if (file.exists() && file.isDirectory() && file.canWrite()) {
            return true;
        } else {
            boolean mkdirs = file.mkdirs();
            Log.d("FileUtils", String.format("mkdirs %s, %B", str, Boolean.valueOf(mkdirs)));
            return mkdirs;
        }
    }

    public static String getFileDirectory(String str) {
        if (str == null) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(File.separator);
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
    }

    public static String concat(String str, String str2) {
        if (str == null) {
            return str2;
        }
        if (str.endsWith(File.separator)) {
            return str + str2;
        }
        return str + File.separator + str2;
    }

    public static boolean existsFile(String str) {
        return existsFile(str, false);
    }

    public static boolean existsFile(String str, boolean z) {
        if (str == null) {
            return false;
        }
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        if (file.length() > 0) {
            return true;
        }
        return z;
    }

    public static boolean existsDirectory(String str) {
        if (str == null) {
            return false;
        }
        File file = new File(str);
        return file.exists() && file.isDirectory();
    }

    public static boolean isSymlink(File file) throws IOException {
        if (file != null) {
            if (file.getParent() != null) {
                file = new File(file.getParentFile().getCanonicalFile(), file.getName());
            }
            return !file.getCanonicalFile().equals(file.getAbsoluteFile());
        }
        throw new NullPointerException("File must not be null");
    }

    public static List<String> getFiles(List<String> list, File file) {
        if (file.isDirectory()) {
            String[] list2 = file.list();
            if (list2.length > 0) {
                return getFiles(list, new File(file, list2[0]));
            }
        }
        list.add(file.getAbsolutePath());
        return list;
    }
}
