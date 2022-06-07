package app.dinus.com.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class PinnedHeaderDecoration extends RecyclerView.ItemDecoration {
    private int b;
    private boolean c;
    private Rect d;
    private View e;
    private RecyclerView.Adapter f;
    private final SparseArray<PinnedHeaderCreator> g = new SparseArray<>();
    private final RecyclerView.AdapterDataObserver h = new RecyclerView.AdapterDataObserver() { // from class: app.dinus.com.itemdecoration.PinnedHeaderDecoration.1
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            PinnedHeaderDecoration.this.c = true;
        }
    };
    private int a = -1;

    /* loaded from: classes.dex */
    public interface PinnedHeaderCreator {
        boolean create(RecyclerView recyclerView, int i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        a(recyclerView);
        View view = this.e;
        if (view != null) {
            View findChildViewUnder = recyclerView.findChildViewUnder(canvas.getWidth() / 2, view.getTop() + this.e.getHeight() + 1);
            if (a(recyclerView, findChildViewUnder)) {
                this.b = findChildViewUnder.getTop() - this.e.getHeight();
            } else {
                this.b = 0;
            }
            this.d = canvas.getClipBounds();
            this.d.top = this.b + this.e.getHeight();
            canvas.clipRect(this.d);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.e != null) {
            canvas.save();
            Rect rect = this.d;
            rect.top = 0;
            canvas.clipRect(rect, Region.Op.UNION);
            canvas.translate(0.0f, this.b);
            this.e.draw(canvas);
            canvas.restore();
        }
    }

    private void a(RecyclerView recyclerView) {
        int a;
        b(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null && layoutManager.getChildCount() > 0 && (a = a(recyclerView, ((RecyclerView.LayoutParams) layoutManager.getChildAt(0).getLayoutParams()).getViewAdapterPosition())) >= 0 && this.a != a) {
            this.a = a;
            RecyclerView.ViewHolder createViewHolder = this.f.createViewHolder(recyclerView, this.f.getItemViewType(a));
            this.f.bindViewHolder(createViewHolder, a);
            this.e = createViewHolder.itemView;
            ViewGroup.LayoutParams layoutParams = this.e.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(-1, -2);
                this.e.setLayoutParams(layoutParams);
            }
            int mode = View.MeasureSpec.getMode(layoutParams.height);
            int size = View.MeasureSpec.getSize(layoutParams.height);
            if (mode == 0) {
                mode = 1073741824;
            }
            int height = (recyclerView.getHeight() - recyclerView.getPaddingTop()) - recyclerView.getPaddingBottom();
            if (size > height) {
                size = height;
            }
            this.e.measure(View.MeasureSpec.makeMeasureSpec((recyclerView.getWidth() - recyclerView.getPaddingLeft()) - recyclerView.getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec(size, mode));
            View view = this.e;
            view.layout(0, 0, view.getMeasuredWidth(), this.e.getMeasuredHeight());
        }
    }

    private int a(RecyclerView recyclerView, int i) {
        if (i > this.f.getItemCount() || i < 0) {
            return -1;
        }
        while (i >= 0) {
            if (a(recyclerView, i, this.f.getItemViewType(i))) {
                return i;
            }
            i--;
        }
        return -1;
    }

    private boolean a(RecyclerView recyclerView, int i, int i2) {
        PinnedHeaderCreator pinnedHeaderCreator = this.g.get(i2);
        return pinnedHeaderCreator != null && pinnedHeaderCreator.create(recyclerView, i);
    }

    private boolean a(RecyclerView recyclerView, View view) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition == -1) {
            return false;
        }
        return a(recyclerView, childAdapterPosition, this.f.getItemViewType(childAdapterPosition));
    }

    private void b(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (this.f != adapter || this.c) {
            a();
            RecyclerView.Adapter adapter2 = this.f;
            if (adapter2 != null) {
                adapter2.unregisterAdapterDataObserver(this.h);
            }
            this.f = adapter;
            RecyclerView.Adapter adapter3 = this.f;
            if (adapter3 != null) {
                adapter3.registerAdapterDataObserver(this.h);
            }
        }
    }

    private void a() {
        this.a = -1;
        this.e = null;
    }

    public void registerTypePinnedHeader(int i, PinnedHeaderCreator pinnedHeaderCreator) {
        this.g.put(i, pinnedHeaderCreator);
    }
}
