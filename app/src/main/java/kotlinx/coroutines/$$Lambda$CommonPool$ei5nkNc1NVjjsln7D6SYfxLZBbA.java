package kotlinx.coroutines;

import java.util.concurrent.Executor;

/* compiled from: lambda */
/* renamed from: kotlinx.coroutines.-$$Lambda$CommonPool$ei5nkNc1NVjjsln7D6SYfxLZBbA  reason: invalid class name */
/* loaded from: classes5.dex */
public final /* synthetic */ class $$Lambda$CommonPool$ei5nkNc1NVjjsln7D6SYfxLZBbA implements Executor {
    public static final /* synthetic */ $$Lambda$CommonPool$ei5nkNc1NVjjsln7D6SYfxLZBbA INSTANCE = new $$Lambda$CommonPool$ei5nkNc1NVjjsln7D6SYfxLZBbA();

    private /* synthetic */ $$Lambda$CommonPool$ei5nkNc1NVjjsln7D6SYfxLZBbA() {
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        CommonPool.a(runnable);
    }
}
