package com.xiaomi.passport;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import com.xiaomi.accountsdk.request.IPUtilExternal;
import com.xiaomi.smarthome.library.common.network.Network;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class IPUtilExternalImpl implements IPUtilExternal {
    private static final String IP_LIST_SPLIT = ",";
    private static final String SP_KEY_PREFIX_BACKUP_IP_LIST = "backup_ip_list";
    private static final String SP_KEY_PREFIX_BACKUP_IP_LIST_EXPIRE = "backup_ip_list_expire";
    private static final String SP_KEY_PREFIX_BACKUP_IP_LIST_TIME = "backup_ip_list_timestamp";
    private static final String SP_KEY_PREFIX_CACHED_IP = "cached_ip";
    private static final String SP_KEY_PREFIX_CACHED_IP_EXPIRE = "cached_ip_expire";
    private static final String SP_KEY_PREFIX_CACHED_IP_TIME = "cached_ip_timestamp";
    private static final String SP_KEY_PREFIX_PING_THRESHOLD = "ping_threshold";
    private static final String SP_KEY_PREFIX_PING_TIME_COEFFICIENT = "ping_time_coefficient";
    private static final String SP_NAME = "ip";
    private final Context mContext;

    private static String getNetworkTypeName(int i) {
        switch (i) {
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "CDMA_EVDO_0";
            case 6:
                return "CDMA_EVDO_A";
            case 7:
                return "CDMA_1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "iDEN";
            case 12:
                return "CDMA_EVDO_B";
            case 13:
                return "LTE";
            case 14:
                return "CDMA_eHRPD";
            case 15:
                return "HSPAP";
            default:
                return "";
        }
    }

    public IPUtilExternalImpl(Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext();
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public String getNetworkName() {
        return getConnectionTypeString(this.mContext);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public boolean isWifi() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        return connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1;
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public void saveCachedIp(String str, String str2, String str3) {
        getIpSP().edit().putString(makeKey(SP_KEY_PREFIX_CACHED_IP, str, str2), str3).commit();
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public String loadCachedIp(String str, String str2, String str3) {
        return getIpSP().getString(makeKey(SP_KEY_PREFIX_CACHED_IP, str, str2), str3);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public void saveBackupIpList(String str, String str2, List<String> list) {
        getIpSP().edit().putString(makeKey(SP_KEY_PREFIX_BACKUP_IP_LIST, str, str2), ipListToString(list)).commit();
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public List<String> loadBackupIpList(String str, String str2, List<String> list) {
        List<String> stringToIpList = stringToIpList(getIpSP().getString(makeKey(SP_KEY_PREFIX_BACKUP_IP_LIST, str, str2), null));
        return stringToIpList != null ? stringToIpList : list;
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public void saveCachedTimeStamp(String str, String str2, long j) {
        saveSPLong(makeKey(SP_KEY_PREFIX_CACHED_IP_TIME, str, str2), j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public long loadCachedTimeStamp(String str, String str2, long j) {
        return loadSPLong(makeKey(SP_KEY_PREFIX_CACHED_IP_TIME, str, str2), j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public void saveBackupTimeStamp(String str, String str2, long j) {
        saveSPLong(makeKey(SP_KEY_PREFIX_BACKUP_IP_LIST_TIME, str, str2), j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public long loadBackupTimeStamp(String str, String str2, long j) {
        return loadSPLong(makeKey(SP_KEY_PREFIX_BACKUP_IP_LIST_TIME, str, str2), j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public void saveCachedIpExpireDuration(long j) {
        saveSPLong(SP_KEY_PREFIX_CACHED_IP_EXPIRE, j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public long loadCachedIpExpireDuration(long j) {
        return loadSPLong(SP_KEY_PREFIX_CACHED_IP_EXPIRE, j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public void saveBackupIpListExpireDuration(long j) {
        saveSPLong(SP_KEY_PREFIX_BACKUP_IP_LIST_EXPIRE, j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public long loadBackupIpListExpireDuration(long j) {
        return loadSPLong(SP_KEY_PREFIX_BACKUP_IP_LIST_EXPIRE, j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public void savePingThreshold(long j) {
        saveSPLong(SP_KEY_PREFIX_PING_THRESHOLD, j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public long loadPingThreshold(long j) {
        return loadSPLong(SP_KEY_PREFIX_PING_THRESHOLD, j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public void savePingTimeCoefficient(long j) {
        saveSPLong(SP_KEY_PREFIX_PING_TIME_COEFFICIENT, j);
    }

    @Override // com.xiaomi.accountsdk.request.IPUtilExternal
    public long loadPingTimeCoefficient(long j) {
        return loadSPLong(SP_KEY_PREFIX_PING_TIME_COEFFICIENT, j);
    }

    private static String getConnectionTypeString(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        String str = null;
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return null;
        }
        int type = activeNetworkInfo.getType();
        if (type == 1) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
            if (wifiManager.getConnectionInfo() != null) {
                str = wifiManager.getConnectionInfo().getSSID();
            }
            if (str != null && str.matches("\".*\"")) {
                str = str.substring(1, str.length() - 1);
            }
            return "W_" + str;
        } else if (type != 0) {
            return null;
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return String.format("M_%s_%s", telephonyManager.getNetworkOperator(), getNetworkTypeName(telephonyManager.getNetworkType()));
        }
    }

    private String ipListToString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(str);
        }
        return sb.toString();
    }

    private List<String> stringToIpList(String str) {
        if (str == null) {
            return null;
        }
        return Arrays.asList(str.split(","));
    }

    private void saveSPLong(String str, long j) {
        getIpSP().edit().putLong(str, j).commit();
    }

    private long loadSPLong(String str, long j) {
        return getIpSP().getLong(str, j);
    }

    SharedPreferences getIpSP() {
        return this.mContext.getSharedPreferences(SP_NAME, 0);
    }

    private String makeKey(String str, String str2, String str3) {
        return String.format("%s#%s#%s", str, str2, str3);
    }
}
