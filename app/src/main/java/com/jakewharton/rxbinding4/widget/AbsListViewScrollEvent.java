package com.jakewharton.rxbinding4.widget;

import android.widget.AbsListView;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbsListViewScrollEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001d"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/AbsListViewScrollEvent;", "", OneTrack.Event.VIEW, "Landroid/widget/AbsListView;", "scrollState", "", "firstVisibleItem", "visibleItemCount", "totalItemCount", "(Landroid/widget/AbsListView;IIII)V", "getFirstVisibleItem", "()I", "getScrollState", "getTotalItemCount", "getView", "()Landroid/widget/AbsListView;", "getVisibleItemCount", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class AbsListViewScrollEvent {
    @NotNull
    private final AbsListView a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;

    public static /* synthetic */ AbsListViewScrollEvent copy$default(AbsListViewScrollEvent absListViewScrollEvent, AbsListView absListView, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            absListView = absListViewScrollEvent.a;
        }
        if ((i5 & 2) != 0) {
            i = absListViewScrollEvent.b;
        }
        if ((i5 & 4) != 0) {
            i2 = absListViewScrollEvent.c;
        }
        if ((i5 & 8) != 0) {
            i3 = absListViewScrollEvent.d;
        }
        if ((i5 & 16) != 0) {
            i4 = absListViewScrollEvent.e;
        }
        return absListViewScrollEvent.copy(absListView, i, i2, i3, i4);
    }

    @NotNull
    public final AbsListView component1() {
        return this.a;
    }

    public final int component2() {
        return this.b;
    }

    public final int component3() {
        return this.c;
    }

    public final int component4() {
        return this.d;
    }

    public final int component5() {
        return this.e;
    }

    @NotNull
    public final AbsListViewScrollEvent copy(@NotNull AbsListView view, int i, int i2, int i3, int i4) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        return new AbsListViewScrollEvent(view, i, i2, i3, i4);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbsListViewScrollEvent)) {
            return false;
        }
        AbsListViewScrollEvent absListViewScrollEvent = (AbsListViewScrollEvent) obj;
        return Intrinsics.areEqual(this.a, absListViewScrollEvent.a) && this.b == absListViewScrollEvent.b && this.c == absListViewScrollEvent.c && this.d == absListViewScrollEvent.d && this.e == absListViewScrollEvent.e;
    }

    public int hashCode() {
        AbsListView absListView = this.a;
        return ((((((((absListView != null ? absListView.hashCode() : 0) * 31) + Integer.hashCode(this.b)) * 31) + Integer.hashCode(this.c)) * 31) + Integer.hashCode(this.d)) * 31) + Integer.hashCode(this.e);
    }

    @NotNull
    public String toString() {
        return "AbsListViewScrollEvent(view=" + this.a + ", scrollState=" + this.b + ", firstVisibleItem=" + this.c + ", visibleItemCount=" + this.d + ", totalItemCount=" + this.e + ")";
    }

    public AbsListViewScrollEvent(@NotNull AbsListView view, int i, int i2, int i3, int i4) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
    }

    @NotNull
    public final AbsListView getView() {
        return this.a;
    }

    public final int getScrollState() {
        return this.b;
    }

    public final int getFirstVisibleItem() {
        return this.c;
    }

    public final int getVisibleItemCount() {
        return this.d;
    }

    public final int getTotalItemCount() {
        return this.e;
    }
}
