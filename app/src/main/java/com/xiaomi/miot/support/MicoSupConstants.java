package com.xiaomi.miot.support;

/* loaded from: classes2.dex */
public class MicoSupConstants {
    public static final String DEFAULT_ROOM_ID = "0";
    public static final String ROOM_DEFAULT = "默认房间";
    public static final String ROOM_SHARE = "共享";
    public static final String SHARE_ROOM_ID = "mico_share";
    public static final String TAG_LAU = "MICO_LAU";
    public static final String TAG_SUP = "MICO_SUP";
    public static boolean sIsDebug = false;

    /* loaded from: classes2.dex */
    public static class MsgAction {
        public static final int ACTION_DEVICE_REFRESH_FINISHED = 4;
        public static final String ACTION_FORCE_REFRESH_CATEGORY = "forceRefreshCategory";
        public static final int ACTION_ON_PROP_CHANGED = 1;
        public static final int ACTION_REPLY_DEVICE_INFO = 8;
        public static final String ACTION_SET_DEVICE_ACTION = "setDeviceAction";
        public static final String ACTION_SET_DEVICE_PROP = "setDeviceProp";
        public static final int ACTION_SMART_HOME_INFO_READY = 2;
    }

    /* loaded from: classes2.dex */
    public static class Other {
        public static final long ALL_INFO_READY_BACKUP_TIMEOUT = 20000;
        public static final String KEY_REFRESH_DEVICE_TASK_ID = "taskId";
        public static final String KEY_SERVER_ENV_PROP = "mico_server_env";
        public static final long REPLY_DEVICE_INFO_TIME_OUT = 5000;
    }

    /* loaded from: classes2.dex */
    public static class SPKey {
        public static final String SPKEY_CATEGORY_MAPPING = "device_category_mapping";
        public static final String SPKEY_NAME_MICO_SUP = "sp_mico_sup";
    }

    private MicoSupConstants() {
    }
}
