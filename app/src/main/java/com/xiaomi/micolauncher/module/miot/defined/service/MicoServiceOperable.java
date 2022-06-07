package com.xiaomi.micolauncher.module.miot.defined.service;

import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.device.operable.ServiceOperable;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.urn.ServiceType;

/* loaded from: classes3.dex */
public abstract class MicoServiceOperable extends ServiceOperable {
    protected abstract int instanceID();

    public MicoServiceOperable(ServiceType serviceType) {
        super(serviceType);
        if (Hardware.isSupportSpec()) {
            super.setInstanceID(instanceID());
        }
    }

    @Override // com.xiaomi.miot.typedef.device.operable.ServiceOperable, com.xiaomi.miot.typedef.handler.OperationHandler
    public final MiotError onAction(ActionInfo actionInfo) {
        if (RomUpdateAdapter.getInstance().isUpdateOngoing()) {
            return super.onAction(actionInfo);
        }
        return executeAction(actionInfo);
    }

    protected MiotError executeAction(ActionInfo actionInfo) {
        return super.onAction(actionInfo);
    }
}
