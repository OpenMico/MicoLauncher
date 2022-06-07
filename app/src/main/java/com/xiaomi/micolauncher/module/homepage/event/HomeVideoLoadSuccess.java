package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.micolauncher.module.homepage.bean.HomeVideoData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabData;
import java.util.List;

/* loaded from: classes3.dex */
public class HomeVideoLoadSuccess {
    public HomeVideoData homeVideoData;
    public boolean isLocalData;
    public List<VideoTabData> tabDatas;
    public String tabKey;

    public HomeVideoLoadSuccess(List<VideoTabData> list, HomeVideoData homeVideoData, String str) {
        this.tabDatas = list;
        this.homeVideoData = homeVideoData;
        this.tabKey = str;
    }
}
