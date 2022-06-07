package com.milink.kit;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.milink.base.utils.Logger;
import com.milink.kit.MiLinkContext;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import miuix.arch.component.AppComponentManager;
import miuix.arch.component.ComponentException;

/* compiled from: MiLinkContextImpl.java */
/* loaded from: classes2.dex */
final class a extends MiLinkContext {
    private final Map<String, Object> a = new ConcurrentHashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull MiLinkContext.Installer installer) {
        super(installer);
        b();
    }

    @Override // com.milink.kit.MiLinkContext
    public void register(@NonNull String str, @NonNull Object obj) {
        synchronized (this.a) {
            if (!this.a.containsKey(str)) {
                this.a.put(str, Objects.requireNonNull(obj));
            }
        }
    }

    @Override // com.milink.kit.MiLinkContext
    public void unregister(@NonNull String str) {
        if (!a(str)) {
            synchronized (this.a) {
                this.a.remove(Objects.requireNonNull(str));
            }
        }
    }

    @Override // com.milink.kit.MiLinkContext
    @Nullable
    public <T> T get(@NonNull String str) {
        T t;
        synchronized (this.a) {
            t = (T) this.a.get(Objects.requireNonNull(str));
            if (t == null && a(str)) {
                try {
                    c().callMethod(str, MiLinkContext.REGISTER_MANAGER, null);
                    t = (T) this.a.get(str);
                    if (t == null) {
                        throw new IllegalStateException("AppComponentManager(milink.kit) not found manager after register");
                    }
                } catch (ComponentException e) {
                    throw new IllegalStateException("get manager fail", e);
                }
            }
        }
        return t;
    }

    @Override // com.milink.kit.MiLinkContext
    @Nullable
    public <T> T get(@NonNull Class<? extends T> cls) {
        Objects.requireNonNull(cls);
        ManagerName managerName = (ManagerName) cls.getAnnotation(ManagerName.class);
        if (managerName == null) {
            return null;
        }
        String value = managerName.value();
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        return (T) get(value);
    }

    private void b() {
        AppComponentManager appComponentManager = AppComponentManager.get("milink.kit");
        if (appComponentManager == null) {
            throw new NullPointerException("ComponentManager(milink.kit) not found.");
        } else if (!appComponentManager.hasActiveComponent("native_core")) {
            Logger.i("MiLinkContext", "native core not support.", new Object[0]);
        } else {
            try {
                appComponentManager.callMethod("native_core", "install", null);
            } catch (ComponentException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private boolean a(@NonNull String str) {
        return c().hasActiveComponent(str);
    }

    @NonNull
    private AppComponentManager c() {
        AppComponentManager appComponentManager = AppComponentManager.get("milink.kit");
        if (appComponentManager != null) {
            return appComponentManager;
        }
        throw new IllegalStateException("AppComponentManager(milink.kit) not install");
    }
}
