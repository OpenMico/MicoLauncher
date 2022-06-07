package com.bumptech.glide.manager;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.manager.ConnectivityMonitor;

/* compiled from: DefaultConnectivityMonitor.java */
/* loaded from: classes.dex */
final class c implements ConnectivityMonitor {
    final ConnectivityMonitor.ConnectivityListener a;
    private final Context b;

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onDestroy() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(@NonNull Context context, @NonNull ConnectivityMonitor.ConnectivityListener connectivityListener) {
        this.b = context.getApplicationContext();
        this.a = connectivityListener;
    }

    private void a() {
        j.a(this.b).a(this.a);
    }

    private void b() {
        j.a(this.b).b(this.a);
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
        a();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
        b();
    }
}
