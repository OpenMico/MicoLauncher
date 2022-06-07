package com.xiaomi.micolauncher.skills.update;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.systemui.SystemUIManager;
import com.xiaomi.micolauncher.common.ubus.UbusManager;
import com.xiaomi.micolauncher.module.QuickSettingHelper;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.countdown.model.CountDownModel;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.BluetoothControlAction;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public class RomUpdateAdapter {
    public static final String ACTION_CANCEL_UPDATE = "com.xiaomi.mico.romupdate.action.CANCEL_UPDATE";
    public static final String ACTION_CHECK_UPDATE = "com.xiaomi.mico.romupdate.action.CHECK_UPDATE";
    public static final String ACTION_CHECK_UPDATE_RESULT = "com.xiaomi.mico.romupdate.check_update_result";
    public static final String ACTION_ROM_UPDATE_EVENT = "com.xiaomi.mico.romupdate.event";
    public static final String CHECK_UPDATE_ACTIVITY_NAME = "com.xiaomi.mico.romupdate.CheckUpdateActivity";
    public static final int ERROR_CHECK_UPDATE_FAILED = -1;
    public static final int ERROR_NO_ERROR = 0;
    public static final String EXTRA_FORCE_UPDATE_INFO = "com.xiaomi.mico.romupdate.extra.FORCE_UPDATE_INFO";
    public static final String EXTRA_NEW_VERSION_NAME = "new_version_name";
    public static final String EXTRA_ROM_UPDATE_ERROR = "rom_update_error";
    public static final String EXTRA_ROM_UPDATE_EVENT_TYPE = "rom_update_event_type";
    public static final String EXTRA_ROM_UPDATE_SILENT = "rom_update_silent";
    public static final String EXTRA_ROM_UPDATE_STATE = "rom_update_state";
    public static final String ROM_UPDATE_ACTIVITY_NAME = "com.xiaomi.mico.romupdate.RomUpdateActivity";
    public static final String ROM_UPDATE_PACKAGE_NAME = "com.xiaomi.mico.romupdate";
    private Context a;
    private String b;
    private Version c;
    private Version d;
    private String e;
    private boolean f;
    private volatile long g;
    private RomUpdateState h;
    private int i;
    private boolean j;
    private boolean k;
    private BroadcastReceiver l;

    /* loaded from: classes3.dex */
    public enum RomUpdateEventType {
        CheckingUpdateStarted,
        CheckingUpdateCompleted,
        UpdatingStarted,
        UpdatingStateChanged,
        UpdatingCompleted,
        UpdatingFailed,
        CancellingUpdateStarted,
        CancellingUpdateCompleted,
        RebootStarted
    }

    /* loaded from: classes3.dex */
    public enum RomUpdateState {
        Idle,
        CheckingUpdate,
        StartingUpdate,
        Downloading,
        Verifying,
        Finalizing,
        CancellingUpdate,
        Rebooting,
        Disabled
    }

    @SuppressLint({"StaticFieldLeak"})
    /* loaded from: classes3.dex */
    public static class a {
        private static final RomUpdateAdapter a = new RomUpdateAdapter();
    }

    public static void init(Context context) {
        getInstance().a(context);
    }

    public static RomUpdateAdapter getInstance() {
        return a.a;
    }

    private RomUpdateAdapter() {
        this.h = RomUpdateState.Idle;
        this.l = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.skills.update.RomUpdateAdapter.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Logger logger = L.update;
                logger.d("romUpdateReceiver: " + action + " is received.");
                if (action != null && action.equals(RomUpdateAdapter.ACTION_ROM_UPDATE_EVENT)) {
                    int intExtra = intent.getIntExtra(RomUpdateAdapter.EXTRA_ROM_UPDATE_EVENT_TYPE, -1);
                    if (intExtra == -1) {
                        L.update.e("Invalid ordinal of event type.");
                        return;
                    }
                    RomUpdateEventType romUpdateEventType = RomUpdateEventType.values()[intExtra];
                    Logger logger2 = L.update;
                    logger2.d("rom_update_event_type is " + romUpdateEventType);
                    int intExtra2 = intent.getIntExtra(RomUpdateAdapter.EXTRA_ROM_UPDATE_STATE, -1);
                    if (intExtra2 == -1) {
                        L.update.e("Invalid ordinal of state.");
                        return;
                    }
                    RomUpdateState romUpdateState = RomUpdateState.values()[intExtra2];
                    Logger logger3 = L.update;
                    logger3.d("rom_update_state is " + romUpdateState);
                    RomUpdateAdapter.this.h = romUpdateState;
                    int intExtra3 = intent.getIntExtra(RomUpdateAdapter.EXTRA_ROM_UPDATE_ERROR, 0);
                    Logger logger4 = L.update;
                    logger4.d("rom_update_error is " + intExtra3);
                    RomUpdateAdapter.this.i = intExtra3;
                    RomUpdateEvent romUpdateEvent = new RomUpdateEvent();
                    romUpdateEvent.eventType = romUpdateEventType;
                    romUpdateEvent.romUpdateState = romUpdateState;
                    romUpdateEvent.romUpdateError = intExtra3;
                    switch (AnonymousClass2.a[romUpdateEventType.ordinal()]) {
                        case 1:
                            String stringExtra = intent.getStringExtra(RomUpdateAdapter.EXTRA_NEW_VERSION_NAME);
                            Logger logger5 = L.update;
                            logger5.d("new_version_name is " + stringExtra);
                            romUpdateEvent.newVersionName = stringExtra;
                            RomUpdateAdapter.this.a(stringExtra);
                            break;
                        case 2:
                            boolean booleanExtra = intent.getBooleanExtra(RomUpdateAdapter.EXTRA_ROM_UPDATE_SILENT, false);
                            Logger logger6 = L.update;
                            logger6.d("rom_update_silent is " + booleanExtra);
                            RomUpdateAdapter.this.j = booleanExtra;
                            romUpdateEvent.romUpdateSilent = booleanExtra;
                            if (!RomUpdateAdapter.this.j) {
                                RomUpdateAdapter.this.a();
                                break;
                            }
                            break;
                        case 3:
                            L.update.d("ROM update is completed with error code %d.", Integer.valueOf(RomUpdateAdapter.this.i));
                            if (RomUpdateAdapter.this.j) {
                                RomUpdateAdapter.this.j = false;
                                break;
                            } else {
                                RomUpdateAdapter.this.b();
                                break;
                            }
                        case 4:
                            L.update.d("ROM update is cancelled.");
                            if (RomUpdateAdapter.this.j) {
                                RomUpdateAdapter.this.j = false;
                                break;
                            } else {
                                RomUpdateAdapter.this.b();
                                break;
                            }
                        case 5:
                            L.update.e("ROM update is failed with error code %d.", Integer.valueOf(RomUpdateAdapter.this.i));
                            if (RomUpdateAdapter.this.j) {
                                RomUpdateAdapter.this.j = false;
                                break;
                            } else {
                                RomUpdateAdapter.this.b();
                                break;
                            }
                        case 6:
                            L.update.d("OTA will reboot");
                            SystemSetting.setRebootReason(1);
                            break;
                    }
                    EventBusRegistry.getEventBus().post(romUpdateEvent);
                }
            }
        };
        this.b = SystemProperties.get("ro.mi.sw_channel");
        String str = SystemProperties.get("ro.mi.sw_ver");
        this.c = new Version(str.contains(Constants.ACCEPT_TIME_SEPARATOR_SERVER) ? str.substring(0, str.indexOf(Constants.ACCEPT_TIME_SEPARATOR_SERVER)) : str);
        this.d = new Version();
        this.f = false;
    }

    private void a(Context context) {
        this.a = context;
        this.a.registerReceiver(this.l, new IntentFilter(ACTION_ROM_UPDATE_EVENT));
    }

    public String getChannel() {
        return this.b;
    }

    public String getChannelString() {
        String str = this.b;
        if (str == null) {
            return "测试版";
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -892499141) {
            if (hashCode == 1126940025 && str.equals(VersionUtil.VERSION_CURRENT)) {
                c = 0;
            }
        } else if (str.equals(VersionUtil.VERSION_STABLE)) {
            c = 1;
        }
        switch (c) {
            case 0:
                return "测试版";
            case 1:
                return "开发版";
            default:
                return "稳定版";
        }
    }

    public Version getVersion() {
        return this.c;
    }

    public Version getNewVersion() {
        return this.d;
    }

    public boolean hasNewVersion() {
        return this.f;
    }

    /* renamed from: com.xiaomi.micolauncher.skills.update.RomUpdateAdapter$2 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[RomUpdateEventType.values().length];

        static {
            try {
                a[RomUpdateEventType.CheckingUpdateCompleted.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[RomUpdateEventType.UpdatingStarted.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[RomUpdateEventType.UpdatingCompleted.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[RomUpdateEventType.CancellingUpdateCompleted.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[RomUpdateEventType.UpdatingFailed.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[RomUpdateEventType.RebootStarted.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public void a() {
        L.update.d("Disable other interactions to avoid disturbing ROM updating.");
        SpeechManager.getInstance().stop();
        QuickSettingHelper.enable(this.a, false);
        SystemUIManager.getDefault().disableGlobalGesture(this.a);
        UbusManager.getInstance().disable();
        CountDownModel.getInstance().cancel();
        AlarmModel.getInstance().disable();
        PlayerApi.pause();
        BluetoothSettingManager.init(this.a);
        this.k = BluetoothSettingManager.isSettingActivityOpened();
        if (this.k && BluetoothSettingManager.getInstance() != null) {
            BluetoothSettingManager.closeBluetooth(BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_CODE);
        }
        if (MiotProvisionManagerWrapper.isMeshEnable()) {
            MiotProvisionManagerWrapper.getInstance().stopMesh();
        }
    }

    public void b() {
        L.update.d("Enable other interactions after completing ROM updating.");
        SpeechManager.getInstance().start();
        QuickSettingHelper.enable(this.a, true);
        SystemUIManager.getDefault().enableGlobalGesture(this.a);
        UbusManager.getInstance().enable();
        AlarmModel.getInstance().enable();
        if (this.k) {
            BluetoothSettingManager.openBluetooth(BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_CODE);
        }
        if (MiotProvisionManagerWrapper.isMeshEnable()) {
            MiotProvisionManagerWrapper.getInstance().resumeMesh();
        }
    }

    public int getRomUpdateError() {
        return this.i;
    }

    public RomUpdateState getRomUpdateState() {
        return this.h;
    }

    public boolean isSilentUpdateOngoing() {
        return this.j;
    }

    public boolean isUpdateOngoing() {
        return this.h == RomUpdateState.StartingUpdate || this.h == RomUpdateState.Downloading || this.h == RomUpdateState.Verifying || this.h == RomUpdateState.Finalizing || this.h == RomUpdateState.CancellingUpdate || this.h == RomUpdateState.Rebooting;
    }

    public void checkUpdate() {
        Intent intent = new Intent(ACTION_CHECK_UPDATE);
        intent.setPackage(ROM_UPDATE_PACKAGE_NAME);
        this.a.sendBroadcast(intent);
    }

    public void startCheckUpdateActivity() {
        ComponentName componentName = new ComponentName(ROM_UPDATE_PACKAGE_NAME, CHECK_UPDATE_ACTIVITY_NAME);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        this.a.startActivity(intent);
    }

    public void doUpdate() {
        ComponentName componentName = new ComponentName(ROM_UPDATE_PACKAGE_NAME, ROM_UPDATE_ACTIVITY_NAME);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        this.a.startActivity(intent);
    }

    public int forceUpdate(String str) {
        ComponentName componentName = new ComponentName(ROM_UPDATE_PACKAGE_NAME, ROM_UPDATE_ACTIVITY_NAME);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.putExtra(EXTRA_FORCE_UPDATE_INFO, str);
        this.a.startActivity(intent);
        return 0;
    }

    public void cancelUpdate() {
        Intent intent = new Intent(ACTION_CANCEL_UPDATE);
        intent.setPackage(ROM_UPDATE_PACKAGE_NAME);
        this.a.sendBroadcast(intent);
    }

    public void a(String str) {
        this.e = str;
        if (str == null || str.equals("")) {
            this.d.reset();
            this.f = false;
            return;
        }
        this.d.parse(str);
        if (this.d.majorVersion > this.c.majorVersion) {
            this.f = true;
        }
        if (this.d.majorVersion == this.c.majorVersion && this.d.minorVersion > this.c.minorVersion) {
            this.f = true;
        }
        if (this.d.majorVersion == this.c.majorVersion && this.d.minorVersion == this.c.minorVersion && this.d.buildVersion > this.c.buildVersion) {
            this.f = true;
        }
    }

    public void updateLastActiveTimestamp(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.g > 5000) {
            Logger logger = L.update;
            logger.d("active by:" + str + " ts:" + currentTimeMillis);
        }
        this.g = currentTimeMillis;
        if (this.j && isUpdateOngoing()) {
            Logger logger2 = L.update;
            logger2.d("active by:" + str + ", cancel silent update.");
            cancelUpdate();
        }
    }
}
