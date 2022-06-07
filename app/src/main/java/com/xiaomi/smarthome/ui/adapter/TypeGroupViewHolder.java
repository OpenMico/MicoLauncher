package com.xiaomi.smarthome.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.databinding.ItemTypeGroupBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: CategoryModeDeviceListAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/smarthome/ui/adapter/TypeGroupViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/xiaomi/smarthome/databinding/ItemTypeGroupBinding;", "(Lcom/xiaomi/smarthome/databinding/ItemTypeGroupBinding;)V", "getBinding", "()Lcom/xiaomi/smarthome/databinding/ItemTypeGroupBinding;", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class TypeGroupViewHolder extends RecyclerView.ViewHolder {
    @NotNull
    private final ItemTypeGroupBinding a;

    @NotNull
    public final ItemTypeGroupBinding getBinding() {
        return this.a;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeGroupViewHolder(@NotNull ItemTypeGroupBinding binding) {
        super(binding.getRoot());
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.a = binding;
    }
}
