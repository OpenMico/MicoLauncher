package io.netty.resolver;

import androidx.exifinterface.media.ExifInterface;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.net.SocketAddress;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class AbstractAddressResolver<T extends SocketAddress> implements AddressResolver<T> {
    private final EventExecutor a;
    private final TypeParameterMatcher b;

    @Override // io.netty.resolver.AddressResolver, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    protected abstract boolean doIsResolved(T t);

    protected abstract void doResolve(T t, Promise<T> promise) throws Exception;

    protected abstract void doResolveAll(T t, Promise<List<T>> promise) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractAddressResolver(EventExecutor eventExecutor) {
        this.a = (EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "executor");
        this.b = TypeParameterMatcher.find(this, AbstractAddressResolver.class, ExifInterface.GPS_DIRECTION_TRUE);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractAddressResolver(EventExecutor eventExecutor, Class<? extends T> cls) {
        this.a = (EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "executor");
        this.b = TypeParameterMatcher.get(cls);
    }

    protected EventExecutor executor() {
        return this.a;
    }

    @Override // io.netty.resolver.AddressResolver
    public boolean isSupported(SocketAddress socketAddress) {
        return this.b.match(socketAddress);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.resolver.AddressResolver
    public final boolean isResolved(SocketAddress socketAddress) {
        if (isSupported(socketAddress)) {
            return doIsResolved(socketAddress);
        }
        throw new UnsupportedAddressTypeException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.resolver.AddressResolver
    public final Future<T> resolve(SocketAddress socketAddress) {
        if (!isSupported((SocketAddress) ObjectUtil.checkNotNull(socketAddress, "address"))) {
            return executor().newFailedFuture(new UnsupportedAddressTypeException());
        }
        if (isResolved(socketAddress)) {
            return this.a.newSucceededFuture(socketAddress);
        }
        try {
            Promise<T> newPromise = executor().newPromise();
            doResolve(socketAddress, newPromise);
            return newPromise;
        } catch (Exception e) {
            return executor().newFailedFuture(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.resolver.AddressResolver
    public final Future<T> resolve(SocketAddress socketAddress, Promise<T> promise) {
        ObjectUtil.checkNotNull(socketAddress, "address");
        ObjectUtil.checkNotNull(promise, "promise");
        if (!isSupported(socketAddress)) {
            return promise.setFailure(new UnsupportedAddressTypeException());
        }
        if (isResolved(socketAddress)) {
            return promise.setSuccess(socketAddress);
        }
        try {
            doResolve(socketAddress, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.resolver.AddressResolver
    public final Future<List<T>> resolveAll(SocketAddress socketAddress) {
        if (!isSupported((SocketAddress) ObjectUtil.checkNotNull(socketAddress, "address"))) {
            return executor().newFailedFuture(new UnsupportedAddressTypeException());
        }
        if (isResolved(socketAddress)) {
            return this.a.newSucceededFuture(Collections.singletonList(socketAddress));
        }
        try {
            Promise<List<T>> newPromise = executor().newPromise();
            doResolveAll(socketAddress, newPromise);
            return newPromise;
        } catch (Exception e) {
            return executor().newFailedFuture(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.resolver.AddressResolver
    public final Future<List<T>> resolveAll(SocketAddress socketAddress, Promise<List<T>> promise) {
        ObjectUtil.checkNotNull(socketAddress, "address");
        ObjectUtil.checkNotNull(promise, "promise");
        if (!isSupported(socketAddress)) {
            return promise.setFailure(new UnsupportedAddressTypeException());
        }
        if (isResolved(socketAddress)) {
            return promise.setSuccess(Collections.singletonList(socketAddress));
        }
        try {
            doResolveAll(socketAddress, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }
}
