package com.xiaomi.micolauncher.module.miot.defined.action;

import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.property.Text;
import com.xiaomi.miot.typedef.urn.ActionType;

/* loaded from: classes3.dex */
public class WakeUp extends MicoActionOperable {
    public static final ActionType TYPE = SpeakerDefined.Action.WakeUp.toActionType();

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int aiid() {
        return 1;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int siid() {
        return 7;
    }

    public WakeUp() {
        super(TYPE);
        super.addArgument(Text.TYPE.toString());
    }
}
