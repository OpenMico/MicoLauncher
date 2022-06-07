package com.elvishew.xlog.internal.printer.file.backup;

import com.elvishew.xlog.printer.file.backup.BackupStrategy2;
import java.io.File;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/internal/printer/file/backup/BackupUtil.class */
public class BackupUtil {
    public static void backup(File loggingFile, BackupStrategy2 backupStrategy) {
        String loggingFileName = loggingFile.getName();
        String path = loggingFile.getParent();
        int maxBackupIndex = backupStrategy.getMaxBackupIndex();
        if (maxBackupIndex > 0) {
            File backupFile = new File(path, backupStrategy.getBackupFileName(loggingFileName, maxBackupIndex));
            if (backupFile.exists()) {
                backupFile.delete();
            }
            for (int i = maxBackupIndex - 1; i > 0; i--) {
                File backupFile2 = new File(path, backupStrategy.getBackupFileName(loggingFileName, i));
                if (backupFile2.exists()) {
                    backupFile2.renameTo(new File(path, backupStrategy.getBackupFileName(loggingFileName, i + 1)));
                }
            }
            loggingFile.renameTo(new File(path, backupStrategy.getBackupFileName(loggingFileName, 1)));
        } else if (maxBackupIndex == 0) {
            for (int i2 = 1; i2 < Integer.MAX_VALUE; i2++) {
                File nextBackupFile = new File(path, backupStrategy.getBackupFileName(loggingFileName, i2));
                if (!nextBackupFile.exists()) {
                    loggingFile.renameTo(nextBackupFile);
                    return;
                }
            }
        }
    }

    public static void verifyBackupStrategy(BackupStrategy2 backupStrategy) {
        int maxBackupIndex = backupStrategy.getMaxBackupIndex();
        if (maxBackupIndex < 0) {
            throw new IllegalArgumentException("Max backup index should not be less than 0");
        } else if (maxBackupIndex == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Max backup index too big: " + maxBackupIndex);
        }
    }
}
