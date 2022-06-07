package androidx.core.os;

import android.os.Build;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public final class CancellationSignal {
    private boolean a;
    private OnCancelListener b;
    private Object c;
    private boolean d;

    /* loaded from: classes.dex */
    public interface OnCancelListener {
        void onCancel();
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this) {
            z = this.a;
        }
        return z;
    }

    public void throwIfCanceled() {
        if (isCanceled()) {
            throw new OperationCanceledException();
        }
    }

    public void cancel() {
        synchronized (this) {
            try {
                if (!this.a) {
                    this.a = true;
                    this.d = true;
                    OnCancelListener onCancelListener = this.b;
                    Object obj = this.c;
                    if (onCancelListener != null) {
                        try {
                            onCancelListener.onCancel();
                        } catch (Throwable th) {
                            synchronized (this) {
                                try {
                                    this.d = false;
                                    notifyAll();
                                    throw th;
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                        }
                    }
                    if (obj != null && Build.VERSION.SDK_INT >= 16) {
                        ((android.os.CancellationSignal) obj).cancel();
                    }
                    synchronized (this) {
                        try {
                            this.d = false;
                            notifyAll();
                        } catch (Throwable th3) {
                            throw th3;
                        }
                    }
                }
            } catch (Throwable th4) {
                throw th4;
            }
        }
    }

    public void setOnCancelListener(@Nullable OnCancelListener onCancelListener) {
        synchronized (this) {
            a();
            if (this.b != onCancelListener) {
                this.b = onCancelListener;
                if (this.a && onCancelListener != null) {
                    onCancelListener.onCancel();
                }
            }
        }
    }

    @Nullable
    public Object getCancellationSignalObject() {
        Object obj;
        if (Build.VERSION.SDK_INT < 16) {
            return null;
        }
        synchronized (this) {
            if (this.c == null) {
                this.c = new android.os.CancellationSignal();
                if (this.a) {
                    ((android.os.CancellationSignal) this.c).cancel();
                }
            }
            obj = this.c;
        }
        return obj;
    }

    private void a() {
        while (this.d) {
            try {
                wait();
            } catch (InterruptedException unused) {
            }
        }
    }
}
