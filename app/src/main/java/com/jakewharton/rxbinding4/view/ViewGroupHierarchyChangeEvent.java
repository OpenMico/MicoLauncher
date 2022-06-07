package com.jakewharton.rxbinding4.view;

import android.view.View;
import android.view.ViewGroup;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: ViewGroupHierarchyChangeEvent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0001\u0002\u000b\f¨\u0006\r"}, d2 = {"Lcom/jakewharton/rxbinding4/view/ViewGroupHierarchyChangeEvent;", "", "()V", VideoDataManager.ORIGIN_CHILD, "Landroid/view/View;", "getChild", "()Landroid/view/View;", OneTrack.Event.VIEW, "Landroid/view/ViewGroup;", "getView", "()Landroid/view/ViewGroup;", "Lcom/jakewharton/rxbinding4/view/ViewGroupHierarchyChildViewAddEvent;", "Lcom/jakewharton/rxbinding4/view/ViewGroupHierarchyChildViewRemoveEvent;", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public abstract class ViewGroupHierarchyChangeEvent {
    @NotNull
    public abstract View getChild();

    @NotNull
    public abstract ViewGroup getView();

    private ViewGroupHierarchyChangeEvent() {
    }

    public /* synthetic */ ViewGroupHierarchyChangeEvent(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
