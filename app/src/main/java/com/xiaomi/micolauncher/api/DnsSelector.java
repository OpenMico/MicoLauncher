package com.xiaomi.micolauncher.api;

import android.util.ArrayMap;
import com.google.android.collect.Lists;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import okhttp3.Dns;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class DnsSelector implements Dns {
    private Map<String, List<InetAddress>> a = new ArrayMap();
    private Mode b;

    /* loaded from: classes3.dex */
    public enum Mode {
        SYSTEM,
        IPV6_FIRST,
        IPV4_FIRST,
        IPV6_ONLY,
        IPV4_ONLY
    }

    public DnsSelector(Mode mode) {
        this.b = mode;
    }

    public static Dns byName(Mode mode) {
        return new DnsSelector(mode);
    }

    @Override // okhttp3.Dns
    public List<InetAddress> lookup(@NotNull String str) throws UnknownHostException {
        List<InetAddress> list = this.a.get(str.toLowerCase());
        if (list != null) {
            return list;
        }
        List<InetAddress> lookup = Dns.SYSTEM.lookup(str);
        switch (this.b) {
            case IPV6_FIRST:
                Inet4Address.class.getClass();
                lookup.sort(Comparator.comparing(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$0FEW_CvcKZdn8DDi7v4-XURo4ck
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Boolean.valueOf(r1.isInstance((InetAddress) obj));
                    }
                }));
                return lookup;
            case IPV4_FIRST:
                Inet4Address.class.getClass();
                lookup.sort(Comparator.comparing(new Function() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$iQrpg6MDZWML25HLEQYJGPlvDlo
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Boolean.valueOf(r1.isInstance(obj));
                    }
                }).reversed());
                return lookup;
            case IPV6_ONLY:
                Stream<InetAddress> stream = lookup.stream();
                Inet6Address.class.getClass();
                return (List) stream.filter(new Predicate() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$J5fhcX4yAhv3pSyJ3Oa178F4DX0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return r1.isInstance((InetAddress) obj);
                    }
                }).collect(Collectors.toList());
            case IPV4_ONLY:
                Stream<InetAddress> stream2 = lookup.stream();
                Inet4Address.class.getClass();
                return (List) stream2.filter(new Predicate() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$J5fhcX4yAhv3pSyJ3Oa178F4DX0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return r1.isInstance((InetAddress) obj);
                    }
                }).collect(Collectors.toList());
            default:
                return lookup;
        }
    }

    public void addOverride(String str, InetAddress inetAddress) {
        this.a.put(str.toLowerCase(), Lists.newArrayList(new InetAddress[]{inetAddress}));
    }
}
