package com.xiaomi.micolauncher.module.miot.defined.action;

import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.property.DoorbellDid;
import com.xiaomi.micolauncher.module.miot.defined.property.DoorbellState;
import com.xiaomi.micolauncher.module.miot.defined.property.DoorbellTimeStamp;
import com.xiaomi.micolauncher.module.miot.defined.property.DoorbellTraceId;
import com.xiaomi.miot.typedef.urn.ActionType;

/* loaded from: classes3.dex */
public class Doorbell extends MicoActionOperable {
    public static final ActionType TYPE = SpeakerDefined.Action.Doorbell.toActionType();

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int aiid() {
        return 1;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int siid() {
        return 1;
    }

    public Doorbell() {
        super(TYPE);
        super.addArgument(DoorbellDid.TYPE.toString());
        super.addArgument(DoorbellState.TYPE.toString());
        super.addArgument(DoorbellTimeStamp.TYPE.toString());
        super.addArgument(DoorbellTraceId.TYPE.toString());
    }
}
