package com.umeng.commonsdk.utils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileLock;

/* loaded from: classes2.dex */
public class FileLockUtil {
    private final Object lockObject = new Object();

    public void doFileOperateion(File file, FileLockCallback fileLockCallback, Object obj) {
        if (file.exists()) {
            synchronized (this.lockObject) {
                FileLock fileLock = getFileLock(file.getAbsolutePath());
                if (fileLock != null) {
                    try {
                        fileLockCallback.onFileLock(file.getName(), obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            fileLock.release();
                            fileLock.channel().close();
                        } catch (IOException e2) {
                            e = e2;
                            e.printStackTrace();
                        }
                    }
                    try {
                        fileLock.release();
                        fileLock.channel().close();
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void doFileOperateion(File file, FileLockCallback fileLockCallback, int i) {
        if (file.exists()) {
            synchronized (this.lockObject) {
                FileLock fileLock = getFileLock(file.getAbsolutePath());
                if (fileLock != null) {
                    try {
                        fileLockCallback.onFileLock(file, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            fileLock.release();
                            fileLock.channel().close();
                        } catch (Throwable th) {
                            th = th;
                            th.printStackTrace();
                        }
                    }
                    try {
                        fileLock.release();
                        fileLock.channel().close();
                    } catch (Throwable th2) {
                        th = th2;
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    public void doFileOperateion(File file, FileLockCallback fileLockCallback) {
        if (file.exists()) {
            synchronized (this.lockObject) {
                FileLock fileLock = getFileLock(file.getAbsolutePath());
                if (fileLock != null) {
                    try {
                        fileLockCallback.onFileLock(file.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            fileLock.release();
                            fileLock.channel().close();
                        } catch (IOException e2) {
                            e = e2;
                            e.printStackTrace();
                        }
                    }
                    try {
                        fileLock.release();
                        fileLock.channel().close();
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void doFileOperateion(String str, FileLockCallback fileLockCallback) {
        File file = new File(str);
        if (file.exists()) {
            synchronized (this.lockObject) {
                FileLock fileLock = getFileLock(str);
                if (fileLock != null) {
                    try {
                        fileLockCallback.onFileLock(file.getName());
                        try {
                            fileLock.release();
                            fileLock.channel().close();
                        } catch (IOException e) {
                            e = e;
                            e.printStackTrace();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        try {
                            fileLock.release();
                            fileLock.channel().close();
                        } catch (IOException e3) {
                            e = e3;
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0022 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.nio.channels.FileLock getFileLock(java.lang.String r3) {
        /*
            r0 = 0
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch: FileNotFoundException -> 0x001b, IOException -> 0x0015
            java.lang.String r2 = "rw"
            r1.<init>(r3, r2)     // Catch: FileNotFoundException -> 0x001b, IOException -> 0x0015
            java.nio.channels.FileChannel r3 = r1.getChannel()     // Catch: FileNotFoundException -> 0x001b, IOException -> 0x0015
            java.nio.channels.FileLock r3 = r3.lock()     // Catch: FileNotFoundException -> 0x0013, IOException -> 0x0011
            return r3
        L_0x0011:
            r1 = move-exception
            goto L_0x0017
        L_0x0013:
            r1 = move-exception
            goto L_0x001d
        L_0x0015:
            r1 = move-exception
            r3 = r0
        L_0x0017:
            r1.printStackTrace()
            goto L_0x0020
        L_0x001b:
            r1 = move-exception
            r3 = r0
        L_0x001d:
            r1.printStackTrace()
        L_0x0020:
            if (r3 == 0) goto L_0x002a
            r3.close()     // Catch: IOException -> 0x0026
            goto L_0x002a
        L_0x0026:
            r3 = move-exception
            r3.printStackTrace()
        L_0x002a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.FileLockUtil.getFileLock(java.lang.String):java.nio.channels.FileLock");
    }
}
