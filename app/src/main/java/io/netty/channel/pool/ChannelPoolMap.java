package io.netty.channel.pool;

import io.netty.channel.pool.ChannelPool;

/* loaded from: classes4.dex */
public interface ChannelPoolMap<K, P extends ChannelPool> {
    boolean contains(K k);

    P get(K k);
}
