package com.xiaomi.micolauncher.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.MiotConnectedEvent;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import com.xiaomi.micolauncher.skills.voip.model.VoipStartEvent;
import com.xiaomi.micolauncher.skills.voip.utils.CameraStatusHelper;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes3.dex */
public class VideoMonitorHelper {
    public static final int CODE_CANT_MATCH_MESSAGE_EVENT = 2;
    private static volatile VideoMonitorHelper a;
    private final Context b;
    private ContentObserver c;
    private int d;
    private boolean e;
    private boolean f;
    private boolean g;

    public static VideoMonitorHelper getInstance() {
        if (a == null) {
            synchronized (VideoMonitorHelper.class) {
                if (a == null) {
                    a = new VideoMonitorHelper(MicoApplication.getGlobalContext());
                }
            }
        }
        return a;
    }

    private VideoMonitorHelper(Context context) {
        this.b = context;
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        b();
        a();
    }

    public int getCode() {
        return hasVideoMonitorFunction() ? 0 : 1;
    }

    public int getStatus() {
        int i = 0;
        if (VoipModel.getInstance().isVoipActive()) {
            i = 4;
        } else {
            boolean isCameraEnabled = CameraStatusHelper.getInstance().isCameraEnabled();
            L.videoMonitor.d("[VideoMonitorHelper] isCameraEnabled: " + isCameraEnabled);
            if (CameraStatusHelper.getInstance().hasLensCover()) {
                boolean isCameraLensCovered = CameraStatusHelper.getInstance().isCameraLensCovered();
                L.videoMonitor.d("[VideoMonitorHelper] isCameraLensCovered: " + isCameraLensCovered);
                if (isCameraEnabled && isCameraLensCovered) {
                    i = 1;
                }
                if (!isCameraEnabled && !isCameraLensCovered) {
                    i = 2;
                }
                if (!isCameraEnabled && isCameraLensCovered) {
                    i = 3;
                }
            } else if (!isCameraEnabled) {
                i = 2;
            }
        }
        L.videoMonitor.d("[VideoMonitorHelper] camera status is " + i);
        return i;
    }

    public boolean canOpenVideoMonitor() {
        boolean z = getStatus() == 0;
        Logger logger = L.videoMonitor;
        logger.d("[VideoMonitorHelper] canOpenVideoMonitor: " + z);
        return z;
    }

    private void a(boolean z) {
        Intent intent = new Intent();
        if (z) {
            L.videoMonitor.d("[VideoMonitorHelper] restart video monitor service");
            intent.setAction("com.xiaomi.mico.persistent.monitor.START_SERVICE");
        } else {
            L.videoMonitor.d("[VideoMonitorHelper] stop video monitor service");
            intent.setAction("com.xiaomi.mico.persistent.monitor.STOP_SERVICE");
        }
        intent.setPackage("com.xiaomi.mico.persistent.monitor");
        this.b.startForegroundService(intent);
    }

    public void saveLowLevelFunctionsState() {
        boolean z = false;
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("shared_preferences_video_monitor", 0);
        this.e = Settings.Global.getInt(this.b.getContentResolver(), MicoSettings.Global.GESTURE_CONTROL, 0) == 1;
        setLowLevelFunctionStateToSP(sharedPreferences, "sp_key_is_gesture_control_open", this.e);
        this.f = Settings.Global.getInt(this.b.getContentResolver(), MicoSettings.Global.DISTANCE_PROTECTION, 0) == 1;
        setLowLevelFunctionStateToSP(sharedPreferences, "sp_key_is_distance_protection_open", this.f);
        if (Settings.Global.getInt(this.b.getContentResolver(), MicoSettings.Global.QUICK_ENTER_CHILD_MODE, 0) == 1) {
            z = true;
        }
        this.g = z;
        setLowLevelFunctionStateToSP(sharedPreferences, "sp_key_is_quick_enter_child_mode_open", this.g);
    }

    public void setGestureControl(boolean z) {
        Settings.Global.putInt(this.b.getContentResolver(), MicoSettings.Global.GESTURE_CONTROL, z ? 1 : 0);
    }

    public void setDistanceProtection(boolean z) {
        Settings.Global.putInt(this.b.getContentResolver(), MicoSettings.Global.DISTANCE_PROTECTION, z ? 1 : 0);
    }

    public void setQuickEnterChildMode(boolean z) {
        Settings.Global.putInt(this.b.getContentResolver(), MicoSettings.Global.QUICK_ENTER_CHILD_MODE, z ? 1 : 0);
    }

    public void setLowLevelFunctionStateToSP(SharedPreferences sharedPreferences, String str, boolean z) {
        sharedPreferences.edit().putBoolean(str, z).apply();
    }

    public boolean getLowLevelFunctionStateFromSP(SharedPreferences sharedPreferences, String str, boolean z) {
        return sharedPreferences.getBoolean(str, z);
    }

    public void updateLowLevelCameraFunctionsStateIfNeeded() {
        if (hasVideoMonitorFunction()) {
            SharedPreferences sharedPreferences = this.b.getSharedPreferences("shared_preferences_video_monitor", 0);
            this.e = getLowLevelFunctionStateFromSP(sharedPreferences, "sp_key_is_gesture_control_open", false);
            if (this.e) {
                setGestureControl(true);
                setLowLevelFunctionStateToSP(sharedPreferences, "sp_key_is_gesture_control_open", false);
            }
            this.f = getLowLevelFunctionStateFromSP(sharedPreferences, "sp_key_is_distance_protection_open", false);
            if (this.f) {
                setDistanceProtection(true);
                setLowLevelFunctionStateToSP(sharedPreferences, "sp_key_is_distance_protection_open", false);
            }
            this.g = getLowLevelFunctionStateFromSP(sharedPreferences, "sp_key_is_quick_enter_child_mode_open", false);
            if (this.g) {
                setQuickEnterChildMode(true);
                setLowLevelFunctionStateToSP(sharedPreferences, "sp_key_is_quick_enter_child_mode_open", false);
            }
        }
    }

    @Subscribe
    public void onMiotConnected(MiotConnectedEvent miotConnectedEvent) {
        L.videoMonitor.d("[VideoMonitorHelper] ot connected, start miss service");
        startVideoMonitorService();
    }

    @Subscribe
    public void onVoipStart(VoipStartEvent voipStartEvent) {
        Logger logger = L.videoMonitor;
        logger.d("[VideoMonitorHelper] onVoipStart: isVideoMonitorRunning = " + isVideoMonitorRunning());
        stopVideoMonitorService();
    }

    public void startVideoMonitorService() {
        if (hasVideoMonitorFunction()) {
            a(true);
        }
    }

    public void stopVideoMonitorService() {
        if (isVideoMonitorRunning()) {
            a(false);
        }
    }

    private void a() {
        if (hasVideoMonitorFunction()) {
            Uri uriFor = Settings.Global.getUriFor("mi_camera_monitor_running");
            this.c = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.xiaomi.micolauncher.common.utils.VideoMonitorHelper.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z, Uri uri) {
                    super.onChange(z, uri);
                    VideoMonitorHelper.this.b();
                    Logger logger = L.videoMonitor;
                    logger.d("[VideoMonitorHelper] videoMonitorState change to " + VideoMonitorHelper.this.d);
                    if (!VideoMonitorHelper.this.isVideoMonitorRunning()) {
                        VideoMonitorHelper videoMonitorHelper = VideoMonitorHelper.this;
                        videoMonitorHelper.setGestureControl(videoMonitorHelper.e);
                        VideoMonitorHelper videoMonitorHelper2 = VideoMonitorHelper.this;
                        videoMonitorHelper2.setDistanceProtection(videoMonitorHelper2.f);
                        VideoMonitorHelper videoMonitorHelper3 = VideoMonitorHelper.this;
                        videoMonitorHelper3.setQuickEnterChildMode(videoMonitorHelper3.g);
                        SharedPreferences sharedPreferences = VideoMonitorHelper.this.b.getSharedPreferences("shared_preferences_video_monitor", 0);
                        VideoMonitorHelper.this.setLowLevelFunctionStateToSP(sharedPreferences, "sp_key_is_gesture_control_open", false);
                        VideoMonitorHelper.this.setLowLevelFunctionStateToSP(sharedPreferences, "sp_key_is_distance_protection_open", false);
                        VideoMonitorHelper.this.setLowLevelFunctionStateToSP(sharedPreferences, "sp_key_is_quick_enter_child_mode_open", false);
                    }
                }
            };
            this.b.getContentResolver().registerContentObserver(uriFor, false, this.c);
        }
    }

    public void b() {
        this.d = Settings.Global.getInt(this.b.getContentResolver(), "mi_camera_monitor_running", 0);
        Logger logger = L.videoMonitor;
        logger.d("[VideoMonitorHelper] updateVideoMonitorState: " + this.d);
    }

    public boolean isVideoMonitorRunning() {
        boolean z = this.d == 1;
        Logger logger = L.videoMonitor;
        logger.d("[VideoMonitorHelper] isVideoMonitorRunning: " + z);
        return z && hasVideoMonitorFunction();
    }

    public boolean hasVideoMonitorFunction() {
        return Hardware.isX10() || Hardware.isX6A();
    }
}
