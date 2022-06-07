package io.netty.resolver;

import java.net.InetAddress;

/* loaded from: classes4.dex */
public interface HostsFileEntriesResolver {
    public static final HostsFileEntriesResolver DEFAULT = new DefaultHostsFileEntriesResolver();

    InetAddress address(String str);
}
