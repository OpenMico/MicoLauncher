package androidx.work.impl;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Logger;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WorkDatabasePathHelper {
    private static final String a = Logger.tagWithPrefix("WrkDbPathHelper");
    private static final String[] b = {"-journal", "-shm", "-wal"};

    @NonNull
    public static String getWorkDatabaseName() {
        return "androidx.work.workdb";
    }

    private WorkDatabasePathHelper() {
    }

    public static void migrateDatabase(@NonNull Context context) {
        File defaultDatabasePath = getDefaultDatabasePath(context);
        if (Build.VERSION.SDK_INT >= 23 && defaultDatabasePath.exists()) {
            Logger.get().debug(a, "Migrating WorkDatabase to the no-backup directory", new Throwable[0]);
            Map<File, File> migrationPaths = migrationPaths(context);
            for (File file : migrationPaths.keySet()) {
                File file2 = migrationPaths.get(file);
                if (file.exists() && file2 != null) {
                    if (file2.exists()) {
                        Logger.get().warning(a, String.format("Over-writing contents of %s", file2), new Throwable[0]);
                    }
                    Logger.get().debug(a, file.renameTo(file2) ? String.format("Migrated %s to %s", file, file2) : String.format("Renaming %s to %s failed", file, file2), new Throwable[0]);
                }
            }
        }
    }

    @NonNull
    @VisibleForTesting
    public static Map<File, File> migrationPaths(@NonNull Context context) {
        HashMap hashMap = new HashMap();
        if (Build.VERSION.SDK_INT >= 23) {
            File defaultDatabasePath = getDefaultDatabasePath(context);
            File databasePath = getDatabasePath(context);
            hashMap.put(defaultDatabasePath, databasePath);
            String[] strArr = b;
            for (String str : strArr) {
                hashMap.put(new File(defaultDatabasePath.getPath() + str), new File(databasePath.getPath() + str));
            }
        }
        return hashMap;
    }

    @NonNull
    @VisibleForTesting
    public static File getDefaultDatabasePath(@NonNull Context context) {
        return context.getDatabasePath("androidx.work.workdb");
    }

    @NonNull
    @VisibleForTesting
    public static File getDatabasePath(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return getDefaultDatabasePath(context);
        }
        return a(context, "androidx.work.workdb");
    }

    @RequiresApi(23)
    private static File a(@NonNull Context context, @NonNull String str) {
        return new File(context.getNoBackupFilesDir(), str);
    }
}
