package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.BluetoothControlAction;

/* loaded from: classes3.dex */
public class BluetoothOperation extends BaseOperation {
    public BluetoothOperation(Instruction instruction) {
        super(instruction);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        char c;
        BaseOperation.OpState opState = BaseOperation.OpState.STATE_FAIL;
        String fullName = fullName();
        switch (fullName.hashCode()) {
            case -876418020:
                if (fullName.equals(AIApiConstants.Bluetooth.Disconnect)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -738784588:
                if (fullName.equals(AIApiConstants.Bluetooth.Switch)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -711740740:
                if (fullName.equals(AIApiConstants.Bluetooth.TurnOn)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -589126606:
                if (fullName.equals(AIApiConstants.Bluetooth.TurnOff)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1327732074:
                if (fullName.equals(AIApiConstants.Bluetooth.Connect)) {
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
                if (BluetoothSettingManager.getInstance() == null) {
                    return opState;
                }
                BluetoothSettingManager.getInstance().openBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_VOICE_QUERY);
                SpeechManager.getInstance().ttsRequest(MicoApplication.getApp().getString(BluetoothSettingManager.getInstance().isEnabled() ? R.string.local_tts_bluetooth_opened : R.string.local_tts_bluetooth_open_failed));
                return BaseOperation.OpState.STATE_SUCCESS;
            case 1:
                if (BluetoothSettingManager.getInstance() == null) {
                    return opState;
                }
                BluetoothSettingManager.getInstance().closeBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_VOICE_QUERY);
                SpeechManager.getInstance().ttsRequest(MicoApplication.getApp().getString(R.string.local_tts_bluetooth_closed));
                return BaseOperation.OpState.STATE_SUCCESS;
            case 2:
                if (BluetoothSettingManager.getInstance() == null) {
                    return opState;
                }
                BluetoothSettingManager.getInstance().connectBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_VOICE_QUERY, null);
                return BaseOperation.OpState.STATE_SUCCESS;
            case 3:
                if (BluetoothSettingManager.getInstance() == null) {
                    return opState;
                }
                BluetoothSettingManager.getInstance().disconnectBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_DISCONNECT_BY_VOICE_QUERY);
                return BaseOperation.OpState.STATE_SUCCESS;
            case 4:
                if (BluetoothSettingManager.getInstance() == null) {
                    return opState;
                }
                if (BluetoothSettingManager.getInstance().isEnabled()) {
                    BluetoothSettingManager.getInstance().closeBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_VOICE_QUERY);
                } else {
                    BluetoothSettingManager.getInstance().openBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_VOICE_QUERY);
                }
                return BaseOperation.OpState.STATE_SUCCESS;
            default:
                return opState;
        }
    }
}
