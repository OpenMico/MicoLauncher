package com.google.common.eventbus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.j2objc.annotations.Weak;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SubscriberRegistry.java */
/* loaded from: classes2.dex */
public final class c {
    private static final LoadingCache<Class<?>, ImmutableList<Method>> c = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, ImmutableList<Method>>() { // from class: com.google.common.eventbus.c.1
        /* renamed from: a */
        public ImmutableList<Method> load(Class<?> cls) throws Exception {
            return c.d(cls);
        }
    });
    private static final LoadingCache<Class<?>, ImmutableSet<Class<?>>> d = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, ImmutableSet<Class<?>>>() { // from class: com.google.common.eventbus.c.2
        /* renamed from: a */
        public ImmutableSet<Class<?>> load(Class<?> cls) {
            return ImmutableSet.copyOf((Collection) TypeToken.of((Class) cls).getTypes().rawTypes());
        }
    });
    private final ConcurrentMap<Class<?>, CopyOnWriteArraySet<b>> a = Maps.newConcurrentMap();
    @Weak
    private final EventBus b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(EventBus eventBus) {
        this.b = (EventBus) Preconditions.checkNotNull(eventBus);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Object obj) {
        for (Map.Entry<Class<?>, Collection<b>> entry : d(obj).asMap().entrySet()) {
            Class<?> key = entry.getKey();
            Collection<b> value = entry.getValue();
            CopyOnWriteArraySet<b> copyOnWriteArraySet = this.a.get(key);
            if (copyOnWriteArraySet == null) {
                CopyOnWriteArraySet<b> copyOnWriteArraySet2 = new CopyOnWriteArraySet<>();
                copyOnWriteArraySet = (CopyOnWriteArraySet) MoreObjects.firstNonNull(this.a.putIfAbsent(key, copyOnWriteArraySet2), copyOnWriteArraySet2);
            }
            copyOnWriteArraySet.addAll(value);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void b(java.lang.Object r5) {
        /*
            r4 = this;
            com.google.common.collect.Multimap r0 = r4.d(r5)
            java.util.Map r0 = r0.asMap()
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0010:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0055
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r2 = r1.getKey()
            java.lang.Class r2 = (java.lang.Class) r2
            java.lang.Object r1 = r1.getValue()
            java.util.Collection r1 = (java.util.Collection) r1
            java.util.concurrent.ConcurrentMap<java.lang.Class<?>, java.util.concurrent.CopyOnWriteArraySet<com.google.common.eventbus.b>> r3 = r4.a
            java.lang.Object r2 = r3.get(r2)
            java.util.concurrent.CopyOnWriteArraySet r2 = (java.util.concurrent.CopyOnWriteArraySet) r2
            if (r2 == 0) goto L_0x0039
            boolean r1 = r2.removeAll(r1)
            if (r1 == 0) goto L_0x0039
            goto L_0x0010
        L_0x0039:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "missing event subscriber for an annotated method. Is "
            r1.append(r2)
            r1.append(r5)
            java.lang.String r5 = " registered?"
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r0.<init>(r5)
            throw r0
        L_0x0055:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.eventbus.c.b(java.lang.Object):void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Iterator<b> c(Object obj) {
        ImmutableSet<Class<?>> a2 = a(obj.getClass());
        ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(a2.size());
        UnmodifiableIterator<Class<?>> it = a2.iterator();
        while (it.hasNext()) {
            CopyOnWriteArraySet<b> copyOnWriteArraySet = this.a.get(it.next());
            if (copyOnWriteArraySet != null) {
                newArrayListWithCapacity.add(copyOnWriteArraySet.iterator());
            }
        }
        return Iterators.concat(newArrayListWithCapacity.iterator());
    }

    private Multimap<Class<?>, b> d(Object obj) {
        HashMultimap create = HashMultimap.create();
        UnmodifiableIterator<Method> it = c(obj.getClass()).iterator();
        while (it.hasNext()) {
            Method next = it.next();
            create.put(next.getParameterTypes()[0], b.a(this.b, obj, next));
        }
        return create;
    }

    private static ImmutableList<Method> c(Class<?> cls) {
        return c.getUnchecked(cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImmutableList<Method> d(Class<?> cls) {
        Set<Class> rawTypes = TypeToken.of((Class) cls).getTypes().rawTypes();
        HashMap newHashMap = Maps.newHashMap();
        for (Class cls2 : rawTypes) {
            Method[] declaredMethods = cls2.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(Subscribe.class) && !method.isSynthetic()) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    boolean z = true;
                    if (parameterTypes.length != 1) {
                        z = false;
                    }
                    Preconditions.checkArgument(z, "Method %s has @Subscribe annotation but has %s parameters.Subscriber methods must have exactly 1 parameter.", (Object) method, parameterTypes.length);
                    a aVar = new a(method);
                    if (!newHashMap.containsKey(aVar)) {
                        newHashMap.put(aVar, method);
                    }
                }
            }
        }
        return ImmutableList.copyOf(newHashMap.values());
    }

    @VisibleForTesting
    static ImmutableSet<Class<?>> a(Class<?> cls) {
        try {
            return d.getUnchecked(cls);
        } catch (UncheckedExecutionException e) {
            throw Throwables.propagate(e.getCause());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SubscriberRegistry.java */
    /* loaded from: classes2.dex */
    public static final class a {
        private final String a;
        private final List<Class<?>> b;

        a(Method method) {
            this.a = method.getName();
            this.b = Arrays.asList(method.getParameterTypes());
        }

        public int hashCode() {
            return Objects.hashCode(this.a, this.b);
        }

        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.a.equals(aVar.a) && this.b.equals(aVar.b);
        }
    }
}
