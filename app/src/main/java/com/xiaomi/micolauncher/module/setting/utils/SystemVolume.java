package com.xiaomi.micolauncher.module.setting.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.UserBehaviorProxy;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.VolumeChangeEvent;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.module.battery.BatteryStatusMonitor;
import com.xiaomi.micolauncher.skills.music.PlayerApi;

/* loaded from: classes3.dex */
public class SystemVolume {
    public static final int MAX_VOLUME_IN_LOWPOWER = 65;
    private static SystemVolume a;
    private final Context b;
    private ContentResolver c;
    private int d;
    private final int e;
    private AudioManager f;
    private int g;
    private a h;
    private boolean i;
    private int j;
    private boolean k;

    public static SystemVolume getInstance() {
        SystemVolume systemVolume = a;
        if (systemVolume != null) {
            return systemVolume;
        }
        throw new IllegalStateException("need call SystemVolume.init() first!");
    }

    public static void init(Context context) {
        if (a == null) {
            a = new SystemVolume(context);
        }
    }

    public static void a() {
        SystemVolume systemVolume = a;
        if (systemVolume != null) {
            systemVolume.e();
            a aVar = a.h;
            if (aVar != null) {
                aVar.releaseContentObserver();
                a.h = null;
            }
            a = null;
        }
    }

    private SystemVolume(Context context) {
        this.b = context;
        this.f = (AudioManager) context.getSystemService("audio");
        this.j = 0;
        AudioManager audioManager = this.f;
        if (audioManager != null) {
            this.e = audioManager.getStreamMaxVolume(3);
            this.d = this.f.getStreamVolume(3);
            this.j = this.d;
        } else {
            this.e = 20;
        }
        this.i = false;
        d();
        this.k = false;
    }

    private void d() {
        if (this.h == null) {
            this.h = new a(new Handler());
        }
        if (this.c == null) {
            this.c = MicoApplication.getGlobalContext().getContentResolver();
        }
        ContentResolver contentResolver = this.c;
        if (contentResolver != null) {
            contentResolver.registerContentObserver(Settings.System.CONTENT_URI, true, this.h);
        }
    }

    private void e() {
        ContentResolver contentResolver;
        a aVar = this.h;
        if (aVar != null && (contentResolver = this.c) != null) {
            contentResolver.unregisterContentObserver(aVar);
        }
    }

    public void a(int i) {
        L.sleep_mode.d("%s enterSleepMode, mSleep=%b, sleepVolume=%s", "[SystemVolume]: ", Boolean.valueOf(this.k), Integer.valueOf(i));
        if (i >= 0) {
            this.g = (i * this.e) / 100;
            if (this.f == null) {
                L.sleep_mode.e("%s AudioManager is null", "[SystemVolume]: ");
            } else if (this.k) {
                L.sleep_mode.i("%s has been sleep", "[SystemVolume]: ");
            } else {
                boolean isHoldByPlay = Screen.getInstance().isHoldByPlay();
                this.d = this.f.getStreamVolume(3);
                SystemSetting.cacheNormalVolume(this.d);
                if (isHoldByPlay) {
                    L.sleep_mode.d("%s enterSleepMode is playing, no action", "[SystemVolume]: ");
                    return;
                }
                int i2 = this.g;
                if (i2 >= this.d) {
                    L.sleep_mode.d("%s enterSleepMode.sleepModeVolume=%d, now=%d, no action", "[SystemVolume]: ", Integer.valueOf(this.g), Integer.valueOf(this.d));
                    return;
                }
                b(i2, 1048576);
                this.k = true;
                L.sleep_mode.d("%s enterSleepMode.nowVolume=%d, sleepModeVolume=%d", "[SystemVolume]: ", Integer.valueOf(this.d), Integer.valueOf(this.g));
            }
        } else {
            throw new IllegalArgumentException("enterSleepMode.sleepVolume must be > 0");
        }
    }

    public void b() {
        L.base.d("%s exitSleepMode, mSleep=%b", "[SystemVolume]: ", Boolean.valueOf(this.k));
        if (this.k) {
            boolean isHoldByPlay = Screen.getInstance().isHoldByPlay();
            int streamVolume = this.f.getStreamVolume(3);
            if (isHoldByPlay) {
                L.base.d("%s exitSleepMode is playing, no action", "[SystemVolume]: ");
            } else {
                int i = this.d;
                if (streamVolume >= i) {
                    L.base.d("%s exitSleepMode.sleepModeVolume=%d, now=%d, no action", "[SystemVolume]: ", Integer.valueOf(streamVolume), Integer.valueOf(this.d));
                } else {
                    this.g = i;
                    b(this.g, 0);
                    L.base.d("%s exitSleepMode.sleepModeVolume=%d, now=%d", "[SystemVolume]: ", Integer.valueOf(streamVolume), Integer.valueOf(this.d));
                }
            }
            SystemSetting.cacheNormalVolume(-1);
            this.k = false;
        }
    }

    public void enterAdjustingSleepModeVolume() {
        if (!SleepMode.getInstance().getSleepStatus() && !this.i) {
            this.d = this.f.getStreamVolume(3);
        }
        this.i = true;
    }

    public void setSleepModeVolume(int i) {
        int i2 = (this.e * i) / 100;
        int streamVolume = this.f.getStreamVolume(3);
        L.base.d("%s setSleepModeVolume.volume=%s, target=%s, now=%s", "[SystemVolume]: ", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(streamVolume));
        if (i2 < 0) {
            i2 = 0;
        }
        if (streamVolume != i2) {
            b(i2, 1048576);
        }
    }

    public void exitAdjustingSleepModeVolume() {
        this.i = false;
        if (!SleepMode.getInstance().getSleepStatus()) {
            b(this.d, 1048576);
        }
    }

    private boolean f() {
        return PlayerApi.getPlayerStatus().status == 1 || VideoMediaSession.getInstance().isPlaying();
    }

    public int setVolume(int i) {
        return setVolume(i, false);
    }

    public static boolean volumeTooBigForPowerSaveMode(int i) {
        return Hardware.isX08E() && i > 65 && BatteryStatusMonitor.getInstance().isPowerSaveMode();
    }

    public int setVolume(int i, boolean z) {
        boolean z2 = false;
        if (volumeTooBigForPowerSaveMode(i)) {
            L.base.i("%s setVolume in power save_mode %s -> %s", "[SystemVolume]: ", Integer.valueOf(i), 65);
            i = 65;
        }
        if (this.f == null) {
            L.base.e("%s setVolume, mAudioManager is null", "[SystemVolume]: ");
            return i;
        }
        L.base.d("%s setVolume.volume=%s, sound=%b", "[SystemVolume]: ", Integer.valueOf(i), Boolean.valueOf(z));
        int a2 = a(i, 3);
        if (z && !f()) {
            z2 = true;
        }
        if (z2) {
            b(a2, 5);
        } else {
            b(a2, 1);
        }
        return i;
    }

    public void setAlarmVolume(int i, boolean z) {
        if (this.f == null) {
            L.base.e("%s setAlarmVolume, mAudioManager is null", "[SystemVolume]: ");
            return;
        }
        int a2 = a(i, 4);
        if (z) {
            this.f.setStreamVolume(4, a2, 5);
        } else {
            this.f.setStreamVolume(4, a2, 8);
        }
    }

    public int getAlarmVolume() {
        AudioManager audioManager = this.f;
        if (audioManager != null) {
            return audioManager.getStreamVolume(4) * 5;
        }
        L.base.e("%s getVolume.mAudioManager=null", "[SystemVolume]: ");
        return -1;
    }

    private int a(int i, int i2) {
        if (i > 100) {
            i = 100;
        } else if (i < 0) {
            i = 0;
        }
        int streamMaxVolume = this.f.getStreamMaxVolume(i2);
        int i3 = ((i + (50 / streamMaxVolume)) * streamMaxVolume) / 100;
        if (i3 <= 0) {
            return 1;
        }
        return i3;
    }

    public void upVolume() {
        AudioManager audioManager = this.f;
        if (audioManager != null) {
            audioManager.adjustStreamVolume(3, 1, 1);
        } else {
            L.base.e("%s upVolume.mAudioManager=null", "[SystemVolume]: ");
        }
    }

    public void downVolume() {
        AudioManager audioManager = this.f;
        if (audioManager != null) {
            audioManager.adjustStreamVolume(3, -1, 1);
        } else {
            L.base.e("%s downVolume.mAudioManager=null", "[SystemVolume]: ");
        }
    }

    public int getVolume() {
        AudioManager audioManager = this.f;
        if (audioManager != null) {
            return audioManager.getStreamVolume(3) * 5;
        }
        L.base.e("%s getVolume.mAudioManager=null", "[SystemVolume]: ");
        return -1;
    }

    public int getVolume2() {
        AudioManager audioManager = this.f;
        if (audioManager != null) {
            return audioManager.getStreamVolume(3);
        }
        L.base.e("%s getVolume.mAudioManager=null", "[SystemVolume]: ");
        return -1;
    }

    public void c() {
        int cachedNormalVolume = SystemSetting.getCachedNormalVolume();
        L.base.i("%s recoverNormalModeVolume.volume=%s", "[SystemVolume]: ", Integer.valueOf(cachedNormalVolume));
        if (cachedNormalVolume > 0 && cachedNormalVolume < this.e && this.f != null) {
            b(cachedNormalVolume, 0);
        }
    }

    private void b(int i, int i2) {
        try {
            this.f.setStreamVolume(3, i, i2);
        } catch (SecurityException e) {
            L.base.e("%s Set volume failed cause of SecurityException: volume=%s, flags=%s, ringerMode=%s", "[SystemVolume]: ", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.f.getRingerMode()));
            e.printStackTrace();
        }
    }

    /* loaded from: classes3.dex */
    public final class a extends ContentObserver {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private a(Handler handler) {
            super(handler);
            SystemVolume.this = r1;
        }

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            char c = 1;
            L.base.d("%s VolumeContentObserver onChange selfChange=%s, uri=%s", "[SystemVolume]: ", Boolean.valueOf(z), uri);
            if (uri.toString().equals("content://settings/system/volume_music_speaker") && !z) {
                EventBusRegistry.getEventBus().post(new VolumeChangeEvent(SystemVolume.this.getVolume()));
            } else if (!uri.toString().equals("content://settings/system/volume_voice_speaker") || z) {
                c = 0;
            }
            if (c > 0) {
                UserBehaviorProxy.setUserBehavior(SystemVolume.this.b, "VolumeChange");
            }
        }
    }
}
