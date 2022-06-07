package com.xiaomi.smarthome.ui.adapter;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.SpanUtils;
import com.umeng.analytics.pro.ai;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.db.CategoryRankEntity;
import com.xiaomi.smarthome.ui.widgets.OnItemTouchListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CategoryListAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0017H\u0016J\u0018\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0017H\u0016J\u0018\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u0017H\u0016J\u001a\u0010#\u001a\u00020\u00192\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020\u0017H\u0016J\u0018\u0010'\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u0017H\u0016J\u0014\u0010(\u001a\u00020\u00192\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00070*R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001e\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006+"}, d2 = {"Lcom/xiaomi/smarthome/ui/adapter/CategoryListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/xiaomi/smarthome/ui/adapter/CategoryViewHolder;", "Lcom/xiaomi/smarthome/ui/widgets/OnItemTouchListener;", "()V", "categoryList", "", "Lcom/xiaomi/smarthome/core/db/CategoryRankEntity;", "getCategoryList", "()Ljava/util/List;", "categoryList$delegate", "Lkotlin/Lazy;", "<set-?>", "", "isDataChanged", "()Z", "touchHelper", "Landroidx/recyclerview/widget/ItemTouchHelper;", "getTouchHelper", "()Landroidx/recyclerview/widget/ItemTouchHelper;", "setTouchHelper", "(Landroidx/recyclerview/widget/ItemTouchHelper;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "pos", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onItemMove", "fromPosition", "toPosition", "onItemSelectedChanged", "viewHolder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "actionState", "onSelectedChanged", "updateData", "list", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class CategoryListAdapter extends RecyclerView.Adapter<CategoryViewHolder> implements OnItemTouchListener {
    @NotNull
    private final Lazy a = LazyKt.lazy(a.a);
    private boolean b;
    @Nullable
    private ItemTouchHelper c;

    @NotNull
    public final List<CategoryRankEntity> getCategoryList() {
        return (List) this.a.getValue();
    }

    @Override // com.xiaomi.smarthome.ui.widgets.OnItemTouchListener
    public void onItemSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
    }

    /* compiled from: CategoryListAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/xiaomi/smarthome/core/db/CategoryRankEntity;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a extends Lambda implements Function0<List<CategoryRankEntity>> {
        public static final a a = new a();

        a() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final List<CategoryRankEntity> invoke() {
            return new ArrayList();
        }
    }

    public final boolean isDataChanged() {
        return this.b;
    }

    @Nullable
    public final ItemTouchHelper getTouchHelper() {
        return this.c;
    }

    public final void setTouchHelper(@Nullable ItemTouchHelper itemTouchHelper) {
        this.c = itemTouchHelper;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public CategoryViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return new CategoryViewHolder(view);
    }

    public void onBindViewHolder(@NotNull CategoryViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        CategoryRankEntity categoryRankEntity = getCategoryList().get(i);
        holder.getIvPic().setImageResource(categoryRankEntity.getIconRes());
        holder.getIvDragHandle().setOnTouchListener(new b(holder));
        SpanUtils.with(holder.getTvName()).append(categoryRankEntity.getCategoryName()).append(StringUtils.SPACE).append(String.valueOf(categoryRankEntity.getDeviceCount())).create();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CategoryListAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", ai.aC, "Landroid/view/View;", "kotlin.jvm.PlatformType", "event", "Landroid/view/MotionEvent;", "onTouch"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class b implements View.OnTouchListener {
        final /* synthetic */ CategoryViewHolder b;

        b(CategoryViewHolder categoryViewHolder) {
            this.b = categoryViewHolder;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            ItemTouchHelper touchHelper;
            CategoryViewHolder categoryViewHolder = this.b;
            if (categoryViewHolder == null || (touchHelper = CategoryListAdapter.this.getTouchHelper()) == null) {
                return false;
            }
            touchHelper.startDrag(categoryViewHolder);
            return false;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return getCategoryList().size();
    }

    public final void updateData(@NotNull List<CategoryRankEntity> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        getCategoryList().clear();
        getCategoryList().addAll(list);
        notifyDataSetChanged();
    }

    @Override // com.xiaomi.smarthome.ui.widgets.OnItemTouchListener
    public void onItemMove(int i, int i2) {
        if (i < i2) {
            int i3 = i;
            while (i3 < i2) {
                int i4 = i3 + 1;
                Collections.swap(getCategoryList(), i3, i4);
                i3 = i4;
            }
        } else {
            int i5 = i2 + 1;
            if (i >= i5) {
                int i6 = i;
                while (true) {
                    Collections.swap(getCategoryList(), i6, i6 - 1);
                    if (i6 == i5) {
                        break;
                    }
                    i6--;
                }
            }
        }
        this.b = true;
        notifyItemMoved(i, i2);
    }

    @Override // com.xiaomi.smarthome.ui.widgets.OnItemTouchListener
    public void onSelectedChanged(int i, int i2) {
        notifyItemRangeChanged(Math.min(i, i2), Math.abs(i - i2) + 1);
    }
}
