package com.jakewharton.rxbinding4.widget;

import android.view.View;
import android.widget.AdapterView;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AdapterViewItemLongClickEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\r\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0016\u001a\u00020\tHÆ\u0003J7\u0010\u0017\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u0007HÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001e"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/AdapterViewItemLongClickEvent;", "", OneTrack.Event.VIEW, "Landroid/widget/AdapterView;", "clickedView", "Landroid/view/View;", "position", "", "id", "", "(Landroid/widget/AdapterView;Landroid/view/View;IJ)V", "getClickedView", "()Landroid/view/View;", "getId", "()J", "getPosition", "()I", "getView", "()Landroid/widget/AdapterView;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class AdapterViewItemLongClickEvent {
    @NotNull
    private final AdapterView<?> a;
    @Nullable
    private final View b;
    private final int c;
    private final long d;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AdapterViewItemLongClickEvent copy$default(AdapterViewItemLongClickEvent adapterViewItemLongClickEvent, AdapterView adapterView, View view, int i, long j, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            adapterView = adapterViewItemLongClickEvent.a;
        }
        if ((i2 & 2) != 0) {
            view = adapterViewItemLongClickEvent.b;
        }
        if ((i2 & 4) != 0) {
            i = adapterViewItemLongClickEvent.c;
        }
        if ((i2 & 8) != 0) {
            j = adapterViewItemLongClickEvent.d;
        }
        return adapterViewItemLongClickEvent.copy(adapterView, view, i, j);
    }

    @NotNull
    public final AdapterView<?> component1() {
        return this.a;
    }

    @Nullable
    public final View component2() {
        return this.b;
    }

    public final int component3() {
        return this.c;
    }

    public final long component4() {
        return this.d;
    }

    @NotNull
    public final AdapterViewItemLongClickEvent copy(@NotNull AdapterView<?> view, @Nullable View view2, int i, long j) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        return new AdapterViewItemLongClickEvent(view, view2, i, j);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AdapterViewItemLongClickEvent)) {
            return false;
        }
        AdapterViewItemLongClickEvent adapterViewItemLongClickEvent = (AdapterViewItemLongClickEvent) obj;
        return Intrinsics.areEqual(this.a, adapterViewItemLongClickEvent.a) && Intrinsics.areEqual(this.b, adapterViewItemLongClickEvent.b) && this.c == adapterViewItemLongClickEvent.c && this.d == adapterViewItemLongClickEvent.d;
    }

    public int hashCode() {
        AdapterView<?> adapterView = this.a;
        int i = 0;
        int hashCode = (adapterView != null ? adapterView.hashCode() : 0) * 31;
        View view = this.b;
        if (view != null) {
            i = view.hashCode();
        }
        return ((((hashCode + i) * 31) + Integer.hashCode(this.c)) * 31) + Long.hashCode(this.d);
    }

    @NotNull
    public String toString() {
        return "AdapterViewItemLongClickEvent(view=" + this.a + ", clickedView=" + this.b + ", position=" + this.c + ", id=" + this.d + ")";
    }

    public AdapterViewItemLongClickEvent(@NotNull AdapterView<?> view, @Nullable View view2, int i, long j) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
        this.b = view2;
        this.c = i;
        this.d = j;
    }

    @NotNull
    public final AdapterView<?> getView() {
        return this.a;
    }

    @Nullable
    public final View getClickedView() {
        return this.b;
    }

    public final int getPosition() {
        return this.c;
    }

    public final long getId() {
        return this.d;
    }
}
