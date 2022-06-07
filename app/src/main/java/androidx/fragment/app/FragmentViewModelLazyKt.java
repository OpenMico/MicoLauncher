package androidx.fragment.app;

import androidx.annotation.MainThread;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentViewModelLazy.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u0010\b\n\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\u0087\bø\u0001\u0000\u001aJ\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00062\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\u0007\u001aD\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u000e\b\n\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0010\b\n\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\u0087\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0010"}, d2 = {"activityViewModels", "Lkotlin/Lazy;", "VM", "Landroidx/lifecycle/ViewModel;", "Landroidx/fragment/app/Fragment;", "factoryProducer", "Lkotlin/Function0;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "createViewModelLazy", "viewModelClass", "Lkotlin/reflect/KClass;", "storeProducer", "Landroidx/lifecycle/ViewModelStore;", "viewModels", "ownerProducer", "Landroidx/lifecycle/ViewModelStoreOwner;", "fragment-ktx_release"}, k = 2, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class FragmentViewModelLazyKt {
    public static /* synthetic */ Lazy viewModels$default(Fragment viewModels, Function0 ownerProducer, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            ownerProducer = new FragmentViewModelLazyKt$viewModels$1(viewModels);
        }
        if ((i & 2) != 0) {
            function0 = null;
        }
        Intrinsics.checkNotNullParameter(viewModels, "$this$viewModels");
        Intrinsics.checkNotNullParameter(ownerProducer, "ownerProducer");
        Intrinsics.reifiedOperationMarker(4, "VM");
        return createViewModelLazy(viewModels, Reflection.getOrCreateKotlinClass(ViewModel.class), new FragmentViewModelLazyKt$viewModels$2(ownerProducer), function0);
    }

    @MainThread
    public static final /* synthetic */ <VM extends ViewModel> Lazy<VM> viewModels(Fragment viewModels, Function0<? extends ViewModelStoreOwner> ownerProducer, Function0<? extends ViewModelProvider.Factory> function0) {
        Intrinsics.checkNotNullParameter(viewModels, "$this$viewModels");
        Intrinsics.checkNotNullParameter(ownerProducer, "ownerProducer");
        Intrinsics.reifiedOperationMarker(4, "VM");
        return createViewModelLazy(viewModels, Reflection.getOrCreateKotlinClass(ViewModel.class), new FragmentViewModelLazyKt$viewModels$2(ownerProducer), function0);
    }

    public static /* synthetic */ Lazy activityViewModels$default(Fragment activityViewModels, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        Intrinsics.checkNotNullParameter(activityViewModels, "$this$activityViewModels");
        Intrinsics.reifiedOperationMarker(4, "VM");
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(ViewModel.class);
        FragmentViewModelLazyKt$activityViewModels$1 fragmentViewModelLazyKt$activityViewModels$1 = new FragmentViewModelLazyKt$activityViewModels$1(activityViewModels);
        if (function0 == null) {
            function0 = new FragmentViewModelLazyKt$activityViewModels$2(activityViewModels);
        }
        return createViewModelLazy(activityViewModels, orCreateKotlinClass, fragmentViewModelLazyKt$activityViewModels$1, function0);
    }

    @MainThread
    public static final /* synthetic */ <VM extends ViewModel> Lazy<VM> activityViewModels(Fragment activityViewModels, Function0<? extends ViewModelProvider.Factory> function0) {
        Intrinsics.checkNotNullParameter(activityViewModels, "$this$activityViewModels");
        Intrinsics.reifiedOperationMarker(4, "VM");
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(ViewModel.class);
        FragmentViewModelLazyKt$activityViewModels$1 fragmentViewModelLazyKt$activityViewModels$1 = new FragmentViewModelLazyKt$activityViewModels$1(activityViewModels);
        if (function0 == null) {
            function0 = new FragmentViewModelLazyKt$activityViewModels$2(activityViewModels);
        }
        return createViewModelLazy(activityViewModels, orCreateKotlinClass, fragmentViewModelLazyKt$activityViewModels$1, function0);
    }

    public static /* synthetic */ Lazy createViewModelLazy$default(Fragment fragment, KClass kClass, Function0 function0, Function0 function02, int i, Object obj) {
        if ((i & 4) != 0) {
            function02 = null;
        }
        return createViewModelLazy(fragment, kClass, function0, function02);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FragmentViewModelLazy.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "Landroidx/lifecycle/ViewModelProvider$Factory;", "VM", "Landroidx/lifecycle/ViewModel;", "invoke"}, k = 3, mv = {1, 4, 1})
    /* loaded from: classes.dex */
    public static final class a extends Lambda implements Function0<ViewModelProvider.Factory> {
        final /* synthetic */ Fragment $this_createViewModelLazy;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(Fragment fragment) {
            super(0);
            this.$this_createViewModelLazy = fragment;
        }

        @NotNull
        /* renamed from: a */
        public final ViewModelProvider.Factory invoke() {
            return this.$this_createViewModelLazy.getDefaultViewModelProviderFactory();
        }
    }

    @MainThread
    @NotNull
    public static final <VM extends ViewModel> Lazy<VM> createViewModelLazy(@NotNull Fragment createViewModelLazy, @NotNull KClass<VM> viewModelClass, @NotNull Function0<? extends ViewModelStore> storeProducer, @Nullable Function0<? extends ViewModelProvider.Factory> function0) {
        Intrinsics.checkNotNullParameter(createViewModelLazy, "$this$createViewModelLazy");
        Intrinsics.checkNotNullParameter(viewModelClass, "viewModelClass");
        Intrinsics.checkNotNullParameter(storeProducer, "storeProducer");
        if (function0 == null) {
            function0 = new a(createViewModelLazy);
        }
        return new ViewModelLazy(viewModelClass, storeProducer, function0);
    }
}
