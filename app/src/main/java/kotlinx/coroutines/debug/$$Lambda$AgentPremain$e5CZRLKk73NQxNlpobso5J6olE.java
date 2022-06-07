package kotlinx.coroutines.debug;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/* compiled from: lambda */
/* renamed from: kotlinx.coroutines.debug.-$$Lambda$AgentPremain$e5CZRLKk73NQ-xNlpobso5J6olE  reason: invalid class name */
/* loaded from: classes5.dex */
public final /* synthetic */ class $$Lambda$AgentPremain$e5CZRLKk73NQxNlpobso5J6olE implements SignalHandler {
    public static final /* synthetic */ $$Lambda$AgentPremain$e5CZRLKk73NQxNlpobso5J6olE INSTANCE = new $$Lambda$AgentPremain$e5CZRLKk73NQxNlpobso5J6olE();

    private /* synthetic */ $$Lambda$AgentPremain$e5CZRLKk73NQxNlpobso5J6olE() {
    }

    public final void handle(Signal signal) {
        AgentPremain.a(signal);
    }
}
