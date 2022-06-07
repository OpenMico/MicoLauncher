package com.xiaomi.miplay.mylibrary.mirror;

/* loaded from: classes4.dex */
public final class DeviceMessage {
    public static final int TYPE_CLIPBOARD = 0;
    private int a;
    private String b;

    private DeviceMessage() {
    }

    public static DeviceMessage createClipboard(String str) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.a = 0;
        deviceMessage.b = str;
        return deviceMessage;
    }

    public int getType() {
        return this.a;
    }

    public String getText() {
        return this.b;
    }
}
