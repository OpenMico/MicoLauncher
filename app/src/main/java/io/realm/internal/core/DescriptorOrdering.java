package io.realm.internal.core;

import io.realm.internal.NativeObject;

/* loaded from: classes5.dex */
public class DescriptorOrdering implements NativeObject {
    private static final long a = nativeGetFinalizerMethodPtr();
    private boolean c = false;
    private boolean d = false;
    private boolean e = false;
    private final long b = nativeCreate();

    private static native void nativeAppendDistinct(long j, QueryDescriptor queryDescriptor);

    private static native void nativeAppendInclude(long j, long j2);

    private static native void nativeAppendLimit(long j, long j2);

    private static native void nativeAppendSort(long j, QueryDescriptor queryDescriptor);

    private static native long nativeCreate();

    private static native long nativeGetFinalizerMethodPtr();

    private static native boolean nativeIsEmpty(long j);

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.b;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return a;
    }

    public void appendSort(QueryDescriptor queryDescriptor) {
        if (!this.c) {
            nativeAppendSort(this.b, queryDescriptor);
            this.c = true;
            return;
        }
        throw new IllegalStateException("A sorting order was already defined. It cannot be redefined");
    }

    public void appendDistinct(QueryDescriptor queryDescriptor) {
        if (!this.d) {
            nativeAppendDistinct(this.b, queryDescriptor);
            this.d = true;
            return;
        }
        throw new IllegalStateException("A distinct field was already defined. It cannot be redefined");
    }

    public void setLimit(long j) {
        if (!this.e) {
            nativeAppendLimit(this.b, j);
            this.e = true;
            return;
        }
        throw new IllegalStateException("A limit was already set. It cannot be redefined.");
    }

    public void appendIncludes(IncludeDescriptor includeDescriptor) {
        nativeAppendInclude(this.b, includeDescriptor.getNativePtr());
    }

    public boolean isEmpty() {
        return nativeIsEmpty(this.b);
    }
}
