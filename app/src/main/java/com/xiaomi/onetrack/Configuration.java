package com.xiaomi.onetrack;

import android.text.TextUtils;
import com.xiaomi.onetrack.OneTrack;

/* loaded from: classes4.dex */
public class Configuration {
    private String a;
    private String b;
    private String c;
    private boolean d;
    private String e;
    private OneTrack.Mode f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    private boolean k;
    private boolean l;
    private String m;
    private boolean n;
    private OneTrack.IEventHook o;

    private Configuration(Builder builder) {
        this.f = OneTrack.Mode.APP;
        this.g = true;
        this.h = true;
        this.i = true;
        this.k = true;
        this.l = false;
        this.n = false;
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f;
        this.g = builder.g;
        this.i = builder.i;
        this.h = builder.h;
        this.j = builder.j;
        this.k = builder.k;
        this.l = builder.l;
        this.m = builder.m;
        this.n = builder.n;
    }

    public String getAppId() {
        return this.a;
    }

    public String getPluginId() {
        return this.b;
    }

    public String getChannel() {
        return this.c;
    }

    public boolean isInternational() {
        return this.d;
    }

    public String getRegion() {
        return this.e;
    }

    public OneTrack.Mode getMode() {
        return this.f;
    }

    @Deprecated
    public boolean isGAIDEnable() {
        return this.g;
    }

    public boolean isExceptionCatcherEnable() {
        return this.j;
    }

    public boolean isIMSIEnable() {
        return this.h;
    }

    public boolean isIMEIEnable() {
        return this.i;
    }

    public boolean isAutoTrackActivityAction() {
        return this.k;
    }

    public boolean isOverrideMiuiRegionSetting() {
        return this.l;
    }

    public String getInstanceId() {
        return this.m;
    }

    public boolean isUseCustomPrivacyPolicy() {
        return this.n;
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        private String a;
        private String b;
        private String c;
        private boolean d;
        private String e;
        private String m;
        private OneTrack.Mode f = OneTrack.Mode.APP;
        private boolean g = true;
        private boolean h = true;
        private boolean i = true;
        private boolean j = false;
        private boolean k = true;
        private boolean l = false;
        private boolean n = false;

        public Builder setAppId(String str) {
            this.a = str;
            return this;
        }

        public Builder setPluginId(String str) {
            this.b = str;
            return this;
        }

        public Builder setChannel(String str) {
            this.c = str;
            return this;
        }

        public Builder setInternational(boolean z) {
            this.d = z;
            return this;
        }

        public Builder setRegion(String str) {
            this.e = str;
            return this;
        }

        public Builder setMode(OneTrack.Mode mode) {
            this.f = mode;
            return this;
        }

        @Deprecated
        public Builder setGAIDEnable(boolean z) {
            this.g = z;
            return this;
        }

        public Builder setImeiEnable(boolean z) {
            this.i = z;
            return this;
        }

        public Builder setImsiEnable(boolean z) {
            this.h = z;
            return this;
        }

        public Builder setExceptionCatcherEnable(boolean z) {
            this.j = z;
            return this;
        }

        public Builder setAutoTrackActivityAction(boolean z) {
            this.k = z;
            return this;
        }

        public Builder setOverrideMiuiRegionSetting(boolean z) {
            this.l = z;
            return this;
        }

        public Builder setInstanceId(String str) {
            this.m = str;
            return this;
        }

        public Builder setUseCustomPrivacyPolicy(boolean z) {
            this.n = z;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }

    public String toString() {
        try {
            return "Configuration{appId='" + a(this.a) + "', pluginId='" + a(this.b) + "', channel='" + this.c + "', international=" + this.d + ", region='" + this.e + "', overrideMiuiRegionSetting=" + this.l + ", mode=" + this.f + ", GAIDEnable=" + this.g + ", IMSIEnable=" + this.h + ", IMEIEnable=" + this.i + ", ExceptionCatcherEnable=" + this.j + ", instanceId=" + a(this.m) + '}';
        } catch (Exception unused) {
            return "";
        }
    }

    private String a(String str) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(str) || str.length() <= 4) {
            sb.append(str);
        } else {
            for (int i = 0; i < str.length(); i++) {
                if (i == 0 || i == 1 || i == str.length() - 2 || i == str.length() - 1) {
                    sb.append(str.charAt(i));
                } else {
                    sb.append("*");
                }
            }
        }
        return sb.toString();
    }
}
