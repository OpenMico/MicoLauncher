package com.xiaomi.smarthome.setting;

/* loaded from: classes4.dex */
public class LoginSetting {
    public static final String ACCOUNT_TYPE_MI = "com.xiaomi";
    public static final String COOKIE_DOMAIN_MIOT_STORE = "shopapi.io.mi.com";
    public static final String COOKIE_DOMAIN_MI_ESHOPM = "m.mi.com";
    public static final String COOKIE_DOMAIN_MI_HUODONG = ".huodong.mi.com";
    public static final String COOKIE_DOMAIN_PASSPORT_API = "account.xiaomi.com";
    public static final String COOKIE_DOMAIN_RECHARGE_WEB = "web.recharge.pay.xiaomi.com";
    public static final String COOKIE_DOMAIN_XIAOMI_HOME = ".home.mi.com";
    public static final String COOKIE_DOMAIN_XIAOMI_IO = ".io.mi.com";
    public static final String COOKIE_DOMAIN_XIAO_QIANG = "api.gorouter.info";
    public static final String SID_I_AI = "i.ai.mi.com";
    public static final String SID_MIOT_STORE = "miotstore";
    public static final String SID_MI_ESHOPM = "mi_eshopm_go";
    public static final String SID_MI_HUODONG = "mi_huodong";
    public static final String SID_PASSPORT_API = "passportapi";
    public static final String SID_RECHARGE_WEB = "recharge-web";
    public static final String SID_STAGING = "miot-third-test";
    public static final String SID_XIAOMI_HOME = "xiaomihome";
    public static final String SID_XIAOMI_IO = "xiaomiio";
    public static final String SID_XIAO_QIANG = "xiaoqiang";

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getDomainBySid(String str) {
        char c;
        switch (str.hashCode()) {
            case -1419722781:
                if (str.equals(SID_MI_HUODONG)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -513303352:
                if (str.equals("passportapi")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -360749757:
                if (str.equals(SID_XIAO_QIANG)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 265338657:
                if (str.equals("xiaomiio")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1587352602:
                if (str.equals(SID_XIAOMI_HOME)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1781001842:
                if (str.equals(SID_MI_ESHOPM)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 2026863872:
                if (str.equals(SID_MIOT_STORE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2144015150:
                if (str.equals(SID_RECHARGE_WEB)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return COOKIE_DOMAIN_XIAOMI_IO;
            case 1:
                return COOKIE_DOMAIN_XIAO_QIANG;
            case 2:
                return COOKIE_DOMAIN_PASSPORT_API;
            case 3:
                return COOKIE_DOMAIN_XIAOMI_HOME;
            case 4:
                return COOKIE_DOMAIN_MIOT_STORE;
            case 5:
                return COOKIE_DOMAIN_RECHARGE_WEB;
            case 6:
                return COOKIE_DOMAIN_MI_ESHOPM;
            case 7:
                return COOKIE_DOMAIN_MI_HUODONG;
            default:
                return "";
        }
    }
}
