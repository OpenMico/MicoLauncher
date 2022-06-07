package autodispose2.lifecycle;

import autodispose2.lifecycle.TestLifecycleScopeProvider;

/* compiled from: lambda */
/* renamed from: autodispose2.lifecycle.-$$Lambda$TestLifecycleScopeProvider$X85VxIDdra34zjFKi_R5D4_Vvt0  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$TestLifecycleScopeProvider$X85VxIDdra34zjFKi_R5D4_Vvt0 implements CorrespondingEventsFunction {
    public static final /* synthetic */ $$Lambda$TestLifecycleScopeProvider$X85VxIDdra34zjFKi_R5D4_Vvt0 INSTANCE = new $$Lambda$TestLifecycleScopeProvider$X85VxIDdra34zjFKi_R5D4_Vvt0();

    private /* synthetic */ $$Lambda$TestLifecycleScopeProvider$X85VxIDdra34zjFKi_R5D4_Vvt0() {
    }

    @Override // autodispose2.lifecycle.CorrespondingEventsFunction, io.reactivex.rxjava3.functions.Function
    public final Object apply(Object obj) {
        TestLifecycleScopeProvider.TestLifecycle a;
        a = TestLifecycleScopeProvider.a((TestLifecycleScopeProvider.TestLifecycle) obj);
        return a;
    }
}
