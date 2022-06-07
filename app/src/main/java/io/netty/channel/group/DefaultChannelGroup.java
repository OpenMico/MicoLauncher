package io.netty.channel.group;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelId;
import io.netty.channel.ServerChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class DefaultChannelGroup extends AbstractSet<Channel> implements ChannelGroup {
    private static final AtomicInteger a = new AtomicInteger();
    private final String b;
    private final EventExecutor c;
    private final ConcurrentMap<ChannelId, Channel> d;
    private final ConcurrentMap<ChannelId, Channel> e;
    private final ChannelFutureListener f;
    private final c g;
    private final boolean h;
    private volatile boolean i;

    @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        return this == obj;
    }

    public DefaultChannelGroup(EventExecutor eventExecutor) {
        this(eventExecutor, false);
    }

    public DefaultChannelGroup(String str, EventExecutor eventExecutor) {
        this(str, eventExecutor, false);
    }

    public DefaultChannelGroup(EventExecutor eventExecutor, boolean z) {
        this("group-0x" + Integer.toHexString(a.incrementAndGet()), eventExecutor, z);
    }

    public DefaultChannelGroup(String str, EventExecutor eventExecutor, boolean z) {
        this.d = PlatformDependent.newConcurrentHashMap();
        this.e = PlatformDependent.newConcurrentHashMap();
        this.f = new ChannelFutureListener() { // from class: io.netty.channel.group.DefaultChannelGroup.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                DefaultChannelGroup.this.remove(channelFuture.channel());
            }
        };
        this.g = new c(this);
        if (str != null) {
            this.b = str;
            this.c = eventExecutor;
            this.h = z;
            return;
        }
        throw new NullPointerException("name");
    }

    @Override // io.netty.channel.group.ChannelGroup
    public String name() {
        return this.b;
    }

    @Override // io.netty.channel.group.ChannelGroup
    public Channel find(ChannelId channelId) {
        Channel channel = this.e.get(channelId);
        return channel != null ? channel : this.d.get(channelId);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.e.isEmpty() && this.d.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.e.size() + this.d.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        if (!(obj instanceof Channel)) {
            return false;
        }
        Channel channel = (Channel) obj;
        if (obj instanceof ServerChannel) {
            return this.d.containsValue(channel);
        }
        return this.e.containsValue(channel);
    }

    public boolean add(Channel channel) {
        boolean z = (channel instanceof ServerChannel ? this.d : this.e).putIfAbsent(channel.id(), channel) == null;
        if (z) {
            channel.closeFuture().addListener((GenericFutureListener<? extends Future<? super Void>>) this.f);
        }
        if (this.h && this.i) {
            channel.close();
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        Channel channel;
        if (obj instanceof ChannelId) {
            channel = this.e.remove(obj);
            if (channel == null) {
                channel = this.d.remove(obj);
            }
        } else if (obj instanceof Channel) {
            Channel channel2 = (Channel) obj;
            if (channel2 instanceof ServerChannel) {
                channel = this.d.remove(channel2.id());
            } else {
                channel = this.e.remove(channel2.id());
            }
        } else {
            channel = null;
        }
        if (channel == null) {
            return false;
        }
        channel.closeFuture().removeListener((GenericFutureListener<? extends Future<? super Void>>) this.f);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.e.clear();
        this.d.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<Channel> iterator() {
        return new a(this.d.values().iterator(), this.e.values().iterator());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public Object[] toArray() {
        ArrayList arrayList = new ArrayList(size());
        arrayList.addAll(this.d.values());
        arrayList.addAll(this.e.values());
        return arrayList.toArray();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        ArrayList arrayList = new ArrayList(size());
        arrayList.addAll(this.d.values());
        arrayList.addAll(this.e.values());
        return (T[]) arrayList.toArray(tArr);
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture close() {
        return close(ChannelMatchers.all());
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture disconnect() {
        return disconnect(ChannelMatchers.all());
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture deregister() {
        return deregister(ChannelMatchers.all());
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture write(Object obj) {
        return write(obj, ChannelMatchers.all());
    }

    private static Object a(Object obj) {
        if (obj instanceof ByteBuf) {
            return ((ByteBuf) obj).retainedDuplicate();
        }
        if (obj instanceof ByteBufHolder) {
            return ((ByteBufHolder) obj).retainedDuplicate();
        }
        return ReferenceCountUtil.retain(obj);
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture write(Object obj, ChannelMatcher channelMatcher) {
        return write(obj, channelMatcher, false);
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture write(Object obj, ChannelMatcher channelMatcher, boolean z) {
        ChannelGroupFuture channelGroupFuture;
        if (obj == null) {
            throw new NullPointerException("message");
        } else if (channelMatcher != null) {
            if (z) {
                for (Channel channel : this.e.values()) {
                    if (channelMatcher.matches(channel)) {
                        channel.write(a(obj), channel.voidPromise());
                    }
                }
                channelGroupFuture = this.g;
            } else {
                LinkedHashMap linkedHashMap = new LinkedHashMap(size());
                for (Channel channel2 : this.e.values()) {
                    if (channelMatcher.matches(channel2)) {
                        linkedHashMap.put(channel2, channel2.write(a(obj)));
                    }
                }
                channelGroupFuture = new b(this, linkedHashMap, this.c);
            }
            ReferenceCountUtil.release(obj);
            return channelGroupFuture;
        } else {
            throw new NullPointerException("matcher");
        }
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroup flush() {
        return flush(ChannelMatchers.all());
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture flushAndWrite(Object obj) {
        return writeAndFlush(obj);
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture writeAndFlush(Object obj) {
        return writeAndFlush(obj, ChannelMatchers.all());
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture disconnect(ChannelMatcher channelMatcher) {
        if (channelMatcher != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(size());
            for (Channel channel : this.d.values()) {
                if (channelMatcher.matches(channel)) {
                    linkedHashMap.put(channel, channel.disconnect());
                }
            }
            for (Channel channel2 : this.e.values()) {
                if (channelMatcher.matches(channel2)) {
                    linkedHashMap.put(channel2, channel2.disconnect());
                }
            }
            return new b(this, linkedHashMap, this.c);
        }
        throw new NullPointerException("matcher");
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture close(ChannelMatcher channelMatcher) {
        if (channelMatcher != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(size());
            if (this.h) {
                this.i = true;
            }
            for (Channel channel : this.d.values()) {
                if (channelMatcher.matches(channel)) {
                    linkedHashMap.put(channel, channel.close());
                }
            }
            for (Channel channel2 : this.e.values()) {
                if (channelMatcher.matches(channel2)) {
                    linkedHashMap.put(channel2, channel2.close());
                }
            }
            return new b(this, linkedHashMap, this.c);
        }
        throw new NullPointerException("matcher");
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture deregister(ChannelMatcher channelMatcher) {
        if (channelMatcher != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(size());
            for (Channel channel : this.d.values()) {
                if (channelMatcher.matches(channel)) {
                    linkedHashMap.put(channel, channel.deregister());
                }
            }
            for (Channel channel2 : this.e.values()) {
                if (channelMatcher.matches(channel2)) {
                    linkedHashMap.put(channel2, channel2.deregister());
                }
            }
            return new b(this, linkedHashMap, this.c);
        }
        throw new NullPointerException("matcher");
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroup flush(ChannelMatcher channelMatcher) {
        for (Channel channel : this.e.values()) {
            if (channelMatcher.matches(channel)) {
                channel.flush();
            }
        }
        return this;
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture flushAndWrite(Object obj, ChannelMatcher channelMatcher) {
        return writeAndFlush(obj, channelMatcher);
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture writeAndFlush(Object obj, ChannelMatcher channelMatcher) {
        return writeAndFlush(obj, channelMatcher, false);
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture writeAndFlush(Object obj, ChannelMatcher channelMatcher, boolean z) {
        ChannelGroupFuture channelGroupFuture;
        if (obj != null) {
            if (z) {
                for (Channel channel : this.e.values()) {
                    if (channelMatcher.matches(channel)) {
                        channel.writeAndFlush(a(obj), channel.voidPromise());
                    }
                }
                channelGroupFuture = this.g;
            } else {
                LinkedHashMap linkedHashMap = new LinkedHashMap(size());
                for (Channel channel2 : this.e.values()) {
                    if (channelMatcher.matches(channel2)) {
                        linkedHashMap.put(channel2, channel2.writeAndFlush(a(obj)));
                    }
                }
                channelGroupFuture = new b(this, linkedHashMap, this.c);
            }
            ReferenceCountUtil.release(obj);
            return channelGroupFuture;
        }
        throw new NullPointerException("message");
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture newCloseFuture() {
        return newCloseFuture(ChannelMatchers.all());
    }

    @Override // io.netty.channel.group.ChannelGroup
    public ChannelGroupFuture newCloseFuture(ChannelMatcher channelMatcher) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(size());
        for (Channel channel : this.d.values()) {
            if (channelMatcher.matches(channel)) {
                linkedHashMap.put(channel, channel.closeFuture());
            }
        }
        for (Channel channel2 : this.e.values()) {
            if (channelMatcher.matches(channel2)) {
                linkedHashMap.put(channel2, channel2.closeFuture());
            }
        }
        return new b(this, linkedHashMap, this.c);
    }

    @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return System.identityHashCode(this);
    }

    public int compareTo(ChannelGroup channelGroup) {
        int compareTo = name().compareTo(channelGroup.name());
        return compareTo != 0 ? compareTo : System.identityHashCode(this) - System.identityHashCode(channelGroup);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return StringUtil.simpleClassName(this) + "(name: " + name() + ", size: " + size() + ')';
    }
}
