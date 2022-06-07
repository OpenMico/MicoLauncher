package com.google.android.material.appbar;

import android.view.View;
import androidx.core.view.ViewCompat;

/* compiled from: ViewOffsetHelper.java */
/* loaded from: classes2.dex */
class d {
    private final View a;
    private int b;
    private int c;
    private int d;
    private int e;
    private boolean f = true;
    private boolean g = true;

    public d(View view) {
        this.a = view;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.b = this.a.getTop();
        this.c = this.a.getLeft();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        View view = this.a;
        ViewCompat.offsetTopAndBottom(view, this.d - (view.getTop() - this.b));
        View view2 = this.a;
        ViewCompat.offsetLeftAndRight(view2, this.e - (view2.getLeft() - this.c));
    }

    public boolean a(int i) {
        if (!this.f || this.d == i) {
            return false;
        }
        this.d = i;
        b();
        return true;
    }

    public boolean b(int i) {
        if (!this.g || this.e == i) {
            return false;
        }
        this.e = i;
        b();
        return true;
    }

    public int c() {
        return this.d;
    }

    public int d() {
        return this.e;
    }

    public int e() {
        return this.b;
    }

    public void a(boolean z) {
        this.f = z;
    }

    public boolean f() {
        return this.f;
    }

    public void b(boolean z) {
        this.g = z;
    }

    public boolean g() {
        return this.g;
    }
}
