package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class DefaultNameResolver extends InetNameResolver {
    public DefaultNameResolver(EventExecutor eventExecutor) {
        super(eventExecutor);
    }

    @Override // io.netty.resolver.SimpleNameResolver
    protected void doResolve(String str, Promise<InetAddress> promise) throws Exception {
        try {
            promise.setSuccess(InetAddress.getByName(str));
        } catch (UnknownHostException e) {
            promise.setFailure(e);
        }
    }

    @Override // io.netty.resolver.SimpleNameResolver
    protected void doResolveAll(String str, Promise<List<InetAddress>> promise) throws Exception {
        try {
            promise.setSuccess(Arrays.asList(InetAddress.getAllByName(str)));
        } catch (UnknownHostException e) {
            promise.setFailure(e);
        }
    }
}
