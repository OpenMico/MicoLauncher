package com.elvishew.xlog.printer.file.backup;

import java.io.File;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
@Deprecated
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/backup/FileSizeBackupStrategy.class */
public class FileSizeBackupStrategy implements BackupStrategy {
    private long maxSize;

    public FileSizeBackupStrategy(long maxSize) {
        this.maxSize = maxSize;
    }

    @Override // com.elvishew.xlog.printer.file.backup.BackupStrategy
    public boolean shouldBackup(File file) {
        return file.length() > this.maxSize;
    }
}
