package com.elvishew.xlog.printer.file.clean;

import java.io.File;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/clean/NeverCleanStrategy.class */
public class NeverCleanStrategy implements CleanStrategy {
    @Override // com.elvishew.xlog.printer.file.clean.CleanStrategy
    public boolean shouldClean(File file) {
        return false;
    }
}
