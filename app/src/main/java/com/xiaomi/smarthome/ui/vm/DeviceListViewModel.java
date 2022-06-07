package com.xiaomi.smarthome.ui.vm;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelKt;
import com.elvishew.xlog.Logger;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.core.ISendMsgCallback;
import com.xiaomi.smarthome.base.BaseViewModel;
import com.xiaomi.smarthome.core.entity.CategoryDic;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.IDevice;
import com.xiaomi.smarthome.core.entity.PropDic;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import com.xiaomi.smarthome.core.utils.OperationUtils;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeviceListViewModel.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0014R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, d2 = {"Lcom/xiaomi/smarthome/ui/vm/DeviceListViewModel;", "Lcom/xiaomi/smarthome/base/BaseViewModel;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", "changedIndex", "Landroidx/lifecycle/MutableLiveData;", "", "getChangedIndex", "()Landroidx/lifecycle/MutableLiveData;", "changedIndex$delegate", "Lkotlin/Lazy;", "patchOperating", "", "categoryDic", "Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "deviceList", "", "Lcom/xiaomi/smarthome/core/entity/IDevice;", "turnStatus", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class DeviceListViewModel extends BaseViewModel {
    @NotNull
    private final Lazy a = LazyKt.lazy(a.a);

    @NotNull
    public final MutableLiveData<Integer> getChangedIndex() {
        return (MutableLiveData) this.a.getValue();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceListViewModel(@NotNull Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
    }

    /* compiled from: DeviceListViewModel.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Landroidx/lifecycle/MutableLiveData;", "", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a extends Lambda implements Function0<MutableLiveData<Integer>> {
        public static final a a = new a();

        a() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final MutableLiveData<Integer> invoke() {
            return new MutableLiveData<>();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: DeviceListViewModel.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.xiaomi.smarthome.ui.vm.DeviceListViewModel$patchOperating$1", f = "DeviceListViewModel.kt", i = {}, l = {33}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes4.dex */
    public static final class b extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List $deviceList;
        final /* synthetic */ boolean $isCurtain;
        final /* synthetic */ boolean $turnStatus;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(List list, boolean z, boolean z2, Continuation continuation) {
            super(2, continuation);
            this.$deviceList = list;
            this.$isCurtain = z;
            this.$turnStatus = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new b(this.$deviceList, this.$isCurtain, this.$turnStatus, completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((b) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: DeviceListViewModel.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "com.xiaomi.smarthome.ui.vm.DeviceListViewModel$patchOperating$1$1", f = "DeviceListViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.xiaomi.smarthome.ui.vm.DeviceListViewModel$b$1  reason: invalid class name */
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
                    ArrayList<DeviceInfoBean> arrayList = new ArrayList();
                    for (Object obj2 : CollectionsKt.filterIsInstance(b.this.$deviceList, DeviceInfoBean.class)) {
                        if (Boxing.boxBoolean(((DeviceInfoBean) obj2).getDeviceInfoWrapper().getDeviceInfo().isOnline).booleanValue()) {
                            arrayList.add(obj2);
                        }
                    }
                    for (DeviceInfoBean deviceInfoBean : arrayList) {
                        if (b.this.$isCurtain) {
                            String propKey = MiotDeviceUtils.getPropKey(deviceInfoBean.getDeviceInfoWrapper(), PropDic.P_MONITOR_CONTROL);
                            if (propKey != null) {
                                MiotManager.setDeviceProperty(deviceInfoBean.getDeviceInfoWrapper().getDeviceInfo().did, propKey, b.this.$turnStatus ? "1" : "2", "int", a.a);
                            }
                        } else {
                            OperationUtils.Companion companion = OperationUtils.Companion;
                            DeviceInfo deviceInfo = deviceInfoBean.getDeviceInfoWrapper().getDeviceInfo();
                            Intrinsics.checkNotNullExpressionValue(deviceInfo, "it.deviceInfoWrapper.deviceInfo");
                            companion.toggleStatus(deviceInfo, b.this.$turnStatus);
                        }
                    }
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* compiled from: DeviceListViewModel.kt */
            @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "result", "", "kotlin.jvm.PlatformType", "onResult"}, k = 3, mv = {1, 4, 2})
            /* renamed from: com.xiaomi.smarthome.ui.vm.DeviceListViewModel$b$1$a */
            /* loaded from: classes4.dex */
            public static final class a implements ISendMsgCallback {
                public static final a a = new a();

                a() {
                }

                @Override // com.xiaomi.miot.support.core.ISendMsgCallback
                public final void onResult(String str) {
                    Logger logger = L.smarthome;
                    logger.d("open curtain " + str);
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    this.label = 1;
                    if (BuildersKt.withContext(Dispatchers.getDefault(), new AnonymousClass1(null), this) == coroutine_suspended) {
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
    }

    public final void patchOperating(@NotNull CategoryDic categoryDic, @NotNull List<? extends IDevice> deviceList, boolean z) {
        Intrinsics.checkNotNullParameter(categoryDic, "categoryDic");
        Intrinsics.checkNotNullParameter(deviceList, "deviceList");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new b(deviceList, categoryDic == CategoryDic.CATEGORY_CURTAIN, z, null), 3, null);
    }
}
