package com.xiaomi.micolauncher.module.setting.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenOpenManager;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenSendBroadcast;
import com.xiaomi.micolauncher.skills.common.VideoPlayerEvent;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class Screen {
    private static final int a = (int) TimeUnit.SECONDS.toMillis(1);
    private static final int b = (int) TimeUnit.MINUTES.toMillis(1);
    @SuppressLint({"StaticFieldLeak"})
    private static Screen c;
    private final Context d;
    private final PowerManager e;
    private PowerManager.WakeLock f;
    private PowerManager.WakeLock g;
    private volatile boolean h;

    public static Screen getInstance() {
        Screen screen = c;
        if (screen != null) {
            return screen;
        }
        throw new IllegalStateException("need to call Screen.init() first!!");
    }

    public static void init(Context context) {
        if (c == null) {
            c = new Screen(context);
        }
    }

    public static void a() {
        Screen screen = c;
        if (screen != null) {
            PowerManager.WakeLock wakeLock = screen.f;
            if (wakeLock != null && wakeLock.isHeld()) {
                c.f.release();
            }
            PowerManager.WakeLock wakeLock2 = c.g;
            if (wakeLock2 != null && wakeLock2.isHeld()) {
                c.g.release();
            }
            EventBusRegistry.getEventBus().unregister(c);
        }
    }

    private Screen(Context context) {
        this.d = context;
        this.e = (PowerManager) this.d.getSystemService("power");
        PowerManager powerManager = this.e;
        if (powerManager != null) {
            this.f = powerManager.newWakeLock(805306394, getClass().getName());
            this.g = this.e.newWakeLock(805306394, getClass().getName());
        }
        d();
        EventBusRegistry.getEventBus().register(this);
    }

    public int b() {
        return Settings.System.getInt(this.d.getContentResolver(), "screen_off_timeout", b);
    }

    public void c() {
        L.base.d("%s enterSleep", "[Screen]:");
    }

    @SuppressLint({"WakelockTimeout"})
    public void d() {
        L.base.d("%s exitSleep", "[Screen]:");
    }

    public void onResume() {
        PowerManager powerManager = this.e;
        if (powerManager != null) {
            powerManager.wakeUp(SystemClock.uptimeMillis());
            this.e.userActivity(SystemClock.uptimeMillis(), false);
            L.base.d("%s onResume", "[Screen]:");
        }
    }

    public void systemUiDismiss(int i) {
        Intent intent = new Intent("com.xiaomi.mico.intent.systemui.dismiss");
        intent.putExtra("delay", i);
        this.d.sendBroadcast(intent);
    }

    @SuppressLint({"WakelockTimeout"})
    public synchronized void onStart() {
        if (this.f != null && !this.f.isHeld()) {
            this.e.wakeUp(SystemClock.uptimeMillis());
            this.f.acquire();
            L.base.d("%s onStart", "[Screen]:");
        }
    }

    public synchronized void onPause() {
        if (this.f != null && this.f.isHeld()) {
            this.e.userActivity(SystemClock.uptimeMillis(), false);
            this.f.release();
            L.base.d("%s onPause", "[Screen]:");
        }
    }

    public boolean isHoldByPlay() {
        int i = PlayerApi.getPlayerStatus().status;
        L.sleep_mode.d("%s music.playStatus=%s, mVideoLock.isHeld=%b, VideoMediaSession.isPlaying = %b", "[Screen]:", Integer.valueOf(i), Boolean.valueOf(this.g.isHeld()), Boolean.valueOf(VideoMediaSession.getInstance().isPlaying()));
        return i == 1 || this.g.isHeld() || VideoMediaSession.getInstance().isPlaying();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @SuppressLint({"WakelockTimeout"})
    public void onVideoPlayerEvent(VideoPlayerEvent videoPlayerEvent) {
        L.base.d("%s onVideoPlayerEvent.state=%s", "[Screen]:", videoPlayerEvent.getEvent());
        if (videoPlayerEvent.getEvent() == VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_PLAYING) {
            PowerManager.WakeLock wakeLock = this.g;
            if (wakeLock != null && !wakeLock.isHeld()) {
                this.e.wakeUp(SystemClock.uptimeMillis());
                this.g.acquire();
                return;
            }
            return;
        }
        PowerManager.WakeLock wakeLock2 = this.g;
        if (wakeLock2 != null && wakeLock2.isHeld()) {
            this.g.release();
        }
    }

    public void startLockScreenActivity(boolean z) {
        LockScreenOpenManager.openLockScreen(this.d, z);
    }

    public void openLockScreen() {
        Threads.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$Screen$kCGKTaAaSYZ4JpYX1a3DVlo9dQQ
            @Override // java.lang.Runnable
            public final void run() {
                Screen.this.e();
            }
        }, a);
    }

    public /* synthetic */ void e() {
        startLockScreenActivity(true);
    }

    public void closeLockScreen() {
        LockScreenSendBroadcast.sendCloseLockScreenEventBroadcast(this.d);
    }

    public boolean isInteractive() {
        return this.e.isInteractive();
    }

    public boolean isLockScreenDisplaying() {
        return this.h;
    }

    public void setLockScreen(boolean z) {
        this.h = z;
    }
}
