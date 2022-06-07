package com.xiaomi.smarthome.ui.vm;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelKt;
import com.elvishew.xlog.Logger;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.mico.base.utils.NetworkUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.smarthome.base.BaseViewModel;
import com.xiaomi.smarthome.core.entity.MediaType;
import com.xiaomi.smarthome.core.entity.MicoMediaData;
import com.xiaomi.smarthome.core.entity.TypeCategoryReorderEntrance;
import com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter;
import com.xiaomi.smarthome.ui.model.CategoryEmpty;
import com.xiaomi.smarthome.ui.model.CategoryScene;
import com.xiaomi.smarthome.ui.model.ISmartHomeModel;
import com.xiaomi.smarthome.ui.model.SmartHomeRepository;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ThreadPoolDispatcherKt;
import kotlinx.coroutines.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SmartHomeViewModel.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \"2\u00020\u0001:\u0001\"B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J0\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020\u0010J2\u0010!\u001a\u00020\u001a2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020\u0010R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R'\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\fR\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u000e\u001a\u0004\b\u0016\u0010\u0017¨\u0006#"}, d2 = {"Lcom/xiaomi/smarthome/ui/vm/SmartHomeViewModel;", "Lcom/xiaomi/smarthome/base/BaseViewModel;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", "categoryScene", "Lcom/xiaomi/smarthome/ui/model/CategoryScene;", "deviceList", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "getDeviceList", "()Landroidx/lifecycle/MutableLiveData;", "deviceList$delegate", "Lkotlin/Lazy;", "loaded", "", "getLoaded", "loading", "getLoading", "repository", "Lcom/xiaomi/smarthome/ui/model/SmartHomeRepository;", "getRepository", "()Lcom/xiaomi/smarthome/ui/model/SmartHomeRepository;", "repository$delegate", "loadDevices", "", "region", "Lcom/xiaomi/smarthome/ui/adapter/MiotRegionAdapter$MiotRegion;", SmartHomeStatHelper.PARAM_VALUE_SCENE, "mediaData", "Lcom/xiaomi/smarthome/core/entity/MicoMediaData;", "isAllInfoReady", "loadDevicesByRegion", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class SmartHomeViewModel extends BaseViewModel {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final CoroutineDispatcher f = ThreadPoolDispatcherKt.newSingleThreadContext("smartHomeWorkThread");
    private final Lazy a = LazyKt.lazy(c.a);
    @NotNull
    private final Lazy b = LazyKt.lazy(a.a);
    @NotNull
    private final MutableLiveData<Boolean> c = new MutableLiveData<>();
    @NotNull
    private final MutableLiveData<Boolean> d = new MutableLiveData<>();
    private volatile CategoryScene e;

    /* JADX INFO: Access modifiers changed from: private */
    public final SmartHomeRepository a() {
        return (SmartHomeRepository) this.a.getValue();
    }

    @NotNull
    public static final CoroutineDispatcher getSmartHomeThread() {
        Companion companion = Companion;
        return f;
    }

    @NotNull
    public final MutableLiveData<List<ISmartHomeModel>> getDeviceList() {
        return (MutableLiveData) this.b.getValue();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartHomeViewModel(@NotNull Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
    }

    /* compiled from: SmartHomeViewModel.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/ui/model/SmartHomeRepository;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class c extends Lambda implements Function0<SmartHomeRepository> {
        public static final c a = new c();

        c() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final SmartHomeRepository invoke() {
            return SmartHomeRepository.Companion.getINSTANCE();
        }
    }

    /* compiled from: SmartHomeViewModel.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/xiaomi/smarthome/ui/vm/SmartHomeViewModel$Companion;", "", "()V", "smartHomeThread", "Lkotlinx/coroutines/CoroutineDispatcher;", "getSmartHomeThread$annotations", "getSmartHomeThread", "()Lkotlinx/coroutines/CoroutineDispatcher;", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        @JvmStatic
        public static /* synthetic */ void getSmartHomeThread$annotations() {
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final CoroutineDispatcher getSmartHomeThread() {
            return SmartHomeViewModel.f;
        }
    }

    /* compiled from: SmartHomeViewModel.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a extends Lambda implements Function0<MutableLiveData<List<? extends ISmartHomeModel>>> {
        public static final a a = new a();

        a() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final MutableLiveData<List<ISmartHomeModel>> invoke() {
            return new MutableLiveData<>();
        }
    }

    @NotNull
    public final MutableLiveData<Boolean> getLoading() {
        return this.c;
    }

    @NotNull
    public final MutableLiveData<Boolean> getLoaded() {
        return this.d;
    }

    public static /* synthetic */ void loadDevicesByRegion$default(SmartHomeViewModel smartHomeViewModel, MiotRegionAdapter.MiotRegion miotRegion, CategoryScene categoryScene, MicoMediaData micoMediaData, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            miotRegion = null;
        }
        if ((i & 2) != 0) {
            categoryScene = null;
        }
        if ((i & 4) != 0) {
            micoMediaData = null;
        }
        smartHomeViewModel.loadDevicesByRegion(miotRegion, categoryScene, micoMediaData, z);
    }

    public final void loadDevicesByRegion(@Nullable MiotRegionAdapter.MiotRegion miotRegion, @Nullable CategoryScene categoryScene, @Nullable MicoMediaData micoMediaData, boolean z) {
        loadDevices(miotRegion, categoryScene, micoMediaData, z);
    }

    public static /* synthetic */ void loadDevices$default(SmartHomeViewModel smartHomeViewModel, MiotRegionAdapter.MiotRegion miotRegion, CategoryScene categoryScene, MicoMediaData micoMediaData, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            categoryScene = null;
        }
        if ((i & 4) != 0) {
            micoMediaData = null;
        }
        smartHomeViewModel.loadDevices(miotRegion, categoryScene, micoMediaData, z);
    }

    public final void loadDevices(@Nullable MiotRegionAdapter.MiotRegion miotRegion, @Nullable CategoryScene categoryScene, @Nullable MicoMediaData micoMediaData, boolean z) {
        MicoMediaData micoMediaData2 = micoMediaData;
        Logger logger = L.smarthome;
        StringBuilder sb = new StringBuilder();
        sb.append("loadDevices region:");
        List<ISmartHomeModel> list = null;
        sb.append(miotRegion != null ? miotRegion.getRegionName() : null);
        logger.d(sb.toString());
        boolean z2 = false;
        if (miotRegion == null || !z) {
            ArrayList arrayList = new ArrayList();
            if (micoMediaData2 == null) {
                micoMediaData2 = new MicoMediaData(MediaType.NONE, "", "", "", null, null, null, 0, null, 496, null);
            }
            arrayList.add(micoMediaData2);
            arrayList.add(new CategoryEmpty());
            getDeviceList().postValue(arrayList);
            this.c.postValue(false);
            L.smarthome.d(MicoSupConstants.TAG_LAU, "Info:loadDevices empty, " + miotRegion + StringUtil.COMMA + z);
            return;
        }
        if (!NetworkUtil.isNetworkConnected(getApplication())) {
            List<ISmartHomeModel> value = getDeviceList().getValue();
            if (value == null || value.isEmpty()) {
                z2 = true;
            }
            if (!z2 && Intrinsics.areEqual((Object) this.d.getValue(), (Object) true)) {
                List<ISmartHomeModel> value2 = getDeviceList().getValue();
                if (value2 != null) {
                    List<ISmartHomeModel> list2 = value2;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    for (MicoMediaData micoMediaData3 : list2) {
                        if (micoMediaData3 instanceof MicoMediaData) {
                            if (micoMediaData2 != null) {
                                micoMediaData3 = micoMediaData2;
                            } else {
                                throw new NullPointerException("null cannot be cast to non-null type com.xiaomi.smarthome.ui.model.ISmartHomeModel");
                            }
                        }
                        arrayList2.add(micoMediaData3);
                    }
                    list = CollectionsKt.toList(arrayList2);
                }
                if (list != null) {
                    getDeviceList().postValue(list);
                }
                L.smarthome.d("loadDevices no network");
                return;
            }
        }
        this.e = categoryScene;
        e.a(ViewModelKt.getViewModelScope(this), null, null, new b(miotRegion, micoMediaData2, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeViewModel.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.xiaomi.smarthome.ui.vm.SmartHomeViewModel$loadDevices$2", f = "SmartHomeViewModel.kt", i = {}, l = {101}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes4.dex */
    public static final class b extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ MicoMediaData $mediaData;
        final /* synthetic */ MiotRegionAdapter.MiotRegion $region;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(MiotRegionAdapter.MiotRegion miotRegion, MicoMediaData micoMediaData, Continuation continuation) {
            super(2, continuation);
            this.$region = miotRegion;
            this.$mediaData = micoMediaData;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new b(this.$region, this.$mediaData, completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((b) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    this.label = 1;
                    if (BuildersKt.withContext(SmartHomeViewModel.Companion.getSmartHomeThread(), new AnonymousClass1(null), this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure(obj);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: SmartHomeViewModel.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "com.xiaomi.smarthome.ui.vm.SmartHomeViewModel$loadDevices$2$1", f = "SmartHomeViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.xiaomi.smarthome.ui.vm.SmartHomeViewModel$b$1  reason: invalid class name */
        /* loaded from: classes4.dex */
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;

            AnonymousClass1(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                return new AnonymousClass1(completion);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    try {
                        Result.Companion companion = Result.Companion;
                        List<ISmartHomeModel> loadDevices = SmartHomeViewModel.this.a().loadDevices(b.this.$region, b.this.$mediaData);
                        if (!loadDevices.isEmpty()) {
                            if (SmartHomeRepository.Companion.getINSTANCE().getDeviceCount() < 5) {
                                loadDevices.add(new CategoryEmpty());
                            }
                            loadDevices.add(new TypeCategoryReorderEntrance(null, 1, null));
                        } else {
                            if (b.this.$region.isHasVoiceBox()) {
                                MicoMediaData micoMediaData = b.this.$mediaData;
                                if (micoMediaData == null) {
                                    micoMediaData = new MicoMediaData(MediaType.NONE, "", "", "", null, null, null, 0, null, 496, null);
                                }
                                loadDevices.add(micoMediaData);
                            }
                            loadDevices.add(new CategoryEmpty());
                        }
                        SmartHomeViewModel.this.getLoading().postValue(Boxing.boxBoolean(false));
                        if (SmartHomeViewModel.this.e != null) {
                            CategoryScene categoryScene = SmartHomeViewModel.this.e;
                            Intrinsics.checkNotNull(categoryScene);
                            loadDevices.add(0, categoryScene);
                        }
                        SmartHomeViewModel.this.getDeviceList().postValue(loadDevices);
                        SmartHomeViewModel.this.getLoaded().postValue(Boxing.boxBoolean(true));
                        Logger logger = L.smarthome;
                        logger.d("postValue:" + loadDevices.size());
                        Result.m1220constructorimpl(Unit.INSTANCE);
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.Companion;
                        Result.m1220constructorimpl(ResultKt.createFailure(th));
                    }
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }
}
