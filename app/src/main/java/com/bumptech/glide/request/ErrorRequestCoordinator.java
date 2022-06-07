package com.bumptech.glide.request;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.RequestCoordinator;

/* loaded from: classes.dex */
public final class ErrorRequestCoordinator implements Request, RequestCoordinator {
    private final Object a;
    @Nullable
    private final RequestCoordinator b;
    private volatile Request c;
    private volatile Request d;
    @GuardedBy("requestLock")
    private RequestCoordinator.RequestState e = RequestCoordinator.RequestState.CLEARED;
    @GuardedBy("requestLock")
    private RequestCoordinator.RequestState f = RequestCoordinator.RequestState.CLEARED;

    public ErrorRequestCoordinator(Object obj, @Nullable RequestCoordinator requestCoordinator) {
        this.a = obj;
        this.b = requestCoordinator;
    }

    public void setRequests(Request request, Request request2) {
        this.c = request;
        this.d = request2;
    }

    @Override // com.bumptech.glide.request.Request
    public void begin() {
        synchronized (this.a) {
            if (this.e != RequestCoordinator.RequestState.RUNNING) {
                this.e = RequestCoordinator.RequestState.RUNNING;
                this.c.begin();
            }
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void clear() {
        synchronized (this.a) {
            this.e = RequestCoordinator.RequestState.CLEARED;
            this.c.clear();
            if (this.f != RequestCoordinator.RequestState.CLEARED) {
                this.f = RequestCoordinator.RequestState.CLEARED;
                this.d.clear();
            }
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void pause() {
        synchronized (this.a) {
            if (this.e == RequestCoordinator.RequestState.RUNNING) {
                this.e = RequestCoordinator.RequestState.PAUSED;
                this.c.pause();
            }
            if (this.f == RequestCoordinator.RequestState.RUNNING) {
                this.f = RequestCoordinator.RequestState.PAUSED;
                this.d.pause();
            }
        }
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isRunning() {
        boolean z;
        synchronized (this.a) {
            if (!(this.e == RequestCoordinator.RequestState.RUNNING || this.f == RequestCoordinator.RequestState.RUNNING)) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isComplete() {
        boolean z;
        synchronized (this.a) {
            if (!(this.e == RequestCoordinator.RequestState.SUCCESS || this.f == RequestCoordinator.RequestState.SUCCESS)) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isCleared() {
        boolean z;
        synchronized (this.a) {
            z = this.e == RequestCoordinator.RequestState.CLEARED && this.f == RequestCoordinator.RequestState.CLEARED;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isEquivalentTo(Request request) {
        if (!(request instanceof ErrorRequestCoordinator)) {
            return false;
        }
        ErrorRequestCoordinator errorRequestCoordinator = (ErrorRequestCoordinator) request;
        return this.c.isEquivalentTo(errorRequestCoordinator.c) && this.d.isEquivalentTo(errorRequestCoordinator.d);
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canSetImage(Request request) {
        boolean z;
        synchronized (this.a) {
            z = a() && a(request);
        }
        return z;
    }

    @GuardedBy("requestLock")
    private boolean a() {
        RequestCoordinator requestCoordinator = this.b;
        return requestCoordinator == null || requestCoordinator.canSetImage(this);
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canNotifyStatusChanged(Request request) {
        boolean z;
        synchronized (this.a) {
            z = c() && a(request);
        }
        return z;
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canNotifyCleared(Request request) {
        boolean z;
        synchronized (this.a) {
            z = b() && a(request);
        }
        return z;
    }

    @GuardedBy("requestLock")
    private boolean b() {
        RequestCoordinator requestCoordinator = this.b;
        return requestCoordinator == null || requestCoordinator.canNotifyCleared(this);
    }

    @GuardedBy("requestLock")
    private boolean c() {
        RequestCoordinator requestCoordinator = this.b;
        return requestCoordinator == null || requestCoordinator.canNotifyStatusChanged(this);
    }

    @GuardedBy("requestLock")
    private boolean a(Request request) {
        return request.equals(this.c) || (this.e == RequestCoordinator.RequestState.FAILED && request.equals(this.d));
    }

    @Override // com.bumptech.glide.request.Request, com.bumptech.glide.request.RequestCoordinator
    public boolean isAnyResourceSet() {
        boolean z;
        synchronized (this.a) {
            if (!this.c.isAnyResourceSet() && !this.d.isAnyResourceSet()) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public void onRequestSuccess(Request request) {
        synchronized (this.a) {
            if (request.equals(this.c)) {
                this.e = RequestCoordinator.RequestState.SUCCESS;
            } else if (request.equals(this.d)) {
                this.f = RequestCoordinator.RequestState.SUCCESS;
            }
            if (this.b != null) {
                this.b.onRequestSuccess(this);
            }
        }
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public void onRequestFailed(Request request) {
        synchronized (this.a) {
            if (!request.equals(this.d)) {
                this.e = RequestCoordinator.RequestState.FAILED;
                if (this.f != RequestCoordinator.RequestState.RUNNING) {
                    this.f = RequestCoordinator.RequestState.RUNNING;
                    this.d.begin();
                }
                return;
            }
            this.f = RequestCoordinator.RequestState.FAILED;
            if (this.b != null) {
                this.b.onRequestFailed(this);
            }
        }
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public RequestCoordinator getRoot() {
        RequestCoordinator root;
        synchronized (this.a) {
            root = this.b != null ? this.b.getRoot() : this;
        }
        return root;
    }
}
