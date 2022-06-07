package com.chad.library.adapter.base;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.R;
import com.chad.library.adapter.base.listener.IDraggableListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import java.util.Collections;

/* loaded from: classes.dex */
public class DraggableController implements IDraggableListener {
    private static final int NO_TOGGLE_VIEW = 0;
    private BaseQuickAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private OnItemDragListener mOnItemDragListener;
    private OnItemSwipeListener mOnItemSwipeListener;
    private View.OnLongClickListener mOnToggleViewLongClickListener;
    private View.OnTouchListener mOnToggleViewTouchListener;
    private int mToggleViewId = 0;
    private boolean itemDragEnabled = false;
    private boolean itemSwipeEnabled = false;
    private boolean mDragOnLongPress = true;

    public DraggableController(BaseQuickAdapter baseQuickAdapter) {
        this.mAdapter = baseQuickAdapter;
    }

    public void initView(BaseViewHolder baseViewHolder) {
        View view;
        int itemViewType = baseViewHolder.getItemViewType();
        if (this.mItemTouchHelper != null && this.itemDragEnabled && itemViewType != 546 && itemViewType != 273 && itemViewType != 1365 && itemViewType != 819 && hasToggleView() && (view = baseViewHolder.getView(this.mToggleViewId)) != null) {
            view.setTag(R.id.BaseQuickAdapter_viewholder_support, baseViewHolder);
            if (this.mDragOnLongPress) {
                view.setOnLongClickListener(this.mOnToggleViewLongClickListener);
            } else {
                view.setOnTouchListener(this.mOnToggleViewTouchListener);
            }
        }
    }

    public void setToggleViewId(int i) {
        this.mToggleViewId = i;
    }

    public void setToggleDragOnLongPress(boolean z) {
        this.mDragOnLongPress = z;
        if (this.mDragOnLongPress) {
            this.mOnToggleViewTouchListener = null;
            this.mOnToggleViewLongClickListener = new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.DraggableController.1
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    if (DraggableController.this.mItemTouchHelper == null || !DraggableController.this.itemDragEnabled) {
                        return true;
                    }
                    DraggableController.this.mItemTouchHelper.startDrag((RecyclerView.ViewHolder) view.getTag(R.id.BaseQuickAdapter_viewholder_support));
                    return true;
                }
            };
            return;
        }
        this.mOnToggleViewTouchListener = new View.OnTouchListener() { // from class: com.chad.library.adapter.base.DraggableController.2
            @Override // android.view.View.OnTouchListener
            @SuppressLint({"ClickableViewAccessibility"})
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEventCompat.getActionMasked(motionEvent) != 0 || DraggableController.this.mDragOnLongPress) {
                    return false;
                }
                if (DraggableController.this.mItemTouchHelper == null || !DraggableController.this.itemDragEnabled) {
                    return true;
                }
                DraggableController.this.mItemTouchHelper.startDrag((RecyclerView.ViewHolder) view.getTag(R.id.BaseQuickAdapter_viewholder_support));
                return true;
            }
        };
        this.mOnToggleViewLongClickListener = null;
    }

    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper) {
        enableDragItem(itemTouchHelper, 0, true);
    }

    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper, int i) {
        enableDragItem(itemTouchHelper, i, true);
    }

    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper, int i, boolean z) {
        this.itemDragEnabled = true;
        this.mItemTouchHelper = itemTouchHelper;
        setToggleViewId(i);
        setToggleDragOnLongPress(z);
    }

    public void disableDragItem() {
        this.itemDragEnabled = false;
        this.mItemTouchHelper = null;
    }

    @Override // com.chad.library.adapter.base.listener.IDraggableListener
    public boolean isItemDraggable() {
        return this.itemDragEnabled;
    }

    @Override // com.chad.library.adapter.base.listener.IDraggableListener
    public boolean hasToggleView() {
        return this.mToggleViewId != 0;
    }

    public void enableSwipeItem() {
        this.itemSwipeEnabled = true;
    }

    public void disableSwipeItem() {
        this.itemSwipeEnabled = false;
    }

    @Override // com.chad.library.adapter.base.listener.IDraggableListener
    public boolean isItemSwipeEnable() {
        return this.itemSwipeEnabled;
    }

    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        this.mOnItemDragListener = onItemDragListener;
    }

    public int getViewHolderPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() - this.mAdapter.getHeaderLayoutCount();
    }

    @Override // com.chad.library.adapter.base.listener.IDraggableListener
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder) {
        OnItemDragListener onItemDragListener = this.mOnItemDragListener;
        if (onItemDragListener != null && this.itemDragEnabled) {
            onItemDragListener.onItemDragStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    @Override // com.chad.library.adapter.base.listener.IDraggableListener
    public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        int viewHolderPosition = getViewHolderPosition(viewHolder);
        int viewHolderPosition2 = getViewHolderPosition(viewHolder2);
        if (inRange(viewHolderPosition) && inRange(viewHolderPosition2)) {
            if (viewHolderPosition < viewHolderPosition2) {
                int i = viewHolderPosition;
                while (i < viewHolderPosition2) {
                    int i2 = i + 1;
                    Collections.swap(this.mAdapter.getData(), i, i2);
                    i = i2;
                }
            } else {
                for (int i3 = viewHolderPosition; i3 > viewHolderPosition2; i3--) {
                    Collections.swap(this.mAdapter.getData(), i3, i3 - 1);
                }
            }
            this.mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
        }
        OnItemDragListener onItemDragListener = this.mOnItemDragListener;
        if (onItemDragListener != null && this.itemDragEnabled) {
            onItemDragListener.onItemDragMoving(viewHolder, viewHolderPosition, viewHolder2, viewHolderPosition2);
        }
    }

    @Override // com.chad.library.adapter.base.listener.IDraggableListener
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder) {
        OnItemDragListener onItemDragListener = this.mOnItemDragListener;
        if (onItemDragListener != null && this.itemDragEnabled) {
            onItemDragListener.onItemDragEnd(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    public void setOnItemSwipeListener(OnItemSwipeListener onItemSwipeListener) {
        this.mOnItemSwipeListener = onItemSwipeListener;
    }

    @Override // com.chad.library.adapter.base.listener.IDraggableListener
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder) {
        OnItemSwipeListener onItemSwipeListener = this.mOnItemSwipeListener;
        if (onItemSwipeListener != null && this.itemSwipeEnabled) {
            onItemSwipeListener.onItemSwipeStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    @Override // com.chad.library.adapter.base.listener.IDraggableListener
    public void onItemSwipeClear(RecyclerView.ViewHolder viewHolder) {
        OnItemSwipeListener onItemSwipeListener = this.mOnItemSwipeListener;
        if (onItemSwipeListener != null && this.itemSwipeEnabled) {
            onItemSwipeListener.clearView(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    @Override // com.chad.library.adapter.base.listener.IDraggableListener
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        OnItemSwipeListener onItemSwipeListener = this.mOnItemSwipeListener;
        if (onItemSwipeListener != null && this.itemSwipeEnabled) {
            onItemSwipeListener.onItemSwiped(viewHolder, getViewHolderPosition(viewHolder));
        }
        int viewHolderPosition = getViewHolderPosition(viewHolder);
        if (inRange(viewHolderPosition)) {
            this.mAdapter.getData().remove(viewHolderPosition);
            this.mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    }

    @Override // com.chad.library.adapter.base.listener.IDraggableListener
    public void onItemSwiping(Canvas canvas, RecyclerView.ViewHolder viewHolder, float f, float f2, boolean z) {
        OnItemSwipeListener onItemSwipeListener = this.mOnItemSwipeListener;
        if (onItemSwipeListener != null && this.itemSwipeEnabled) {
            onItemSwipeListener.onItemSwipeMoving(canvas, viewHolder, f, f2, z);
        }
    }

    private boolean inRange(int i) {
        return i >= 0 && i < this.mAdapter.getData().size();
    }
}
