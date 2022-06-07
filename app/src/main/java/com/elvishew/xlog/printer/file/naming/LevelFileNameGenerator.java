package com.elvishew.xlog.printer.file.naming;

import com.elvishew.xlog.LogLevel;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/naming/LevelFileNameGenerator.class */
public class LevelFileNameGenerator implements FileNameGenerator {
    @Override // com.elvishew.xlog.printer.file.naming.FileNameGenerator
    public boolean isFileNameChangeable() {
        return true;
    }

    @Override // com.elvishew.xlog.printer.file.naming.FileNameGenerator
    public String generateFileName(int logLevel, long timestamp) {
        return LogLevel.getLevelName(logLevel);
    }
}
