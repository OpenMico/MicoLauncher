package com.google.common.base;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
/* loaded from: classes2.dex */
public class FinalizableReferenceQueue implements Closeable {
    private static final Logger d = Logger.getLogger(FinalizableReferenceQueue.class.getName());
    private static final Method e = a(a(new d(), new a(), new b()));
    final ReferenceQueue<Object> a = new ReferenceQueue<>();
    final PhantomReference<Object> b = new PhantomReference<>(this, this.a);
    final boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface c {
        @NullableDecl
        Class<?> a();
    }

    public FinalizableReferenceQueue() {
        boolean z = true;
        try {
            e.invoke(null, FinalizableReference.class, this.a, this.b);
        } catch (IllegalAccessException e2) {
            throw new AssertionError(e2);
        } catch (Throwable th) {
            d.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", th);
            z = false;
        }
        this.c = z;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.b.enqueue();
        a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        if (!this.c) {
            while (true) {
                Reference<? extends Object> poll = this.a.poll();
                if (poll != null) {
                    poll.clear();
                    try {
                        ((FinalizableReference) poll).finalizeReferent();
                    } catch (Throwable th) {
                        d.log(Level.SEVERE, "Error cleaning up after reference.", th);
                    }
                } else {
                    return;
                }
            }
        }
    }

    private static Class<?> a(c... cVarArr) {
        for (c cVar : cVarArr) {
            Class<?> a2 = cVar.a();
            if (a2 != null) {
                return a2;
            }
        }
        throw new AssertionError();
    }

    /* loaded from: classes2.dex */
    static class d implements c {
        @VisibleForTesting
        static boolean a;

        d() {
        }

        @Override // com.google.common.base.FinalizableReferenceQueue.c
        @NullableDecl
        public Class<?> a() {
            if (a) {
                return null;
            }
            try {
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                if (systemClassLoader == null) {
                    return null;
                }
                try {
                    return systemClassLoader.loadClass("com.google.common.base.internal.Finalizer");
                } catch (ClassNotFoundException unused) {
                    return null;
                }
            } catch (SecurityException unused2) {
                FinalizableReferenceQueue.d.info("Not allowed to access system class loader.");
                return null;
            }
        }
    }

    /* loaded from: classes2.dex */
    static class a implements c {
        a() {
        }

        @Override // com.google.common.base.FinalizableReferenceQueue.c
        @NullableDecl
        public Class<?> a() {
            try {
                return a(b()).loadClass("com.google.common.base.internal.Finalizer");
            } catch (Exception e) {
                FinalizableReferenceQueue.d.log(Level.WARNING, "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Guava to your system class path.", (Throwable) e);
                return null;
            }
        }

        URL b() throws IOException {
            String str = "com.google.common.base.internal.Finalizer".replace('.', (char) JsonPointer.SEPARATOR) + ".class";
            URL resource = getClass().getClassLoader().getResource(str);
            if (resource != null) {
                String url = resource.toString();
                if (url.endsWith(str)) {
                    return new URL(resource, url.substring(0, url.length() - str.length()));
                }
                throw new IOException("Unsupported path style: " + url);
            }
            throw new FileNotFoundException(str);
        }

        URLClassLoader a(URL url) {
            return new URLClassLoader(new URL[]{url}, null);
        }
    }

    /* loaded from: classes2.dex */
    static class b implements c {
        b() {
        }

        @Override // com.google.common.base.FinalizableReferenceQueue.c
        public Class<?> a() {
            try {
                return Class.forName("com.google.common.base.internal.Finalizer");
            } catch (ClassNotFoundException e) {
                throw new AssertionError(e);
            }
        }
    }

    static Method a(Class<?> cls) {
        try {
            return cls.getMethod("startFinalizer", Class.class, ReferenceQueue.class, PhantomReference.class);
        } catch (NoSuchMethodException e2) {
            throw new AssertionError(e2);
        }
    }
}
