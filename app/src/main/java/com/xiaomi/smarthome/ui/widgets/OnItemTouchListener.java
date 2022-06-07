package com.xiaomi.smarthome.ui.widgets;

import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

/* compiled from: ItemTouchCallBack.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\u001a\u0010\u0007\u001a\u00020\u00032\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u0005H&J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\f"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/OnItemTouchListener;", "", "onItemMove", "", "fromPosition", "", "toPosition", "onItemSelectedChanged", "viewHolder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "actionState", "onSelectedChanged", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public interface OnItemTouchListener {
    void onItemMove(int i, int i2);

    void onItemSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i);

    void onSelectedChanged(int i, int i2);
}
