package androidx.savedstate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.Recreator;
import java.util.Map;

@SuppressLint({"RestrictedApi"})
/* loaded from: classes.dex */
public final class SavedStateRegistry {
    @Nullable
    private Bundle c;
    private boolean d;
    private Recreator.a e;
    private SafeIterableMap<String, SavedStateProvider> b = new SafeIterableMap<>();
    boolean a = true;

    /* loaded from: classes.dex */
    public interface AutoRecreated {
        void onRecreated(@NonNull SavedStateRegistryOwner savedStateRegistryOwner);
    }

    /* loaded from: classes.dex */
    public interface SavedStateProvider {
        @NonNull
        Bundle saveState();
    }

    @Nullable
    @MainThread
    public Bundle consumeRestoredStateForKey(@NonNull String str) {
        if (this.d) {
            Bundle bundle = this.c;
            if (bundle == null) {
                return null;
            }
            Bundle bundle2 = bundle.getBundle(str);
            this.c.remove(str);
            if (this.c.isEmpty()) {
                this.c = null;
            }
            return bundle2;
        }
        throw new IllegalStateException("You can consumeRestoredStateForKey only after super.onCreate of corresponding component");
    }

    @MainThread
    public void registerSavedStateProvider(@NonNull String str, @NonNull SavedStateProvider savedStateProvider) {
        if (this.b.putIfAbsent(str, savedStateProvider) != null) {
            throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
        }
    }

    @MainThread
    public void unregisterSavedStateProvider(@NonNull String str) {
        this.b.remove(str);
    }

    @MainThread
    public boolean isRestored() {
        return this.d;
    }

    @MainThread
    public void runOnNextRecreation(@NonNull Class<? extends AutoRecreated> cls) {
        if (this.a) {
            if (this.e == null) {
                this.e = new Recreator.a(this);
            }
            try {
                cls.getDeclaredConstructor(new Class[0]);
                this.e.a(cls.getName());
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("Class" + cls.getSimpleName() + " must have default constructor in order to be automatically recreated", e);
            }
        } else {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
    }

    @MainThread
    public void a(@NonNull Lifecycle lifecycle, @Nullable Bundle bundle) {
        if (!this.d) {
            if (bundle != null) {
                this.c = bundle.getBundle("androidx.lifecycle.BundlableSavedStateRegistry.key");
            }
            lifecycle.addObserver(new GenericLifecycleObserver() { // from class: androidx.savedstate.SavedStateRegistry.1
                @Override // androidx.lifecycle.LifecycleEventObserver
                public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_START) {
                        SavedStateRegistry.this.a = true;
                    } else if (event == Lifecycle.Event.ON_STOP) {
                        SavedStateRegistry.this.a = false;
                    }
                }
            });
            this.d = true;
            return;
        }
        throw new IllegalStateException("SavedStateRegistry was already restored.");
    }

    @MainThread
    public void a(@NonNull Bundle bundle) {
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = this.c;
        if (bundle3 != null) {
            bundle2.putAll(bundle3);
        }
        SafeIterableMap<String, SavedStateProvider>.IteratorWithAdditions iteratorWithAdditions = this.b.iteratorWithAdditions();
        while (iteratorWithAdditions.hasNext()) {
            Map.Entry next = iteratorWithAdditions.next();
            bundle2.putBundle((String) next.getKey(), ((SavedStateProvider) next.getValue()).saveState());
        }
        bundle.putBundle("androidx.lifecycle.BundlableSavedStateRegistry.key", bundle2);
    }
}
