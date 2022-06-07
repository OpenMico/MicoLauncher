package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;

/* loaded from: classes.dex */
public final class PhoneUtils {
    private PhoneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isPhone() {
        return a().getPhoneType() != 0;
    }

    @RequiresPermission("android.permission.READ_PHONE_STATE")
    @SuppressLint({"HardwareIds"})
    public static String getDeviceId() {
        if (Build.VERSION.SDK_INT >= 29) {
            return "";
        }
        TelephonyManager a = a();
        String deviceId = a.getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        if (Build.VERSION.SDK_INT < 26) {
            return "";
        }
        String imei = a.getImei();
        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        String meid = a.getMeid();
        return TextUtils.isEmpty(meid) ? "" : meid;
    }

    @RequiresPermission("android.permission.READ_PHONE_STATE")
    @SuppressLint({"HardwareIds"})
    public static String getSerial() {
        if (Build.VERSION.SDK_INT < 29) {
            return Build.VERSION.SDK_INT >= 26 ? Build.getSerial() : Build.SERIAL;
        }
        try {
            return Build.getSerial();
        } catch (SecurityException e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequiresPermission("android.permission.READ_PHONE_STATE")
    public static String getIMEI() {
        return getImeiOrMeid(true);
    }

    @RequiresPermission("android.permission.READ_PHONE_STATE")
    public static String getMEID() {
        return getImeiOrMeid(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b1  */
    @androidx.annotation.RequiresPermission("android.permission.READ_PHONE_STATE")
    @android.annotation.SuppressLint({"HardwareIds"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getImeiOrMeid(boolean r12) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.PhoneUtils.getImeiOrMeid(boolean):java.lang.String");
    }

    private static String a(String str, String str2) {
        boolean isEmpty = TextUtils.isEmpty(str);
        boolean isEmpty2 = TextUtils.isEmpty(str2);
        return (!isEmpty || !isEmpty2) ? (isEmpty || isEmpty2) ? !isEmpty ? str : str2 : str.compareTo(str2) <= 0 ? str : str2 : "";
    }

    private static String a(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(BluetoothConstants.GET, String.class, String.class).invoke(cls, str, "");
        } catch (Exception unused) {
            return "";
        }
    }

    @RequiresPermission("android.permission.READ_PHONE_STATE")
    @SuppressLint({"HardwareIds"})
    public static String getIMSI() {
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                a().getSubscriberId();
            } catch (SecurityException e) {
                e.printStackTrace();
                return "";
            }
        }
        return a().getSubscriberId();
    }

    public static int getPhoneType() {
        return a().getPhoneType();
    }

    public static boolean isSimCardReady() {
        return a().getSimState() == 5;
    }

    public static String getSimOperatorName() {
        return a().getSimOperatorName();
    }

    public static String getSimOperatorByMnc() {
        String simOperator = a().getSimOperator();
        if (simOperator == null) {
            return "";
        }
        char c = 65535;
        int hashCode = simOperator.hashCode();
        if (hashCode != 49679479) {
            if (hashCode != 49679502) {
                if (hashCode != 49679532) {
                    switch (hashCode) {
                        case 49679470:
                            if (simOperator.equals("46000")) {
                                c = 0;
                                break;
                            }
                            break;
                        case 49679471:
                            if (simOperator.equals("46001")) {
                                c = 4;
                                break;
                            }
                            break;
                        case 49679472:
                            if (simOperator.equals("46002")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 49679473:
                            if (simOperator.equals("46003")) {
                                c = 7;
                                break;
                            }
                            break;
                        default:
                            switch (hashCode) {
                                case 49679475:
                                    if (simOperator.equals("46005")) {
                                        c = '\b';
                                        break;
                                    }
                                    break;
                                case 49679476:
                                    if (simOperator.equals("46006")) {
                                        c = 5;
                                        break;
                                    }
                                    break;
                                case 49679477:
                                    if (simOperator.equals("46007")) {
                                        c = 2;
                                        break;
                                    }
                                    break;
                            }
                    }
                } else if (simOperator.equals("46020")) {
                    c = 3;
                }
            } else if (simOperator.equals("46011")) {
                c = '\t';
            }
        } else if (simOperator.equals("46009")) {
            c = 6;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
                return "中国移动";
            case 4:
            case 5:
            case 6:
                return "中国联通";
            case 7:
            case '\b':
            case '\t':
                return "中国电信";
            default:
                return simOperator;
        }
    }

    public static void dial(@NonNull String str) {
        if (str != null) {
            Utils.getApp().startActivity(b.k(str));
            return;
        }
        throw new NullPointerException("Argument 'phoneNumber' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @RequiresPermission("android.permission.CALL_PHONE")
    public static void call(@NonNull String str) {
        if (str != null) {
            Utils.getApp().startActivity(b.l(str));
            return;
        }
        throw new NullPointerException("Argument 'phoneNumber' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void sendSms(@NonNull String str, String str2) {
        if (str != null) {
            Utils.getApp().startActivity(b.a(str, str2));
            return;
        }
        throw new NullPointerException("Argument 'phoneNumber' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static TelephonyManager a() {
        return (TelephonyManager) Utils.getApp().getSystemService("phone");
    }
}
