package org.greenrobot.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;

/* loaded from: classes5.dex */
public class EventBus {
    public static String TAG = "EventBus";
    static volatile EventBus a;
    private static final EventBusBuilder b = new EventBusBuilder();
    private static final Map<Class<?>, List<Class<?>>> c = new HashMap();
    private final Map<Class<?>, CopyOnWriteArrayList<g>> d;
    private final Map<Object, List<Class<?>>> e;
    private final Map<Class<?>, Object> f;
    private final ThreadLocal<a> g;
    private final MainThreadSupport h;
    private final e i;
    private final b j;
    private final a k;
    private final f l;
    private final ExecutorService m;
    private final boolean n;
    private final boolean o;
    private final boolean p;
    private final boolean q;
    private final boolean r;
    private final boolean s;
    private final int t;
    private final Logger u;

    public static EventBus getDefault() {
        if (a == null) {
            synchronized (EventBus.class) {
                if (a == null) {
                    a = new EventBus();
                }
            }
        }
        return a;
    }

    public static EventBusBuilder builder() {
        return new EventBusBuilder();
    }

    public static void clearCaches() {
        f.a();
        c.clear();
    }

    public EventBus() {
        this(b);
    }

    public EventBus(EventBusBuilder eventBusBuilder) {
        this.g = new ThreadLocal<a>() { // from class: org.greenrobot.eventbus.EventBus.1
            /* renamed from: a */
            public a initialValue() {
                return new a();
            }
        };
        this.u = eventBusBuilder.a();
        this.d = new HashMap();
        this.e = new HashMap();
        this.f = new ConcurrentHashMap();
        this.h = eventBusBuilder.b();
        MainThreadSupport mainThreadSupport = this.h;
        this.i = mainThreadSupport != null ? mainThreadSupport.createPoster(this) : null;
        this.j = new b(this);
        this.k = new a(this);
        this.t = eventBusBuilder.k != null ? eventBusBuilder.k.size() : 0;
        this.l = new f(eventBusBuilder.k, eventBusBuilder.h, eventBusBuilder.g);
        this.o = eventBusBuilder.a;
        this.p = eventBusBuilder.b;
        this.q = eventBusBuilder.c;
        this.r = eventBusBuilder.d;
        this.n = eventBusBuilder.e;
        this.s = eventBusBuilder.f;
        this.m = eventBusBuilder.i;
    }

    public void register(Object obj) {
        List<SubscriberMethod> a2 = this.l.a(obj.getClass());
        synchronized (this) {
            for (SubscriberMethod subscriberMethod : a2) {
                a(obj, subscriberMethod);
            }
        }
    }

    private void a(Object obj, SubscriberMethod subscriberMethod) {
        Class<?> cls = subscriberMethod.c;
        g gVar = new g(obj, subscriberMethod);
        CopyOnWriteArrayList<g> copyOnWriteArrayList = this.d.get(cls);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.d.put(cls, copyOnWriteArrayList);
        } else if (copyOnWriteArrayList.contains(gVar)) {
            throw new EventBusException("Subscriber " + obj.getClass() + " already registered to event " + cls);
        }
        int size = copyOnWriteArrayList.size();
        for (int i = 0; i <= size; i++) {
            if (i == size || subscriberMethod.d > copyOnWriteArrayList.get(i).b.d) {
                copyOnWriteArrayList.add(i, gVar);
                break;
            }
        }
        List<Class<?>> list = this.e.get(obj);
        if (list == null) {
            list = new ArrayList<>();
            this.e.put(obj, list);
        }
        list.add(cls);
        if (!subscriberMethod.e) {
            return;
        }
        if (this.s) {
            for (Map.Entry<Class<?>, Object> entry : this.f.entrySet()) {
                if (cls.isAssignableFrom(entry.getKey())) {
                    b(gVar, entry.getValue());
                }
            }
            return;
        }
        b(gVar, this.f.get(cls));
    }

    private void b(g gVar, Object obj) {
        if (obj != null) {
            a(gVar, obj, b());
        }
    }

    private boolean b() {
        MainThreadSupport mainThreadSupport = this.h;
        if (mainThreadSupport != null) {
            return mainThreadSupport.isMainThread();
        }
        return true;
    }

    public synchronized boolean isRegistered(Object obj) {
        return this.e.containsKey(obj);
    }

    private void a(Object obj, Class<?> cls) {
        CopyOnWriteArrayList<g> copyOnWriteArrayList = this.d.get(cls);
        if (copyOnWriteArrayList != null) {
            int size = copyOnWriteArrayList.size();
            int i = 0;
            while (i < size) {
                g gVar = copyOnWriteArrayList.get(i);
                if (gVar.a == obj) {
                    gVar.c = false;
                    copyOnWriteArrayList.remove(i);
                    i--;
                    size--;
                }
                i++;
            }
        }
    }

    public synchronized void unregister(Object obj) {
        List<Class<?>> list = this.e.get(obj);
        if (list != null) {
            for (Class<?> cls : list) {
                a(obj, cls);
            }
            this.e.remove(obj);
        } else {
            Logger logger = this.u;
            Level level = Level.WARNING;
            logger.log(level, "Subscriber to unregister was not registered before: " + obj.getClass());
        }
    }

    public void post(Object obj) {
        a aVar = this.g.get();
        List<Object> list = aVar.a;
        list.add(obj);
        if (!aVar.b) {
            aVar.c = b();
            aVar.b = true;
            if (!aVar.f) {
                while (true) {
                    try {
                        if (!list.isEmpty()) {
                            a(list.remove(0), aVar);
                        } else {
                            return;
                        }
                    } finally {
                        aVar.b = false;
                        aVar.c = false;
                    }
                }
            } else {
                throw new EventBusException("Internal error. Abort state was not reset");
            }
        }
    }

    public void cancelEventDelivery(Object obj) {
        a aVar = this.g.get();
        if (!aVar.b) {
            throw new EventBusException("This method may only be called from inside event handling methods on the posting thread");
        } else if (obj == null) {
            throw new EventBusException("Event may not be null");
        } else if (aVar.e != obj) {
            throw new EventBusException("Only the currently handled event may be aborted");
        } else if (aVar.d.b.b == ThreadMode.POSTING) {
            aVar.f = true;
        } else {
            throw new EventBusException(" event handlers may only abort the incoming event");
        }
    }

    public void postSticky(Object obj) {
        synchronized (this.f) {
            this.f.put(obj.getClass(), obj);
        }
        post(obj);
    }

    public <T> T getStickyEvent(Class<T> cls) {
        T cast;
        synchronized (this.f) {
            cast = cls.cast(this.f.get(cls));
        }
        return cast;
    }

    public <T> T removeStickyEvent(Class<T> cls) {
        T cast;
        synchronized (this.f) {
            cast = cls.cast(this.f.remove(cls));
        }
        return cast;
    }

    public boolean removeStickyEvent(Object obj) {
        synchronized (this.f) {
            Class<?> cls = obj.getClass();
            if (!obj.equals(this.f.get(cls))) {
                return false;
            }
            this.f.remove(cls);
            return true;
        }
    }

    public void removeAllStickyEvents() {
        synchronized (this.f) {
            this.f.clear();
        }
    }

    public boolean hasSubscriberForEvent(Class<?> cls) {
        CopyOnWriteArrayList<g> copyOnWriteArrayList;
        List<Class<?>> a2 = a(cls);
        if (a2 != null) {
            int size = a2.size();
            for (int i = 0; i < size; i++) {
                Class<?> cls2 = a2.get(i);
                synchronized (this) {
                    copyOnWriteArrayList = this.d.get(cls2);
                }
                if (!(copyOnWriteArrayList == null || copyOnWriteArrayList.isEmpty())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void a(Object obj, a aVar) throws Error {
        boolean z;
        Class<?> cls = obj.getClass();
        if (this.s) {
            List<Class<?>> a2 = a(cls);
            int size = a2.size();
            z = false;
            for (int i = 0; i < size; i++) {
                z |= a(obj, aVar, a2.get(i));
            }
        } else {
            z = a(obj, aVar, cls);
        }
        if (!z) {
            if (this.p) {
                this.u.log(Level.FINE, "No subscribers registered for event " + cls);
            }
            if (!(!this.r || cls == NoSubscriberEvent.class || cls == SubscriberExceptionEvent.class)) {
                post(new NoSubscriberEvent(this, obj));
            }
        }
    }

    private boolean a(Object obj, a aVar, Class<?> cls) {
        CopyOnWriteArrayList<g> copyOnWriteArrayList;
        synchronized (this) {
            copyOnWriteArrayList = this.d.get(cls);
        }
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.isEmpty()) {
            return false;
        }
        Iterator<g> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            g next = it.next();
            aVar.e = obj;
            aVar.d = next;
            try {
                a(next, obj, aVar.c);
                if (aVar.f) {
                    return true;
                }
            } finally {
                aVar.e = null;
                aVar.d = null;
                aVar.f = false;
            }
        }
        return true;
    }

    private void a(g gVar, Object obj, boolean z) {
        switch (gVar.b.b) {
            case POSTING:
                a(gVar, obj);
                return;
            case MAIN:
                if (z) {
                    a(gVar, obj);
                    return;
                } else {
                    this.i.enqueue(gVar, obj);
                    return;
                }
            case MAIN_ORDERED:
                e eVar = this.i;
                if (eVar != null) {
                    eVar.enqueue(gVar, obj);
                    return;
                } else {
                    a(gVar, obj);
                    return;
                }
            case BACKGROUND:
                if (z) {
                    this.j.enqueue(gVar, obj);
                    return;
                } else {
                    a(gVar, obj);
                    return;
                }
            case ASYNC:
                this.k.enqueue(gVar, obj);
                return;
            default:
                throw new IllegalStateException("Unknown thread mode: " + gVar.b.b);
        }
    }

    private static List<Class<?>> a(Class<?> cls) {
        List<Class<?>> list;
        synchronized (c) {
            list = c.get(cls);
            if (list == null) {
                list = new ArrayList<>();
                for (Class<?> cls2 = cls; cls2 != null; cls2 = cls2.getSuperclass()) {
                    list.add(cls2);
                    a(list, cls2.getInterfaces());
                }
                c.put(cls, list);
            }
        }
        return list;
    }

    static void a(List<Class<?>> list, Class<?>[] clsArr) {
        for (Class<?> cls : clsArr) {
            if (!list.contains(cls)) {
                list.add(cls);
                a(list, cls.getInterfaces());
            }
        }
    }

    public void a(c cVar) {
        Object obj = cVar.a;
        g gVar = cVar.b;
        c.a(cVar);
        if (gVar.c) {
            a(gVar, obj);
        }
    }

    void a(g gVar, Object obj) {
        try {
            gVar.b.a.invoke(gVar.a, obj);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unexpected exception", e);
        } catch (InvocationTargetException e2) {
            a(gVar, obj, e2.getCause());
        }
    }

    private void a(g gVar, Object obj, Throwable th) {
        if (obj instanceof SubscriberExceptionEvent) {
            if (this.o) {
                Logger logger = this.u;
                Level level = Level.SEVERE;
                logger.log(level, "SubscriberExceptionEvent subscriber " + gVar.a.getClass() + " threw an exception", th);
                SubscriberExceptionEvent subscriberExceptionEvent = (SubscriberExceptionEvent) obj;
                Logger logger2 = this.u;
                Level level2 = Level.SEVERE;
                logger2.log(level2, "Initial event " + subscriberExceptionEvent.causingEvent + " caused exception in " + subscriberExceptionEvent.causingSubscriber, subscriberExceptionEvent.throwable);
            }
        } else if (!this.n) {
            if (this.o) {
                Logger logger3 = this.u;
                Level level3 = Level.SEVERE;
                logger3.log(level3, "Could not dispatch event: " + obj.getClass() + " to subscribing class " + gVar.a.getClass(), th);
            }
            if (this.q) {
                post(new SubscriberExceptionEvent(this, th, obj, gVar.a));
            }
        } else {
            throw new EventBusException("Invoking subscriber failed", th);
        }
    }

    /* loaded from: classes5.dex */
    public static final class a {
        final List<Object> a = new ArrayList();
        boolean b;
        boolean c;
        g d;
        Object e;
        boolean f;

        a() {
        }
    }

    public ExecutorService a() {
        return this.m;
    }

    public Logger getLogger() {
        return this.u;
    }

    public String toString() {
        return "EventBus[indexCount=" + this.t + ", eventInheritance=" + this.s + "]";
    }
}
