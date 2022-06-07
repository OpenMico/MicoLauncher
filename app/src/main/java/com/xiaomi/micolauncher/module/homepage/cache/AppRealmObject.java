package com.xiaomi.micolauncher.module.homepage.cache;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* loaded from: classes3.dex */
public class AppRealmObject extends RealmObject implements com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface {
    @SerializedName("appName")
    @PrimaryKey
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
    private int t;
    @SerializedName("sizeCache")
    private long u;
    @SerializedName("sizeApp")
    private long v;
    @SerializedName("sizeData")
    private long w;
    @SerializedName("position")
    private int x;
    @SerializedName("modeType")
    private String y;

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$MD5() {
        return this.o;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$appCategory() {
        return this.j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public long realmGet$appKey() {
        return this.r;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$appName() {
        return this.a;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$appType() {
        return this.c;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$channel() {
        return this.n;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$downloadUrl() {
        return this.k;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$hardware() {
        return this.m;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$hardwareVersion() {
        return this.s;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public boolean realmGet$hidden() {
        return this.p;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public int realmGet$iconResId() {
        return this.t;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$iconUrl() {
        return this.d;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$micoAction() {
        return this.l;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$modeType() {
        return this.y;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$onlineStatus() {
        return this.h;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$packageName() {
        return this.e;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$portion() {
        return this.q;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public int realmGet$position() {
        return this.x;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public boolean realmGet$removable() {
        return this.i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public long realmGet$sizeApp() {
        return this.v;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public long realmGet$sizeCache() {
        return this.u;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public long realmGet$sizeData() {
        return this.w;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$updateDate() {
        return this.b;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public long realmGet$versionCode() {
        return this.f;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public String realmGet$versionName() {
        return this.g;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$MD5(String str) {
        this.o = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$appCategory(String str) {
        this.j = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$appKey(long j) {
        this.r = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$appName(String str) {
        this.a = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$appType(String str) {
        this.c = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$channel(String str) {
        this.n = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$downloadUrl(String str) {
        this.k = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$hardware(String str) {
        this.m = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$hardwareVersion(String str) {
        this.s = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$hidden(boolean z) {
        this.p = z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$iconResId(int i) {
        this.t = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$iconUrl(String str) {
        this.d = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$micoAction(String str) {
        this.l = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$modeType(String str) {
        this.y = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$onlineStatus(String str) {
        this.h = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$packageName(String str) {
        this.e = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$portion(String str) {
        this.q = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$position(int i) {
        this.x = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$removable(boolean z) {
        this.i = z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$sizeApp(long j) {
        this.v = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$sizeCache(long j) {
        this.u = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$sizeData(long j) {
        this.w = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$updateDate(String str) {
        this.b = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$versionCode(long j) {
        this.f = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_cache_AppRealmObjectRealmProxyInterface
    public void realmSet$versionName(String str) {
        this.g = str;
    }

    public AppRealmObject() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public AppRealmObject(AppInfo appInfo, int i) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$appName(appInfo.getAppName());
        realmSet$updateDate(appInfo.getUpdateDate());
        realmSet$appType(appInfo.getAppType());
        realmSet$iconUrl(appInfo.getIconUrl());
        realmSet$packageName(appInfo.getPackageName());
        realmSet$versionCode(appInfo.getVersionCode());
        realmSet$versionName(appInfo.getVersionName());
        realmSet$onlineStatus(appInfo.getOnlineStatus());
        realmSet$removable(appInfo.isRemovable());
        realmSet$appCategory(appInfo.getAppCategory());
        realmSet$downloadUrl(appInfo.getDownloadUrl());
        realmSet$micoAction(appInfo.getMicoAction());
        realmSet$hardware(ContainerUtil.isEmpty(appInfo.getHardware()) ? "" : Gsons.getGson().toJson(appInfo.getHardware()));
        realmSet$channel(ContainerUtil.isEmpty(appInfo.getChannel()) ? "" : Gsons.getGson().toJson(appInfo.getChannel()));
        realmSet$MD5(appInfo.getMD5());
        realmSet$hidden(appInfo.isHidden());
        realmSet$portion(ContainerUtil.isEmpty(appInfo.getPortion()) ? "" : Gsons.getGson().toJson(appInfo.getPortion()));
        realmSet$appKey(appInfo.getAppKey());
        realmSet$hardwareVersion(appInfo.getHardwareVersion());
        realmSet$iconResId(appInfo.getIconResId());
        realmSet$sizeCache(appInfo.getSizeCache());
        realmSet$sizeApp(appInfo.getSizeApp());
        realmSet$sizeData(appInfo.getSizeData());
        realmSet$position(i + 1);
        realmSet$modeType(appInfo.getModeType());
    }

    public AppInfo convert2AppInfo() {
        AppInfo appInfo = new AppInfo(realmGet$iconUrl(), realmGet$appName(), realmGet$packageName(), realmGet$downloadUrl(), realmGet$micoAction(), realmGet$appType());
        appInfo.setRemovable(realmGet$removable());
        appInfo.setVersionCode(realmGet$versionCode());
        appInfo.setVersionName(realmGet$versionName());
        appInfo.setOnlineStatus(realmGet$onlineStatus());
        appInfo.setUpdateDate(realmGet$updateDate());
        appInfo.setAppCategory(realmGet$appCategory());
        appInfo.setHardware(realmGet$hardware());
        appInfo.setChannel(realmGet$channel());
        appInfo.setMD5(realmGet$MD5());
        appInfo.setHidden(realmGet$hidden());
        appInfo.setPortion(realmGet$portion());
        appInfo.setAppKey(realmGet$appKey());
        appInfo.setHardwareVersion(realmGet$hardwareVersion());
        appInfo.setIconResId(realmGet$iconResId());
        appInfo.setSizeCache(realmGet$sizeCache());
        appInfo.setSizeApp(realmGet$sizeApp());
        appInfo.setSizeData(realmGet$sizeData());
        appInfo.setModeType(realmGet$modeType());
        return appInfo;
    }

    public int getPosition() {
        return realmGet$position();
    }

    public void setPosition(int i) {
        realmSet$position(i);
    }

    public String getAppName() {
        return realmGet$appName();
    }

    public void setAppName(String str) {
        realmSet$appName(str);
    }

    public String getAppType() {
        return realmGet$appType();
    }

    public void setAppType(String str) {
        realmSet$appType(str);
    }

    public String getIconUrl() {
        return realmGet$iconUrl();
    }

    public void setIconUrl(String str) {
        realmSet$iconUrl(str);
    }

    public String getPackageName() {
        return realmGet$packageName();
    }

    public void setPackageName(String str) {
        realmSet$packageName(str);
    }

    public long getVersionCode() {
        return realmGet$versionCode();
    }

    public void setVersionCode(long j) {
        realmSet$versionCode(j);
    }

    public String getVersionName() {
        return realmGet$versionName();
    }

    public void setVersionName(String str) {
        realmSet$versionName(str);
    }

    public String getOnlineStatus() {
        return realmGet$onlineStatus();
    }

    public void setOnlineStatus(String str) {
        realmSet$onlineStatus(str);
    }

    public boolean isRemovable() {
        return realmGet$removable();
    }

    public void setRemovable(boolean z) {
        realmSet$removable(z);
    }

    public String getAppCategory() {
        return realmGet$appCategory();
    }

    public void setAppCategory(String str) {
        realmSet$appCategory(str);
    }

    public String getDownloadUrl() {
        return realmGet$downloadUrl();
    }

    public void setDownloadUrl(String str) {
        realmSet$downloadUrl(str);
    }

    public String getMicoAction() {
        return realmGet$micoAction();
    }

    public void setMicoAction(String str) {
        realmSet$micoAction(str);
    }

    public String getHardware() {
        return realmGet$hardware();
    }

    public void setHardware(String str) {
        realmSet$hardware(str);
    }

    public String getChannel() {
        return realmGet$channel();
    }

    public void setChannel(String str) {
        realmSet$channel(str);
    }

    public String getMD5() {
        return realmGet$MD5();
    }

    public void setMD5(String str) {
        realmSet$MD5(str);
    }

    public boolean isHidden() {
        return realmGet$hidden();
    }

    public void setHidden(boolean z) {
        realmSet$hidden(z);
    }

    public String getPortion() {
        return realmGet$portion();
    }

    public void setPortion(String str) {
        realmSet$portion(str);
    }

    public int getIconResId() {
        return realmGet$iconResId();
    }

    public void setIconResId(int i) {
        realmSet$iconResId(i);
    }

    public long getSizeCache() {
        return realmGet$sizeCache();
    }

    public void setSizeCache(long j) {
        realmSet$sizeCache(j);
    }

    public long getSizeApp() {
        return realmGet$sizeApp();
    }

    public void setSizeApp(long j) {
        realmSet$sizeApp(j);
    }

    public long getSizeData() {
        return realmGet$sizeData();
    }

    public void setSizeData(long j) {
        realmSet$sizeData(j);
    }

    public long getAppKey() {
        return realmGet$appKey();
    }

    public void setAppKey(int i) {
        realmSet$appKey(i);
    }
}
