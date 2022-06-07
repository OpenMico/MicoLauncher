package com.xiaomi.micolauncher.module.miot.defined.action;

import com.xiaomi.mico.common.Hardware;
import com.xiaomi.miot.typedef.device.operable.ActionOperable;
import com.xiaomi.miot.typedef.urn.ActionType;

/* loaded from: classes3.dex */
public abstract class MicoActionOperable extends ActionOperable {
    protected abstract int aiid();

    protected abstract int siid();

    public MicoActionOperable(ActionType actionType) {
        super(actionType);
        if (Hardware.isSupportSpec()) {
            super.setServiceInstanceID(siid());
            super.setDeviceInstanceID(0);
            super.setInstanceID(aiid());
        }
    }
}
