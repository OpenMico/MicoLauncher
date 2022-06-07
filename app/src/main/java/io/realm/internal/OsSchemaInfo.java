package io.realm.internal;

import java.util.Collection;

/* loaded from: classes5.dex */
public class OsSchemaInfo implements NativeObject {
    private static final long b = nativeGetFinalizerPtr();
    private long a;
    private final OsSharedRealm c;

    private static native long nativeCreateFromList(long[] jArr);

    private static native long nativeGetFinalizerPtr();

    private static native long nativeGetObjectSchemaInfo(long j, String str);

    public OsSchemaInfo(Collection<OsObjectSchemaInfo> collection) {
        this.a = nativeCreateFromList(a(collection));
        NativeContext.dummyContext.addReference(this);
        this.c = null;
    }

    public OsSchemaInfo(long j, OsSharedRealm osSharedRealm) {
        this.a = j;
        this.c = osSharedRealm;
    }

    private static long[] a(Collection<OsObjectSchemaInfo> collection) {
        long[] jArr = new long[collection.size()];
        int i = 0;
        for (OsObjectSchemaInfo osObjectSchemaInfo : collection) {
            jArr[i] = osObjectSchemaInfo.getNativePtr();
            i++;
        }
        return jArr;
    }

    public OsObjectSchemaInfo getObjectSchemaInfo(String str) {
        return new OsObjectSchemaInfo(nativeGetObjectSchemaInfo(this.a, str));
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.a;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return b;
    }
}
