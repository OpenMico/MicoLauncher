package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class ReflectiveChannelFactory<T extends Channel> implements ChannelFactory<T> {
    private final Class<? extends T> a;

    public ReflectiveChannelFactory(Class<? extends T> cls) {
        if (cls != null) {
            this.a = cls;
            return;
        }
        throw new NullPointerException("clazz");
    }

    @Override // io.netty.channel.ChannelFactory, io.netty.bootstrap.ChannelFactory
    public T newChannel() {
        try {
            return (T) ((Channel) this.a.newInstance());
        } catch (Throwable th) {
            throw new ChannelException("Unable to create Channel from class " + this.a, th);
        }
    }

    public String toString() {
        return StringUtil.simpleClassName((Class<?>) this.a) + ".class";
    }
}
