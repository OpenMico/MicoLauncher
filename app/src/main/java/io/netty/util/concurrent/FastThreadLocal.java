package io.netty.util.concurrent;

import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.PlatformDependent;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/* loaded from: classes4.dex */
public class FastThreadLocal<V> {
    private static final int a = InternalThreadLocalMap.nextVariableIndex();
    private final int b = InternalThreadLocalMap.nextVariableIndex();

    protected V initialValue() throws Exception {
        return null;
    }

    protected void onRemoval(V v) throws Exception {
    }

    public static void removeAll() {
        InternalThreadLocalMap ifSet = InternalThreadLocalMap.getIfSet();
        if (ifSet != null) {
            try {
                Object indexedVariable = ifSet.indexedVariable(a);
                if (!(indexedVariable == null || indexedVariable == InternalThreadLocalMap.UNSET)) {
                    Set set = (Set) indexedVariable;
                    for (FastThreadLocal fastThreadLocal : (FastThreadLocal[]) set.toArray(new FastThreadLocal[set.size()])) {
                        fastThreadLocal.remove(ifSet);
                    }
                }
            } finally {
                InternalThreadLocalMap.remove();
            }
        }
    }

    public static int size() {
        InternalThreadLocalMap ifSet = InternalThreadLocalMap.getIfSet();
        if (ifSet == null) {
            return 0;
        }
        return ifSet.size();
    }

    public static void destroy() {
        InternalThreadLocalMap.destroy();
    }

    private static void a(InternalThreadLocalMap internalThreadLocalMap, FastThreadLocal<?> fastThreadLocal) {
        Set set;
        Object indexedVariable = internalThreadLocalMap.indexedVariable(a);
        if (indexedVariable == InternalThreadLocalMap.UNSET || indexedVariable == null) {
            set = Collections.newSetFromMap(new IdentityHashMap());
            internalThreadLocalMap.setIndexedVariable(a, set);
        } else {
            set = (Set) indexedVariable;
        }
        set.add(fastThreadLocal);
    }

    private static void b(InternalThreadLocalMap internalThreadLocalMap, FastThreadLocal<?> fastThreadLocal) {
        Object indexedVariable = internalThreadLocalMap.indexedVariable(a);
        if (indexedVariable != InternalThreadLocalMap.UNSET && indexedVariable != null) {
            ((Set) indexedVariable).remove(fastThreadLocal);
        }
    }

    public final V get() {
        return get(InternalThreadLocalMap.get());
    }

    public final V get(InternalThreadLocalMap internalThreadLocalMap) {
        V v = (V) internalThreadLocalMap.indexedVariable(this.b);
        return v != InternalThreadLocalMap.UNSET ? v : a(internalThreadLocalMap);
    }

    private V a(InternalThreadLocalMap internalThreadLocalMap) {
        V v;
        try {
            v = initialValue();
        } catch (Exception e) {
            PlatformDependent.throwException(e);
            v = null;
        }
        internalThreadLocalMap.setIndexedVariable(this.b, v);
        a(internalThreadLocalMap, this);
        return v;
    }

    public final void set(V v) {
        if (v != InternalThreadLocalMap.UNSET) {
            set(InternalThreadLocalMap.get(), v);
        } else {
            remove();
        }
    }

    public final void set(InternalThreadLocalMap internalThreadLocalMap, V v) {
        if (v == InternalThreadLocalMap.UNSET) {
            remove(internalThreadLocalMap);
        } else if (internalThreadLocalMap.setIndexedVariable(this.b, v)) {
            a(internalThreadLocalMap, this);
        }
    }

    public final boolean isSet() {
        return isSet(InternalThreadLocalMap.getIfSet());
    }

    public final boolean isSet(InternalThreadLocalMap internalThreadLocalMap) {
        return internalThreadLocalMap != null && internalThreadLocalMap.isIndexedVariableSet(this.b);
    }

    public final void remove() {
        remove(InternalThreadLocalMap.getIfSet());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void remove(InternalThreadLocalMap internalThreadLocalMap) {
        if (internalThreadLocalMap != null) {
            Object removeIndexedVariable = internalThreadLocalMap.removeIndexedVariable(this.b);
            b(internalThreadLocalMap, this);
            if (removeIndexedVariable != InternalThreadLocalMap.UNSET) {
                try {
                    onRemoval(removeIndexedVariable);
                } catch (Exception e) {
                    PlatformDependent.throwException(e);
                }
            }
        }
    }
}
