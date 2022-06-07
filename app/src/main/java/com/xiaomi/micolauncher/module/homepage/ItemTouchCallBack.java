package com.xiaomi.micolauncher.module.homepage;

import android.graphics.Canvas;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.homepage.adapter.AppAdapter;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppAlarmViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppGalleryViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppWeatherViewHolder;

/* loaded from: classes3.dex */
public class ItemTouchCallBack extends ItemTouchHelper.Callback {
    private OnItemTouchListener a;
    private boolean b;

    /* loaded from: classes3.dex */
    public interface OnItemTouchListener {
        void onMove(int i, int i2);
    }

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.a = onItemTouchListener;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (!(recyclerView.getLayoutManager() instanceof GridLayoutManager)) {
            return makeMovementFlags(3, 8);
        }
        if ((viewHolder instanceof AppAlarmViewHolder) || (viewHolder instanceof AppGalleryViewHolder) || (viewHolder instanceof AppWeatherViewHolder)) {
            return makeMovementFlags(0, 0);
        }
        return makeMovementFlags(15, 0);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2) {
        this.a.onMove(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
        return true;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
        float f3;
        float f4;
        float top = viewHolder.itemView.getTop() + f2;
        float height = viewHolder.itemView.getHeight() + top;
        if (top < 0.0f) {
            f3 = 0.0f;
        } else {
            f3 = height > ((float) recyclerView.getHeight()) ? (recyclerView.getHeight() - viewHolder.itemView.getHeight()) - viewHolder.itemView.getTop() : f2;
        }
        float left = viewHolder.itemView.getLeft() + f;
        float width = viewHolder.itemView.getWidth() + left;
        if (left < 0.0f) {
            f4 = 0.0f;
        } else {
            f4 = width > ((float) recyclerView.getWidth()) ? (recyclerView.getWidth() - viewHolder.itemView.getWidth()) - viewHolder.itemView.getLeft() : f;
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, f4, f3, i, z);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        L.skillpage.i("onSwiped............");
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
        super.onSelectedChanged(viewHolder, i);
        L.skillpage.i("onSelectedChanged .......... ");
        if (this.b) {
            ((AppAdapter) this.a).notifyDataSetChanged();
            this.b = false;
        }
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull RecyclerView.ViewHolder viewHolder2, int i2, int i3, int i4) {
        super.onMoved(recyclerView, viewHolder, i, viewHolder2, i2, i3, i4);
        this.b = true;
    }
}
