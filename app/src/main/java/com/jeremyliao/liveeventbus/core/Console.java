package com.jeremyliao.liveeventbus.core;

/* loaded from: classes2.dex */
public final class Console {
    private Console() {
    }

    public static String getInfo() {
        return LiveEventBusCore.get().console.getConsoleInfo();
    }
}
