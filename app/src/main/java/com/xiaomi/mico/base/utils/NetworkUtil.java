package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/* loaded from: classes3.dex */
public class NetworkUtil {
    public static final int TYPE_MOBILE_2G = 2;
    public static final int TYPE_MOBILE_3G = 3;
    public static final int TYPE_MOBILE_4G = 4;
    public static final int TYPE_MOBILE_UNKNOWN = 1;
    public static final int TYPE_NONE = -1;
    public static final int TYPE_OTHER = 5;
    public static final int TYPE_WIFI = 0;

    private static int a(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 2;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 3;
            case 13:
                return 4;
            default:
                return 1;
        }
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        return (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        return (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null || activeNetworkInfo.getType() != 1) ? false : true;
    }

    public static int getNetworkType(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            return -1;
        }
        switch (networkInfo.getType()) {
            case 0:
                return a(networkInfo.getSubtype());
            case 1:
                return 0;
            default:
                return 5;
        }
    }

    public static int getActiveNetworkType(@NonNull Context context) {
        return getNetworkType(((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo());
    }

    public static int getTelephonyNetworkClass(@NonNull Context context) {
        return a(((TelephonyManager) context.getSystemService("phone")).getNetworkType());
    }

    public static String appendUrl(String str, List<Pair<String, String>> list) throws UnsupportedEncodingException {
        if (str != null) {
            StringBuilder sb = new StringBuilder(str);
            int indexOf = str.indexOf("?");
            if (indexOf < 0) {
                sb.append("?");
            } else if (indexOf != str.length() - 1) {
                sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            }
            for (int i = 0; i < list.size(); i++) {
                if (i != 0) {
                    sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
                }
                String str2 = (String) list.get(i).first;
                String str3 = (String) list.get(i).second;
                if (TextUtils.isEmpty(str3)) {
                    return "";
                }
                sb.append(str2);
                sb.append("=");
                sb.append(URLEncoder.encode(str3, "UTF-8"));
            }
            return sb.toString();
        }
        throw new NullPointerException("origin is not allowed null");
    }
}
