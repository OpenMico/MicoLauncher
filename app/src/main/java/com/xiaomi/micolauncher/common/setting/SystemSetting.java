package com.xiaomi.micolauncher.common.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.base.utils.ScheduleRefreshManager;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mico.settingslib.core.NightModeConfig;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.application.ScheduleKey;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.utils.SecurePrefUtils;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import com.xiaomi.micolauncher.skills.update.Version;
import com.xiaomi.micolauncher.skills.update.VersionUtil;
import com.xiaomi.miotse.MiotSecureElementManager;
import io.reactivex.Observable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

/* loaded from: classes3.dex */
public class SystemSetting {
    public static final String KEY_AUDIO_RELAY = "audio_relay";
    public static final String KEY_CHILD_CONTENT_RECOMMENDATION_ENABLE = "child_content_recommendation_enable";
    public static final String KEY_DISTANCE_PROTECTION_ENABLE = "distance_protection_enable";
    public static final String KEY_DISTANCE_PROTECTION_ENABLE_CHILDMODE = "distance_protection_enable_childmode";
    public static final String KEY_EYE_PROTECTION_MODE_ENABLE = "eye_protection_mode_enable";
    public static final String KEY_GESTURE_CONTROL = "gesture_control";
    public static final String KEY_MORNING_REMIND_ENABLE = "key_morning_remind_enable";
    public static final String KEY_MORNING_REMIND_TIME_FROM = "morning_remind_time_from";
    public static final String KEY_MORNING_REMIND_TIME_TO = "morning_remind_time_to";
    public static final String KEY_MULTI_DEVICE_SPEECH = "multi_device_speech";
    public static final String KEY_MULTI_DIALOG = "multi_dialog";
    public static final String KEY_QUICK_ENTER_CHILD_MODE_ENABLE = "key_quick_enter_child_mode_enable";
    public static final String KEY_SLEEP_MODE_CLOSE_SCREEN_SWITCHER = "sleep_mode_close_screen_switcher";
    public static final String KEY_SLEEP_MODE_TIME_FROM = "sleep_mode_time_from";
    public static final String KEY_SLEEP_MODE_TIME_SWITCHER = "sleep_mode_time_switcher";
    public static final String KEY_SLEEP_MODE_TIME_TO = "sleep_mode_time_to";
    public static final String KEY_SLEEP_MODE_VOLUME = "sleep_mode_volume";
    public static final String KEY_SLEEP_OP_MODE = "sleep_op_mode";
    public static final String KEY_SLEEP_STATUS = "sleep_status";
    public static final String KEY_WAKEUP_SOUND = "wakeup_sound";
    public static final int MAX_GET_SEVEN_VIP_DIALOG_SHOW_TIMES = 3;
    public static final int REBOOT_REASON_NORMAL = 0;
    public static final int REBOOT_REASON_OTA = 1;
    private static volatile String b;
    private static volatile String c;
    private static volatile String d;
    private static HashMap<String, UserProfile> a = new HashMap<>();
    private static int e = -1;
    private static boolean f = false;
    private static boolean g = false;

    public static void setCmccAndlinkToken(String str) {
    }

    public static void setCmccDeviceId(String str) {
    }

    public static void setCmccDeviceToken(String str) {
    }

    public static void setCmccGwAddress2(String str) {
    }

    public static void setCmccGwToken(String str) {
    }

    public static void setCmccUserPhone(String str) {
    }

    public static void setDeviceID(Context context, String str) {
    }

    public static boolean isInitializedNoCheckAccount(Context context) {
        return a(MicoSettings.Secure.KEY_INITIALIZED, context);
    }

    public static boolean isInitialized(Context context) {
        if (!f || !g) {
            return a(MicoSettings.Secure.KEY_INITIALIZED, context) && TokenManager.hasValidAccount(context);
        }
        return true;
    }

    public static void setIsInitialized() {
        g = true;
        f = true;
    }

    public static void setInitializedTimestamp(long j) {
        PreferenceUtils.setSettingLong(c(), "initialized_timestamp", j);
    }

    public static long getInitializedTimestamp() {
        return PreferenceUtils.getSettingLong(c(), "initialized_timestamp", 0L);
    }

    public static void setIntroMoviePlayed(Context context) {
        PreferenceUtils.setSettingBoolean(context, "introduction_movie_played", true);
    }

    public static boolean isIntroMoviePlayed(Context context) {
        return PreferenceUtils.getSettingBoolean(context, "introduction_movie_played", false);
    }

    public static String getDeviceID() {
        if (!TextUtils.isEmpty(b)) {
            return b;
        }
        b = a(MicoSettings.Secure.DEVICE_ID);
        L.battery.i("getDeviceID %s", b);
        return b;
    }

    public static void setDeviceName(String str) {
        PreferenceUtils.setSettingString(c(), ai.J, str);
    }

    public static String getDeviceName() {
        return PreferenceUtils.getSettingString(c(), ai.J, null);
    }

    public static void setDeviceLocation(String str) {
        PreferenceUtils.setSettingString(c(), "device_location", str);
    }

    public static String getDeviceLocation() {
        return PreferenceUtils.getSettingString(c(), "device_location", null);
    }

    public static boolean getSleepModeCloseScreenSwitcher() {
        return NightModeConfig.getSleepMode(c()).isCloseScreen();
    }

    public static void cacheNormalVolume(int i) {
        PreferenceUtils.setSettingInt(c(), "sleep_mode_normal_volume", i);
    }

    public static int getCachedNormalVolume() {
        return PreferenceUtils.getSettingInt(c(), "sleep_mode_normal_volume", -1);
    }

    public static void setContinueUpdateAfterReboot(boolean z) {
        b().edit().putBoolean("continue_update", z).apply();
    }

    public static void setRebootReason(int i) {
        PreferenceUtils.setSettingInt(c(), "reboot_reason", i);
    }

    public static int getRebootReason() {
        int i = e;
        if (i != -1) {
            return i;
        }
        e = PreferenceUtils.getSettingInt(c(), "reboot_reason", 0);
        setRebootReason(0);
        return e;
    }

    public static boolean getContinueUpdateAfterReboot() {
        return b().getBoolean("continue_update", false);
    }

    public static void setScreenOffAfterReboot(boolean z) {
        L.update.d("setScreenOffAfterReboot %s", Boolean.valueOf(z));
        b().edit().putBoolean("screen_off_after_reboot", z).apply();
    }

    public static boolean getScreenOffAfterReboot() {
        boolean z = b().getBoolean("screen_off_after_reboot", false);
        L.update.d("getScreenOffAfterReboot %s", Boolean.valueOf(z));
        return z;
    }

    private static SharedPreferences b() {
        return c().getSharedPreferences("update_pref", 0);
    }

    public static void addLocationOptions(String str) {
        Set locationOptions = getLocationOptions();
        if (locationOptions != null) {
            locationOptions.add(str);
        } else {
            locationOptions = new HashSet();
            locationOptions.add(str);
        }
        PreferenceUtils.setSettingStringSet(c(), "device_location_options", locationOptions);
    }

    public static Set<String> getLocationOptions() {
        return PreferenceUtils.getSettingStringSet(c(), "device_location_options", null);
    }

    public static void setWallpaper(int i) {
        PreferenceUtils.setSettingInt(c(), "wallpaper", i);
    }

    public static void setLockScreen(Context context, int i) {
        Logger logger = L.lockscreen;
        logger.d("SystemSetting setLockScreen id: " + i);
        MicoSettings.setLockScreenStyle(context, i);
    }

    public static void setLockScreenAdvertiser(long j) {
        MicoSettings.setLockScreenAdvertiserCode(c(), j);
    }

    public static long getLockScreenAdvertiser() {
        return MicoSettings.getLockScreenAdvertiserCode(c());
    }

    public static String getMiotDeviceId() {
        if (TextUtils.isEmpty(c)) {
            c = a(0);
        }
        return c;
    }

    public static String getMiotToken() {
        if (TextUtils.isEmpty(d)) {
            d = a(1);
        }
        return d;
    }

    private static String a(int i) {
        MiotSecureElementManager miotSecureElementManager;
        Context globalContext = MicoApplication.getGlobalContext();
        if (i < 1 && globalContext.getPackageManager().hasSystemFeature(MiotDeviceManager.SE_HARDWARE_FEATURE) && (miotSecureElementManager = (MiotSecureElementManager) globalContext.getSystemService(MiotDeviceManager.SE_ELEMENT_SERVICE)) != null && miotSecureElementManager.getDid() > 0) {
            return String.valueOf(miotSecureElementManager.getDid());
        }
        String str = SystemProperties.get("mi.did");
        L.base.i("mi.did = %s", str);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            String[] split = str.split("\\|");
            int size = ContainerUtil.getSize(split);
            if (size != 2) {
                L.base.e("mi.did format is unexpected : %s", str);
            }
            if (size == 2) {
                return split[i];
            }
            return null;
        } catch (PatternSyntaxException unused) {
            return null;
        }
    }

    public static String getMiBrainLevel() {
        return ApiConstants.getServiceEnvName();
    }

    public static String getRomChannel() {
        return SystemProperties.get("ro.mi.sw_channel");
    }

    public static void setLastSyncTime(long j) {
        PreferenceUtils.setSettingLong(c(), "last_sync_time", j);
    }

    public static long getLastSyncTime() {
        return PreferenceUtils.getSettingLong(c(), "last_sync_time", Build.TIME);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier removed */
    /* loaded from: classes3.dex */
    public static class ThemeStyle extends Enum<ThemeStyle> {
        public int getForegroundColor(Context context) {
            return context.getColor(R.color.mainscreen_dark);
        }
    }

    /* loaded from: classes3.dex */
    public static final class LockScreen extends Enum<LockScreen> {
        public static final LockScreen ALBUM;
        public static final LockScreen CLOCK_DIAL;
        public static final LockScreen CLOCK_FLIP;
        public static final LockScreen EMPTY;
        public static final LockScreen FINGER_NUMBER;
        public static final LockScreen FOREST;
        public static final LockScreen MAIN;
        public static final LockScreen SPACEMAN;
        public static final LockScreen SUMMER_NIGHT;
        public static final LockScreen TIME_DOMESTIC;
        public static final LockScreen TIME_WALL_PAPER;
        public static final LockScreen WALL_PAPER = new LockScreen("WALL_PAPER", 0, 0, R.drawable.wallpaper_pictorial_default_bg, R.drawable.thumbnail_wall_paper, R.string.lock_screen_wall_paper, R.string.lock_screen_wall_paper_subtitle, "https://cdn.cnbj1.fds.api.mi-img.com/mico/2618ca3d-813d-4194-8889-36f85890873d", R.drawable.dialmark_dongtaihuabao);
        private static final /* synthetic */ LockScreen[] a;
        private int backgroundRes;
        private int dialmarkRes;
        private int id;
        private int nameRes;
        private int settingRes;
        private int subtitleRes;
        private String url;

        public static LockScreen valueOf(String str) {
            return (LockScreen) Enum.valueOf(LockScreen.class, str);
        }

        public static LockScreen[] values() {
            return (LockScreen[]) a.clone();
        }

        static {
            ALBUM = new LockScreen("ALBUM", 1, 4, R.drawable.wallwrapper_album, R.drawable.thumbnail_album, R.string.lock_screen_picture, R.string.lock_screen_picture_subtitle, Hardware.isBigScreen() ? "https://cdn.cnbj1.fds.api.mi-img.com/mico/465e2cfb-d472-4051-b4de-2683f60aa509" : "https://cdn.cnbj1.fds.api.mi-img.com/mico/634d7ab0-b67d-4eb7-b59e-152cfc97c752", R.drawable.dialmark_album);
            TIME_WALL_PAPER = new LockScreen("TIME_WALL_PAPER", 2, 6, 0, R.drawable.thumbnail_time_wall_paper, R.string.lock_screen_time_wall_paper, R.string.lock_screen_time_wall_paper_subtitle, "https://cdn.cnbj1.fds.api.mi-img.com/mico/30256a99-3a85-45e8-8422-d7aee5cb79be", R.drawable.dialmark_tianse_shiguang);
            SUMMER_NIGHT = new LockScreen("SUMMER_NIGHT", 3, 9, 0, R.drawable.thumbnail_clock_summer_night, R.string.lock_screen_summer_night, R.string.lock_screen_empty_subtitle, Hardware.isBigScreen() ? "https://cdn.cnbj1.fds.api.mi-img.com/mico/afccf411-de84-4b8c-9975-59bb29cdc06f" : "https://cdn.cnbj1.fds.api.mi-img.com/mico/359df616-1ca8-48a8-a935-6d480db18c00", R.drawable.dialmark_summer_night);
            FOREST = new LockScreen("FOREST", 4, 10, 0, R.drawable.thumbnail_dial_forest, R.string.lock_screen_forest, R.string.lock_screen_empty_subtitle, "https://cdn.cnbj1.fds.api.mi-img.com/mico/d4f64f90-4fb1-475a-80f0-a1657baf272e", R.drawable.dialmark_fantasy_forest);
            TIME_DOMESTIC = new LockScreen("TIME_DOMESTIC", 5, 7, 0, R.drawable.thumbnail_time_domestic, R.string.lock_screen_time_domestic, R.string.lock_screen_time_domestic_subtitle, "https://cdn.cnbj1.fds.api.mi-img.com/mico/84d49baa-5f59-4999-87a5-882bc6a9ca8a");
            FINGER_NUMBER = new LockScreen("FINGER_NUMBER", 6, 13, 0, R.drawable.thumbnail_finger_number, R.string.lock_screen_finger_number, R.string.lock_screen_empty_subtitle, "https://cdn.cnbj1.fds.api.mi-img.com/mico/5295f449-0929-4181-8ecc-5a1b18b10ab9", R.drawable.dialmark_finger_number);
            SPACEMAN = new LockScreen("SPACEMAN", 7, 11, 0, R.drawable.thumbnail_dial_space_roaming, R.string.lock_screen_space_roaming, R.string.lock_screen_empty_subtitle, Hardware.isBigScreen() ? "https://cdn.cnbj1.fds.api.mi-img.com/mico/d33dbdc4-b1aa-4072-aa4b-651e21fcb69b" : "https://cdn.cnbj1.fds.api.mi-img.com/mico/bdcab992-c73a-4bd8-bc0e-cf33b1cd7801", R.drawable.dialmark_space_roaming);
            MAIN = new LockScreen("MAIN", 8, 5, R.drawable.wallpaper_time_number_default_bg, R.drawable.thumbnail_clock_0, R.string.lock_screen_main, R.string.lock_screen_main_subtitle, Hardware.isBigScreen() ? "https://cdn.cnbj1.fds.api.mi-img.com/mico/87d736b6-7148-4238-8595-b952de22dc3d" : "https://cdn.cnbj1.fds.api.mi-img.com/mico/64a559d3-fe54-4310-b096-f4c9eb63c008", R.drawable.dialmark_dongtaishuzi);
            CLOCK_FLIP = new LockScreen("CLOCK_FLIP", 9, 2, R.drawable.wallwrapper_flip, R.drawable.thumbnail_clock_flip, R.string.lock_screen_clock_flip, R.string.lock_screen_clock_flip_subtitle, "https://cdn.cnbj1.fds.api.mi-img.com/mico/4a29c5b0-50d2-4063-b6c3-6f34a828bad2", R.drawable.dialmark_fanyeshizhong);
            CLOCK_DIAL = new LockScreen("CLOCK_DIAL", 10, 1, R.drawable.wallwrapper_dial, R.drawable.thumbnail_clock_dial, R.string.lock_screen_clock_dial, R.string.lock_screen_clock_dial_subtitle, "https://cdn.cnbj1.fds.api.mi-img.com/mico/ce5f774e-5ece-4f91-a98f-65ccf0f0c132", R.drawable.dialmark_zhizhenshizhong);
            EMPTY = new LockScreen("EMPTY", 11, 3, 0, R.drawable.thumbnail_empty, R.string.lock_screen_clock_empty, R.string.lock_screen_empty_subtitle, Hardware.isBigScreen() ? "https://cdn.cnbj1.fds.api.mi-img.com/mico/185def67-7d37-416a-bf56-4ac2e980e3e6" : "https://cdn.cnbj1.fds.api.mi-img.com/mico/53d6ae9a-5112-4f7e-9f26-27c429750bfc", R.drawable.dialmark_empty);
            a = new LockScreen[]{WALL_PAPER, ALBUM, TIME_WALL_PAPER, SUMMER_NIGHT, FOREST, TIME_DOMESTIC, FINGER_NUMBER, SPACEMAN, MAIN, CLOCK_FLIP, CLOCK_DIAL, EMPTY};
        }

        private LockScreen(String str, int i, int i2, int i3, int i4, int i5, int i6, String str2, int i7) {
            super(str, i);
            this.id = i2;
            this.backgroundRes = i3;
            this.settingRes = i4;
            this.nameRes = i5;
            this.subtitleRes = i6;
            this.url = str2;
            this.dialmarkRes = i7;
        }

        private LockScreen(String str, int i, int i2, int i3, int i4, int i5, int i6, String str2) {
            super(str, i);
            this.id = i2;
            this.backgroundRes = i3;
            this.settingRes = i4;
            this.nameRes = i5;
            this.subtitleRes = i6;
            this.url = str2;
        }

        public static LockScreen valueOf(int i) {
            LockScreen[] values = values();
            for (LockScreen lockScreen : values) {
                if (lockScreen.getId() == i) {
                    return lockScreen;
                }
            }
            return null;
        }

        public int getId() {
            return this.id;
        }

        public int getBackgroundRes() {
            return this.backgroundRes;
        }

        public int getSettingRes() {
            return this.settingRes;
        }

        public int getNameRes() {
            return this.nameRes;
        }

        public int getSubtitleRes() {
            return this.subtitleRes;
        }

        public String getImageUrl() {
            return this.url;
        }

        public boolean isUserType() {
            return this.id < 100;
        }

        public int getDialmarkRes() {
            return this.dialmarkRes;
        }
    }

    /* loaded from: classes3.dex */
    public enum WallWrapper {
        digital(0, R.drawable.wallpaper_time_number_default_bg, ThemeStyle.Light),
        dial(1, R.drawable.wallwrapper_dial, ThemeStyle.Light),
        flip(2, R.drawable.wallwrapper_flip, ThemeStyle.Light);
        
        private final int id;
        private final ThemeStyle mThemeStyle;
        private final int mWallWrapperRes;

        WallWrapper(int i, int i2, ThemeStyle themeStyle) {
            this.id = i;
            this.mWallWrapperRes = i2;
            this.mThemeStyle = themeStyle;
        }

        public int getWrapperRes() {
            return this.mWallWrapperRes;
        }

        public ThemeStyle getThemeStyle() {
            return this.mThemeStyle;
        }

        public int getId() {
            return this.id;
        }

        public static WallWrapper valueOf(int i) {
            WallWrapper[] values = values();
            for (WallWrapper wallWrapper : values) {
                if (wallWrapper.getId() == i) {
                    return wallWrapper;
                }
            }
            return null;
        }
    }

    public static WallWrapper getWallpaper() {
        return WallWrapper.digital;
    }

    public static LockScreen getLockScreen() {
        int lockScreenStyle = MicoSettings.getLockScreenStyle(c());
        Logger logger = L.lockscreen;
        logger.d("SystemSetting getLockScreen lockscreen id: " + lockScreenStyle);
        LockScreen valueOf = LockScreen.valueOf(lockScreenStyle);
        return valueOf == null ? LockScreen.WALL_PAPER : valueOf;
    }

    public static int getLockScreenId() {
        int settingInt = PreferenceUtils.getSettingInt(c(), "lockscreen", LockScreen.WALL_PAPER.getId());
        return settingInt == LockScreen.WALL_PAPER.getId() ? PreferenceUtils.getSettingInt(c(), "wallpaper", WallWrapper.digital.getId()) : settingInt;
    }

    public static UserProfile getUserProfile() {
        String userId = TokenManager.getInstance().getUserId();
        if (a.containsKey(userId)) {
            return a.get(userId);
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        a.put(userId, userProfile);
        return userProfile;
    }

    public static Context c() {
        return MicoApplication.getGlobalContext();
    }

    /* loaded from: classes3.dex */
    public static class UserProfile {
        private String a;
        private volatile ThirdPartyResponse.UserCard b;
        private MusicSuorce c = MusicSuorce.NONE;

        public UserProfile setUserId(String str) {
            this.a = str;
            ScheduleRefreshManager.getInstance().start(ScheduleKey.MUSIC_SOURCE);
            return this;
        }

        public void initUserCard() {
            getUserCardObservable().subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.common.setting.-$$Lambda$SystemSetting$UserProfile$_7YR4T1WWAQAN3CwbYRcxSJ2ERM
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SystemSetting.UserProfile.this.b((ThirdPartyResponse.UserCard) obj);
                }
            }, new Consumer<Throwable>() { // from class: com.xiaomi.micolauncher.common.setting.SystemSetting.UserProfile.1
                /* renamed from: a */
                public void accept(Throwable th) throws Exception {
                    L.base.e("getUserCard  error");
                }
            });
        }

        public /* synthetic */ void b(ThirdPartyResponse.UserCard userCard) throws Exception {
            this.b = userCard;
        }

        public ThirdPartyResponse.UserCard getUserCard() {
            return this.b;
        }

        public int getCountdownCount() {
            return PreferenceUtils.getSettingInt(SystemSetting.c(), "countdown_start_count", 0);
        }

        public void increaseCountdownCount() {
            PreferenceUtils.setSettingInt(SystemSetting.c(), "countdown_start_count", getCountdownCount() + 1);
        }

        public void setCheckVersionAfterInit() {
            PreferenceUtils.setSettingBoolean(SystemSetting.c(), "key_check_version_after_init", true);
        }

        public boolean getCheckVersionAfterInit() {
            return PreferenceUtils.getSettingBoolean(SystemSetting.c(), "key_check_version_after_init", false);
        }

        public void updateVersionTimestamp() {
            Version newVersion;
            if (getUpdateVersionTimestamp() == 0 && (newVersion = RomUpdateAdapter.getInstance().getNewVersion()) != null) {
                String version = newVersion.toString();
                Context c = SystemSetting.c();
                PreferenceUtils.setSettingLong(c, "update_timestamp_" + version, System.currentTimeMillis());
            }
        }

        public long getUpdateVersionTimestamp() {
            Version newVersion = RomUpdateAdapter.getInstance().getNewVersion();
            if (newVersion == null) {
                return 0L;
            }
            String version = newVersion.toString();
            Context c = SystemSetting.c();
            return PreferenceUtils.getSettingLong(c, "update_timestamp_" + version, 0L);
        }

        public int getSerialHistory(String str) {
            Context c = SystemSetting.c();
            return PreferenceUtils.getSettingInt(c, "video_serial_record_" + str, 0);
        }

        public void setSerialHistory(String str, int i) {
            Context c = SystemSetting.c();
            PreferenceUtils.setSettingInt(c, "video_serial_record_" + str, i);
        }

        public int getVideoPlayPosition(String str, int i) {
            Context c = SystemSetting.c();
            return PreferenceUtils.getSettingInt(c, "video_play_position_" + str + "" + i, 0);
        }

        public void setVideoPlayPosition(String str, int i, int i2) {
            Context c = SystemSetting.c();
            PreferenceUtils.setSettingInt(c, "video_play_position_" + str + "" + i, i2);
        }

        public Observable<ThirdPartyResponse.UserCard> getUserCardObservable() {
            if (this.b == null) {
                return ApiManager.thirdPartyService.getUserCard(this.a).map($$Lambda$SystemSetting$UserProfile$6ykGm9jI78XIMwpS2JYgyKU7La8.INSTANCE).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.common.setting.-$$Lambda$SystemSetting$UserProfile$z7IX4uXnrPPya-S2Vg-WuKL6M_k
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        SystemSetting.UserProfile.this.a((ThirdPartyResponse.UserCard) obj);
                    }
                });
            }
            return Observable.just(this.b);
        }

        public static /* synthetic */ ThirdPartyResponse.UserCard a(ThirdPartyResponse.UserCardResponse userCardResponse) throws Exception {
            if (userCardResponse.data != null && userCardResponse.data.list.size() > 0) {
                return userCardResponse.data.list.get(0);
            }
            throw Exceptions.propagate(new Exception("invalid response"));
        }

        public /* synthetic */ void a(ThirdPartyResponse.UserCard userCard) throws Exception {
            this.b = userCard;
        }

        public MusicSuorce getMusicSource() {
            if (ScheduleRefreshManager.getInstance().shouldRefresh(ScheduleKey.MUSIC_SOURCE)) {
                a();
            }
            return this.c;
        }

        private void a() {
            ApiManager.userProfileService.getMusicSource().subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.common.setting.SystemSetting.UserProfile.2
                /* renamed from: a */
                public void onSuccess(String str) {
                    UserProfile.this.c = MusicSuorce.value(str);
                    ScheduleRefreshManager.getInstance().setRefreshed(ScheduleKey.MUSIC_SOURCE);
                }
            });
        }
    }

    public static String getRomVersion() {
        return SystemProperties.get("ro.mi.sw_ver");
    }

    public static String getHardWareModel() {
        return Hardware.getBuildModel();
    }

    public static boolean isFirstWakeup() {
        return PreferenceUtils.getSettingBoolean(c(), "wakeup_first", true);
    }

    public static void setFirstWakeup() {
        PreferenceUtils.setSettingBoolean(c(), "wakeup_first", false);
    }

    public static boolean isFirstStart() {
        return PreferenceUtils.getSettingBoolean(c(), "start_first", true);
    }

    public static void setFirstStart() {
        PreferenceUtils.setSettingBoolean(c(), "start_first", false);
    }

    public static String getLastVersionRomUpdate() {
        return PreferenceUtils.getSettingString(c(), "last_version_rom_update", "");
    }

    public static void setLastVersionRomUpdate(String str) {
        PreferenceUtils.setSettingString(c(), "last_version_rom_update", str);
    }

    public static boolean getMicMute() {
        return PreferenceUtils.getSettingBoolean(c(), "mic_mute", false);
    }

    public static void setMicMute(boolean z) {
        PreferenceUtils.setSettingBoolean(c(), "mic_mute", z);
    }

    public static void setMultiDialog(boolean z) {
        MicoSettings.setMultiDialogEnable(c(), z);
    }

    public static boolean getMultiDialog() {
        return MicoSettings.isMultiDialogEnable(c());
    }

    public static void setDuplexSpeechOpenTimeStamp() {
        PreferenceUtils.setSettingLong(c(), "multi_dialog_ts", System.currentTimeMillis());
    }

    public static long getDuplexSpeechOpenTimeStamp() {
        return PreferenceUtils.getSettingLong(c(), "multi_dialog_ts", System.currentTimeMillis());
    }

    public static void setNearbyWakeup(boolean z) {
        Logger logger = L.update;
        logger.d("setNearbyWakeup " + z);
        MicoSettings.setNearbyWakeupEnable(c(), z);
    }

    public static boolean getNearbyWakeup() {
        return MicoSettings.isNearbyWakeupEnable(c());
    }

    /* loaded from: classes3.dex */
    public enum MusicSuorce {
        MI(HomePageUtils.MUSIC_SOURCE_MI),
        QQ("QQ"),
        KKBOX("KKBOX"),
        NONE("NONE");
        
        private String source;

        MusicSuorce(String str) {
            this.source = str;
        }

        public String getSource() {
            return this.source;
        }

        public static MusicSuorce value(String str) {
            MusicSuorce[] values = values();
            for (MusicSuorce musicSuorce : values) {
                if (musicSuorce.source.equalsIgnoreCase(str)) {
                    return musicSuorce;
                }
            }
            return NONE;
        }
    }

    public static boolean getHasPopUpAppUdateReminderDialogue() {
        return PreferenceUtils.getSettingBoolean(c(), "voip_has_popup_app_update_dialogue", false);
    }

    public static void setHasPopUpAppUdateReminderDialogue(boolean z) {
        PreferenceUtils.setSettingBoolean(c(), "voip_has_popup_app_update_dialogue", z);
    }

    public static String getCmccUserKey() {
        return a("cmcc_user_key");
    }

    public static String getCmccGwAddress() {
        return a("cmcc_gw_address");
    }

    public static String getCmccGwAddress2() {
        return a("cmcc_gw_address2");
    }

    public static String getCmccUserPhone() {
        return a("cmcc_user_phone");
    }

    public static String getCmccDeviceId() {
        return a("cmcc_device_id");
    }

    public static String getCmccDeviceIdPublic() {
        return PreferenceUtils.getSettingString(c(), "cmcc_device_id_public", "");
    }

    public static void setCmccDeviceIdPublic(String str) {
        PreferenceUtils.setSettingString(c(), "cmcc_device_id_public", str);
    }

    public static String getCmccAndlinkToken() {
        return a("cmcc_andlink_token");
    }

    public static String getCmccGwToken() {
        return a("cmcc_gw_token");
    }

    public static String getCmccDeviceToken() {
        return a("cmcc_device_token");
    }

    public static int getKeyMeshEnable() {
        return PreferenceUtils.getSettingInt(c(), "mesh_enable", -1);
    }

    public static void setKeyMeshEnable(int i) {
        PreferenceUtils.setSettingInt(c(), "mesh_enable", i);
    }

    public static boolean getKeyDistanceProtectionEnable() {
        return MicoSettings.isDistanceProtectionEnable(c());
    }

    public static void setKeyDistanceProtectionEnable(boolean z) {
        MicoSettings.setDistanceProtectionEnable(c(), z);
    }

    public static boolean getKeyDistanceProtectionEnableChildMode() {
        return MicoSettings.isDistanceProtectionEnableChildMode(c());
    }

    public static void setKeyDistanceProtectionEnableChildMode(boolean z) {
        MicoSettings.setDistanceProtectionEnableChildMode(c(), z);
    }

    public static boolean getKeyEyeProtectionModeEnable() {
        return MicoSettings.isEyeProtectionEnable(c());
    }

    public static void setKeyQuickEnterChildModeEnable(boolean z) {
        MicoSettings.setQuickEnterChildModeEnable(c(), true);
    }

    public static boolean getKeyQuickEnterChildModeEnable() {
        return MicoSettings.isQuickEnterChildModeEnable(c());
    }

    public static void setKeyHasShowQuickEnterChildDialog(boolean z) {
        PreferenceUtils.setSettingBoolean(c(), "key_has_show_quick_enter_child_dialog", z);
    }

    public static boolean getKeyHasShowQuickEnterChildDialog() {
        return PreferenceUtils.getSettingBoolean(c(), "key_has_show_quick_enter_child_dialog", false);
    }

    public static void setKeyIsTempChild(boolean z) {
        PreferenceUtils.setSettingBoolean(c(), "key_is_temp_child", z);
    }

    public static boolean getKeyIsTempChild() {
        return PreferenceUtils.getSettingBoolean(c(), "key_is_temp_child", false);
    }

    public static void setKeyGetSevenVipDialogShowTimes(int i) {
        Context c2 = c();
        PreferenceUtils.setSettingInt(c2, "KEY_GET_SEVEN_VIP_DIALOG_SHOW_TIMES" + TokenManager.getInstance().getUserId(), i);
    }

    public static void setKeySelectCourseTabIndex(int i) {
        PreferenceUtils.setSettingInt(c(), "KEY_SELECT_COURSE_TAB_INDEX", i);
    }

    public static int getKeySelectCourseTabIndex() {
        return PreferenceUtils.getSettingInt(c(), "KEY_SELECT_COURSE_TAB_INDEX", 0);
    }

    public static void setKeyGetSevenVipDialogShowTimesIncrease() {
        setKeyGetSevenVipDialogShowTimes(getKeyGetSevenVipDialogShowTimes() + 1);
    }

    public static void setKeyGetSevenVipDialogShowTimesMax() {
        setKeyGetSevenVipDialogShowTimes(getKeyGetSevenVipDialogShowTimes() + 3);
    }

    public static int getKeyGetSevenVipDialogShowTimes() {
        Context c2 = c();
        return PreferenceUtils.getSettingInt(c2, "KEY_GET_SEVEN_VIP_DIALOG_SHOW_TIMES" + TokenManager.getInstance().getUserId(), 0);
    }

    public static void setKeyMiTvVipStatus(boolean z) {
        PreferenceUtils.setSettingBoolean(c(), "KEY_MI_TV_VIP_STATUScom.gitv.xiaomiertong.vip", z);
    }

    public static boolean getKeyMiTvVipStatus() {
        return PreferenceUtils.getSettingBoolean(c(), "KEY_MI_TV_VIP_STATUScom.gitv.xiaomiertong.vip", false);
    }

    public static boolean getSevenVipDialogShowTimesNotEnough() {
        return getKeyGetSevenVipDialogShowTimes() < 3;
    }

    public static void setKeyHasPurchasedVip(boolean z) {
        Context c2 = c();
        PreferenceUtils.setSettingBoolean(c2, "key_has_purchased_vip" + TokenManager.getInstance().getUserId(), z);
    }

    public static boolean getKeyHasPurchasedVip() {
        Context c2 = c();
        return PreferenceUtils.getSettingBoolean(c2, "key_has_purchased_vip" + TokenManager.getInstance().getUserId(), false);
    }

    public static void setKeyEyeProtectionModeEnable(boolean z) {
        MicoSettings.setEyeProtectionEnable(c(), z);
    }

    public static boolean getWakeupSoundEnable() {
        return MicoSettings.isWakeupSoundEnable(c(), true);
    }

    public static void setWakeupSoundEnable(boolean z) {
        MicoSettings.setWakeupSoundEnable(c(), z);
    }

    public static void setTtsVendor(String str) {
        PreferenceUtils.setSettingString(c(), "tts_vendor", str);
    }

    public static String getTtsVendor(String str) {
        return PreferenceUtils.getSettingString(c(), "tts_vendor", str);
    }

    public static boolean getKeyGestureControlEnable() {
        return MicoSettings.isGestureControlEnable(c());
    }

    private static String a(String str) {
        String secureString = SecurePrefUtils.getSecureString(c(), str);
        if (!TextUtils.isEmpty(secureString)) {
            return secureString;
        }
        String settingString = PreferenceUtils.getSettingString(c(), str, "");
        SecurePrefUtils.putSecureString(c(), str, settingString);
        return settingString;
    }

    private static boolean a(String str, Context context) {
        try {
            return SecurePrefUtils.getSecureInt(context, str) == 1;
        } catch (Settings.SettingNotFoundException unused) {
            boolean settingBoolean = PreferenceUtils.getSettingBoolean(context, str, false);
            SecurePrefUtils.putSecureInt(context, str, settingBoolean ? 1 : 0);
            return settingBoolean;
        }
    }

    public static void setKeyChildContentRecommendationEnable(boolean z) {
        MicoSettings.setChildContentRecommendationEnable(c(), z);
    }

    public static boolean getKeyChildContentRecommendationEnable() {
        return MicoSettings.isChildContentRecommendationEnable(c());
    }

    public static boolean getKeyAudioRelayEnable() {
        return MicoSettings.getAudioRelay(c());
    }

    public static boolean needGestureFunction(Context context) {
        if (VersionUtil.isDailyVersion()) {
            return true;
        }
        return (VersionUtil.isDevVersion() && Hardware.isX08A(context)) || Hardware.isX08C();
    }

    public static void setHourlyChimeEnable(boolean z) {
        PreferenceUtils.setSettingBoolean(c(), "key_hourly_chime_enable", z);
    }

    public static boolean getHourlyChimeEnable() {
        return PreferenceUtils.getSettingBoolean(c(), "key_hourly_chime_enable", false);
    }
}
