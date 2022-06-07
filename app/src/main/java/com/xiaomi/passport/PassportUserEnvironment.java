package com.xiaomi.passport;

import android.annotation.SuppressLint;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.hasheddeviceidlib.HardwareInfo;
import com.xiaomi.accountsdk.hasheddeviceidlib.MacAddressUtil;
import com.xiaomi.accountsdk.hasheddeviceidlib.PlainDeviceIdUtil;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.CloudCoder;
import com.xiaomi.smarthome.library.common.network.Network;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public class PassportUserEnvironment {
    private static final String DELIMITER = "#";
    private static final int ENV_FACTOR_LENGTH = 6;
    private static final int NETWORK_TYPE_NONE = -1;
    private static final String TAG = "PassportUserEnvironment";
    private static final String UTF8 = "utf-8";

    protected List<String> getPhoneNumbers(Application application) {
        return null;
    }

    public List<String> getSimId() {
        return null;
    }

    /* loaded from: classes4.dex */
    public static class Holder {
        private static final PassportUserEnvironment sDefaultInstance = new PassportUserEnvironment();
        private static PassportUserEnvironment sInstance = sDefaultInstance;

        public static void setInstance(PassportUserEnvironment passportUserEnvironment) {
            if (passportUserEnvironment != null) {
                sInstance = passportUserEnvironment;
                return;
            }
            throw new IllegalArgumentException("PassportUserEnvironment instance cannot be null!");
        }

        public static PassportUserEnvironment getInstance() {
            return sInstance;
        }

        public static PassportUserEnvironment getDefaultInstance() {
            return sDefaultInstance;
        }
    }

    /* loaded from: classes4.dex */
    public enum TelePhonyInfo {
        SUBSCRIBE_ID("getSubscriberIdForSlot"),
        SERIAL_NUMBER("getSimSerialNumberForSlot"),
        OPERATOR("getSimOperatorForSlot"),
        DEVICE_ID_LIST("getDeviceIdList");
        
        String methodToGetValue;

        TelePhonyInfo(String str) {
            this.methodToGetValue = str;
        }
    }

    @Deprecated
    public synchronized String getPlainDeviceId() throws SecurityException {
        return new PlainDeviceIdUtil.PlainDeviceIdUtilImplDefault().getPlainDeviceId(XMPassportSettings.getApplicationContext());
    }

    @Deprecated
    public String getMacAddress() {
        return MacAddressUtil.getMacAddress(XMPassportSettings.getApplicationContext());
    }

    public String getSSID() {
        Application applicationContext = XMPassportSettings.getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        try {
            WifiManager wifiManager = (WifiManager) applicationContext.getSystemService(Network.NETWORK_TYPE_WIFI);
            if (wifiManager.getConnectionInfo() != null) {
                return wifiManager.getConnectionInfo().getSSID();
            }
            return null;
        } catch (SecurityException e) {
            AccountLog.i(TAG, "failed to get SSID with SecurityException " + e.getMessage());
            return null;
        }
    }

    public List<String> getConfiguredSSIDs() {
        Application applicationContext = XMPassportSettings.getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        try {
            List<WifiConfiguration> configuredNetworks = ((WifiManager) applicationContext.getSystemService(Network.NETWORK_TYPE_WIFI)).getConfiguredNetworks();
            if (configuredNetworks == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList(configuredNetworks.size());
            for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                arrayList.add(wifiConfiguration.SSID);
            }
            return arrayList;
        } catch (SecurityException e) {
            AccountLog.i(TAG, "failed to get configuredSSIDs with SecurityException " + e.getMessage());
            return null;
        }
    }

    public List<String> getConfiguredSSIDLimit(int i) {
        List<String> configuredSSIDs = getConfiguredSSIDs();
        return (configuredSSIDs == null || configuredSSIDs.size() <= i) ? configuredSSIDs : configuredSSIDs.subList(0, i);
    }

    @SuppressLint({"MissingPermission"})
    @Deprecated
    public String getBluetoothId() {
        try {
            if (BluetoothAdapter.getDefaultAdapter() != null) {
                return BluetoothAdapter.getDefaultAdapter().getAddress();
            }
            return null;
        } catch (SecurityException e) {
            AccountLog.i(TAG, "failed to get bluetooth id with SecurityException " + e.getMessage());
            return null;
        }
    }

    @Deprecated
    public String getNetworkOperator() {
        Application applicationContext = XMPassportSettings.getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        try {
            return ((TelephonyManager) applicationContext.getSystemService("phone")).getNetworkOperator();
        } catch (SecurityException e) {
            AccountLog.i(TAG, "failed to get network operator with SecurityException " + e.getMessage());
            return null;
        }
    }

    private String getBSSID(Application application) {
        if (application == null) {
            return null;
        }
        try {
            WifiInfo connectionInfo = ((WifiManager) application.getSystemService(Network.NETWORK_TYPE_WIFI)).getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getBSSID();
            }
        } catch (SecurityException e) {
            AccountLog.i(TAG, "failed to get BSSID with SecurityException " + e.getMessage());
        }
        return null;
    }

    private String getWifiMacAddress(Application application) {
        return HardwareInfo.getWifiMacAddress(application);
    }

    private String getBluetoothMacAddress(Application application) {
        return HardwareInfo.getBluetoothMacAddress(application);
    }

    @SuppressLint({"MissingPermission"})
    private int getNetWorkType(Application application) {
        if (application == null) {
            return -1;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) application.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return activeNetworkInfo.getType();
            }
        } catch (SecurityException e) {
            AccountLog.i(TAG, "failed to getNetWorkType with SecurityException " + e.getMessage());
        }
        return -1;
    }

    private String getAndroidId(Application application) {
        if (application == null) {
            return null;
        }
        return Settings.Secure.getString(application.getContentResolver(), "android_id");
    }

    protected String getUserSetDeviceName(Application application) {
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                return defaultAdapter.getName();
            }
            return null;
        } catch (SecurityException e) {
            AccountLog.i(TAG, "failed to get bluetooth id with SecurityException " + e.getMessage());
            return null;
        }
    }

    protected List<String> getSubscriberIds(Application application) {
        return getTelePhonyInfoFromNoMiui(application, TelePhonyInfo.SUBSCRIBE_ID);
    }

    protected List<String> getSimSerialNumbers(Application application) {
        return getTelePhonyInfoFromNoMiui(application, TelePhonyInfo.SERIAL_NUMBER);
    }

    protected List<String> getSimOperators(Application application) {
        return getTelePhonyInfoFromNoMiui(application, TelePhonyInfo.OPERATOR);
    }

    protected List<String> getDeviceIdList(Application application) {
        return getTelePhonyInfoFromNoMiui(application, TelePhonyInfo.DEVICE_ID_LIST);
    }

    @SuppressLint({"MissingPermission"})
    private List<String> getTelePhonyInfoFromNoMiui(Application application, TelePhonyInfo telePhonyInfo) {
        if (application == null) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) application.getSystemService("phone");
            ArrayList arrayList = new ArrayList();
            switch (telePhonyInfo) {
                case OPERATOR:
                    arrayList.add(telephonyManager.getSimOperator());
                    break;
                case SERIAL_NUMBER:
                    arrayList.add(telephonyManager.getSimSerialNumber());
                    break;
                case SUBSCRIBE_ID:
                    arrayList.add(telephonyManager.getSubscriberId());
                    break;
                case DEVICE_ID_LIST:
                    arrayList.add(telephonyManager.getDeviceId());
                    break;
                default:
                    throw new IllegalStateException("not here");
            }
            if (arrayList.size() > 0) {
                return arrayList;
            }
            return null;
        } catch (SecurityException e) {
            AccountLog.i(TAG, "failed to get SubscriberId with SecurityException " + e.getMessage());
            return null;
        }
    }

    private LinkedList<Object> getAllLinkedEnvInfos(Application application) {
        String hash = hash(getSSID());
        String hash2 = hash(getBSSID(application));
        List<String> hash3 = hash(getConfiguredSSIDLimit(50));
        String base64 = base64(String.valueOf(getNetWorkType(application)));
        String base642 = base64(Build.MODEL);
        String base643 = base64(Build.SERIAL);
        String hash4 = hash(getAndroidId(application));
        List<String> hash5 = hash(getDeviceIdList(application));
        String hash6 = hash(getBluetoothMacAddress(application));
        String hash7 = hash(getWifiMacAddress(application));
        List<String> base644 = base64(getSubscriberIds(application));
        List<String> base645 = base64(getSimSerialNumbers(application));
        List<String> base646 = base64(getPhoneNumbers(application));
        List<String> base647 = base64(getSimOperators(application));
        List<String> hash8 = hash(getSimId());
        String base648 = base64(getUserSetDeviceName(application));
        LinkedList<Object> linkedList = new LinkedList<>();
        linkedList.add(hash);
        linkedList.add(hash2);
        linkedList.add(hash3);
        linkedList.add(base64);
        linkedList.add(base642);
        linkedList.add(base643);
        linkedList.add(hash4);
        linkedList.add(hash5);
        linkedList.add(hash6);
        linkedList.add(hash7);
        linkedList.add(base644);
        linkedList.add(base645);
        linkedList.add(base646);
        linkedList.add(base647);
        linkedList.add(hash8);
        linkedList.add(base648);
        return linkedList;
    }

    public String[] getEnvInfoArray(Application application) {
        String str;
        LinkedList<Object> allLinkedEnvInfos = getAllLinkedEnvInfos(application);
        ArrayList arrayList = new ArrayList();
        Iterator<Object> it = allLinkedEnvInfos.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next == null) {
                str = "";
            } else if (next instanceof List) {
                str = joinEnvParam((List) next);
            } else if (next instanceof String) {
                str = (String) next;
            } else {
                throw new IllegalStateException("not here");
            }
            arrayList.add(str);
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private static List<String> blurLocationInfo(double d, double d2) {
        long round = Math.round(d * 10.0d) * 10;
        long round2 = Math.round(d2 * 10.0d) * 10;
        ArrayList arrayList = new ArrayList(8);
        long j = round - 10;
        arrayList.add(String.valueOf(j));
        long j2 = round2 - 10;
        arrayList.add(String.valueOf(j2));
        arrayList.add(String.valueOf(j));
        arrayList.add(String.valueOf(round2));
        arrayList.add(String.valueOf(round));
        arrayList.add(String.valueOf(round2));
        arrayList.add(String.valueOf(round));
        arrayList.add(String.valueOf(j2));
        return arrayList;
    }

    public static String hashEnvParamString(ArrayList<String> arrayList) {
        return (arrayList == null || arrayList.size() == 0) ? "" : CloudCoder.hashAndJoin(DELIMITER, arrayList, 6);
    }

    private static String joinEnvParam(List list) {
        return (list == null || list.size() == 0) ? "" : TextUtils.join(DELIMITER, list);
    }

    private List<String> hash(List<String> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            arrayList.add(hash(str));
        }
        return arrayList;
    }

    private String hash(String str) {
        return hash(str, 6);
    }

    private String hash(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String hash4SHA1 = CloudCoder.hash4SHA1(str);
        return (i <= 0 || i > hash4SHA1.length()) ? hash4SHA1 : hash4SHA1.substring(0, i);
    }

    private List<String> base64(List<String> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            arrayList.add(base64(str));
        }
        return arrayList;
    }

    private String base64(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Base64.encodeToString(str.getBytes("utf-8"), 10);
        } catch (UnsupportedEncodingException e) {
            AccountLog.e(TAG, "base64 failed: ", e);
            return null;
        }
    }
}
