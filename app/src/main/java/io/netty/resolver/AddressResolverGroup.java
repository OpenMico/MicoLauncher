package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.Closeable;
import java.net.SocketAddress;
import java.util.IdentityHashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class AddressResolverGroup<T extends SocketAddress> implements Closeable {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(AddressResolverGroup.class);
    private final Map<EventExecutor, AddressResolver<T>> b = new IdentityHashMap();

    protected abstract AddressResolver<T> newResolver(EventExecutor eventExecutor) throws Exception;

    public AddressResolver<T> getResolver(final EventExecutor eventExecutor) {
        final AddressResolver<T> addressResolver;
        if (eventExecutor == null) {
            throw new NullPointerException("executor");
        } else if (!eventExecutor.isShuttingDown()) {
            synchronized (this.b) {
                addressResolver = this.b.get(eventExecutor);
                if (addressResolver == null) {
                    try {
                        addressResolver = newResolver(eventExecutor);
                        this.b.put(eventExecutor, addressResolver);
                        eventExecutor.terminationFuture().addListener(new FutureListener<Object>() { // from class: io.netty.resolver.AddressResolverGroup.1
                            @Override // io.netty.util.concurrent.GenericFutureListener
                            public void operationComplete(Future<Object> future) throws Exception {
                                synchronized (AddressResolverGroup.this.b) {
                                    AddressResolverGroup.this.b.remove(eventExecutor);
                                }
                                addressResolver.close();
                            }
                        });
                    } catch (Exception e) {
                        throw new IllegalStateException("failed to create a new resolver", e);
                    }
                }
            }
            return addressResolver;
        } else {
            throw new IllegalStateException("executor not accepting a task");
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        AddressResolver[] addressResolverArr;
        synchronized (this.b) {
            addressResolverArr = (AddressResolver[]) this.b.values().toArray(new AddressResolver[this.b.size()]);
            this.b.clear();
        }
        for (AddressResolver addressResolver : addressResolverArr) {
            try {
                addressResolver.close();
            } catch (Throwable th) {
                a.warn("Failed to close a resolver:", th);
            }
        }
    }
}
