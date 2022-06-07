package io.realm.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final class NativeObjectReference extends PhantomReference<NativeObject> {
    private static a f = new a();
    private final long a;
    private final long b;
    private final NativeContext c;
    private NativeObjectReference d;
    private NativeObjectReference e;

    private static native void nativeCleanUp(long j, long j2);

    /* loaded from: classes5.dex */
    private static class a {
        NativeObjectReference a;

        private a() {
        }

        synchronized void a(NativeObjectReference nativeObjectReference) {
            nativeObjectReference.d = null;
            nativeObjectReference.e = this.a;
            if (this.a != null) {
                this.a.d = nativeObjectReference;
            }
            this.a = nativeObjectReference;
        }

        synchronized void b(NativeObjectReference nativeObjectReference) {
            NativeObjectReference nativeObjectReference2 = nativeObjectReference.e;
            NativeObjectReference nativeObjectReference3 = nativeObjectReference.d;
            nativeObjectReference.e = null;
            nativeObjectReference.d = null;
            if (nativeObjectReference3 != null) {
                nativeObjectReference3.e = nativeObjectReference2;
            } else {
                this.a = nativeObjectReference2;
            }
            if (nativeObjectReference2 != null) {
                nativeObjectReference2.d = nativeObjectReference3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public NativeObjectReference(NativeContext nativeContext, NativeObject nativeObject, ReferenceQueue<? super NativeObject> referenceQueue) {
        super(nativeObject, referenceQueue);
        this.a = nativeObject.getNativePtr();
        this.b = nativeObject.getNativeFinalizerPtr();
        this.c = nativeContext;
        f.a(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        synchronized (this.c) {
            nativeCleanUp(this.b, this.a);
        }
        f.b(this);
    }
}
