package com.elvishew.xlog.printer.file.backup;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/backup/AbstractBackupStrategy.class */
public abstract class AbstractBackupStrategy implements BackupStrategy2 {
    @Override // com.elvishew.xlog.printer.file.backup.BackupStrategy2
    public String getBackupFileName(String fileName, int backupIndex) {
        return fileName + ".bak." + backupIndex;
    }
}
