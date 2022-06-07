package com.xiaomi.micolauncher.module.miot.defined.action;

import com.xiaomi.micolauncher.module.miot.defined.SpeakerDefined;
import com.xiaomi.micolauncher.module.miot.defined.property.SilentExecution;
import com.xiaomi.micolauncher.module.miot.defined.property.Text;
import com.xiaomi.miot.typedef.urn.ActionType;

/* loaded from: classes3.dex */
public class DirectivesIntelligent extends MicoActionOperable {
    public static final ActionType TYPE = SpeakerDefined.Action.Directives.toActionType();

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int aiid() {
        return 4;
    }

    @Override // com.xiaomi.micolauncher.module.miot.defined.action.MicoActionOperable
    protected int siid() {
        return 7;
    }

    public DirectivesIntelligent() {
        super(TYPE);
        super.addArgument(Text.TYPE.toString());
        super.addArgument(SilentExecution.TYPE.toString());
    }
}
