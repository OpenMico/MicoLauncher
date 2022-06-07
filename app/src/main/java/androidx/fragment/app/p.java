package androidx.fragment.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FragmentViewLifecycleOwner.java */
/* loaded from: classes.dex */
public class p implements HasDefaultViewModelProviderFactory, ViewModelStoreOwner, SavedStateRegistryOwner {
    private final Fragment a;
    private final ViewModelStore b;
    private ViewModelProvider.Factory c;
    private LifecycleRegistry d = null;
    private SavedStateRegistryController e = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    public p(@NonNull Fragment fragment, @NonNull ViewModelStore viewModelStore) {
        this.a = fragment;
        this.b = viewModelStore;
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    @NonNull
    public ViewModelStore getViewModelStore() {
        a();
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        if (this.d == null) {
            this.d = new LifecycleRegistry(this);
            this.e = SavedStateRegistryController.create(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return this.d != null;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    public Lifecycle getLifecycle() {
        a();
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull Lifecycle.State state) {
        this.d.setCurrentState(state);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull Lifecycle.Event event) {
        this.d.handleLifecycleEvent(event);
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    @NonNull
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        ViewModelProvider.Factory defaultViewModelProviderFactory = this.a.getDefaultViewModelProviderFactory();
        if (!defaultViewModelProviderFactory.equals(this.a.mDefaultFactory)) {
            this.c = defaultViewModelProviderFactory;
            return defaultViewModelProviderFactory;
        }
        if (this.c == null) {
            Application application = null;
            Context applicationContext = this.a.requireContext().getApplicationContext();
            while (true) {
                if (!(applicationContext instanceof ContextWrapper)) {
                    break;
                } else if (applicationContext instanceof Application) {
                    application = (Application) applicationContext;
                    break;
                } else {
                    applicationContext = ((ContextWrapper) applicationContext).getBaseContext();
                }
            }
            this.c = new SavedStateViewModelFactory(application, this, this.a.getArguments());
        }
        return this.c;
    }

    @Override // androidx.savedstate.SavedStateRegistryOwner
    @NonNull
    public SavedStateRegistry getSavedStateRegistry() {
        a();
        return this.e.getSavedStateRegistry();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable Bundle bundle) {
        this.e.performRestore(bundle);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@NonNull Bundle bundle) {
        this.e.performSave(bundle);
    }
}
