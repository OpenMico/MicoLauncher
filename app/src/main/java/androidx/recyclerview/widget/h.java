package androidx.recyclerview.widget;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.recyclerview.widget.ThreadUtil;
import androidx.recyclerview.widget.TileList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: MessageThreadUtil.java */
/* loaded from: classes.dex */
class h<T> implements ThreadUtil<T> {

    /* compiled from: MessageThreadUtil.java */
    /* renamed from: androidx.recyclerview.widget.h$1  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements ThreadUtil.MainThreadCallback<T> {
        final /* synthetic */ ThreadUtil.MainThreadCallback b;
        final a a = new a();
        private final Handler d = new Handler(Looper.getMainLooper());
        private Runnable e = new Runnable() { // from class: androidx.recyclerview.widget.h.1.1
            @Override // java.lang.Runnable
            public void run() {
                b a = AnonymousClass1.this.a.a();
                while (a != null) {
                    switch (a.b) {
                        case 1:
                            AnonymousClass1.this.b.updateItemCount(a.c, a.d);
                            break;
                        case 2:
                            AnonymousClass1.this.b.addTile(a.c, (TileList.Tile) a.h);
                            break;
                        case 3:
                            AnonymousClass1.this.b.removeTile(a.c, a.d);
                            break;
                        default:
                            Log.e("ThreadUtil", "Unsupported message, what=" + a.b);
                            break;
                    }
                    a = AnonymousClass1.this.a.a();
                }
            }
        };

        AnonymousClass1(ThreadUtil.MainThreadCallback mainThreadCallback) {
            this.b = mainThreadCallback;
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void updateItemCount(int i, int i2) {
            a(b.a(1, i, i2));
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void addTile(int i, TileList.Tile<T> tile) {
            a(b.a(2, i, tile));
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
        public void removeTile(int i, int i2) {
            a(b.a(3, i, i2));
        }

        private void a(b bVar) {
            this.a.b(bVar);
            this.d.post(this.e);
        }
    }

    @Override // androidx.recyclerview.widget.ThreadUtil
    public ThreadUtil.MainThreadCallback<T> a(ThreadUtil.MainThreadCallback<T> mainThreadCallback) {
        return new AnonymousClass1(mainThreadCallback);
    }

    /* compiled from: MessageThreadUtil.java */
    /* renamed from: androidx.recyclerview.widget.h$2  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass2 implements ThreadUtil.BackgroundCallback<T> {
        final /* synthetic */ ThreadUtil.BackgroundCallback c;
        final a a = new a();
        private final Executor e = AsyncTask.THREAD_POOL_EXECUTOR;
        AtomicBoolean b = new AtomicBoolean(false);
        private Runnable f = new Runnable() { // from class: androidx.recyclerview.widget.h.2.1
            @Override // java.lang.Runnable
            public void run() {
                while (true) {
                    b a = AnonymousClass2.this.a.a();
                    if (a != null) {
                        switch (a.b) {
                            case 1:
                                AnonymousClass2.this.a.a(1);
                                AnonymousClass2.this.c.refresh(a.c);
                                break;
                            case 2:
                                AnonymousClass2.this.a.a(2);
                                AnonymousClass2.this.a.a(3);
                                AnonymousClass2.this.c.updateRange(a.c, a.d, a.e, a.f, a.g);
                                break;
                            case 3:
                                AnonymousClass2.this.c.loadTile(a.c, a.d);
                                break;
                            case 4:
                                AnonymousClass2.this.c.recycleTile((TileList.Tile) a.h);
                                break;
                            default:
                                Log.e("ThreadUtil", "Unsupported message, what=" + a.b);
                                break;
                        }
                    } else {
                        AnonymousClass2.this.b.set(false);
                        return;
                    }
                }
            }
        };

        AnonymousClass2(ThreadUtil.BackgroundCallback backgroundCallback) {
            this.c = backgroundCallback;
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void refresh(int i) {
            b(b.a(1, i, (Object) null));
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void updateRange(int i, int i2, int i3, int i4, int i5) {
            b(b.a(2, i, i2, i3, i4, i5, null));
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void loadTile(int i, int i2) {
            a(b.a(3, i, i2));
        }

        @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
        public void recycleTile(TileList.Tile<T> tile) {
            a(b.a(4, 0, tile));
        }

        private void a(b bVar) {
            this.a.b(bVar);
            a();
        }

        private void b(b bVar) {
            this.a.a(bVar);
            a();
        }

        private void a() {
            if (this.b.compareAndSet(false, true)) {
                this.e.execute(this.f);
            }
        }
    }

    @Override // androidx.recyclerview.widget.ThreadUtil
    public ThreadUtil.BackgroundCallback<T> a(ThreadUtil.BackgroundCallback<T> backgroundCallback) {
        return new AnonymousClass2(backgroundCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MessageThreadUtil.java */
    /* loaded from: classes.dex */
    public static class b {
        private static b i;
        private static final Object j = new Object();
        b a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public Object h;

        b() {
        }

        void a() {
            this.a = null;
            this.g = 0;
            this.f = 0;
            this.e = 0;
            this.d = 0;
            this.c = 0;
            this.b = 0;
            this.h = null;
            synchronized (j) {
                if (i != null) {
                    this.a = i;
                }
                i = this;
            }
        }

        static b a(int i2, int i3, int i4, int i5, int i6, int i7, Object obj) {
            b bVar;
            synchronized (j) {
                if (i == null) {
                    bVar = new b();
                } else {
                    bVar = i;
                    i = i.a;
                    bVar.a = null;
                }
                bVar.b = i2;
                bVar.c = i3;
                bVar.d = i4;
                bVar.e = i5;
                bVar.f = i6;
                bVar.g = i7;
                bVar.h = obj;
            }
            return bVar;
        }

        static b a(int i2, int i3, int i4) {
            return a(i2, i3, i4, 0, 0, 0, null);
        }

        static b a(int i2, int i3, Object obj) {
            return a(i2, i3, 0, 0, 0, 0, obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MessageThreadUtil.java */
    /* loaded from: classes.dex */
    public static class a {
        private b a;

        a() {
        }

        synchronized b a() {
            if (this.a == null) {
                return null;
            }
            b bVar = this.a;
            this.a = this.a.a;
            return bVar;
        }

        synchronized void a(b bVar) {
            bVar.a = this.a;
            this.a = bVar;
        }

        synchronized void b(b bVar) {
            if (this.a == null) {
                this.a = bVar;
                return;
            }
            b bVar2 = this.a;
            while (bVar2.a != null) {
                bVar2 = bVar2.a;
            }
            bVar2.a = bVar;
        }

        synchronized void a(int i) {
            while (this.a != null && this.a.b == i) {
                b bVar = this.a;
                this.a = this.a.a;
                bVar.a();
            }
            if (this.a != null) {
                b bVar2 = this.a;
                b bVar3 = bVar2.a;
                while (bVar3 != null) {
                    b bVar4 = bVar3.a;
                    if (bVar3.b == i) {
                        bVar2.a = bVar4;
                        bVar3.a();
                    } else {
                        bVar2 = bVar3;
                    }
                    bVar3 = bVar4;
                }
            }
        }
    }
}
