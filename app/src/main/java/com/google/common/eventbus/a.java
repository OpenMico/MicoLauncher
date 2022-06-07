package com.google.common.eventbus;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Dispatcher.java */
/* loaded from: classes2.dex */
public abstract class a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(Object obj, Iterator<b> it);

    a() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a a() {
        return new b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a b() {
        return new C0106a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Dispatcher.java */
    /* loaded from: classes2.dex */
    public static final class b extends a {
        private final ThreadLocal<Queue<C0108a>> a;
        private final ThreadLocal<Boolean> b;

        private b() {
            this.a = new ThreadLocal<Queue<C0108a>>() { // from class: com.google.common.eventbus.a.b.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Queue<C0108a> initialValue() {
                    return Queues.newArrayDeque();
                }
            };
            this.b = new ThreadLocal<Boolean>() { // from class: com.google.common.eventbus.a.b.2
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public Boolean initialValue() {
                    return false;
                }
            };
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x0053 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0037 A[Catch: all -> 0x005e, LOOP:0: B:7:0x0037->B:9:0x0041, LOOP_START, TryCatch #0 {all -> 0x005e, blocks: (B:5:0x002f, B:7:0x0037, B:9:0x0041), top: B:15:0x002f }] */
        @Override // com.google.common.eventbus.a
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void a(java.lang.Object r4, java.util.Iterator<com.google.common.eventbus.b> r5) {
            /*
                r3 = this;
                com.google.common.base.Preconditions.checkNotNull(r4)
                com.google.common.base.Preconditions.checkNotNull(r5)
                java.lang.ThreadLocal<java.util.Queue<com.google.common.eventbus.a$b$a>> r0 = r3.a
                java.lang.Object r0 = r0.get()
                java.util.Queue r0 = (java.util.Queue) r0
                com.google.common.eventbus.a$b$a r1 = new com.google.common.eventbus.a$b$a
                r2 = 0
                r1.<init>(r4, r5)
                r0.offer(r1)
                java.lang.ThreadLocal<java.lang.Boolean> r4 = r3.b
                java.lang.Object r4 = r4.get()
                java.lang.Boolean r4 = (java.lang.Boolean) r4
                boolean r4 = r4.booleanValue()
                if (r4 != 0) goto L_0x006a
                java.lang.ThreadLocal<java.lang.Boolean> r4 = r3.b
                r5 = 1
                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                r4.set(r5)
            L_0x002f:
                java.lang.Object r4 = r0.poll()     // Catch: all -> 0x005e
                com.google.common.eventbus.a$b$a r4 = (com.google.common.eventbus.a.b.C0108a) r4     // Catch: all -> 0x005e
                if (r4 == 0) goto L_0x0053
            L_0x0037:
                java.util.Iterator r5 = com.google.common.eventbus.a.b.C0108a.a(r4)     // Catch: all -> 0x005e
                boolean r5 = r5.hasNext()     // Catch: all -> 0x005e
                if (r5 == 0) goto L_0x002f
                java.util.Iterator r5 = com.google.common.eventbus.a.b.C0108a.a(r4)     // Catch: all -> 0x005e
                java.lang.Object r5 = r5.next()     // Catch: all -> 0x005e
                com.google.common.eventbus.b r5 = (com.google.common.eventbus.b) r5     // Catch: all -> 0x005e
                java.lang.Object r1 = com.google.common.eventbus.a.b.C0108a.b(r4)     // Catch: all -> 0x005e
                r5.a(r1)     // Catch: all -> 0x005e
                goto L_0x0037
            L_0x0053:
                java.lang.ThreadLocal<java.lang.Boolean> r4 = r3.b
                r4.remove()
                java.lang.ThreadLocal<java.util.Queue<com.google.common.eventbus.a$b$a>> r4 = r3.a
                r4.remove()
                goto L_0x006a
            L_0x005e:
                r4 = move-exception
                java.lang.ThreadLocal<java.lang.Boolean> r5 = r3.b
                r5.remove()
                java.lang.ThreadLocal<java.util.Queue<com.google.common.eventbus.a$b$a>> r5 = r3.a
                r5.remove()
                throw r4
            L_0x006a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.eventbus.a.b.a(java.lang.Object, java.util.Iterator):void");
        }

        /* compiled from: Dispatcher.java */
        /* renamed from: com.google.common.eventbus.a$b$a  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        private static final class C0108a {
            private final Object a;
            private final Iterator<b> b;

            private C0108a(Object obj, Iterator<b> it) {
                this.a = obj;
                this.b = it;
            }
        }
    }

    /* compiled from: Dispatcher.java */
    /* renamed from: com.google.common.eventbus.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    private static final class C0106a extends a {
        private final ConcurrentLinkedQueue<C0107a> a;

        private C0106a() {
            this.a = Queues.newConcurrentLinkedQueue();
        }

        @Override // com.google.common.eventbus.a
        void a(Object obj, Iterator<b> it) {
            Preconditions.checkNotNull(obj);
            while (it.hasNext()) {
                this.a.add(new C0107a(obj, it.next()));
            }
            while (true) {
                C0107a poll = this.a.poll();
                if (poll != null) {
                    poll.b.a(poll.a);
                } else {
                    return;
                }
            }
        }

        /* compiled from: Dispatcher.java */
        /* renamed from: com.google.common.eventbus.a$a$a  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        private static final class C0107a {
            private final Object a;
            private final b b;

            private C0107a(Object obj, b bVar) {
                this.a = obj;
                this.b = bVar;
            }
        }
    }
}
