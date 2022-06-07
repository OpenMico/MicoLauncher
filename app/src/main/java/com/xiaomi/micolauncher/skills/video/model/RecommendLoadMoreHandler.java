package com.xiaomi.micolauncher.skills.video.model;

import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;

/* loaded from: classes3.dex */
public class RecommendLoadMoreHandler implements LoadMoreHandler {
    @Override // com.xiaomi.micolauncher.skills.video.model.LoadMoreHandler
    public void loadMore() {
        if (VideoModel.getInstance().getPlayIndex() >= VideoModel.getInstance().getPlayList().size() - 1) {
            RecommendDataManager.getManager().refreshNewsList();
        }
    }
}
