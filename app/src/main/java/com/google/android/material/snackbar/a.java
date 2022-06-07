package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;

/* compiled from: SnackbarManager.java */
/* loaded from: classes2.dex */
class a {
    private static a a;
    @NonNull
    private final Object b = new Object();
    @NonNull
    private final Handler c = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.material.snackbar.a.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(@NonNull Message message) {
            if (message.what != 0) {
                return false;
            }
            a.this.a((b) message.obj);
            return true;
        }
    });
    @Nullable
    private b d;
    @Nullable
    private b e;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SnackbarManager.java */
    /* renamed from: com.google.android.material.snackbar.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public interface AbstractC0079a {
        void a();

        void a(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a a() {
        if (a == null) {
            a = new a();
        }
        return a;
    }

    private a() {
    }

    public void a(int i, AbstractC0079a aVar) {
        synchronized (this.b) {
            if (g(aVar)) {
                this.d.b = i;
                this.c.removeCallbacksAndMessages(this.d);
                b(this.d);
                return;
            }
            if (h(aVar)) {
                this.e.b = i;
            } else {
                this.e = new b(i, aVar);
            }
            if (this.d == null || !a(this.d, 4)) {
                this.d = null;
                b();
            }
        }
    }

    public void a(AbstractC0079a aVar, int i) {
        synchronized (this.b) {
            if (g(aVar)) {
                a(this.d, i);
            } else if (h(aVar)) {
                a(this.e, i);
            }
        }
    }

    public void a(AbstractC0079a aVar) {
        synchronized (this.b) {
            if (g(aVar)) {
                this.d = null;
                if (this.e != null) {
                    b();
                }
            }
        }
    }

    public void b(AbstractC0079a aVar) {
        synchronized (this.b) {
            if (g(aVar)) {
                b(this.d);
            }
        }
    }

    public void c(AbstractC0079a aVar) {
        synchronized (this.b) {
            if (g(aVar) && !this.d.c) {
                this.d.c = true;
                this.c.removeCallbacksAndMessages(this.d);
            }
        }
    }

    public void d(AbstractC0079a aVar) {
        synchronized (this.b) {
            if (g(aVar) && this.d.c) {
                this.d.c = false;
                b(this.d);
            }
        }
    }

    public boolean e(AbstractC0079a aVar) {
        boolean g;
        synchronized (this.b) {
            g = g(aVar);
        }
        return g;
    }

    public boolean f(AbstractC0079a aVar) {
        boolean z;
        synchronized (this.b) {
            if (!g(aVar) && !h(aVar)) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SnackbarManager.java */
    /* loaded from: classes2.dex */
    public static class b {
        @NonNull
        final WeakReference<AbstractC0079a> a;
        int b;
        boolean c;

        b(int i, AbstractC0079a aVar) {
            this.a = new WeakReference<>(aVar);
            this.b = i;
        }

        boolean a(@Nullable AbstractC0079a aVar) {
            return aVar != null && this.a.get() == aVar;
        }
    }

    private void b() {
        b bVar = this.e;
        if (bVar != null) {
            this.d = bVar;
            this.e = null;
            AbstractC0079a aVar = this.d.a.get();
            if (aVar != null) {
                aVar.a();
            } else {
                this.d = null;
            }
        }
    }

    private boolean a(@NonNull b bVar, int i) {
        AbstractC0079a aVar = bVar.a.get();
        if (aVar == null) {
            return false;
        }
        this.c.removeCallbacksAndMessages(bVar);
        aVar.a(i);
        return true;
    }

    private boolean g(AbstractC0079a aVar) {
        b bVar = this.d;
        return bVar != null && bVar.a(aVar);
    }

    private boolean h(AbstractC0079a aVar) {
        b bVar = this.e;
        return bVar != null && bVar.a(aVar);
    }

    private void b(@NonNull b bVar) {
        if (bVar.b != -2) {
            int i = 2750;
            if (bVar.b > 0) {
                i = bVar.b;
            } else if (bVar.b == -1) {
                i = 1500;
            }
            this.c.removeCallbacksAndMessages(bVar);
            Handler handler = this.c;
            handler.sendMessageDelayed(Message.obtain(handler, 0, bVar), i);
        }
    }

    void a(@NonNull b bVar) {
        synchronized (this.b) {
            if (this.d == bVar || this.e == bVar) {
                a(bVar, 2);
            }
        }
    }
}
