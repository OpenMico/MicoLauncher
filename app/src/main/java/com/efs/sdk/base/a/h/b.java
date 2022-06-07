package com.efs.sdk.base.a.h;

import com.efs.sdk.base.a.a.a;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/* loaded from: classes.dex */
public final class b {
    private static final Random a = new Random();

    public static boolean a(File file, byte[] bArr) {
        Throwable th;
        FileOutputStream fileOutputStream;
        Throwable th2;
        FileOutputStream fileOutputStream2;
        try {
            fileOutputStream = null;
            try {
                fileOutputStream2 = new FileOutputStream(file);
            } catch (Throwable th3) {
                th2 = th3;
            }
        } catch (Throwable th4) {
            th = th4;
        }
        try {
            fileOutputStream2.write(bArr);
            fileOutputStream2.flush();
            a(fileOutputStream2);
            return true;
        } catch (Throwable th5) {
            th2 = th5;
            fileOutputStream = fileOutputStream2;
            d.b("efs.util.file", "write file error, filename is " + file.getName(), th2);
            a(fileOutputStream);
            return false;
        }
    }

    public static boolean a(File file, String str) {
        return a(file, str.getBytes());
    }

    public static String a(File file) {
        return e(file);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.Closeable, java.io.FileInputStream] */
    private static String e(File file) {
        Throwable th;
        Closeable closeable;
        Throwable th2;
        String str = "";
        boolean exists = file.exists();
        if (!exists) {
            return str;
        }
        try {
            closeable = null;
            try {
                exists = new FileInputStream(file);
            } catch (Throwable th3) {
                th2 = th3;
            }
        } catch (Throwable th4) {
            th = th4;
        }
        try {
            byte[] bArr = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while (true) {
                int read = exists.read(bArr);
                if (read <= 0) {
                    break;
                }
                sb.append(new String(bArr, 0, read));
            }
            str = sb.toString();
            a((Closeable) exists);
            exists = sb;
        } catch (Throwable th5) {
            th2 = th5;
            closeable = exists;
            d.b("efs.util.file", "read file error", th2);
            a(closeable);
            exists = closeable;
            return str;
        }
        return str;
    }

    public static byte[] a(String str) {
        Throwable th;
        Exception e;
        FileInputStream fileInputStream;
        byte[] bArr = new byte[0];
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(str);
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            a(fileInputStream);
        } catch (Exception e3) {
            e = e3;
            fileInputStream2 = fileInputStream;
            d.b("efs.util.file", "read data error", e);
            a(fileInputStream2);
            return bArr;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            a(fileInputStream2);
            throw th;
        }
        return bArr;
    }

    public static void b(File file) {
        File[] listFiles;
        if (file != null && file.exists()) {
            if (file.isDirectory() && (listFiles = file.listFiles()) != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    b(file2);
                }
            }
            file.delete();
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
                d.b("efs.util.file", "safe close error", th);
            }
        }
    }

    public static long c(File file) {
        long j = 0;
        if (!file.isDirectory()) {
            return 0 + file.length();
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return 0L;
        }
        for (File file2 : listFiles) {
            j += c(file2);
        }
        return j;
    }

    public static List<File> d(File file) {
        if (file.isFile()) {
            return Collections.emptyList();
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (File file2 : listFiles) {
            if (file2.isFile()) {
                arrayList.add(file2);
            } else {
                arrayList.addAll(d(file2));
            }
        }
        return arrayList;
    }

    public static String a(com.efs.sdk.base.a.f.b bVar) {
        StringBuilder sb = new StringBuilder();
        sb.append(bVar.a.a);
        sb.append("_");
        sb.append(bVar.a.d);
        sb.append("_");
        sb.append(bVar.a.e);
        sb.append("_");
        sb.append((int) bVar.a.b);
        sb.append("_");
        sb.append(g.a());
        sb.append("_");
        sb.append(a.nextInt(10000));
        sb.append("_");
        a.a();
        sb.append(a.b());
        return sb.toString();
    }

    public static com.efs.sdk.base.a.f.b b(String str) {
        String[] split = str.split("_");
        if (split.length != 7) {
            d.a("efs.util.file", "File name error, name is ".concat(String.valueOf(str)), null);
            return null;
        }
        String str2 = split[0];
        String str3 = split[1];
        byte byteValue = Byte.valueOf(split[2]).byteValue();
        com.efs.sdk.base.a.f.b bVar = new com.efs.sdk.base.a.f.b(str2, Byte.valueOf(split[3]).byteValue());
        bVar.a(str3);
        bVar.a(byteValue);
        return bVar;
    }

    public static void a(File file, File file2) {
        Exception e;
        FileOutputStream fileOutputStream;
        byte[] bArr = new byte[524288];
        File parentFile = file2.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (file2.isDirectory()) {
            file2 = new File(file2, file.getName());
        }
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                while (true) {
                    try {
                        int read = fileInputStream2.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    } catch (Exception e2) {
                        e = e2;
                        fileInputStream = fileInputStream2;
                        try {
                            d.b("efs.util.file", "error when copy", e);
                            a(fileInputStream);
                            a(fileOutputStream);
                            b(file);
                        } catch (Throwable th) {
                            th = th;
                            a(fileInputStream);
                            a(fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = fileInputStream2;
                        a(fileInputStream);
                        a(fileOutputStream);
                        throw th;
                    }
                }
                a(fileInputStream2);
            } catch (Exception e3) {
                e = e3;
                fileOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
        } catch (Exception e4) {
            e = e4;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
        a(fileOutputStream);
        b(file);
    }
}
