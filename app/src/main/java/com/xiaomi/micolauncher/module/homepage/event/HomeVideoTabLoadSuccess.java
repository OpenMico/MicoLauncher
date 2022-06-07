package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.micolauncher.module.homepage.bean.VideoTabData;
import java.util.List;

/* loaded from: classes3.dex */
public class HomeVideoTabLoadSuccess {
    private List<VideoTabData> a;

    public HomeVideoTabLoadSuccess(List<VideoTabData> list) {
        this.a = list;
    }

    public List<VideoTabData> getTabList() {
        return this.a;
    }
}
