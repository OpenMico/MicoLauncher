package com.xiaomi.smarthome.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.jakewharton.rxbinding4.view.RxView;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4Edit;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4SmallButton;
import com.xiaomi.micolauncher.common.ui.MicoAnimationManager;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.ButtonGroupBean;
import com.xiaomi.smarthome.core.entity.CategoryDic;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.DividerBean;
import com.xiaomi.smarthome.core.entity.EditBean;
import com.xiaomi.smarthome.core.entity.IDevice;
import com.xiaomi.smarthome.ui.widgets.OnItemTouchListener;
import com.xiaomi.smarthome.ui.widgets.curtain.CurtainView;
import com.xiaomi.smarthome.ui.widgets.environments.EnvView;
import com.xiaomi.smarthome.ui.widgets.housework.HouseWorkView;
import com.xiaomi.smarthome.ui.widgets.lights.LightView;
import com.xiaomi.smarthome.ui.widgets.os.OSView;
import com.xiaomi.smarthome.ui.widgets.security.SecurityView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: GroupAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 L2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001LB/\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\b\u00106\u001a\u00020\nH\u0016J\u0010\u00107\u001a\u00020\n2\u0006\u00108\u001a\u00020\nH\u0016J\u0006\u00109\u001a\u00020\u0017J\u0018\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\nH\u0017J\u0018\u0010>\u001a\u00020\u00022\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\nH\u0017J\u0018\u0010B\u001a\u00020;2\u0006\u0010C\u001a\u00020\n2\u0006\u0010D\u001a\u00020\nH\u0016J\u001a\u0010E\u001a\u00020;2\b\u0010<\u001a\u0004\u0018\u00010\u00022\u0006\u0010F\u001a\u00020\nH\u0016J\u0018\u0010G\u001a\u00020;2\u0006\u0010C\u001a\u00020\n2\u0006\u0010D\u001a\u00020\nH\u0016J\u0014\u0010H\u001a\u00020;2\f\u0010I\u001a\b\u0012\u0004\u0012\u00020K0JR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R$\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u0017@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0019\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0011\u0010(\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u001c\u0010-\u001a\u0004\u0018\u00010.X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010*\"\u0004\b4\u00105¨\u0006M"}, d2 = {"Lcom/xiaomi/smarthome/ui/adapter/GroupAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/xiaomi/smarthome/ui/widgets/OnItemTouchListener;", "category", "Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "list", "", "Lcom/xiaomi/smarthome/core/entity/IDevice;", "showCountInFirstPage", "", "pageLocation", "Lcom/xiaomi/smarthome/ui/adapter/PageLocation;", "(Lcom/xiaomi/smarthome/core/entity/CategoryDic;Ljava/util/List;ILcom/xiaomi/smarthome/ui/adapter/PageLocation;)V", "buttonGroupBean", "Lcom/xiaomi/smarthome/core/entity/ButtonGroupBean;", "getCategory", "()Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "dividerBean", "Lcom/xiaomi/smarthome/core/entity/DividerBean;", "editBean", "Lcom/xiaomi/smarthome/core/entity/EditBean;", "<set-?>", "", "isDataChange", "()Z", com.xiaomi.onetrack.api.b.p, "isEditMode", "setEditMode", "(Z)V", "itemTouchHelper", "Landroidx/recyclerview/widget/ItemTouchHelper;", "getItemTouchHelper", "()Landroidx/recyclerview/widget/ItemTouchHelper;", "setItemTouchHelper", "(Landroidx/recyclerview/widget/ItemTouchHelper;)V", "getList", "()Ljava/util/List;", "setList", "(Ljava/util/List;)V", "mutableDividerIndex", "getMutableDividerIndex", "()I", "getPageLocation", "()Lcom/xiaomi/smarthome/ui/adapter/PageLocation;", "selectItem", "Lcom/xiaomi/smarthome/ui/adapter/SelectItem;", "getSelectItem", "()Lcom/xiaomi/smarthome/ui/adapter/SelectItem;", "setSelectItem", "(Lcom/xiaomi/smarthome/ui/adapter/SelectItem;)V", "getShowCountInFirstPage", "setShowCountInFirstPage", "(I)V", "getItemCount", "getItemViewType", "position", "isOpenOrCloseAllSupported", "onBindViewHolder", "", "viewHolder", "pos", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onItemMove", "fromPosition", "toPosition", "onItemSelectedChanged", "actionState", "onSelectedChanged", "updateData", "newList", "", "Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemTouchListener {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public static final int ITEM_TYPE_BUTTON_GROUP = 998;
    public static final int ITEM_TYPE_DIVIDER = 999;
    public static final int ITEM_TYPE_EDIT = 997;
    @NotNull
    public static final String TAG = "GroupAdapter:";
    @Nullable
    private ItemTouchHelper a;
    @Nullable
    private SelectItem b;
    private final DividerBean c;
    private final ButtonGroupBean d;
    private final EditBean e;
    private boolean f;
    private boolean g;
    @NotNull
    private final CategoryDic h;
    @NotNull
    private List<IDevice> i;
    private int j;
    @NotNull
    private final PageLocation k;

    @Override // com.xiaomi.smarthome.ui.widgets.OnItemTouchListener
    public void onSelectedChanged(int i, int i2) {
    }

    @NotNull
    public final CategoryDic getCategory() {
        return this.h;
    }

    @NotNull
    public final List<IDevice> getList() {
        return this.i;
    }

    public final void setList(@NotNull List<IDevice> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.i = list;
    }

    public /* synthetic */ GroupAdapter(CategoryDic categoryDic, List list, int i, PageLocation pageLocation, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(categoryDic, list, (i2 & 4) != 0 ? categoryDic.getDefaultShowCount() : i, (i2 & 8) != 0 ? PageLocation.PAGE_FIRST : pageLocation);
    }

    public final int getShowCountInFirstPage() {
        return this.j;
    }

    public final void setShowCountInFirstPage(int i) {
        this.j = i;
    }

    @NotNull
    public final PageLocation getPageLocation() {
        return this.k;
    }

    public GroupAdapter(@NotNull CategoryDic category, @NotNull List<IDevice> list, int i, @NotNull PageLocation pageLocation) {
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(pageLocation, "pageLocation");
        this.h = category;
        this.i = list;
        this.j = i;
        this.k = pageLocation;
        this.c = new DividerBean();
        this.d = new ButtonGroupBean();
        this.e = new EditBean();
        if (this.k == PageLocation.PAGE_SECOND && Hardware.isX6A()) {
            if (isOpenOrCloseAllSupported()) {
                this.i.add(0, this.d);
            }
            this.i.add(this.e);
        }
    }

    /* compiled from: GroupAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/xiaomi/smarthome/ui/adapter/GroupAdapter$Companion;", "", "()V", "ITEM_TYPE_BUTTON_GROUP", "", "ITEM_TYPE_DIVIDER", "ITEM_TYPE_EDIT", "TAG", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Nullable
    public final ItemTouchHelper getItemTouchHelper() {
        return this.a;
    }

    public final void setItemTouchHelper(@Nullable ItemTouchHelper itemTouchHelper) {
        this.a = itemTouchHelper;
    }

    @Nullable
    public final SelectItem getSelectItem() {
        return this.b;
    }

    public final void setSelectItem(@Nullable SelectItem selectItem) {
        this.b = selectItem;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        IDevice iDevice = this.i.get(i);
        if (iDevice instanceof DividerBean) {
            return 999;
        }
        return iDevice instanceof ButtonGroupBean ? ITEM_TYPE_BUTTON_GROUP : iDevice instanceof EditBean ? ITEM_TYPE_EDIT : this.h.getType();
    }

    public final int getMutableDividerIndex() {
        return this.i.indexOf(this.c);
    }

    public final boolean isEditMode() {
        return this.f;
    }

    public final void setEditMode(boolean z) {
        if (this.f != z) {
            if (z) {
                if (!this.i.contains(this.c)) {
                    this.i.remove(this.d);
                    this.i.remove(this.e);
                    int size = this.i.size();
                    int i = this.j;
                    if (size <= i) {
                        this.i.add(this.c);
                    } else {
                        this.i.add(i, this.c);
                    }
                    notifyDataSetChanged();
                }
            } else if (this.i.contains(this.c)) {
                if (Hardware.isX6A()) {
                    if (isOpenOrCloseAllSupported()) {
                        this.i.add(0, this.d);
                    }
                    this.i.add(this.e);
                }
                this.i.remove(this.c);
                notifyDataSetChanged();
            }
            this.f = z;
        }
    }

    public final boolean isDataChange() {
        return this.g;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"ClickableViewAccessibility"})
    @NotNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        switch (i) {
            case ITEM_TYPE_EDIT /* 997 */:
                View editView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit, parent, false);
                Intrinsics.checkNotNullExpressionValue(editView, "editView");
                return new EditViewHolder(editView);
            case ITEM_TYPE_BUTTON_GROUP /* 998 */:
                View buttonView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button_group, parent, false);
                Intrinsics.checkNotNullExpressionValue(buttonView, "buttonView");
                return new ButtonGroupViewHolder(buttonView);
            case 999:
                View dividerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider, parent, false);
                Intrinsics.checkNotNullExpressionValue(dividerView, "dividerView");
                return new DividerViewHolder(dividerView);
            default:
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
                switch (this.h) {
                    case CATEGORY_ENV:
                        Context context = parent.getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "parent.context");
                        EnvView envView = new EnvView(context);
                        envView.setLayoutParams(layoutParams);
                        return new EnvironmentViewHolder(envView);
                    case CATEGORY_LIGHT:
                        Context context2 = parent.getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "parent.context");
                        LightView lightView = new LightView(context2);
                        lightView.setLayoutParams(layoutParams);
                        return new LightViewHolder(lightView);
                    case CATEGORY_SOCKET:
                        OSView oSView = new OSView(parent.getContext());
                        oSView.setLayoutParams(layoutParams);
                        return new OutletSwitchViewHolder(oSView);
                    case CATEGORY_CURTAIN:
                        Context context3 = parent.getContext();
                        Intrinsics.checkNotNullExpressionValue(context3, "parent.context");
                        CurtainView curtainView = new CurtainView(context3);
                        curtainView.setLayoutParams(layoutParams);
                        return new CurtainViewHolder(curtainView);
                    case CATEGORY_SECURITY:
                        SecurityView securityView = new SecurityView(parent.getContext());
                        securityView.setLayoutParams(layoutParams);
                        return new SecurityViewHolder(securityView);
                    case CATEGORY_HOUSE_WORK:
                        HouseWorkView houseWorkView = new HouseWorkView(parent.getContext());
                        houseWorkView.setLayoutParams(layoutParams);
                        return new HouseWorkViewHolder(houseWorkView);
                    default:
                        Logger logger = L.smarthome;
                        logger.e("GroupAdapter: not found " + this.h.getTypeName() + " viewholder");
                        Context context4 = parent.getContext();
                        Intrinsics.checkNotNullExpressionValue(context4, "parent.context");
                        EnvView envView2 = new EnvView(context4);
                        envView2.setLayoutParams(layoutParams);
                        return new EnvironmentViewHolder(envView2);
                }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"ClickableViewAccessibility"})
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder viewHolder, int i) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        IDevice iDevice = this.i.get(i);
        if (iDevice instanceof DividerBean) {
            if (this.f) {
                View view = viewHolder.itemView;
                Intrinsics.checkNotNullExpressionValue(view, "viewHolder.itemView");
                view.setVisibility(4);
                MicoAnimationManager.getManager().startShowAnims(viewHolder.itemView);
                MicoAnimationManager.getManager().startLayoutWidthAnim(viewHolder.itemView);
            }
        } else if (iDevice instanceof ButtonGroupBean) {
            View findViewById = viewHolder.itemView.findViewById(R.id.tvOpenAll);
            Intrinsics.checkNotNullExpressionValue(findViewById, "viewHolder.itemView.findViewById(R.id.tvOpenAll)");
            View findViewById2 = viewHolder.itemView.findViewById(R.id.tvCloseAll);
            Intrinsics.checkNotNullExpressionValue(findViewById2, "viewHolder.itemView.findViewById(R.id.tvCloseAll)");
            TextView textView = (TextView) findViewById;
            RxView.clicks(textView).throttleFirst(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new a());
            AnimLifecycleManager.repeatOnAttach(textView, MicoAnimConfigurator4SmallButton.get());
            TextView textView2 = (TextView) findViewById2;
            RxView.clicks(textView2).throttleFirst(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new b());
            AnimLifecycleManager.repeatOnAttach(textView2, MicoAnimConfigurator4SmallButton.get());
        } else if (iDevice instanceof EditBean) {
            View findViewById3 = viewHolder.itemView.findViewById(R.id.ivEdit);
            Intrinsics.checkNotNullExpressionValue(findViewById3, "viewHolder.itemView.findViewById(R.id.ivEdit)");
            ImageView imageView = (ImageView) findViewById3;
            RxView.clicks(imageView).throttleFirst(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new c());
            AnimLifecycleManager.repeatOnAttach(imageView, MicoAnimConfigurator4Edit.get());
        } else if (iDevice != null) {
            DeviceInfoBean deviceInfoBean = (DeviceInfoBean) iDevice;
            if (viewHolder instanceof EnvironmentViewHolder) {
                if (this.k == PageLocation.PAGE_FIRST) {
                    ((EnvironmentViewHolder) viewHolder).getView().bindData(deviceInfoBean, (r17 & 2) != 0 ? false : this.f, this.k, (r17 & 8) != 0 ? null : this.i, this.h.getTypeName(), (r17 & 32) != 0 ? null : null, (r17 & 64) != 0 ? null : null);
                    return;
                }
                ((EnvironmentViewHolder) viewHolder).getView().bindData(deviceInfoBean, (r17 & 2) != 0 ? false : this.f, this.k, (r17 & 8) != 0 ? null : null, this.h.getTypeName(), (r17 & 32) != 0 ? null : this.a, (r17 & 64) != 0 ? null : viewHolder);
            } else if (viewHolder instanceof LightViewHolder) {
                if (this.k == PageLocation.PAGE_FIRST) {
                    ((LightViewHolder) viewHolder).getView().bindData(deviceInfoBean, (r17 & 2) != 0 ? false : this.f, this.k, (r17 & 8) != 0 ? null : this.i, this.h.getTypeName(), (r17 & 32) != 0 ? null : null, (r17 & 64) != 0 ? null : null);
                    return;
                }
                ((LightViewHolder) viewHolder).getView().bindData(deviceInfoBean, (r17 & 2) != 0 ? false : this.f, this.k, (r17 & 8) != 0 ? null : null, this.h.getTypeName(), (r17 & 32) != 0 ? null : this.a, (r17 & 64) != 0 ? null : viewHolder);
            } else if (viewHolder instanceof OutletSwitchViewHolder) {
                if (this.k == PageLocation.PAGE_FIRST) {
                    ((OutletSwitchViewHolder) viewHolder).getView().bindData(this.i, deviceInfoBean, this.f, this.k, this.h.getTypeName(), null, viewHolder);
                } else {
                    ((OutletSwitchViewHolder) viewHolder).getView().bindData(null, deviceInfoBean, this.f, this.k, this.h.getTypeName(), this.a, viewHolder);
                }
            } else if (viewHolder instanceof SecurityViewHolder) {
                if (this.k == PageLocation.PAGE_FIRST) {
                    ((SecurityViewHolder) viewHolder).getView().bindData(this.i, deviceInfoBean, this.f, this.k, this.h.getTypeName(), null, viewHolder);
                } else {
                    ((SecurityViewHolder) viewHolder).getView().bindData(null, deviceInfoBean, this.f, this.k, this.h.getTypeName(), this.a, viewHolder);
                }
            } else if (viewHolder instanceof CurtainViewHolder) {
                if (this.k == PageLocation.PAGE_FIRST) {
                    ((CurtainViewHolder) viewHolder).getView().bindData(deviceInfoBean, (r17 & 2) != 0 ? false : this.f, this.k, (r17 & 8) != 0 ? null : this.i, this.h.getTypeName(), (r17 & 32) != 0 ? null : null, (r17 & 64) != 0 ? null : null);
                    return;
                }
                ((CurtainViewHolder) viewHolder).getView().bindData(deviceInfoBean, (r17 & 2) != 0 ? false : this.f, this.k, (r17 & 8) != 0 ? null : null, this.h.getTypeName(), (r17 & 32) != 0 ? null : this.a, (r17 & 64) != 0 ? null : viewHolder);
            } else if (!(viewHolder instanceof HouseWorkViewHolder)) {
            } else {
                if (this.k == PageLocation.PAGE_FIRST) {
                    ((HouseWorkViewHolder) viewHolder).getView().bindData(this.i, deviceInfoBean, this.f, this.k, this.h.getTypeName(), null, viewHolder);
                } else {
                    ((HouseWorkViewHolder) viewHolder).getView().bindData(null, deviceInfoBean, this.f, this.k, this.h.getTypeName(), this.a, viewHolder);
                }
            }
        } else {
            throw new NullPointerException("null cannot be cast to non-null type com.xiaomi.smarthome.core.entity.DeviceInfoBean");
        }
    }

    /* compiled from: GroupAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a<T> implements Consumer<Unit> {
        a() {
            GroupAdapter.this = r1;
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            SelectItem selectItem = GroupAdapter.this.getSelectItem();
            Intrinsics.checkNotNull(selectItem);
            selectItem.onClick(true);
        }
    }

    /* compiled from: GroupAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class b<T> implements Consumer<Unit> {
        b() {
            GroupAdapter.this = r1;
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            SelectItem selectItem = GroupAdapter.this.getSelectItem();
            Intrinsics.checkNotNull(selectItem);
            selectItem.onClick(false);
        }
    }

    /* compiled from: GroupAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "kotlin.jvm.PlatformType", "accept", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class c<T> implements Consumer<Unit> {
        c() {
            GroupAdapter.this = r1;
        }

        /* renamed from: a */
        public final void accept(Unit unit) {
            SelectItem selectItem = GroupAdapter.this.getSelectItem();
            Intrinsics.checkNotNull(selectItem);
            selectItem.onEdit();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.i.size();
    }

    public final void updateData(@NotNull List<DeviceInfoBean> newList) {
        Intrinsics.checkNotNullParameter(newList, "newList");
        this.i.clear();
        this.i.addAll(newList);
        notifyDataSetChanged();
    }

    public final boolean isOpenOrCloseAllSupported() {
        return (this.h == CategoryDic.CATEGORY_SOCKET || this.h == CategoryDic.CATEGORY_HOUSE_WORK || this.h == CategoryDic.CATEGORY_SECURITY || this.h == CategoryDic.CATEGORY_MEDIA) ? false : true;
    }

    @Override // com.xiaomi.smarthome.ui.widgets.OnItemTouchListener
    public void onItemMove(int i, int i2) {
        if (getMutableDividerIndex() > 1 || i != 0) {
            if (i < i2) {
                int i3 = i;
                while (i3 < i2) {
                    int i4 = i3 + 1;
                    Collections.swap(this.i, i3, i4);
                    i3 = i4;
                }
            } else {
                int i5 = i2 + 1;
                if (i >= i5) {
                    int i6 = i;
                    while (true) {
                        Collections.swap(this.i, i6, i6 - 1);
                        if (i6 == i5) {
                            break;
                        }
                        i6--;
                    }
                }
            }
            this.g = true;
            notifyItemMoved(i, i2);
        }
    }

    @Override // com.xiaomi.smarthome.ui.widgets.OnItemTouchListener
    public void onItemSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
        if (i == 2 && getMutableDividerIndex() == 1 && viewHolder != null && viewHolder.getLayoutPosition() == 0) {
            ToastUtil.showToast(R.string.tip_can_not_drag);
        }
    }
}
