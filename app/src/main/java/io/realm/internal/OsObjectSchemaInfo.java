package io.realm.internal;

import io.realm.RealmFieldType;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class OsObjectSchemaInfo implements NativeObject {
    private static final long b = nativeGetFinalizerPtr();
    private long a;

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddProperties(long j, long[] jArr, long[] jArr2);

    private static native long nativeCreateRealmObjectSchema(String str);

    private static native String nativeGetClassName(long j);

    private static native long nativeGetFinalizerPtr();

    private static native long nativeGetPrimaryKeyProperty(long j);

    private static native long nativeGetProperty(long j, String str);

    /* loaded from: classes5.dex */
    public static class Builder {
        private final String a;
        private final long[] b;
        private final long[] d;
        private int c = 0;
        private int e = 0;

        public Builder(String str, int i, int i2) {
            this.a = str;
            this.b = new long[i];
            this.d = new long[i2];
        }

        public Builder addPersistedProperty(String str, RealmFieldType realmFieldType, boolean z, boolean z2, boolean z3) {
            long nativeCreatePersistedProperty = Property.nativeCreatePersistedProperty(str, Property.a(realmFieldType, z3), z, z2);
            long[] jArr = this.b;
            int i = this.c;
            jArr[i] = nativeCreatePersistedProperty;
            this.c = i + 1;
            return this;
        }

        public Builder addPersistedValueListProperty(String str, RealmFieldType realmFieldType, boolean z) {
            long nativeCreatePersistedProperty = Property.nativeCreatePersistedProperty(str, Property.a(realmFieldType, z), false, false);
            long[] jArr = this.b;
            int i = this.c;
            jArr[i] = nativeCreatePersistedProperty;
            this.c = i + 1;
            return this;
        }

        public Builder addPersistedLinkProperty(String str, RealmFieldType realmFieldType, String str2) {
            long nativeCreatePersistedLinkProperty = Property.nativeCreatePersistedLinkProperty(str, Property.a(realmFieldType, false), str2);
            long[] jArr = this.b;
            int i = this.c;
            jArr[i] = nativeCreatePersistedLinkProperty;
            this.c = i + 1;
            return this;
        }

        public Builder addComputedLinkProperty(String str, String str2, String str3) {
            long nativeCreateComputedLinkProperty = Property.nativeCreateComputedLinkProperty(str, str2, str3);
            long[] jArr = this.d;
            int i = this.e;
            jArr[i] = nativeCreateComputedLinkProperty;
            this.e = i + 1;
            return this;
        }

        public OsObjectSchemaInfo build() {
            if (this.c == -1 || this.e == -1) {
                throw new IllegalStateException("'OsObjectSchemaInfo.build()' has been called before on this object.");
            }
            OsObjectSchemaInfo osObjectSchemaInfo = new OsObjectSchemaInfo(this.a);
            OsObjectSchemaInfo.nativeAddProperties(osObjectSchemaInfo.a, this.b, this.d);
            this.c = -1;
            this.e = -1;
            return osObjectSchemaInfo;
        }
    }

    private OsObjectSchemaInfo(String str) {
        this(nativeCreateRealmObjectSchema(str));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OsObjectSchemaInfo(long j) {
        this.a = j;
        NativeContext.dummyContext.addReference(this);
    }

    public String getClassName() {
        return nativeGetClassName(this.a);
    }

    public Property getProperty(String str) {
        return new Property(nativeGetProperty(this.a, str));
    }

    @Nullable
    public Property getPrimaryKeyProperty() {
        if (nativeGetPrimaryKeyProperty(this.a) == 0) {
            return null;
        }
        return new Property(nativeGetPrimaryKeyProperty(this.a));
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
