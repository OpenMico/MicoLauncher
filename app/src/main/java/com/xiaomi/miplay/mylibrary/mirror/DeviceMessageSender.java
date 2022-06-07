package com.xiaomi.miplay.mylibrary.mirror;

import java.io.IOException;

/* loaded from: classes4.dex */
public final class DeviceMessageSender {
    private final MirrorControl a;
    private String b;

    public DeviceMessageSender(MirrorControl mirrorControl) {
        this.a = mirrorControl;
    }

    public synchronized void pushClipboardText(String str) {
        this.b = str;
        notify();
    }

    public void loop() throws IOException, InterruptedException {
        String str;
        while (true) {
            synchronized (this) {
                while (this.b == null) {
                    wait();
                }
                str = this.b;
                this.b = null;
            }
            this.a.sendDeviceMessage(DeviceMessage.createClipboard(str));
        }
    }
}
