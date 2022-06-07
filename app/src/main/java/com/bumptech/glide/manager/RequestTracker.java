package com.bumptech.glide.manager;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.util.Util;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class RequestTracker {
    private final Set<Request> a = Collections.newSetFromMap(new WeakHashMap());
    private final Set<Request> b = new HashSet();
    private boolean c;

    public void runRequest(@NonNull Request request) {
        this.a.add(request);
        if (!this.c) {
            request.begin();
            return;
        }
        request.clear();
        if (Log.isLoggable("RequestTracker", 2)) {
            Log.v("RequestTracker", "Paused, delaying request");
        }
        this.b.add(request);
    }

    public boolean clearAndRemove(@Nullable Request request) {
        boolean z = true;
        if (request == null) {
            return true;
        }
        boolean remove = this.a.remove(request);
        if (!this.b.remove(request) && !remove) {
            z = false;
        }
        if (z) {
            request.clear();
        }
        return z;
    }

    public boolean isPaused() {
        return this.c;
    }

    public void pauseRequests() {
        this.c = true;
        for (Request request : Util.getSnapshot(this.a)) {
            if (request.isRunning()) {
                request.pause();
                this.b.add(request);
            }
        }
    }

    public void pauseAllRequests() {
        this.c = true;
        for (Request request : Util.getSnapshot(this.a)) {
            if (request.isRunning() || request.isComplete()) {
                request.clear();
                this.b.add(request);
            }
        }
    }

    public void resumeRequests() {
        this.c = false;
        for (Request request : Util.getSnapshot(this.a)) {
            if (!request.isComplete() && !request.isRunning()) {
                request.begin();
            }
        }
        this.b.clear();
    }

    public void clearRequests() {
        for (Request request : Util.getSnapshot(this.a)) {
            clearAndRemove(request);
        }
        this.b.clear();
    }

    public void restartRequests() {
        for (Request request : Util.getSnapshot(this.a)) {
            if (!request.isComplete() && !request.isCleared()) {
                request.clear();
                if (!this.c) {
                    request.begin();
                } else {
                    this.b.add(request);
                }
            }
        }
    }

    public String toString() {
        return super.toString() + "{numRequests=" + this.a.size() + ", isPaused=" + this.c + "}";
    }
}
