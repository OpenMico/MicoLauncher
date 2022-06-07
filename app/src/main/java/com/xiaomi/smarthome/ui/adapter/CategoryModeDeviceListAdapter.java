package com.xiaomi.smarthome.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import androidx.annotation.MainThread;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dinuscxj.itemdecoration.LinearOffsetsItemDecoration;
import com.elvishew.xlog.Logger;
import com.jakewharton.rxbinding4.view.RxView;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4BigButton;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4Edit;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.event.MainPageVisibilityEvent;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.CategoryDic;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.MicoMediaData;
import com.xiaomi.smarthome.core.entity.TypeGroup;
import com.xiaomi.smarthome.core.miot.MediaControllerEvent;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import com.xiaomi.smarthome.databinding.ItemTypeGroupBinding;
import com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment;
import com.xiaomi.smarthome.ui.model.CategoryScene;
import com.xiaomi.smarthome.ui.model.ISmartHomeModel;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import com.xiaomi.smarthome.ui.widgets.CategoryEmptyViewHolder;
import com.xiaomi.smarthome.ui.widgets.MicoMediaViewHolder;
import com.xiaomi.smarthome.ui.widgets.MiotViewCachedManager;
import com.xiaomi.smarthome.ui.widgets.SceneListViewHolder;
import io.reactivex.rxjava3.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: CategoryModeDeviceListAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u0018\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u0018\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016J\u0010\u0010\u0019\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u0002H\u0016J\u0010\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u0002H\u0016J\u0014\u0010\u001c\u001a\u00020\f2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u001eR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/xiaomi/smarthome/ui/adapter/CategoryModeDeviceListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "list", "", "Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "(Ljava/util/List;)V", "isFirstView", "", "mediaViewHolders", "Lcom/xiaomi/smarthome/ui/widgets/MicoMediaViewHolder;", "dispatchPlayStateChanged", "", "musicState", "Lcom/xiaomi/smarthome/core/miot/MediaControllerEvent$MusicState;", "getItemCount", "", "getItemViewType", "position", "onBindViewHolder", "viewHolder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onViewAttachedToWindow", "holder", "onViewDetachedFromWindow", "updateData", "newList", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class CategoryModeDeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean a = true;
    private List<MicoMediaViewHolder> b = new ArrayList();
    private final List<ISmartHomeModel> c;

    public CategoryModeDeviceListAdapter(@NotNull List<ISmartHomeModel> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        this.c = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.c.get(i).type().getType();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (i == CategoryDic.CATEGORY_MEDIA.getType()) {
            BaseViewHolder fetchMediaViewHolder = MiotViewCachedManager.getInstance().fetchMediaViewHolder(parent.getContext());
            Intrinsics.checkNotNullExpressionValue(fetchMediaViewHolder, "MiotViewCachedManager.ge…iewHolder(parent.context)");
            return fetchMediaViewHolder;
        } else if (i == CategoryDic.CATEGORY_ENTRANCE.getType()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_reorder, parent, false);
            Intrinsics.checkNotNullExpressionValue(view, "view");
            return new TypeEntranceViewHolder(view);
        } else if (i == CategoryDic.CATEGORY_SCENE.getType()) {
            BaseViewHolder fetchSceneListViewHolder = MiotViewCachedManager.getInstance().fetchSceneListViewHolder(parent.getContext());
            Intrinsics.checkNotNullExpressionValue(fetchSceneListViewHolder, "MiotViewCachedManager.ge…iewHolder(parent.context)");
            return fetchSceneListViewHolder;
        } else if (i == CategoryDic.CATEGORY_EMPTY.getType()) {
            return new CategoryEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mico_miot_category_empty_view, parent, false));
        } else {
            ItemTypeGroupBinding bindingGroup = ItemTypeGroupBinding.bind(MiotViewCachedManager.getInstance().fetchTypeGroupView(parent.getContext()));
            Intrinsics.checkNotNullExpressionValue(bindingGroup, "bindingGroup");
            return new TypeGroupViewHolder(bindingGroup);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, int i) {
        List<DeviceInfoBean> list;
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        ISmartHomeModel iSmartHomeModel = this.c.get(i);
        Logger logger = L.smarthome;
        logger.d("onBindViewHolder item= " + iSmartHomeModel);
        if (this.a) {
            ViewTreeObserver viewTreeObserver = viewHolder.itemView.getViewTreeObserver();
            Intrinsics.checkNotNullExpressionValue(viewTreeObserver, "viewHolder.itemView.getViewTreeObserver()");
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.xiaomi.smarthome.ui.adapter.CategoryModeDeviceListAdapter$onBindViewHolder$1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    if (!MainPageVisibilityEvent.miotDevicelistUpdated()) {
                        EventBusRegistry.getEventBus().post(new MainPageVisibilityEvent(2));
                    }
                }
            });
            this.a = false;
        }
        if (viewHolder instanceof CategoryEmptyViewHolder) {
            AnimLifecycleManager.repeatOnAttach(viewHolder.itemView, MicoAnimConfigurator4BigButton.get());
            viewHolder.itemView.setOnClickListener(new a(iSmartHomeModel));
        } else if (viewHolder instanceof SceneListViewHolder) {
            SceneListViewHolder sceneListViewHolder = (SceneListViewHolder) viewHolder;
            if (iSmartHomeModel != null) {
                sceneListViewHolder.bindData(((CategoryScene) iSmartHomeModel).sceneList);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type com.xiaomi.smarthome.ui.model.CategoryScene");
        } else if (viewHolder instanceof MicoMediaViewHolder) {
            MicoMediaViewHolder micoMediaViewHolder = (MicoMediaViewHolder) viewHolder;
            if (iSmartHomeModel != null) {
                micoMediaViewHolder.bind((MicoMediaData) iSmartHomeModel, 1);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type com.xiaomi.smarthome.core.entity.MicoMediaData");
        } else if (viewHolder instanceof TypeGroupViewHolder) {
            if (iSmartHomeModel != null) {
                TypeGroup typeGroup = (TypeGroup) iSmartHomeModel;
                CategoryDic category = typeGroup.getCategory();
                View view = viewHolder.itemView;
                Intrinsics.checkNotNullExpressionValue(view, "viewHolder.itemView");
                String string = view.getContext().getString(R.string.smart_home_room_name_format, category.getTypeName(), Integer.valueOf(typeGroup.getDeviceList().size()));
                TypeGroupViewHolder typeGroupViewHolder = (TypeGroupViewHolder) viewHolder;
                TextView textView = typeGroupViewHolder.getBinding().groupLabel;
                Intrinsics.checkNotNullExpressionValue(textView, "viewHolder.binding.groupLabel");
                textView.setText(string);
                View root = typeGroupViewHolder.getBinding().getRoot();
                Intrinsics.checkNotNullExpressionValue(root, "viewHolder.binding.root");
                Context context = root.getContext();
                typeGroupViewHolder.getBinding().groupLabel.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, MiotDeviceUtils.getCategoryRes(category.getTypeName())), (Drawable) null, AppCompatResources.getDrawable(context, R.drawable.sh_ic_arrow_right), (Drawable) null);
                AnimLifecycleManager.repeatOnAttach(typeGroupViewHolder.getBinding().groupLabel, MicoAnimConfigurator4TabAndSwitch.get());
                TextView textView2 = typeGroupViewHolder.getBinding().groupLabel;
                Intrinsics.checkNotNullExpressionValue(textView2, "viewHolder.binding.groupLabel");
                RxView.clicks(textView2).throttleFirst(500L, TimeUnit.MILLISECONDS).subscribe(new b(iSmartHomeModel));
                switch (category) {
                    case CATEGORY_LIGHT:
                    case CATEGORY_ENV:
                    case CATEGORY_CURTAIN:
                    case CATEGORY_HOUSE_WORK:
                    case CATEGORY_SOCKET:
                    case CATEGORY_SECURITY:
                        if (typeGroup.getDeviceList().size() <= typeGroup.getShowCountInfFirstPage()) {
                            list = typeGroup.getDeviceList();
                        } else {
                            list = typeGroup.getDeviceList().subList(0, typeGroup.getShowCountInfFirstPage());
                        }
                        RecyclerView recyclerView = typeGroupViewHolder.getBinding().rvDevices;
                        Intrinsics.checkNotNullExpressionValue(recyclerView, "viewHolder.binding.rvDevices");
                        if (recyclerView.getAdapter() == null) {
                            View root2 = typeGroupViewHolder.getBinding().getRoot();
                            Intrinsics.checkNotNullExpressionValue(root2, "viewHolder.binding.root");
                            Context context2 = root2.getContext();
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context2, 0, false);
                            RecyclerView recyclerView2 = typeGroupViewHolder.getBinding().rvDevices;
                            Intrinsics.checkNotNullExpressionValue(recyclerView2, "viewHolder.binding.rvDevices");
                            recyclerView2.setLayoutManager(linearLayoutManager);
                            RecyclerView recyclerView3 = typeGroupViewHolder.getBinding().rvDevices;
                            Intrinsics.checkNotNullExpressionValue(recyclerView3, "viewHolder.binding.rvDevices");
                            recyclerView3.setAdapter(new GroupAdapter(category, CollectionsKt.toMutableList((Collection) list), 0, null, 12, null));
                            RecyclerView recyclerView4 = typeGroupViewHolder.getBinding().rvDevices;
                            Intrinsics.checkNotNullExpressionValue(recyclerView4, "viewHolder.binding.rvDevices");
                            if (recyclerView4.getItemDecorationCount() <= 0) {
                                LinearOffsetsItemDecoration linearOffsetsItemDecoration = new LinearOffsetsItemDecoration(0);
                                Intrinsics.checkNotNullExpressionValue(context2, "context");
                                linearOffsetsItemDecoration.setItemOffsets(context2.getResources().getDimensionPixelOffset(R.dimen.group_decoration));
                                typeGroupViewHolder.getBinding().rvDevices.addItemDecoration(linearOffsetsItemDecoration);
                                return;
                            }
                            return;
                        }
                        RecyclerView recyclerView5 = typeGroupViewHolder.getBinding().rvDevices;
                        Intrinsics.checkNotNullExpressionValue(recyclerView5, "viewHolder.binding.rvDevices");
                        RecyclerView.Adapter adapter = recyclerView5.getAdapter();
                        Intrinsics.checkNotNull(adapter);
                        if (adapter != null) {
                            ((GroupAdapter) adapter).updateData(list);
                            return;
                        }
                        throw new NullPointerException("null cannot be cast to non-null type com.xiaomi.smarthome.ui.adapter.GroupAdapter");
                    default:
                        L.smarthome.e("CategoryModeDeviceListAdapter:onBind() category type not exists");
                        return;
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type com.xiaomi.smarthome.core.entity.TypeGroup");
            }
        } else if (viewHolder instanceof TypeEntranceViewHolder) {
            View findViewById = viewHolder.itemView.findViewById(R.id.ivCategoryReorder);
            if (findViewById != null) {
                AnimLifecycleManager.repeatOnAttach(viewHolder.itemView, MicoAnimConfigurator4Edit.get());
                RxView.clicks(findViewById).throttleFirst(500L, TimeUnit.MILLISECONDS).subscribe(c.a);
            }
        } else {
            throw new IllegalArgumentException("Unknown view holder");
        }
    }

    /* compiled from: CategoryModeDeviceListAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a implements View.OnClickListener {
        final /* synthetic */ ISmartHomeModel a;

        a(ISmartHomeModel iSmartHomeModel) {
            this.a = iSmartHomeModel;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_JUMP_TO_USER_GUIDE).post(this.a);
            SmartHomeStatHelper.recordGuideCardClick();
        }
    }

    /* compiled from: CategoryModeDeviceListAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class b<T> implements Consumer<Unit> {
        final /* synthetic */ ISmartHomeModel a;

        b(ISmartHomeModel iSmartHomeModel) {
            this.a = iSmartHomeModel;
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_JUMP_TO_DEVICE_LIST).post(this.a);
            SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_TAB);
        }
    }

    /* compiled from: CategoryModeDeviceListAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class c<T> implements Consumer<Unit> {
        public static final c a = new c();

        c() {
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_JUMP_TO_CATEGORY_LIST).post(0);
            SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_RANK);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.c.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(@NotNull RecyclerView.ViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onViewAttachedToWindow(holder);
        if (holder instanceof MicoMediaViewHolder) {
            this.b.add(holder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(@NotNull RecyclerView.ViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof MicoMediaViewHolder) {
            this.b.remove(holder);
        }
    }

    public final void updateData(@NotNull List<? extends ISmartHomeModel> newList) {
        Intrinsics.checkNotNullParameter(newList, "newList");
        this.c.clear();
        List<? extends ISmartHomeModel> list = newList;
        if (!list.isEmpty()) {
            this.c.addAll(list);
        }
        notifyDataSetChanged();
    }

    @MainThread
    public final void dispatchPlayStateChanged(@NotNull MediaControllerEvent.MusicState musicState) {
        Intrinsics.checkNotNullParameter(musicState, "musicState");
        for (MicoMediaViewHolder micoMediaViewHolder : this.b) {
            micoMediaViewHolder.onPlayStateChanged(musicState);
        }
    }
}
