package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

@Beta
/* loaded from: classes2.dex */
public class EventBus {
    private static final Logger a = Logger.getLogger(EventBus.class.getName());
    private final String b;
    private final Executor c;
    private final SubscriberExceptionHandler d;
    private final c e;
    private final a f;

    public EventBus() {
        this("default");
    }

    public EventBus(String str) {
        this(str, MoreExecutors.directExecutor(), a.a(), a.a);
    }

    public EventBus(SubscriberExceptionHandler subscriberExceptionHandler) {
        this("default", MoreExecutors.directExecutor(), a.a(), subscriberExceptionHandler);
    }

    public EventBus(String str, Executor executor, a aVar, SubscriberExceptionHandler subscriberExceptionHandler) {
        this.e = new c(this);
        this.b = (String) Preconditions.checkNotNull(str);
        this.c = (Executor) Preconditions.checkNotNull(executor);
        this.f = (a) Preconditions.checkNotNull(aVar);
        this.d = (SubscriberExceptionHandler) Preconditions.checkNotNull(subscriberExceptionHandler);
    }

    public final String identifier() {
        return this.b;
    }

    public final Executor a() {
        return this.c;
    }

    public void a(Throwable th, SubscriberExceptionContext subscriberExceptionContext) {
        Preconditions.checkNotNull(th);
        Preconditions.checkNotNull(subscriberExceptionContext);
        try {
            this.d.handleException(th, subscriberExceptionContext);
        } catch (Throwable th2) {
            a.log(Level.SEVERE, String.format(Locale.ROOT, "Exception %s thrown while handling exception: %s", th2, th), th2);
        }
    }

    public void register(Object obj) {
        this.e.a(obj);
    }

    public void unregister(Object obj) {
        this.e.b(obj);
    }

    public void post(Object obj) {
        Iterator<b> c = this.e.c(obj);
        if (c.hasNext()) {
            this.f.a(obj, c);
        } else if (!(obj instanceof DeadEvent)) {
            post(new DeadEvent(this, obj));
        }
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(this.b).toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class a implements SubscriberExceptionHandler {
        static final a a = new a();

        a() {
        }

        @Override // com.google.common.eventbus.SubscriberExceptionHandler
        public void handleException(Throwable th, SubscriberExceptionContext subscriberExceptionContext) {
            Logger a2 = a(subscriberExceptionContext);
            if (a2.isLoggable(Level.SEVERE)) {
                a2.log(Level.SEVERE, b(subscriberExceptionContext), th);
            }
        }

        private static Logger a(SubscriberExceptionContext subscriberExceptionContext) {
            return Logger.getLogger(EventBus.class.getName() + "." + subscriberExceptionContext.getEventBus().identifier());
        }

        private static String b(SubscriberExceptionContext subscriberExceptionContext) {
            Method subscriberMethod = subscriberExceptionContext.getSubscriberMethod();
            return "Exception thrown by subscriber method " + subscriberMethod.getName() + '(' + subscriberMethod.getParameterTypes()[0].getName() + ") on subscriber " + subscriberExceptionContext.getSubscriber() + " when dispatching event: " + subscriberExceptionContext.getEvent();
        }
    }
}
