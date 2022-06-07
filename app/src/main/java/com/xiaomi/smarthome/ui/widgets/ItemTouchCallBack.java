package com.xiaomi.smarthome.ui.widgets;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ItemTouchCallBack.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J \u0010\u0013\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012H\u0016J@\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004H\u0016J\u001a\u0010\u001b\u001a\u00020\u00162\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001c\u001a\u00020\u0004H\u0016J\u0018\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/ItemTouchCallBack;", "Landroidx/recyclerview/widget/ItemTouchHelper$Callback;", "()V", "fromPosition", "", "isMoved", "", "listener", "Lcom/xiaomi/smarthome/ui/widgets/OnItemTouchListener;", "getListener", "()Lcom/xiaomi/smarthome/ui/widgets/OnItemTouchListener;", "setListener", "(Lcom/xiaomi/smarthome/ui/widgets/OnItemTouchListener;)V", "toPosition", "getMovementFlags", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "viewHolder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "onMove", OneTrackWorldUrl.ACTION_TARGET, "onMoved", "", "fromPos", "toPos", "x", "y", "onSelectedChanged", "actionState", "onSwiped", ai.aA, "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class ItemTouchCallBack extends ItemTouchHelper.Callback {
    @Nullable
    private OnItemTouchListener a;
    private boolean b;
    private int c;
    private int d;

    @Nullable
    public final OnItemTouchListener getListener() {
        return this.a;
    }

    public final void setListener(@Nullable OnItemTouchListener onItemTouchListener) {
        this.a = onItemTouchListener;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        if ((recyclerView.getLayoutManager() instanceof GridLayoutManager) || (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager)) {
            return ItemTouchHelper.Callback.makeMovementFlags(15, 0);
        }
        return ItemTouchHelper.Callback.makeMovementFlags(12, 0);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder, @NotNull RecyclerView.ViewHolder target) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        Intrinsics.checkNotNullParameter(target, "target");
        this.c = viewHolder.getLayoutPosition();
        this.d = target.getLayoutPosition();
        OnItemTouchListener onItemTouchListener = this.a;
        if (onItemTouchListener == null) {
            return true;
        }
        onItemTouchListener.onItemMove(this.c, this.d);
        return true;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(@NotNull RecyclerView.ViewHolder viewHolder, int i) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        L.smarthome.i("ItemTouchCallBack onSwiped............");
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
        OnItemTouchListener onItemTouchListener;
        super.onSelectedChanged(viewHolder, i);
        L.smarthome.i("ItemTouchCallBack onSelectedChanged .......... ");
        if (this.b) {
            this.b = false;
        }
        OnItemTouchListener onItemTouchListener2 = this.a;
        if (onItemTouchListener2 != null) {
            onItemTouchListener2.onItemSelectedChanged(viewHolder, i);
        }
        if (i == 0 && (onItemTouchListener = this.a) != null) {
            onItemTouchListener.onSelectedChanged(this.c, this.d);
        }
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onMoved(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder, int i, @NotNull RecyclerView.ViewHolder target, int i2, int i3, int i4) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        Intrinsics.checkNotNullParameter(target, "target");
        super.onMoved(recyclerView, viewHolder, i, target, i2, i3, i4);
        this.b = true;
    }
}
