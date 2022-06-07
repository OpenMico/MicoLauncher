package com.xiaomi.account.diagnosis.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes2.dex */
public class FileUtils {
    private FileUtils() {
    }

    public static void cleanDirectory(File file) throws IOException {
        IOException e = null;
        for (File file2 : verifiedListFiles(file)) {
            try {
                forceDelete(file2);
            } catch (IOException e2) {
                e = e2;
            }
        }
        if (e != null) {
            throw e;
        }
    }

    private static File[] verifiedListFiles(File file) throws IOException {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        } else if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                return listFiles;
            }
            throw new IOException("Failed to list contents of " + file);
        } else {
            throw new IllegalArgumentException(file + " is not a directory");
        }
    }

    public static boolean isSymlink(File file) throws IOException {
        if (file != null) {
            return isSymbolicLink(file);
        }
        throw new NullPointerException("File must not be null");
    }

    public static boolean isSymbolicLink(File file) throws IOException {
        return isSymbolicLink(file.getParentFile(), file.getName());
    }

    public static boolean isSymbolicLink(File file, String str) throws IOException {
        File file2 = new File(file.getCanonicalPath(), str);
        return !file2.getAbsolutePath().equals(file2.getCanonicalPath());
    }

    public static void deleteDirectory(File file) throws IOException {
        if (file.exists()) {
            if (!isSymlink(file)) {
                cleanDirectory(file);
            }
            if (!file.delete()) {
                throw new IOException("Unable to delete directory " + file + ".");
            }
        }
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
            return;
        }
        boolean exists = file.exists();
        if (file.delete()) {
            return;
        }
        if (!exists) {
            throw new FileNotFoundException("File does not exist: " + file);
        }
        throw new IOException("Unable to delete file: " + file);
    }
}
