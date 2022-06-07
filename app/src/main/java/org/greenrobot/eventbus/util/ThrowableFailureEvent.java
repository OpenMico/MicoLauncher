package org.greenrobot.eventbus.util;

/* loaded from: classes5.dex */
public class ThrowableFailureEvent implements HasExecutionScope {
    private Object a;
    protected final boolean suppressErrorUi;
    protected final Throwable throwable;

    public ThrowableFailureEvent(Throwable th) {
        this.throwable = th;
        this.suppressErrorUi = false;
    }

    public ThrowableFailureEvent(Throwable th, boolean z) {
        this.throwable = th;
        this.suppressErrorUi = z;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public boolean isSuppressErrorUi() {
        return this.suppressErrorUi;
    }

    @Override // org.greenrobot.eventbus.util.HasExecutionScope
    public Object getExecutionScope() {
        return this.a;
    }

    @Override // org.greenrobot.eventbus.util.HasExecutionScope
    public void setExecutionScope(Object obj) {
        this.a = obj;
    }
}
