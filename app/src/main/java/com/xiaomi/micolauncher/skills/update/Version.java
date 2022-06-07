package com.xiaomi.micolauncher.skills.update;

import android.annotation.SuppressLint;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class Version {
    public int buildVersion;
    public String description;
    public int majorVersion;
    public int minorVersion;

    public Version() {
    }

    public Version(String str) {
        parse(str);
    }

    public void reset() {
        this.majorVersion = 0;
        this.minorVersion = 0;
        this.buildVersion = 0;
    }

    public boolean parse(String str) {
        String[] split = str.split("\\.");
        if (split.length < 3) {
            return false;
        }
        try {
            this.majorVersion = Integer.parseInt(split[0]);
            this.minorVersion = Integer.parseInt(split[1]);
            this.buildVersion = Integer.parseInt(split[2]);
        } catch (NumberFormatException e) {
            Logger logger = L.base;
            logger.e("failed to parse version string " + str, e);
        }
        return true;
    }

    public String toString() {
        return this.majorVersion + "." + this.minorVersion + "." + this.buildVersion;
    }

    public boolean isEquals(Version version) {
        return version != null && this.majorVersion == version.majorVersion && this.minorVersion == version.minorVersion && this.buildVersion == version.buildVersion;
    }

    @SuppressLint({"DefaultLocale"})
    public int toIntVersion() {
        try {
            return Integer.parseInt(String.format("%02d%03d%03d", Integer.valueOf(this.majorVersion), Integer.valueOf(this.minorVersion), Integer.valueOf(this.buildVersion)));
        } catch (NumberFormatException e) {
            L.base.e("failed to get int version ", e);
            return this.majorVersion;
        }
    }
}
