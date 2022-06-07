package com.milink.kit;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.milink.kit.MiLinkContext;
import com.milink.kit.lock.b;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import miuix.arch.component.AppComponentDelegate;
import miuix.arch.component.ComponentConfiguration;
import miuix.arch.component.Task;

/* loaded from: classes2.dex */
public final class MiLinkKitComponentConfig$ComponentConfiguration implements ComponentConfiguration {
    @Override // miuix.arch.component.ComponentConfiguration
    public final String getComponentManagerDomain() {
        return "milink.kit";
    }

    @Override // miuix.arch.component.ComponentConfiguration
    public final Map<Integer, List<Task>> createMainComponentInitMap() {
        return new TreeMap();
    }

    @Override // miuix.arch.component.ComponentConfiguration
    public final Map<Integer, Map<Integer, List<Task>>> createBackgroundComponentInitMap() {
        return new TreeMap();
    }

    @Override // miuix.arch.component.ComponentConfiguration
    public final Map<String, AppComponentDelegate<?>> createAppComponentDelegates() {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("lock_provider", new AppComponentDelegate<b>() { // from class: com.milink.kit.lock.LockProviderComponent$AppComponent
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // miuix.arch.component.AppComponentDelegate
            @NonNull
            public final b createAppComponent() {
                return new b();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // miuix.arch.component.AppComponentDelegate
            public final void performTask(Object obj, int i) throws Exception {
                throw new IllegalArgumentException("LockProviderComponent:" + i);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // miuix.arch.component.AppComponentDelegate
            @NonNull
            public final Object callTask(Object obj, String str, Object obj2) throws Exception {
                char c;
                int hashCode = str.hashCode();
                if (hashCode != -1397124591) {
                    if (hashCode == 51 && str.equals("3")) {
                        c = 1;
                    }
                    c = 65535;
                } else {
                    if (str.equals(MiLinkContext.REGISTER_MANAGER)) {
                        c = 0;
                    }
                    c = 65535;
                }
                switch (c) {
                    case 0:
                    case 1:
                        getSingleton().a();
                        return Bundle.EMPTY;
                    default:
                        throw new IllegalArgumentException("LockProviderComponent:" + str);
                }
            }
        });
        return concurrentHashMap;
    }

    @Override // miuix.arch.component.ComponentConfiguration
    public final Map<String, Task> createOnEventTaskMap() {
        return new TreeMap();
    }
}
