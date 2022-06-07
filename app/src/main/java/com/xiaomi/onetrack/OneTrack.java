package com.xiaomi.onetrack;

import android.content.Context;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.api.f;
import com.xiaomi.onetrack.b.g;
import com.xiaomi.onetrack.e.a;
import com.xiaomi.onetrack.util.i;
import com.xiaomi.onetrack.util.p;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class OneTrack {
    private static boolean a;
    private static boolean b;
    private f c;

    /* loaded from: classes4.dex */
    public static class Event {
        public static final String ANCHOR_STATUS = "anchor_status";
        public static final String CART = "cart";
        public static final String CLICK = "click";
        public static final String COMMENT = "comment";
        public static final String DOWNLOAD = "download";
        public static final String EXPOSE = "expose";
        public static final String FAVORITE = "favorite";
        public static final String FOLLOW = "follow";
        public static final String GIFT_INTERACTION = "gift_interaction";
        public static final String LIKE = "like";
        public static final String LOGIN = "login";
        public static final String ORDER = "order";
        public static final String PLAY = "play";
        public static final String ROOM_SEATING = "room_seating";
        public static final String SEARCH = "search";
        public static final String SEND_MESSAGE = "send_message";
        public static final String UNLOCK = "unlock";
        public static final String VIEW = "view";
        public static final String VIEWER_STATUS = "viewer_status";
    }

    /* loaded from: classes4.dex */
    public interface ICommonPropertyProvider {
        Map<String, Object> getDynamicProperty(String str);
    }

    /* loaded from: classes4.dex */
    public interface IEventHook {
        boolean isCustomDauEvent(String str);

        boolean isRecommendEvent(String str);
    }

    /* loaded from: classes4.dex */
    public static class Param {
        public static final String ANCHOR_UID = "anchor_uid";
        public static final String ANDROID_ID = "android_id";
        public static final String APPID = "appid";
        public static final String APP_PLATFORM = "app_platform";
        public static final String APP_VER = "app_ver";
        public static final String ASSET_ID = "asset_id";
        public static final String ASSET_NAME = "asset_name";
        public static final String BIRTHDAY = "birthday";
        public static final String BUILD = "build";
        public static final String CHANNEL = "channel";
        public static final String CITY = "city";
        public static final String CLASS = "class";
        public static final String DEVICE_LANG = "device_lang";
        public static final String DURATION = "duration";
        public static final String ELEMENT_ID = "element_id";
        public static final String ELEMENT_NAME = "element_name";
        public static final String EXCEPTION = "exception";
        public static final String EXP_ID = "exp_id";
        public static final String E_TS = "e_ts";
        public static final String FIRST_LOGIN_TIME = "first_login_time";
        public static final String FIRST_OPEN = "first_open";
        public static final String FIRST_ORDER_TIME = "first_order_time";
        public static final String FIRST_VISIT_TIME = "first_visit_time";
        public static final String GENDER = "gender";
        public static final String GIFT_ID = "gift_id";
        public static final String GIFT_NUM = "gift_num";
        public static final String HOBBY = "hobby";
        public static final String IMEI_MD5 = "imei";
        public static final String INSTANCE_ID = "instance_id";
        public static final String JOB = "job";
        public static final String LINK = "link";
        public static final String LOGIN_METHOD = "login_method";
        public static final String LOGIN_RESULT = "login_result";
        public static final String LOGIN_TYPE = "login_type";
        public static final String MESSAGE = "message";
        public static final String MESSAGE_EMOJI = "message_emoji";
        public static final String MESSAGE_GIF = "message_gif";
        public static final String MESSAGE_PIC = "message_pic";
        public static final String MESSAGE_TEXT = "message_text";
        public static final String MESSAGE_VOICE = "message_voice";
        public static final String MFRS = "mfrs";
        public static final String MIUI = "miui";
        public static final String MODEL = "model";
        public static final String NAME = "name";
        public static final String NET = "net";
        public static final String OAID = "oaid";
        public static final String ORDER_ACT_TYPE = "order_act_type";
        public static final String ORDER_ID = "order_id";
        public static final String OS_VER = "os_ver";
        public static final String PHONE = "phone";
        public static final String PKG = "pkg";
        public static final String PLATFORM = "platform";
        public static final String PROVINCE = "province";
        public static final String REF_TIP = "ref_tip";
        public static final String REGION = "region";
        public static final String ROOM_ID = "room_id";
        public static final String SESSION_ID = "session_id";
        public static final String SKU_ID = "sku_id";
        public static final String SKU_NUM = "sku_num";
        public static final String TO_UID = "to_uid";
        public static final String TZ = "tz";
        public static final String UID = "uid";
        public static final String USER_ID = "user_id";
        public static final String UTM_CAMPAIGN = "utm_campaign";
        public static final String UTM_CONTENT = "utm_content";
        public static final String UTM_MEDIUM = "utm_medium";
        public static final String UTM_SOURCE = "utm_source";
        public static final String UTM_TERM = "utm_term";
        public static final String VIP_LEVEL = "vip_level";
    }

    public static String sdkVersion() {
        return BuildConfig.SDK_VERSION;
    }

    /* loaded from: classes4.dex */
    public enum Mode {
        APP("app"),
        PLUGIN("plugin"),
        SDK("sdk");
        
        private String a;

        Mode(String str) {
            this.a = str;
        }

        public String getType() {
            return this.a;
        }
    }

    /* loaded from: classes4.dex */
    public enum UserIdType {
        XIAOMI("xiaomi"),
        PHONE_NUMBER("phone_number"),
        WEIXIN(AccountIntent.WEIXIN_SNS_TYPE),
        WEIBO("weibo"),
        QQ(AccountIntent.QQ_SNS_TYPE),
        OTHER("other");
        
        private String a;

        UserIdType(String str) {
            this.a = str;
        }

        public String getUserIdType() {
            return this.a;
        }
    }

    /* loaded from: classes4.dex */
    public enum NetType {
        NOT_CONNECTED("NONE"),
        MOBILE_2G("2G"),
        MOBILE_3G("3G"),
        MOBILE_4G("4G"),
        MOBILE_5G("5G"),
        WIFI("WIFI"),
        ETHERNET("ETHERNET"),
        UNKNOWN("UNKNOWN");
        
        private String a;

        NetType(String str) {
            this.a = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.a;
        }
    }

    private OneTrack(Context context, Configuration configuration) {
        a.a(context.getApplicationContext());
        this.c = new f(context, configuration);
        setEventHook(new DefaultEventHook());
    }

    public static OneTrack createInstance(Context context, Configuration configuration) {
        return new OneTrack(context, configuration);
    }

    public static void setDebugMode(boolean z) {
        p.a(z);
    }

    public static void setDisable(boolean z) {
        a = z;
    }

    public static boolean isDisable() {
        return a;
    }

    public static void setUseSystemNetTrafficOnly() {
        b = true;
    }

    public static boolean isUseSystemNetTrafficOnly() {
        return b;
    }

    public static void setTestMode(boolean z) {
        p.b(z);
    }

    public static void registerCrashHook(Context context) {
        CrashAnalysis.a(context);
    }

    public static void setAccessNetworkEnable(Context context, final boolean z) {
        a(context);
        i.a(new Runnable() { // from class: com.xiaomi.onetrack.OneTrack.1
            @Override // java.lang.Runnable
            public void run() {
                g.a(z);
                g.b(z);
            }
        });
    }

    private static void a(Context context) {
        if (context != null) {
            a.a(context.getApplicationContext());
            return;
        }
        throw new IllegalStateException("context is null!");
    }

    public void track(String str, Map<String, Object> map) {
        this.c.a(str, map);
    }

    public void track(String str, List<String> list, Map<String, Object> map) {
        HashMap hashMap = new HashMap();
        if (map != null) {
            hashMap.putAll(map);
        }
        hashMap.put("exp_id", String.join(Constants.ACCEPT_TIME_SEPARATOR_SP, list));
        this.c.a(str, (Map<String, Object>) hashMap);
    }

    public void trackPluginEvent(String str, String str2, Map<String, Object> map) {
        this.c.a(str, str2, map);
    }

    public void login(String str, UserIdType userIdType, Map<String, Object> map, boolean z) {
        this.c.a(str, userIdType, map, z);
    }

    public void login(String str, UserIdType userIdType, Map<String, Object> map) {
        login(str, userIdType, map, false);
    }

    public void logout() {
        logout(null, false);
    }

    public void logout(Map<String, Object> map, boolean z) {
        this.c.a(map, z);
    }

    public void setCommonProperty(Map<String, Object> map) {
        this.c.c(map);
    }

    public void clearCommonProperty() {
        this.c.a();
    }

    public void removeCommonProperty(String str) {
        this.c.a(str);
    }

    public void setDynamicCommonProperty(ICommonPropertyProvider iCommonPropertyProvider) {
        this.c.a(iCommonPropertyProvider);
    }

    public void setUserProfile(Map<String, Object> map) {
        this.c.a(map);
    }

    public void setUserProfile(String str, Object obj) {
        this.c.a(str, obj);
    }

    public void userProfileIncrement(Map<String, ? extends Number> map) {
        this.c.b(map);
    }

    public void userProfileIncrement(String str, Number number) {
        this.c.a(str, number);
    }

    public void setInstanceId(String str) {
        this.c.b(str);
    }

    public String getInstanceId() throws OnMainThreadException {
        return this.c.b();
    }

    public String getOAID(Context context) throws OnMainThreadException {
        return this.c.a(context);
    }

    public void trackServiceQualityEvent(ServiceQualityEvent serviceQualityEvent) {
        this.c.a(serviceQualityEvent);
    }

    public void setEventHook(IEventHook iEventHook) {
        this.c.a(iEventHook);
    }

    public void setCustomPrivacyPolicyAccepted(boolean z) {
        this.c.b(z);
    }
}
