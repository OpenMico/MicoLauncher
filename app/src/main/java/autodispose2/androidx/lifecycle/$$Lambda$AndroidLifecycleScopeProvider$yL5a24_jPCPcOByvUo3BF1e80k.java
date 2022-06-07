package autodispose2.androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import autodispose2.lifecycle.CorrespondingEventsFunction;

/* compiled from: lambda */
/* renamed from: autodispose2.androidx.lifecycle.-$$Lambda$AndroidLifecycleScopeProvider$yL5a24_jPCPcOByvU-o3BF1e80k  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$AndroidLifecycleScopeProvider$yL5a24_jPCPcOByvUo3BF1e80k implements CorrespondingEventsFunction {
    public static final /* synthetic */ $$Lambda$AndroidLifecycleScopeProvider$yL5a24_jPCPcOByvUo3BF1e80k INSTANCE = new $$Lambda$AndroidLifecycleScopeProvider$yL5a24_jPCPcOByvUo3BF1e80k();

    private /* synthetic */ $$Lambda$AndroidLifecycleScopeProvider$yL5a24_jPCPcOByvUo3BF1e80k() {
    }

    @Override // autodispose2.lifecycle.CorrespondingEventsFunction, io.reactivex.rxjava3.functions.Function
    public final Object apply(Object obj) {
        Lifecycle.Event a;
        a = AndroidLifecycleScopeProvider.a((Lifecycle.Event) obj);
        return a;
    }
}
