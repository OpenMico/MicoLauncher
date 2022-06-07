package com.xiaomi.mico.settingslib.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class Constants {
    public static final String ACTION_MANUALLY_DELETE_WIFI = "com.xiaomi.mico.settings.manually.delete.wifi";
    public static final String ACTION_MANUALLY_SETTING_WIFI = "com.xiaomi.mico.settings.manually.set.wifi";
    public static final String ENCRYPT_KEY = "encryptionsecret";
    public static final String EXTRA_CUSER_ID = "EXTRA_CUSER_ID";
    public static final String EXTRA_DEVICE_ID = "EXTRA_DEVICE_ID";
    public static final String EXTRA_LAUNCHED_BY_LAUNCHER = "EXTRA_LAUNCHED_BY_LAUNCHER";
    public static final String EXTRA_MANUALLY_DELETE_WIFI_CONNECTED = "extra_is_connected_before_delete";
    public static final String EXTRA_MANUALLY_SETTING_WIFI_STATUS = "status";
    public static final String EXTRA_MICO_SID_TOKEN = "EXTRA_MICO_SID_TOKEN";
    public static final String EXTRA_MI_BRAIN_ID = "EXTRA_MI_BRAIN_ID";
    public static final String EXTRA_MODEL = "EXTRA_MODEL";
    public static final String EXTRA_ROM_CHANNEL = "EXTRA_ROM_CHANNEL";
    public static final String EXTRA_ROM_VERSION = "EXTRA_ROM_VERSION";
    public static final String EXTRA_SN = "EXTRA_SN";
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    public static final int FACE_TRACK_STATE_CLOSE = 0;
    public static final int FACE_TRACK_STATE_DEFAULT = -1;
    public static final int FACE_TRACK_STATE_OPEN = 1;
    public static final int MICO_SMARTHOME_MODE_CATEGORY = 0;
    public static final int MICO_SMARTHOME_MODE_ROOM = 1;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface MICO_SMARTHOME_MODE {
    }
}
