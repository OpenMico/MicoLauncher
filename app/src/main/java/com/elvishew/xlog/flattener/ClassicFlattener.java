package com.elvishew.xlog.flattener;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/flattener/ClassicFlattener.class */
public class ClassicFlattener extends PatternFlattener {
    private static final String DEFAULT_PATTERN = "{d} {l}/{t}: {m}";

    public ClassicFlattener() {
        super(DEFAULT_PATTERN);
    }
}
