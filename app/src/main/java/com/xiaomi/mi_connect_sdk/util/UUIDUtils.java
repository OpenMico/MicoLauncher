package com.xiaomi.mi_connect_sdk.util;

import java.util.UUID;

/* loaded from: classes3.dex */
public class UUIDUtils {
    private static final String UUID_BASE = "0000%4s-0000-1000-8000-00805f9b34fb";
    public static final String UUID_FORMAT = "0000%04x-0000-1000-8000-00805f9b34fb";

    public static UUID makeUUID(int i) {
        return UUID.fromString(String.format("0000%04x-0000-1000-8000-00805f9b34fb", Integer.valueOf(i)));
    }

    public static UUID makeUUID(String str) {
        return UUID.fromString(String.format(UUID_BASE, str));
    }
}
