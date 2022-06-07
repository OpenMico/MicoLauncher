package com.xiaomi.smarthome.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import autodispose2.AutoDispose;
import autodispose2.ObservableSubscribeProxy;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;
import com.dinuscxj.itemdecoration.LinearOffsetsItemDecoration;
import com.jakewharton.rxbinding4.view.RxView;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.utils.AnimationUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.homepage.event.MainPageGotoSetEvent;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.base.BaseActivity;
import com.xiaomi.smarthome.core.db.CategoryRankEntity;
import com.xiaomi.smarthome.core.db.Db;
import com.xiaomi.smarthome.core.db.RankDao;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;
import com.xiaomi.smarthome.core.utils.ViewModelUtils;
import com.xiaomi.smarthome.databinding.ActivityCategoryReorderBinding;
import com.xiaomi.smarthome.ui.ConfirmDialog;
import com.xiaomi.smarthome.ui.adapter.CategoryListAdapter;
import com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter;
import com.xiaomi.smarthome.ui.model.ISmartHomeModel;
import com.xiaomi.smarthome.ui.vm.SmartHomeViewModel;
import com.xiaomi.smarthome.ui.widgets.ItemTouchCallBack;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CategoryReorderActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020 H\u0002J\b\u0010\"\u001a\u00020 H\u0002J\b\u0010#\u001a\u00020 H\u0015J\b\u0010$\u001a\u00020 H\u0014J\u0012\u0010%\u001a\u00020 2\b\u0010&\u001a\u0004\u0018\u00010'H\u0014J\b\u0010(\u001a\u00020 H\u0014J\u001a\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010.H\u0016J\b\u0010/\u001a\u00020 H\u0014J\u0010\u00100\u001a\u00020 2\u0006\u00101\u001a\u00020'H\u0015J\b\u00102\u001a\u00020 H\u0002J\b\u00103\u001a\u00020 H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u000e@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\b\u001a\u0004\b\u0014\u0010\u0015R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010\u0019\u001a\n \u001b*\u0004\u0018\u00010\u001a0\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\b\u001a\u0004\b\u001c\u0010\u001d¨\u00064"}, d2 = {"Lcom/xiaomi/smarthome/ui/CategoryReorderActivity;", "Lcom/xiaomi/smarthome/base/BaseActivity;", "()V", "adapter", "Lcom/xiaomi/smarthome/ui/adapter/CategoryListAdapter;", "getAdapter", "()Lcom/xiaomi/smarthome/ui/adapter/CategoryListAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "binding", "Lcom/xiaomi/smarthome/databinding/ActivityCategoryReorderBinding;", "dialog", "Lcom/xiaomi/smarthome/ui/ConfirmDialog;", com.xiaomi.onetrack.api.b.p, "Lcom/xiaomi/smarthome/ui/adapter/MiotRegionAdapter$MiotRegion;", "mCurRegion", "setMCurRegion", "(Lcom/xiaomi/smarthome/ui/adapter/MiotRegionAdapter$MiotRegion;)V", "touchCallBack", "Lcom/xiaomi/smarthome/ui/widgets/ItemTouchCallBack;", "getTouchCallBack", "()Lcom/xiaomi/smarthome/ui/widgets/ItemTouchCallBack;", "touchCallBack$delegate", "touchHelper", "Landroidx/recyclerview/widget/ItemTouchHelper;", "vm", "Lcom/xiaomi/smarthome/ui/vm/SmartHomeViewModel;", "kotlin.jvm.PlatformType", "getVm", "()Lcom/xiaomi/smarthome/ui/vm/SmartHomeViewModel;", "vm$delegate", "addTouchHelper", "", "doSave", "initConfirmDialog", "initViews", "observeData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onKeyDown", "", "keyCode", "", "event", "Landroid/view/KeyEvent;", "onResume", "onSaveInstanceState", "outState", "removeTouchHelper", "updateList", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class CategoryReorderActivity extends BaseActivity {
    private ActivityCategoryReorderBinding a;
    private ItemTouchHelper c;
    private MiotRegionAdapter.MiotRegion f;
    private ConfirmDialog g;
    private final Lazy b = LazyKt.lazy(j.a);
    private final Lazy d = LazyKt.lazy(a.a);
    private final Lazy e = LazyKt.lazy(new l());

    private final ItemTouchCallBack a() {
        return (ItemTouchCallBack) this.b.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CategoryListAdapter b() {
        return (CategoryListAdapter) this.d.getValue();
    }

    private final SmartHomeViewModel c() {
        return (SmartHomeViewModel) this.e.getValue();
    }

    public static final /* synthetic */ ActivityCategoryReorderBinding access$getBinding$p(CategoryReorderActivity categoryReorderActivity) {
        ActivityCategoryReorderBinding activityCategoryReorderBinding = categoryReorderActivity.a;
        if (activityCategoryReorderBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        return activityCategoryReorderBinding;
    }

    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/ui/widgets/ItemTouchCallBack;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class j extends Lambda implements Function0<ItemTouchCallBack> {
        public static final j a = new j();

        j() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final ItemTouchCallBack invoke() {
            return new ItemTouchCallBack();
        }
    }

    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/ui/adapter/CategoryListAdapter;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a extends Lambda implements Function0<CategoryListAdapter> {
        public static final a a = new a();

        a() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final CategoryListAdapter invoke() {
            return new CategoryListAdapter();
        }
    }

    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/ui/vm/SmartHomeViewModel;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class l extends Lambda implements Function0<SmartHomeViewModel> {
        l() {
            super(0);
        }

        /* renamed from: a */
        public final SmartHomeViewModel invoke() {
            return (SmartHomeViewModel) ViewModelUtils.obtainViewModel(CategoryReorderActivity.this, SmartHomeViewModel.class);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a(MiotRegionAdapter.MiotRegion miotRegion) {
        String str;
        if (miotRegion != null) {
            String regionId = miotRegion.getRegionId();
            MiotRegionAdapter.MiotRegion miotRegion2 = this.f;
            if (!regionId.equals(miotRegion2 != null ? miotRegion2.getRegionId() : null)) {
                this.f = miotRegion;
                L.smarthome.d("region has changed,need reload devices");
                c().loadDevicesByRegion(miotRegion, null, null, true);
                ActivityCategoryReorderBinding activityCategoryReorderBinding = this.a;
                if (activityCategoryReorderBinding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                }
                TextView textView = activityCategoryReorderBinding.title;
                Intrinsics.checkNotNullExpressionValue(textView, "binding.title");
                if (TextUtils.isEmpty(miotRegion.getRoomName()) || !Hardware.isX6A()) {
                    str = miotRegion.getRegionName() + ' ';
                } else {
                    str = miotRegion.getHomeName() + " | " + miotRegion.getRoomName() + ' ';
                }
                textView.setText(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.smarthome.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_category_reorder);
        Intrinsics.checkNotNullExpressionValue(contentView, "DataBindingUtil.setConte…ctivity_category_reorder)");
        this.a = (ActivityCategoryReorderBinding) contentView;
        initViews();
        f();
        observeData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.smarthome.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
        a(instance.getCurRegion());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.xiaomi.smarthome.ui.CategoryReorderActivity$updateList$1", f = "CategoryReorderActivity.kt", i = {}, l = {259}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes4.dex */
    public static final class k extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        k(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new k(completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((k) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            String regionId;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    MiotRegionAdapter.MiotRegion miotRegion = CategoryReorderActivity.this.f;
                    if (!(miotRegion == null || (regionId = miotRegion.getRegionId()) == null)) {
                        this.label = 1;
                        if (FlowKt.flowOn(Db.Companion.getInstance().rankDao().getCategoryRankRecordsByHomeId(regionId), Dispatchers.getDefault()).collect(new FlowCollector<List<? extends CategoryRankEntity>>() { // from class: com.xiaomi.smarthome.ui.CategoryReorderActivity$updateList$1$invokeSuspend$$inlined$let$lambda$1
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            @Nullable
                            public Object emit(List<? extends CategoryRankEntity> list, @NotNull Continuation<? super Unit> continuation) {
                                List<? extends CategoryRankEntity> list2 = list;
                                ImageView imageView = CategoryReorderActivity.access$getBinding$p(CategoryReorderActivity.this).defaultBgIv;
                                Intrinsics.checkNotNullExpressionValue(imageView, "binding.defaultBgIv");
                                imageView.setVisibility(list2.size() == 0 ? 0 : 8);
                                CategoryReorderActivity.this.b().updateData(list2);
                                return Unit.INSTANCE;
                            }
                        }, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void d() {
        kotlinx.coroutines.e.a(LifecycleOwnerKt.getLifecycleScope(this), null, null, new k(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    @SuppressLint({"MissingSuperCall"})
    public void onSaveInstanceState(@NotNull Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.smarthome.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        g();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.smarthome.base.BaseActivity
    @SuppressLint({"ClickableViewAccessibility"})
    public void initViews() {
        super.initViews();
        e();
        ActivityCategoryReorderBinding activityCategoryReorderBinding = this.a;
        if (activityCategoryReorderBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        activityCategoryReorderBinding.ivCancel.setOnTouchListener(new e());
        ActivityCategoryReorderBinding activityCategoryReorderBinding2 = this.a;
        if (activityCategoryReorderBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        ImageView imageView = activityCategoryReorderBinding2.ivCancel;
        Intrinsics.checkNotNullExpressionValue(imageView, "binding.ivCancel");
        Observable<Unit> observeOn = RxView.clicks(imageView).throttleFirst(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread());
        Intrinsics.checkNotNullExpressionValue(observeOn, "binding.ivCancel.clicks(…dSchedulers.mainThread())");
        CategoryReorderActivity categoryReorderActivity = this;
        Object obj = observeOn.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(categoryReorderActivity)));
        Intrinsics.checkExpressionValueIsNotNull(obj, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
        ((ObservableSubscribeProxy) obj).subscribe(new f());
        ActivityCategoryReorderBinding activityCategoryReorderBinding3 = this.a;
        if (activityCategoryReorderBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        AnimLifecycleManager.repeatOnAttach(activityCategoryReorderBinding3.tvDone, MicoAnimConfigurator4TabAndSwitch.get());
        ActivityCategoryReorderBinding activityCategoryReorderBinding4 = this.a;
        if (activityCategoryReorderBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        TextView textView = activityCategoryReorderBinding4.tvDone;
        Intrinsics.checkNotNullExpressionValue(textView, "binding.tvDone");
        Observable<Unit> observeOn2 = RxView.clicks(textView).throttleFirst(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread());
        Intrinsics.checkNotNullExpressionValue(observeOn2, "binding.tvDone.clicks()\n…dSchedulers.mainThread())");
        Object obj2 = observeOn2.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(categoryReorderActivity)));
        Intrinsics.checkExpressionValueIsNotNull(obj2, "this.to(AutoDispose.auto…er.from(lifecycleOwner)))");
        ((ObservableSubscribeProxy) obj2).subscribe(new g());
        ActivityCategoryReorderBinding activityCategoryReorderBinding5 = this.a;
        if (activityCategoryReorderBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        RecyclerView recyclerView = activityCategoryReorderBinding5.rvCategoryList;
        Intrinsics.checkNotNullExpressionValue(recyclerView, "binding.rvCategoryList");
        RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
        ActivityCategoryReorderBinding activityCategoryReorderBinding6 = this.a;
        if (activityCategoryReorderBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        RecyclerView recyclerView2 = activityCategoryReorderBinding6.rvCategoryList;
        Intrinsics.checkNotNullExpressionValue(recyclerView2, "binding.rvCategoryList");
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, 0, false));
        LinearOffsetsItemDecoration linearOffsetsItemDecoration = new LinearOffsetsItemDecoration(0);
        linearOffsetsItemDecoration.setItemOffsets(getResources().getDimensionPixelSize(R.dimen.recorder_item_decoration));
        ActivityCategoryReorderBinding activityCategoryReorderBinding7 = this.a;
        if (activityCategoryReorderBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        activityCategoryReorderBinding7.rvCategoryList.addItemDecoration(linearOffsetsItemDecoration);
        ActivityCategoryReorderBinding activityCategoryReorderBinding8 = this.a;
        if (activityCategoryReorderBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        RecyclerView recyclerView3 = activityCategoryReorderBinding8.rvCategoryList;
        Intrinsics.checkNotNullExpressionValue(recyclerView3, "binding.rvCategoryList");
        recyclerView3.setAdapter(b());
        if (Hardware.isX6A()) {
            ActivityCategoryReorderBinding activityCategoryReorderBinding9 = this.a;
            if (activityCategoryReorderBinding9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            }
            RxViewHelp.debounceClicksWithOneSeconds(activityCategoryReorderBinding9.title).subscribe(h.a);
            Drawable drawable = getDrawable(R.drawable.sh_ic_arrow_right);
            Intrinsics.checkNotNullExpressionValue(drawable, "getDrawable(R.drawable.sh_ic_arrow_right)");
            drawable.setBounds(0, 0, drawable.getMinimumWidth() * 2, drawable.getMinimumHeight() * 2);
            ActivityCategoryReorderBinding activityCategoryReorderBinding10 = this.a;
            if (activityCategoryReorderBinding10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            }
            activityCategoryReorderBinding10.title.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroid/view/View;", "kotlin.jvm.PlatformType", "motionEvent", "Landroid/view/MotionEvent;", "onTouch"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class e implements View.OnTouchListener {
        e() {
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            Intrinsics.checkNotNullExpressionValue(motionEvent, "motionEvent");
            int action = motionEvent.getAction();
            if (action != 3) {
                switch (action) {
                    case 0:
                        AnimationUtils.titleIconTouchDown(CategoryReorderActivity.access$getBinding$p(CategoryReorderActivity.this).ivCancelCircleBg);
                        return false;
                    case 1:
                        break;
                    default:
                        return false;
                }
            }
            AnimationUtils.titleIconTouchUp(CategoryReorderActivity.access$getBinding$p(CategoryReorderActivity.this).ivCancelCircleBg);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class f<T> implements Consumer<Unit> {
        f() {
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            if (CategoryReorderActivity.this.b().isDataChanged()) {
                ConfirmDialog confirmDialog = CategoryReorderActivity.this.g;
                if (confirmDialog != null) {
                    confirmDialog.show();
                    return;
                }
                return;
            }
            CategoryReorderActivity.this.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class g<T> implements Consumer<Unit> {
        g() {
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            CategoryReorderActivity.this.h();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "o", "", "accept"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class h<T> implements io.reactivex.functions.Consumer<Object> {
        public static final h a = new h();

        h() {
        }

        @Override // io.reactivex.functions.Consumer
        public final void accept(@Nullable Object obj) {
            EventBusRegistry.getEventBus().post(new MainPageGotoSetEvent());
        }
    }

    private final void e() {
        this.g = new ConfirmDialog(this).setOnRightClickListener(new c()).setOnLeftClickListener(new d()).setCancelable(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onRightClick"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class c implements ConfirmDialog.OnRightClickListener {
        c() {
        }

        @Override // com.xiaomi.smarthome.ui.ConfirmDialog.OnRightClickListener
        public final void onRightClick(View view) {
            CategoryReorderActivity.this.h();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onLeftClick"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class d implements ConfirmDialog.OnLeftClickListener {
        d() {
        }

        @Override // com.xiaomi.smarthome.ui.ConfirmDialog.OnLeftClickListener
        public final void onLeftClick(View view) {
            CategoryReorderActivity.this.finish();
        }
    }

    private final void f() {
        if (this.c == null) {
            a().setListener(b());
            this.c = new ItemTouchHelper(a());
            CategoryListAdapter b2 = b();
            ItemTouchHelper itemTouchHelper = this.c;
            if (itemTouchHelper != null) {
                b2.setTouchHelper(itemTouchHelper);
                ItemTouchHelper itemTouchHelper2 = this.c;
                if (itemTouchHelper2 != null) {
                    ActivityCategoryReorderBinding activityCategoryReorderBinding = this.a;
                    if (activityCategoryReorderBinding == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    }
                    itemTouchHelper2.attachToRecyclerView(activityCategoryReorderBinding.rvCategoryList);
                    return;
                }
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type androidx.recyclerview.widget.ItemTouchHelper");
        }
    }

    private final void g() {
        if (this.c != null) {
            try {
                Method method = ItemTouchHelper.class.getDeclaredMethod("e", new Class[0]);
                Intrinsics.checkNotNullExpressionValue(method, "method");
                method.setAccessible(true);
                method.invoke(this.c, new Object[0]);
                this.c = null;
                L.smarthome.i("DeviceListActivity: removeTouchHelper ");
            } catch (IllegalAccessException e2) {
                L.smarthome.i("DeviceListActivity: removeTouchHelper : ", e2);
            } catch (NoSuchMethodException e3) {
                L.smarthome.i("DeviceListActivity: removeTouchHelper : ", e3);
            } catch (InvocationTargetException e4) {
                L.smarthome.i("DeviceListActivity: removeTouchHelper : ", e4);
            }
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i2, @Nullable KeyEvent keyEvent) {
        if (i2 == 4) {
            ConfirmDialog confirmDialog = this.g;
            if (confirmDialog != null && confirmDialog.isShowing()) {
                ConfirmDialog confirmDialog2 = this.g;
                if (confirmDialog2 != null) {
                    confirmDialog2.dismiss();
                }
                return true;
            } else if (b().isDataChanged()) {
                ConfirmDialog confirmDialog3 = this.g;
                if (confirmDialog3 != null) {
                    confirmDialog3.show();
                }
                return true;
            }
        }
        return super.onKeyDown(i2, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.xiaomi.smarthome.ui.CategoryReorderActivity$doSave$1", f = "CategoryReorderActivity.kt", i = {}, l = {242}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes4.dex */
    public static final class b extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        b(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            b bVar = new b(completion);
            bVar.L$0 = obj;
            return bVar;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((b) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: CategoryReorderActivity.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "com.xiaomi.smarthome.ui.CategoryReorderActivity$doSave$1$deffer$1", f = "CategoryReorderActivity.kt", i = {}, l = {239}, m = "invokeSuspend", n = {}, s = {})
        /* loaded from: classes4.dex */
        public static final class a extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
            int label;

            a(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                return new a(completion);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
                return ((a) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                String regionId;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        MiotRegionAdapter.MiotRegion miotRegion = CategoryReorderActivity.this.f;
                        if (miotRegion == null || (regionId = miotRegion.getRegionId()) == null) {
                            return null;
                        }
                        RankDao rankDao = Db.Companion.getInstance().rankDao();
                        List<CategoryRankEntity> categoryList = CategoryReorderActivity.this.b().getCategoryList();
                        this.label = 1;
                        obj = rankDao.insertCategoryRankRecordsForKotlin(regionId, categoryList, this);
                        if (obj == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        break;
                    case 1:
                        ResultKt.throwOnFailure(obj);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                return Boxing.boxBoolean(((Boolean) obj).booleanValue());
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Deferred b;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    b = kotlinx.coroutines.e.b((CoroutineScope) this.L$0, null, null, new a(null), 3, null);
                    this.label = 1;
                    obj = b.await(this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure(obj);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            if (Intrinsics.areEqual((Boolean) obj, Boxing.boxBoolean(true))) {
                LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_REFRESH).post(Boxing.boxBoolean(true));
            }
            CategoryReorderActivity.this.finish();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void h() {
        kotlinx.coroutines.e.a(LifecycleOwnerKt.getLifecycleScope(this), null, null, new b(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CategoryReorderActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u001a\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004 \u0005*\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0006"}, d2 = {"<anonymous>", "", "it", "", "Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class i<T> implements Observer<List<? extends ISmartHomeModel>> {
        i() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: CategoryReorderActivity.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "com.xiaomi.smarthome.ui.CategoryReorderActivity$observeData$1$1", f = "CategoryReorderActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.xiaomi.smarthome.ui.CategoryReorderActivity$i$1  reason: invalid class name */
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
                    CategoryReorderActivity.this.d();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }

        /* renamed from: a */
        public final void onChanged(List<? extends ISmartHomeModel> list) {
            kotlinx.coroutines.e.a(LifecycleOwnerKt.getLifecycleScope(CategoryReorderActivity.this), null, null, new AnonymousClass1(null), 3, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.smarthome.base.BaseActivity
    public void observeData() {
        c().getDeviceList().observeForever(new i());
    }
}
