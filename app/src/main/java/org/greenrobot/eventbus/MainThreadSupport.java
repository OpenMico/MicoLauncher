package org.greenrobot.eventbus;

import android.os.Looper;

/* loaded from: classes5.dex */
public interface MainThreadSupport {
    e createPoster(EventBus eventBus);

    boolean isMainThread();

    /* loaded from: classes5.dex */
    public static class AndroidHandlerMainThreadSupport implements MainThreadSupport {
        private final Looper a;

        public AndroidHandlerMainThreadSupport(Looper looper) {
            this.a = looper;
        }

        @Override // org.greenrobot.eventbus.MainThreadSupport
        public boolean isMainThread() {
            return this.a == Looper.myLooper();
        }

        @Override // org.greenrobot.eventbus.MainThreadSupport
        public e createPoster(EventBus eventBus) {
            return new HandlerPoster(eventBus, this.a, 10);
        }
    }
}
