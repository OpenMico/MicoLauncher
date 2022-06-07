package androidx.transition;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import java.util.ArrayList;

/* compiled from: GhostViewHolder.java */
@SuppressLint({"ViewConstructor"})
/* loaded from: classes.dex */
class e extends FrameLayout {
    @NonNull
    private ViewGroup a;
    private boolean b = true;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(ViewGroup viewGroup) {
        super(viewGroup.getContext());
        setClipChildren(false);
        this.a = viewGroup;
        this.a.setTag(R.id.ghost_view_holder, this);
        w.a(this.a).a(this);
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        if (this.b) {
            super.onViewAdded(view);
            return;
        }
        throw new IllegalStateException("This GhostViewHolder is detached!");
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if ((getChildCount() == 1 && getChildAt(0) == view) || getChildCount() == 0) {
            this.a.setTag(R.id.ghost_view_holder, null);
            w.a(this.a).b(this);
            this.b = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static e a(@NonNull ViewGroup viewGroup) {
        return (e) viewGroup.getTag(R.id.ghost_view_holder);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        if (this.b) {
            w.a(this.a).b(this);
            w.a(this.a).a(this);
            return;
        }
        throw new IllegalStateException("This GhostViewHolder is detached!");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(g gVar) {
        ArrayList<View> arrayList = new ArrayList<>();
        a(gVar.c, arrayList);
        int a = a(arrayList);
        if (a < 0 || a >= getChildCount()) {
            addView(gVar);
        } else {
            addView(gVar, a);
        }
    }

    private int a(ArrayList<View> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        int childCount = getChildCount() - 1;
        int i = 0;
        while (i <= childCount) {
            int i2 = (i + childCount) / 2;
            a(((g) getChildAt(i2)).c, arrayList2);
            if (a(arrayList, arrayList2)) {
                i = i2 + 1;
            } else {
                childCount = i2 - 1;
            }
            arrayList2.clear();
        }
        return i;
    }

    private static boolean a(ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        if (arrayList.isEmpty() || arrayList2.isEmpty() || arrayList.get(0) != arrayList2.get(0)) {
            return true;
        }
        int min = Math.min(arrayList.size(), arrayList2.size());
        for (int i = 1; i < min; i++) {
            View view = arrayList.get(i);
            View view2 = arrayList2.get(i);
            if (view != view2) {
                return a(view, view2);
            }
        }
        return arrayList2.size() == min;
    }

    private static void a(View view, ArrayList<View> arrayList) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            a((View) parent, arrayList);
        }
        arrayList.add(view);
    }

    private static boolean a(View view, View view2) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int childCount = viewGroup.getChildCount();
        if (Build.VERSION.SDK_INT >= 21 && view.getZ() != view2.getZ()) {
            return view.getZ() > view2.getZ();
        }
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(w.a(viewGroup, i));
            if (childAt == view) {
                return false;
            }
            if (childAt == view2) {
                return true;
            }
        }
        return true;
    }
}
