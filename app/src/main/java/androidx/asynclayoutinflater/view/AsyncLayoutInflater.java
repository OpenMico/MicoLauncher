package androidx.asynclayoutinflater.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.util.Pools;
import java.util.concurrent.ArrayBlockingQueue;

/* loaded from: classes.dex */
public final class AsyncLayoutInflater {
    LayoutInflater a;
    private Handler.Callback d = new Handler.Callback() { // from class: androidx.asynclayoutinflater.view.AsyncLayoutInflater.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            b bVar = (b) message.obj;
            if (bVar.d == null) {
                bVar.d = AsyncLayoutInflater.this.a.inflate(bVar.c, bVar.b, false);
            }
            bVar.e.onInflateFinished(bVar.d, bVar.c, bVar.b);
            AsyncLayoutInflater.this.c.a(bVar);
            return true;
        }
    };
    Handler b = new Handler(this.d);
    c c = c.a();

    /* loaded from: classes.dex */
    public interface OnInflateFinishedListener {
        void onInflateFinished(@NonNull View view, @LayoutRes int i, @Nullable ViewGroup viewGroup);
    }

    public AsyncLayoutInflater(@NonNull Context context) {
        this.a = new a(context);
    }

    @UiThread
    public void inflate(@LayoutRes int i, @Nullable ViewGroup viewGroup, @NonNull OnInflateFinishedListener onInflateFinishedListener) {
        if (onInflateFinishedListener != null) {
            b c2 = this.c.c();
            c2.a = this;
            c2.c = i;
            c2.b = viewGroup;
            c2.e = onInflateFinishedListener;
            this.c.b(c2);
            return;
        }
        throw new NullPointerException("callback argument may not be null!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class b {
        AsyncLayoutInflater a;
        ViewGroup b;
        int c;
        View d;
        OnInflateFinishedListener e;

        b() {
        }
    }

    /* loaded from: classes.dex */
    private static class a extends LayoutInflater {
        private static final String[] a = {"android.widget.", "android.webkit.", "android.app."};

        a(Context context) {
            super(context);
        }

        @Override // android.view.LayoutInflater
        public LayoutInflater cloneInContext(Context context) {
            return new a(context);
        }

        @Override // android.view.LayoutInflater
        protected View onCreateView(String str, AttributeSet attributeSet) throws ClassNotFoundException {
            View createView;
            for (String str2 : a) {
                try {
                    createView = createView(str, str2, attributeSet);
                } catch (ClassNotFoundException unused) {
                }
                if (createView != null) {
                    return createView;
                }
            }
            return super.onCreateView(str, attributeSet);
        }
    }

    /* loaded from: classes.dex */
    private static class c extends Thread {
        private static final c a = new c();
        private ArrayBlockingQueue<b> b = new ArrayBlockingQueue<>(10);
        private Pools.SynchronizedPool<b> c = new Pools.SynchronizedPool<>(10);

        private c() {
        }

        static {
            a.start();
        }

        public static c a() {
            return a;
        }

        public void b() {
            try {
                b take = this.b.take();
                try {
                    take.d = take.a.a.inflate(take.c, take.b, false);
                } catch (RuntimeException e) {
                    Log.w("AsyncLayoutInflater", "Failed to inflate resource in the background! Retrying on the UI thread", e);
                }
                Message.obtain(take.a.b, 0, take).sendToTarget();
            } catch (InterruptedException e2) {
                Log.w("AsyncLayoutInflater", e2);
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                b();
            }
        }

        public b c() {
            b acquire = this.c.acquire();
            return acquire == null ? new b() : acquire;
        }

        public void a(b bVar) {
            bVar.e = null;
            bVar.a = null;
            bVar.b = null;
            bVar.c = 0;
            bVar.d = null;
            this.c.release(bVar);
        }

        public void b(b bVar) {
            try {
                this.b.put(bVar);
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to enqueue async inflate request", e);
            }
        }
    }
}
