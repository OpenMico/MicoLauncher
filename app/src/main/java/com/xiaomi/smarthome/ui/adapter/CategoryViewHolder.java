package com.xiaomi.smarthome.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: CategoryListAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lcom/xiaomi/smarthome/ui/adapter/CategoryViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "ivDragHandle", "Landroid/widget/ImageView;", "getIvDragHandle", "()Landroid/widget/ImageView;", "ivPic", "getIvPic", "tvName", "Landroid/widget/TextView;", "getTvName", "()Landroid/widget/TextView;", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class CategoryViewHolder extends RecyclerView.ViewHolder {
    @NotNull
    private final ImageView a;
    @NotNull
    private final TextView b;
    @NotNull
    private final ImageView c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CategoryViewHolder(@NotNull View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        View findViewById = itemView.findViewById(R.id.ivCategory);
        Intrinsics.checkNotNullExpressionValue(findViewById, "itemView.findViewById(R.id.ivCategory)");
        this.a = (ImageView) findViewById;
        View findViewById2 = itemView.findViewById(R.id.tvCategory);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "itemView.findViewById(R.id.tvCategory)");
        this.b = (TextView) findViewById2;
        View findViewById3 = itemView.findViewById(R.id.ivDragHandle);
        Intrinsics.checkNotNullExpressionValue(findViewById3, "itemView.findViewById(R.id.ivDragHandle)");
        this.c = (ImageView) findViewById3;
    }

    @NotNull
    public final ImageView getIvPic() {
        return this.a;
    }

    @NotNull
    public final TextView getTvName() {
        return this.b;
    }

    @NotNull
    public final ImageView getIvDragHandle() {
        return this.c;
    }
}
