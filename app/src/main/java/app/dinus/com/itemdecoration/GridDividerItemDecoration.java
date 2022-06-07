package app.dinus.com.itemdecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/* loaded from: classes.dex */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int GRID_DIVIDER_HORIZONTAL = 0;
    public static final int GRID_DIVIDER_VERTICAL = 1;
    private final SparseIntArray b = new SparseIntArray();
    private final SparseIntArray c = new SparseIntArray();
    private final SparseArray<DrawableCreator> d = new SparseArray<>();
    private int e;
    private Drawable f;
    private Drawable g;
    private static final int[] a = {16843284};
    public static final Drawable EMPTY_DIVIDER = new ColorDrawable(0);

    /* loaded from: classes.dex */
    public interface DrawableCreator {
        Drawable createHorizontal(RecyclerView recyclerView, int i);

        Drawable createVertical(RecyclerView recyclerView, int i);
    }

    public GridDividerItemDecoration(Context context, int i) {
        a(context);
        setOrientation(i);
    }

    private void a(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(a);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        this.f = drawable;
        this.g = drawable;
        obtainStyledAttributes.recycle();
    }

    public void setOrientation(int i) {
        this.e = i;
    }

    public void setVerticalDivider(Drawable drawable) {
        this.g = drawable;
    }

    public void setHorizontalDivider(Drawable drawable) {
        this.f = drawable;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        drawHorizontalDividers(canvas, recyclerView);
        drawVerticalDividers(canvas, recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int a2 = a(recyclerView);
        int itemCount = recyclerView.getAdapter().getItemCount();
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        if (this.b.indexOfKey(childAdapterPosition) < 0) {
            this.b.put(childAdapterPosition, b(recyclerView, childAdapterPosition).getIntrinsicHeight());
        }
        if (this.c.indexOfKey(childAdapterPosition) < 0) {
            this.c.put(childAdapterPosition, a(recyclerView, childAdapterPosition).getIntrinsicHeight());
        }
        rect.set(0, 0, this.b.get(childAdapterPosition), this.c.get(childAdapterPosition));
        if (b(childAdapterPosition, a2, itemCount)) {
            rect.bottom = 0;
        }
        if (a(childAdapterPosition, a2, itemCount)) {
            rect.right = 0;
        }
    }

    private boolean a(int i, int i2, int i3) {
        if (this.e == 1) {
            return (i + 1) % i2 == 0;
        }
        int i4 = i3 % i2;
        if (i4 != 0) {
            i2 = i4;
        }
        return i >= i3 - i2;
    }

    private boolean b(int i, int i2, int i3) {
        if (this.e != 1) {
            return (i + 1) % i2 == 0;
        }
        int i4 = i3 % i2;
        if (i4 != 0) {
            i2 = i4;
        }
        return i >= i3 - i2;
    }

    private int a(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        throw new UnsupportedOperationException("the GridDividerItemDecoration can only be used in the RecyclerView which use a GridLayoutManager or StaggeredGridLayoutManager");
    }

    public void drawVerticalDividers(Canvas canvas, RecyclerView recyclerView) {
        int paddingLeft = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            Drawable a2 = a(recyclerView, layoutParams.getViewAdapterPosition());
            int bottom = childAt.getBottom() + layoutParams.bottomMargin + Math.round(ViewCompat.getTranslationY(childAt));
            this.c.put(layoutParams.getViewAdapterPosition(), a2.getIntrinsicHeight());
            a2.setBounds(paddingLeft, bottom, width, a2.getIntrinsicHeight() + bottom);
            a2.draw(canvas);
        }
    }

    public void drawHorizontalDividers(Canvas canvas, RecyclerView recyclerView) {
        int paddingTop = recyclerView.getPaddingTop();
        int height = recyclerView.getHeight() - recyclerView.getPaddingBottom();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            Drawable b = b(recyclerView, layoutParams.getViewAdapterPosition());
            int right = childAt.getRight() + layoutParams.rightMargin + Math.round(ViewCompat.getTranslationX(childAt));
            this.b.put(layoutParams.getViewAdapterPosition(), b.getIntrinsicHeight());
            b.setBounds(right, paddingTop, b.getIntrinsicHeight() + right, height);
            b.draw(canvas);
        }
    }

    private Drawable a(RecyclerView recyclerView, int i) {
        DrawableCreator drawableCreator = this.d.get(recyclerView.getAdapter().getItemViewType(i));
        if (drawableCreator != null) {
            return drawableCreator.createVertical(recyclerView, i);
        }
        return this.g;
    }

    private Drawable b(RecyclerView recyclerView, int i) {
        DrawableCreator drawableCreator = this.d.get(recyclerView.getAdapter().getItemViewType(i));
        if (drawableCreator != null) {
            return drawableCreator.createHorizontal(recyclerView, i);
        }
        return this.f;
    }

    public void registerTypeDrawable(int i, DrawableCreator drawableCreator) {
        this.d.put(i, drawableCreator);
    }
}
