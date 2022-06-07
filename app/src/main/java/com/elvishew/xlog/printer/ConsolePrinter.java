package com.elvishew.xlog.printer;

import com.elvishew.xlog.flattener.Flattener;
import com.elvishew.xlog.internal.DefaultsFactory;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/ConsolePrinter.class */
public class ConsolePrinter implements Printer {
    private Flattener flattener;

    public ConsolePrinter() {
        this.flattener = DefaultsFactory.createFlattener();
    }

    public ConsolePrinter(Flattener flattener) {
        this.flattener = flattener;
    }

    @Override // com.elvishew.xlog.printer.Printer
    public void println(int logLevel, String tag, String msg) {
        System.out.println(this.flattener.flatten(logLevel, tag, msg).toString());
    }
}
