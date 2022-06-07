package com.xiaomi.micolauncher.module.miot.defined.action;

import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.miot.typedef.urn.ActionType;

/* loaded from: classes3.dex */
public class Play extends MicoActionOperable {
    public static final ActionType TYPE = SpeakerDefined.Action.Play.toActionType();

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int aiid() {
        return 2;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int siid() {
        return 3;
    }

    public Play() {
        super(TYPE);
    }
}
