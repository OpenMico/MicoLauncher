package io.realm.internal;

import java.lang.ref.ReferenceQueue;

/* loaded from: classes5.dex */
public class NativeContext {
    private static final ReferenceQueue<NativeObject> a = new ReferenceQueue<>();
    private static final Thread b = new Thread(new a(a));
    public static final NativeContext dummyContext = new NativeContext();

    static {
        b.setName("RealmFinalizingDaemon");
        b.start();
    }

    public void addReference(NativeObject nativeObject) {
        new NativeObjectReference(this, nativeObject, a);
    }
}
