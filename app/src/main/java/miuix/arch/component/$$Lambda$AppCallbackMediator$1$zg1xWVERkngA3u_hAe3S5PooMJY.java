package miuix.arch.component;

import android.os.MessageQueue;
import miuix.arch.component.AppCallbackMediator;

/* compiled from: lambda */
/* renamed from: miuix.arch.component.-$$Lambda$AppCallbackMediator$1$zg1xWVERkngA3u_hAe3S5PooMJY  reason: invalid class name */
/* loaded from: classes5.dex */
public final /* synthetic */ class $$Lambda$AppCallbackMediator$1$zg1xWVERkngA3u_hAe3S5PooMJY implements MessageQueue.IdleHandler {
    public static final /* synthetic */ $$Lambda$AppCallbackMediator$1$zg1xWVERkngA3u_hAe3S5PooMJY INSTANCE = new $$Lambda$AppCallbackMediator$1$zg1xWVERkngA3u_hAe3S5PooMJY();

    private /* synthetic */ $$Lambda$AppCallbackMediator$1$zg1xWVERkngA3u_hAe3S5PooMJY() {
    }

    @Override // android.os.MessageQueue.IdleHandler
    public final boolean queueIdle() {
        boolean b;
        b = AppCallbackMediator.AnonymousClass1.b();
        return b;
    }
}
