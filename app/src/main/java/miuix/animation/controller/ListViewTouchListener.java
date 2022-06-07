package miuix.animation.controller;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes5.dex */
public class ListViewTouchListener implements View.OnTouchListener {
    private WeakHashMap<View, View.OnTouchListener> a = new WeakHashMap<>();
    private Rect b = new Rect();
    private float c = Float.MAX_VALUE;
    private float d = Float.MAX_VALUE;
    private int e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ListViewTouchListener(ViewGroup viewGroup) {
        this.e = ViewConfiguration.get(viewGroup.getContext()).getScaledTouchSlop();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.c = motionEvent.getRawX();
            this.d = motionEvent.getRawY();
        } else if (actionMasked != 2) {
            this.d = Float.MAX_VALUE;
            this.c = Float.MAX_VALUE;
        } else {
            z = motionEvent.getRawY() - this.d > ((float) this.e) || motionEvent.getRawX() - this.c > ((float) this.e);
            a((ViewGroup) view, motionEvent, z);
            return false;
        }
        z = false;
        a((ViewGroup) view, motionEvent, z);
        return false;
    }

    public void putListener(View view, View.OnTouchListener onTouchListener) {
        this.a.put(view, onTouchListener);
    }

    private void a(ViewGroup viewGroup, MotionEvent motionEvent, boolean z) {
        View a = a(viewGroup, motionEvent);
        for (Map.Entry<View, View.OnTouchListener> entry : this.a.entrySet()) {
            View key = entry.getKey();
            entry.getValue().onTouch(key, !z && key == a ? motionEvent : null);
        }
    }

    private View a(ViewGroup viewGroup, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.getLocalVisibleRect(this.b);
            this.b.offset(childAt.getLeft(), childAt.getTop());
            if (this.b.contains(x, y)) {
                return childAt;
            }
        }
        return null;
    }
}
