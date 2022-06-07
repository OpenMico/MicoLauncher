package com.mi.milink.mediacore;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import androidx.annotation.NonNull;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import java.util.Objects;

/* compiled from: Utils.java */
/* loaded from: classes2.dex */
class b {
    @NonNull
    private static String a(NetworkInfo networkInfo) {
        if (networkInfo == null || !networkInfo.isConnected()) {
            return "NA";
        }
        if (networkInfo.getType() == 1) {
            return "WIFI";
        }
        if (networkInfo.getType() != 0) {
            return "NA";
        }
        switch (networkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3G";
            case 13:
                return "4G";
            default:
                String subtypeName = networkInfo.getSubtypeName();
                if ("TD-SCDMA".equalsIgnoreCase(subtypeName) || "WCDMA".equalsIgnoreCase(subtypeName) || "CDMA2000".equalsIgnoreCase(subtypeName)) {
                    subtypeName = "3G";
                }
                return subtypeName;
        }
    }

    @NonNull
    public static String a(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager == null ? "NA" : a(connectivityManager.getActiveNetworkInfo());
    }

    @NonNull
    public static String a() {
        return Build.DISPLAY;
    }

    @NonNull
    public static String b(@NonNull Context context) {
        try {
            String str = (String) Class.forName("mitv.os.System").getMethod("getDeviceID", new Class[0]).invoke(null, new Object[0]);
            if (str != null) {
                Log.i("Utils", "return mitv Device ID: " + str);
                return str;
            }
        } catch (Exception e) {
            Log.e("Utils", "get mitv deviceid fail.", e);
        }
        try {
            String string = Settings.Secure.getString(context.getContentResolver(), MicoSettings.Secure.DEVICE_ID);
            if (string != null) {
                Log.i("Utils", "return Device ID from settings: " + string);
                return string;
            }
        } catch (Exception e2) {
            Log.e("Utils", "get deviceid fail.", e2);
        }
        Log.i("Utils", "return default Device ID: NA");
        return "NA";
    }

    @NonNull
    public static String b() {
        try {
            Object invoke = Class.forName("mitv.os.System").getMethod("getPlatform", new Class[0]).invoke(null, new Object[0]);
            if (invoke != null) {
                Log.i("Utils", "return mitv Device Model: " + invoke);
                return String.valueOf(invoke);
            }
        } catch (Exception e) {
            Log.e("Utils", "get mitv DeviceModel fail.", e);
        }
        String str = Build.MODEL;
        Log.i("Utils", "return default Device Model: " + str);
        return str;
    }

    @SuppressLint({"HardwareIds"})
    public static String c(@NonNull Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService("bluetooth");
        if (bluetoothManager == null) {
            Log.e("Utils", "getBluetoothMac: get BluetoothManager is null");
            return null;
        }
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        if (adapter != null) {
            return adapter.getAddress();
        }
        Log.e("Utils", "getBluetoothMac: BluetoothAdapter is null");
        return null;
    }

    public static Context d(Context context) {
        return ((Context) Objects.requireNonNull(context)).getApplicationContext();
    }
}
