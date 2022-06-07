package com.xiaomi.miot.support.core;

import java.util.List;

/* loaded from: classes3.dex */
public class MiotDeviceEvent {
    public final List<Attr> attrs;
    public final String did;
    public final String roomId;

    /* loaded from: classes3.dex */
    public enum Type {
        TEMPERATURE,
        HUMIDITY,
        PM25
    }

    /* loaded from: classes3.dex */
    public static class Attr {
        public final Type type;
        public final String value;

        public Attr(Type type, String str) {
            this.type = type;
            this.value = str;
        }
    }

    public MiotDeviceEvent(String str, String str2, List<Attr> list) {
        this.did = str;
        this.roomId = str2;
        this.attrs = list;
    }
}
