package com.xiaomi.miot.lib.impl;

import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/* loaded from: classes2.dex */
public class ZipFileUtils {
    private static final int BUFFER_SIZE = 1024;

    public static boolean syncUnzip(String str, String str2) {
        return doUnzip(str, str2, (String) null);
    }

    public static boolean syncUnzipWithFolder(ZipInputStream zipInputStream, String str) {
        while (true) {
            try {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry == null) {
                    return true;
                }
                String name = nextEntry.getName();
                if (nextEntry.isDirectory()) {
                    String substring = name.substring(0, name.length() - 1);
                    new File(str + File.separator + substring).mkdirs();
                } else {
                    File file = new File(str + File.separator + name);
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = zipInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                        fileOutputStream.flush();
                    }
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static boolean syncUnzipSingleFile(String str, String str2, String str3) {
        return doUnzip(str, str2, str3);
    }

    @Deprecated
    private static void syncUnzipOld(String str, String str2, UnzipLibraryRunnable.UnzipCallBack unzipCallBack) {
        new UnzipLibraryRunnable(str, str2, unzipCallBack).run();
    }

    @Deprecated
    private static void syncUnzipSingleFileOld(String str, String str2, String str3) {
        new UnzipLibraryRunnable(str, str2, str3, null).run();
    }

    @Deprecated
    private static void syncUnzipSingleFileOld(String str, String str2, String str3, UnzipLibraryRunnable.UnzipCallBack unzipCallBack) {
        new UnzipLibraryRunnable(str, str2, str3, unzipCallBack).run();
    }

    public static File createFileIfNotExists(String str) {
        File file = new File(str);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException unused) {
        }
        return file;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean doUnzip(String str, String str2, String str3) {
        try {
            return doUnzip(new ZipFile(str), str2, str3);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean doUnzip(ZipFile zipFile, String str, String str2) {
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            byte[] bArr = new byte[1024];
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                if (!zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    if (!(name == null || name.indexOf("\\") == -1)) {
                        name = name.replaceAll("\\\\", File.separator);
                    }
                    String str3 = str + File.separator + name;
                    if (TextUtils.isEmpty(str2) || str3.indexOf(str2) != -1) {
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(createFileIfNotExists(str3)));
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                        while (true) {
                            int read = bufferedInputStream.read(bArr, 0, 1024);
                            if (read == -1) {
                                break;
                            }
                            bufferedOutputStream.write(bArr, 0, read);
                        }
                        bufferedOutputStream.flush();
                        bufferedInputStream.close();
                        bufferedOutputStream.close();
                    }
                }
            }
            zipFile.close();
            return true;
        } catch (FileNotFoundException unused) {
            return false;
        } catch (Exception unused2) {
            return false;
        }
    }

    /* loaded from: classes2.dex */
    public static class UnzipLibraryRunnable implements Runnable {
        private String onlyUnzipFile;
        private String outputDirectory;
        private UnzipCallBack unzipCallBack;
        private String zipPath;

        /* loaded from: classes2.dex */
        public interface UnzipCallBack {
            void onSuccess();

            void onUnzipErr();
        }

        public UnzipLibraryRunnable(String str, String str2, String str3, UnzipCallBack unzipCallBack) {
            this.zipPath = str;
            this.outputDirectory = str2;
            this.onlyUnzipFile = str3;
            this.unzipCallBack = unzipCallBack;
        }

        public UnzipLibraryRunnable(String str, String str2, UnzipCallBack unzipCallBack) {
            this(str, str2, null, unzipCallBack);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ZipFileUtils.doUnzip(this.zipPath, this.outputDirectory, this.onlyUnzipFile)) {
                UnzipCallBack unzipCallBack = this.unzipCallBack;
                if (unzipCallBack != null) {
                    unzipCallBack.onSuccess();
                    return;
                }
                return;
            }
            UnzipCallBack unzipCallBack2 = this.unzipCallBack;
            if (unzipCallBack2 != null) {
                unzipCallBack2.onUnzipErr();
            }
        }
    }
}
