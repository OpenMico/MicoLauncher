package com.bumptech.glide.manager;

import androidx.annotation.NonNull;
import com.bumptech.glide.util.Util;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ActivityFragmentLifecycle.java */
/* loaded from: classes.dex */
public class a implements Lifecycle {
    private final Set<LifecycleListener> a = Collections.newSetFromMap(new WeakHashMap());
    private boolean b;
    private boolean c;

    @Override // com.bumptech.glide.manager.Lifecycle
    public void addListener(@NonNull LifecycleListener lifecycleListener) {
        this.a.add(lifecycleListener);
        if (this.c) {
            lifecycleListener.onDestroy();
        } else if (this.b) {
            lifecycleListener.onStart();
        } else {
            lifecycleListener.onStop();
        }
    }

    @Override // com.bumptech.glide.manager.Lifecycle
    public void removeListener(@NonNull LifecycleListener lifecycleListener) {
        this.a.remove(lifecycleListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.b = true;
        for (LifecycleListener lifecycleListener : Util.getSnapshot(this.a)) {
            lifecycleListener.onStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        this.b = false;
        for (LifecycleListener lifecycleListener : Util.getSnapshot(this.a)) {
            lifecycleListener.onStop();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c() {
        this.c = true;
        for (LifecycleListener lifecycleListener : Util.getSnapshot(this.a)) {
            lifecycleListener.onDestroy();
        }
    }
}
