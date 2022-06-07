package com.xiaomi.mico.base.utils;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* loaded from: classes3.dex */
public class IOUtils {
    public static final int BUFFER_SIZE = 4096;
    public static final String[] SUPPORTED_IMAGE_FORMATS = {"jpg", "png", "bmp", "gif", "webp"};

    public static void zip(ZipOutputStream zipOutputStream, File file, String str, FileFilter fileFilter) throws IOException {
        Throwable th;
        IOException e;
        FileInputStream fileInputStream;
        File[] fileArr;
        if (str == null) {
            str = "";
        }
        try {
            FileInputStream fileInputStream2 = null;
            try {
                if (file.isDirectory()) {
                    if (fileFilter != null) {
                        fileArr = file.listFiles(fileFilter);
                    } else {
                        fileArr = file.listFiles();
                    }
                    zipOutputStream.putNextEntry(new ZipEntry(str + File.separator));
                    String str2 = TextUtils.isEmpty(str) ? "" : str + File.separator;
                    if (fileArr != null) {
                        for (File file2 : fileArr) {
                            zip(zipOutputStream, file2, str2 + file2.getName(), null);
                        }
                    }
                    File[] listFiles = file.listFiles(new FileFilter() { // from class: com.xiaomi.mico.base.utils.IOUtils.1
                        @Override // java.io.FileFilter
                        public boolean accept(File file3) {
                            return file3.isDirectory();
                        }
                    });
                    if (listFiles != null) {
                        for (File file3 : listFiles) {
                            zip(zipOutputStream, file3, str2 + File.separator + file3.getName(), fileFilter);
                        }
                    }
                    fileInputStream = null;
                } else {
                    if (!TextUtils.isEmpty(str)) {
                        zipOutputStream.putNextEntry(new ZipEntry(str));
                    } else {
                        Date date = new Date();
                        zipOutputStream.putNextEntry(new ZipEntry(String.valueOf(date.getTime()) + ".txt"));
                    }
                    fileInputStream = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            zipOutputStream.write(bArr, 0, read);
                        }
                    } catch (IOException e2) {
                        e = e2;
                        fileInputStream2 = fileInputStream;
                        Log.e("IOUtils", "zipFiction failed with exception:", e);
                        closeQuietly(fileInputStream2);
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        closeQuietly(fileInputStream);
                        throw th;
                    }
                }
                closeQuietly(fileInputStream);
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static void zip(ZipOutputStream zipOutputStream, String str, InputStream inputStream) {
        try {
            if (!TextUtils.isEmpty(str)) {
                zipOutputStream.putNextEntry(new ZipEntry(str));
            } else {
                Date date = new Date();
                zipOutputStream.putNextEntry(new ZipEntry(String.valueOf(date.getTime()) + ".txt"));
            }
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    zipOutputStream.write(bArr, 0, read);
                } else {
                    return;
                }
            }
        } catch (IOException e) {
            Log.e("IOUtils", "zipFiction failed with exception:", e);
        }
    }

    public static void unZip(@NonNull File file, @NonNull File file2) {
        unZip(file.getAbsolutePath(), file2.getAbsolutePath());
    }

    public static void unZip(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            if (!str2.endsWith("/")) {
                str2 = str2 + "/";
            }
            try {
                ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
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
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            copy(zipInputStream, fileOutputStream);
                            closeQuietly(fileOutputStream);
                        }
                    } else {
                        closeQuietly(zipInputStream);
                        return;
                    }
                }
            } catch (IOException e) {
                Log.e("IOUtils", "unzip failed!!!", e);
            }
        }
    }

    public static void closeQuietly(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException unused) {
            }
            try {
                outputStream.close();
            } catch (IOException unused2) {
            }
        }
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, 4096);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += read;
        }
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
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read >= 0) {
                                fileOutputStream2.write(bArr, 0, read);
                            } else {
                                closeQuietly(fileInputStream);
                                closeQuietly(fileOutputStream2);
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        closeQuietly(fileInputStream);
                        closeQuietly(fileOutputStream);
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

    public static void hideFromMediaScanner(File file) {
        File file2 = new File(file, ".nomedia");
        if (!file2.exists() || !file2.isFile()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                Log.e("IOUtils", "hideFromMediaScanner", e);
            }
        }
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

    public static void deleteDirs(File file) {
        Log.v("IOUtils", "deleteDirs filePath = " + file.getAbsolutePath());
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

    public static String getFileSuffix(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf > 0 ? str.substring(lastIndexOf + 1) : "";
    }

    public static boolean isSupportImageSuffix(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (String str2 : SUPPORTED_IMAGE_FORMATS) {
            if (str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
