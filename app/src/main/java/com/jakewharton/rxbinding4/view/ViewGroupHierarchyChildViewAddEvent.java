package com.jakewharton.rxbinding4.view;

import android.view.View;
import android.view.ViewGroup;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ViewGroupHierarchyChangeEvent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lcom/jakewharton/rxbinding4/view/ViewGroupHierarchyChildViewAddEvent;", "Lcom/jakewharton/rxbinding4/view/ViewGroupHierarchyChangeEvent;", OneTrack.Event.VIEW, "Landroid/view/ViewGroup;", VideoDataManager.ORIGIN_CHILD, "Landroid/view/View;", "(Landroid/view/ViewGroup;Landroid/view/View;)V", "getChild", "()Landroid/view/View;", "getView", "()Landroid/view/ViewGroup;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class ViewGroupHierarchyChildViewAddEvent extends ViewGroupHierarchyChangeEvent {
    @NotNull
    private final ViewGroup a;
    @NotNull
    private final View b;

    public static /* synthetic */ ViewGroupHierarchyChildViewAddEvent copy$default(ViewGroupHierarchyChildViewAddEvent viewGroupHierarchyChildViewAddEvent, ViewGroup viewGroup, View view, int i, Object obj) {
        if ((i & 1) != 0) {
            viewGroup = viewGroupHierarchyChildViewAddEvent.getView();
        }
        if ((i & 2) != 0) {
            view = viewGroupHierarchyChildViewAddEvent.getChild();
        }
        return viewGroupHierarchyChildViewAddEvent.copy(viewGroup, view);
    }

    @NotNull
    public final ViewGroup component1() {
        return getView();
    }

    @NotNull
    public final View component2() {
        return getChild();
    }

    @NotNull
    public final ViewGroupHierarchyChildViewAddEvent copy(@NotNull ViewGroup view, @NotNull View child) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(child, "child");
        return new ViewGroupHierarchyChildViewAddEvent(view, child);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ViewGroupHierarchyChildViewAddEvent)) {
            return false;
        }
        ViewGroupHierarchyChildViewAddEvent viewGroupHierarchyChildViewAddEvent = (ViewGroupHierarchyChildViewAddEvent) obj;
        return Intrinsics.areEqual(getView(), viewGroupHierarchyChildViewAddEvent.getView()) && Intrinsics.areEqual(getChild(), viewGroupHierarchyChildViewAddEvent.getChild());
    }

    public int hashCode() {
        ViewGroup view = getView();
        int i = 0;
        int hashCode = (view != null ? view.hashCode() : 0) * 31;
        View child = getChild();
        if (child != null) {
            i = child.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "ViewGroupHierarchyChildViewAddEvent(view=" + getView() + ", child=" + getChild() + ")";
    }

    @Override // com.jakewharton.rxbinding4.view.ViewGroupHierarchyChangeEvent
    @NotNull
    public ViewGroup getView() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding4.view.ViewGroupHierarchyChangeEvent
    @NotNull
    public View getChild() {
        return this.b;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewGroupHierarchyChildViewAddEvent(@NotNull ViewGroup view, @NotNull View child) {
        super(null);
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(child, "child");
        this.a = view;
        this.b = child;
    }
}
