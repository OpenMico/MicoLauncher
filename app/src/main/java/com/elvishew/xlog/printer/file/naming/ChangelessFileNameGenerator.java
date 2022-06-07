package com.elvishew.xlog.printer.file.naming;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/naming/ChangelessFileNameGenerator.class */
public class ChangelessFileNameGenerator implements FileNameGenerator {
    private final String fileName;

    public ChangelessFileNameGenerator(String fileName) {
        this.fileName = fileName;
    }

    @Override // com.elvishew.xlog.printer.file.naming.FileNameGenerator
    public boolean isFileNameChangeable() {
        return false;
    }

    @Override // com.elvishew.xlog.printer.file.naming.FileNameGenerator
    public String generateFileName(int logLevel, long timestamp) {
        return this.fileName;
    }
}
