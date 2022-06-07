package com.xiaomi.miot.typedef.device.operable;

import com.xiaomi.miot.typedef.device.Action;
import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.urn.ActionType;

/* loaded from: classes3.dex */
public class ActionOperable extends Action {
    public ActionOperable(ActionType actionType) {
        super(actionType);
    }

    public MiotError onAction(ActionInfo actionInfo) {
        if (actionInfo.getInstanceID() != getInstanceID()) {
            return MiotError.IOT_RESOURCE_NOT_EXIST;
        }
        return null;
    }
}
