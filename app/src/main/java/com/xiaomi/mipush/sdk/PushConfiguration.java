package com.xiaomi.mipush.sdk;

import com.xiaomi.push.service.module.PushChannelRegion;

/* loaded from: classes4.dex */
public class PushConfiguration {
    private PushChannelRegion a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;

    /* loaded from: classes4.dex */
    public static class PushConfigurationBuilder {
        private PushChannelRegion a;
        private boolean b;
        private boolean c;
        private boolean d;
        private boolean e;

        public PushConfiguration build() {
            return new PushConfiguration(this);
        }

        public PushConfigurationBuilder openCOSPush(boolean z) {
            this.d = z;
            return this;
        }

        public PushConfigurationBuilder openFCMPush(boolean z) {
            this.c = z;
            return this;
        }

        public PushConfigurationBuilder openFTOSPush(boolean z) {
            this.e = z;
            return this;
        }

        public PushConfigurationBuilder openHmsPush(boolean z) {
            this.b = z;
            return this;
        }

        public PushConfigurationBuilder region(PushChannelRegion pushChannelRegion) {
            this.a = pushChannelRegion;
            return this;
        }
    }

    public PushConfiguration() {
        this.a = PushChannelRegion.China;
        this.b = false;
        this.c = false;
        this.d = false;
        this.e = false;
    }

    private PushConfiguration(PushConfigurationBuilder pushConfigurationBuilder) {
        this.a = pushConfigurationBuilder.a == null ? PushChannelRegion.China : pushConfigurationBuilder.a;
        this.b = pushConfigurationBuilder.b;
        this.c = pushConfigurationBuilder.c;
        this.d = pushConfigurationBuilder.d;
        this.e = pushConfigurationBuilder.e;
    }

    public boolean getOpenCOSPush() {
        return this.d;
    }

    public boolean getOpenFCMPush() {
        return this.c;
    }

    public boolean getOpenFTOSPush() {
        return this.e;
    }

    public boolean getOpenHmsPush() {
        return this.b;
    }

    public PushChannelRegion getRegion() {
        return this.a;
    }

    public void setOpenCOSPush(boolean z) {
        this.d = z;
    }

    public void setOpenFCMPush(boolean z) {
        this.c = z;
    }

    public void setOpenFTOSPush(boolean z) {
        this.e = z;
    }

    public void setOpenHmsPush(boolean z) {
        this.b = z;
    }

    public void setRegion(PushChannelRegion pushChannelRegion) {
        this.a = pushChannelRegion;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("PushConfiguration{");
        stringBuffer.append("Region:");
        PushChannelRegion pushChannelRegion = this.a;
        stringBuffer.append(pushChannelRegion == null ? "null" : pushChannelRegion.name());
        stringBuffer.append(",mOpenHmsPush:" + this.b);
        stringBuffer.append(",mOpenFCMPush:" + this.c);
        stringBuffer.append(",mOpenCOSPush:" + this.d);
        stringBuffer.append(",mOpenFTOSPush:" + this.e);
        stringBuffer.append('}');
        return stringBuffer.toString();
    }
}
