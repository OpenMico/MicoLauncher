package com.xiaomi.micolauncher.skills.voip.model;

import android.content.Context;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;

/* loaded from: classes3.dex */
public class VoipModel {
    public static final String CALL_LOG_TYPE_CMCC = "CMCC";
    public static final String XIAOXUN_PACKAGE_NAME = "com.xiaoxun.xun";
    private static VoipModel a = new VoipModel();
    private volatile VoipStatus b = VoipStatus.Disconnected;
    private volatile boolean c = false;
    private volatile boolean d = false;
    private Context e = null;

    /* loaded from: classes3.dex */
    public enum VoipStatus {
        Disconnected(0),
        Ringing(1),
        Connected(2),
        Connecting(3);
        
        private final int code;

        VoipStatus(int i) {
            this.code = i;
        }

        public int getCode() {
            return this.code;
        }
    }

    private VoipModel() {
    }

    public static VoipModel getInstance() {
        if (a == null) {
            synchronized (VoipModel.class) {
                a = new VoipModel();
            }
        }
        return a;
    }

    public void init(Context context) {
        L.voip.i("init,context=%s,deviceId=%s", context, SystemSetting.getDeviceID());
        this.e = context;
    }

    public Context getContext() {
        return this.e;
    }

    public void setVoipRunning(boolean z) {
        this.c = z;
    }

    public boolean isVoipActive() {
        return this.c;
    }

    public boolean isVoipConnected() {
        return this.b == VoipStatus.Connected;
    }

    public void setIsVoipTriggerClosingVisualAlgorithm(boolean z) {
        this.d = z;
    }

    public boolean isVoipTriggerClosingVisualAlgorithm() {
        return this.d;
    }
}
