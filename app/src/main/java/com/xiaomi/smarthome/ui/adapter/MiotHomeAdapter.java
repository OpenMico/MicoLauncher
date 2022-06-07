package com.xiaomi.smarthome.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.widgets.HomeSelectListener;
import com.xiaomi.smarthome.ui.widgets.MiotHomeViewHolder;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class MiotHomeAdapter extends BaseMiotListAdapter<MiotHome> {
    private final LayoutInflater a;
    private HomeSelectListener b;

    public MiotHomeAdapter(Context context) {
        this.a = LayoutInflater.from(context);
    }

    public void setHomeSelectListener(HomeSelectListener homeSelectListener) {
        this.b = homeSelectListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MiotHomeViewHolder(this.a.inflate(R.layout.item_smart_home_miot_home, viewGroup, false));
    }

    @SuppressLint({"CheckResult"})
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        if (baseViewHolder instanceof MiotHomeViewHolder) {
            MiotHomeViewHolder miotHomeViewHolder = (MiotHomeViewHolder) baseViewHolder;
            final MiotHome miotHome = (MiotHome) this.dataList.get(i);
            miotHomeViewHolder.bindData(miotHome, i, getItemCount());
            RxViewHelp.debounceClicksWithOneSeconds(miotHomeViewHolder.itemView).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.adapter.-$$Lambda$MiotHomeAdapter$bcr0IRKxed_8I5wnKrLDBaFbfL0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MiotHomeAdapter.this.a(miotHome, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MiotHome miotHome, Object obj) throws Exception {
        for (MiotHome miotHome2 : this.dataList) {
            miotHome2.selected = miotHome2.homeId.equals(miotHome.homeId);
        }
        HomeSelectListener homeSelectListener = this.b;
        if (homeSelectListener != null) {
            homeSelectListener.onHomeChanged(miotHome, this.dataList);
        }
    }
}
