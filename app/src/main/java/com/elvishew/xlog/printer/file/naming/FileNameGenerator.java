package com.elvishew.xlog.printer.file.naming;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/naming/FileNameGenerator.class */
public interface FileNameGenerator {
    boolean isFileNameChangeable();

    String generateFileName(int i, long j);
}
