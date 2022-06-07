package com.google.protobuf;

import com.google.protobuf.MapEntryLite;
import java.util.Map;

/* compiled from: MapFieldSchemaLite.java */
/* loaded from: classes2.dex */
class u implements t {
    @Override // com.google.protobuf.t
    public Map<?, ?> a(Object obj) {
        return (MapFieldLite) obj;
    }

    @Override // com.google.protobuf.t
    public MapEntryLite.a<?, ?> f(Object obj) {
        return ((MapEntryLite) obj).a();
    }

    @Override // com.google.protobuf.t
    public Map<?, ?> b(Object obj) {
        return (MapFieldLite) obj;
    }

    @Override // com.google.protobuf.t
    public boolean c(Object obj) {
        return !((MapFieldLite) obj).isMutable();
    }

    @Override // com.google.protobuf.t
    public Object d(Object obj) {
        ((MapFieldLite) obj).makeImmutable();
        return obj;
    }

    @Override // com.google.protobuf.t
    public Object e(Object obj) {
        return MapFieldLite.emptyMapField().mutableCopy();
    }

    @Override // com.google.protobuf.t
    public Object a(Object obj, Object obj2) {
        return b(obj, obj2);
    }

    private static <K, V> MapFieldLite<K, V> b(Object obj, Object obj2) {
        MapFieldLite<K, V> mapFieldLite = (MapFieldLite) obj;
        MapFieldLite<K, V> mapFieldLite2 = (MapFieldLite) obj2;
        if (!mapFieldLite2.isEmpty()) {
            if (!mapFieldLite.isMutable()) {
                mapFieldLite = mapFieldLite.mutableCopy();
            }
            mapFieldLite.mergeFrom(mapFieldLite2);
        }
        return mapFieldLite;
    }

    @Override // com.google.protobuf.t
    public int a(int i, Object obj, Object obj2) {
        return b(i, obj, obj2);
    }

    private static <K, V> int b(int i, Object obj, Object obj2) {
        MapFieldLite mapFieldLite = (MapFieldLite) obj;
        MapEntryLite mapEntryLite = (MapEntryLite) obj2;
        int i2 = 0;
        if (mapFieldLite.isEmpty()) {
            return 0;
        }
        for (Map.Entry<K, V> entry : mapFieldLite.entrySet()) {
            i2 += mapEntryLite.computeMessageSize(i, entry.getKey(), entry.getValue());
        }
        return i2;
    }
}
