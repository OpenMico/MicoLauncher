package io.netty.handler.codec.spdy;

import io.netty.channel.ChannelPromise;
import io.netty.util.internal.PlatformDependent;
import java.util.Comparator;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class SpdySession {
    private final AtomicInteger a = new AtomicInteger();
    private final AtomicInteger b = new AtomicInteger();
    private final Map<Integer, b> c = PlatformDependent.newConcurrentHashMap();
    private final a d = new a();
    private final AtomicInteger e;
    private final AtomicInteger f;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SpdySession(int i, int i2) {
        this.e = new AtomicInteger(i);
        this.f = new AtomicInteger(i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(boolean z) {
        if (z) {
            return this.b.get();
        }
        return this.a.get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return this.c.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(int i) {
        return this.c.containsKey(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Map<Integer, b> b() {
        TreeMap treeMap = new TreeMap(this.d);
        treeMap.putAll(this.c);
        return treeMap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, byte b2, boolean z, boolean z2, int i2, int i3, boolean z3) {
        if ((z && z2) || this.c.put(Integer.valueOf(i), new b(b2, z, z2, i2, i3)) != null) {
            return;
        }
        if (z3) {
            this.b.incrementAndGet();
        } else {
            this.a.incrementAndGet();
        }
    }

    private b c(int i, boolean z) {
        b remove = this.c.remove(Integer.valueOf(i));
        if (remove != null) {
            if (z) {
                this.b.decrementAndGet();
            } else {
                this.a.decrementAndGet();
            }
        }
        return remove;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, Throwable th, boolean z) {
        b c = c(i, z);
        if (c != null) {
            c.a(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b(int i) {
        b bVar = this.c.get(Integer.valueOf(i));
        return bVar == null || bVar.b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, boolean z) {
        b bVar = this.c.get(Integer.valueOf(i));
        if (bVar != null) {
            bVar.c();
            if (bVar.d()) {
                c(i, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c(int i) {
        b bVar = this.c.get(Integer.valueOf(i));
        return bVar == null || bVar.d();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(int i, boolean z) {
        b bVar = this.c.get(Integer.valueOf(i));
        if (bVar != null) {
            bVar.e();
            if (bVar.b()) {
                c(i, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean d(int i) {
        b bVar = this.c.get(Integer.valueOf(i));
        return bVar != null && bVar.f();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e(int i) {
        b bVar = this.c.get(Integer.valueOf(i));
        if (bVar != null) {
            bVar.g();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int f(int i) {
        if (i == 0) {
            return this.e.get();
        }
        b bVar = this.c.get(Integer.valueOf(i));
        if (bVar != null) {
            return bVar.h();
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(int i, int i2) {
        if (i == 0) {
            return this.e.addAndGet(i2);
        }
        b bVar = this.c.get(Integer.valueOf(i));
        if (bVar != null) {
            return bVar.a(i2);
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b(int i, int i2) {
        if (i == 0) {
            return this.f.addAndGet(i2);
        }
        b bVar = this.c.get(Integer.valueOf(i));
        if (bVar == null) {
            return -1;
        }
        if (i2 > 0) {
            bVar.c(0);
        }
        return bVar.b(i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int g(int i) {
        b bVar;
        if (i == 0 || (bVar = this.c.get(Integer.valueOf(i))) == null) {
            return 0;
        }
        return bVar.i();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void h(int i) {
        for (b bVar : this.c.values()) {
            bVar.a(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void i(int i) {
        for (b bVar : this.c.values()) {
            bVar.b(i);
            if (i < 0) {
                bVar.c(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(int i, PendingWrite pendingWrite) {
        b bVar = this.c.get(Integer.valueOf(i));
        return bVar != null && bVar.a(pendingWrite);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PendingWrite j(int i) {
        PendingWrite j;
        if (i == 0) {
            for (Map.Entry<Integer, b> entry : b().entrySet()) {
                b value = entry.getValue();
                if (value.h() > 0 && (j = value.j()) != null) {
                    return j;
                }
            }
            return null;
        }
        b bVar = this.c.get(Integer.valueOf(i));
        if (bVar != null) {
            return bVar.j();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PendingWrite k(int i) {
        b bVar = this.c.get(Integer.valueOf(i));
        if (bVar != null) {
            return bVar.k();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b {
        private final byte a;
        private boolean b;
        private boolean c;
        private boolean d;
        private final AtomicInteger e;
        private final AtomicInteger f;
        private int g;
        private final Queue<PendingWrite> h = new ConcurrentLinkedQueue();

        b(byte b, boolean z, boolean z2, int i, int i2) {
            this.a = b;
            this.b = z;
            this.c = z2;
            this.e = new AtomicInteger(i);
            this.f = new AtomicInteger(i2);
        }

        byte a() {
            return this.a;
        }

        boolean b() {
            return this.b;
        }

        void c() {
            this.b = true;
        }

        boolean d() {
            return this.c;
        }

        void e() {
            this.c = true;
        }

        boolean f() {
            return this.d;
        }

        void g() {
            this.d = true;
        }

        int h() {
            return this.e.get();
        }

        int a(int i) {
            return this.e.addAndGet(i);
        }

        int b(int i) {
            return this.f.addAndGet(i);
        }

        int i() {
            return this.g;
        }

        void c(int i) {
            this.g = i;
        }

        boolean a(PendingWrite pendingWrite) {
            return this.h.offer(pendingWrite);
        }

        PendingWrite j() {
            return this.h.peek();
        }

        PendingWrite k() {
            return this.h.poll();
        }

        void a(Throwable th) {
            while (true) {
                PendingWrite poll = this.h.poll();
                if (poll != null) {
                    poll.a(th);
                } else {
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a implements Comparator<Integer> {
        a() {
        }

        /* renamed from: a */
        public int compare(Integer num, Integer num2) {
            int a = ((b) SpdySession.this.c.get(num)).a() - ((b) SpdySession.this.c.get(num2)).a();
            return a != 0 ? a : num.intValue() - num2.intValue();
        }
    }

    /* loaded from: classes4.dex */
    public static final class PendingWrite {
        final SpdyDataFrame a;
        final ChannelPromise b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public PendingWrite(SpdyDataFrame spdyDataFrame, ChannelPromise channelPromise) {
            this.a = spdyDataFrame;
            this.b = channelPromise;
        }

        void a(Throwable th) {
            this.a.release();
            this.b.setFailure(th);
        }
    }
}
