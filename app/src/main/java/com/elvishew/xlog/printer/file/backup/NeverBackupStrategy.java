package com.elvishew.xlog.printer.file.backup;

import java.io.File;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/backup/NeverBackupStrategy.class */
public class NeverBackupStrategy implements BackupStrategy {
    @Override // com.elvishew.xlog.printer.file.backup.BackupStrategy
    public boolean shouldBackup(File file) {
        return false;
    }
}
