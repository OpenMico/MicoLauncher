package com.bumptech.glide.request;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.RequestCoordinator;

/* loaded from: classes.dex */
public class ThumbnailRequestCoordinator implements Request, RequestCoordinator {
    @Nullable
    private final RequestCoordinator a;
    private final Object b;
    private volatile Request c;
    private volatile Request d;
    @GuardedBy("requestLock")
    private RequestCoordinator.RequestState e = RequestCoordinator.RequestState.CLEARED;
    @GuardedBy("requestLock")
    private RequestCoordinator.RequestState f = RequestCoordinator.RequestState.CLEARED;
    @GuardedBy("requestLock")
    private boolean g;

    public ThumbnailRequestCoordinator(Object obj, @Nullable RequestCoordinator requestCoordinator) {
        this.b = obj;
        this.a = requestCoordinator;
    }

    public void setRequests(Request request, Request request2) {
        this.c = request;
        this.d = request2;
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canSetImage(Request request) {
        boolean z;
        synchronized (this.b) {
            z = a() && (request.equals(this.c) || this.e != RequestCoordinator.RequestState.SUCCESS);
        }
        return z;
    }

    @GuardedBy("requestLock")
    private boolean a() {
        RequestCoordinator requestCoordinator = this.a;
        return requestCoordinator == null || requestCoordinator.canSetImage(this);
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canNotifyStatusChanged(Request request) {
        boolean z;
        synchronized (this.b) {
            z = c() && request.equals(this.c) && !isAnyResourceSet();
        }
        return z;
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public boolean canNotifyCleared(Request request) {
        boolean z;
        synchronized (this.b) {
            z = b() && request.equals(this.c) && this.e != RequestCoordinator.RequestState.PAUSED;
        }
        return z;
    }

    @GuardedBy("requestLock")
    private boolean b() {
        RequestCoordinator requestCoordinator = this.a;
        return requestCoordinator == null || requestCoordinator.canNotifyCleared(this);
    }

    @GuardedBy("requestLock")
    private boolean c() {
        RequestCoordinator requestCoordinator = this.a;
        return requestCoordinator == null || requestCoordinator.canNotifyStatusChanged(this);
    }

    @Override // com.bumptech.glide.request.Request, com.bumptech.glide.request.RequestCoordinator
    public boolean isAnyResourceSet() {
        boolean z;
        synchronized (this.b) {
            if (!this.d.isAnyResourceSet() && !this.c.isAnyResourceSet()) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public void onRequestSuccess(Request request) {
        synchronized (this.b) {
            if (request.equals(this.d)) {
                this.f = RequestCoordinator.RequestState.SUCCESS;
                return;
            }
            this.e = RequestCoordinator.RequestState.SUCCESS;
            if (this.a != null) {
                this.a.onRequestSuccess(this);
            }
            if (!this.f.a()) {
                this.d.clear();
            }
        }
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public void onRequestFailed(Request request) {
        synchronized (this.b) {
            if (!request.equals(this.c)) {
                this.f = RequestCoordinator.RequestState.FAILED;
                return;
            }
            this.e = RequestCoordinator.RequestState.FAILED;
            if (this.a != null) {
                this.a.onRequestFailed(this);
            }
        }
    }

    @Override // com.bumptech.glide.request.RequestCoordinator
    public RequestCoordinator getRoot() {
        RequestCoordinator root;
        synchronized (this.b) {
            root = this.a != null ? this.a.getRoot() : this;
        }
        return root;
    }

    @Override // com.bumptech.glide.request.Request
    public void begin() {
        synchronized (this.b) {
            this.g = true;
            if (!(this.e == RequestCoordinator.RequestState.SUCCESS || this.f == RequestCoordinator.RequestState.RUNNING)) {
                this.f = RequestCoordinator.RequestState.RUNNING;
                this.d.begin();
            }
            if (this.g && this.e != RequestCoordinator.RequestState.RUNNING) {
                this.e = RequestCoordinator.RequestState.RUNNING;
                this.c.begin();
            }
            this.g = false;
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void clear() {
        synchronized (this.b) {
            this.g = false;
            this.e = RequestCoordinator.RequestState.CLEARED;
            this.f = RequestCoordinator.RequestState.CLEARED;
            this.d.clear();
            this.c.clear();
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void pause() {
        synchronized (this.b) {
            if (!this.f.a()) {
                this.f = RequestCoordinator.RequestState.PAUSED;
                this.d.pause();
            }
            if (!this.e.a()) {
                this.e = RequestCoordinator.RequestState.PAUSED;
                this.c.pause();
            }
        }
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isRunning() {
        boolean z;
        synchronized (this.b) {
            z = this.e == RequestCoordinator.RequestState.RUNNING;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isComplete() {
        boolean z;
        synchronized (this.b) {
            z = this.e == RequestCoordinator.RequestState.SUCCESS;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isCleared() {
        boolean z;
        synchronized (this.b) {
            z = this.e == RequestCoordinator.RequestState.CLEARED;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isEquivalentTo(Request request) {
        if (!(request instanceof ThumbnailRequestCoordinator)) {
            return false;
        }
        ThumbnailRequestCoordinator thumbnailRequestCoordinator = (ThumbnailRequestCoordinator) request;
        if (this.c == null) {
            if (thumbnailRequestCoordinator.c != null) {
                return false;
            }
        } else if (!this.c.isEquivalentTo(thumbnailRequestCoordinator.c)) {
            return false;
        }
        if (this.d == null) {
            if (thumbnailRequestCoordinator.d != null) {
                return false;
            }
        } else if (!this.d.isEquivalentTo(thumbnailRequestCoordinator.d)) {
            return false;
        }
        return true;
    }
}
