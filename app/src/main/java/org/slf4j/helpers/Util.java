package org.slf4j.helpers;

import java.io.PrintStream;

/* loaded from: classes4.dex */
public class Util {
    public static final void report(String str, Throwable th) {
        System.err.println(str);
        System.err.println("Reported exception:");
        th.printStackTrace();
    }

    public static final void report(String str) {
        PrintStream printStream = System.err;
        printStream.println("SLF4J: " + str);
    }
}
