package com.xiaomi.smarthome.core.server.internal.util;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.smarthome.core.server.internal.account.AccountManager;
import com.xiaomi.smarthome.globalsetting.GlobalSetting;
import com.xiaomi.smarthome.library.common.util.XMStringUtils;
import com.xiaomi.smarthome.library.commonapi.SystemApi;
import com.xiaomi.smarthome.library.crypto.SHA1Util;
import java.net.URLEncoder;

/* loaded from: classes4.dex */
public class UserAgentUtil {
    public static final String FOR_APP = "screenspeakerapp";
    public static final String FOR_RN = "xiaomi_ai_app_rn";
    private static String a;
    private static Object b = new Object();

    private static boolean a(char c) {
        return c > 31 && c < 127;
    }

    public static void clear() {
        synchronized (b) {
            a = null;
        }
    }

    public static String getUserAgent(String str) {
        synchronized (b) {
            if (a == null) {
                Context appContext = GlobalSetting.getAppContext();
                boolean isEurope = GlobalSetting.isEurope();
                String imei = SystemApi.getInstance().getImei(appContext);
                StringBuilder sb = new StringBuilder();
                sb.append(SystemApi.getInstance().getOsName().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, ""));
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append(SystemApi.getInstance().getOsVersion().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, ""));
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append("");
                sb.append(SystemApi.getInstance().getAppVersion(appContext).replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, ""));
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append(SystemApi.getInstance().getDeviceModel().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, ""));
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append(SystemApi.getInstance().getOsIncremental().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, ""));
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append(SystemApi.getInstance().getDeviceId(appContext, isEurope).replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, ""));
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append((isEurope ? SHA1Util.getSHA2Digest(imei) : XMStringUtils.getMd5Digest(imei)).replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, ""));
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append(AccountManager.getInstance().getMiId().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, ""));
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append(str);
                a = sanitizeUA(sb.toString());
            }
        }
        return a;
    }

    public static String sanitizeUA(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (checkValid(str)) {
            return str;
        }
        try {
            StringBuilder sb = new StringBuilder(str.length());
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (!a(charAt)) {
                    sb.append(URLEncoder.encode(charAt + "", "UTF-8"));
                } else {
                    sb.append(charAt);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static boolean checkValid(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!a(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
