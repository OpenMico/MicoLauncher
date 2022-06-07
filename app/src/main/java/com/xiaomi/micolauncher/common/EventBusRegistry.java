package com.xiaomi.micolauncher.common;

import com.xiaomi.mico.utils.ThreadUtil;
import java.util.logging.Level;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;

/* loaded from: classes3.dex */
public class EventBusRegistry {
    private final b a;

    private EventBusRegistry() {
        this.a = new b();
    }

    /* loaded from: classes3.dex */
    public static class a {
        private static final EventBusRegistry a = new EventBusRegistry();
    }

    private static EventBusRegistry a() {
        return a.a;
    }

    private EventBus b() {
        return this.a;
    }

    public static EventBus getEventBus() {
        return a().b();
    }

    /* loaded from: classes3.dex */
    public static class b extends EventBus {
        private final EventBus b = EventBus.builder().executorService(ThreadUtil.getComputationThreadPool()).logger(new Logger() { // from class: com.xiaomi.micolauncher.common.EventBusRegistry.b.1
            @Override // org.greenrobot.eventbus.Logger
            public void log(Level level, String str) {
                L.eventbus.log(b.this.a(level), str);
            }

            @Override // org.greenrobot.eventbus.Logger
            public void log(Level level, String str, Throwable th) {
                L.eventbus.log(b.this.a(level), str, th);
            }
        }).logSubscriberExceptions(true).build();

        b() {
        }

        public int a(Level level) {
            if (level == Level.FINEST) {
                return 2;
            }
            if (level == Level.INFO) {
                return 4;
            }
            if (level == Level.WARNING) {
                return 5;
            }
            return level == Level.SEVERE ? 6 : 3;
        }

        @Override // org.greenrobot.eventbus.EventBus
        public void register(Object obj) {
            this.b.register(obj);
        }

        @Override // org.greenrobot.eventbus.EventBus
        public boolean isRegistered(Object obj) {
            return this.b.isRegistered(obj);
        }

        @Override // org.greenrobot.eventbus.EventBus
        public void unregister(Object obj) {
            this.b.unregister(obj);
        }

        @Override // org.greenrobot.eventbus.EventBus
        public void post(Object obj) {
            this.b.post(obj);
            L.eventbus.d("post event=%s", obj.getClass().getSimpleName());
        }

        @Override // org.greenrobot.eventbus.EventBus
        public void cancelEventDelivery(Object obj) {
            this.b.cancelEventDelivery(obj);
        }

        @Override // org.greenrobot.eventbus.EventBus
        public void postSticky(Object obj) {
            this.b.postSticky(obj);
            L.eventbus.d("postSticky %s", obj.getClass().getSimpleName());
        }

        @Override // org.greenrobot.eventbus.EventBus
        public <T> T getStickyEvent(Class<T> cls) {
            return (T) this.b.getStickyEvent(cls);
        }

        @Override // org.greenrobot.eventbus.EventBus
        public <T> T removeStickyEvent(Class<T> cls) {
            return (T) this.b.removeStickyEvent((Class<Object>) cls);
        }

        @Override // org.greenrobot.eventbus.EventBus
        public boolean removeStickyEvent(Object obj) {
            return this.b.removeStickyEvent(obj);
        }

        @Override // org.greenrobot.eventbus.EventBus
        public void removeAllStickyEvents() {
            this.b.removeAllStickyEvents();
        }

        @Override // org.greenrobot.eventbus.EventBus
        public boolean hasSubscriberForEvent(Class<?> cls) {
            return this.b.hasSubscriberForEvent(cls);
        }
    }
}
