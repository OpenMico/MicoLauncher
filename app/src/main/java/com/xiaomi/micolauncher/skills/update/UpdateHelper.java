package com.xiaomi.micolauncher.skills.update;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.ubus.UbusManager;
import com.xiaomi.micolauncher.module.QuickSettingHelper;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.countdown.model.CountDownModel;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.BluetoothControlAction;

/* loaded from: classes3.dex */
public class UpdateHelper {
    private boolean a;

    public void enableEverything(BaseActivity baseActivity) {
        L.update.d("enableEverything");
        SpeechManager.getInstance().start();
        QuickSettingHelper.enable(baseActivity, true);
        UbusManager.getInstance().enable();
        AlarmModel.getInstance().enable();
        if (this.a) {
            BluetoothSettingManager.openBluetooth(BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_CODE);
        }
        if (MiotProvisionManagerWrapper.isMeshEnable()) {
            MiotProvisionManagerWrapper.getInstance().resumeMesh();
        }
        baseActivity.toggleSwipeBackFeature(true);
        baseActivity.scheduleToClose(BaseActivity.DEFAULT_CLOSE_DURATION);
    }

    public void disableEverything(BaseActivity baseActivity) {
        L.update.d("disableEverything");
        SpeechManager.getInstance().stop();
        QuickSettingHelper.enable(baseActivity, false);
        UbusManager.getInstance().disable();
        CountDownModel.getInstance().cancel();
        AlarmModel.getInstance().disable();
        BluetoothSettingManager.init(baseActivity.getApplication());
        this.a = BluetoothSettingManager.isSettingActivityOpened();
        if (this.a && BluetoothSettingManager.getInstance() != null) {
            BluetoothSettingManager.closeBluetooth(BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_CODE);
        }
        if (MiotProvisionManagerWrapper.isMeshEnable()) {
            MiotProvisionManagerWrapper.getInstance().stopMesh();
        }
        baseActivity.toggleSwipeBackFeature(false);
        baseActivity.removeCloseScheduler();
    }

    public static boolean isForceUpdate(String str) {
        a aVar = (a) Gsons.getGson().fromJson(str, (Class<Object>) a.class);
        return aVar != null && aVar.a();
    }

    /* loaded from: classes3.dex */
    private static class a {
        @SerializedName("forceUpdate")
        private boolean a;

        private a() {
        }

        boolean a() {
            return this.a;
        }
    }
}
