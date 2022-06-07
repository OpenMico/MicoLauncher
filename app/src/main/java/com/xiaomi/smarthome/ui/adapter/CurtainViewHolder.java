package com.xiaomi.smarthome.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.smarthome.ui.widgets.curtain.CurtainView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: GroupAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/smarthome/ui/adapter/CurtainViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", OneTrack.Event.VIEW, "Lcom/xiaomi/smarthome/ui/widgets/curtain/CurtainView;", "(Lcom/xiaomi/smarthome/ui/widgets/curtain/CurtainView;)V", "getView", "()Lcom/xiaomi/smarthome/ui/widgets/curtain/CurtainView;", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class CurtainViewHolder extends RecyclerView.ViewHolder {
    @NotNull
    private final CurtainView a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurtainViewHolder(@NotNull CurtainView view) {
        super(view);
        Intrinsics.checkNotNullParameter(view, "view");
        this.a = view;
    }

    @NotNull
    public final CurtainView getView() {
        return this.a;
    }
}
