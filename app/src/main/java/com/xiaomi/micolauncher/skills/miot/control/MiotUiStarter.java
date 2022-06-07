package com.xiaomi.micolauncher.skills.miot.control;

import android.content.Context;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.lock.LockSetting;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.StartMiotCameraUiEvent;
import com.xiaomi.micolauncher.module.setting.widget.LockAppDialog;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.DevicesSetPincodeCallback;
import com.xiaomi.miot.support.ICameraStatus;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.core.MiotDeviceCore;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class MiotUiStarter {
    private Context a;

    public MiotUiStarter(Context context) {
        this.a = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartMiotCameraUiEvent(StartMiotCameraUiEvent startMiotCameraUiEvent) {
        final String str = startMiotCameraUiEvent.encryptDid;
        final ICameraStatus iCameraStatus = startMiotCameraUiEvent.iCameraStatus;
        DeviceInfo deviceByEncryptDid = MiotDeviceCore.with(this.a).getDeviceByEncryptDid(str);
        if (deviceByEncryptDid == null) {
            L.miot.i("deviceInfo is empty encryptDid=%s", str);
            return;
        }
        String str2 = deviceByEncryptDid.did;
        if (!Hardware.isBigScreen() || !LockSetting.getHasLock(this.a, LockSetting.MI_JIA_APP)) {
            a(this.a, str, iCameraStatus);
        } else {
            MiotManager.deviceIsSetPinCode(str2, new DevicesSetPincodeCallback() { // from class: com.xiaomi.micolauncher.skills.miot.control.-$$Lambda$MiotUiStarter$LT7BvEm9SJDnE1TWtlrE1_-bo24
                @Override // com.xiaomi.miot.support.DevicesSetPincodeCallback
                public final void onResult(boolean z) {
                    MiotUiStarter.this.a(str, iCameraStatus, z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final String str, final ICameraStatus iCameraStatus, boolean z) {
        if (z) {
            a(this.a, str, iCameraStatus);
            return;
        }
        ThreadUtil.verifyMainThread();
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.miot.control.-$$Lambda$MiotUiStarter$2_X82Ft5x-YoNe13c3m5PLYIPEE
            @Override // java.lang.Runnable
            public final void run() {
                MiotUiStarter.this.a(str, iCameraStatus);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final String str, final ICameraStatus iCameraStatus) {
        new LockAppDialog(this.a, new LockAppDialog.OnLockAppPassCorrectListener() { // from class: com.xiaomi.micolauncher.skills.miot.control.-$$Lambda$MiotUiStarter$TX60K2fnGxRxNNP421mrxHZpokA
            @Override // com.xiaomi.micolauncher.module.setting.widget.LockAppDialog.OnLockAppPassCorrectListener
            public final void onPassCorrect() {
                MiotUiStarter.this.b(str, iCameraStatus);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str, ICameraStatus iCameraStatus) {
        a(this.a, str, iCameraStatus);
    }

    private void a(Context context, String str, ICameraStatus iCameraStatus) {
        MiotManager.showDevicePage(context, str, "", iCameraStatus);
    }
}
