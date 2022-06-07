package androidx.loader.app;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.core.util.DebugUtils;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class LoaderManagerImpl extends LoaderManager {
    static boolean a = false;
    @NonNull
    private final LifecycleOwner b;
    @NonNull
    private final b c;

    /* loaded from: classes.dex */
    public static class LoaderInfo<D> extends MutableLiveData<D> implements Loader.OnLoadCompleteListener<D> {
        private final int a;
        @Nullable
        private final Bundle b;
        @NonNull
        private final Loader<D> c;
        private LifecycleOwner d;
        private a<D> e;
        private Loader<D> f;

        LoaderInfo(int i, @Nullable Bundle bundle, @NonNull Loader<D> loader, @Nullable Loader<D> loader2) {
            this.a = i;
            this.b = bundle;
            this.c = loader;
            this.f = loader2;
            this.c.registerListener(i, this);
        }

        @NonNull
        Loader<D> a() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.lifecycle.LiveData
        public void onActive() {
            if (LoaderManagerImpl.a) {
                Log.v("LoaderManager", "  Starting: " + this);
            }
            this.c.startLoading();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.lifecycle.LiveData
        public void onInactive() {
            if (LoaderManagerImpl.a) {
                Log.v("LoaderManager", "  Stopping: " + this);
            }
            this.c.stopLoading();
        }

        @NonNull
        @MainThread
        Loader<D> a(@NonNull LifecycleOwner lifecycleOwner, @NonNull LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
            a<D> aVar = new a<>(this.c, loaderCallbacks);
            observe(lifecycleOwner, aVar);
            a<D> aVar2 = this.e;
            if (aVar2 != null) {
                removeObserver(aVar2);
            }
            this.d = lifecycleOwner;
            this.e = aVar;
            return this.c;
        }

        void b() {
            LifecycleOwner lifecycleOwner = this.d;
            a<D> aVar = this.e;
            if (lifecycleOwner != null && aVar != null) {
                super.removeObserver(aVar);
                observe(lifecycleOwner, aVar);
            }
        }

        boolean c() {
            a<D> aVar;
            return hasActiveObservers() && (aVar = this.e) != null && !aVar.a();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.lifecycle.LiveData
        public void removeObserver(@NonNull Observer<? super D> observer) {
            super.removeObserver(observer);
            this.d = null;
            this.e = null;
        }

        @MainThread
        Loader<D> a(boolean z) {
            if (LoaderManagerImpl.a) {
                Log.v("LoaderManager", "  Destroying: " + this);
            }
            this.c.cancelLoad();
            this.c.abandon();
            a<D> aVar = this.e;
            if (aVar != null) {
                removeObserver(aVar);
                if (z) {
                    aVar.b();
                }
            }
            this.c.unregisterListener(this);
            if ((aVar == null || aVar.a()) && !z) {
                return this.c;
            }
            this.c.reset();
            return this.f;
        }

        @Override // androidx.loader.content.Loader.OnLoadCompleteListener
        public void onLoadComplete(@NonNull Loader<D> loader, @Nullable D d) {
            if (LoaderManagerImpl.a) {
                Log.v("LoaderManager", "onLoadComplete: " + this);
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                setValue(d);
                return;
            }
            if (LoaderManagerImpl.a) {
                Log.w("LoaderManager", "onLoadComplete was incorrectly called on a background thread");
            }
            postValue(d);
        }

        @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
        public void setValue(D d) {
            super.setValue(d);
            Loader<D> loader = this.f;
            if (loader != null) {
                loader.reset();
                this.f = null;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" #");
            sb.append(this.a);
            sb.append(" : ");
            DebugUtils.buildShortClassTag(this.c, sb);
            sb.append("}}");
            return sb.toString();
        }

        public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.print(str);
            printWriter.print("mId=");
            printWriter.print(this.a);
            printWriter.print(" mArgs=");
            printWriter.println(this.b);
            printWriter.print(str);
            printWriter.print("mLoader=");
            printWriter.println(this.c);
            Loader<D> loader = this.c;
            loader.dump(str + "  ", fileDescriptor, printWriter, strArr);
            if (this.e != null) {
                printWriter.print(str);
                printWriter.print("mCallbacks=");
                printWriter.println(this.e);
                a<D> aVar = this.e;
                aVar.a(str + "  ", printWriter);
            }
            printWriter.print(str);
            printWriter.print("mData=");
            printWriter.println(a().dataToString(getValue()));
            printWriter.print(str);
            printWriter.print("mStarted=");
            printWriter.println(hasActiveObservers());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a<D> implements Observer<D> {
        @NonNull
        private final Loader<D> a;
        @NonNull
        private final LoaderManager.LoaderCallbacks<D> b;
        private boolean c = false;

        a(@NonNull Loader<D> loader, @NonNull LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
            this.a = loader;
            this.b = loaderCallbacks;
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(@Nullable D d) {
            if (LoaderManagerImpl.a) {
                Log.v("LoaderManager", "  onLoadFinished in " + this.a + ": " + this.a.dataToString(d));
            }
            this.b.onLoadFinished(this.a, d);
            this.c = true;
        }

        boolean a() {
            return this.c;
        }

        @MainThread
        void b() {
            if (this.c) {
                if (LoaderManagerImpl.a) {
                    Log.v("LoaderManager", "  Resetting: " + this.a);
                }
                this.b.onLoaderReset(this.a);
            }
        }

        public String toString() {
            return this.b.toString();
        }

        public void a(String str, PrintWriter printWriter) {
            printWriter.print(str);
            printWriter.print("mDeliveredData=");
            printWriter.println(this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class b extends ViewModel {
        private static final ViewModelProvider.Factory a = new ViewModelProvider.Factory() { // from class: androidx.loader.app.LoaderManagerImpl.b.1
            @Override // androidx.lifecycle.ViewModelProvider.Factory
            @NonNull
            public <T extends ViewModel> T create(@NonNull Class<T> cls) {
                return new b();
            }
        };
        private SparseArrayCompat<LoaderInfo> b = new SparseArrayCompat<>();
        private boolean c = false;

        b() {
        }

        @NonNull
        static b a(ViewModelStore viewModelStore) {
            return (b) new ViewModelProvider(viewModelStore, a).get(b.class);
        }

        void a() {
            this.c = true;
        }

        boolean b() {
            return this.c;
        }

        void c() {
            this.c = false;
        }

        void a(int i, @NonNull LoaderInfo loaderInfo) {
            this.b.put(i, loaderInfo);
        }

        <D> LoaderInfo<D> a(int i) {
            return this.b.get(i);
        }

        void b(int i) {
            this.b.remove(i);
        }

        boolean d() {
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                if (this.b.valueAt(i).c()) {
                    return true;
                }
            }
            return false;
        }

        void e() {
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                this.b.valueAt(i).b();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.lifecycle.ViewModel
        public void onCleared() {
            super.onCleared();
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                this.b.valueAt(i).a(true);
            }
            this.b.clear();
        }

        public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (this.b.size() > 0) {
                printWriter.print(str);
                printWriter.println("Loaders:");
                String str2 = str + "    ";
                for (int i = 0; i < this.b.size(); i++) {
                    LoaderInfo valueAt = this.b.valueAt(i);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(this.b.keyAt(i));
                    printWriter.print(": ");
                    printWriter.println(valueAt.toString());
                    valueAt.dump(str2, fileDescriptor, printWriter, strArr);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LoaderManagerImpl(@NonNull LifecycleOwner lifecycleOwner, @NonNull ViewModelStore viewModelStore) {
        this.b = lifecycleOwner;
        this.c = b.a(viewModelStore);
    }

    /* JADX WARN: Finally extract failed */
    @NonNull
    @MainThread
    private <D> Loader<D> a(int i, @Nullable Bundle bundle, @NonNull LoaderManager.LoaderCallbacks<D> loaderCallbacks, @Nullable Loader<D> loader) {
        try {
            this.c.a();
            Loader<D> onCreateLoader = loaderCallbacks.onCreateLoader(i, bundle);
            if (onCreateLoader != null) {
                if (onCreateLoader.getClass().isMemberClass() && !Modifier.isStatic(onCreateLoader.getClass().getModifiers())) {
                    throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + onCreateLoader);
                }
                LoaderInfo loaderInfo = new LoaderInfo(i, bundle, onCreateLoader, loader);
                if (a) {
                    Log.v("LoaderManager", "  Created new loader " + loaderInfo);
                }
                this.c.a(i, loaderInfo);
                this.c.c();
                return loaderInfo.a(this.b, loaderCallbacks);
            }
            throw new IllegalArgumentException("Object returned from onCreateLoader must not be null");
        } catch (Throwable th) {
            this.c.c();
            throw th;
        }
    }

    @Override // androidx.loader.app.LoaderManager
    @NonNull
    @MainThread
    public <D> Loader<D> initLoader(int i, @Nullable Bundle bundle, @NonNull LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        if (this.c.b()) {
            throw new IllegalStateException("Called while creating a loader");
        } else if (Looper.getMainLooper() == Looper.myLooper()) {
            LoaderInfo<D> a2 = this.c.a(i);
            if (a) {
                Log.v("LoaderManager", "initLoader in " + this + ": args=" + bundle);
            }
            if (a2 == null) {
                return a(i, bundle, loaderCallbacks, null);
            }
            if (a) {
                Log.v("LoaderManager", "  Re-using existing loader " + a2);
            }
            return a2.a(this.b, loaderCallbacks);
        } else {
            throw new IllegalStateException("initLoader must be called on the main thread");
        }
    }

    @Override // androidx.loader.app.LoaderManager
    @NonNull
    @MainThread
    public <D> Loader<D> restartLoader(int i, @Nullable Bundle bundle, @NonNull LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        if (this.c.b()) {
            throw new IllegalStateException("Called while creating a loader");
        } else if (Looper.getMainLooper() == Looper.myLooper()) {
            if (a) {
                Log.v("LoaderManager", "restartLoader in " + this + ": args=" + bundle);
            }
            LoaderInfo<D> a2 = this.c.a(i);
            Loader<D> loader = null;
            if (a2 != null) {
                loader = a2.a(false);
            }
            return a(i, bundle, loaderCallbacks, loader);
        } else {
            throw new IllegalStateException("restartLoader must be called on the main thread");
        }
    }

    @Override // androidx.loader.app.LoaderManager
    @MainThread
    public void destroyLoader(int i) {
        if (this.c.b()) {
            throw new IllegalStateException("Called while creating a loader");
        } else if (Looper.getMainLooper() == Looper.myLooper()) {
            if (a) {
                Log.v("LoaderManager", "destroyLoader in " + this + " of " + i);
            }
            LoaderInfo a2 = this.c.a(i);
            if (a2 != null) {
                a2.a(true);
                this.c.b(i);
            }
        } else {
            throw new IllegalStateException("destroyLoader must be called on the main thread");
        }
    }

    @Override // androidx.loader.app.LoaderManager
    @Nullable
    public <D> Loader<D> getLoader(int i) {
        if (!this.c.b()) {
            LoaderInfo<D> a2 = this.c.a(i);
            if (a2 != null) {
                return a2.a();
            }
            return null;
        }
        throw new IllegalStateException("Called while creating a loader");
    }

    @Override // androidx.loader.app.LoaderManager
    public void markForRedelivery() {
        this.c.e();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        DebugUtils.buildShortClassTag(this.b, sb);
        sb.append("}}");
        return sb.toString();
    }

    @Override // androidx.loader.app.LoaderManager
    @Deprecated
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.c.a(str, fileDescriptor, printWriter, strArr);
    }

    @Override // androidx.loader.app.LoaderManager
    public boolean hasRunningLoaders() {
        return this.c.d();
    }
}
