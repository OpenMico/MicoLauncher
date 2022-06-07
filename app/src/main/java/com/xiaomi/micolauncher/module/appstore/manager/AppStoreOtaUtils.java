package com.xiaomi.micolauncher.module.appstore.manager;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.FileSizeUtil;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreOtaDebugInfo;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreOtaResult;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreOtaStatistic;
import com.xiaomi.miot.host.manager.MiotHostManager;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;
import com.xiaomi.miot.typedef.listener.GatewayMessageListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AppStoreOtaUtils {
    public static final String MESH_GATEWAY_APP = "com.xiaomi.mesh.gateway";
    public static final int MESH_GATEWAY_SIGN_IN_UPDATE_TYPE = 0;
    public static final int MESH_GATEWAY_SILIENT_UPDATE_TYPE = 1;
    public static final int OTA_UPDATE_ERROR_CODE_INSTALL_FAILED = -33027;
    public static final int OTA_UPDATE_ERROR_CODE_INVALID_URL = -33026;
    public static final int OTA_UPDATE_ERROR_CODE_NOT_ENOUGH_SPACE = -33101;
    public static final int OTA_UPDATE_ERROR_CODE_VERIFY_FAIL = -33025;
    public static final int OTA_UPDATE_SUCCESS = 0;
    private static final long a = TimeUnit.SECONDS.toMillis(1);
    private static String b = "";
    private static AppStoreOtaUtils c = new AppStoreOtaUtils();

    private AppStoreOtaUtils() {
    }

    public static AppStoreOtaUtils getInstance() {
        AppStoreOtaUtils appStoreOtaUtils;
        synchronized (AppStoreOtaUtils.class) {
            appStoreOtaUtils = c;
        }
        return appStoreOtaUtils;
    }

    public void setCurrentVer(String str) {
        b = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int reportMeshGatewayOtaResult(final Context context, int i, boolean z, int i2, int i3, String str) {
        AppStoreOtaResult.OtaInfo otaInfo = new AppStoreOtaResult.OtaInfo();
        otaInfo.setCurVersion(b);
        otaInfo.setOtaVersion(str);
        otaInfo.setReason(i2);
        ArrayList arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add(Integer.valueOf(z ? 1 : 0));
        otaInfo.setResult(arrayList);
        AppStoreOtaResult.OtaResult otaResult = new AppStoreOtaResult.OtaResult();
        otaResult.setType(i);
        otaResult.setMeshGateway(otaInfo);
        otaResult.setResult(z);
        otaResult.setCosttime(i3);
        AppStoreOtaResult.OtaResultSummay otaResultSummay = new AppStoreOtaResult.OtaResultSummay();
        otaResultSummay.setParams(otaResult);
        String str2 = SystemProperties.get("ro.product.model");
        otaResultSummay.setModel("xiaomi.wifispeaker." + str2);
        String str3 = SystemProperties.get("ro.mi.sw_ver");
        otaResultSummay.setFirmwareVer(str3);
        otaResultSummay.setMcuFirmwareVer(str3);
        AppStoreOtaResult appStoreOtaResult = new AppStoreOtaResult();
        appStoreOtaResult.setId(generateSessionId());
        appStoreOtaResult.setMethod("_async.stat");
        appStoreOtaResult.setParams(otaResultSummay);
        String json = Gsons.getGson().toJson(appStoreOtaResult);
        Logger logger = L.storage;
        logger.d("appStoreOtaResult : " + json);
        b = getVersionName(context, "com.xiaomi.mesh.gateway");
        try {
            if (MiotHostManager.getInstance().isMiotConnected()) {
                MiotHostManager.getInstance().sendGatewayMessage(json, new GatewayMessageListener() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreOtaUtils.1
                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                    public void onSucceed(String str4) {
                        L.storage.d("send ota Message success");
                    }

                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                    public void onFailed(MiotError miotError) {
                        if (miotError.getCode() == MiotError.INTERNAL.getCode() || miotError.getCode() == MiotError.INTERNAL_NOT_INITIALIZED.getCode() || miotError.getCode() == MiotError.INTERNAL_OT_SERVICE_NOT_START.getCode() || miotError.getCode() == MiotError.INTERNAL_NO_CONNECTION_ESTABLISHED.getCode() || miotError.getCode() == MiotError.INTERNAL_MIOT_SERVICE_DISCONNECTED.getCode() || miotError.getCode() == MiotError.INTERNAL_INVALID_ARGS.getCode() || miotError.getCode() == MiotError.INTERNAL_REQUEST_TIMEOUT.getCode()) {
                            Logger logger2 = L.storage;
                            logger2.e("send ota Message error, code = " + miotError.getCode());
                        }
                    }
                });
            } else {
                L.storage.e("send ota Message error, MiotHost is disconnect");
            }
        } catch (MiotException e) {
            e.printStackTrace();
        }
        if (i2 == 0) {
            return 0;
        }
        Threads.getLightWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.appstore.manager.-$$Lambda$AppStoreOtaUtils$dKwsLaB9ATKR46yrp7b10zPDMYU
            @Override // java.lang.Runnable
            public final void run() {
                AppStoreOtaUtils.this.b(context);
            }
        }, a);
        return 0;
    }

    private int a(Context context) {
        ArrayList arrayList = new ArrayList();
        List<String> installedAppList = getInstalledAppList(context);
        AppStoreOtaDebugInfo.MemoryInfo memoryInfo = new AppStoreOtaDebugInfo.MemoryInfo();
        memoryInfo.setFreeSpace(FileSizeUtil.getStorageFreeSizeSync(context));
        memoryInfo.setPkgInfoListSize(installedAppList.size());
        int i = 2;
        for (String str : installedAppList) {
            AppStoreOtaDebugInfo.pkgInfo pkginfo = new AppStoreOtaDebugInfo.pkgInfo();
            StorageStats packageStats = getPackageStats(context, str);
            pkginfo.setPkg(str);
            pkginfo.setCacheStats(packageStats.getCacheBytes());
            pkginfo.setDataStats(packageStats.getDataBytes());
            arrayList.add(pkginfo);
            i--;
            if (i <= 0) {
                break;
            }
        }
        memoryInfo.setPkgInfoList(arrayList);
        AppStoreOtaDebugInfo appStoreOtaDebugInfo = new AppStoreOtaDebugInfo();
        appStoreOtaDebugInfo.setId(generateSessionId());
        appStoreOtaDebugInfo.setMethod("_async.stat");
        appStoreOtaDebugInfo.setParams(memoryInfo);
        String json = Gsons.getGson().toJson(appStoreOtaDebugInfo);
        L.storage.d("appStoreOtaDebugInfo : " + json);
        if (json.length() >= 1024) {
            return 0;
        }
        try {
            if (MiotHostManager.getInstance().isMiotConnected()) {
                MiotHostManager.getInstance().sendGatewayMessage(json, new GatewayMessageListener() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreOtaUtils.2
                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                    public void onSucceed(String str2) {
                        L.storage.d("send ota Message success");
                    }

                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                    public void onFailed(MiotError miotError) {
                        if (miotError.getCode() == MiotError.INTERNAL.getCode() || miotError.getCode() == MiotError.INTERNAL_NOT_INITIALIZED.getCode() || miotError.getCode() == MiotError.INTERNAL_OT_SERVICE_NOT_START.getCode() || miotError.getCode() == MiotError.INTERNAL_NO_CONNECTION_ESTABLISHED.getCode() || miotError.getCode() == MiotError.INTERNAL_MIOT_SERVICE_DISCONNECTED.getCode() || miotError.getCode() == MiotError.INTERNAL_INVALID_ARGS.getCode() || miotError.getCode() == MiotError.INTERNAL_REQUEST_TIMEOUT.getCode()) {
                            Logger logger = L.storage;
                            logger.e("send ota Message error, code = " + miotError.getCode());
                        }
                    }
                });
            } else {
                L.storage.e("send ota Message error, MiotHost is disconnect");
            }
        } catch (MiotException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* renamed from: reportOtaDebugInfoMessage */
    public int b(Context context) {
        ArrayList arrayList = new ArrayList();
        List<String> installedAppList = getInstalledAppList(context);
        AppStoreOtaDebugInfo.MemoryInfo memoryInfo = new AppStoreOtaDebugInfo.MemoryInfo();
        memoryInfo.setFreeSpace(FileSizeUtil.getStorageFreeSizeSync(context));
        memoryInfo.setPkgInfoListSize(installedAppList.size());
        int i = 10;
        for (String str : installedAppList) {
            AppStoreOtaDebugInfo.pkgInfo pkginfo = new AppStoreOtaDebugInfo.pkgInfo();
            StorageStats packageStats = getPackageStats(context, str);
            pkginfo.setPkg(str);
            pkginfo.setCacheStats(packageStats.getCacheBytes());
            pkginfo.setDataStats(packageStats.getDataBytes());
            arrayList.add(pkginfo);
            i--;
            if (i <= 0) {
                break;
            }
        }
        memoryInfo.setPkgInfoList(arrayList);
        AppStoreOtaDebugInfo appStoreOtaDebugInfo = new AppStoreOtaDebugInfo();
        appStoreOtaDebugInfo.setId(generateSessionId());
        appStoreOtaDebugInfo.setMethod("_async.stat");
        appStoreOtaDebugInfo.setParams(memoryInfo);
        String json = Gsons.getGson().toJson(appStoreOtaDebugInfo);
        L.storage.d("appStoreOtaDebugInfo : " + json);
        if (json.length() >= 1024) {
            a(context);
            return 0;
        }
        try {
            if (MiotHostManager.getInstance().isMiotConnected()) {
                MiotHostManager.getInstance().sendGatewayMessage(json, new GatewayMessageListener() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreOtaUtils.3
                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                    public void onSucceed(String str2) {
                        L.storage.d("send ota Message success");
                    }

                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                    public void onFailed(MiotError miotError) {
                        if (miotError.getCode() == MiotError.INTERNAL.getCode() || miotError.getCode() == MiotError.INTERNAL_NOT_INITIALIZED.getCode() || miotError.getCode() == MiotError.INTERNAL_OT_SERVICE_NOT_START.getCode() || miotError.getCode() == MiotError.INTERNAL_NO_CONNECTION_ESTABLISHED.getCode() || miotError.getCode() == MiotError.INTERNAL_MIOT_SERVICE_DISCONNECTED.getCode() || miotError.getCode() == MiotError.INTERNAL_INVALID_ARGS.getCode() || miotError.getCode() == MiotError.INTERNAL_REQUEST_TIMEOUT.getCode()) {
                            Logger logger = L.storage;
                            logger.e("send ota Message error, code = " + miotError.getCode());
                        }
                    }
                });
            } else {
                L.storage.e("send ota Message error, MiotHost is disconnect");
            }
        } catch (MiotException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int reportMeshGatewayOtaStatistic(Context context, String str) {
        AppStoreOtaStatistic.OtaInfo otaInfo = new AppStoreOtaStatistic.OtaInfo();
        b = getVersionName(context, "com.xiaomi.mesh.gateway");
        otaInfo.setCurVersion(b);
        otaInfo.setOtaVersion(str);
        AppStoreOtaStatistic.OtaStatistic otaStatistic = new AppStoreOtaStatistic.OtaStatistic();
        otaStatistic.setMeshGateway(otaInfo);
        AppStoreOtaStatistic.OtaStatisticSummay otaStatisticSummay = new AppStoreOtaStatistic.OtaStatisticSummay();
        otaStatisticSummay.setParams(otaStatistic);
        String str2 = SystemProperties.get("ro.product.model");
        otaStatisticSummay.setModel("xiaomi.wifispeaker." + str2);
        String str3 = SystemProperties.get("ro.mi.sw_ver");
        otaStatisticSummay.setFirmwareVer(str3);
        otaStatisticSummay.setMcuFirmwareVer(str3);
        otaStatisticSummay.setDeviceSn(SystemProperties.get("ro.mi.device.id"));
        AppStoreOtaStatistic appStoreOtaStatistic = new AppStoreOtaStatistic();
        appStoreOtaStatistic.setId(generateSessionId());
        appStoreOtaStatistic.setMethod("_async.stat");
        appStoreOtaStatistic.setParams(otaStatisticSummay);
        String json = Gsons.getGson().toJson(appStoreOtaStatistic);
        Logger logger = L.storage;
        logger.d("appStoreOtaStatistic : " + json);
        try {
            if (MiotHostManager.getInstance().isMiotConnected()) {
                MiotHostManager.getInstance().sendGatewayMessage(json, new GatewayMessageListener() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreOtaUtils.4
                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                    public void onSucceed(String str4) {
                        L.storage.d("send ota Message success");
                    }

                    @Override // com.xiaomi.miot.typedef.listener.GatewayMessageListener
                    public void onFailed(MiotError miotError) {
                        if (miotError.getCode() == MiotError.INTERNAL.getCode() || miotError.getCode() == MiotError.INTERNAL_NOT_INITIALIZED.getCode() || miotError.getCode() == MiotError.INTERNAL_OT_SERVICE_NOT_START.getCode() || miotError.getCode() == MiotError.INTERNAL_NO_CONNECTION_ESTABLISHED.getCode() || miotError.getCode() == MiotError.INTERNAL_MIOT_SERVICE_DISCONNECTED.getCode() || miotError.getCode() == MiotError.INTERNAL_INVALID_ARGS.getCode() || miotError.getCode() == MiotError.INTERNAL_REQUEST_TIMEOUT.getCode()) {
                            Logger logger2 = L.storage;
                            logger2.e("send ota Message error, code = " + miotError.getCode());
                        }
                    }
                });
            } else {
                L.storage.e("send ota Message error, MiotHost is disconnect");
            }
            return 0;
        } catch (MiotException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int generateSessionId() {
        return (int) (((Math.random() * 9.0d) + 1.0d) * 100000.0d);
    }

    public String getVersionName(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return "unknow";
        }
        try {
            return packageManager.getPackageInfo(str, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "unknow";
        }
    }

    public StorageStats getPackageStats(Context context, String str) {
        try {
            return ((StorageStatsManager) context.getSystemService("storagestats")).queryStatsForPackage(StorageManager.UUID_DEFAULT, str, UserHandle.getUserHandleForUid(-2));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        } catch (SecurityException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public List<String> getInstalledAppList(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            for (PackageInfo packageInfo : context.getPackageManager().getInstalledPackages(5)) {
                String str = packageInfo.packageName;
                if (!a(packageInfo)) {
                    arrayList.add(str);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        Logger logger = L.mesh;
        logger.d("" + arrayList.toString());
        return arrayList;
    }

    private boolean a(PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & 1) == 1) || ((packageInfo.applicationInfo.flags & 128) == 1);
    }
}
