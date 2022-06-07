package com.xiaomi.micolauncher.module.homepage.activity;

import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.adapter.SubAudiobookContentAdapter;
import com.xiaomi.micolauncher.module.homepage.event.LoadHistoryEvent;
import com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AudiobookHistoryActivity extends AudiobookCommonActivity {
    @Override // com.xiaomi.micolauncher.module.homepage.activity.AudiobookCommonActivity
    protected int getTitleResource() {
        return R.string.audiobook_history;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.activity.AudiobookCommonActivity, com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity
    public void loadData() {
        super.loadData();
        AudiobookDataManager.getManager().loadHistory(50);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadHistoryDataSuccess(LoadHistoryEvent loadHistoryEvent) {
        showOrHideEmptyLayout(loadHistoryEvent.items);
        this.b.setVisibility(8);
        this.adapter = new SubAudiobookContentAdapter();
        this.adapter.setBlackList(this.blackList);
        this.adapter.setItems(loadHistoryEvent.items, null);
        this.e.setAdapter(this.adapter);
    }
}
