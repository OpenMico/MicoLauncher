package com.xiaomi.micolauncher.module.miot.defined.action;

import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.property.GatewayStatusResponse;
import com.xiaomi.miot.typedef.urn.ActionType;

/* loaded from: classes3.dex */
public class GatewayStatus extends MicoActionOperable {
    public static final ActionType TYPE = SpeakerDefined.Action.bt_gateway_status.toActionType();

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int aiid() {
        return 3;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int siid() {
        return 0;
    }

    public GatewayStatus() {
        super(TYPE);
        super.addResult(GatewayStatusResponse.TYPE.toString());
    }
}
