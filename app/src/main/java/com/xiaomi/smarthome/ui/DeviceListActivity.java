package com.xiaomi.smarthome.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.NetworkUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xiaomi.mico.base.utils.NetworkUtil;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimationManager;
import com.xiaomi.micolauncher.common.utils.AnimationUtils;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.base.BaseActivity;
import com.xiaomi.smarthome.core.db.Db;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.IDevice;
import com.xiaomi.smarthome.core.entity.TypeGroup;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;
import com.xiaomi.smarthome.core.utils.ViewModelUtils;
import com.xiaomi.smarthome.databinding.ActivityDeviceListBinding;
import com.xiaomi.smarthome.ui.ConfirmDialog;
import com.xiaomi.smarthome.ui.adapter.GroupAdapter;
import com.xiaomi.smarthome.ui.adapter.PageLocation;
import com.xiaomi.smarthome.ui.vm.DeviceListViewModel;
import com.xiaomi.smarthome.ui.widgets.ItemTouchCallBack;
import io.reactivex.rxjava3.functions.Consumer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeviceListActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 <2\u00020\u0001:\u0002<=B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010)\u001a\u00020*H\u0002J\b\u0010+\u001a\u00020*H\u0002J\b\u0010,\u001a\u00020*H\u0002J\b\u0010-\u001a\u00020*H\u0002J\b\u0010.\u001a\u00020*H\u0015J\b\u0010/\u001a\u00020*H\u0014J\u0012\u00100\u001a\u00020*2\b\u00101\u001a\u0004\u0018\u000102H\u0014J\b\u00103\u001a\u00020*H\u0014J\u001a\u00104\u001a\u00020\u00102\u0006\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u000108H\u0016J\u0010\u00109\u001a\u00020*2\u0006\u0010:\u001a\u000202H\u0015J\b\u0010;\u001a\u00020*H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0012@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u001a0\u0019j\b\u0012\u0004\u0012\u00020\u001a`\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001c\u001a\u00020\u001d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\b\u001a\u0004\b\u001e\u0010\u001fR\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010#\u001a\n %*\u0004\u0018\u00010$0$8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\b\u001a\u0004\b&\u0010'¨\u0006>"}, d2 = {"Lcom/xiaomi/smarthome/ui/DeviceListActivity;", "Lcom/xiaomi/smarthome/base/BaseActivity;", "()V", "adapter", "Lcom/xiaomi/smarthome/ui/adapter/GroupAdapter;", "getAdapter", "()Lcom/xiaomi/smarthome/ui/adapter/GroupAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "binding", "Lcom/xiaomi/smarthome/databinding/ActivityDeviceListBinding;", "data", "Lcom/xiaomi/smarthome/core/entity/TypeGroup;", "dialog", "Lcom/xiaomi/smarthome/ui/ConfirmDialog;", "isX6A", "", com.xiaomi.onetrack.api.b.p, "Lcom/xiaomi/smarthome/ui/DeviceListActivity$Mode;", "mode", "setMode", "(Lcom/xiaomi/smarthome/ui/DeviceListActivity$Mode;)V", "regionId", "", "tempDeviceList", "Ljava/util/ArrayList;", "Lcom/xiaomi/smarthome/core/entity/IDevice;", "Lkotlin/collections/ArrayList;", "touchCallBack", "Lcom/xiaomi/smarthome/ui/widgets/ItemTouchCallBack;", "getTouchCallBack", "()Lcom/xiaomi/smarthome/ui/widgets/ItemTouchCallBack;", "touchCallBack$delegate", "touchHelper", "Landroidx/recyclerview/widget/ItemTouchHelper;", "vm", "Lcom/xiaomi/smarthome/ui/vm/DeviceListViewModel;", "kotlin.jvm.PlatformType", "getVm", "()Lcom/xiaomi/smarthome/ui/vm/DeviceListViewModel;", "vm$delegate", "addTouchHelper", "", "doCancel", "doSave", "initConfirmDialog", "initViews", "observeData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onKeyDown", "keyCode", "", "event", "Landroid/view/KeyEvent;", "onSaveInstanceState", "outState", "removeTouchHelper", "Companion", "Mode", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class DeviceListActivity extends BaseActivity {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    public static final String EXTRA_DATA = "extra_data";
    @NotNull
    public static final String EXTRA_REGION_ID = "extra_region_id";
    @NotNull
    public static final String TAG = "DeviceListActivity:";
    private ActivityDeviceListBinding a;
    private TypeGroup b;
    private String c;
    private ItemTouchHelper f;
    private ConfirmDialog k;
    private final Lazy d = LazyKt.lazy(p.a);
    private final Lazy e = LazyKt.lazy(new q());
    private final ArrayList<IDevice> g = new ArrayList<>();
    private final boolean h = Hardware.isX6A();
    private Mode i = Mode.ModeNormal;
    private final Lazy j = LazyKt.lazy(new a());

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lcom/xiaomi/smarthome/ui/DeviceListActivity$Mode;", "", "(Ljava/lang/String;I)V", "ModeNormal", "ModeEdit", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public enum Mode {
        ModeNormal,
        ModeEdit
    }

    private final ItemTouchCallBack a() {
        return (ItemTouchCallBack) this.d.getValue();
    }

    public final DeviceListViewModel b() {
        return (DeviceListViewModel) this.e.getValue();
    }

    public final GroupAdapter c() {
        return (GroupAdapter) this.j.getValue();
    }

    public static final /* synthetic */ ActivityDeviceListBinding access$getBinding$p(DeviceListActivity deviceListActivity) {
        ActivityDeviceListBinding activityDeviceListBinding = deviceListActivity.a;
        if (activityDeviceListBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }
        return activityDeviceListBinding;
    }

    public static final /* synthetic */ TypeGroup access$getData$p(DeviceListActivity deviceListActivity) {
        TypeGroup typeGroup = deviceListActivity.b;
        if (typeGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
        }
        return typeGroup;
    }

    public static final /* synthetic */ String access$getRegionId$p(DeviceListActivity deviceListActivity) {
        String str = deviceListActivity.c;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("regionId");
        }
        return str;
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/ui/widgets/ItemTouchCallBack;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class p extends Lambda implements Function0<ItemTouchCallBack> {
        public static final p a = new p();

        p() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final ItemTouchCallBack invoke() {
            return new ItemTouchCallBack();
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/ui/vm/DeviceListViewModel;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class q extends Lambda implements Function0<DeviceListViewModel> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        q() {
            super(0);
            DeviceListActivity.this = r1;
        }

        /* renamed from: a */
        public final DeviceListViewModel invoke() {
            return (DeviceListViewModel) ViewModelUtils.obtainViewModel(DeviceListActivity.this, DeviceListViewModel.class);
        }
    }

    public final void a(Mode mode) {
        if (this.i != mode) {
            ActivityDeviceListBinding activityDeviceListBinding = this.a;
            if (activityDeviceListBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            }
            activityDeviceListBinding.setMode(mode);
            switch (mode) {
                case ModeNormal:
                    ActivityDeviceListBinding activityDeviceListBinding2 = this.a;
                    if (activityDeviceListBinding2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    }
                    RecyclerView recyclerView = activityDeviceListBinding2.rvDeviceList;
                    Intrinsics.checkNotNullExpressionValue(recyclerView, "binding.rvDeviceList");
                    ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
                    if (layoutParams != null) {
                        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
                        layoutParams2.width = -2;
                        layoutParams2.height = -1;
                        layoutParams2.topMargin = getResources().getDimensionPixelSize(R.dimen.device_list_margin_top);
                        layoutParams2.bottomMargin = getResources().getDimensionPixelSize(R.dimen.device_list_margin_bottom);
                        layoutParams2.setMarginStart(getResources().getDimensionPixelSize(R.dimen.device_list_recycler_view_margin_start));
                        ActivityDeviceListBinding activityDeviceListBinding3 = this.a;
                        if (activityDeviceListBinding3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                        }
                        RecyclerView recyclerView2 = activityDeviceListBinding3.rvDeviceList;
                        Intrinsics.checkNotNullExpressionValue(recyclerView2, "binding.rvDeviceList");
                        recyclerView2.setLayoutParams(layoutParams2);
                        h();
                        MicoAnimationManager.getManager().changeToNormalMode();
                        break;
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
                    }
                case ModeEdit:
                    ActivityDeviceListBinding activityDeviceListBinding4 = this.a;
                    if (activityDeviceListBinding4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    }
                    RecyclerView recyclerView3 = activityDeviceListBinding4.rvDeviceList;
                    Intrinsics.checkNotNullExpressionValue(recyclerView3, "binding.rvDeviceList");
                    ViewGroup.LayoutParams layoutParams3 = recyclerView3.getLayoutParams();
                    if (layoutParams3 != null) {
                        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) layoutParams3;
                        layoutParams4.width = -1;
                        layoutParams4.height = getResources().getDimensionPixelSize(R.dimen.device_list_margin_height);
                        layoutParams4.topMargin = getResources().getDimensionPixelSize(R.dimen.device_list_order_margin_top);
                        layoutParams4.bottomMargin = getResources().getDimensionPixelSize(R.dimen.device_list_order_margin_bottom);
                        layoutParams4.setMarginStart(getResources().getDimensionPixelSize(R.dimen.device_list_recycler_view_order_margin_start));
                        ActivityDeviceListBinding activityDeviceListBinding5 = this.a;
                        if (activityDeviceListBinding5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("binding");
                        }
                        RecyclerView recyclerView4 = activityDeviceListBinding5.rvDeviceList;
                        Intrinsics.checkNotNullExpressionValue(recyclerView4, "binding.rvDeviceList");
                        recyclerView4.setLayoutParams(layoutParams4);
                        this.g.clear();
                        this.g.addAll(c().getList());
                        ToastUtil.showToast(getString(R.string.device_list_draggable_tip));
                        g();
                        MicoAnimationManager.getManager().changeToEditMode();
                        break;
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
                    }
            }
            c().setEditMode(mode == Mode.ModeEdit);
            this.i = mode;
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/ui/adapter/GroupAdapter;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a extends Lambda implements Function0<GroupAdapter> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a() {
            super(0);
            DeviceListActivity.this = r1;
        }

        @NotNull
        /* renamed from: a */
        public final GroupAdapter invoke() {
            return new GroupAdapter(DeviceListActivity.access$getData$p(DeviceListActivity.this).getCategory(), CollectionsKt.toMutableList((Collection) DeviceListActivity.access$getData$p(DeviceListActivity.this).getDeviceList()), DeviceListActivity.access$getData$p(DeviceListActivity.this).getShowCountInfFirstPage(), PageLocation.PAGE_SECOND);
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/smarthome/ui/DeviceListActivity$Companion;", "", "()V", "EXTRA_DATA", "", "EXTRA_REGION_ID", "TAG", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.xiaomi.smarthome.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            Parcelable parcelable = bundle.getParcelable("extra_data");
            Intrinsics.checkNotNull(parcelable);
            this.b = (TypeGroup) parcelable;
            String string = bundle.getString(EXTRA_REGION_ID);
            if (string == null) {
                string = "";
            }
            this.c = string;
        } else {
            Intent intent = getIntent();
            Intrinsics.checkNotNullExpressionValue(intent, "intent");
            Bundle extras = intent.getExtras();
            Intrinsics.checkNotNull(extras);
            Parcelable parcelable2 = extras.getParcelable("extra_data");
            Intrinsics.checkNotNull(parcelable2);
            this.b = (TypeGroup) parcelable2;
            Intent intent2 = getIntent();
            Intrinsics.checkNotNullExpressionValue(intent2, "intent");
            Bundle extras2 = intent2.getExtras();
            Intrinsics.checkNotNull(extras2);
            String string2 = extras2.getString(EXTRA_REGION_ID);
            if (string2 == null) {
                string2 = "";
            }
            this.c = string2;
        }
        ViewDataBinding contentView = DataBindingUtil.setContentView(this, R.layout.activity_device_list);
        Intrinsics.checkNotNullExpressionValue(contentView, "DataBindingUtil.setConte…out.activity_device_list)");
        this.a = (ActivityDeviceListBinding) contentView;
        observeData();
        initViews();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    @SuppressLint({"MissingSuperCall"})
    public void onSaveInstanceState(@NotNull Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        TypeGroup typeGroup = this.b;
        if (typeGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
        }
        outState.putParcelable("extra_data", typeGroup);
        String str = this.c;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("regionId");
        }
        outState.putString("extra_data", str);
    }

    @Override // com.xiaomi.smarthome.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ConfirmDialog confirmDialog = this.k;
        if (confirmDialog != null) {
            confirmDialog.dismiss();
        }
        this.k = null;
        MicoAnimationManager.getManager().clearViewLists();
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x038b  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x03c7  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x03e9  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0448  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0219  */
    @Override // com.xiaomi.smarthome.base.BaseActivity
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initViews() {
        /*
            Method dump skipped, instructions count: 1184
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.ui.DeviceListActivity.initViews():void");
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroid/view/View;", "kotlin.jvm.PlatformType", "motionEvent", "Landroid/view/MotionEvent;", "onTouch"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class e implements View.OnTouchListener {
        e() {
            DeviceListActivity.this = r1;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            Intrinsics.checkNotNullExpressionValue(motionEvent, "motionEvent");
            int action = motionEvent.getAction();
            if (action != 3) {
                switch (action) {
                    case 0:
                        AnimationUtils.titleIconTouchDown(DeviceListActivity.access$getBinding$p(DeviceListActivity.this).ivBackCircleBg);
                        return false;
                    case 1:
                        break;
                    default:
                        return false;
                }
            }
            AnimationUtils.titleIconTouchUp(DeviceListActivity.access$getBinding$p(DeviceListActivity.this).ivBackCircleBg);
            return false;
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class h<T> implements Consumer<Unit> {
        h() {
            DeviceListActivity.this = r1;
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            DeviceListActivity.this.finish();
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroid/view/View;", "kotlin.jvm.PlatformType", "motionEvent", "Landroid/view/MotionEvent;", "onTouch"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class i implements View.OnTouchListener {
        i() {
            DeviceListActivity.this = r1;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            Intrinsics.checkNotNullExpressionValue(motionEvent, "motionEvent");
            int action = motionEvent.getAction();
            if (action != 3) {
                switch (action) {
                    case 0:
                        AnimationUtils.titleIconTouchDown(DeviceListActivity.access$getBinding$p(DeviceListActivity.this).ivEditCircleBg);
                        return false;
                    case 1:
                        break;
                    default:
                        return false;
                }
            }
            AnimationUtils.titleIconTouchUp(DeviceListActivity.access$getBinding$p(DeviceListActivity.this).ivEditCircleBg);
            return false;
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class j<T> implements Consumer<Unit> {
        j() {
            DeviceListActivity.this = r1;
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            DeviceListActivity.this.a(Mode.ModeEdit);
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class k<T> implements Consumer<Unit> {
        k() {
            DeviceListActivity.this = r1;
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            if (!NetworkUtils.isConnected()) {
                ToastUtil.showToast(R.string.tip_network_is_broken);
            } else {
                DeviceListActivity.this.b().patchOperating(DeviceListActivity.this.c().getCategory(), DeviceListActivity.this.c().getList(), false);
            }
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class l<T> implements Consumer<Unit> {
        l() {
            DeviceListActivity.this = r1;
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            if (!NetworkUtils.isConnected()) {
                ToastUtil.showToast(R.string.tip_network_is_broken);
            } else {
                DeviceListActivity.this.b().patchOperating(DeviceListActivity.this.c().getCategory(), DeviceListActivity.this.c().getList(), true);
            }
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", OneTrack.Event.VIEW, "Landroid/view/View;", "kotlin.jvm.PlatformType", "motionEvent", "Landroid/view/MotionEvent;", "onTouch"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class m implements View.OnTouchListener {
        public static final m a = new m();

        m() {
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            Intrinsics.checkNotNullExpressionValue(motionEvent, "motionEvent");
            int action = motionEvent.getAction();
            if (action != 3) {
                switch (action) {
                    case 0:
                        AnimationUtils.titleTouchDown(view);
                        return false;
                    case 1:
                        break;
                    default:
                        return false;
                }
            }
            AnimationUtils.titleTouchUp(view);
            return false;
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class n<T> implements Consumer<Unit> {
        n() {
            DeviceListActivity.this = r1;
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            DeviceListActivity.this.e();
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroid/view/View;", "kotlin.jvm.PlatformType", "motionEvent", "Landroid/view/MotionEvent;", "onTouch"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class f implements View.OnTouchListener {
        f() {
            DeviceListActivity.this = r1;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            Intrinsics.checkNotNullExpressionValue(motionEvent, "motionEvent");
            int action = motionEvent.getAction();
            if (action != 3) {
                switch (action) {
                    case 0:
                        AnimationUtils.titleIconTouchDown(DeviceListActivity.access$getBinding$p(DeviceListActivity.this).ivBackCircleBg);
                        return false;
                    case 1:
                        break;
                    default:
                        return false;
                }
            }
            AnimationUtils.titleIconTouchUp(DeviceListActivity.access$getBinding$p(DeviceListActivity.this).ivBackCircleBg);
            return false;
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class g<T> implements Consumer<Unit> {
        g() {
            DeviceListActivity.this = r1;
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            if (Mode.ModeEdit != DeviceListActivity.this.i) {
                return;
            }
            if (DeviceListActivity.this.c().isDataChange()) {
                ConfirmDialog confirmDialog = DeviceListActivity.this.k;
                if (confirmDialog != null) {
                    confirmDialog.show();
                    return;
                }
                return;
            }
            DeviceListActivity.this.d();
        }
    }

    public final void d() {
        if (!this.g.isEmpty()) {
            c().getList().clear();
            c().getList().addAll(this.g);
            c().notifyDataSetChanged();
        }
        a(Mode.ModeNormal);
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.xiaomi.smarthome.ui.DeviceListActivity$doSave$1", f = "DeviceListActivity.kt", i = {}, l = {371}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes4.dex */
    public static final class b extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(Continuation continuation) {
            super(2, continuation);
            DeviceListActivity.this = r1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new b(completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((b) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* compiled from: DeviceListActivity.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "com.xiaomi.smarthome.ui.DeviceListActivity$doSave$1$1", f = "DeviceListActivity.kt", i = {}, l = {384}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.xiaomi.smarthome.ui.DeviceListActivity$b$1 */
        /* loaded from: classes4.dex */
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(Continuation continuation) {
                super(2, continuation);
                b.this = r1;
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
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        int mutableDividerIndex = DeviceListActivity.this.c().getMutableDividerIndex();
                        List<DeviceInfoBean> list = CollectionsKt.toList(CollectionsKt.filterIsInstance(DeviceListActivity.this.c().getList(), DeviceInfoBean.class));
                        int size = list.size();
                        int i = 0;
                        while (true) {
                            int i2 = 1;
                            if (i < size) {
                                DeviceInfoBean deviceInfoBean = list.get(i);
                                if (i >= mutableDividerIndex) {
                                    i2 = 0;
                                }
                                deviceInfoBean.setShowInFirstPage(i2);
                                i++;
                            } else {
                                Db.Companion.getInstance().rankDao().insertDeviceRankRecords(list, DeviceListActivity.access$getRegionId$p(DeviceListActivity.this), DeviceListActivity.access$getData$p(DeviceListActivity.this).getCategory().getTypeName());
                                Ref.IntRef intRef = new Ref.IntRef();
                                intRef.element = Db.Companion.getInstance().rankDao().getFirstPageShowCount(DeviceListActivity.access$getRegionId$p(DeviceListActivity.this), DeviceListActivity.access$getData$p(DeviceListActivity.this).getCategory().getTypeName());
                                this.label = 1;
                                if (BuildersKt.withContext(Dispatchers.getMain(), new C01901(intRef, null), this) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
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

            /* compiled from: DeviceListActivity.kt */
            @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
            @DebugMetadata(c = "com.xiaomi.smarthome.ui.DeviceListActivity$doSave$1$1$1", f = "DeviceListActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
            /* renamed from: com.xiaomi.smarthome.ui.DeviceListActivity$b$1$1 */
            /* loaded from: classes4.dex */
            public static final class C01901 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ Ref.IntRef $newCount;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C01901(Ref.IntRef intRef, Continuation continuation) {
                    super(2, continuation);
                    AnonymousClass1.this = r1;
                    this.$newCount = intRef;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                @NotNull
                public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                    Intrinsics.checkNotNullParameter(completion, "completion");
                    return new C01901(this.$newCount, completion);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((C01901) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                @Nullable
                public final Object invokeSuspend(@NotNull Object obj) {
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label == 0) {
                        ResultKt.throwOnFailure(obj);
                        LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_REFRESH).post(Boxing.boxBoolean(false));
                        DeviceListActivity.this.c().setShowCountInFirstPage(this.$newCount.element);
                        DeviceListActivity.this.a(Mode.ModeNormal);
                        return Unit.INSTANCE;
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
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
                    if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(null), this) == coroutine_suspended) {
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

    public final void e() {
        kotlinx.coroutines.e.a(LifecycleOwnerKt.getLifecycleScope(this), null, null, new b(null), 3, null);
    }

    private final void f() {
        this.k = new ConfirmDialog(this).setOnRightClickListener(new c()).setOnLeftClickListener(new d()).setCancelable(true);
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onRightClick"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class c implements ConfirmDialog.OnRightClickListener {
        c() {
            DeviceListActivity.this = r1;
        }

        @Override // com.xiaomi.smarthome.ui.ConfirmDialog.OnRightClickListener
        public final void onRightClick(View view) {
            DeviceListActivity.this.e();
        }
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onLeftClick"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class d implements ConfirmDialog.OnLeftClickListener {
        d() {
            DeviceListActivity.this = r1;
        }

        @Override // com.xiaomi.smarthome.ui.ConfirmDialog.OnLeftClickListener
        public final void onLeftClick(View view) {
            DeviceListActivity.this.d();
        }
    }

    @Override // com.xiaomi.smarthome.base.BaseActivity
    public void observeData() {
        super.observeData();
        LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_OPEN_CARD).observe(this, new o());
    }

    /* compiled from: DeviceListActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class o<T> implements Observer<Object> {
        o() {
            DeviceListActivity.this = r1;
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            Lifecycle lifecycle = DeviceListActivity.this.getLifecycle();
            Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
            if (!lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                return;
            }
            if (!NetworkUtil.isNetworkConnected(DeviceListActivity.this)) {
                ToastUtil.showToast(R.string.tip_network_is_broken);
            } else if (obj != null) {
                Map map = (Map) obj;
                Object obj2 = map.get(OneTrack.Event.VIEW);
                if (obj2 != null) {
                    View view = (View) obj2;
                    Object obj3 = map.get("item");
                    if (obj3 != null) {
                        DeviceInfoWrapper deviceInfoWrapper = (DeviceInfoWrapper) obj3;
                        if (deviceInfoWrapper.getDeviceInfo().isOnline) {
                            MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
                            Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
                            instance.getDeviceHelper().onDevicePageShow(view, deviceInfoWrapper.getDeviceInfo());
                            return;
                        }
                        ToastUtil.showToast(R.string.miot_device_off_line);
                        return;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type com.xiaomi.miot.support.category.DeviceInfoWrapper");
                }
                throw new NullPointerException("null cannot be cast to non-null type android.view.View");
            } else {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<*, *>");
            }
        }
    }

    private final void g() {
        if (this.f == null) {
            a().setListener(c());
            this.f = new ItemTouchHelper(a());
            GroupAdapter c2 = c();
            ItemTouchHelper itemTouchHelper = this.f;
            if (itemTouchHelper != null) {
                c2.setItemTouchHelper(itemTouchHelper);
                ItemTouchHelper itemTouchHelper2 = this.f;
                if (itemTouchHelper2 != null) {
                    ActivityDeviceListBinding activityDeviceListBinding = this.a;
                    if (activityDeviceListBinding == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    }
                    itemTouchHelper2.attachToRecyclerView(activityDeviceListBinding.rvDeviceList);
                    return;
                }
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type androidx.recyclerview.widget.ItemTouchHelper");
        }
    }

    private final void h() {
        if (this.f != null) {
            try {
                Method method = ItemTouchHelper.class.getDeclaredMethod("e", new Class[0]);
                Intrinsics.checkNotNullExpressionValue(method, "method");
                method.setAccessible(true);
                method.invoke(this.f, new Object[0]);
                this.f = null;
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
        if (i2 != 4 || this.i != Mode.ModeEdit) {
            return super.onKeyDown(i2, keyEvent);
        }
        ConfirmDialog confirmDialog = this.k;
        if (confirmDialog != null && confirmDialog.isShowing()) {
            ConfirmDialog confirmDialog2 = this.k;
            if (confirmDialog2 == null) {
                return true;
            }
            confirmDialog2.dismiss();
            return true;
        } else if (c().isDataChange()) {
            ConfirmDialog confirmDialog3 = this.k;
            if (confirmDialog3 == null) {
                return true;
            }
            confirmDialog3.show();
            return true;
        } else {
            d();
            return true;
        }
    }
}
