package com.xiaomi.micolauncher.skills.common.view.wakeup;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
public class CustomLinkedBlockingQueue {
    private final AtomicInteger a = new AtomicInteger();
    private final ReentrantLock b = new ReentrantLock();
    private final Condition c = this.b.newCondition();
    private final ReentrantLock d = new ReentrantLock();
    private final Condition e = this.d.newCondition();
    private final int f;
    private LinkedBitmap g;

    public CustomLinkedBlockingQueue(int i) {
        if (i > 0) {
            this.f = i;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void put(LinkedBitmap linkedBitmap) throws InterruptedException {
        if (linkedBitmap != null) {
            ReentrantLock reentrantLock = this.d;
            AtomicInteger atomicInteger = this.a;
            reentrantLock.lockInterruptibly();
            while (atomicInteger.get() == this.f) {
                try {
                    this.e.await();
                } finally {
                    reentrantLock.unlock();
                }
            }
            a(linkedBitmap);
            int andIncrement = atomicInteger.getAndIncrement();
            if (andIncrement + 1 < this.f) {
                this.e.signal();
            }
            if (andIncrement == 0) {
                d();
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    public boolean offer(LinkedBitmap linkedBitmap) {
        if (linkedBitmap != null) {
            AtomicInteger atomicInteger = this.a;
            if (atomicInteger.get() == this.f) {
                return false;
            }
            int i = -1;
            ReentrantLock reentrantLock = this.d;
            reentrantLock.lock();
            try {
                if (atomicInteger.get() < this.f) {
                    a(linkedBitmap);
                    i = atomicInteger.getAndIncrement();
                    if (i + 1 < this.f) {
                        this.e.signal();
                    }
                }
                if (i == 0) {
                    d();
                }
                return i >= 0;
            } finally {
                reentrantLock.unlock();
            }
        } else {
            throw new NullPointerException();
        }
    }

    /* JADX WARN: Finally extract failed */
    public LinkedBitmap take() throws InterruptedException {
        AtomicInteger atomicInteger = this.a;
        ReentrantLock reentrantLock = this.b;
        reentrantLock.lockInterruptibly();
        while (atomicInteger.get() == 0) {
            try {
                this.c.await();
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }
        LinkedBitmap c = c();
        int andDecrement = atomicInteger.getAndDecrement();
        if (andDecrement > 1) {
            this.c.signal();
        }
        reentrantLock.unlock();
        if (andDecrement == this.f) {
            e();
        }
        return c;
    }

    private void a(LinkedBitmap linkedBitmap) {
        if (linkedBitmap != null) {
            LinkedBitmap linkedBitmap2 = this.g;
            if (linkedBitmap2 == null) {
                this.g = linkedBitmap;
            } else {
                try {
                    linkedBitmap2.next = linkedBitmap;
                } catch (NullPointerException unused) {
                    this.g = linkedBitmap;
                }
            }
            linkedBitmap.next = null;
        }
    }

    private LinkedBitmap c() {
        LinkedBitmap linkedBitmap = this.g;
        if (linkedBitmap == null) {
            return null;
        }
        this.g = linkedBitmap.next;
        return linkedBitmap;
    }

    private void d() {
        ReentrantLock reentrantLock = this.b;
        reentrantLock.lock();
        try {
            this.c.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    private void e() {
        ReentrantLock reentrantLock = this.d;
        reentrantLock.lock();
        try {
            this.e.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    private void f() {
        LinkedBitmap linkedBitmap = this.g;
        if (linkedBitmap != null) {
            while (linkedBitmap != null) {
                if (linkedBitmap.bitmap != null) {
                    linkedBitmap.bitmap.recycle();
                }
                linkedBitmap.bitmap = null;
                linkedBitmap = linkedBitmap.next;
            }
        }
    }

    public void clear() {
        f();
        a();
        try {
            this.e.signalAll();
            this.c.signalAll();
        } finally {
            b();
        }
    }

    void a() {
        this.d.lock();
        this.b.lock();
    }

    void b() {
        this.b.unlock();
        this.d.unlock();
    }

    public int getCount() {
        return this.a.get();
    }
}
