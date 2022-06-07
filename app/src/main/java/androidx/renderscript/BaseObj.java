package androidx.renderscript;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public class BaseObj {
    private long a;
    private boolean b = false;
    RenderScript t;

    android.renderscript.BaseObj a() {
        return null;
    }

    public BaseObj(long j, RenderScript renderScript) {
        renderScript.g();
        this.t = renderScript;
        this.a = j;
    }

    void a(long j) {
        if (this.a == 0) {
            this.a = j;
            return;
        }
        throw new RSRuntimeException("Internal Error, reset of object ID.");
    }

    public long a(RenderScript renderScript) {
        this.t.g();
        if (this.b) {
            throw new RSInvalidStateException("using a destroyed object.");
        } else if (this.a == 0) {
            throw new RSRuntimeException("Internal error: Object id 0.");
        } else if (renderScript == null || renderScript == this.t) {
            return this.a;
        } else {
            throw new RSInvalidStateException("using object with mismatched context.");
        }
    }

    void b() {
        if (this.a == 0 && a() == null) {
            throw new RSIllegalArgumentException("Invalid object.");
        }
    }

    private void c() {
        boolean z;
        synchronized (this) {
            z = true;
            if (!this.b) {
                this.b = true;
            } else {
                z = false;
            }
        }
        if (z) {
            ReentrantReadWriteLock.ReadLock readLock = this.t.m.readLock();
            readLock.lock();
            if (this.t.i()) {
                this.t.a(this.a);
            }
            readLock.unlock();
            this.t = null;
            this.a = 0L;
        }
    }

    public void finalize() throws Throwable {
        c();
        super.finalize();
    }

    public void destroy() {
        if (!this.b) {
            c();
            return;
        }
        throw new RSInvalidStateException("Object already destroyed.");
    }

    public int hashCode() {
        long j = this.a;
        return (int) ((j >> 32) ^ (268435455 & j));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.a == ((BaseObj) obj).a;
    }
}
