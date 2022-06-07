package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Sys;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.ChildModeConfig;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.HourlyChimeEvent;
import com.xiaomi.micolauncher.common.event.HourlyChimeQueryEvent;
import com.xiaomi.micolauncher.common.event.NearByWakeupEvent;
import com.xiaomi.micolauncher.common.event.QuitChildModeDialogEvent;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.NearByWakeupHelper;
import com.xiaomi.micolauncher.common.ubus.storage.ChildModeStorage;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.camera2.action.Camera2ControlAction;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.module.setting.utils.SleepMode;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeUtils;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.OpenDistanceProtectModeEvent;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.OpenEyeProtectModeEvent;

/* loaded from: classes3.dex */
public class SystemSetPropertyOperation extends BaseOperation<Instruction<Sys.SetProperty>> {
    public SystemSetPropertyOperation(Instruction instruction) {
        super(instruction);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        char c;
        Sys.SetProperty setProperty = (Sys.SetProperty) this.instruction.getPayload();
        String name = setProperty.getName();
        String value = setProperty.getValue();
        switch (name.hashCode()) {
            case -2119071107:
                if (name.equals("TIMER_MODE")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -661550649:
                if (name.equals("EYE_PROTECTION")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -158038815:
                if (name.equals("AUTO_BRIGHTNESS")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -87148150:
                if (name.equals("NIGHT_MODE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 102400995:
                if (name.equals("CHILDREN_MODE")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 476330077:
                if (name.equals("NEAR_FIELD_AWAKENING")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1037122147:
                if (name.equals("SCREEN_DISTANCE_MONITOR")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1896844514:
                if (name.equals("SCREEN_SAVER")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if ("ON".equalsIgnoreCase(value)) {
                    a(true);
                } else if ("OFF".equalsIgnoreCase(value)) {
                    a(false);
                }
                return BaseOperation.OpState.STATE_SUCCESS;
            case 1:
                if ("ON".equalsIgnoreCase(value)) {
                    SleepMode.getInstance().setDelaySetVolumeWhenEnterSleep(true);
                    SleepMode.getInstance().doEnterSleep(true, false);
                } else if ("OFF".equalsIgnoreCase(value)) {
                    SleepMode.getInstance().doExitSleep(true, false);
                }
                return BaseOperation.OpState.STATE_SUCCESS;
            case 2:
                if ("ON".equalsIgnoreCase(value)) {
                    Screen.getInstance().openLockScreen();
                } else if ("OFF".equalsIgnoreCase(value)) {
                    Screen.getInstance().closeLockScreen();
                }
                return BaseOperation.OpState.STATE_SUCCESS;
            case 3:
                if ("ON".equalsIgnoreCase(value)) {
                    b(true);
                    EventBusRegistry.getEventBus().post(new OpenEyeProtectModeEvent(true));
                } else if ("OFF".equalsIgnoreCase(value)) {
                    b(false);
                    EventBusRegistry.getEventBus().post(new OpenEyeProtectModeEvent(false));
                }
                return BaseOperation.OpState.STATE_SUCCESS;
            case 4:
                if ("ON".equalsIgnoreCase(value)) {
                    c(true);
                } else if ("OFF".equalsIgnoreCase(value)) {
                    c(false);
                }
                return BaseOperation.OpState.STATE_SUCCESS;
            case 5:
                if ("ON".equalsIgnoreCase(value)) {
                    d(true);
                } else if ("OFF".equalsIgnoreCase(value)) {
                    d(false);
                }
                return BaseOperation.OpState.STATE_SUCCESS;
            case 6:
                if ("ON".equalsIgnoreCase(value)) {
                    e(true);
                } else if ("OFF".equalsIgnoreCase(value)) {
                    e(false);
                }
                return BaseOperation.OpState.STATE_SUCCESS;
            case 7:
                if ("ON".equalsIgnoreCase(value)) {
                    MicoSettings.setBrightnessMode(getContext(), true);
                } else if ("OFF".equalsIgnoreCase(value)) {
                    MicoSettings.setBrightnessMode(getContext(), false);
                }
                return BaseOperation.OpState.STATE_SUCCESS;
            default:
                return BaseOperation.OpState.STATE_FAIL;
        }
    }

    private void a(boolean z) {
        ChildModeConfig load = ChildModeStorage.getInstance().load(getContext());
        if (z) {
            if (!load.isChildMode) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$SystemSetPropertyOperation$W9pUDCCw6Gm6aE3TSyhU_sbgdus
                    @Override // java.lang.Runnable
                    public final void run() {
                        SystemSetPropertyOperation.this.a();
                    }
                });
            }
        } else if (!load.isChildMode) {
            L.ope.d("is not child mode");
            SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.not_child_mode_hit));
        } else if (Hardware.hasCameraCapability(getContext())) {
            EventBusRegistry.getEventBus().post(new QuitChildModeDialogEvent());
        } else {
            SpeechManager.getInstance().ttsRequest(getContext().getString(R.string.child_mode_exit_msg2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        ChildModeManager.getManager().showOpenChildModeDialog(getContext(), true);
    }

    private void b(boolean z) {
        SystemSetting.setKeyEyeProtectionModeEnable(z);
        ChildModeUtils.setEyeProtectionMode(z);
    }

    private void c(boolean z) {
        SystemSetting.setKeyDistanceProtectionEnable(z);
        EventBusRegistry.getEventBus().post(new OpenDistanceProtectModeEvent(z));
        if (z) {
            Camera2VisualRecognitionManager.getInstance(getContext()).requestStartAction(Camera2ControlAction.EnumAction.ACTION_DISTANCE);
        } else {
            Camera2VisualRecognitionManager.getInstance(getContext()).requestStopAction(Camera2ControlAction.EnumAction.ACTION_DISTANCE);
        }
    }

    private void d(boolean z) {
        if (SpeechConfig.supportNearByWakeup()) {
            MicoSettings.setNearbyWakeupEnable(getContext(), z);
            EventBusRegistry.getEventBus().post(new NearByWakeupEvent());
            NearByWakeupHelper.setNearbyStatus(z);
        }
    }

    private void e(boolean z) {
        L.hourlychime.d("SystemSetPropertyOperation open: %b", Boolean.valueOf(z));
        if (SpeechConfig.supportHourlyChime()) {
            SystemSetting.setHourlyChimeEnable(z);
            EventBusRegistry.getEventBus().post(new HourlyChimeEvent());
            EventBusRegistry.getEventBus().post(new HourlyChimeQueryEvent());
        }
    }
}
