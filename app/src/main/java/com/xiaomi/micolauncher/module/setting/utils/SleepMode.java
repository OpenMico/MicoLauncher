package com.xiaomi.micolauncher.module.setting.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.settingslib.core.NightModeConfig;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.StatusBarEvent;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class SleepMode {
    public static final String SLEEP_MODE_ACTION_ENTER = "sleep_mode_action_enter";
    public static final String SLEEP_MODE_ACTION_EXIT = "sleep_mode_action_exit";
    private static final long a = TimeUnit.MINUTES.toMillis(10);
    private static final long b = TimeUnit.SECONDS.toMillis(2);
    private static final long c = TimeUnit.MINUTES.toMillis(15);
    @SuppressLint({"StaticFieldLeak"})
    private static volatile SleepMode d;
    private boolean e;
    private boolean f;
    private Context g;
    private int h;
    private int i;
    private int j;
    private NightModeConfig m;
    private boolean n;
    private boolean o;
    private AlarmManager p;
    private PendingIntent q;
    private PendingIntent r;
    private Calendar s;
    private Handler l = new Handler(ThreadUtil.getLightWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$SleepMode$HIo00XHXr3XbEYh3M71-F-0pXFo
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean a2;
            a2 = SleepMode.this.a(message);
            return a2;
        }
    });
    private BroadcastReceiver k = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.setting.utils.SleepMode.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.sleep_mode.d("[SleepMode]: onReceive, action=" + action);
            if (action != null) {
                char c2 = 65535;
                int hashCode = action.hashCode();
                if (hashCode != -2128145023) {
                    if (hashCode != -1454123155) {
                        if (hashCode != -969456047) {
                            if (hashCode == 505380757 && action.equals("android.intent.action.TIME_SET")) {
                                c2 = 1;
                            }
                        } else if (action.equals("com.xiaomi.mico.action.night.systemui")) {
                            c2 = 0;
                        }
                    } else if (action.equals("android.intent.action.SCREEN_ON")) {
                        c2 = 3;
                    }
                } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                    c2 = 2;
                }
                switch (c2) {
                    case 0:
                        if (intent.getBooleanExtra("com.xiaomi.sleep.mode.state", false)) {
                            SleepMode.this.doEnterSleep(false, false);
                        } else {
                            SleepMode.this.doExitSleep(false, false);
                        }
                        Screen.getInstance().systemUiDismiss(200);
                        return;
                    case 1:
                        SleepMode.this.a(false);
                        return;
                    case 2:
                        if (SleepMode.this.l != null) {
                            if (ScreenUtil.isFromPwrKey()) {
                                AudioPolicyService.getInstance().requestForceStopAll(AudioSource.AUDIO_SOURCE_POWER_KEY);
                                SleepMode.this.l.removeMessages(3);
                                SleepMode.this.l.removeMessages(4);
                                SleepMode.this.l.sendEmptyMessage(4);
                            }
                            ScreenUtil.setFromPwrKey();
                            SleepMode.this.l.post(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.utils.SleepMode.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (SpeechManager.peekInstance() != null && !AudioPolicyService.getInstance().hasActiveAudioSource()) {
                                        L.sleep_mode.d("[SleepMode]: enter sleep mode on ACTION_SCREEN_OFF");
                                        SpeechManager.getInstance().enterPowerSaveMode();
                                    }
                                }
                            });
                            L.sleep_mode.d("[SleepMode]: screen_timeout=" + Screen.getInstance().b());
                            return;
                        }
                        return;
                    case 3:
                        if (SleepMode.this.l != null) {
                            SleepMode.this.l.removeMessages(4);
                            SleepMode.this.l.removeMessages(5);
                        }
                        if (SpeechManager.peekInstance() != null) {
                            SpeechManager.getInstance().enterPerfMode();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    };

    private void b() {
    }

    public void setDelaySetVolumeWhenEnterSleep(boolean z) {
        this.o = z;
    }

    private SleepMode(Context context) {
        this.g = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.xiaomi.mico.action.night.systemui");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.TIME_SET");
        this.g.registerReceiver(this.k, intentFilter);
        this.m = NightModeConfig.getSleepMode(context);
        a(this.m);
        if (this.j < 0) {
            this.j = 0;
        }
        BrightnessControllerProxy.getInstance();
        SystemVolume.init(context);
        Screen.init(context);
        if (this.e) {
            SystemVolume.getInstance().c();
        }
        a(true);
    }

    public /* synthetic */ boolean a(Message message) {
        this.l.removeMessages(message.what);
        L.sleep_mode.d("%s handleMessage=%s", "[SleepMode]: ", Integer.valueOf(message.what));
        switch (message.what) {
            case 2:
                if (this.e) {
                    SystemVolume.getInstance().a(this.j);
                    break;
                }
                break;
            case 3:
                if (!Screen.getInstance().isHoldByPlay()) {
                    Screen.getInstance().startLockScreenActivity(false);
                    break;
                }
                break;
            case 4:
                boolean isUpdateOngoing = RomUpdateAdapter.getInstance().isUpdateOngoing();
                boolean isInitialized = SystemSetting.isInitialized(this.g);
                L.sleep_mode.d("%s isUpdating=%s, isInitialized=%s", "[SleepMode]: ", Boolean.valueOf(isUpdateOngoing), Boolean.valueOf(isInitialized));
                if (!isUpdateOngoing && isInitialized) {
                    this.l.sendEmptyMessageDelayed(5, c);
                    break;
                }
                break;
            case 5:
                SchemaManager.handleSchema(this.g, HomepageSchemaHandler.PATH_MAIN_PAGE);
                Screen.getInstance().startLockScreenActivity(false);
                break;
        }
        return false;
    }

    private void a(NightModeConfig nightModeConfig) {
        if (nightModeConfig != null) {
            this.e = nightModeConfig.isEnable();
            this.f = nightModeConfig.isTimeSleep();
            this.h = nightModeConfig.getTimeStart();
            this.i = nightModeConfig.getTimeEnd();
            this.j = nightModeConfig.getVolume();
        }
    }

    public static void initialize(Context context) {
        synchronized (SleepMode.class) {
            if (d == null) {
                d = new SleepMode(context);
            }
        }
    }

    public void onConfigChange(NightModeConfig nightModeConfig) {
        AlarmManager alarmManager;
        this.m = nightModeConfig;
        boolean z = this.e != nightModeConfig.isEnable();
        boolean z2 = (this.f == nightModeConfig.isTimeSleep() && this.h == nightModeConfig.getTimeStart() && this.i == nightModeConfig.getTimeEnd()) ? false : true;
        if (z2) {
            this.n = false;
        }
        a(nightModeConfig);
        if (z) {
            a(false);
        } else if (!z2) {
        } else {
            if (this.f || (alarmManager = this.p) == null) {
                c();
                if (!this.e && a()) {
                    doEnterSleep(true, true);
                    return;
                }
                return;
            }
            alarmManager.cancel(this.q);
            this.p.cancel(this.r);
            this.p = null;
        }
    }

    public static SleepMode peekInstance() {
        return d;
    }

    public static SleepMode getInstance() {
        if (d != null) {
            return d;
        }
        throw new IllegalStateException("need to call SleepMode.instance() first!");
    }

    public NightModeConfig getSleepModeConfig(Context context) {
        if (this.m == null) {
            this.m = NightModeConfig.getSleepMode(context);
        }
        return this.m;
    }

    public void a(boolean z) {
        boolean isAuto = this.m.isAuto();
        this.e = this.m.isEnable();
        this.f = this.m.isTimeSleep();
        Logger logger = L.sleep_mode;
        logger.d("[SleepMode]: refreshSleepStatus ,isAuto=" + isAuto + ", mSleep=" + this.e);
        if (!isAuto) {
            if (z && this.f) {
                c();
            }
            if (this.e) {
                this.e = false;
                doEnterSleep(true, false);
                return;
            }
            this.e = true;
            doExitSleep(true, false);
            return;
        }
        c();
        if (a()) {
            this.e = false;
            doEnterSleep(true, true);
            b();
        } else if (!this.e || this.f) {
            this.e = true;
            doExitSleep(true, true);
        } else {
            this.e = false;
            doEnterSleep(true, true);
            b();
        }
    }

    public void destroy() {
        BroadcastReceiver broadcastReceiver;
        L.sleep_mode.d("[SleepMode]: destroy");
        Context context = this.g;
        if (!(context == null || (broadcastReceiver = this.k) == null)) {
            context.unregisterReceiver(broadcastReceiver);
        }
        Handler handler = this.l;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        BrightnessControllerProxy.destroy();
        SystemVolume.a();
        Screen.a();
    }

    public synchronized void doEnterSleep(boolean z, boolean z2) {
        L.sleep_mode.d("%s doEnterSleep.mSleep=%s, .auto=%s, .mVolume=%s, .sendBroadcast=%s", "[SleepMode]: ", Boolean.valueOf(this.e), Boolean.valueOf(z2), Integer.valueOf(this.j), Boolean.valueOf(z));
        this.m.setAuto(z2);
        if (this.l != null) {
            this.l.removeMessages(3);
            if (!Screen.getInstance().isHoldByPlay()) {
                if (!this.e) {
                    ToastUtil.showCustomToast(R.string.setting_sleep_mode_enter_tip, (int) b);
                }
                this.l.sendEmptyMessageDelayed(3, b);
            }
        }
        if (!this.e) {
            BrightnessControllerProxy.getInstance().a();
            Screen.getInstance().c();
            if (z) {
                Intent intent = new Intent("com.xiaomi.mico.action.night.launcher");
                intent.putExtra("com.xiaomi.sleep.mode.state", true);
                this.g.sendStickyBroadcast(intent);
            }
            this.e = true;
            this.m.setEnable(true);
            if (this.o) {
                this.o = false;
                this.l.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$SleepMode$d2rNO8g9NA-EG01I3TcyhRpUwbw
                    @Override // java.lang.Runnable
                    public final void run() {
                        SleepMode.this.d();
                    }
                }, 3300L);
            } else {
                SystemVolume.getInstance().a(this.j);
            }
            EventBusRegistry.getEventBus().post(new StatusBarEvent(StatusBarEvent.StatusBarType.STATUS_BAR_TYPE_NIGHT_MODE, true));
        }
        this.n = z2;
        NightModeConfig.saveSleepMode(this.g.getApplicationContext(), this.m);
    }

    public /* synthetic */ void d() {
        SystemVolume.getInstance().a(this.j);
    }

    public synchronized void doExitSleep(boolean z, boolean z2) {
        Logger logger = L.sleep_mode;
        logger.d("[SleepMode]: doExitSleep.mSleep = " + this.e + ", auto = " + z2);
        this.m.setAuto(z2);
        if (this.e) {
            BrightnessControllerProxy.getInstance().b();
            Screen.getInstance().d();
            if (z) {
                Intent intent = new Intent("com.xiaomi.mico.action.night.launcher");
                intent.putExtra("com.xiaomi.sleep.mode.state", false);
                this.g.sendStickyBroadcast(intent);
            }
            this.m.setEnable(false);
            this.e = false;
            SystemVolume.getInstance().b();
            EventBusRegistry.getEventBus().post(new StatusBarEvent(StatusBarEvent.StatusBarType.STATUS_BAR_TYPE_NIGHT_MODE, false));
        }
        NightModeConfig.saveSleepMode(this.g.getApplicationContext(), this.m);
    }

    public boolean inDuringSleepTime() {
        int i = (Calendar.getInstance().get(11) * 60) + Calendar.getInstance().get(12);
        L.sleep_mode.d("[SleepMode]: inDuringSleepTime.current=%d, start=%d, end=%d", Integer.valueOf(i), Integer.valueOf(this.h), Integer.valueOf(this.i));
        int i2 = this.h;
        int i3 = this.i;
        if (i2 > i3) {
            return i >= i2 || i < i3;
        }
        if (i2 == i3) {
            return true;
        }
        return i >= i2 && i < i3;
    }

    private boolean a() {
        Logger logger = L.sleep_mode;
        logger.d("[SleepMode]: shouldEnterSleep.mTimeSwitcher=" + this.f);
        return this.f && inDuringSleepTime();
    }

    public int getTimeStart() {
        return this.h;
    }

    public int getTimeEnd() {
        return this.i;
    }

    public int getVolume() {
        return this.j;
    }

    public boolean getSleepStatus() {
        return this.e;
    }

    public void notifyPlayerStart() {
        Handler handler = this.l;
        if (handler != null) {
            handler.removeMessages(2);
        }
    }

    public void notifyPlayerStop() {
        Handler handler;
        if (this.e && (handler = this.l) != null) {
            handler.removeMessages(2);
            this.l.sendEmptyMessageDelayed(2, a);
        }
    }

    private void c() {
        AlarmManager alarmManager = this.p;
        if (alarmManager == null) {
            this.p = (AlarmManager) this.g.getSystemService("alarm");
            Intent intent = new Intent(this.g, SleepModeService.class);
            intent.setAction(SLEEP_MODE_ACTION_ENTER);
            Intent intent2 = new Intent(this.g, SleepModeService.class);
            intent2.setAction(SLEEP_MODE_ACTION_EXIT);
            this.q = PendingIntent.getService(this.g, 0, intent, 0);
            this.r = PendingIntent.getService(this.g, 0, intent2, 0);
            this.s = Calendar.getInstance();
        } else {
            alarmManager.cancel(this.q);
            this.p.cancel(this.r);
        }
        b(true);
        b(false);
    }

    private void b(boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        int i = z ? this.h : this.i;
        this.s.setTimeInMillis(currentTimeMillis);
        int i2 = (this.s.get(11) * 60) + this.s.get(12);
        L.sleep_mode.d("[SleepMode]: setAlarm , current=%d, start=%d, end=%d", Integer.valueOf(i2), Integer.valueOf(this.h), Integer.valueOf(this.i));
        this.s.set(11, i / 60);
        this.s.set(12, i % 60);
        this.s.set(13, 0);
        this.s.set(14, 0);
        if (i2 >= i) {
            this.s.add(6, 1);
        }
        this.p.setExactAndAllowWhileIdle(0, this.s.getTimeInMillis(), z ? this.q : this.r);
    }
}
