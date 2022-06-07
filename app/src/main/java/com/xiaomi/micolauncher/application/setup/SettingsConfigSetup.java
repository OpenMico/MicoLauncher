package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.ChildModeConfig;
import com.xiaomi.mico.settingslib.core.IntercomDeviceConfig;
import com.xiaomi.mico.settingslib.core.MicoSettingChangeListener;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mico.settingslib.core.MicoSettingsAction;
import com.xiaomi.mico.settingslib.core.MicoSettingsManager;
import com.xiaomi.mico.settingslib.core.MorningRemindConfig;
import com.xiaomi.mico.settingslib.core.NightModeConfig;
import com.xiaomi.mico.settingslib.lock.LockApp;
import com.xiaomi.mico.settingslib.lock.LockSetting;
import com.xiaomi.mico.settingslib.wifi.BlackListWifiStore;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEngineHelper;
import com.xiaomi.micolauncher.common.ubus.storage.ChildModeStorage;
import com.xiaomi.micolauncher.module.battery.EnergySaver;
import com.xiaomi.micolauncher.module.camera2.action.Camera2ControlAction;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraRelatedSwitchEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraSingleSwitchEvent;
import com.xiaomi.micolauncher.module.intercom.BaseIntercomResponse;
import com.xiaomi.micolauncher.module.intercom.DeviceConfigIntercom;
import com.xiaomi.micolauncher.module.intercom.IntercomSettings;
import com.xiaomi.micolauncher.module.intercom.PushIntercom;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothAudioRelayManager;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager;
import com.xiaomi.micolauncher.module.setting.utils.FullDuplexSpeechHelper;
import com.xiaomi.micolauncher.module.setting.utils.SleepMode;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeUtils;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.BluetoothControlAction;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.Map;

/* loaded from: classes3.dex */
public class SettingsConfigSetup extends AbsSyncSetup {
    private Disposable a;
    private Disposable b;

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        MicoSettings.setBluetoothEnable(context, false);
        a(context);
        if (!SystemSetting.isInitialized(context)) {
            L.base.i("SettingsConfigSetup setSynchSettings true is not Initialized");
        } else if (MicoSettings.isSynchSettings(context)) {
            ChildModeStorage.getInstance().load(context);
            L.base.i("SettingsConfigSetup isSynchSettings");
        } else {
            if (TextUtils.isEmpty(MicoSettings.getDeviceId(context))) {
                MicoSettings.setDeviceId(context, SystemSetting.getDeviceID());
            }
            ChildModeConfig.saveChildModeConfig(context, ChildModeStorage.getInstance().loadFromPref(context));
            MicoSettings.setQuickEnterChildModeEnable(context, getKeyQuickEnterChildModeEnable());
            MicoSettings.setDistanceProtectionEnable(context, getKeyDistanceProtectionEnable());
            MicoSettings.setDistanceProtectionEnableChildMode(context, getKeyDistanceProtectionEnableChildMode());
            MicoSettings.setEyeProtectionEnable(context, getKeyEyeProtectionModeEnable());
            MicoSettings.setChildContentRecommendationEnable(context, getKeyChildContentRecommendationEnable());
            if (MicoSettings.getMorningRemind(context) == null) {
                MorningRemindConfig morningRemindConfig = new MorningRemindConfig();
                morningRemindConfig.setEnable(getKeyMorningRemindEnable());
                morningRemindConfig.setTimeStart(getMorningRemindTimeStart());
                morningRemindConfig.setTimeEnd(getMorningRemindTimeEnd());
                MorningRemindConfig.saveMorningRemind(context, morningRemindConfig);
            }
            MicoSettings.setGestureControlEnable(context, getKeyGestureControlEnable());
            if (MicoSettings.getLockScreenStyle(context, -1) == -1) {
                MicoSettings.setLockScreenStyle(context, SystemSetting.getLockScreenId());
            }
            MicoSettings.setAutoScreenOffTimeIndex(context, getAutoScreenOffTimeKey());
            MicoSettings.setMultiDialogEnable(context, getMultiDialog());
            MicoSettings.setNearbyWakeupEnable(context, getNearbyWakeup());
            if (MicoSettings.getSleepMode(context) == null) {
                NightModeConfig nightModeConfig = new NightModeConfig();
                nightModeConfig.setEnable(getSleepStatus());
                nightModeConfig.setTimeStart(getSleepModeTimeStart());
                nightModeConfig.setTimeEnd(getSleepModeTimeEnd());
                nightModeConfig.setAuto(getSleepOpMode());
                nightModeConfig.setTimeSleep(getSleepModeTimeSwitcher());
                nightModeConfig.setCloseScreen(getSleepModeCloseScreenSwitcher());
                nightModeConfig.setVolume(getSleepModeVolume());
                NightModeConfig.saveSleepMode(context, nightModeConfig);
                SleepMode.getInstance().onConfigChange(nightModeConfig);
            }
            MicoSettings.setWakeupSoundEnable(context, getWakeupSoundEnable());
            MicoSettings.setAudioRelay(context, getKeyAudioRelayEnable());
            MicoSettings.setAppLockPass(context, getLockPass(context));
            LockApp[] values = LockApp.values();
            for (LockApp lockApp : values) {
                LockSetting.setLock(context, lockApp.getId(), a(context, lockApp.getId()));
            }
            b(context);
            MicoSettings.setSynchSettings(context, true);
            ChildModeStorage.getInstance().load(context);
        }
    }

    private void a(final Context context) {
        MicoSettingsManager.getInstance().register(context.getApplicationContext(), new MicoSettingChangeListener() { // from class: com.xiaomi.micolauncher.application.setup.-$$Lambda$SettingsConfigSetup$XoZqn_r9vV1X8iMCDWcSiRVHrF4
            @Override // com.xiaomi.mico.settingslib.core.MicoSettingChangeListener
            public final void onConfigChange(boolean z, String str) {
                SettingsConfigSetup.this.a(context, z, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public /* synthetic */ void a(Context context, boolean z, String str) {
        L.base.i("onConfigChange=%s", str);
        String lastPathSegment = Uri.parse(str).getLastPathSegment();
        if (!TextUtils.isEmpty(lastPathSegment)) {
            char c = 65535;
            switch (lastPathSegment.hashCode()) {
                case -1923615003:
                    if (lastPathSegment.equals(MicoSettings.Global.MULTI_DIALOG)) {
                        c = '\r';
                        break;
                    }
                    break;
                case -1398703824:
                    if (lastPathSegment.equals(MicoSettings.Global.GESTURE_CONTROL)) {
                        c = '\f';
                        break;
                    }
                    break;
                case -1103887102:
                    if (lastPathSegment.equals(MicoSettings.Global.SLEEP_MODE)) {
                        c = 7;
                        break;
                    }
                    break;
                case -872175463:
                    if (lastPathSegment.equals(MicoSettings.Global.CHILD_CONTENT_RECOMMENDATION)) {
                        c = 5;
                        break;
                    }
                    break;
                case -796383828:
                    if (lastPathSegment.equals(MicoSettings.Global.DISTANCE_PROTECTION)) {
                        c = 1;
                        break;
                    }
                    break;
                case -646683821:
                    if (lastPathSegment.equals(MicoSettings.Global.DISTANCE_PROTECTION_CHILD_MODE)) {
                        c = 2;
                        break;
                    }
                    break;
                case -490241188:
                    if (lastPathSegment.equals(MicoSettings.Global.MORNING_REMIND)) {
                        c = 6;
                        break;
                    }
                    break;
                case -429724202:
                    if (lastPathSegment.equals(MicoSettings.Global.QUICK_ENTER_CHILD_MODE)) {
                        c = 3;
                        break;
                    }
                    break;
                case -301876250:
                    if (lastPathSegment.equals(MicoSettings.Global.WAKEUP_SOUND)) {
                        c = '\b';
                        break;
                    }
                    break;
                case 179784829:
                    if (lastPathSegment.equals(MicoSettings.Global.CHILD_MODE)) {
                        c = 0;
                        break;
                    }
                    break;
                case 217519304:
                    if (lastPathSegment.equals(MicoSettings.Global.INTERCOM_DEVICE_CONFIG)) {
                        c = 11;
                        break;
                    }
                    break;
                case 783632616:
                    if (lastPathSegment.equals("audio_relay")) {
                        c = 14;
                        break;
                    }
                    break;
                case 882035532:
                    if (lastPathSegment.equals(MicoSettings.Global.CURRENT_BLUETOOTH_DEVICE_OPERATION)) {
                        c = 16;
                        break;
                    }
                    break;
                case 1064497540:
                    if (lastPathSegment.equals(MicoSettings.Global.EYE_PROTECTION_MODE)) {
                        c = 4;
                        break;
                    }
                    break;
                case 1445482635:
                    if (lastPathSegment.equals(MicoSettings.Global.BLUETOOTH_ENABLE)) {
                        c = 15;
                        break;
                    }
                    break;
                case 1577465943:
                    if (lastPathSegment.equals(MicoSettings.Global.AUTO_SCREEN_OFF_TIME)) {
                        c = '\n';
                        break;
                    }
                    break;
                case 1842943817:
                    if (lastPathSegment.equals(MicoSettings.Global.LOCK_SCREEN_WALL_PAPER)) {
                        c = '\t';
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    ChildModeConfig childModeConfig = ChildModeConfig.getChildModeConfig(context);
                    ChildModeStorage.getInstance().onConfigChange(a(), childModeConfig, false);
                    ChildModeManager.getManager().setChildModeConfig(childModeConfig);
                    return;
                case 1:
                    if (!MicoSettings.isDistanceProtectionEnable(a())) {
                        EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, false));
                        return;
                    } else if (!MicoSettings.isDistanceProtectionEnableChildMode(a())) {
                        EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, true));
                        return;
                    } else if (ChildModeManager.getManager().isChildMode()) {
                        EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, true));
                        return;
                    } else {
                        return;
                    }
                case 2:
                    boolean isDistanceProtectionEnableChildMode = MicoSettings.isDistanceProtectionEnableChildMode(a());
                    if (!MicoSettings.isDistanceProtectionEnable(a())) {
                        return;
                    }
                    if (!isDistanceProtectionEnableChildMode) {
                        EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, true));
                        return;
                    } else if (!ChildModeManager.getManager().isChildMode()) {
                        EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, false));
                        return;
                    } else {
                        return;
                    }
                case 3:
                    boolean isQuickEnterChildModeEnable = MicoSettings.isQuickEnterChildModeEnable(a());
                    L.base.i("onConfigChange QUICK_ENTER_CHILD_MODE=%s", Boolean.valueOf(isQuickEnterChildModeEnable));
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_ENTER_CHILD_MODE, isQuickEnterChildModeEnable));
                    return;
                case 4:
                    boolean isEyeProtectionEnable = MicoSettings.isEyeProtectionEnable(a());
                    L.base.i("onConfigChange EYE_PROTECTION_MODE=%s", Boolean.valueOf(isEyeProtectionEnable));
                    ChildModeUtils.setEyeProtectionMode(isEyeProtectionEnable);
                    return;
                case 5:
                    boolean isChildContentRecommendationEnable = MicoSettings.isChildContentRecommendationEnable(a());
                    L.base.i("onConfigChange CHILD_CONTENT_RECOMMENDATION=%s", Boolean.valueOf(isChildContentRecommendationEnable));
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_CHILD_CONTENT_RECOMMENDATION, isChildContentRecommendationEnable));
                    return;
                case 6:
                case '\t':
                default:
                    return;
                case 7:
                    SleepMode.getInstance().onConfigChange(NightModeConfig.getSleepMode(a()));
                    return;
                case '\b':
                    boolean isWakeupSoundEnable = MicoSettings.isWakeupSoundEnable(a(), true);
                    L.base.i("onConfigChange WAKEUP_SOUND=%s", Boolean.valueOf(isWakeupSoundEnable));
                    SpeechConfig.setWakeupSound(isWakeupSoundEnable);
                    return;
                case '\n':
                    if (EnergySaver.getInstance(context).isBatteryMode()) {
                        L.battery.d("SettingsConfigSetup scheduleScreenOff isLowBattery=false");
                        EnergySaver.getInstance(context).scheduleScreenOff(false);
                        return;
                    }
                    return;
                case 11:
                    IntercomDeviceConfig intercomDeviceConfig = MicoSettings.getIntercomDeviceConfig(a());
                    if (intercomDeviceConfig.action == 0) {
                        c(a());
                        return;
                    } else if (intercomDeviceConfig.action == 1) {
                        a(a(), intercomDeviceConfig);
                        return;
                    } else {
                        return;
                    }
                case '\f':
                    boolean isGestureControlEnable = MicoSettings.isGestureControlEnable(a());
                    L.base.i("onConfigChange GESTURE_CONTROL=%s", Boolean.valueOf(isGestureControlEnable));
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_GESTURE, isGestureControlEnable));
                    if (!isGestureControlEnable && !MicoSettings.isQuickEnterChildModeEnable(a()) && !MicoSettings.isDistanceProtectionEnable(a())) {
                        EventBusRegistry.getEventBus().post(new CameraRelatedSwitchEvent(CameraRelatedSwitchEvent.event.CLOSE));
                        return;
                    }
                    return;
                case '\r':
                    FullDuplexSpeechHelper.onEnableChange(MicoSettings.isMultiDialogEnable(a()), 1);
                    return;
                case 14:
                    boolean audioRelay = MicoSettings.getAudioRelay(a());
                    if (BluetoothAudioRelayManager.getInstance() == null) {
                        L.base.i("BluetoothAudioRelayManager is null");
                        return;
                    } else if (audioRelay) {
                        BluetoothAudioRelayManager.getInstance().start();
                        return;
                    } else {
                        BluetoothAudioRelayManager.getInstance().destroy();
                        return;
                    }
                case 15:
                    if (MicoSettings.isBluetoothEnable(context)) {
                        BluetoothSettingManager.getInstance().openBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_BT_BUTTON);
                        return;
                    } else {
                        BluetoothSettingManager.getInstance().closeBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_BT_BUTTON);
                        return;
                    }
                case 16:
                    String currentBluetoothDeviceOperation = MicoSettings.getCurrentBluetoothDeviceOperation(context);
                    if (TextUtils.isEmpty(currentBluetoothDeviceOperation) || BluetoothSettingManager.getInstance() == null) {
                        return;
                    }
                    if (currentBluetoothDeviceOperation.startsWith(MicoSettingsAction.BLUETOOTH_DISCONNECT)) {
                        BluetoothSettingManager.getInstance().disconnectBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_DISCONNECT_BY_ITEM_CLICK);
                        return;
                    } else if (currentBluetoothDeviceOperation.startsWith("connect")) {
                        String[] split = currentBluetoothDeviceOperation.split("_");
                        if (split.length >= 3) {
                            BluetoothSettingManager.getInstance().connectBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_ITEM_CLICK, BluetoothSettingManager.getInstance().getRemoteDeviceByMac(split[1]));
                            return;
                        }
                        return;
                    } else if (currentBluetoothDeviceOperation.startsWith(MicoSettingsAction.BLUETOOTH_REMOVE_BONDED)) {
                        String[] split2 = currentBluetoothDeviceOperation.split("_");
                        if (split2.length >= 3) {
                            BluetoothSettingManager.getInstance().removeBonded(BluetoothControlAction.EnumAction.ACTION_REQUEST_REMOVE_BONDED_BY_ITEM_CLICK, BluetoothSettingManager.getInstance().getRemoteDeviceByMac(split2[1]));
                            return;
                        }
                        return;
                    } else {
                        return;
                    }
            }
        }
    }

    private static Context a() {
        return MicoApplication.getGlobalContext();
    }

    private static boolean a(Context context, String str) {
        return PreferenceUtils.getSettingBoolean(context, str, false);
    }

    public static String getLockPass(Context context) {
        return PreferenceUtils.getSettingString(context, "security_pass_key", "");
    }

    public static boolean getKeyQuickEnterChildModeEnable() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_QUICK_ENTER_CHILD_MODE_ENABLE, false);
    }

    public static boolean getKeyDistanceProtectionEnable() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_DISTANCE_PROTECTION_ENABLE, false);
    }

    public static boolean getKeyDistanceProtectionEnableChildMode() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_DISTANCE_PROTECTION_ENABLE_CHILDMODE, false);
    }

    public static boolean getKeyEyeProtectionModeEnable() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_EYE_PROTECTION_MODE_ENABLE, false);
    }

    public static boolean getKeyChildContentRecommendationEnable() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_CHILD_CONTENT_RECOMMENDATION_ENABLE, false);
    }

    public static boolean getKeyGestureControlEnable() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_GESTURE_CONTROL, false);
    }

    public static boolean getWakeupSoundEnable() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_WAKEUP_SOUND, true);
    }

    public static boolean getMultiDialog() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_MULTI_DIALOG, false);
    }

    public static boolean getNearbyWakeup() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_MULTI_DEVICE_SPEECH, true);
    }

    public static boolean getKeyMorningRemindEnable() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_MORNING_REMIND_ENABLE, false);
    }

    public static int getMorningRemindTimeStart() {
        return PreferenceUtils.getSettingInt(a(), SystemSetting.KEY_MORNING_REMIND_TIME_FROM, 420);
    }

    public static int getMorningRemindTimeEnd() {
        return PreferenceUtils.getSettingInt(a(), SystemSetting.KEY_MORNING_REMIND_TIME_TO, 660);
    }

    public static boolean getSleepStatus() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_SLEEP_STATUS, false);
    }

    public static int getSleepModeTimeStart() {
        return PreferenceUtils.getSettingInt(a(), SystemSetting.KEY_SLEEP_MODE_TIME_FROM, 1380);
    }

    public static int getSleepModeTimeEnd() {
        return PreferenceUtils.getSettingInt(a(), SystemSetting.KEY_SLEEP_MODE_TIME_TO, 360);
    }

    public static boolean getSleepOpMode() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_SLEEP_OP_MODE, false);
    }

    public static boolean getSleepModeTimeSwitcher() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_SLEEP_MODE_TIME_SWITCHER, false);
    }

    public static boolean getSleepModeCloseScreenSwitcher() {
        return PreferenceUtils.getSettingBoolean(a(), SystemSetting.KEY_SLEEP_MODE_CLOSE_SCREEN_SWITCHER, false);
    }

    public static int getSleepModeVolume() {
        int i = Hardware.isBigScreen() ? 20 : 25;
        NightModeConfig sleepMode = NightModeConfig.getSleepMode(a());
        return sleepMode.getVolume() <= 0 ? i : sleepMode.getVolume();
    }

    public static int getAutoScreenOffTimeKey() {
        return PreferenceUtils.getSettingInt(a(), "auto_screen_off_time_key", 1);
    }

    public static boolean getKeyAudioRelayEnable() {
        return PreferenceUtils.getSettingBoolean(a(), "audio_relay", false);
    }

    private void b(Context context) {
        Map<String, ?> all = context.getSharedPreferences("WifiBlackList", 0).getAll();
        if (ContainerUtil.hasData(all)) {
            BlackListWifiStore.addAll(context, all);
        }
    }

    private void c(final Context context) {
        Disposable disposable = this.a;
        if (disposable != null) {
            disposable.dispose();
        }
        this.a = DeviceConfigIntercom.getInstance().getIntercomSettings(context).retry(2L).flatMap(new Function() { // from class: com.xiaomi.micolauncher.application.setup.-$$Lambda$SettingsConfigSetup$JsqUMQ4lRXW0yC9QKnsMWRZWEcs
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a;
                a = SettingsConfigSetup.a(context, (BaseIntercomResponse) obj);
                return a;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$SettingsConfigSetup$5jQNwxPJQ1pfQZbfnZ3MUrofuRA.INSTANCE, $$Lambda$SettingsConfigSetup$CIad5EgJZfOtD_EAbMCd3Ij8WX0.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(Context context, BaseIntercomResponse baseIntercomResponse) throws Exception {
        L.push.d("SettingsConfigSetup getIntercomSettings from cloud success, and update to MicoSettings");
        IntercomSettings intercomSettings = (IntercomSettings) baseIntercomResponse.data;
        PreferenceUtils.setSettingString(context, DeviceConfigIntercom.SP_INTERCOM_SETTINGS, Gsons.getGson().toJson(intercomSettings));
        MicoSettings.setIntercomDeviceConfig(context, new IntercomDeviceConfig(intercomSettings.current_device.support_intercom, intercomSettings.night_mode_no_disturbing, -1, System.currentTimeMillis()));
        return Observable.just(intercomSettings);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(IntercomSettings intercomSettings) throws Exception {
        PushIntercom.getInstance().setNightModeNoDisturbing(intercomSettings.night_mode_no_disturbing);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.push.e("SettingsConfigSetup getIntercomSettings onError", th);
    }

    private void a(Context context, IntercomDeviceConfig intercomDeviceConfig) {
        Disposable disposable = this.b;
        if (disposable != null) {
            disposable.dispose();
        }
        IntercomSettings.CurrentDevice currentDevice = new IntercomSettings.CurrentDevice(SpeechEngineHelper.getAiDeviceId(), intercomDeviceConfig.support_intercom);
        String settingString = PreferenceUtils.getSettingString(context, DeviceConfigIntercom.SP_INTERCOM_SETTINGS, "");
        if (!TextUtils.isEmpty(settingString)) {
            final IntercomSettings intercomSettings = (IntercomSettings) Gsons.getGson().fromJson(settingString, (Class<Object>) IntercomSettings.class);
            intercomSettings.night_mode_no_disturbing = intercomDeviceConfig.night_mode_no_disturbing;
            intercomSettings.current_device.support_intercom = currentDevice.support_intercom;
            this.b = DeviceConfigIntercom.getInstance().uploadIntercomSettings(Gsons.getGson().toJson(intercomSettings)).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.application.setup.-$$Lambda$SettingsConfigSetup$Y6Jqogg0bU6_jVyCrorshBy5J-0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SettingsConfigSetup.a(IntercomSettings.this, (BaseIntercomResponse) obj);
                }
            }, $$Lambda$SettingsConfigSetup$LmeLWMG97NQMqZUK6UtVe5cFgmU.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(IntercomSettings intercomSettings, BaseIntercomResponse baseIntercomResponse) throws Exception {
        PushIntercom.getInstance().setNightModeNoDisturbing(intercomSettings.night_mode_no_disturbing);
        L.push.i("SettingsConfigSetup uploadIntercomSettings success");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.push.e("SettingsConfigSetup uploadIntercomSettings onError", th);
    }
}
