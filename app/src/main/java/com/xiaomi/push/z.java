package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes4.dex */
public class z {
    public static final String[] a = {"jpg", "png", "bmp", "gif", "webp"};

    public static String a(File file) {
        Throwable th;
        StringWriter stringWriter;
        IOException e;
        InputStreamReader inputStreamReader;
        try {
            stringWriter = new StringWriter();
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            inputStreamReader = new InputStreamReader(new BufferedInputStream(new FileInputStream(file)));
            try {
                char[] cArr = new char[2048];
                while (true) {
                    int read = inputStreamReader.read(cArr);
                    if (read != -1) {
                        stringWriter.write(cArr, 0, read);
                    } else {
                        String stringWriter2 = stringWriter.toString();
                        a(inputStreamReader);
                        a(stringWriter);
                        return stringWriter2;
                    }
                }
            } catch (IOException e2) {
                e = e2;
                b.c("read file :" + file.getAbsolutePath() + " failure :" + e.getMessage());
                a(inputStreamReader);
                a(stringWriter);
                return null;
            }
        } catch (IOException e3) {
            e = e3;
            inputStreamReader = null;
        } catch (Throwable th3) {
            th = th3;
            a((Closeable) null);
            a(stringWriter);
            throw th;
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: a */
    public static void m1175a(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                m1175a(file2);
            }
        } else if (!file.exists()) {
            return;
        }
        file.delete();
    }

    public static void a(File file, File file2) {
        Throwable th;
        IOException e;
        try {
            ZipOutputStream zipOutputStream = null;
            try {
                ZipOutputStream zipOutputStream2 = new ZipOutputStream(new FileOutputStream(file, false));
                try {
                    a(zipOutputStream2, file2, null, null);
                    a(zipOutputStream2);
                } catch (FileNotFoundException unused) {
                    zipOutputStream = zipOutputStream2;
                    a(zipOutputStream);
                } catch (IOException e2) {
                    e = e2;
                    zipOutputStream = zipOutputStream2;
                    b.m149a("zip file failure + " + e.getMessage());
                    a(zipOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    zipOutputStream = zipOutputStream2;
                    a(zipOutputStream);
                    throw th;
                }
            } catch (FileNotFoundException unused2) {
                a(zipOutputStream);
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    public static void a(File file, String str) {
        Throwable th;
        IOException e;
        BufferedWriter bufferedWriter;
        boolean exists = file.exists();
        BufferedWriter bufferedWriter2 = exists;
        if (!exists) {
            b.c("mkdir " + file.getAbsolutePath());
            File parentFile = file.getParentFile();
            parentFile.mkdirs();
            bufferedWriter2 = parentFile;
        }
        try {
            bufferedWriter2 = 0;
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            bufferedWriter.write(str);
            a(bufferedWriter);
        } catch (IOException e3) {
            e = e3;
            bufferedWriter2 = bufferedWriter;
            b.c("write file :" + file.getAbsolutePath() + " failure :" + e.getMessage());
            a(bufferedWriter2);
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter2 = bufferedWriter;
            a((Closeable) bufferedWriter2);
            throw th;
        }
    }

    public static void a(ZipOutputStream zipOutputStream, File file, String str, FileFilter fileFilter) {
        Throwable th;
        IOException e;
        FileInputStream fileInputStream;
        ZipEntry zipEntry;
        if (str == null) {
            str = "";
        }
        try {
            FileInputStream fileInputStream2 = null;
            try {
                if (file.isDirectory()) {
                    File[] listFiles = fileFilter != null ? file.listFiles(fileFilter) : file.listFiles();
                    zipOutputStream.putNextEntry(new ZipEntry(str + File.separator));
                    String str2 = TextUtils.isEmpty(str) ? "" : str + File.separator;
                    for (int i = 0; i < listFiles.length; i++) {
                        a(zipOutputStream, listFiles[i], str2 + listFiles[i].getName(), null);
                    }
                    File[] listFiles2 = file.listFiles(new j());
                    if (listFiles2 != null) {
                        for (File file2 : listFiles2) {
                            a(zipOutputStream, file2, str2 + File.separator + file2.getName(), fileFilter);
                        }
                    }
                    fileInputStream = null;
                } else {
                    if (!TextUtils.isEmpty(str)) {
                        zipEntry = new ZipEntry(str);
                    } else {
                        Date date = new Date();
                        zipEntry = new ZipEntry(String.valueOf(date.getTime()) + ".txt");
                    }
                    zipOutputStream.putNextEntry(zipEntry);
                    fileInputStream = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[1024];
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
                        b.d("zipFiction failed with exception:" + e.toString());
                        a(fileInputStream2);
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        a(fileInputStream);
                        throw th;
                    }
                }
                a(fileInputStream);
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* renamed from: a */
    public static boolean m1176a(File file) {
        try {
            if (file.isDirectory()) {
                return false;
            }
            if (file.exists()) {
                return true;
            }
            File parentFile = file.getParentFile();
            if (parentFile.exists() || parentFile.mkdirs()) {
                return file.createNewFile();
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static byte[] a(byte[] bArr) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.finish();
            gZIPOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Exception unused) {
            return bArr;
        }
    }

    public static void b(File file, File file2) {
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
}
