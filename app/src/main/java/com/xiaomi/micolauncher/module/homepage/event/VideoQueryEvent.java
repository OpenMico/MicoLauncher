package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.micolauncher.module.homepage.bean.VideoData;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoQueryEvent {
    public List<VideoData> datas;

    public VideoQueryEvent(List<VideoData> list) {
        this.datas = list;
    }
}
