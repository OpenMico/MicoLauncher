package app.dinus.com.itemdecoration;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class LinearOffsetsItemDecoration extends RecyclerView.ItemDecoration {
    public static final int LINEAR_OFFSETS_HORIZONTAL = 0;
    public static final int LINEAR_OFFSETS_VERTICAL = 1;
    private final SparseArray<OffsetsCreator> a = new SparseArray<>();
    private int b;
    private int c;

    /* loaded from: classes.dex */
    public interface OffsetsCreator {
        int create(RecyclerView recyclerView, int i);
    }

    public LinearOffsetsItemDecoration(int i) {
        this.b = i;
    }

    public void setOrientation(int i) {
        this.b = i;
    }

    public void setItemOffsets(int i) {
        this.c = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (recyclerView.getChildAdapterPosition(view) != recyclerView.getAdapter().getItemCount() - 1) {
            if (this.b == 0) {
                rect.right = a(recyclerView, view);
            } else {
                rect.bottom = a(recyclerView, view);
            }
        }
    }

    private int a(RecyclerView recyclerView, View view) {
        if (this.a.size() == 0) {
            return this.c;
        }
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        OffsetsCreator offsetsCreator = this.a.get(recyclerView.getAdapter().getItemViewType(childAdapterPosition));
        if (offsetsCreator != null) {
            return offsetsCreator.create(recyclerView, childAdapterPosition);
        }
        return 0;
    }

    public void registerTypeOffset(int i, OffsetsCreator offsetsCreator) {
        this.a.put(i, offsetsCreator);
    }
}
