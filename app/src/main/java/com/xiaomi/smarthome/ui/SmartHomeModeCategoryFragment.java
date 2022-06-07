package com.xiaomi.smarthome.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.dinuscxj.itemdecoration.LinearOffsetsItemDecoration;
import com.elvishew.xlog.Logger;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xiaomi.mico.base.exts.ViewExtKt;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.NetworkUtil;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4BigButton;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.common.widget.RefreshCallBack;
import com.xiaomi.micolauncher.common.widget.RefreshLayout;
import com.xiaomi.micolauncher.module.homepage.event.MainPageMaskEvent;
import com.xiaomi.micolauncher.module.homepage.event.ResetScrollViewPositionEvent;
import com.xiaomi.miot.scene.CommonUsedScene;
import com.xiaomi.miot.support.IMiotMsgCallback;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.miot.support.ui.MiotDeviceHelper;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.base.BaseSmartHomeFragment;
import com.xiaomi.smarthome.core.entity.MediaType;
import com.xiaomi.smarthome.core.entity.MicoMediaData;
import com.xiaomi.smarthome.core.entity.TypeGroup;
import com.xiaomi.smarthome.core.miot.MediaControllerEvent;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;
import com.xiaomi.smarthome.core.utils.ViewModelUtils;
import com.xiaomi.smarthome.databinding.FragmentSmartHomeModeCategoryBinding;
import com.xiaomi.smarthome.ui.adapter.CategoryModeDeviceListAdapter;
import com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter;
import com.xiaomi.smarthome.ui.media.RelayMediaListener;
import com.xiaomi.smarthome.ui.media.RelayMediaManger;
import com.xiaomi.smarthome.ui.model.CategoryScene;
import com.xiaomi.smarthome.ui.model.ISmartHomeModel;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import com.xiaomi.smarthome.ui.vm.SmartHomeViewModel;
import com.xiaomi.smarthome.ui.widgets.MIotRegionPopupWindow;
import com.xiaomi.smarthome.ui.widgets.MicoSceneCallback;
import com.xiaomi.smarthome.ui.widgets.RegionSelectListener;
import io.netty.util.internal.StringUtil;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.e;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/* compiled from: SmartHomeModeCategoryFragment.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Ü\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 p2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u00072\u00020\b:\u0001pB\u0005¢\u0006\u0002\u0010\tJ\b\u0010?\u001a\u00020@H\u0015J\b\u0010A\u001a\u00020@H\u0014J\u0010\u0010B\u001a\u00020@2\u0006\u0010C\u001a\u00020DH\u0016J\u0012\u0010E\u001a\u00020@2\b\u0010F\u001a\u0004\u0018\u00010GH\u0016J$\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020K2\b\u0010L\u001a\u0004\u0018\u00010M2\b\u0010F\u001a\u0004\u0018\u00010GH\u0017J\u0016\u0010N\u001a\u00020@2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020/0&H\u0016J\u0010\u0010O\u001a\u00020@2\u0006\u0010P\u001a\u00020QH\u0016J\b\u0010R\u001a\u00020@H\u0016J\b\u0010S\u001a\u00020@H\u0016J\b\u0010T\u001a\u00020@H\u0016J\b\u0010U\u001a\u00020@H\u0016J\b\u0010V\u001a\u00020@H\u0016J\b\u0010W\u001a\u00020@H\u0016J\b\u0010X\u001a\u00020@H\u0016J\b\u0010Y\u001a\u00020@H\u0016J\b\u0010Z\u001a\u00020@H\u0016J\u0012\u0010[\u001a\u00020@2\b\u0010\\\u001a\u0004\u0018\u00010/H\u0016J\"\u0010]\u001a\u00020@2\u0006\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020Q2\b\u0010a\u001a\u0004\u0018\u00010_H\u0016J \u0010b\u001a\u00020@2\u0006\u0010^\u001a\u00020_2\u000e\u0010c\u001a\n\u0012\u0004\u0012\u00020d\u0018\u00010&H\u0016J\b\u0010e\u001a\u00020@H\u0016J\u0010\u0010f\u001a\u00020@2\u0006\u00105\u001a\u000206H\u0007J\b\u0010g\u001a\u00020@H\u0016J\u0010\u0010h\u001a\u00020@2\u0006\u0010i\u001a\u00020(H\u0002J\b\u0010j\u001a\u00020@H\u0016J\b\u0010k\u001a\u00020@H\u0016J\b\u0010l\u001a\u00020@H\u0016J\u0010\u0010m\u001a\u00020@2\u0006\u0010n\u001a\u00020oH\u0007R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0017\u001a\u00020\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u000f\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001e\"\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00140&X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010)\u001a\u0004\u0018\u00010(2\b\u0010'\u001a\u0004\u0018\u00010(@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b*\u0010+R\u000e\u0010,\u001a\u00020-X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010/X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u00100\u001a\u0002018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\u000f\u001a\u0004\b2\u00103R\u0010\u00105\u001a\u0004\u0018\u000106X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u000108X\u0082\u000e¢\u0006\u0002\n\u0000R#\u00109\u001a\n ;*\u0004\u0018\u00010:0:8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b>\u0010\u000f\u001a\u0004\b<\u0010=¨\u0006q"}, d2 = {"Lcom/xiaomi/smarthome/ui/SmartHomeModeCategoryFragment;", "Lcom/xiaomi/smarthome/base/BaseSmartHomeFragment;", "Lcom/xiaomi/miot/support/ui/MiotDeviceHelper$DevicesStatusCallback;", "Lcom/xiaomi/smarthome/core/miot/MicoMiotDeviceManager$MicoDeviceChangeListener;", "Lcom/xiaomi/smarthome/ui/widgets/MicoSceneCallback;", "Lcom/xiaomi/miot/support/MiotManager$IDeviceInfoReadyCallback;", "Lcom/xiaomi/smarthome/ui/media/RelayMediaListener;", "Lcom/xiaomi/miot/support/MiotManager$IMiotServiceStatusListener;", "Lcom/xiaomi/micolauncher/common/widget/RefreshCallBack;", "()V", "binding", "Lcom/xiaomi/smarthome/databinding/FragmentSmartHomeModeCategoryBinding;", "getBinding", "()Lcom/xiaomi/smarthome/databinding/FragmentSmartHomeModeCategoryBinding;", "binding$delegate", "Lkotlin/Lazy;", "categoryScene", "Lcom/xiaomi/smarthome/ui/model/CategoryScene;", "deviceList", "", "Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "deviceListAdapter", "Lcom/xiaomi/smarthome/ui/adapter/CategoryModeDeviceListAdapter;", "iMiotMsgCallback", "Lcom/xiaomi/miot/support/IMiotMsgCallback;", "getIMiotMsgCallback", "()Lcom/xiaomi/miot/support/IMiotMsgCallback;", "iMiotMsgCallback$delegate", "isAllInfoReady", "", "()Z", "setAllInfoReady", "(Z)V", "isLeftRefresh", "isServiceConnected", "lastTimestamp", "", "list", "", com.xiaomi.onetrack.api.b.p, "Lcom/xiaomi/smarthome/ui/adapter/MiotRegionAdapter$MiotRegion;", "mCurRegion", "setMCurRegion", "(Lcom/xiaomi/smarthome/ui/adapter/MiotRegionAdapter$MiotRegion;)V", "mOnRefreshingRunnable", "Ljava/lang/Runnable;", "mediaData", "Lcom/xiaomi/smarthome/core/entity/MicoMediaData;", "miotHelper", "Lcom/xiaomi/miot/support/ui/MiotDeviceHelper;", "getMiotHelper", "()Lcom/xiaomi/miot/support/ui/MiotDeviceHelper;", "miotHelper$delegate", "musicState", "Lcom/xiaomi/smarthome/core/miot/MediaControllerEvent$MusicState;", "popupWindow", "Lcom/xiaomi/smarthome/ui/widgets/MIotRegionPopupWindow;", "vm", "Lcom/xiaomi/smarthome/ui/vm/SmartHomeViewModel;", "kotlin.jvm.PlatformType", "getVm", "()Lcom/xiaomi/smarthome/ui/vm/SmartHomeViewModel;", "vm$delegate", "initViews", "", "observeData", "onAttach", com.umeng.analytics.pro.c.R, "Landroid/content/Context;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDataChanged", "onDataSizeChange", "size", "", "onDestroy", "onDestroyView", "onDetach", "onDeviceChanged", "onDeviceEvent", "onDeviceInfoReady", "onDevicesFailed", "onDevicesRefreshed", "onEventsUpdated", "onFirstDataChanged", "data", "onGetSceneFailed", "homeid", "", "errorCode", "errorInfo", "onGetSceneSuccess", "sceneList", "Lcom/xiaomi/miot/scene/CommonUsedScene;", "onPause", "onPlayStateChanged", "onRefreshing", "onRegionSelected", "region", "onResume", "onServiceConnect", "onServiceDisconnect", "resetRecyclerViewPosition", "event", "Lcom/xiaomi/micolauncher/module/homepage/event/ResetScrollViewPositionEvent;", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class SmartHomeModeCategoryFragment extends BaseSmartHomeFragment implements RefreshCallBack, MiotManager.IDeviceInfoReadyCallback, MiotManager.IMiotServiceStatusListener, MiotDeviceHelper.DevicesStatusCallback, MicoMiotDeviceManager.MicoDeviceChangeListener, RelayMediaListener, MicoSceneCallback {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    public static final String EVENT_JUMP_TO_CATEGORY_LIST = "event_jump_to_category_list";
    @NotNull
    public static final String EVENT_JUMP_TO_DEVICE_LIST = "event_jump_device";
    @NotNull
    public static final String EVENT_JUMP_TO_MEDIA_LIST = "event_jump_media";
    @NotNull
    public static final String EVENT_JUMP_TO_USER_GUIDE = "event_jump_to_user_guide";
    @NotNull
    public static final String EVENT_MODE_CHANGED = "event_mode_changed";
    @NotNull
    public static final String EVENT_OPEN_CARD = "event_open_card";
    @NotNull
    public static final String EVENT_REFRESH = "event_refresh";
    @NotNull
    public static final String EVENT_SCENE_CHANGED = "event_scene_changed";
    @NotNull
    public static final String TAG = "SmartHomePageFragment";
    private List<? extends ISmartHomeModel> b;
    private MicoMediaData c;
    private CategoryScene d;
    private MediaControllerEvent.MusicState e;
    private boolean f;
    private MIotRegionPopupWindow l;
    private MiotRegionAdapter.MiotRegion m;
    private long o;
    private volatile boolean p;
    private final Lazy a = LazyKt.lazy(new a());
    private volatile boolean g = true;
    private final Runnable h = new e();
    private final Lazy i = LazyKt.lazy(new s());
    private final List<ISmartHomeModel> j = new ArrayList();
    private final CategoryModeDeviceListAdapter k = new CategoryModeDeviceListAdapter(this.j);
    private final Lazy n = LazyKt.lazy(new f());
    private final Lazy q = LazyKt.lazy(new b());

    /* JADX INFO: Access modifiers changed from: private */
    public final FragmentSmartHomeModeCategoryBinding a() {
        return (FragmentSmartHomeModeCategoryBinding) this.a.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SmartHomeViewModel b() {
        return (SmartHomeViewModel) this.i.getValue();
    }

    private final MiotDeviceHelper c() {
        return (MiotDeviceHelper) this.n.getValue();
    }

    private final IMiotMsgCallback d() {
        return (IMiotMsgCallback) this.q.getValue();
    }

    @Override // com.xiaomi.smarthome.ui.media.RelayMediaListener
    public void onDataChanged(@NotNull List<MicoMediaData> list) {
        Intrinsics.checkNotNullParameter(list, "list");
    }

    @Override // com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager.MicoDeviceChangeListener
    public void onDeviceEvent() {
    }

    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/databinding/FragmentSmartHomeModeCategoryBinding;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a extends Lambda implements Function0<FragmentSmartHomeModeCategoryBinding> {
        a() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final FragmentSmartHomeModeCategoryBinding invoke() {
            return FragmentSmartHomeModeCategoryBinding.inflate(SmartHomeModeCategoryFragment.this.getLayoutInflater());
        }
    }

    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class e implements Runnable {
        e() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (SmartHomeModeCategoryFragment.this.f) {
                SmartHomeModeCategoryFragment.this.a().rvRefreshLayout.onRefreshComplete();
                SmartHomeModeCategoryFragment.this.f = false;
            }
        }
    }

    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/ui/vm/SmartHomeViewModel;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class s extends Lambda implements Function0<SmartHomeViewModel> {
        s() {
            super(0);
        }

        /* renamed from: a */
        public final SmartHomeViewModel invoke() {
            return (SmartHomeViewModel) ViewModelUtils.obtainViewModel(SmartHomeModeCategoryFragment.this, SmartHomeViewModel.class);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a(MiotRegionAdapter.MiotRegion miotRegion) {
        if (miotRegion != null) {
            String regionId = miotRegion.getRegionId();
            MiotRegionAdapter.MiotRegion miotRegion2 = this.m;
            if (!regionId.equals(miotRegion2 != null ? miotRegion2.getRegionId() : null)) {
                this.m = miotRegion;
                this.d = null;
                L.smarthome.d("region has changed,need reload devices");
                b().loadDevicesByRegion(miotRegion, this.d, this.c, this.p);
                if (miotRegion instanceof MiotRegionAdapter.MiotRegionHome) {
                    MicoMiotDeviceManager.getInstance().getScenesByHomeId(((MiotRegionAdapter.MiotRegionHome) miotRegion).getQueryHomeId(), this);
                }
                TextView textView = a().tvFamilyInfo;
                Intrinsics.checkNotNullExpressionValue(textView, "binding.tvFamilyInfo");
                textView.setText(miotRegion.getRegionName());
            }
        }
    }

    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/miot/support/ui/MiotDeviceHelper;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class f extends Lambda implements Function0<MiotDeviceHelper> {
        f() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final MiotDeviceHelper invoke() {
            return new MiotDeviceHelper(SmartHomeModeCategoryFragment.this.context);
        }
    }

    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\r\u0010\r\u001a\u00020\u000eH\u0001¢\u0006\u0002\b\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/xiaomi/smarthome/ui/SmartHomeModeCategoryFragment$Companion;", "", "()V", "EVENT_JUMP_TO_CATEGORY_LIST", "", "EVENT_JUMP_TO_DEVICE_LIST", "EVENT_JUMP_TO_MEDIA_LIST", "EVENT_JUMP_TO_USER_GUIDE", "EVENT_MODE_CHANGED", "EVENT_OPEN_CARD", "EVENT_REFRESH", "EVENT_SCENE_CHANGED", "TAG", "newInstance", "Lcom/xiaomi/smarthome/ui/SmartHomeModeCategoryFragment;", "newInstance$smarthome_release", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final SmartHomeModeCategoryFragment newInstance$smarthome_release() {
            return new SmartHomeModeCategoryFragment();
        }
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    @NotNull
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        super.onCreateView(inflater, viewGroup, bundle);
        observeData();
        initViews();
        c().registerCallback(this);
        MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
        instance.setDeviceInfoReady(false);
        MiotManager.registerDeviceInfoReadyCallback(this);
        MiotManager.registerServiceStatusListener(this);
        L.smarthome.d("registerCallback Miot");
        b().getLoading().setValue(true);
        b().loadDevicesByRegion(this.m, this.d, this.c, this.p);
        FragmentSmartHomeModeCategoryBinding binding = a();
        Intrinsics.checkNotNullExpressionValue(binding, "binding");
        View root = binding.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return root;
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        EventBusRegistry.getEventBus().register(this);
        RelayMediaManger.INSTANCE.registerListener(this);
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment
    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    protected void initViews() {
        super.initViews();
        MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
        String userId = instance.getUserId();
        int i2 = 0;
        if (userId != null) {
            TextView textView = a().tvFamilyInfo;
            Intrinsics.checkNotNullExpressionValue(textView, "binding.tvFamilyInfo");
            textView.setText(this.context.getString(R.string.miot_home_name_empty_suffix, userId));
        }
        MicoMiotDeviceManager instance2 = MicoMiotDeviceManager.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance2, "MicoMiotDeviceManager.getInstance()");
        a(instance2.getCurRegion());
        AnimLifecycleManager.repeatOnAttach(a().groupLoading, MicoAnimConfigurator4BigButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(a().groupLoading).subscribe(new c());
        a().rvRefreshLayout.setHeadMode(RefreshLayout.HeadMode.REFRESH, this);
        LinearOffsetsItemDecoration linearOffsetsItemDecoration = new LinearOffsetsItemDecoration(0);
        linearOffsetsItemDecoration.setItemOffsets(getResources().getDimensionPixelOffset(R.dimen.smart_home_item_decoration));
        HorizontalRecyclerView horizontalRecyclerView = a().rvIotDevices;
        Intrinsics.checkNotNullExpressionValue(horizontalRecyclerView, "binding.rvIotDevices");
        horizontalRecyclerView.setAdapter(this.k);
        a().rvIotDevices.addItemDecoration(linearOffsetsItemDecoration);
        HorizontalRecyclerView horizontalRecyclerView2 = a().rvIotDevices;
        Intrinsics.checkNotNullExpressionValue(horizontalRecyclerView2, "binding.rvIotDevices");
        horizontalRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        TextView textView2 = a().tvFamilyInfo;
        Intrinsics.checkNotNullExpressionValue(textView2, "binding.tvFamilyInfo");
        if (Hardware.isX6A()) {
            i2 = 8;
        }
        textView2.setVisibility(i2);
        MiotRegionAdapter.MiotRegion miotRegion = this.m;
        if (miotRegion != null && (miotRegion instanceof MiotRegionAdapter.MiotRegionHome)) {
            MicoMiotDeviceManager.getInstance().getScenesByHomeId(((MiotRegionAdapter.MiotRegionHome) miotRegion).getQueryHomeId(), this);
        }
        AnimLifecycleManager.repeatOnAttach(a().tvFamilyInfo, MicoAnimConfigurator4TabAndSwitch.get());
        Context context = this.context;
        Intrinsics.checkNotNullExpressionValue(context, "context");
        RxViewHelp.debounceClicksWithOneSeconds(a().tvFamilyInfo).subscribe(new d(context.getResources().getDimensionPixelSize(R.dimen.mico_smart_home_home_title_width)));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "accept"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class c<T> implements Consumer<Object> {
        c() {
        }

        @Override // io.reactivex.functions.Consumer
        public final void accept(Object obj) {
            SmartHomeModeCategoryFragment.this.startActivity(new Intent(SmartHomeModeCategoryFragment.this.context, UserGuideActivity.class));
            SmartHomeStatHelper.recordGuideCardClick();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "accept"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class d<T> implements Consumer<Object> {
        final /* synthetic */ int b;

        d(int i) {
            this.b = i;
        }

        @Override // io.reactivex.functions.Consumer
        public final void accept(Object obj) {
            List<MiotRegionAdapter.MiotRegion> regionList = MicoMiotDeviceManager.getInstance().getRegionList();
            if (regionList.size() > 1) {
                SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_DIS);
                SmartHomeModeCategoryFragment smartHomeModeCategoryFragment = SmartHomeModeCategoryFragment.this;
                smartHomeModeCategoryFragment.l = new MIotRegionPopupWindow(smartHomeModeCategoryFragment.context);
                MIotRegionPopupWindow mIotRegionPopupWindow = SmartHomeModeCategoryFragment.this.l;
                if (mIotRegionPopupWindow != null) {
                    mIotRegionPopupWindow.setRegionList(regionList);
                }
                MIotRegionPopupWindow mIotRegionPopupWindow2 = SmartHomeModeCategoryFragment.this.l;
                if (mIotRegionPopupWindow2 != null) {
                    mIotRegionPopupWindow2.setPopWindowWidth(this.b);
                }
                MIotRegionPopupWindow mIotRegionPopupWindow3 = SmartHomeModeCategoryFragment.this.l;
                if (mIotRegionPopupWindow3 != null) {
                    mIotRegionPopupWindow3.setOnDismissListener(AnonymousClass1.a);
                }
                MIotRegionPopupWindow mIotRegionPopupWindow4 = SmartHomeModeCategoryFragment.this.l;
                if (mIotRegionPopupWindow4 != null) {
                    mIotRegionPopupWindow4.setRegionSelectListener(new RegionSelectListener() { // from class: com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment.d.2
                        @Override // com.xiaomi.smarthome.ui.widgets.RegionSelectListener
                        public final void onRegionChanged(@NotNull MiotRegionAdapter.MiotRegion region) {
                            Intrinsics.checkNotNullParameter(region, "region");
                            SmartHomeModeCategoryFragment.this.b(region);
                            MIotRegionPopupWindow mIotRegionPopupWindow5 = SmartHomeModeCategoryFragment.this.l;
                            if (mIotRegionPopupWindow5 != null) {
                                mIotRegionPopupWindow5.dismiss();
                            }
                            MicoMiotDeviceManager.getInstance().pickRegion(region);
                        }
                    });
                }
                EventBusRegistry.getEventBus().post(new MainPageMaskEvent(true));
                MIotRegionPopupWindow mIotRegionPopupWindow5 = SmartHomeModeCategoryFragment.this.l;
                if (mIotRegionPopupWindow5 != null) {
                    mIotRegionPopupWindow5.show(SmartHomeModeCategoryFragment.this.a().tvFamilyInfo, SmartHomeModeCategoryFragment.this.getResources().getDimensionPixelSize(R.dimen.popup_window_bias_x), SmartHomeModeCategoryFragment.this.getResources().getDimensionPixelSize(R.dimen.popup_window_bias_y));
                }
            }
        }

        /* compiled from: SmartHomeModeCategoryFragment.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "onDismiss"}, k = 3, mv = {1, 4, 2})
        /* renamed from: com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment$d$1  reason: invalid class name */
        /* loaded from: classes4.dex */
        static final class AnonymousClass1 implements PopupWindow.OnDismissListener {
            public static final AnonymousClass1 a = new AnonymousClass1();

            AnonymousClass1() {
            }

            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                EventBusRegistry.getEventBus().post(new MainPageMaskEvent(false));
            }
        }
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger logger = L.smarthome;
        logger.d("SmartHomePageFragment isServiceConnected=" + this.g);
        if (!this.g) {
            MiotManager.bindMiotService();
        }
        MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
        a(instance.getCurRegion());
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        MicoMiotDeviceManager.getInstance().addListeners(this);
        MiotManager.registerMiotMsgCallback(d());
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        MicoMiotDeviceManager.getInstance().unregisterListener(this);
        MiotManager.unregisterMiotMsgCallback(d());
    }

    @Override // com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager.MicoDeviceChangeListener
    public void onDeviceChanged() {
        L.smarthome.d("onDeviceChanged");
        MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
        a(instance.getCurRegion());
        if (this.f) {
            a().rvRefreshLayout.onRefreshComplete();
            this.f = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void b(MiotRegionAdapter.MiotRegion miotRegion) {
        String str;
        Logger logger = L.smarthome;
        logger.d("onRegionSelected:" + miotRegion.getRegionName());
        a(miotRegion);
        if (miotRegion instanceof MiotRegionAdapter.MiotRegionHome) {
            str = SmartHomeStatHelper.PARAM_VALUE_HOME;
        } else {
            str = miotRegion.isCurrentSpeakerRoom() ? SmartHomeStatHelper.PARAM_VALUE_SAME_WITH_SPEAKER : SmartHomeStatHelper.PARAM_VALUE_NO_SAME_WITH_SPEAKER;
        }
        SmartHomeStatHelper.recordSmartTabDisChoose(str);
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        c().unregisterCallback();
        Threads.removeCallbacksInMainThread(this.h);
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        MIotRegionPopupWindow mIotRegionPopupWindow = this.l;
        if (mIotRegionPopupWindow != null) {
            mIotRegionPopupWindow.dismiss();
        }
        super.onPause();
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        EventBusRegistry.getEventBus().unregister(this);
        RelayMediaManger.INSTANCE.unRegisterListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onPlayStateChanged(@NotNull MediaControllerEvent.MusicState musicState) {
        Intrinsics.checkNotNullParameter(musicState, "musicState");
        this.e = musicState;
        this.k.dispatchPlayStateChanged(musicState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void resetRecyclerViewPosition(@NotNull ResetScrollViewPositionEvent event) {
        FragmentSmartHomeModeCategoryBinding a2;
        HorizontalRecyclerView horizontalRecyclerView;
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventType() == 1 && (a2 = a()) != null && (horizontalRecyclerView = a2.rvIotDevices) != null) {
            horizontalRecyclerView.scrollToPosition(0);
        }
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment
    protected void observeData() {
        super.observeData();
        this.c = RelayMediaManger.INSTANCE.getFirstData();
        b().getDeviceList().observeForever(new g());
        b().getLoading().observe(getViewLifecycleOwner(), new i());
        LiveEventBus.get(EVENT_REFRESH).observe(getViewLifecycleOwner(), new j());
        LiveEventBus.get(EVENT_JUMP_TO_DEVICE_LIST).observe(getViewLifecycleOwner(), new k());
        LiveEventBus.get(EVENT_JUMP_TO_MEDIA_LIST).observe(getViewLifecycleOwner(), new l());
        LiveEventBus.get(EVENT_OPEN_CARD).observe(getViewLifecycleOwner(), new m());
        LiveEventBus.get(EVENT_JUMP_TO_CATEGORY_LIST).observe(getViewLifecycleOwner(), new n());
        LiveEventBus.get(EVENT_MODE_CHANGED).observe(getViewLifecycleOwner(), new o());
        LiveEventBus.get(EVENT_SCENE_CHANGED).observe(getViewLifecycleOwner(), new p());
        LiveEventBus.get(EVENT_JUMP_TO_USER_GUIDE).observe(getViewLifecycleOwner(), new h());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u001a\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004 \u0005*\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0006"}, d2 = {"<anonymous>", "", "it", "", "Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class g<T> implements Observer<List<? extends ISmartHomeModel>> {
        g() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: SmartHomeModeCategoryFragment.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment$observeData$1$1", f = "SmartHomeModeCategoryFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment$g$1  reason: invalid class name */
        /* loaded from: classes4.dex */
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ List $it;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(List list, Continuation continuation) {
                super(2, continuation);
                this.$it = list;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                return new AnonymousClass1(this.$it, completion);
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
                    SmartHomeModeCategoryFragment.this.b = this.$it;
                    HorizontalRecyclerView horizontalRecyclerView = SmartHomeModeCategoryFragment.this.a().rvIotDevices;
                    Intrinsics.checkNotNullExpressionValue(horizontalRecyclerView, "binding.rvIotDevices");
                    RecyclerView.Adapter adapter = horizontalRecyclerView.getAdapter();
                    if (adapter != null) {
                        List<? extends ISmartHomeModel> it = this.$it;
                        Intrinsics.checkNotNullExpressionValue(it, "it");
                        ((CategoryModeDeviceListAdapter) adapter).updateData(it);
                        return Unit.INSTANCE;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type com.xiaomi.smarthome.ui.adapter.CategoryModeDeviceListAdapter");
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }

        /* renamed from: a */
        public final void onChanged(List<? extends ISmartHomeModel> list) {
            kotlinx.coroutines.e.a(LifecycleOwnerKt.getLifecycleScope(SmartHomeModeCategoryFragment.this), null, null, new AnonymousClass1(list, null), 3, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "isLoading", "", "kotlin.jvm.PlatformType", "onChanged", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class i<T> implements Observer<Boolean> {
        i() {
        }

        /* renamed from: a */
        public final void onChanged(Boolean isLoading) {
            Logger logger = L.smarthome;
            logger.d("loading:" + isLoading);
            Intrinsics.checkNotNullExpressionValue(isLoading, "isLoading");
            if (isLoading.booleanValue()) {
                SmartHomeStatHelper.recordGuideCardShow();
            }
            HorizontalRecyclerView horizontalRecyclerView = SmartHomeModeCategoryFragment.this.a().rvIotDevices;
            Intrinsics.checkNotNullExpressionValue(horizontalRecyclerView, "binding.rvIotDevices");
            ViewExtKt.visibility(horizontalRecyclerView, !isLoading.booleanValue());
            ImageView imageView = SmartHomeModeCategoryFragment.this.a().groupLoading;
            Intrinsics.checkNotNullExpressionValue(imageView, "binding.groupLoading");
            ViewExtKt.visibility(imageView, isLoading.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class j<T> implements Observer<Object> {
        j() {
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            L.smarthome.d("view life cycle to load devices");
            if (Intrinsics.areEqual(obj, (Object) true)) {
                SmartHomeModeCategoryFragment.this.a().rvIotDevices.smoothScrollToPosition(0);
            }
            SmartHomeModeCategoryFragment.this.b().loadDevicesByRegion(SmartHomeModeCategoryFragment.this.m, SmartHomeModeCategoryFragment.this.d, SmartHomeModeCategoryFragment.this.c, SmartHomeModeCategoryFragment.this.isAllInfoReady());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class k<T> implements Observer<Object> {
        k() {
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            Intent intent = new Intent(SmartHomeModeCategoryFragment.this.context, DeviceListActivity.class);
            if (obj != null) {
                intent.putExtra("extra_data", (TypeGroup) obj);
                MiotRegionAdapter.MiotRegion miotRegion = SmartHomeModeCategoryFragment.this.m;
                intent.putExtra(DeviceListActivity.EXTRA_REGION_ID, miotRegion != null ? miotRegion.getRegionId() : null);
                SmartHomeModeCategoryFragment.this.startActivity(intent);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type com.xiaomi.smarthome.core.entity.TypeGroup");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class l<T> implements Observer<Object> {
        l() {
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            Intent intent = new Intent(SmartHomeModeCategoryFragment.this.context, MediaListActivity.class);
            MediaControllerEvent.MusicState musicState = SmartHomeModeCategoryFragment.this.e;
            intent.putExtra(MediaListActivity.EXTRA_STATUS, musicState != null ? musicState.getState() : 0);
            SmartHomeModeCategoryFragment.this.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class m<T> implements Observer<Object> {
        m() {
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            Lifecycle lifecycle = SmartHomeModeCategoryFragment.this.getLifecycle();
            Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
            if (!lifecycle.getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                return;
            }
            if (!NetworkUtil.isNetworkConnected(SmartHomeModeCategoryFragment.this.context)) {
                ToastUtil.showToast(R.string.tip_network_is_broken);
                return;
            }
            MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
            Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
            if (!instance.isDeviceInfoReady()) {
                ToastUtil.showToast(R.string.device_info_is_loading);
            } else if (obj != null) {
                Map map = (Map) obj;
                Object obj2 = map.get(OneTrack.Event.VIEW);
                if (obj2 != null) {
                    View view = (View) obj2;
                    Object obj3 = map.get("item");
                    if (obj3 != null) {
                        DeviceInfoWrapper deviceInfoWrapper = (DeviceInfoWrapper) obj3;
                        if (deviceInfoWrapper.getDeviceInfo().isOnline) {
                            MicoMiotDeviceManager instance2 = MicoMiotDeviceManager.getInstance();
                            Intrinsics.checkNotNullExpressionValue(instance2, "MicoMiotDeviceManager.getInstance()");
                            instance2.getDeviceHelper().onDevicePageShow(view, deviceInfoWrapper.getDeviceInfo());
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

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class n<T> implements Observer<Object> {
        n() {
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            SmartHomeModeCategoryFragment.this.startActivity(new Intent(SmartHomeModeCategoryFragment.this.context, CategoryReorderActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class o<T> implements Observer<Object> {
        o() {
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            MiotRegionAdapter.MiotRegion miotRegion = SmartHomeModeCategoryFragment.this.m;
            if (miotRegion != null && (miotRegion instanceof MiotRegionAdapter.MiotRegionHome)) {
                MicoMiotDeviceManager.getInstance().getScenesByHomeId(((MiotRegionAdapter.MiotRegionHome) miotRegion).getQueryHomeId(), SmartHomeModeCategoryFragment.this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class p<T> implements Observer<Object> {
        p() {
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            SmartHomeModeCategoryFragment.this.b().loadDevicesByRegion(SmartHomeModeCategoryFragment.this.m, SmartHomeModeCategoryFragment.this.d, SmartHomeModeCategoryFragment.this.c, SmartHomeModeCategoryFragment.this.isAllInfoReady());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onChanged"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class h<T> implements Observer<Object> {
        h() {
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            SmartHomeModeCategoryFragment.this.startActivity(new Intent(SmartHomeModeCategoryFragment.this.context, UserGuideActivity.class));
        }
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public void onDevicesRefreshed() {
        L.smarthome.d("onDevicesRefreshed");
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.o > 5000) {
            Logger logger = L.smarthome;
            StringBuilder sb = new StringBuilder();
            sb.append("onDevicesRefreshed mCurRegion=");
            MiotRegionAdapter.MiotRegion miotRegion = this.m;
            sb.append(miotRegion != null ? miotRegion.getRegionName() : null);
            logger.d(sb.toString());
            b().loadDevicesByRegion(this.m, this.d, this.c, this.p);
            MiotRegionAdapter.MiotRegion miotRegion2 = this.m;
            if (miotRegion2 != null && (miotRegion2 instanceof MiotRegionAdapter.MiotRegionHome)) {
                MicoMiotDeviceManager.getInstance().getScenesByHomeId(((MiotRegionAdapter.MiotRegionHome) miotRegion2).getQueryHomeId(), this);
            }
            this.o = currentTimeMillis;
        }
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public void onDevicesFailed() {
        L.smarthome.d("onDevicesFailed");
        if (!this.p) {
            ToastUtil.showToast(R.string.device_info_load_failed);
        }
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public void onEventsUpdated() {
        L.smarthome.d("onEventsUpdated");
    }

    @Override // com.xiaomi.smarthome.ui.widgets.MicoSceneCallback
    public void onGetSceneSuccess(@NotNull String homeid, @Nullable List<CommonUsedScene> list) {
        Intrinsics.checkNotNullParameter(homeid, "homeid");
        L.smarthome.d("scene %s", "onGetSceneSuccess");
        Threads.postInMainThread(new r(homeid, list));
    }

    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class r implements Runnable {
        final /* synthetic */ String b;
        final /* synthetic */ List c;

        r(String str, List list) {
            this.b = str;
            this.c = list;
        }

        @Override // java.lang.Runnable
        public final void run() {
            String str = this.b;
            MiotRegionAdapter.MiotRegion miotRegion = SmartHomeModeCategoryFragment.this.m;
            CategoryScene categoryScene = null;
            if (!TextUtils.equals(str, miotRegion != null ? miotRegion.getQueryHomeId() : null)) {
                L.smarthome.d("scene %s", "homeId is changed");
            } else if (SmartHomeModeCategoryFragment.this.d != null) {
                CategoryScene categoryScene2 = SmartHomeModeCategoryFragment.this.d;
                Intrinsics.checkNotNull(categoryScene2);
                if (!Objects.equals(categoryScene2.sceneList, this.c)) {
                    SmartHomeModeCategoryFragment smartHomeModeCategoryFragment = SmartHomeModeCategoryFragment.this;
                    List list = this.c;
                    if (!(list == null || list.isEmpty())) {
                        categoryScene = new CategoryScene(this.c);
                    }
                    smartHomeModeCategoryFragment.d = categoryScene;
                    L.smarthome.d("scene has changed %s,loadDevices", SmartHomeModeCategoryFragment.this.d);
                    SmartHomeModeCategoryFragment.this.b().loadDevicesByRegion(SmartHomeModeCategoryFragment.this.m, SmartHomeModeCategoryFragment.this.d, SmartHomeModeCategoryFragment.this.c, SmartHomeModeCategoryFragment.this.isAllInfoReady());
                }
            } else {
                SmartHomeModeCategoryFragment smartHomeModeCategoryFragment2 = SmartHomeModeCategoryFragment.this;
                if (!ContainerUtil.isEmpty(this.c)) {
                    categoryScene = new CategoryScene(this.c);
                }
                smartHomeModeCategoryFragment2.d = categoryScene;
                if (SmartHomeModeCategoryFragment.this.d != null) {
                    SmartHomeModeCategoryFragment.this.b().loadDevicesByRegion(SmartHomeModeCategoryFragment.this.m, SmartHomeModeCategoryFragment.this.d, SmartHomeModeCategoryFragment.this.c, SmartHomeModeCategoryFragment.this.isAllInfoReady());
                }
                Logger logger = L.smarthome;
                logger.d("scene %s", "loadDevices " + SmartHomeModeCategoryFragment.this.d);
            }
        }
    }

    @Override // com.xiaomi.smarthome.ui.widgets.MicoSceneCallback
    public void onGetSceneFailed(@NotNull String homeid, int i2, @Nullable String str) {
        Intrinsics.checkNotNullParameter(homeid, "homeid");
        Logger logger = L.smarthome;
        logger.d("scene %s", "get scene fail:" + homeid + StringUtil.COMMA + i2 + StringUtil.COMMA + str);
    }

    public final boolean isAllInfoReady() {
        return this.p;
    }

    public final void setAllInfoReady(boolean z) {
        this.p = z;
    }

    @Override // com.xiaomi.miot.support.MiotManager.IDeviceInfoReadyCallback
    public void onDeviceInfoReady() {
        L.smarthome.d("onDeviceInfoReady");
        Log.i(MicoSupConstants.TAG_LAU, "Info: launcher receive all info ready!");
        this.p = true;
        MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
        instance.setDeviceInfoReady(true);
        if (this.m == null) {
            MicoMiotDeviceManager.getInstance().refreshDevice();
        } else {
            Threads.postInMainThread(new q());
        }
    }

    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class q implements Runnable {
        q() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            SmartHomeModeCategoryFragment.this.b().loadDevicesByRegion(SmartHomeModeCategoryFragment.this.m, SmartHomeModeCategoryFragment.this.d, SmartHomeModeCategoryFragment.this.c, SmartHomeModeCategoryFragment.this.isAllInfoReady());
            MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
            MiotRegionAdapter.MiotRegion miotRegion = SmartHomeModeCategoryFragment.this.m;
            instance.getScenesByHomeId(miotRegion != null ? miotRegion.getQueryHomeId() : null, SmartHomeModeCategoryFragment.this);
        }
    }

    @Override // com.xiaomi.smarthome.ui.media.RelayMediaListener
    public void onFirstDataChanged(@Nullable MicoMediaData micoMediaData) {
        if (micoMediaData == null || !micoMediaData.isSameItem(this.c)) {
            if (micoMediaData == null) {
                this.c = new MicoMediaData(MediaType.NONE, "", "", "", null, null, null, 0, null, 496, null);
            } else {
                this.c = micoMediaData;
            }
            b().loadDevicesByRegion(this.m, this.d, this.c, this.p);
        }
    }

    @Override // com.xiaomi.smarthome.ui.media.RelayMediaListener
    public void onDataSizeChange(int i2) {
        b().loadDevicesByRegion(this.m, this.d, this.c, this.p);
    }

    @Override // com.xiaomi.micolauncher.common.widget.RefreshCallBack
    public void onRefreshing() {
        this.f = true;
        Threads.postDelayedInMainThread(this.h, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        MiotManager.refreshDevices();
    }

    @Override // com.xiaomi.miot.support.MiotManager.IMiotServiceStatusListener
    public void onServiceDisconnect() {
        L.smarthome.d("SmartHomePageFragment onServiceDisconnect");
        this.g = false;
    }

    @Override // com.xiaomi.miot.support.MiotManager.IMiotServiceStatusListener
    public void onServiceConnect() {
        L.smarthome.d("SmartHomePageFragment onServiceConnect");
        this.g = true;
        MiotManager.refreshDevices();
    }

    /* compiled from: SmartHomeModeCategoryFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\t\n\u0000\n\u0002\b\u0003*\u0001\u0001\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "com/xiaomi/smarthome/ui/SmartHomeModeCategoryFragment$iMiotMsgCallback$2$1", "invoke", "()Lcom/xiaomi/smarthome/ui/SmartHomeModeCategoryFragment$iMiotMsgCallback$2$1;"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class b extends Lambda implements Function0<SmartHomeModeCategoryFragment$iMiotMsgCallback$2$1> {
        b() {
            super(0);
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment$iMiotMsgCallback$2$1] */
        @NotNull
        /* renamed from: a */
        public final SmartHomeModeCategoryFragment$iMiotMsgCallback$2$1 invoke() {
            return new IMiotMsgCallback() { // from class: com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment$iMiotMsgCallback$2$1
                @Override // com.xiaomi.miot.support.IMiotMsgCallback
                public int getAction() {
                    return 1;
                }

                /* compiled from: SmartHomeModeCategoryFragment.kt */
                @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
                @DebugMetadata(c = "com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment$iMiotMsgCallback$2$1$onMsgCallback$1", f = "SmartHomeModeCategoryFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                /* loaded from: classes4.dex */
                static final class a extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    final /* synthetic */ JSONObject $data;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    a(JSONObject jSONObject, Continuation continuation) {
                        super(2, continuation);
                        this.$data = jSONObject;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @NotNull
                    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                        Intrinsics.checkNotNullParameter(completion, "completion");
                        return new a(this.$data, completion);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return ((a) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        String string;
                        IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        if (this.label == 0) {
                            ResultKt.throwOnFailure(obj);
                            JSONObject jSONObject = this.$data;
                            if (jSONObject != null && jSONObject.has("did") && jSONObject.has("prop") && jSONObject.has(b.p) && (string = jSONObject.getString("prop")) != null && (Intrinsics.areEqual("event.dev_online", string) || Intrinsics.areEqual("event.dev_offline", string))) {
                                MiotManager.refreshDevices();
                            }
                            return Unit.INSTANCE;
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                }

                @Override // com.xiaomi.miot.support.IMiotMsgCallback
                public void onMsgCallback(@Nullable JSONObject jSONObject) {
                    Logger logger = L.smarthome;
                    logger.d("SmartHomePageFragment onMsgCallback " + jSONObject);
                    e.a(LifecycleOwnerKt.getLifecycleScope(SmartHomeModeCategoryFragment.this), null, null, new a(jSONObject, null), 3, null);
                }
            };
        }
    }
}
