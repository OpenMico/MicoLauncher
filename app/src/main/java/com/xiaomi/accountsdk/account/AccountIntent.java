package com.xiaomi.accountsdk.account;

/* loaded from: classes2.dex */
public class AccountIntent {
    public static final String ACTION_COMMON_SERVICE = "com.xiaomi.account.action.COMMON_SERVICE";
    public static final String ACTION_MODIFY_SAFE_PHONE = "com.xiaomi.account.action.MODIFY_SAFE_PHONE";
    public static final String ACTION_SERVICE_TOKEN_OP = "com.xiaomi.account.action.SERVICE_TOKEN_OP";
    public static final String ACTION_SIM_ACTIVATE_SERVICE = "com.xiaomi.simactivate.service.action.SimActivateService";
    public static final String ACTION_SNS_NOTIFICATION = "com.xiaomi.action.SNS_NOTIFICATION_REDIRECTION";
    public static final String ACTION_UDEVID_SERVICE = "com.xiaomi.account.action.BIND_XIAOMI_UDEVID_SERVICE";
    public static final String ACTION_UPLOAD_DEVICE_INFO = "com.xiaomi.account.action.UPLOAD_DEVICE_INFO";
    public static final String ACTION_VIEW_BIND_PHONE_INFO = "com.xiaomi.account.action.VIEW_BIND_PHONE_INFO";
    public static final String DEVICE_INFO_PARAM = "uploadDeviceInfo";
    public static final String DEVICE_NAME_UPLOAD_PARAM = "deviceName";
    @Deprecated
    public static final String EXTRA_ACCOUNT_PHONE_LIST = "acc_phone_list";
    @Deprecated
    public static final String EXTRA_BINDING_PHONE_NUM = "extra_binding_phone_num";
    public static final String EXTRA_BIND_PHONE_TYPE = "bind_phone_type";
    public static final String EXTRA_HAS_PASSWORD = "has_password";
    public static final String EXTRA_NOTIFICATION_ACTIONBAR_TITLE = "notification_actionbar_title";
    public static final String EXTRA_NOTIFICATION_URL = "notification_url";
    public static final String EXTRA_PASSWORD_LOGIN = "password_login";
    public static final String EXTRA_RESULT_NOTIFICATION_IDENTITY_INFO = "notification_identity_info";
    public static final String EXTRA_RESULT_NOTIFICATION_PASSTOKEN_INDEX = "notification_passtoken_index";
    public static final String EXTRA_RESULT_NOTIFICATION_USER_ID = "notification_user_id";
    public static final String EXTRA_RETURN_STS_URL = "return_sts_url";
    public static final String EXTRA_SNS_TYPE = "extra_sns_type";
    public static final String EXTRA_STS_URL_RESULT = "sts_url_result";
    public static final String FB_SNS_TYPE = "facebook";
    public static final String GOOGLE_SNS_TYPE = "google";
    public static final String PACKAGE_NAME_FIND_DEVICE = "com.xiaomi.finddevice";
    public static final String PACKAGE_SIM_ACTIVATE = "com.xiaomi.simactivate.service";
    public static final String PACKAGE_XIAOMI_ACCOUNT = "com.xiaomi.account";
    public static final String QQ_SNS_TYPE = "qq";
    public static final String SINA_SNS_TYPE = "sina";
    public static final String STAT_KEY_SOURCE = "stat_key_source";
    public static final int TYPE_COMPLETE = 0;
    public static final int TYPE_MODIFY = 1;
    public static final String WEIXIN_SNS_TYPE = "weixin";
}
