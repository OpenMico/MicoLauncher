package com.xiaomi.smarthome.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.elvishew.xlog.Logger;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.umeng.analytics.pro.c;
import com.xiaomi.mico.base.exts.ViewExtKt;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.LifeCycleUtils;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.common.widget.RefreshCallBack;
import com.xiaomi.micolauncher.common.widget.RefreshLayout;
import com.xiaomi.micolauncher.module.homepage.event.ResetScrollViewPositionEvent;
import com.xiaomi.miot.scene.CommonUsedScene;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.miot.support.core.MiotRoom;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.base.BaseSmartHomeFragment;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;
import com.xiaomi.smarthome.databinding.FragmentSmartHomeModeRoomBinding;
import com.xiaomi.smarthome.ui.adapter.MiotDeviceAdapter;
import com.xiaomi.smarthome.ui.adapter.RoomTabAdapter;
import com.xiaomi.smarthome.ui.widgets.MicoSceneCallback;
import com.xiaomi.smarthome.ui.widgets.MiotViewCachedManager;
import com.xiaomi.smarthome.ui.widgets.RoomTabViewHolder;
import io.netty.util.internal.StringUtil;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SmartHomeModeRoomFragment.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 >2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u0001>B\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\b\u0010\u001f\u001a\u00020\u001cH\u0015J\u0010\u0010 \u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0012\u0010!\u001a\u00020\u001c2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J$\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010\"\u001a\u0004\u0018\u00010#H\u0017J\b\u0010*\u001a\u00020\u001cH\u0016J\b\u0010+\u001a\u00020\u001cH\u0016J\b\u0010,\u001a\u00020\u001cH\u0016J\b\u0010-\u001a\u00020\u001cH\u0016J\b\u0010.\u001a\u00020\u001cH\u0016J\"\u0010/\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\u000e2\u0006\u00101\u001a\u00020\u001a2\b\u00102\u001a\u0004\u0018\u00010\u000eH\u0016J \u00103\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\u000e2\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u000104H\u0016J\b\u00105\u001a\u00020\u001cH\u0016J\b\u00106\u001a\u00020\u001cH\u0016J\u001a\u00107\u001a\u00020\u001c2\b\u00108\u001a\u0004\u0018\u0001092\u0006\u0010:\u001a\u00020\u001aH\u0016J\u0010\u0010;\u001a\u00020\u001c2\u0006\u0010<\u001a\u00020=H\u0007R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lcom/xiaomi/smarthome/ui/SmartHomeModeRoomFragment;", "Lcom/xiaomi/smarthome/base/BaseSmartHomeFragment;", "Lcom/xiaomi/smarthome/core/miot/MicoMiotDeviceManager$MicoDeviceChangeListener;", "Lcom/xiaomi/smarthome/ui/widgets/RoomTabViewHolder$RoomClickListener;", "Lcom/xiaomi/smarthome/ui/widgets/MicoSceneCallback;", "Lcom/xiaomi/micolauncher/common/widget/RefreshCallBack;", "()V", "binding", "Lcom/xiaomi/smarthome/databinding/FragmentSmartHomeModeRoomBinding;", "getBinding", "()Lcom/xiaomi/smarthome/databinding/FragmentSmartHomeModeRoomBinding;", "binding$delegate", "Lkotlin/Lazy;", "currentRoomId", "", "homeId", "isLeftRefresh", "", "miotDeviceAdapter", "Lcom/xiaomi/smarthome/ui/adapter/MiotDeviceAdapter;", "roomTabAdapter", "Lcom/xiaomi/smarthome/ui/adapter/RoomTabAdapter;", "sceneList", "", "Lcom/xiaomi/miot/scene/CommonUsedScene;", "tabPosition", "", "init", "", c.R, "Landroid/content/Context;", "initViews", "onAttach", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onDestroyView", "onDetach", "onDeviceChanged", "onDeviceEvent", "onGetSceneFailed", "home_id", "errorCode", "errorInfo", "onGetSceneSuccess", "", "onRefreshing", "onResume", "onTabClick", "room", "Lcom/xiaomi/miot/support/core/MiotRoom;", "position", "resetRecyclerViewPosition", "event", "Lcom/xiaomi/micolauncher/module/homepage/event/ResetScrollViewPositionEvent;", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class SmartHomeModeRoomFragment extends BaseSmartHomeFragment implements RefreshCallBack, MicoMiotDeviceManager.MicoDeviceChangeListener, MicoSceneCallback, RoomTabViewHolder.RoomClickListener {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    public static final String TAG = "SmartHomeModeRoomFragment";
    private RoomTabAdapter b;
    private MiotDeviceAdapter c;
    private int d;
    private List<? extends CommonUsedScene> e;
    private boolean h;
    private final Lazy a = LazyKt.lazy(new a());
    private String f = "";
    private String g = "";

    /* JADX INFO: Access modifiers changed from: private */
    public final FragmentSmartHomeModeRoomBinding a() {
        return (FragmentSmartHomeModeRoomBinding) this.a.getValue();
    }

    @Override // com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager.MicoDeviceChangeListener
    public void onDeviceEvent() {
    }

    /* compiled from: SmartHomeModeRoomFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/databinding/FragmentSmartHomeModeRoomBinding;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a extends Lambda implements Function0<FragmentSmartHomeModeRoomBinding> {
        a() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final FragmentSmartHomeModeRoomBinding invoke() {
            return FragmentSmartHomeModeRoomBinding.inflate(SmartHomeModeRoomFragment.this.getLayoutInflater());
        }
    }

    /* compiled from: SmartHomeModeRoomFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\r\u0010\u0005\u001a\u00020\u0006H\u0001¢\u0006\u0002\b\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/xiaomi/smarthome/ui/SmartHomeModeRoomFragment$Companion;", "", "()V", "TAG", "", "newInstance", "Lcom/xiaomi/smarthome/ui/SmartHomeModeRoomFragment;", "newInstance$smarthome_release", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final SmartHomeModeRoomFragment newInstance$smarthome_release() {
            return new SmartHomeModeRoomFragment();
        }
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    @NotNull
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        super.onCreateView(inflater, viewGroup, bundle);
        initViews();
        observeData();
        FragmentSmartHomeModeRoomBinding binding = a();
        Intrinsics.checkNotNullExpressionValue(binding, "binding");
        View root = binding.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return root;
    }

    public final void init(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        EventBusRegistry.getEventBus().register(this);
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        EventBusRegistry.getEventBus().unregister(this);
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment
    @SuppressLint({"ClickableViewAccessibility"})
    protected void initViews() {
        super.initViews();
        a().devicesView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.smarthome.ui.SmartHomeModeRoomFragment$initViews$1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
                Intrinsics.checkNotNullParameter(outRect, "outRect");
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(parent, "parent");
                Intrinsics.checkNotNullParameter(state, "state");
                super.getItemOffsets(outRect, view, parent, state);
                int dimensionPixelSize = view.getResources().getDimensionPixelSize(R.dimen.room_item_padding_start);
                outRect.right = dimensionPixelSize;
                outRect.bottom = dimensionPixelSize;
            }
        });
        RecyclerView recyclerView = a().roomView;
        Intrinsics.checkNotNullExpressionValue(recyclerView, "binding.roomView");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
        a().rvRefreshLayout.setHeadMode(RefreshLayout.HeadMode.REFRESH, this);
        this.b = new RoomTabAdapter(getActivity(), this);
        RecyclerView recyclerView2 = a().roomView;
        Intrinsics.checkNotNullExpressionValue(recyclerView2, "binding.roomView");
        recyclerView2.setAdapter(this.b);
        final FragmentActivity activity = getActivity();
        final int integer = getResources().getInteger(R.integer.room_mode_span_count);
        GridLayoutManager smartHomeModeRoomFragment$initViews$roomLayoutManager$1 = new GridLayoutManager(activity, integer, 0, false) { // from class: com.xiaomi.smarthome.ui.SmartHomeModeRoomFragment$initViews$roomLayoutManager$1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean isAutoMeasureEnabled() {
                return false;
            }
        };
        smartHomeModeRoomFragment$initViews$roomLayoutManager$1.setItemPrefetchEnabled(true);
        smartHomeModeRoomFragment$initViews$roomLayoutManager$1.setInitialPrefetchItemCount(getResources().getInteger(R.integer.room_mode_prefetch_item_count));
        smartHomeModeRoomFragment$initViews$roomLayoutManager$1.setRecycleChildrenOnDetach(true);
        HorizontalRecyclerView horizontalRecyclerView = a().devicesView;
        Intrinsics.checkNotNullExpressionValue(horizontalRecyclerView, "binding.devicesView");
        horizontalRecyclerView.setLayoutManager(smartHomeModeRoomFragment$initViews$roomLayoutManager$1);
        this.c = new MiotDeviceAdapter(getActivity(), a().devicesView);
        MiotDeviceAdapter miotDeviceAdapter = this.c;
        if (miotDeviceAdapter != null) {
            miotDeviceAdapter.register();
        }
        HorizontalRecyclerView horizontalRecyclerView2 = a().devicesView;
        Intrinsics.checkNotNullExpressionValue(horizontalRecyclerView2, "binding.devicesView");
        horizontalRecyclerView2.setAdapter(this.c);
        a().devicesView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.smarthome.ui.SmartHomeModeRoomFragment$initViews$2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView3, int i) {
                Context context;
                Context context2;
                Context context3;
                Intrinsics.checkNotNullParameter(recyclerView3, "recyclerView");
                super.onScrollStateChanged(recyclerView3, i);
                context = SmartHomeModeRoomFragment.this.context;
                if (LifeCycleUtils.isInvalidActivity(context)) {
                    return;
                }
                if (i == 0) {
                    context3 = SmartHomeModeRoomFragment.this.context;
                    Glide.with(context3).resumeRequests();
                } else if (i == 2) {
                    context2 = SmartHomeModeRoomFragment.this.context;
                    Glide.with(context2).pauseRequests();
                }
            }
        });
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        MicoMiotDeviceManager.getInstance().refresh(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager.MicoDeviceChangeListener
    public void onDeviceChanged() {
        boolean z;
        if (this.h) {
            a().rvRefreshLayout.onRefreshComplete();
            this.h = false;
        }
        MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
        MiotHome currentHome = instance.getCurrentHome();
        if (currentHome == null) {
            this.e = null;
            RelativeLayout relativeLayout = a().roomLoadingView;
            Intrinsics.checkNotNullExpressionValue(relativeLayout, "binding.roomLoadingView");
            ViewExtKt.visibility(relativeLayout, true);
            return;
        }
        String newHomeId = currentHome.homeId;
        if (!TextUtils.equals(newHomeId, this.f)) {
            Intrinsics.checkNotNullExpressionValue(newHomeId, "newHomeId");
            this.f = newHomeId;
            this.e = null;
            MicoMiotDeviceManager.getInstance().getScenesByHomeId(this.f, this);
            z = true;
        } else {
            z = false;
        }
        RecyclerView recyclerView = a().roomView;
        Intrinsics.checkNotNullExpressionValue(recyclerView, "binding.roomView");
        ViewExtKt.visibility(recyclerView, true);
        List<MiotRoom> list = currentHome.roomList;
        RoomTabAdapter roomTabAdapter = this.b;
        if (roomTabAdapter != null) {
            roomTabAdapter.updateDataList(list);
        }
        HorizontalRecyclerView horizontalRecyclerView = a().devicesView;
        Intrinsics.checkNotNullExpressionValue(horizontalRecyclerView, "binding.devicesView");
        ViewExtKt.visibility(horizontalRecyclerView, true);
        if (!ContainerUtil.isEmpty(list)) {
            if (z) {
                this.d = 0;
                RoomTabAdapter roomTabAdapter2 = this.b;
                if (roomTabAdapter2 != null) {
                    roomTabAdapter2.setHighlightPosition(this.d);
                }
            } else {
                int size = list.size();
                if (this.d >= size) {
                    this.d = size - 1;
                    RoomTabAdapter roomTabAdapter3 = this.b;
                    if (roomTabAdapter3 != null) {
                        roomTabAdapter3.setHighlightPosition(this.d);
                    }
                }
            }
            MiotRoom miotRoom = list.get(this.d);
            String str = miotRoom.roomId;
            Intrinsics.checkNotNullExpressionValue(str, "miotRoom.roomId");
            this.g = str;
            MiotDeviceAdapter miotDeviceAdapter = this.c;
            if (miotDeviceAdapter != null) {
                miotDeviceAdapter.setMiotRoom(miotRoom);
            }
            MiotDeviceAdapter miotDeviceAdapter2 = this.c;
            if (miotDeviceAdapter2 != 0) {
                miotDeviceAdapter2.updateDataList(this.e, MicoMiotDeviceManager.getInstance().getDeviceListByRoomId(this.g));
            }
        } else {
            this.g = "";
            MiotDeviceAdapter miotDeviceAdapter3 = this.c;
            if (miotDeviceAdapter3 != 0) {
                miotDeviceAdapter3.updateDataList(this.e, null);
            }
        }
        RelativeLayout relativeLayout2 = a().roomLoadingView;
        Intrinsics.checkNotNullExpressionValue(relativeLayout2, "binding.roomLoadingView");
        ViewExtKt.visibility(relativeLayout2, false);
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        MicoMiotDeviceManager.getInstance().addListeners(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        MicoMiotDeviceManager.getInstance().unregisterListener(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.xiaomi.smarthome.ui.widgets.RoomTabViewHolder.RoomClickListener
    public void onTabClick(@Nullable MiotRoom miotRoom, int i) {
        String str;
        this.d = i;
        RoomTabAdapter roomTabAdapter = this.b;
        if (roomTabAdapter != null) {
            roomTabAdapter.setHighlightPosition(this.d);
        }
        MiotDeviceAdapter miotDeviceAdapter = this.c;
        if (miotDeviceAdapter != null) {
            miotDeviceAdapter.setMiotRoom(miotRoom);
        }
        if (miotRoom != null) {
            str = miotRoom.roomId;
            Intrinsics.checkNotNullExpressionValue(str, "room.roomId");
        } else {
            str = "";
        }
        this.g = str;
        MiotDeviceAdapter miotDeviceAdapter2 = this.c;
        if (miotDeviceAdapter2 != 0) {
            miotDeviceAdapter2.updateDataList(this.e, MicoMiotDeviceManager.getInstance().getDeviceListByRoomId(this.g));
        }
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        MiotDeviceAdapter miotDeviceAdapter = this.c;
        if (miotDeviceAdapter != null) {
            miotDeviceAdapter.unregister();
        }
        MiotViewCachedManager.getInstance().clearMiotViewHolder();
    }

    @Override // com.xiaomi.smarthome.ui.widgets.MicoSceneCallback
    public void onGetSceneSuccess(@NotNull String home_id, @Nullable List<CommonUsedScene> list) {
        Intrinsics.checkNotNullParameter(home_id, "home_id");
        if (TextUtils.equals(home_id, this.f)) {
            this.e = list;
            MiotDeviceAdapter miotDeviceAdapter = this.c;
            if (miotDeviceAdapter != null) {
                miotDeviceAdapter.updateDataList(list, MicoMiotDeviceManager.getInstance().getDeviceListByRoomId(this.g));
            }
        }
    }

    @Override // com.xiaomi.smarthome.ui.widgets.MicoSceneCallback
    public void onGetSceneFailed(@NotNull String home_id, int i, @Nullable String str) {
        Intrinsics.checkNotNullParameter(home_id, "home_id");
        Logger logger = L.smarthome;
        logger.d("errCode=" + home_id + StringUtil.COMMA + i + ";errorInfo=" + str);
    }

    /* compiled from: SmartHomeModeRoomFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (SmartHomeModeRoomFragment.this.h) {
                SmartHomeModeRoomFragment.this.a().rvRefreshLayout.onRefreshComplete();
                SmartHomeModeRoomFragment.this.h = false;
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.widget.RefreshCallBack
    public void onRefreshing() {
        Threads.postDelayedInMainThread(new b(), SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        this.h = true;
        MicoMiotDeviceManager.getInstance().refreshDevice();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void resetRecyclerViewPosition(@NotNull ResetScrollViewPositionEvent event) {
        FragmentSmartHomeModeRoomBinding a2;
        HorizontalRecyclerView horizontalRecyclerView;
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventType() == 1 && (a2 = a()) != null && (horizontalRecyclerView = a2.devicesView) != null) {
            horizontalRecyclerView.scrollToPosition(0);
        }
    }
}
