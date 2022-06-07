package io.netty.channel.pool;

import io.netty.channel.pool.ChannelPool;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReadOnlyIterator;
import java.io.Closeable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes4.dex */
public abstract class AbstractChannelPoolMap<K, P extends ChannelPool> implements ChannelPoolMap<K, P>, Closeable, Iterable<Map.Entry<K, P>> {
    private final ConcurrentMap<K, P> a = PlatformDependent.newConcurrentHashMap();

    protected abstract P newPool(K k);

    @Override // io.netty.channel.pool.ChannelPoolMap
    public final P get(K k) {
        P p = this.a.get(ObjectUtil.checkNotNull(k, "key"));
        if (p != null) {
            return p;
        }
        P newPool = newPool(k);
        P putIfAbsent = this.a.putIfAbsent(k, newPool);
        if (putIfAbsent == null) {
            return newPool;
        }
        newPool.close();
        return putIfAbsent;
    }

    public final boolean remove(K k) {
        P remove = this.a.remove(ObjectUtil.checkNotNull(k, "key"));
        if (remove == null) {
            return false;
        }
        remove.close();
        return true;
    }

    @Override // java.lang.Iterable
    public final Iterator<Map.Entry<K, P>> iterator() {
        return new ReadOnlyIterator(this.a.entrySet().iterator());
    }

    public final int size() {
        return this.a.size();
    }

    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // io.netty.channel.pool.ChannelPoolMap
    public final boolean contains(K k) {
        return this.a.containsKey(ObjectUtil.checkNotNull(k, "key"));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        for (K k : this.a.keySet()) {
            remove(k);
        }
    }
}
