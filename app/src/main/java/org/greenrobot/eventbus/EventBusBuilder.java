package org.greenrobot.eventbus;

import android.os.Looper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.greenrobot.eventbus.Logger;
import org.greenrobot.eventbus.MainThreadSupport;
import org.greenrobot.eventbus.meta.SubscriberInfoIndex;

/* loaded from: classes5.dex */
public class EventBusBuilder {
    private static final ExecutorService n = Executors.newCachedThreadPool();
    boolean e;
    boolean g;
    boolean h;
    List<Class<?>> j;
    List<SubscriberInfoIndex> k;
    Logger l;
    MainThreadSupport m;
    boolean a = true;
    boolean b = true;
    boolean c = true;
    boolean d = true;
    boolean f = true;
    ExecutorService i = n;

    public EventBusBuilder logSubscriberExceptions(boolean z) {
        this.a = z;
        return this;
    }

    public EventBusBuilder logNoSubscriberMessages(boolean z) {
        this.b = z;
        return this;
    }

    public EventBusBuilder sendSubscriberExceptionEvent(boolean z) {
        this.c = z;
        return this;
    }

    public EventBusBuilder sendNoSubscriberEvent(boolean z) {
        this.d = z;
        return this;
    }

    public EventBusBuilder throwSubscriberException(boolean z) {
        this.e = z;
        return this;
    }

    public EventBusBuilder eventInheritance(boolean z) {
        this.f = z;
        return this;
    }

    public EventBusBuilder executorService(ExecutorService executorService) {
        this.i = executorService;
        return this;
    }

    public EventBusBuilder skipMethodVerificationFor(Class<?> cls) {
        if (this.j == null) {
            this.j = new ArrayList();
        }
        this.j.add(cls);
        return this;
    }

    public EventBusBuilder ignoreGeneratedIndex(boolean z) {
        this.g = z;
        return this;
    }

    public EventBusBuilder strictMethodVerification(boolean z) {
        this.h = z;
        return this;
    }

    public EventBusBuilder addIndex(SubscriberInfoIndex subscriberInfoIndex) {
        if (this.k == null) {
            this.k = new ArrayList();
        }
        this.k.add(subscriberInfoIndex);
        return this;
    }

    public EventBusBuilder logger(Logger logger) {
        this.l = logger;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Logger a() {
        Logger logger = this.l;
        return logger != null ? logger : (!Logger.AndroidLogger.isAndroidLogAvailable() || c() == null) ? new Logger.SystemOutLogger() : new Logger.AndroidLogger("EventBus");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MainThreadSupport b() {
        Object c;
        MainThreadSupport mainThreadSupport = this.m;
        if (mainThreadSupport != null) {
            return mainThreadSupport;
        }
        if (!Logger.AndroidLogger.isAndroidLogAvailable() || (c = c()) == null) {
            return null;
        }
        return new MainThreadSupport.AndroidHandlerMainThreadSupport((Looper) c);
    }

    Object c() {
        try {
            return Looper.getMainLooper();
        } catch (RuntimeException unused) {
            return null;
        }
    }

    public EventBus installDefaultEventBus() {
        EventBus eventBus;
        synchronized (EventBus.class) {
            if (EventBus.a == null) {
                EventBus.a = build();
                eventBus = EventBus.a;
            } else {
                throw new EventBusException("Default instance already exists. It may be only set once before it's used the first time to ensure consistent behavior.");
            }
        }
        return eventBus;
    }

    public EventBus build() {
        return new EventBus(this);
    }
}
