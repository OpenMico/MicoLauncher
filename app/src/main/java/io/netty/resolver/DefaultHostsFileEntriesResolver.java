package io.netty.resolver;

import java.net.InetAddress;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public final class DefaultHostsFileEntriesResolver implements HostsFileEntriesResolver {
    private final Map<String, InetAddress> a = HostsFileParser.parseSilently();

    @Override // io.netty.resolver.HostsFileEntriesResolver
    public InetAddress address(String str) {
        return this.a.get(str.toLowerCase(Locale.ENGLISH));
    }
}
