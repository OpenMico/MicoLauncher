package com.zhangyue.we.anoprocesser;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/* loaded from: classes4.dex */
public class Log {
    private static Messager a;

    public static void init(Messager messager) {
        a = messager;
    }

    public static void w(String str) {
        Messager messager = a;
        if (messager != null) {
            messager.printMessage(Diagnostic.Kind.OTHER, str);
        }
    }

    public static void e(String str) {
        Messager messager = a;
        if (messager != null) {
            messager.printMessage(Diagnostic.Kind.ERROR, str);
        }
    }
}
