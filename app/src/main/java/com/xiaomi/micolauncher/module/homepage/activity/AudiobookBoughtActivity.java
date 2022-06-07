package com.xiaomi.micolauncher.module.homepage.activity;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.adapter.SubAudiobookContentAdapter;
import com.xiaomi.micolauncher.module.homepage.bean.Order;
import com.xiaomi.micolauncher.module.homepage.event.LoadPaymentOrderEvent;
import com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AudiobookBoughtActivity extends AudiobookCommonActivity {
    private Order f;
    private List<Order.OrderInfo> g;

    @Override // com.xiaomi.micolauncher.module.homepage.activity.AudiobookCommonActivity
    protected int getTitleResource() {
        return R.string.audiobook_buy_text;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.activity.AudiobookCommonActivity, com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity
    public void loadData() {
        super.loadData();
        AudiobookDataManager.getManager().loadPaymentOrders(this, this.f == null ? 0L : 1L);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadPaymentOrderSuccess(LoadPaymentOrderEvent loadPaymentOrderEvent) {
        this.b.setVisibility(8);
        this.f = loadPaymentOrderEvent.order;
        List<Order.OrderInfo> list = this.f.list;
        if (ContainerUtil.isEmpty(this.g)) {
            showOrHideEmptyLayout(list);
        }
        if (!ContainerUtil.isEmpty(list)) {
            if (ContainerUtil.isEmpty(this.g)) {
                this.adapter = new SubAudiobookContentAdapter();
                this.g = list;
                this.adapter.setBlackList(this.blackList);
                this.adapter.setOrderInfos(this.g);
                this.e.setAdapter(this.adapter);
                return;
            }
            this.g.addAll(list);
            this.adapter.notifyDataSetChanged();
        }
    }
}
