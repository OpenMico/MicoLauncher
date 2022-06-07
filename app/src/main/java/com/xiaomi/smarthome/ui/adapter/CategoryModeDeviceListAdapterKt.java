package com.xiaomi.smarthome.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: CategoryModeDeviceListAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001b\u0010\u0000\u001a\u00020\u00018FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"pool", "Landroidx/recyclerview/widget/RecyclerView$RecycledViewPool;", "getPool", "()Landroidx/recyclerview/widget/RecyclerView$RecycledViewPool;", "pool$delegate", "Lkotlin/Lazy;", "smarthome_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class CategoryModeDeviceListAdapterKt {
    @NotNull
    private static final Lazy a = LazyKt.lazy(a.a);

    @NotNull
    public static final RecyclerView.RecycledViewPool getPool() {
        return (RecyclerView.RecycledViewPool) a.getValue();
    }

    /* compiled from: CategoryModeDeviceListAdapter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Landroidx/recyclerview/widget/RecyclerView$RecycledViewPool;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a extends Lambda implements Function0<RecyclerView.RecycledViewPool> {
        public static final a a = new a();

        a() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final RecyclerView.RecycledViewPool invoke() {
            return new RecyclerView.RecycledViewPool();
        }
    }
}
