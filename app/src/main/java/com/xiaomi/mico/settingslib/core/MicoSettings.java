package com.xiaomi.mico.settingslib.core;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.common.Gsons;
import java.util.ArrayList;
import java.util.HashSet;

/* loaded from: classes3.dex */
public class MicoSettings {
    public static final String BIG_CURRENT_FRAGMENT = "current_fragment";
    public static final String BIG_TARGET_FRAGMENT = "current_fragment_string";
    public static final HashSet<String> RIGISTER_KEY = new HashSet<String>() { // from class: com.xiaomi.mico.settingslib.core.MicoSettings.1
        {
            add(Global.GESTURE_CONTROL);
            add(Global.CHILD_MODE);
            add(Global.QUICK_ENTER_CHILD_MODE);
            add(Global.DISTANCE_PROTECTION);
            add(Global.DISTANCE_PROTECTION_CHILD_MODE);
            add(Global.EYE_PROTECTION_MODE);
            add(Global.CHILD_CONTENT_RECOMMENDATION);
            add(Global.LOCK_SCREEN_WALL_PAPER);
            add(Global.MULTI_DIALOG);
            add(Global.MULTI_DEVICE_SPEECH);
            add(Global.WAKEUP_SOUND);
            add(Global.MORNING_REMIND);
            add(Global.SLEEP_MODE);
            add(Global.TTS_VENDOR);
            add(Global.MESH_ENABLE);
            add(Global.ENV_LEVEL);
            add(Global.AUTO_SCREEN_OFF_TIME);
            add(Global.INTERCOM_DEVICE_CONFIG);
            add("audio_relay");
            add(Global.BLUETOOTH_BT_CONTROL);
            add(Global.BLUETOOTH_ENABLE);
            add(Global.CURRENT_BLUETOOTH_DEVICE_OPERATION);
            add(Global.MICO_HOME_LIST);
            add(Global.MICO_SMARTHOME_MODE);
        }
    };

    /* loaded from: classes3.dex */
    public static final class Global {
        public static final String ALBUM_TYPE = "mico_album_type";
        public static final String ANTI_ADDITION = "mico_anti_addiction";
        public static final String APP_HAS_SET_LOCK_PASS = "security_has_set_pass_key";
        public static final String APP_LOCK_PASS = "security_pass_key";
        public static final String APP_OPEN_LOCK_PASS = "mico_app_open_lock";
        public static final String AUDIO_RELAY = "audio_relay";
        public static final String AUTO_SCREEN_OFF_TIME = "mico_auto_screen_off_time";
        public static final String BLUETOOTH_BT_CONTROL = "mico_bluetooth_bt_control";
        public static final String BLUETOOTH_ENABLE = "mico_bluetooth_enable";
        public static final String CHILD_CONTENT_RECOMMENDATION = "mico_child_content_recommendation";
        public static final String CHILD_MODE = "mico_child_mode";
        public static final String CURRENT_BLUETOOTH_DEVICE_OPERATION = "mico_current_bluetooth_device_operation";
        public static final String DISTANCE_PROTECTION = "mico_distance_protection";
        public static final String DISTANCE_PROTECTION_CHILD_MODE = "mico_distance_protection_for_child";
        public static final String ENV_LEVEL = "mico_level";
        public static final String EYE_PROTECTION_MODE = "mico_eye_protection_mode";
        public static final String GESTURE_CONTROL = "mico_gesture_control";
        public static final String INTERCOM_DEVICE_CONFIG = "mico_intercom_device_config";
        public static final String INTERCOM_FACE_TRACK_STATE = "mico_intercom_face_track_state";
        public static final String INTERCOM_OPEN_REFUSE_UNFAMILIAR_PHONE = "mico_intercom_open_refuse_unfamiliar_phone";
        public static final String LOCK_SCREEN_ADVERTISER_CODE = "mico_lock_screen_advertiser_code";
        public static final String LOCK_SCREEN_WALL_PAPER = "mico_lock_screen";
        public static final String MESH_ENABLE = "mico_mesh_enable";
        public static final String MICO_CLOSE_SLEEP_MODE_TIME = "close_sleep_mode_time";
        public static final String MICO_HOME_LIST = "mico_home_list";
        public static final String MICO_ROM_UPDATE_LOG = "rom_update_log";
        public static final String MICO_SERVER_ENV = "mico_server_env";
        public static final String MICO_SMARTHOME_MODE = "mico_smarthome_mode";
        public static final String MORNING_REMIND = "mico_morning_reminds";
        public static final String MULTI_DEVICE_SPEECH = "mico_multi_device_speech";
        public static final String MULTI_DIALOG = "mico_multi_dialog";
        public static final String QUICK_ENTER_CHILD_MODE = "mico_quick_enter_child_mode";
        public static final String SLEEP_MODE = "mico_sleep_moded";
        public static final String SYNCH_SETTINGS = "mico_synch_settings_state";
        public static final String TTS_VENDOR = "mico_tts_vendor";
        public static final String WAKEUP_SOUND = "mico_wakeup_sound";
    }

    public static boolean putBooleanGlobal(Context context, String str, boolean z) {
        return Settings.Global.putInt(context.getContentResolver(), str, z ? 1 : 0);
    }

    public static boolean getBooleanGlobal(Context context, String str) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), str, 0) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getBooleanGlobal(Context context, String str, boolean z) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), str, z ? 1 : 0) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return z;
        }
    }

    public static boolean putStringGlobal(Context context, String str, String str2) {
        return Settings.Global.putString(context.getContentResolver(), str, str2);
    }

    public static String getStringGlobal(Context context, String str, String str2) {
        String string;
        try {
            string = Settings.Global.getString(context.getContentResolver(), str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !TextUtils.isEmpty(string) ? string : str2;
    }

    public static int getIntGlobal(Context context, String str, int i) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), str, i);
        } catch (Exception e) {
            e.printStackTrace();
            return i;
        }
    }

    public static boolean putIntGlobal(Context context, String str, int i) {
        return Settings.Global.putInt(context.getContentResolver(), str, i);
    }

    public static long getLongGlobal(Context context, String str, long j) {
        try {
            return Settings.Global.getLong(context.getContentResolver(), str, j);
        } catch (Exception e) {
            e.printStackTrace();
            return j;
        }
    }

    public static boolean putLongGlobal(Context context, String str, long j) {
        return Settings.Global.putLong(context.getContentResolver(), str, j);
    }

    /* loaded from: classes3.dex */
    public static class Secure {
        public static final String BLACK_LIST_NAME = "mico_WifiBlackList";
        public static final String DEVICE_ID = "deviceid";
        public static final String KEY_INITIALIZED = "initialized";
        public static final String PWD_SP = "mico_history_wifi_pwd";

        public static String getSecureString(Context context, String str, String str2) {
            try {
                return Settings.Secure.getString(context.getContentResolver(), str);
            } catch (Exception e) {
                e.printStackTrace();
                return str2;
            }
        }

        public static boolean putSecureString(Context context, String str, String str2) {
            return Settings.Secure.putString(context.getContentResolver(), str, str2);
        }

        public static int getSecureInt(Context context, String str, int i) {
            try {
                return Settings.Secure.getInt(context.getContentResolver(), str);
            } catch (Exception e) {
                e.printStackTrace();
                return i;
            }
        }

        public static boolean putSecureInt(Context context, String str, int i) {
            return Settings.Secure.putInt(context.getContentResolver(), str, i);
        }

        public static boolean getSecureBoolean(Context context, String str, boolean z) {
            try {
                return Settings.Secure.getInt(context.getContentResolver(), str, z ? 1 : 0) == 1;
            } catch (Exception e) {
                e.printStackTrace();
                return z;
            }
        }
    }

    public static void setDeviceId(Context context, String str) {
        putStringGlobal(context, Secure.DEVICE_ID, str);
    }

    public static String getDeviceId(Context context) {
        return getStringGlobal(context, Secure.DEVICE_ID, "");
    }

    public static void setStoreWifiInfo(Context context, String str) {
        Settings.Secure.putString(context.getContentResolver(), Secure.PWD_SP, str);
    }

    public static String getStoreWifiInfo(Context context, String str) {
        return Secure.getSecureString(context, Secure.PWD_SP, str);
    }

    public static void setBlackListWifiInfo(Context context, String str) {
        Settings.Secure.putString(context.getContentResolver(), Secure.BLACK_LIST_NAME, str);
    }

    public static String getBlackListWifiInfo(Context context, String str) {
        return Secure.getSecureString(context, Secure.BLACK_LIST_NAME, str);
    }

    public static void setAppLockPass(Context context, String str) {
        Settings.Global.putString(context.getContentResolver(), "security_pass_key", str);
    }

    public static String getRomUpdateLog(Context context) {
        return getStringGlobal(context, Global.MICO_ROM_UPDATE_LOG, "");
    }

    public static void setRomUpdateLog(Context context, String str) {
        putStringGlobal(context, Global.MICO_ROM_UPDATE_LOG, str);
    }

    public static String getAppLockPass(Context context) {
        return getStringGlobal(context, "security_pass_key", "");
    }

    public static String getAppOpenLockPass(Context context) {
        return getStringGlobal(context, Global.APP_OPEN_LOCK_PASS, "");
    }

    public static void setAppOpenLockPass(Context context, String str) {
        Settings.Global.putString(context.getContentResolver(), Global.APP_OPEN_LOCK_PASS, str);
    }

    public static boolean getAudioRelay(Context context) {
        return getBooleanGlobal(context, "audio_relay", false);
    }

    public static void setAudioRelay(Context context, boolean z) {
        putBooleanGlobal(context, "audio_relay", z);
    }

    public static boolean a(Context context, ChildModeConfig childModeConfig) {
        String jsonStr;
        if (childModeConfig == null || (jsonStr = childModeConfig.toJsonStr()) == null) {
            return false;
        }
        return putStringGlobal(context, Global.CHILD_MODE, jsonStr);
    }

    public static ChildModeConfig a(Context context) {
        return ChildModeConfig.parse(getStringGlobal(context, Global.CHILD_MODE, null));
    }

    public static boolean setQuickEnterChildModeEnable(Context context, boolean z) {
        return putBooleanGlobal(context, Global.QUICK_ENTER_CHILD_MODE, z);
    }

    public static boolean isQuickEnterChildModeEnable(Context context) {
        return getBooleanGlobal(context, Global.QUICK_ENTER_CHILD_MODE);
    }

    public static boolean setDistanceProtectionEnable(Context context, boolean z) {
        return putBooleanGlobal(context, Global.DISTANCE_PROTECTION, z);
    }

    public static boolean isDistanceProtectionEnable(Context context) {
        return getBooleanGlobal(context, Global.DISTANCE_PROTECTION);
    }

    public static boolean setDistanceProtectionEnableChildMode(Context context, boolean z) {
        return putBooleanGlobal(context, Global.DISTANCE_PROTECTION_CHILD_MODE, z);
    }

    public static boolean isDistanceProtectionEnableChildMode(Context context) {
        return getBooleanGlobal(context, Global.DISTANCE_PROTECTION_CHILD_MODE);
    }

    public static boolean setEyeProtectionEnable(Context context, boolean z) {
        return putBooleanGlobal(context, Global.EYE_PROTECTION_MODE, z);
    }

    public static boolean isEyeProtectionEnable(Context context) {
        return getBooleanGlobal(context, Global.EYE_PROTECTION_MODE);
    }

    public static boolean setChildContentRecommendationEnable(Context context, boolean z) {
        return putBooleanGlobal(context, Global.CHILD_CONTENT_RECOMMENDATION, z);
    }

    public static boolean isChildContentRecommendationEnable(Context context) {
        return getBooleanGlobal(context, Global.CHILD_CONTENT_RECOMMENDATION);
    }

    public static boolean setGestureControlEnable(Context context, boolean z) {
        return putBooleanGlobal(context, Global.GESTURE_CONTROL, z);
    }

    public static boolean setAntiAddictionEnable(Context context, boolean z) {
        return putBooleanGlobal(context, Global.ANTI_ADDITION, z);
    }

    public static boolean isAntiAddictionEnable(Context context) {
        return getBooleanGlobal(context, Global.ANTI_ADDITION);
    }

    public static boolean isGestureControlEnable(Context context) {
        return getBooleanGlobal(context, Global.GESTURE_CONTROL);
    }

    public static boolean setMultiDialogEnable(Context context, boolean z) {
        return putBooleanGlobal(context, Global.MULTI_DIALOG, z);
    }

    public static boolean isMultiDialogEnable(Context context) {
        return getBooleanGlobal(context, Global.MULTI_DIALOG);
    }

    public static void setNearbyWakeupEnable(Context context, boolean z) {
        putBooleanGlobal(context, Global.MULTI_DEVICE_SPEECH, z);
    }

    public static boolean isNearbyWakeupEnable(Context context) {
        return getBooleanGlobal(context, Global.MULTI_DEVICE_SPEECH);
    }

    public static void setWakeupSoundEnable(Context context, boolean z) {
        putBooleanGlobal(context, Global.WAKEUP_SOUND, z);
    }

    public static boolean isWakeupSoundEnable(Context context, boolean z) {
        return getBooleanGlobal(context, Global.WAKEUP_SOUND, z);
    }

    public static void setAppLockEnable(Context context, boolean z) {
        putBooleanGlobal(context, Global.APP_HAS_SET_LOCK_PASS, z);
    }

    public static boolean isAppLockEnable(Context context) {
        return getBooleanGlobal(context, Global.APP_HAS_SET_LOCK_PASS, false);
    }

    public static void setLockScreenStyle(Context context, int i) {
        Settings.Global.putInt(context.getContentResolver(), Global.LOCK_SCREEN_WALL_PAPER, i);
    }

    public static int getLockScreenStyle(Context context) {
        return getIntGlobal(context, Global.LOCK_SCREEN_WALL_PAPER, 0);
    }

    public static int getLockScreenStyle(Context context, int i) {
        return getIntGlobal(context, Global.LOCK_SCREEN_WALL_PAPER, i);
    }

    public static void setLockScreenAdvertiserCode(Context context, long j) {
        Settings.Global.putLong(context.getContentResolver(), Global.LOCK_SCREEN_ADVERTISER_CODE, j);
    }

    public static long getLockScreenAdvertiserCode(Context context) {
        return getLongGlobal(context, Global.LOCK_SCREEN_ADVERTISER_CODE, 0L);
    }

    public static boolean setAutoScreenOffTimeIndex(Context context, int i) {
        return Settings.Global.putInt(context.getContentResolver(), Global.AUTO_SCREEN_OFF_TIME, i);
    }

    public static int getAutoScreenOffTimeIndex(Context context, int i) {
        return getIntGlobal(context, Global.AUTO_SCREEN_OFF_TIME, i);
    }

    public static boolean setIntercomDeviceConfig(Context context, IntercomDeviceConfig intercomDeviceConfig) {
        return putStringGlobal(context, Global.INTERCOM_DEVICE_CONFIG, Gsons.getGson().toJson(intercomDeviceConfig));
    }

    @Nullable
    public static IntercomDeviceConfig getIntercomDeviceConfig(Context context) {
        String stringGlobal = getStringGlobal(context, Global.INTERCOM_DEVICE_CONFIG, "");
        if (!TextUtils.isEmpty(stringGlobal)) {
            return (IntercomDeviceConfig) Gsons.getGson().fromJson(stringGlobal, (Class<Object>) IntercomDeviceConfig.class);
        }
        return null;
    }

    public static boolean setMorningRemind(Context context, MorningRemindConfig morningRemindConfig) {
        String jsonStr;
        if (morningRemindConfig == null || (jsonStr = morningRemindConfig.toJsonStr()) == null) {
            return false;
        }
        return putStringGlobal(context, Global.MORNING_REMIND, jsonStr);
    }

    public static MorningRemindConfig getMorningRemind(Context context) {
        return MorningRemindConfig.parse(getStringGlobal(context, Global.MORNING_REMIND, null));
    }

    public static boolean setSleepMode(Context context, NightModeConfig nightModeConfig) {
        String jsonStr;
        if (nightModeConfig == null || (jsonStr = nightModeConfig.toJsonStr()) == null) {
            return false;
        }
        return putStringGlobal(context, Global.SLEEP_MODE, jsonStr);
    }

    public static NightModeConfig getSleepMode(Context context) {
        return NightModeConfig.parse(getStringGlobal(context, Global.SLEEP_MODE, null));
    }

    public static void setTtsVendor(Context context, String str) {
        putStringGlobal(context, Global.TTS_VENDOR, str);
    }

    public static String getTtsVendor(Context context, String str) {
        return getStringGlobal(context, Global.TTS_VENDOR, str);
    }

    public static void setMiBrainLevel(Context context, String str) {
        putStringGlobal(context, Global.ENV_LEVEL, str);
    }

    public static String getMiBrainLevel(Context context, String str) {
        return getStringGlobal(context, Global.ENV_LEVEL, str);
    }

    public static Uri getUriFor(String str) {
        return Settings.Global.getUriFor(str);
    }

    public static String getPath(Uri uri) {
        return uri.getLastPathSegment();
    }

    public static void setSynchSettings(Context context, boolean z) {
        putBooleanGlobal(context, Global.SYNCH_SETTINGS, z);
    }

    public static boolean isSynchSettings(Context context) {
        return getBooleanGlobal(context, Global.SYNCH_SETTINGS);
    }

    public static String getAlbumType(Context context, String str) {
        return getStringGlobal(context, Global.ALBUM_TYPE, str);
    }

    public static void setAlbumType(Context context, String str) {
        putStringGlobal(context, Global.ALBUM_TYPE, str);
    }

    public static boolean isBluetoothEnable(Context context) {
        return getBooleanGlobal(context, Global.BLUETOOTH_ENABLE);
    }

    public static void setBluetoothEnable(Context context, boolean z) {
        putBooleanGlobal(context, Global.BLUETOOTH_ENABLE, z);
    }

    public static String getCurrentBluetoothDeviceOperation(Context context) {
        return getStringGlobal(context, Global.CURRENT_BLUETOOTH_DEVICE_OPERATION, null);
    }

    public static boolean setCurrentBluetoothDeviceOperation(Context context, String str) {
        return putStringGlobal(context, Global.CURRENT_BLUETOOTH_DEVICE_OPERATION, str);
    }

    public static int getBluetoothBTControl(Context context, int i) {
        return getIntGlobal(context, Global.BLUETOOTH_BT_CONTROL, i);
    }

    public static void setBluetoothBTControl(Context context, int i) {
        putIntGlobal(context, Global.BLUETOOTH_BT_CONTROL, i);
    }

    public static boolean isInitialized(Context context) {
        return Secure.getSecureBoolean(context, Secure.KEY_INITIALIZED, false);
    }

    public static int getServiceEnv(Context context, int i) {
        return getIntGlobal(context, "mico_server_env", i);
    }

    public static void setServiceEnv(Context context, int i) {
        putIntGlobal(context, "mico_server_env", i);
    }

    public static void setBrightnessMode(Context context, boolean z) {
        Settings.System.putInt(context.getContentResolver(), "screen_brightness_mode", z ? 1 : 0);
    }

    public static void setRefuseUnfamiliarPhoneEnable(Context context, boolean z) {
        putBooleanGlobal(context, Global.INTERCOM_OPEN_REFUSE_UNFAMILIAR_PHONE, z);
    }

    public static boolean isRefuseUnfamiliarPhoneEnable(Context context) {
        return getBooleanGlobal(context, Global.INTERCOM_OPEN_REFUSE_UNFAMILIAR_PHONE);
    }

    public static void setMicoSmartHomeMode(Context context, int i) {
        putIntGlobal(context, Global.MICO_SMARTHOME_MODE, i);
    }

    public static int getMicoSmartHomeMode(Context context) {
        return getIntGlobal(context, Global.MICO_SMARTHOME_MODE, 0);
    }

    public static boolean setMicoHomeList(Context context, ArrayList<MicoSettingHome> arrayList) {
        return putStringGlobal(context, Global.MICO_HOME_LIST, Gsons.getGson().toJson(arrayList));
    }

    @Nullable
    public static ArrayList<MicoSettingHome> getMicoHomeList(Context context) {
        String stringGlobal = getStringGlobal(context, Global.MICO_HOME_LIST, "");
        if (!TextUtils.isEmpty(stringGlobal)) {
            return (ArrayList) Gsons.getGson().fromJson(stringGlobal, new TypeToken<ArrayList<MicoSettingHome>>() { // from class: com.xiaomi.mico.settingslib.core.MicoSettings.2
            }.getType());
        }
        return null;
    }

    public static void setFaceTrackState(Context context, int i) {
        putIntGlobal(context, Global.INTERCOM_FACE_TRACK_STATE, i);
    }

    public static int getFaceTrackState(Context context) {
        return getIntGlobal(context, Global.INTERCOM_FACE_TRACK_STATE, -1);
    }
}
