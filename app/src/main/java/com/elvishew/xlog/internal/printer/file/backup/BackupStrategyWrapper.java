package com.elvishew.xlog.internal.printer.file.backup;

import com.elvishew.xlog.printer.file.backup.BackupStrategy;
import com.elvishew.xlog.printer.file.backup.BackupStrategy2;
import java.io.File;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/internal/printer/file/backup/BackupStrategyWrapper.class */
public class BackupStrategyWrapper implements BackupStrategy2 {
    private BackupStrategy backupStrategy;

    public BackupStrategyWrapper(BackupStrategy backupStrategy) {
        this.backupStrategy = backupStrategy;
    }

    @Override // com.elvishew.xlog.printer.file.backup.BackupStrategy2
    public int getMaxBackupIndex() {
        return 1;
    }

    @Override // com.elvishew.xlog.printer.file.backup.BackupStrategy2
    public String getBackupFileName(String fileName, int backupIndex) {
        return fileName + ".bak";
    }

    @Override // com.elvishew.xlog.printer.file.backup.BackupStrategy
    public boolean shouldBackup(File file) {
        return this.backupStrategy.shouldBackup(file);
    }
}
