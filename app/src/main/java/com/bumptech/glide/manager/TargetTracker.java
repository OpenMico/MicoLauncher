package com.bumptech.glide.manager;

import androidx.annotation.NonNull;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public final class TargetTracker implements LifecycleListener {
    private final Set<Target<?>> a = Collections.newSetFromMap(new WeakHashMap());

    public void track(@NonNull Target<?> target) {
        this.a.add(target);
    }

    public void untrack(@NonNull Target<?> target) {
        this.a.remove(target);
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
        for (Target target : Util.getSnapshot(this.a)) {
            target.onStart();
        }
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
        for (Target target : Util.getSnapshot(this.a)) {
            target.onStop();
        }
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onDestroy() {
        for (Target target : Util.getSnapshot(this.a)) {
            target.onDestroy();
        }
    }

    @NonNull
    public List<Target<?>> getAll() {
        return Util.getSnapshot(this.a);
    }

    public void clear() {
        this.a.clear();
    }
}
