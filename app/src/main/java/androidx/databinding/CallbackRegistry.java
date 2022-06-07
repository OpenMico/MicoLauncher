package androidx.databinding;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CallbackRegistry<C, T, A> implements Cloneable {
    private List<C> a = new ArrayList();
    private long b = 0;
    private long[] c;
    private int d;
    private final NotifierCallback<C, T, A> e;

    /* loaded from: classes.dex */
    public static abstract class NotifierCallback<C, T, A> {
        public abstract void onNotifyCallback(C c, T t, int i, A a);
    }

    public CallbackRegistry(NotifierCallback<C, T, A> notifierCallback) {
        this.e = notifierCallback;
    }

    public synchronized void notifyCallbacks(T t, int i, A a) {
        this.d++;
        b(t, i, a);
        this.d--;
        if (this.d == 0) {
            if (this.c != null) {
                for (int length = this.c.length - 1; length >= 0; length--) {
                    long j = this.c[length];
                    if (j != 0) {
                        a((length + 1) * 64, j);
                        this.c[length] = 0;
                    }
                }
            }
            if (this.b != 0) {
                a(0, this.b);
                this.b = 0L;
            }
        }
    }

    private void a(T t, int i, A a) {
        a(t, i, a, 0, Math.min(64, this.a.size()), this.b);
    }

    private void b(T t, int i, A a) {
        int size = this.a.size();
        long[] jArr = this.c;
        int length = jArr == null ? -1 : jArr.length - 1;
        a(t, i, a, length);
        a(t, i, a, (length + 2) * 64, size, 0L);
    }

    private void a(T t, int i, A a, int i2) {
        if (i2 < 0) {
            a(t, i, a);
            return;
        }
        long j = this.c[i2];
        int i3 = (i2 + 1) * 64;
        int min = Math.min(this.a.size(), i3 + 64);
        a(t, i, a, i2 - 1);
        a(t, i, a, i3, min, j);
    }

    private void a(T t, int i, A a, int i2, int i3, long j) {
        long j2 = 1;
        while (i2 < i3) {
            if ((j & j2) == 0) {
                this.e.onNotifyCallback(this.a.get(i2), t, i, a);
            }
            j2 <<= 1;
            i2++;
        }
    }

    public synchronized void add(C c) {
        if (c != null) {
            int lastIndexOf = this.a.lastIndexOf(c);
            if (lastIndexOf < 0 || a(lastIndexOf)) {
                this.a.add(c);
            }
        } else {
            throw new IllegalArgumentException("callback cannot be null");
        }
    }

    private boolean a(int i) {
        int i2;
        if (i < 64) {
            return ((1 << i) & this.b) != 0;
        }
        long[] jArr = this.c;
        if (jArr != null && (i2 = (i / 64) - 1) < jArr.length) {
            return ((1 << (i % 64)) & jArr[i2]) != 0;
        }
        return false;
    }

    private void a(int i, long j) {
        long j2 = Long.MIN_VALUE;
        for (int i2 = (i + 64) - 1; i2 >= i; i2--) {
            if ((j & j2) != 0) {
                this.a.remove(i2);
            }
            j2 >>>= 1;
        }
    }

    public synchronized void remove(C c) {
        if (this.d == 0) {
            this.a.remove(c);
        } else {
            int lastIndexOf = this.a.lastIndexOf(c);
            if (lastIndexOf >= 0) {
                b(lastIndexOf);
            }
        }
    }

    private void b(int i) {
        if (i < 64) {
            this.b = (1 << i) | this.b;
            return;
        }
        int i2 = (i / 64) - 1;
        long[] jArr = this.c;
        if (jArr == null) {
            this.c = new long[this.a.size() / 64];
        } else if (jArr.length <= i2) {
            long[] jArr2 = new long[this.a.size() / 64];
            long[] jArr3 = this.c;
            System.arraycopy(jArr3, 0, jArr2, 0, jArr3.length);
            this.c = jArr2;
        }
        long j = 1 << (i % 64);
        long[] jArr4 = this.c;
        jArr4[i2] = j | jArr4[i2];
    }

    public synchronized ArrayList<C> copyCallbacks() {
        ArrayList<C> arrayList;
        arrayList = new ArrayList<>(this.a.size());
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            if (!a(i)) {
                arrayList.add(this.a.get(i));
            }
        }
        return arrayList;
    }

    public synchronized void copyCallbacks(List<C> list) {
        list.clear();
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            if (!a(i)) {
                list.add(this.a.get(i));
            }
        }
    }

    public synchronized boolean isEmpty() {
        if (this.a.isEmpty()) {
            return true;
        }
        if (this.d == 0) {
            return false;
        }
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            if (!a(i)) {
                return false;
            }
        }
        return true;
    }

    public synchronized void clear() {
        if (this.d == 0) {
            this.a.clear();
        } else if (!this.a.isEmpty()) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                b(size);
            }
        }
    }

    public synchronized CallbackRegistry<C, T, A> clone() {
        CallbackRegistry<C, T, A> callbackRegistry;
        CloneNotSupportedException e;
        try {
            callbackRegistry = (CallbackRegistry) super.clone();
            try {
                callbackRegistry.b = 0L;
                callbackRegistry.c = null;
                callbackRegistry.d = 0;
                callbackRegistry.a = new ArrayList();
                int size = this.a.size();
                for (int i = 0; i < size; i++) {
                    if (!a(i)) {
                        callbackRegistry.a.add(this.a.get(i));
                    }
                }
            } catch (CloneNotSupportedException e2) {
                e = e2;
                e.printStackTrace();
                return callbackRegistry;
            }
        } catch (CloneNotSupportedException e3) {
            e = e3;
            callbackRegistry = null;
        }
        return callbackRegistry;
    }
}
