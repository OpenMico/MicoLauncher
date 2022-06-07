package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: AggregateFuture.java */
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class c<InputT, OutputT> extends AbstractFuture.h<OutputT> {
    private static final Logger a = Logger.getLogger(c.class.getName());
    @NullableDecl
    private c<InputT, OutputT>.a b;

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected final void afterDone() {
        super.afterDone();
        c<InputT, OutputT>.a aVar = this.b;
        if (aVar != null) {
            this.b = null;
            ImmutableCollection immutableCollection = ((a) aVar).b;
            boolean wasInterrupted = wasInterrupted();
            if (wasInterrupted) {
                aVar.c();
            }
            if (isCancelled() && (immutableCollection != null)) {
                UnmodifiableIterator it = immutableCollection.iterator();
                while (it.hasNext()) {
                    ((ListenableFuture) it.next()).cancel(wasInterrupted);
                }
            }
        }
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected String pendingToString() {
        ImmutableCollection immutableCollection;
        c<InputT, OutputT>.a aVar = this.b;
        if (aVar == null || (immutableCollection = ((a) aVar).b) == null) {
            return null;
        }
        return "futures=[" + immutableCollection + "]";
    }

    final void a(c<InputT, OutputT>.a aVar) {
        this.b = aVar;
        aVar.f();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AggregateFuture.java */
    /* loaded from: classes2.dex */
    public abstract class a extends d implements Runnable {
        private ImmutableCollection<? extends ListenableFuture<? extends InputT>> b;
        private final boolean c;
        private final boolean d;

        abstract void a(boolean z, int i, @NullableDecl InputT inputt);

        abstract void b();

        void c() {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection, boolean z, boolean z2) {
            super(immutableCollection.size());
            c.this = r1;
            this.b = (ImmutableCollection) Preconditions.checkNotNull(immutableCollection);
            this.c = z;
            this.d = z2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            g();
        }

        public void f() {
            if (this.b.isEmpty()) {
                b();
            } else if (this.c) {
                final int i = 0;
                UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it = this.b.iterator();
                while (it.hasNext()) {
                    final ListenableFuture listenableFuture = (ListenableFuture) it.next();
                    i++;
                    listenableFuture.addListener(new Runnable() { // from class: com.google.common.util.concurrent.c.a.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                a.this.a(i, listenableFuture);
                            } finally {
                                a.this.g();
                            }
                        }
                    }, MoreExecutors.directExecutor());
                }
            } else {
                UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it2 = this.b.iterator();
                while (it2.hasNext()) {
                    ((ListenableFuture) it2.next()).addListener(this, MoreExecutors.directExecutor());
                }
            }
        }

        private void a(Throwable th) {
            boolean z;
            boolean z2;
            Preconditions.checkNotNull(th);
            boolean z3 = true;
            if (this.c) {
                z2 = c.this.setException(th);
                if (z2) {
                    a();
                    z = true;
                } else {
                    z = c.b(d(), th);
                }
            } else {
                z = true;
                z2 = false;
            }
            boolean z4 = th instanceof Error;
            boolean z5 = this.c;
            if (z2) {
                z3 = false;
            }
            if ((z5 & z3 & z) || z4) {
                c.a.log(Level.SEVERE, z4 ? "Input Future failed with Error" : "Got more than one input Future failure. Logging failures after the first", th);
            }
        }

        @Override // com.google.common.util.concurrent.d
        final void a(Set<Throwable> set) {
            if (!c.this.isCancelled()) {
                c.b(set, c.this.a());
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a(int i, Future<? extends InputT> future) {
            Preconditions.checkState(this.c || !c.this.isDone() || c.this.isCancelled(), "Future was done before all dependencies completed");
            try {
                Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
                if (this.c) {
                    if (future.isCancelled()) {
                        c.this.b = null;
                        c.this.cancel(false);
                    } else {
                        Object done = Futures.getDone(future);
                        if (this.d) {
                            a(this.c, i, (int) done);
                        }
                    }
                } else if (this.d && !future.isCancelled()) {
                    a(this.c, i, (int) Futures.getDone(future));
                }
            } catch (ExecutionException e) {
                a(e.getCause());
            } catch (Throwable th) {
                a(th);
            }
        }

        public void g() {
            int e = e();
            Preconditions.checkState(e >= 0, "Less than 0 remaining futures");
            if (e == 0) {
                h();
            }
        }

        private void h() {
            if (this.d && (!this.c)) {
                int i = 0;
                UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it = this.b.iterator();
                while (it.hasNext()) {
                    i++;
                    a(i, (ListenableFuture) it.next());
                }
            }
            b();
        }

        @ForOverride
        @OverridingMethodsMustInvokeSuper
        public void a() {
            this.b = null;
        }
    }

    public static boolean b(Set<Throwable> set, Throwable th) {
        while (th != null) {
            if (!set.add(th)) {
                return false;
            }
            th = th.getCause();
        }
        return true;
    }
}
