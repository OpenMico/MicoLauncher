package io.netty.handler.ssl;

import io.netty.util.internal.InternalThreadLocalMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public final class IdentityCipherSuiteFilter implements CipherSuiteFilter {
    public static final IdentityCipherSuiteFilter INSTANCE = new IdentityCipherSuiteFilter();

    private IdentityCipherSuiteFilter() {
    }

    @Override // io.netty.handler.ssl.CipherSuiteFilter
    public String[] filterCipherSuites(Iterable<String> iterable, List<String> list, Set<String> set) {
        String next;
        if (iterable == null) {
            return (String[]) list.toArray(new String[list.size()]);
        }
        ArrayList arrayList = InternalThreadLocalMap.get().arrayList(set.size());
        Iterator<String> it = iterable.iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            arrayList.add(next);
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
