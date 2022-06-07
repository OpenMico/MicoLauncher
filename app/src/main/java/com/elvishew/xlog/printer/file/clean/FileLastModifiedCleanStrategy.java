package com.elvishew.xlog.printer.file.clean;

import java.io.File;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/clean/FileLastModifiedCleanStrategy.class */
public class FileLastModifiedCleanStrategy implements CleanStrategy {
    private long maxTimeMillis;

    public FileLastModifiedCleanStrategy(long maxTimeMillis) {
        this.maxTimeMillis = maxTimeMillis;
    }

    @Override // com.elvishew.xlog.printer.file.clean.CleanStrategy
    public boolean shouldClean(File file) {
        return System.currentTimeMillis() - file.lastModified() > this.maxTimeMillis;
    }
}
