package com.xiaomi.micolauncher.module.miot.defined.action;

import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.miot.typedef.urn.ActionType;

/* loaded from: classes3.dex */
public class Pause extends MicoActionOperable {
    public static final ActionType TYPE = SpeakerDefined.Action.Pause.toActionType();

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int aiid() {
        return 3;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int siid() {
        return 3;
    }

    public Pause() {
        super(TYPE);
    }
}
