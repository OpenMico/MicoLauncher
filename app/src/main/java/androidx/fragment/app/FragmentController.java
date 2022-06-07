package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;
import androidx.core.util.Preconditions;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class FragmentController {
    private final FragmentHostCallback<?> a;

    @Deprecated
    public void dispatchReallyStop() {
    }

    @Deprecated
    public void doLoaderDestroy() {
    }

    @Deprecated
    public void doLoaderRetain() {
    }

    @Deprecated
    public void doLoaderStart() {
    }

    @Deprecated
    public void doLoaderStop(boolean z) {
    }

    @Deprecated
    public void dumpLoaders(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
    }

    @Deprecated
    public void reportLoaderStart() {
    }

    @Deprecated
    public void restoreLoaderNonConfig(@SuppressLint({"UnknownNullness"}) SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
    }

    @Nullable
    @Deprecated
    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        return null;
    }

    @NonNull
    public static FragmentController createController(@NonNull FragmentHostCallback<?> fragmentHostCallback) {
        return new FragmentController((FragmentHostCallback) Preconditions.checkNotNull(fragmentHostCallback, "callbacks == null"));
    }

    private FragmentController(FragmentHostCallback<?> fragmentHostCallback) {
        this.a = fragmentHostCallback;
    }

    @NonNull
    public FragmentManager getSupportFragmentManager() {
        return this.a.b;
    }

    @SuppressLint({"UnknownNullness"})
    @Deprecated
    public LoaderManager getSupportLoaderManager() {
        throw new UnsupportedOperationException("Loaders are managed separately from FragmentController, use LoaderManager.getInstance() to obtain a LoaderManager.");
    }

    @Nullable
    public Fragment findFragmentByWho(@NonNull String str) {
        return this.a.b.a(str);
    }

    public int getActiveFragmentsCount() {
        return this.a.b.c();
    }

    @NonNull
    public List<Fragment> getActiveFragments(@SuppressLint({"UnknownNullness"}) List<Fragment> list) {
        return this.a.b.b();
    }

    public void attachHost(@Nullable Fragment fragment) {
        FragmentManager fragmentManager = this.a.b;
        FragmentHostCallback<?> fragmentHostCallback = this.a;
        fragmentManager.a(fragmentHostCallback, fragmentHostCallback, fragment);
    }

    @Nullable
    public View onCreateView(@Nullable View view, @NonNull String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        return this.a.b.B().onCreateView(view, str, context, attributeSet);
    }

    public void noteStateNotSaved() {
        this.a.b.l();
    }

    @Nullable
    public Parcelable saveAllState() {
        return this.a.b.g();
    }

    @Deprecated
    public void restoreAllState(@Nullable Parcelable parcelable, @Nullable List<Fragment> list) {
        this.a.b.a(parcelable, new FragmentManagerNonConfig(list, null, null));
    }

    @Deprecated
    public void restoreAllState(@Nullable Parcelable parcelable, @Nullable FragmentManagerNonConfig fragmentManagerNonConfig) {
        this.a.b.a(parcelable, fragmentManagerNonConfig);
    }

    public void restoreSaveState(@Nullable Parcelable parcelable) {
        FragmentHostCallback<?> fragmentHostCallback = this.a;
        if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            fragmentHostCallback.b.a(parcelable);
            return;
        }
        throw new IllegalStateException("Your FragmentHostCallback must implement ViewModelStoreOwner to call restoreSaveState(). Call restoreAllState()  if you're still using retainNestedNonConfig().");
    }

    @Nullable
    @Deprecated
    public List<Fragment> retainNonConfig() {
        FragmentManagerNonConfig f = this.a.b.f();
        if (f == null || f.a() == null) {
            return null;
        }
        return new ArrayList(f.a());
    }

    @Nullable
    @Deprecated
    public FragmentManagerNonConfig retainNestedNonConfig() {
        return this.a.b.f();
    }

    public void dispatchCreate() {
        this.a.b.n();
    }

    public void dispatchActivityCreated() {
        this.a.b.p();
    }

    public void dispatchStart() {
        this.a.b.q();
    }

    public void dispatchResume() {
        this.a.b.r();
    }

    public void dispatchPause() {
        this.a.b.s();
    }

    public void dispatchStop() {
        this.a.b.t();
    }

    public void dispatchDestroyView() {
        this.a.b.u();
    }

    public void dispatchDestroy() {
        this.a.b.v();
    }

    public void dispatchMultiWindowModeChanged(boolean z) {
        this.a.b.b(z);
    }

    public void dispatchPictureInPictureModeChanged(boolean z) {
        this.a.b.c(z);
    }

    public void dispatchConfigurationChanged(@NonNull Configuration configuration) {
        this.a.b.a(configuration);
    }

    public void dispatchLowMemory() {
        this.a.b.w();
    }

    public boolean dispatchCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        return this.a.b.a(menu, menuInflater);
    }

    public boolean dispatchPrepareOptionsMenu(@NonNull Menu menu) {
        return this.a.b.a(menu);
    }

    public boolean dispatchOptionsItemSelected(@NonNull MenuItem menuItem) {
        return this.a.b.a(menuItem);
    }

    public boolean dispatchContextItemSelected(@NonNull MenuItem menuItem) {
        return this.a.b.b(menuItem);
    }

    public void dispatchOptionsMenuClosed(@NonNull Menu menu) {
        this.a.b.b(menu);
    }

    public boolean execPendingActions() {
        return this.a.b.a(true);
    }
}
