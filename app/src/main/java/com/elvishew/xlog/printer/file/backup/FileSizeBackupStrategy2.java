package com.elvishew.xlog.printer.file.backup;

import java.io.File;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/backup/FileSizeBackupStrategy2.class */
public class FileSizeBackupStrategy2 extends AbstractBackupStrategy {
    private long maxSize;
    private int maxBackupIndex;

    public FileSizeBackupStrategy2(long maxSize, int maxBackupIndex) {
        this.maxSize = maxSize;
        this.maxBackupIndex = maxBackupIndex;
    }

    @Override // com.elvishew.xlog.printer.file.backup.BackupStrategy
    public boolean shouldBackup(File file) {
        return file.length() > this.maxSize;
    }

    @Override // com.elvishew.xlog.printer.file.backup.BackupStrategy2
    public int getMaxBackupIndex() {
        return this.maxBackupIndex;
    }
}
