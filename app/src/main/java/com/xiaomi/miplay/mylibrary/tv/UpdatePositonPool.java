package com.xiaomi.miplay.mylibrary.tv;

import com.xiaomi.miplay.mylibrary.DeviceManager;
import com.xiaomi.miplay.mylibrary.MiDevice;
import com.xiaomi.miplay.mylibrary.mirror.CmdSessionControl;
import com.xiaomi.miplay.mylibrary.session.ActiveAudioSessionManager;
import com.xiaomi.miplay.mylibrary.session.ActiveSessionRecord;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class UpdatePositonPool {
    private static final String a = "UpdatePositonPool";
    private ScheduledThreadPoolExecutor b;
    private volatile boolean c;
    private DeviceManager d;
    private Map<String, CmdSessionControl> e;
    private ActiveAudioSessionManager f;

    private UpdatePositonPool() {
    }

    public static UpdatePositonPool getInstance() {
        return UpdatePositonPoolSingleton.a;
    }

    /* loaded from: classes4.dex */
    public static class UpdatePositonPoolSingleton {
        private static final UpdatePositonPool a = new UpdatePositonPool();
    }

    public void setmDeviceManager(DeviceManager deviceManager) {
        this.d = deviceManager;
    }

    public void setmActiveAudioSessionManager(ActiveAudioSessionManager activeAudioSessionManager) {
        this.f = activeAudioSessionManager;
    }

    public void setCmdSessionControlMap(Map<String, CmdSessionControl> map) {
        this.e = map;
    }

    public boolean isStartTimered() {
        String str = a;
        Logger.i(str, "mstartTimered:" + this.c, new Object[0]);
        return this.c;
    }

    public void startTimer() {
        String str = a;
        Logger.i(str, "startTimer :" + this.c, new Object[0]);
        if (!this.c) {
            this.c = true;
            this.b = new ScheduledThreadPoolExecutor(1);
            this.b.scheduleAtFixedRate(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.tv.UpdatePositonPool.1
                @Override // java.lang.Runnable
                public void run() {
                    if (UpdatePositonPool.this.d != null) {
                        for (MiDevice miDevice : UpdatePositonPool.this.d.getMiDeviceList()) {
                            String str2 = UpdatePositonPool.a;
                            Logger.d(str2, "deviceInfo:" + miDevice, new Object[0]);
                            if (miDevice.getDeviceType() == 2 || miDevice.getDeviceType() == 16) {
                                if (miDevice.getDeviceConnectState() == 1 && UpdatePositonPool.this.e != null) {
                                    long position = UpdatePositonPool.this.getPosition();
                                    String str3 = UpdatePositonPool.a;
                                    Logger.i(str3, "position:" + position, new Object[0]);
                                    if (UpdatePositonPool.this.e.get(miDevice.getMac()) == null) {
                                        Logger.i(UpdatePositonPool.a, "CmdSessionControl  on a null object", new Object[0]);
                                    } else {
                                        ((CmdSessionControl) UpdatePositonPool.this.e.get(miDevice.getMac())).setPosition(position);
                                    }
                                }
                            }
                        }
                        return;
                    }
                    Logger.i(UpdatePositonPool.a, "mDeviceManager is null", new Object[0]);
                }
            }, 0L, 1000L, TimeUnit.MILLISECONDS);
        }
    }

    public void stopTimer() {
        Logger.i(a, "stopTimer.", new Object[0]);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.b;
        if (scheduledThreadPoolExecutor != null) {
            scheduledThreadPoolExecutor.shutdown();
            this.b = null;
        }
        this.c = false;
    }

    public long getPosition() {
        Logger.i(a, "getPosition.", new Object[0]);
        ActiveSessionRecord topActiveSessionRecord = this.f.getTopActiveSessionRecord();
        if (topActiveSessionRecord != null) {
            return topActiveSessionRecord.getAudioMediaController().getPosition();
        }
        Logger.i(a, "record is null", new Object[0]);
        return 0L;
    }
}
