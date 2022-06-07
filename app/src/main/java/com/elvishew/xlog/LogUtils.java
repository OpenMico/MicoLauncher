package com.elvishew.xlog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/LogUtils.class */
public class LogUtils {
    public static String formatJson(String json) {
        XLog.assertInitialization();
        return XLog.sLogConfiguration.jsonFormatter.format(json);
    }

    public static String formatXml(String xml) {
        XLog.assertInitialization();
        return XLog.sLogConfiguration.xmlFormatter.format(xml);
    }

    public static String formatThrowable(Throwable throwable) {
        XLog.assertInitialization();
        return XLog.sLogConfiguration.throwableFormatter.format(throwable);
    }

    public static String formatThread(Thread thread) {
        XLog.assertInitialization();
        return XLog.sLogConfiguration.threadFormatter.format(thread);
    }

    public static String formatStackTrace(StackTraceElement[] stackTrace) {
        XLog.assertInitialization();
        return XLog.sLogConfiguration.stackTraceFormatter.format(stackTrace);
    }

    public static String addBorder(String[] segments) {
        XLog.assertInitialization();
        return XLog.sLogConfiguration.borderFormatter.format(segments);
    }

    public static void compress(String folderPath, String zipFilePath) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IOException("Folder " + folderPath + " does't exist or isn't a directory");
        }
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            File zipFolder = zipFile.getParentFile();
            if (!zipFolder.exists() && !zipFolder.mkdirs()) {
                throw new IOException("Zip folder " + zipFolder.getAbsolutePath() + " not created");
            } else if (!zipFile.createNewFile()) {
                throw new IOException("Zip file " + zipFilePath + " not created");
            }
        }
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try {
            byte[] buffer = new byte[8192];
            String[] list = folder.list();
            for (String fileName : list) {
                if (!fileName.equals(".") && !fileName.equals("..")) {
                    File file = new File(folder, fileName);
                    if (file.isFile()) {
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file), 8192);
                        zos.putNextEntry(new ZipEntry(fileName));
                        while (true) {
                            int count = bis.read(buffer, 0, 8192);
                            if (count == -1) {
                                break;
                            }
                            zos.write(buffer, 0, count);
                        }
                        try {
                            bis.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
            try {
                zos.close();
            } catch (IOException e2) {
            }
        } catch (Throwable th) {
            try {
                zos.close();
            } catch (IOException e3) {
            }
            throw th;
        }
    }
}
