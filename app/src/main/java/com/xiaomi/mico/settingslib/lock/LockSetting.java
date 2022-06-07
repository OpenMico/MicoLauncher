package com.xiaomi.mico.settingslib.lock;

import android.content.Context;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class LockSetting {
    public static final String AI_QI_YI_APP = "lock_ai_qi_yi";
    public static final String AUTHORITY_IBILI = "ibili";
    public static final String AUTHORITY_MIJIA = "mijia";
    public static final String AUTHORITY_QIYI = "qiyi";
    public static final String AUTHORITY_TENCENTVIDEO = "tencentvideo";
    public static final String AUTHORITY_TIKTOK = "tiktok";
    public static final String AUTHORITY_YOUKU = "youku";
    public static final String AUTHORITY_YYTV = "yytv";
    public static final String BILIBILI_APP = "lock_bilibili";
    public static final String CP_BILI = "bilibili";
    public static final String CP_IQIYI = "iqiyi";
    public static final String CP_TENCENT_VIDEO = "tencent";
    public static final String MICO_LOCK_APP_PRE = "lock_%s";
    public static final String MI_JIA_APP = "lock_mi_jia";
    public static final String PACKAGE_MI_JIA = "mi_jia";
    public static final String PACKAGE_NAME_BILI = "tv.danmaku.bili";
    public static final String PACKAGE_NAME_QIYI = "com.qiyi.video.speaker";
    public static final String PACKAGE_NAME_TENCENT_VIDEO = "com.tencent.qqlive.audiobox";
    public static final String PACKAGE_NAME_TIKTOK = "com.ss.android.ugc.aweme";
    public static final String PACKAGE_NAME_YOUKU = "com.youku.iot";
    public static final String PACKAGE_NAME_YY = "com.duowan.yytv";
    public static final String PACKAGE_SETTING = "com.xiaomi.mico.settings";
    public static final String PACKAGE_SMART_HOME = "smart_home";
    public static final int SMART_HOME_POSITION = 0;
    public static final String TENCENT_APP = "lock_tencent";
    public static final String TIK_TOK_APP = "lock_tik_tok";
    public static final String YOU_KU_APP = "lock_you_ku";
    public static final String YY_APP = "lock_yy";

    public static boolean getHasLock(Context context, String str) {
        String appId;
        List<String> lockAppList;
        return a(context) && MicoSettings.isAppLockEnable(context) && (appId = getAppId(str)) != null && (lockAppList = getLockAppList(context)) != null && lockAppList.contains(appId);
    }

    public static List<String> getLockAppList(Context context) {
        List<String> list = (List) Gsons.getGson().fromJson(MicoSettings.getAppOpenLockPass(context), new TypeToken<List<String>>() { // from class: com.xiaomi.mico.settingslib.lock.LockSetting.1
        }.getType());
        if (list != null) {
            list.remove(String.format(MICO_LOCK_APP_PRE, PACKAGE_MI_JIA));
        }
        return list;
    }

    public static String getAppId(String str) {
        if (str == null) {
            return null;
        }
        LockApp[] values = LockApp.values();
        for (LockApp lockApp : values) {
            if (str.equals(lockApp.getId())) {
                return lockApp.getId();
            }
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -1427573947:
                if (str.equals("tencent")) {
                    c = '\n';
                    break;
                }
                break;
            case -894756483:
                if (str.equals("com.duowan.yytv")) {
                    c = 6;
                    break;
                }
                break;
            case -873713414:
                if (str.equals("tiktok")) {
                    c = '\b';
                    break;
                }
                break;
            case -426347972:
                if (str.equals("com.youku.iot")) {
                    c = 3;
                    break;
                }
                break;
            case -338862911:
                if (str.equals("com.tencent.qqlive.audiobox")) {
                    c = 11;
                    break;
                }
                break;
            case 3471144:
                if (str.equals("qiyi")) {
                    c = 2;
                    break;
                }
                break;
            case 3724706:
                if (str.equals("yytv")) {
                    c = 5;
                    break;
                }
                break;
            case 99993581:
                if (str.equals("ibili")) {
                    c = 14;
                    break;
                }
                break;
            case 100440849:
                if (str.equals("iqiyi")) {
                    c = 0;
                    break;
                }
                break;
            case 103897062:
                if (str.equals("mijia")) {
                    c = 7;
                    break;
                }
                break;
            case 115168713:
                if (str.equals("youku")) {
                    c = 4;
                    break;
                }
                break;
            case 203777110:
                if (str.equals("tencentvideo")) {
                    c = '\f';
                    break;
                }
                break;
            case 313184810:
                if (str.equals("com.ss.android.ugc.aweme")) {
                    c = '\t';
                    break;
                }
                break;
            case 409393875:
                if (str.equals("com.qiyi.video.speaker")) {
                    c = 1;
                    break;
                }
                break;
            case 887268872:
                if (str.equals("bilibili")) {
                    c = '\r';
                    break;
                }
                break;
            case 1994036591:
                if (str.equals("tv.danmaku.bili")) {
                    c = 15;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                return AI_QI_YI_APP;
            case 3:
            case 4:
                return YOU_KU_APP;
            case 5:
            case 6:
                return YY_APP;
            case 7:
                return MI_JIA_APP;
            case '\b':
            case '\t':
                return TIK_TOK_APP;
            case '\n':
            case 11:
            case '\f':
                return TENCENT_APP;
            case '\r':
            case 14:
            case 15:
                return BILIBILI_APP;
            default:
                return String.format(MICO_LOCK_APP_PRE, str);
        }
    }

    public static String getLockPass(Context context) {
        return MicoSettings.getAppLockPass(context);
    }

    private static boolean a(Context context) {
        return !"".equals(getLockPass(context));
    }

    public static void clearPass(Context context) {
        MicoSettings.setAppLockPass(context, "");
        MicoSettings.setAppOpenLockPass(context, "");
        MicoSettings.setAppLockEnable(context, false);
    }

    public static void setLock(Context context, String str, boolean z) {
        List list = (List) Gsons.getGson().fromJson(MicoSettings.getAppOpenLockPass(context), new TypeToken<List<String>>() { // from class: com.xiaomi.mico.settingslib.lock.LockSetting.2
        }.getType());
        if (list == null) {
            list = new ArrayList();
        }
        if (z) {
            list.add(getAppId(str));
        } else {
            list.remove(getAppId(str));
        }
        MicoSettings.setAppOpenLockPass(context, Gsons.getGson().toJson(list));
    }
}
