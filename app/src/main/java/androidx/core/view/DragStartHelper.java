package androidx.core.view;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes.dex */
public class DragStartHelper {
    private final View a;
    private final OnDragStartListener b;
    private int c;
    private int d;
    private boolean e;
    private final View.OnLongClickListener f = new View.OnLongClickListener() { // from class: androidx.core.view.DragStartHelper.1
        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            return DragStartHelper.this.onLongClick(view);
        }
    };
    private final View.OnTouchListener g = new View.OnTouchListener() { // from class: androidx.core.view.DragStartHelper.2
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return DragStartHelper.this.onTouch(view, motionEvent);
        }
    };

    /* loaded from: classes.dex */
    public interface OnDragStartListener {
        boolean onDragStart(View view, DragStartHelper dragStartHelper);
    }

    public DragStartHelper(View view, OnDragStartListener onDragStartListener) {
        this.a = view;
        this.b = onDragStartListener;
    }

    public void attach() {
        this.a.setOnLongClickListener(this.f);
        this.a.setOnTouchListener(this.g);
    }

    public void detach() {
        this.a.setOnLongClickListener(null);
        this.a.setOnTouchListener(null);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        switch (motionEvent.getAction()) {
            case 0:
                this.c = x;
                this.d = y;
                break;
            case 1:
            case 3:
                this.e = false;
                break;
            case 2:
                if (MotionEventCompat.isFromSource(motionEvent, 8194) && (motionEvent.getButtonState() & 1) != 0 && !this.e && !(this.c == x && this.d == y)) {
                    this.c = x;
                    this.d = y;
                    this.e = this.b.onDragStart(view, this);
                    return this.e;
                }
                break;
        }
        return false;
    }

    public boolean onLongClick(View view) {
        return this.b.onDragStart(view, this);
    }

    public void getTouchPosition(Point point) {
        point.set(this.c, this.d);
    }
}
