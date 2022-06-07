package com.milink.kit.lock;

import com.milink.kit.MiLinkContext;
import miuix.arch.component.AppComponent;
import miuix.arch.component.ComponentPort;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LockProviderComponent.java */
@AppComponent(name = "lock_provider")
/* loaded from: classes2.dex */
public class b {
    /* JADX INFO: Access modifiers changed from: package-private */
    @ComponentPort(name = MiLinkContext.REGISTER_MANAGER)
    public void a() {
        MiLinkContext instance = MiLinkContext.getInstance();
        instance.register("lock_provider", new c(instance.getCallbackExecutor()));
    }
}
