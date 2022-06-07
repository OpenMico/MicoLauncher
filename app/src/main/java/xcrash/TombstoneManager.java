package xcrash;

import android.text.TextUtils;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

/* loaded from: classes6.dex */
public class TombstoneManager {
    private TombstoneManager() {
    }

    public static boolean appendSection(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || str3 == null) {
            return false;
        }
        d a = d.a();
        return a.a(str, "\n\n" + str2 + ":\n" + str3 + "\n\n");
    }

    public static boolean isJavaCrash(File file) {
        return file.getName().endsWith(".java.xcrash");
    }

    public static boolean isNativeCrash(File file) {
        return file.getName().endsWith(".native.xcrash");
    }

    public static boolean isAnr(File file) {
        return file.getName().endsWith(".anr.xcrash");
    }

    public static File[] getJavaTombstones() {
        return a(new String[]{".java.xcrash"});
    }

    public static File[] getNativeTombstones() {
        return a(new String[]{".native.xcrash"});
    }

    public static File[] getAnrTombstones() {
        return a(new String[]{".anr.xcrash"});
    }

    public static File[] getAllTombstones() {
        return a(new String[]{".java.xcrash", ".native.xcrash", ".anr.xcrash"});
    }

    public static boolean deleteTombstone(File file) {
        return d.a().a(file);
    }

    public static boolean deleteTombstone(String str) {
        return d.a().a(new File(str));
    }

    public static boolean clearJavaTombstones() {
        return b(new String[]{".java.xcrash"});
    }

    public static boolean clearNativeTombstones() {
        return b(new String[]{".native.xcrash"});
    }

    public static boolean clearAnrTombstones() {
        return b(new String[]{".anr.xcrash"});
    }

    public static boolean clearAllTombstones() {
        return b(new String[]{".java.xcrash", ".native.xcrash", ".anr.xcrash"});
    }

    private static File[] a(final String[] strArr) {
        String c = XCrash.c();
        if (c == null) {
            return new File[0];
        }
        File file = new File(c);
        if (!file.exists() || !file.isDirectory()) {
            return new File[0];
        }
        File[] listFiles = file.listFiles(new FilenameFilter() { // from class: xcrash.TombstoneManager.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str) {
                if (!str.startsWith("tombstone_")) {
                    return false;
                }
                for (String str2 : strArr) {
                    if (str.endsWith(str2)) {
                        return true;
                    }
                }
                return false;
            }
        });
        if (listFiles == null) {
            return new File[0];
        }
        Arrays.sort(listFiles, new Comparator<File>() { // from class: xcrash.TombstoneManager.2
            /* renamed from: a */
            public int compare(File file2, File file3) {
                return file2.getName().compareTo(file3.getName());
            }
        });
        return listFiles;
    }

    private static boolean b(final String[] strArr) {
        File[] listFiles;
        String c = XCrash.c();
        if (c == null) {
            return false;
        }
        File file = new File(c);
        if (!file.exists() || !file.isDirectory() || (listFiles = file.listFiles(new FilenameFilter() { // from class: xcrash.TombstoneManager.3
            @Override // java.io.FilenameFilter
            public boolean accept(File file2, String str) {
                if (!str.startsWith("tombstone_")) {
                    return false;
                }
                for (String str2 : strArr) {
                    if (str.endsWith(str2)) {
                        return true;
                    }
                }
                return false;
            }
        })) == null) {
            return false;
        }
        boolean z = true;
        for (File file2 : listFiles) {
            if (!d.a().a(file2)) {
                z = false;
            }
        }
        return z;
    }
}
