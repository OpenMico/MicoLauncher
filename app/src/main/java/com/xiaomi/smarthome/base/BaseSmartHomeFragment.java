package com.xiaomi.smarthome.base;

import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.miot.support.MiotManager;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseSmartHomeFragment.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lcom/xiaomi/smarthome/base/BaseSmartHomeFragment;", "Lcom/xiaomi/smarthome/base/BaseFragment;", "()V", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "setRecyclerView", "(Landroidx/recyclerview/widget/RecyclerView;)V", "onStart", "", "onStop", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public class BaseSmartHomeFragment extends BaseFragment {
    @Nullable
    private RecyclerView a;

    @Nullable
    public final RecyclerView getRecyclerView() {
        return this.a;
    }

    public final void setRecyclerView(@Nullable RecyclerView recyclerView) {
        this.a = recyclerView;
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        MiotManager.enterDeviceListPage();
    }

    @Override // com.xiaomi.smarthome.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        MiotManager.exitDeviceListPage();
    }
}
