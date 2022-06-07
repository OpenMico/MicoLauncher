package androidx.lifecycle;

import android.app.Application;
import androidx.annotation.MainThread;
import androidx.annotation.RestrictTo;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ViewModelProvider.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001:\u0005\u0014\u0015\u0016\u0017\u0018B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u0015\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\nJ&\u0010\u000b\u001a\u0002H\f\"\b\b\u0000\u0010\f*\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\f0\u000fH\u0097\u0002¢\u0006\u0002\u0010\u0010J.\u0010\u000b\u001a\u0002H\f\"\b\b\u0000\u0010\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\f0\u000fH\u0097\u0002¢\u0006\u0002\u0010\u0013R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Landroidx/lifecycle/ViewModelProvider;", "", "owner", "Landroidx/lifecycle/ViewModelStoreOwner;", "(Landroidx/lifecycle/ViewModelStoreOwner;)V", "factory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "(Landroidx/lifecycle/ViewModelStoreOwner;Landroidx/lifecycle/ViewModelProvider$Factory;)V", "store", "Landroidx/lifecycle/ViewModelStore;", "(Landroidx/lifecycle/ViewModelStore;Landroidx/lifecycle/ViewModelProvider$Factory;)V", BluetoothConstants.GET, ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "key", "", "(Ljava/lang/String;Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "AndroidViewModelFactory", "Factory", "KeyedFactory", "NewInstanceFactory", "OnRequeryFactory", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public class ViewModelProvider {
    @NotNull
    private final Factory factory;
    @NotNull
    private final ViewModelStore store;

    /* compiled from: ViewModelProvider.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J%\u0010\u0002\u001a\u0002H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0006H&¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$Factory;", "", "create", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface Factory {
        @NotNull
        <T extends ViewModel> T create(@NotNull Class<T> cls);
    }

    /* compiled from: ViewModelProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$OnRequeryFactory;", "", "()V", "onRequery", "", "viewModel", "Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public static class OnRequeryFactory {
        public void onRequery(@NotNull ViewModel viewModel) {
            Intrinsics.checkNotNullParameter(viewModel, "viewModel");
        }
    }

    public ViewModelProvider(@NotNull ViewModelStore store, @NotNull Factory factory) {
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.store = store;
        this.factory = factory;
    }

    /* compiled from: ViewModelProvider.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b'\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J%\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\bH\u0016¢\u0006\u0002\u0010\tJ-\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\bH&¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$KeyedFactory;", "Landroidx/lifecycle/ViewModelProvider$OnRequeryFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "()V", "create", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "key", "", "(Ljava/lang/String;Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public static abstract class KeyedFactory extends OnRequeryFactory implements Factory {
        @NotNull
        public abstract <T extends ViewModel> T create(@NotNull String str, @NotNull Class<T> cls);

        @NotNull
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            throw new UnsupportedOperationException("create(String, Class<?>) must be called on implementations of KeyedFactory");
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ViewModelProvider(@org.jetbrains.annotations.NotNull androidx.lifecycle.ViewModelStoreOwner r3) {
        /*
            r2 = this;
            java.lang.String r0 = "owner"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            androidx.lifecycle.ViewModelStore r0 = r3.getViewModelStore()
            java.lang.String r1 = "owner.viewModelStore"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            androidx.lifecycle.ViewModelProvider$AndroidViewModelFactory$Companion r1 = androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion
            androidx.lifecycle.ViewModelProvider$Factory r3 = r1.defaultFactory$lifecycle_viewmodel_release(r3)
            r2.<init>(r0, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.ViewModelProvider.<init>(androidx.lifecycle.ViewModelStoreOwner):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ViewModelProvider(@org.jetbrains.annotations.NotNull androidx.lifecycle.ViewModelStoreOwner r2, @org.jetbrains.annotations.NotNull androidx.lifecycle.ViewModelProvider.Factory r3) {
        /*
            r1 = this;
            java.lang.String r0 = "owner"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r0)
            java.lang.String r0 = "factory"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            androidx.lifecycle.ViewModelStore r2 = r2.getViewModelStore()
            java.lang.String r0 = "owner.viewModelStore"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r0)
            r1.<init>(r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.ViewModelProvider.<init>(androidx.lifecycle.ViewModelStoreOwner, androidx.lifecycle.ViewModelProvider$Factory):void");
    }

    @MainThread
    @NotNull
    public <T extends ViewModel> T get(@NotNull Class<T> modelClass) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName != null) {
            return (T) get(Intrinsics.stringPlus("androidx.lifecycle.ViewModelProvider.DefaultKey:", canonicalName), modelClass);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    @MainThread
    @NotNull
    public <T extends ViewModel> T get(@NotNull String key, @NotNull Class<T> modelClass) {
        T viewModel;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        T viewModel2 = (T) this.store.get(key);
        if (modelClass.isInstance(viewModel2)) {
            Factory factory = this.factory;
            OnRequeryFactory onRequeryFactory = factory instanceof OnRequeryFactory ? (OnRequeryFactory) factory : null;
            if (onRequeryFactory != null) {
                Intrinsics.checkNotNullExpressionValue(viewModel2, "viewModel");
                onRequeryFactory.onRequery(viewModel2);
            }
            if (viewModel2 != null) {
                return viewModel2;
            }
            throw new NullPointerException("null cannot be cast to non-null type T of androidx.lifecycle.ViewModelProvider.get");
        }
        Factory factory2 = this.factory;
        if (factory2 instanceof KeyedFactory) {
            viewModel = (T) ((KeyedFactory) factory2).create(key, modelClass);
        } else {
            viewModel = (T) factory2.create(modelClass);
        }
        this.store.put(key, viewModel);
        Intrinsics.checkNotNullExpressionValue(viewModel, "viewModel");
        return viewModel;
    }

    /* compiled from: ViewModelProvider.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0004*\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0007H\u0016¢\u0006\u0002\u0010\b¨\u0006\n"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "()V", "create", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "Companion", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static class NewInstanceFactory implements Factory {
        @NotNull
        public static final Companion Companion = new Companion(null);
        @Nullable
        private static NewInstanceFactory sInstance;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @NotNull
        public static final NewInstanceFactory getInstance() {
            return Companion.getInstance();
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        @NotNull
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            try {
                T newInstance = modelClass.newInstance();
                Intrinsics.checkNotNullExpressionValue(newInstance, "{\n                modelClass.newInstance()\n            }");
                return newInstance;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", modelClass), e);
            } catch (InstantiationException e2) {
                throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", modelClass), e2);
            }
        }

        /* compiled from: ViewModelProvider.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u00048GX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory$Companion;", "", "()V", "instance", "Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory;", "getInstance$annotations", "getInstance", "()Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory;", "sInstance", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @JvmStatic
            public static /* synthetic */ void getInstance$annotations() {
            }

            private Companion() {
            }

            @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
            @NotNull
            public final NewInstanceFactory getInstance() {
                if (NewInstanceFactory.sInstance == null) {
                    NewInstanceFactory.sInstance = new NewInstanceFactory();
                }
                NewInstanceFactory newInstanceFactory = NewInstanceFactory.sInstance;
                Intrinsics.checkNotNull(newInstanceFactory);
                return newInstanceFactory;
            }
        }
    }

    /* compiled from: ViewModelProvider.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J%\u0010\u0005\u001a\u0002H\u0006\"\b\b\u0000\u0010\u0006*\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00060\tH\u0016¢\u0006\u0002\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$AndroidViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", "create", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "Companion", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static class AndroidViewModelFactory extends NewInstanceFactory {
        @NotNull
        public static final Companion Companion = new Companion(null);
        @NotNull
        public static final String DEFAULT_KEY = "androidx.lifecycle.ViewModelProvider.DefaultKey";
        @Nullable
        private static AndroidViewModelFactory sInstance;
        @NotNull
        private final Application application;

        @JvmStatic
        @NotNull
        public static final AndroidViewModelFactory getInstance(@NotNull Application application) {
            return Companion.getInstance(application);
        }

        public AndroidViewModelFactory(@NotNull Application application) {
            Intrinsics.checkNotNullParameter(application, "application");
            this.application = application;
        }

        @Override // androidx.lifecycle.ViewModelProvider.NewInstanceFactory, androidx.lifecycle.ViewModelProvider.Factory
        @NotNull
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass");
            if (!AndroidViewModel.class.isAssignableFrom(modelClass)) {
                return (T) super.create(modelClass);
            }
            try {
                T newInstance = modelClass.getConstructor(Application.class).newInstance(this.application);
                Intrinsics.checkNotNullExpressionValue(newInstance, "{\n                try {\n                    modelClass.getConstructor(Application::class.java).newInstance(application)\n                } catch (e: NoSuchMethodException) {\n                    throw RuntimeException(\"Cannot create an instance of $modelClass\", e)\n                } catch (e: IllegalAccessException) {\n                    throw RuntimeException(\"Cannot create an instance of $modelClass\", e)\n                } catch (e: InstantiationException) {\n                    throw RuntimeException(\"Cannot create an instance of $modelClass\", e)\n                } catch (e: InvocationTargetException) {\n                    throw RuntimeException(\"Cannot create an instance of $modelClass\", e)\n                }\n            }");
                return newInstance;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", modelClass), e);
            } catch (InstantiationException e2) {
                throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", modelClass), e2);
            } catch (NoSuchMethodException e3) {
                throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", modelClass), e3);
            } catch (InvocationTargetException e4) {
                throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", modelClass), e4);
            }
        }

        /* compiled from: ViewModelProvider.kt */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0000¢\u0006\u0002\b\u000bJ\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Landroidx/lifecycle/ViewModelProvider$AndroidViewModelFactory$Companion;", "", "()V", "DEFAULT_KEY", "", "sInstance", "Landroidx/lifecycle/ViewModelProvider$AndroidViewModelFactory;", "defaultFactory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "owner", "Landroidx/lifecycle/ViewModelStoreOwner;", "defaultFactory$lifecycle_viewmodel_release", "getInstance", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            @NotNull
            public final Factory defaultFactory$lifecycle_viewmodel_release(@NotNull ViewModelStoreOwner owner) {
                Intrinsics.checkNotNullParameter(owner, "owner");
                if (!(owner instanceof HasDefaultViewModelProviderFactory)) {
                    return NewInstanceFactory.Companion.getInstance();
                }
                Factory defaultViewModelProviderFactory = ((HasDefaultViewModelProviderFactory) owner).getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "owner.defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }

            @JvmStatic
            @NotNull
            public final AndroidViewModelFactory getInstance(@NotNull Application application) {
                Intrinsics.checkNotNullParameter(application, "application");
                if (AndroidViewModelFactory.sInstance == null) {
                    AndroidViewModelFactory.sInstance = new AndroidViewModelFactory(application);
                }
                AndroidViewModelFactory androidViewModelFactory = AndroidViewModelFactory.sInstance;
                Intrinsics.checkNotNull(androidViewModelFactory);
                return androidViewModelFactory;
            }
        }
    }
}
