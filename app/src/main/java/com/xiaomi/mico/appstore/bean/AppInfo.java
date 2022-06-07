package com.xiaomi.mico.appstore.bean;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Objects;

/* loaded from: classes3.dex */
public class AppInfo {
    public static final String TYPE_QUICK_APP = "quick_app";
    public static final String TYPE_SYSTEM_APP = "system_app";
    public static final String TYPE_THIRD_PARTY = "3rd_party";
    @SerializedName("appName")
    private String a;
    @SerializedName("updateDate")
    private String b;
    @SerializedName("appType")
    private String c;
    @SerializedName("iconUrl")
    private String d;
    @SerializedName("packageName")
    private String e;
    @SerializedName("versionCode")
    private long f;
    @SerializedName("versionName")
    private String g;
    @SerializedName("onlineStatus")
    private String h;
    @SerializedName("removable")
    private boolean i;
    @SerializedName("appCategory")
    private String j;
    @SerializedName("downloadUrl")
    private String k;
    @SerializedName("micoAction")
    private String l;
    @SerializedName("hardware")
    private String m;
    @SerializedName("channel")
    private String n;
    @SerializedName("MD5")
    private String o;
    @SerializedName("hidden")
    private boolean p;
    @SerializedName("portion")
    private String q;
    @SerializedName("appKey")
    private long r;
    @SerializedName("hardwareVersion")
    private String s;
    @SerializedName("modeType")
    private String t;
    @SerializedName("tags")
    private String u;
    private int v;
    @SerializedName("sizeCache")
    private long w = 0;
    @SerializedName("sizeApp")
    private long x = 0;
    @SerializedName("sizeData")
    private long y = 0;

    public AppInfo(String str, String str2, String str3, String str4, String str5, String str6) {
        this.e = str3;
        this.d = str;
        this.a = str2;
        this.k = str4;
        this.l = str5;
        this.c = str6;
    }

    public AppInfo(int i, String str, String str2) {
        this.v = i;
        this.e = str2;
        this.a = str;
    }

    public String getAppName() {
        return this.a;
    }

    public void setAppName(String str) {
        this.a = str;
    }

    public String getUpdateDate() {
        return this.b;
    }

    public void setUpdateDate(String str) {
        this.b = str;
    }

    public String getAppType() {
        return this.c;
    }

    public void setAppType(String str) {
        this.c = str;
    }

    public String getIconUrl() {
        return this.d;
    }

    public String getPackageName() {
        return this.e;
    }

    public long getVersionCode() {
        return this.f;
    }

    public String getVersionName() {
        return this.g;
    }

    public String getOnlineStatus() {
        return this.h;
    }

    public String getDownloadUrl() {
        return this.k;
    }

    public String getMicoAction() {
        return this.l;
    }

    public boolean isRemovable() {
        return this.i;
    }

    public String getAppCategory() {
        return this.j;
    }

    public int getIconResId() {
        return this.v;
    }

    public void setIconResId(int i) {
        this.v = i;
    }

    public String[] getHardware() {
        if (!TextUtils.isEmpty(this.m)) {
            return (String[]) Gsons.getGson().fromJson(this.m, (Class<Object>) String[].class);
        }
        return null;
    }

    public String getHardwareVersion() {
        return this.s;
    }

    public void setHardwareVersion(String str) {
        this.s = str;
    }

    public String[] getChannel() {
        if (!TextUtils.isEmpty(this.n)) {
            return (String[]) Gsons.getGson().fromJson(this.n, (Class<Object>) String[].class);
        }
        return null;
    }

    public String getMD5() {
        return this.o;
    }

    public boolean isOnline() {
        if (TextUtils.equals(TYPE_THIRD_PARTY, this.c)) {
            return TextUtils.equals("online", this.h);
        }
        return true;
    }

    public boolean isHidden() {
        return this.p;
    }

    public String[] getPortion() {
        if (!TextUtils.isEmpty(this.q)) {
            return this.q.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        }
        return null;
    }

    public long getAppKey() {
        return this.r;
    }

    public void setAppKey(long j) {
        this.r = j;
    }

    public long getSizeCache() {
        return this.w;
    }

    public void setSizeCache(long j) {
        this.w = j;
    }

    public long getSizeApp() {
        return this.x;
    }

    public void setSizeApp(long j) {
        this.x = j;
    }

    public long getSizeData() {
        return this.y;
    }

    public void setSizeData(long j) {
        this.y = j;
    }

    public void setIconUrl(String str) {
        this.d = str;
    }

    public void setPackageName(String str) {
        this.e = str;
    }

    public void setVersionCode(long j) {
        this.f = j;
    }

    public void setVersionName(String str) {
        this.g = str;
    }

    public void setOnlineStatus(String str) {
        this.h = str;
    }

    public void setRemovable(boolean z) {
        this.i = z;
    }

    public void setAppCategory(String str) {
        this.j = str;
    }

    public void setDownloadUrl(String str) {
        this.k = str;
    }

    public void setMicoAction(String str) {
        this.l = str;
    }

    public void setHardware(String str) {
        this.m = str;
    }

    public void setChannel(String str) {
        this.n = str;
    }

    public void setMD5(String str) {
        this.o = str;
    }

    public void setHidden(boolean z) {
        this.p = z;
    }

    public void setPortion(String str) {
        this.q = str;
    }

    public static AppInfo fromJson(String str) {
        return (AppInfo) Gsons.getGson().fromJson(str, new TypeToken<AppInfo>() { // from class: com.xiaomi.mico.appstore.bean.AppInfo.1
        }.getType());
    }

    public String toJson() {
        return Gsons.getGson().toJson(this);
    }

    public boolean matchChannel(String str) {
        String[] channel = getChannel();
        if (channel == null || channel.length <= 0) {
            return true;
        }
        for (String str2 : channel) {
            if (str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchHardware(String str) {
        String[] hardware = getHardware();
        if (hardware == null || hardware.length <= 0) {
            return true;
        }
        for (String str2 : hardware) {
            if (str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchHardwareVersion(String str, String str2) {
        if (TextUtils.isEmpty(this.s)) {
            return true;
        }
        String[] strArr = (String[]) Gsons.getGson().fromJson(this.s, (Class<Object>) String[].class);
        if (strArr == null || strArr.length == 0) {
            return false;
        }
        boolean z = false;
        for (String str3 : strArr) {
            if (str3.length() >= str.length() && str3.substring(0, str.length()).equalsIgnoreCase(str)) {
                if (str3.length() <= str.length() + 1) {
                    return true;
                }
                String[] split = str3.substring(str.length() + 1, str3.length()).split("\\.");
                String[] split2 = str2.split("\\.");
                boolean z2 = z;
                int i = 0;
                while (true) {
                    if (i >= split.length || i >= split2.length) {
                        break;
                    }
                    int parseInt = Integer.parseInt(split[i]);
                    int parseInt2 = Integer.parseInt(split2[i]);
                    if (parseInt2 > parseInt) {
                        z2 = true;
                        break;
                    } else if (parseInt2 < parseInt) {
                        break;
                    } else {
                        if (i == split.length - 1) {
                            z2 = true;
                        }
                        i++;
                    }
                }
                if (z2) {
                    return z2;
                }
                z = z2;
            }
        }
        return z;
    }

    public String getTags() {
        return this.u;
    }

    public void setTags(String str) {
        this.u = str;
    }

    public String getModeType() {
        return this.t;
    }

    public void setModeType(String str) {
        this.t = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AppInfo appInfo = (AppInfo) obj;
        return this.f == appInfo.f && this.i == appInfo.i && this.p == appInfo.p && this.r == appInfo.r && this.v == appInfo.v && Objects.equals(this.a, appInfo.a) && Objects.equals(this.b, appInfo.b) && Objects.equals(this.c, appInfo.c) && Objects.equals(this.d, appInfo.d) && Objects.equals(this.e, appInfo.e) && Objects.equals(this.g, appInfo.g) && Objects.equals(this.h, appInfo.h) && Objects.equals(this.j, appInfo.j) && Objects.equals(this.k, appInfo.k) && Objects.equals(this.l, appInfo.l) && Objects.equals(this.m, appInfo.m) && Objects.equals(this.n, appInfo.n) && Objects.equals(this.o, appInfo.o) && Objects.equals(this.q, appInfo.q) && Objects.equals(this.s, appInfo.s) && Objects.equals(this.t, appInfo.t) && Objects.equals(this.u, appInfo.u);
    }

    public int hashCode() {
        return Objects.hash(this.a, this.b, this.c, this.d, this.e, Long.valueOf(this.f), this.g, this.h, Boolean.valueOf(this.i), this.j, this.k, this.l, this.m, this.n, this.o, Boolean.valueOf(this.p), this.q, Long.valueOf(this.r), this.s, Integer.valueOf(this.v), this.t, this.u);
    }

    public boolean needShow(boolean z) {
        if ("GENERAL".equals(this.t)) {
            return true;
        }
        if (z) {
            return "CHILD".equals(this.t);
        }
        return "ADULT".equals(this.t);
    }
}
