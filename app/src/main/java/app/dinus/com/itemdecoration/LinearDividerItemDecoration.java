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
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class LinearDividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int LINEAR_DIVIDER_HORIZONTAL = 0;
    public static final int LINEAR_DIVIDER_VERTICAL = 1;
    private final SparseIntArray b = new SparseIntArray();
    private final SparseArray<DrawableCreator> c = new SparseArray<>();
    private int d;
    private Drawable e;
    private static final int[] a = {16843284};
    public static final Drawable EMPTY_DIVIDER = new ColorDrawable(0);

    /* loaded from: classes.dex */
    public interface DrawableCreator {
        Drawable create(RecyclerView recyclerView, int i);
    }

    public LinearDividerItemDecoration(Context context, int i) {
        a(context);
        setOrientation(i);
    }

    private void a(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(a);
        this.e = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
    }

    public void setOrientation(int i) {
        this.d = i;
    }

    public void setDivider(Drawable drawable) {
        this.e = drawable;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.d == 1) {
            drawVerticalDividers(canvas, recyclerView);
        } else {
            drawHorizontalDividers(canvas, recyclerView);
        }
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
            this.b.put(layoutParams.getViewAdapterPosition(), a2.getIntrinsicHeight());
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
            Drawable a2 = a(recyclerView, layoutParams.getViewAdapterPosition());
            int right = childAt.getRight() + layoutParams.rightMargin + Math.round(ViewCompat.getTranslationX(childAt));
            this.b.put(layoutParams.getViewAdapterPosition(), a2.getIntrinsicHeight());
            a2.setBounds(right, paddingTop, a2.getIntrinsicHeight() + right, height);
            a2.draw(canvas);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition != recyclerView.getAdapter().getItemCount() - 1) {
            if (this.b.indexOfKey(childAdapterPosition) < 0) {
                this.b.put(childAdapterPosition, a(recyclerView, childAdapterPosition).getIntrinsicHeight());
            }
            if (this.d == 1) {
                rect.set(0, 0, 0, this.b.get(recyclerView.getChildAdapterPosition(view)));
            } else {
                rect.set(0, 0, this.b.get(recyclerView.getChildAdapterPosition(view)), 0);
            }
        }
    }

    private Drawable a(RecyclerView recyclerView, int i) {
        DrawableCreator drawableCreator = this.c.get(recyclerView.getAdapter().getItemViewType(i));
        if (drawableCreator != null) {
            return drawableCreator.create(recyclerView, i);
        }
        return this.e;
    }

    public void registerTypeDrawable(int i, DrawableCreator drawableCreator) {
        this.c.put(i, drawableCreator);
    }
}
