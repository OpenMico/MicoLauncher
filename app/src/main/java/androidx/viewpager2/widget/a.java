package androidx.viewpager2.widget;

import android.animation.LayoutTransition;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AnimateLayoutChangeDetector.java */
/* loaded from: classes.dex */
public final class a {
    private static final ViewGroup.MarginLayoutParams a = new ViewGroup.MarginLayoutParams(-1, -1);
    private LinearLayoutManager b;

    static {
        a.setMargins(0, 0, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull LinearLayoutManager linearLayoutManager) {
        this.b = linearLayoutManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return (!b() || this.b.getChildCount() <= 1) && c();
    }

    private boolean b() {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        int i;
        int i2;
        int i3;
        int childCount = this.b.getChildCount();
        if (childCount == 0) {
            return true;
        }
        boolean z = this.b.getOrientation() == 0;
        int[][] iArr = (int[][]) Array.newInstance(int.class, childCount, 2);
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = this.b.getChildAt(i4);
            if (childAt != null) {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                } else {
                    marginLayoutParams = a;
                }
                int[] iArr2 = iArr[i4];
                if (z) {
                    i = childAt.getLeft() - marginLayoutParams.leftMargin;
                } else {
                    i = childAt.getTop() - marginLayoutParams.topMargin;
                }
                iArr2[0] = i;
                int[] iArr3 = iArr[i4];
                if (z) {
                    i3 = childAt.getRight();
                    i2 = marginLayoutParams.rightMargin;
                } else {
                    i3 = childAt.getBottom();
                    i2 = marginLayoutParams.bottomMargin;
                }
                iArr3[1] = i3 + i2;
            } else {
                throw new IllegalStateException("null view contained in the view hierarchy");
            }
        }
        Arrays.sort(iArr, new Comparator<int[]>() { // from class: androidx.viewpager2.widget.a.1
            /* renamed from: a */
            public int compare(int[] iArr4, int[] iArr5) {
                return iArr4[0] - iArr5[0];
            }
        });
        for (int i5 = 1; i5 < childCount; i5++) {
            if (iArr[i5 - 1][1] != iArr[i5][0]) {
                return false;
            }
        }
        return iArr[0][0] <= 0 && iArr[childCount - 1][1] >= iArr[0][1] - iArr[0][0];
    }

    private boolean c() {
        int childCount = this.b.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (a(this.b.getChildAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            LayoutTransition layoutTransition = viewGroup.getLayoutTransition();
            if (layoutTransition != null && layoutTransition.isChangingLayout()) {
                return true;
            }
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (a(viewGroup.getChildAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }
}
