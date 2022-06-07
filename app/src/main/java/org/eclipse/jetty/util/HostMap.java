package org.eclipse.jetty.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/* loaded from: classes5.dex */
public class HostMap<TYPE> extends HashMap<String, TYPE> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
        return put((String) obj, (String) obj2);
    }

    public HostMap() {
        super(11);
    }

    public HostMap(int i) {
        super(i);
    }

    public TYPE put(String str, TYPE type) throws IllegalArgumentException {
        return (TYPE) super.put((HostMap<TYPE>) str, (String) type);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public TYPE get(Object obj) {
        return (TYPE) super.get(obj);
    }

    public Object getLazyMatches(String str) {
        int indexOf;
        if (str == null) {
            return LazyList.getList(super.entrySet());
        }
        String trim = str.trim();
        HashSet hashSet = new HashSet();
        do {
            hashSet.add(trim);
            indexOf = trim.indexOf(46);
            if (indexOf > 0) {
                trim = trim.substring(indexOf + 1);
                continue;
            }
        } while (indexOf > 0);
        Object obj = null;
        for (Map.Entry entry : super.entrySet()) {
            if (hashSet.contains(entry.getKey())) {
                obj = LazyList.add(obj, entry);
            }
        }
        return obj;
    }
}
