package com.xiaomi.micolauncher.common.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemProperties;
import androidx.annotation.Keep;
import com.xiaomi.mico.base.utils.DeviceUuid;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.build.BuildSettings;
import com.xiaomi.micolauncher.common.setting.SystemSetting;

@Keep
/* loaded from: classes3.dex */
public class DeviceInfo {
    public String androidCodeName;
    public int androidSdkLevel;
    public String androidVersion;
    public String appChannel;
    public int appVersionCode;
    public String appVersionName;
    public String board;
    public String bootloader;
    public String brand;
    public String device;
    public String display;
    public String fingerprint;
    public String hardware;
    public String hardwareId;
    public String manufacturer;
    private String miotDid;
    private String miotToken;
    public String model;
    public int networkType;
    public String operator;
    public String operatorName;
    public int phoneType;
    public String platform;
    public String product;
    private String publicSourceDir;
    private String sn;
    public String softwareId;
    private String sourceDir;
    public String version;

    public static DeviceInfo get(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.platform = "android";
        deviceInfo.hardwareId = DeviceUuid.getHardwareId(context);
        deviceInfo.softwareId = DeviceUuid.getSoftUuid(context);
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            deviceInfo.appVersionName = packageInfo.versionName;
            deviceInfo.version = SystemProperties.get("ro.mi.sw_ver");
            deviceInfo.appVersionCode = packageInfo.versionCode;
            deviceInfo.appChannel = BuildSettings.getReleaseChannel(context);
            deviceInfo.publicSourceDir = packageInfo.applicationInfo.publicSourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        deviceInfo.androidVersion = Build.VERSION.RELEASE;
        deviceInfo.androidSdkLevel = Build.VERSION.SDK_INT;
        deviceInfo.androidCodeName = Build.VERSION.CODENAME;
        deviceInfo.manufacturer = Build.MANUFACTURER;
        deviceInfo.model = Hardware.getBuildModel();
        deviceInfo.product = Build.PRODUCT;
        deviceInfo.board = Build.BOARD;
        deviceInfo.bootloader = Build.BOOTLOADER;
        deviceInfo.brand = Build.BRAND;
        deviceInfo.display = Build.DISPLAY;
        deviceInfo.fingerprint = Build.FINGERPRINT;
        deviceInfo.hardware = Build.HARDWARE;
        deviceInfo.sn = Constants.getSn();
        deviceInfo.miotDid = SystemSetting.getMiotDeviceId();
        deviceInfo.miotToken = SystemSetting.getMiotToken();
        return deviceInfo;
    }
}
