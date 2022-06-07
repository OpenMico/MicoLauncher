package androidx.viewpager2.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ScrollEventAdapter.java */
/* loaded from: classes.dex */
public final class e extends RecyclerView.OnScrollListener {
    private ViewPager2.OnPageChangeCallback a;
    @NonNull
    private final ViewPager2 b;
    @NonNull
    private final RecyclerView c;
    @NonNull
    private final LinearLayoutManager d;
    private int e;
    private int f;
    private a g = new a();
    private int h;
    private int i;
    private boolean j;
    private boolean k;
    private boolean l;
    private boolean m;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(@NonNull ViewPager2 viewPager2) {
        this.b = viewPager2;
        this.c = this.b.d;
        this.d = (LinearLayoutManager) this.c.getLayoutManager();
        i();
    }

    private void i() {
        this.e = 0;
        this.f = 0;
        this.g.a();
        this.h = -1;
        this.i = -1;
        this.j = false;
        this.k = false;
        this.m = false;
        this.l = false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i) {
        boolean z = true;
        if (!(this.e == 1 && this.f == 1) && i == 1) {
            a(false);
        } else if (!k() || i != 2) {
            if (k() && i == 0) {
                j();
                if (!this.k) {
                    if (this.g.a != -1) {
                        a(this.g.a, 0.0f, 0);
                    }
                } else if (this.g.c != 0) {
                    z = false;
                } else if (this.h != this.g.a) {
                    b(this.g.a);
                }
                if (z) {
                    a(0);
                    i();
                }
            }
            if (this.e == 2 && i == 0 && this.l) {
                j();
                if (this.g.c == 0) {
                    if (this.i != this.g.a) {
                        b(this.g.a == -1 ? 0 : this.g.a);
                    }
                    a(0);
                    i();
                }
            }
        } else if (this.k) {
            a(2);
            this.j = true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001d, code lost:
        if ((r5 < 0) == r3.b.b()) goto L_0x0022;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003d  */
    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onScrolled(@androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView r4, int r5, int r6) {
        /*
            r3 = this;
            r4 = 1
            r3.k = r4
            r3.j()
            boolean r0 = r3.j
            r1 = -1
            r2 = 0
            if (r0 == 0) goto L_0x0041
            r3.j = r2
            if (r6 > 0) goto L_0x0022
            if (r6 != 0) goto L_0x0020
            if (r5 >= 0) goto L_0x0016
            r5 = r4
            goto L_0x0017
        L_0x0016:
            r5 = r2
        L_0x0017:
            androidx.viewpager2.widget.ViewPager2 r6 = r3.b
            boolean r6 = r6.b()
            if (r5 != r6) goto L_0x0020
            goto L_0x0022
        L_0x0020:
            r5 = r2
            goto L_0x0023
        L_0x0022:
            r5 = r4
        L_0x0023:
            if (r5 == 0) goto L_0x0031
            androidx.viewpager2.widget.e$a r5 = r3.g
            int r5 = r5.c
            if (r5 == 0) goto L_0x0031
            androidx.viewpager2.widget.e$a r5 = r3.g
            int r5 = r5.a
            int r5 = r5 + r4
            goto L_0x0035
        L_0x0031:
            androidx.viewpager2.widget.e$a r5 = r3.g
            int r5 = r5.a
        L_0x0035:
            r3.i = r5
            int r5 = r3.h
            int r6 = r3.i
            if (r5 == r6) goto L_0x004f
            r3.b(r6)
            goto L_0x004f
        L_0x0041:
            int r5 = r3.e
            if (r5 != 0) goto L_0x004f
            androidx.viewpager2.widget.e$a r5 = r3.g
            int r5 = r5.a
            if (r5 != r1) goto L_0x004c
            r5 = r2
        L_0x004c:
            r3.b(r5)
        L_0x004f:
            androidx.viewpager2.widget.e$a r5 = r3.g
            int r5 = r5.a
            if (r5 != r1) goto L_0x0057
            r5 = r2
            goto L_0x005b
        L_0x0057:
            androidx.viewpager2.widget.e$a r5 = r3.g
            int r5 = r5.a
        L_0x005b:
            androidx.viewpager2.widget.e$a r6 = r3.g
            float r6 = r6.b
            androidx.viewpager2.widget.e$a r0 = r3.g
            int r0 = r0.c
            r3.a(r5, r6, r0)
            androidx.viewpager2.widget.e$a r5 = r3.g
            int r5 = r5.a
            int r6 = r3.i
            if (r5 == r6) goto L_0x0070
            if (r6 != r1) goto L_0x0080
        L_0x0070:
            androidx.viewpager2.widget.e$a r5 = r3.g
            int r5 = r5.c
            if (r5 != 0) goto L_0x0080
            int r5 = r3.f
            if (r5 == r4) goto L_0x0080
            r3.a(r2)
            r3.i()
        L_0x0080:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager2.widget.e.onScrolled(androidx.recyclerview.widget.RecyclerView, int, int):void");
    }

    private void j() {
        int i;
        a aVar = this.g;
        aVar.a = this.d.findFirstVisibleItemPosition();
        if (aVar.a == -1) {
            aVar.a();
            return;
        }
        View findViewByPosition = this.d.findViewByPosition(aVar.a);
        if (findViewByPosition == null) {
            aVar.a();
            return;
        }
        int leftDecorationWidth = this.d.getLeftDecorationWidth(findViewByPosition);
        int rightDecorationWidth = this.d.getRightDecorationWidth(findViewByPosition);
        int topDecorationHeight = this.d.getTopDecorationHeight(findViewByPosition);
        int bottomDecorationHeight = this.d.getBottomDecorationHeight(findViewByPosition);
        ViewGroup.LayoutParams layoutParams = findViewByPosition.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            leftDecorationWidth += marginLayoutParams.leftMargin;
            rightDecorationWidth += marginLayoutParams.rightMargin;
            topDecorationHeight += marginLayoutParams.topMargin;
            bottomDecorationHeight += marginLayoutParams.bottomMargin;
        }
        r3 = findViewByPosition.getHeight() + topDecorationHeight + bottomDecorationHeight;
        int width = rightDecorationWidth + findViewByPosition.getWidth() + leftDecorationWidth;
        if (this.d.getOrientation() == 0) {
            i = (findViewByPosition.getLeft() - leftDecorationWidth) - this.c.getPaddingLeft();
            if (this.b.b()) {
                i = -i;
            }
        } else {
            i = (findViewByPosition.getTop() - topDecorationHeight) - this.c.getPaddingTop();
        }
        aVar.c = -i;
        if (aVar.c >= 0) {
            aVar.b = width == 0 ? 0.0f : aVar.c / width;
        } else if (new a(this.d).a()) {
            throw new IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
        } else {
            throw new IllegalStateException(String.format(Locale.US, "Page can only be offset by a positive amount, not by %d", Integer.valueOf(aVar.c)));
        }
    }

    private void a(boolean z) {
        this.m = z;
        this.e = z ? 4 : 1;
        int i = this.i;
        if (i != -1) {
            this.h = i;
            this.i = -1;
        } else if (this.h == -1) {
            this.h = l();
        }
        a(1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.l = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, boolean z) {
        this.e = z ? 2 : 3;
        boolean z2 = false;
        this.m = false;
        if (this.i != i) {
            z2 = true;
        }
        this.i = i;
        a(2);
        if (z2) {
            b(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        this.e = 4;
        a(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c() {
        if (!f() || this.m) {
            this.m = false;
            j();
            if (this.g.c == 0) {
                if (this.g.a != this.h) {
                    b(this.g.a);
                }
                a(0);
                i();
                return;
            }
            a(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ViewPager2.OnPageChangeCallback onPageChangeCallback) {
        this.a = onPageChangeCallback;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int d() {
        return this.f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean e() {
        return this.f == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean f() {
        return this.f == 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean g() {
        return this.m;
    }

    private boolean k() {
        int i = this.e;
        return i == 1 || i == 4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public double h() {
        j();
        return this.g.a + this.g.b;
    }

    private void a(int i) {
        if ((this.e != 3 || this.f != 0) && this.f != i) {
            this.f = i;
            ViewPager2.OnPageChangeCallback onPageChangeCallback = this.a;
            if (onPageChangeCallback != null) {
                onPageChangeCallback.onPageScrollStateChanged(i);
            }
        }
    }

    private void b(int i) {
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.a;
        if (onPageChangeCallback != null) {
            onPageChangeCallback.onPageSelected(i);
        }
    }

    private void a(int i, float f, int i2) {
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.a;
        if (onPageChangeCallback != null) {
            onPageChangeCallback.onPageScrolled(i, f, i2);
        }
    }

    private int l() {
        return this.d.findFirstVisibleItemPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ScrollEventAdapter.java */
    /* loaded from: classes.dex */
    public static final class a {
        int a;
        float b;
        int c;

        a() {
        }

        void a() {
            this.a = -1;
            this.b = 0.0f;
            this.c = 0;
        }
    }
}
