package com.bumptech.glide.manager;

import androidx.annotation.NonNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ApplicationLifecycle.java */
/* loaded from: classes.dex */
public class b implements Lifecycle {
    @Override // com.bumptech.glide.manager.Lifecycle
    public void removeListener(@NonNull LifecycleListener lifecycleListener) {
    }

    @Override // com.bumptech.glide.manager.Lifecycle
    public void addListener(@NonNull LifecycleListener lifecycleListener) {
        lifecycleListener.onStart();
    }
}
