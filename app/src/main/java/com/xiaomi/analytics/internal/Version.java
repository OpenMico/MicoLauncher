package com.xiaomi.analytics.internal;

/* loaded from: classes3.dex */
public class Version implements Comparable<Version> {
    public int build;
    public int major;
    public int minor;

    public Version(int i, int i2, int i3) {
        this.major = 1;
        this.minor = 0;
        this.build = 0;
        this.major = i;
        this.minor = i2;
        this.build = i3;
    }

    public Version(String str) {
        this.major = 1;
        this.minor = 0;
        this.build = 0;
        try {
            String[] split = str.split("\\.");
            this.major = Integer.parseInt(split[0]);
            this.minor = Integer.parseInt(split[1]);
            this.build = Integer.parseInt(split[2]);
        } catch (Exception unused) {
        }
    }

    public boolean equalsIgnoreBuild(Version version) {
        return version != null && this.major == version.major && this.minor == version.minor;
    }

    public int compareTo(Version version) {
        if (version == null) {
            return 1;
        }
        int i = this.major;
        int i2 = version.major;
        if (i != i2) {
            return i - i2;
        }
        int i3 = this.minor;
        int i4 = version.minor;
        return i3 != i4 ? i3 - i4 : this.build - version.build;
    }

    public String toString() {
        return this.major + "." + this.minor + "." + this.build;
    }
}
