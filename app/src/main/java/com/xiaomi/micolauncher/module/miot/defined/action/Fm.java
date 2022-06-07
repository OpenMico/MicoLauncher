package com.xiaomi.micolauncher.module.miot.defined.action;

import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.miot.typedef.urn.ActionType;

/* loaded from: classes3.dex */
public class Fm extends MicoActionOperable {
    public static final ActionType TYPE = SpeakerDefined.Action.Fm.toActionType();

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int aiid() {
        return 2;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int siid() {
        return 7;
    }

    public Fm() {
        super(TYPE);
    }
}
